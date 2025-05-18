package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowInsets
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout

class SetupProfileActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup_profile)
        val roleClient = findViewById<LinearLayout>(R.id.role_client)
        val roleArchitect = findViewById<LinearLayout>(R.id.role_architect)

        roleClient.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        roleArchitect.setOnClickListener {
            val intent = Intent(this, RegisterArchiActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }



    }
}
