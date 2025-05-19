package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterArchiActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_archi)

        val firstName = findViewById<EditText>(R.id.edit_first_name)
        val lastName = findViewById<EditText>(R.id.edit_last_name)
        val email = findViewById<EditText>(R.id.edit_email)
        val password = findViewById<EditText>(R.id.edit_password)
        val confirmPassword = findViewById<EditText>(R.id.edit_confirm_password)
        val phone = findViewById<EditText>(R.id.edit_phone)
        val experience = findViewById<EditText>(R.id.edit_experience)
        val prcLicense = findViewById<EditText>(R.id.edit_prc_license)
        val nextButton = findViewById<Button>(R.id.button_next)

        nextButton.setOnClickListener {
            val fname = firstName.text.toString().trim()
            val lname = lastName.text.toString().trim()
            val mail = email.text.toString().trim()
            val pass = password.text.toString().trim()
            val confirmPass = confirmPassword.text.toString().trim()
            val phoneNum = phone.text.toString().trim()
            val yearsExp = experience.text.toString().trim()
            val prc = prcLicense.text.toString().trim()

            if (fname.isEmpty() || lname.isEmpty() || mail.isEmpty() || pass.isEmpty()
                || confirmPass.isEmpty() || phoneNum.isEmpty() || yearsExp.isEmpty()
            ) {
                Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pass != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, RegisterArchi2Activity::class.java).apply {
                putExtra("firstName", fname)
                putExtra("lastName", lname)
                putExtra("email", mail)
                putExtra("password", pass)
                putExtra("phoneNumber", phoneNum)
                putExtra("experience", yearsExp)
                putExtra("prcLicense", prc)
            }

            startActivity(intent)
        }
    }
}
