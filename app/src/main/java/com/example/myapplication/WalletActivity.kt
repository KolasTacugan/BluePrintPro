package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WalletActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_wallet)

        val googlePayButton = findViewById<Button>(R.id.btnGooglePay)
        googlePayButton.setOnClickListener {
            // TODO: Add logic for initiating Google Pay
            Toast.makeText(this, "Google Pay Clicked", Toast.LENGTH_SHORT).show()
        }

    }
}
