package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HomeActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

       /* // Get reference to UI elements
        val buttonBack = findViewById<Button>(R.id.button_back)
        val textViewFName = findViewById<TextView>(R.id.textview_fname)
        val textViewLName = findViewById<TextView>(R.id.textview_lname)
        val textViewSex = findViewById<TextView>(R.id.textview_sex)
        val textViewPhone = findViewById<TextView>(R.id.textview_number)
        val textViewEmail = findViewById<TextView>(R.id.textview_email)

        // Load user data (assuming stored in UserData object)
        textViewFName.text = UserData.firstName
        textViewLName.text = UserData.lastName
        textViewSex.text = UserData.gender
        textViewPhone.text = UserData.phone
        textViewEmail.text = UserData.email

        // Handle back button click
        buttonBack.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish() // Close HomeActivity to prevent stacking
        }
*/
    }
}