package com.example.interface2 // Verifique se o nome do seu pacote está correto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// --- MUDANÇA 1: Remoção da importação do Firebase ---
// import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    // --- MUDANÇA 2: Remoção da variável de autenticação do Firebase ---
    // private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // --- MUDANÇA 3: A inicialização do Firebase foi removida ---
        // auth = FirebaseAuth.getInstance()

        // Referências aos componentes da tela (sem alteração)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerButton = findViewById<TextView>(R.id.registerButton)


        // --- MUDANÇA 4: Lógica do botão de Login simplificada ---
        loginButton.setOnClickListener {
            // Agora, o botão "Entrar" leva diretamente para o scanner de QR.
            val intent = Intent(this, activity_qr_scanner::class.java)
            startActivity(intent)
        }


        // --- Lógica do botão de Cadastro (sem alteração) ---
        registerButton.setOnClickListener {
            // Leva o usuário para a tela de seleção de perfil
            val intent = Intent(this, activity_role_selection::class.java)
            startActivity(intent)
        }

    }
}
