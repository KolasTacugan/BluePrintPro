package com.example.myapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.ArrayAdapter

class MatchingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching)

        val matchButton = findViewById<Button>(R.id.match_button)
        val matchButton1 = findViewById<Button>(R.id.match_button_1)
        val styleSpinner = findViewById<Spinner>(R.id.styleSpinner)
        val budgetSpinner = findViewById<Spinner>(R.id.budgetSpinner)
        val typeSpinner = findViewById<Spinner>(R.id.typeSpinner)
        val locationSpinner = findViewById<Spinner>(R.id.locationSpinner)
        val backBtn = findViewById<ImageView>(R.id.backButton)

        // Set up spinners
        setupSpinner(styleSpinner, R.array.style_options)
        setupSpinner(budgetSpinner, R.array.budget_options)
        setupSpinner(typeSpinner, R.array.type_options)
        setupSpinner(locationSpinner, R.array.location_options)

        // Match with Monique
        matchButton.setOnClickListener {
            showMatchDialog("Monique Hir")
        }

        // Match with Mina
        matchButton1.setOnClickListener {
            showMatchDialog("Mina Myuoi")
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    private fun setupSpinner(spinner: Spinner, arrayResId: Int) {
        ArrayAdapter.createFromResource(
            this,
            arrayResId,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
    }

    private fun showMatchDialog(name: String) {
        AlertDialog.Builder(this)
            .setTitle("Match Found!")
            .setMessage("You matched with $name!")
            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}
