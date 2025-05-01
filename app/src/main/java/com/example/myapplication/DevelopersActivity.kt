package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView

class DevelopersActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_developers)


        val buttonImg = findViewById<ImageView>(R.id.button_back_dev)

        buttonImg.setOnClickListener{
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}