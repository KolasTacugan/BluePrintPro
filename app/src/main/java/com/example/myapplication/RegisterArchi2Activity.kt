package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.view.children
import com.example.myapplication.model.Architect
import com.example.myapplication.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterArchi2Activity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_archi2)

        // Views
        val designStylesGroup = findViewById<GridLayout>(R.id.designStylesGroup)
        val workGroup = findViewById<GridLayout>(R.id.workGroup)
        val laborGroup = findViewById<RadioGroup>(R.id.laborGroup)
        val locationGroup = findViewById<RadioGroup>(R.id.locationGroup)
        val inputCity = findViewById<EditText>(R.id.inputCity)
        val checkTerms = findViewById<CheckBox>(R.id.checkTerms)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val loginLink = findViewById<TextView>(R.id.loginLink)

        // Extract data from previous activity
        val firstName = intent.getStringExtra("firstName") ?: ""
        val lastName = intent.getStringExtra("lastName") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        val password = intent.getStringExtra("password") ?: ""
        val phoneNumber = intent.getStringExtra("phoneNumber") ?: ""
        val experience = intent.getStringExtra("experience") ?: ""
        val prcLicense = intent.getStringExtra("prcLicense") ?: ""

        btnRegister.setOnClickListener {
            if (!checkTerms.isChecked) {
                Toast.makeText(this, "You must agree to the terms", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gather selected design styles
            val selectedDesignStyles = designStylesGroup.children
                .filterIsInstance<CheckBox>()
                .filter { it.isChecked }
                .map { it.text.toString() }
                .toList()

            if (selectedDesignStyles.isEmpty()) {
                Toast.makeText(this, "Please select at least one Design Style", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Gather selected specializations
            val selectedSpecializations = workGroup.children
                .filterIsInstance<CheckBox>()
                .filter { it.isChecked }
                .map { it.text.toString() }
                .toList()

            if (selectedSpecializations.isEmpty()) {
                Toast.makeText(this, "Please select at least one Specialization", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Get labor cost
            val laborCostId = laborGroup.checkedRadioButtonId
            if (laborCostId == -1) {
                Toast.makeText(this, "Please select your Labor Cost", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val laborCost = findViewById<RadioButton>(laborCostId).text.toString()

            // Get service location
            val locationId = locationGroup.checkedRadioButtonId
            if (locationId == -1) {
                Toast.makeText(this, "Please select your Service Location", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val serviceLocation = findViewById<RadioButton>(locationId).text.toString()

            // Get city
            val city = inputCity.text.toString().trim()
            if (city.isEmpty()) {
                Toast.makeText(this, "Please enter your City/Province", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create architect object
            val architect = Architect(
                username = email,
                password = password,
                firstname = firstName,
                lastname = lastName,
                phone = phoneNumber,
                designStyles = selectedDesignStyles,
                specializations = selectedSpecializations,
                laborCost = laborCost,
                serviceLocation = serviceLocation,
                city = city,
                experience = experience,
                prcLicense = prcLicense
            )

            Log.d("RegisterArchi2", "Sending Architect: $architect")

            // Send to backend
            ApiClient.api.registerArchitect(architect)
                .enqueue(object : Callback<Map<String, Boolean>> {
                    override fun onResponse(
                        call: Call<Map<String, Boolean>>,
                        response: Response<Map<String, Boolean>>
                    ) {
                        if (response.isSuccessful && response.body()?.get("success") == true) {
                            Toast.makeText(this@RegisterArchi2Activity, "Registration Successful", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@RegisterArchi2Activity, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@RegisterArchi2Activity, "Registration Failed", Toast.LENGTH_LONG).show()
                            Log.e("RegisterArchi2", "Failure response: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<Map<String, Boolean>>, t: Throwable) {
                        Toast.makeText(this@RegisterArchi2Activity, "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
                        Log.e("RegisterArchi2", "Network error", t)
                    }
                })
        }

        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
