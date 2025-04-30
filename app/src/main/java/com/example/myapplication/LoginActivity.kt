package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edittextUsername = findViewById<EditText>(R.id.edittext_username)
        val edittextPassword = findViewById<EditText>(R.id.edittext_password)
        val buttonLogin = findViewById<Button>(R.id.button_login)
        val buttonRegister = findViewById<Button>(R.id.button_register)

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        buttonLogin.setOnClickListener {
            val username = edittextUsername.text.toString()
            val password = edittextPassword.text.toString()


            val registeredUsername = sharedPreferences.getString("username", null)
            val registeredPassword = sharedPreferences.getString("password", null)

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please input credentials", Toast.LENGTH_LONG).show()
            } else if (username == registeredUsername && password == registeredPassword) {
                val intent = Intent(this, LandingActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show()
            }
        }
    }
}