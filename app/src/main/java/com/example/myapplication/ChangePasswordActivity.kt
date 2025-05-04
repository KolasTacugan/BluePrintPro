package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChangePasswordActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_change_password)
        val newPasswordEditText = findViewById<EditText>(R.id.edit_new_password)
        val toggleNewPassword = findViewById<TextView>(R.id.toggle_new_password)

        var isPasswordVisible = false

        toggleNewPassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                newPasswordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                toggleNewPassword.text = "🙈" // open eye
            } else {
                newPasswordEditText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                toggleNewPassword.text = "👁️" // closed eye
            }
            newPasswordEditText.setSelection(newPasswordEditText.text.length)
        }

    }
}