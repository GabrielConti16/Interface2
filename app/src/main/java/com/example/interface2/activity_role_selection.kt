package com.example.interface2 // Verifique se o nome do seu pacote está correto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class activity_role_selection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_selection)

        // Referência aos botões do layout
        val teacherButton = findViewById<Button>(R.id.teacherButton)
        val studentButton = findViewById<Button>(R.id.studentButton)

        // Define a ação para o clique no botão "Sou Professor"
        teacherButton.setOnClickListener {
            // Cria um Intent para abrir a tela de cadastro de professor
            val intent = Intent(this, activity_teacher_registration::class.java)
            startActivity(intent)
        }

        // Define a ação para o clique no botão "Sou Aluno"
        studentButton.setOnClickListener {
            // Cria um Intent para abrir a tela de cadastro de aluno
            val intent = Intent(this, activity_student_registration::class.java)
            startActivity(intent)
        }
    }
}
