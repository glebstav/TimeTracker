package com.example.timetracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.timetracker.db.DBHelper

class Authentication:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth)

        val registrBtn: TextView = findViewById(R.id.registrationBtn)
        registrBtn.setOnClickListener {
            startActivity(Intent(this, Registration::class.java))
        }
        val loginUser: EditText = findViewById(R.id.loginAuthText)

        val authBtn: Button = findViewById(R.id.regButton)
        authBtn.setOnClickListener {
            val login: String = loginUser.text.toString().trim()
            if (login == "") {
                Toast.makeText(this, "Пустой '$login' логин!", Toast.LENGTH_SHORT).show()
            } else {
                val db = DBHelper(this, null)
                if (!db.isUserExist(login)){
                    Toast.makeText(this, "пользователь '$login' не существует!", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userLogin", login)
                    startActivity(intent)
                }
            }

        }
    }
}