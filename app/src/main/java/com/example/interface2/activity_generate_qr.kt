package com.example.interface2

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.BarcodeEncoder

class activity_generate_qr : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_qr)

        val qrCodeDataEditText = findViewById<EditText>(R.id.qrCodeDataEditText)
        val generateButton = findViewById<Button>(R.id.generateButton)
        val qrCodeImageView = findViewById<ImageView>(R.id.qrCodeImageView)

        generateButton.setOnClickListener {
            val textToEncode = qrCodeDataEditText.text.toString().trim()

            if (textToEncode.isEmpty()) {
                Toast.makeText(this, "Por favor, insira um texto para gerar o QR Code.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val barcodeEncoder = BarcodeEncoder()
                // Gera o QR Code como um Bitmap (uma imagem)
                val bitmap: Bitmap = barcodeEncoder.encodeBitmap(textToEncode, com.google.zxing.BarcodeFormat.QR_CODE, 400, 400)

                // Exibe o Bitmap no ImageView
                qrCodeImageView.setImageBitmap(bitmap)

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Erro ao gerar o QR Code.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}