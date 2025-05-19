package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class EditProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_profile)

        val editFName = findViewById<EditText>(R.id.edittext_edit_fName)
        val editLName = findViewById<EditText>(R.id.edittext_edit_lName)
        val editEmail = findViewById<EditText>(R.id.edittext_edit_email)
        val editPhone = findViewById<EditText>(R.id.edittext_edit_phoneNum)

       // val buttonUpdate = findViewById<Button>(R.id.button_update_information)


        editFName.setText(UserData.firstName)
        editLName.setText(UserData.lastName)
        editEmail.setText(UserData.email)
        editPhone.setText(UserData.phone)


        /*buttonUpdate.setOnClickListener {

            UserData.firstName = editFName.text.toString()
            UserData.lastName = editLName.text.toString()
            UserData.email = editEmail.text.toString()
            UserData.phone = editPhone.text.toString()
            UserData.gender = editGender.text.toString()

            Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show()


            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }*/
    }
}