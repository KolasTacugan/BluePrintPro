package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.Spinner
import android.widget.ArrayAdapter

class MatchingActivity : Activity() {
    private lateinit var matchButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching)

        val matchButton = findViewById<Button>(R.id.match_button)
        val matchButton1 = findViewById<Button>(R.id.match_button_1)
        val styleSpinner = findViewById<Spinner>(R.id.styleSpinner)
        val budgetSpinner = findViewById<Spinner>(R.id.budgetSpinner)
        val typeSpinner = findViewById<Spinner>(R.id.typeSpinner)
        val locationSpinner = findViewById<Spinner>(R.id.locationSpinner)

// Style Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.style_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            styleSpinner.adapter = adapter
        }

// Budget Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.budget_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            budgetSpinner.adapter = adapter
        }

// Type Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.type_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            typeSpinner.adapter = adapter
        }

// Location Spinner
        ArrayAdapter.createFromResource(
            this,
            R.array.location_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            locationSpinner.adapter = adapter
        }

        matchButton.setOnClickListener {
            Toast.makeText(this, "Matched with Monique Hir!", Toast.LENGTH_SHORT).show()
        }

        matchButton1.setOnClickListener {
            Toast.makeText(this, "Matched with Mina Myuoi!", Toast.LENGTH_SHORT).show()
        }

    }
}
