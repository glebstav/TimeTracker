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

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
        val authBtn: TextView = findViewById(R.id.AuthBtn)
        authBtn.setOnClickListener {
            startActivity(Intent(this, Authentication::class.java))
        }
        val regBtn: Button = findViewById(R.id.regButton)
        val loginUser: EditText = findViewById<EditText>(R.id.loginText)
        regBtn.setOnClickListener {
            val login = loginUser.text.toString().trim()
            if (login == "") {
                Toast.makeText(this, "Пустой логин!", Toast.LENGTH_SHORT).show()
            } else {
                val db = DBHelper(this, null)
                if (db.isUserExist(login)){
                    Toast.makeText(this, "пользователь '$login' уже существует!", Toast.LENGTH_SHORT).show()
                } else {
                    db.addUser(login)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("userLogin", login)
                    startActivity(intent)
                }
            }

        }
    }
}