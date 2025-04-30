package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button


class SettingsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        val button_edit_profile: Button = findViewById(R.id.button_edit_profile)
        val button_pagehome: Button = findViewById(R.id.button_pagehome)
        val button_logout: Button = findViewById(R.id.button_logout)

        button_edit_profile.setOnClickListener{
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        button_pagehome.setOnClickListener{
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
        }
        button_logout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}