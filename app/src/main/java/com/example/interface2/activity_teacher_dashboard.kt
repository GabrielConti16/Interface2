package com.example.interface2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class activity_teacher_dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_dashboard)

        // Referência ao botão "Criar QR Code" do seu layout
        val createQrButton = findViewById<Button>(R.id.createQrButton)

        // Define a ação de clique para o botão
        createQrButton.setOnClickListener {
            // Cria um Intent para iniciar a activity_generate_qr
            val intent = Intent(this, activity_generate_qr::class.java)
            startActivity(intent)
        }

        // Futuramente, a lógica para os outros botões ("Relatório de Faltas", etc.)
        // pode ser adicionada aqui.
    }
}
