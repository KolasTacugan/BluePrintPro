package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout

class LandingActivity : Activity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_landing2)

        val archiBtn = findViewById<LinearLayout>(R.id.findArchitect)
        val marketBtn = findViewById<LinearLayout>(R.id.browseMarket)
        val profileBtn = findViewById<ImageView>(R.id.profileBtn)
        val walletBtn = findViewById<ImageView>(R.id.walletBtn)

        archiBtn.setOnClickListener {
            val intent = Intent(this, MatchingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        marketBtn.setOnClickListener {
            val intent = Intent(this, MarketPlaceActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        profileBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }
}