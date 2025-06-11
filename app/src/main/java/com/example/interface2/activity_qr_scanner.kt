package com.example.interface2

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.common.HybridBinarizer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class activity_qr_scanner : AppCompatActivity() {

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var previewView: PreviewView

    private val CAMERA_REQUEST_CODE = 101
    private val TAG = "QR_SCANNER_APP"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_scanner)

        previewView = findViewById(R.id.cameraPreviewView)
        cameraExecutor = Executors.newSingleThreadExecutor()

        if (hasCameraPermission()) {
            startCamera()
        } else {
            requestCameraPermission()
        }
    }

    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            Toast.makeText(this, "Permissão da câmera é necessária", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindCameraUseCases(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindCameraUseCases(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        // --- MUDANÇA 1: Adicionando o caso de uso de Análise de Imagem ---
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        // --- MUDANÇA 2: Configurando o analisador para processar os frames ---
        imageAnalysis.setAnalyzer(cameraExecutor, QRCodeAnalyzer { result ->
            // Este bloco é executado quando um QR code é encontrado
            runOnUiThread {
                Toast.makeText(this, "QR Code: ${result.text}", Toast.LENGTH_LONG).show()
                // Para a câmera para evitar múltiplas leituras
                cameraProvider.unbindAll()
            }
        })

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            // --- MUDANÇA 3: Vinculando o novo caso de uso (imageAnalysis) à câmera ---
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                imageAnalysis // Adicionado aqui!
            )
        } catch (exc: Exception) {
            Log.e(TAG, "Falha ao vincular os casos de uso", exc)
        }
    }

    // --- MUDANÇA 4: Classe interna para analisar a imagem ---
    private class QRCodeAnalyzer(private val onQrCodeScanned: (com.google.zxing.Result) -> Unit) : ImageAnalysis.Analyzer {

        private val reader = MultiFormatReader()

        @SuppressLint("UnsafeOptInUsageError")
        override fun analyze(imageProxy: ImageProxy) {
            try {
                val buffer = imageProxy.planes[0].buffer
                val data = ByteArray(buffer.remaining())
                buffer.get(data)

                val source = PlanarYUVLuminanceSource(
                    data,
                    imageProxy.width,
                    imageProxy.height,
                    0,
                    0,
                    imageProxy.width,
                    imageProxy.height,
                    false
                )
                val binaryBitmap = BinaryBitmap(HybridBinarizer(source))

                // Tenta decodificar o QR Code
                val result = reader.decode(binaryBitmap)
                onQrCodeScanned(result)

            } catch (e: Exception) {
                // Nenhum QR code encontrado ou erro na leitura
            } finally {
                imageProxy.close()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
