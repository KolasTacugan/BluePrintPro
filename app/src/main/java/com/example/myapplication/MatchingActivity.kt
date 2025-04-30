package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class MatchingActivity : Activity() {
    private lateinit var matchButton: Button
    private lateinit var leftArrow: ImageView
    private lateinit var rightArrow: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching)

        matchButton = findViewById(R.id.match_button)

        matchButton.setOnClickListener {
            Toast.makeText(this, "Match clicked!", Toast.LENGTH_SHORT).show()
        }

        leftArrow.setOnClickListener {
            Toast.makeText(this, "Previous profile", Toast.LENGTH_SHORT).show()
        }

        rightArrow.setOnClickListener {
            Toast.makeText(this, "Next profile", Toast.LENGTH_SHORT).show()
        }
    }
}
