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
        val walletOptions = listOf("GooglePay", "Gcash", "PayMaya")
        val adapter = ArrayAdapter(
            this,
            R.layout.spinner_item_wallet, // custom layout
            walletOptions
        )
        adapter.setDropDownViewResource(R.layout.spinner_item_wallet) // same layout for dropdown

        val googlePayButton = findViewById<Button>(R.id.btnGooglePay)
        googlePayButton.setOnClickListener {
            // TODO: Add logic for initiating Google Pay
            Toast.makeText(this, "Google Pay Clicked", Toast.LENGTH_SHORT).show()
        }

    }
}
