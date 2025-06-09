package com.example.interface2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class activity_test_menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_menu)

        findViewById<Button>(R.id.goToMainButton).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<Button>(R.id.goToRoleSelectionButton).setOnClickListener {
            startActivity(Intent(this, activity_role_selection::class.java))
        }
        findViewById<Button>(R.id.goToTeacherRegButton).setOnClickListener {
            // Supondo que o nome da activity seja activity_teacher_registration
            startActivity(Intent(this, activity_teacher_registration::class.java))
        }
        findViewById<Button>(R.id.goToStudentRegButton).setOnClickListener {
            // Supondo que o nome da activity seja activity_student_registration
            startActivity(Intent(this, activity_student_registration::class.java))
        }
        findViewById<Button>(R.id.goToTeacherDashboardButton).setOnClickListener {
            // Supondo que o nome da activity seja activity_teacher_dashboard
            startActivity(Intent(this, activity_teacher_dashboard::class.java))
        }
        findViewById<Button>(R.id.goToQrScannerButton).setOnClickListener {
            startActivity(Intent(this, activity_qr_scanner::class.java))
        }
        findViewById<Button>(R.id.goToAdminDashboardButton).setOnClickListener {
            startActivity(Intent(this, activity_admin_dashboard::class.java))
            }
    }
}
