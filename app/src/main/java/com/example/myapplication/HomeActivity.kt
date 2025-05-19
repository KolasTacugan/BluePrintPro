package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class HomeActivity : Activity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var logoutButton: Button
    private lateinit var userNameText: TextView
    private lateinit var userEmailText: TextView
    private lateinit var userPhoneText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val profileBtn = findViewById<ImageView>(R.id.profileBtn)
        val walletBtn = findViewById<ImageView>(R.id.walletBtn)
        val homeBtn = findViewById<ImageView>(R.id.homeBtn)
        val settingsBtn = findViewById<ImageView>(R.id.settingsBtn)
        val userName = findViewById<TextView>(R.id.userName)

        userName.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        settingsBtn.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        homeBtn.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
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

        walletBtn.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        // Init views
        logoutButton = findViewById(R.id.logoutButton)
        userNameText = findViewById(R.id.userName)
        userEmailText = findViewById(R.id.userEmail)
        userPhoneText = findViewById(R.id.userPhone)

        // Set up GoogleSignIn client
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Retrieve shared preferences
        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val manualLogin = sharedPreferences.getBoolean("manualLogin", false)

        // Display user data
        if (manualLogin) {
            val username = sharedPreferences.getString("username", "Manual User")
            val email = sharedPreferences.getString("email", "N/A")
            val phone = sharedPreferences.getString("phone", "N/A")

            userNameText.text = username
            userEmailText.text = email
            userPhoneText.text = phone
        } else {
            val acct = GoogleSignIn.getLastSignedInAccount(this)
            if (acct != null) {
                userNameText.text = acct.displayName ?: "Google User"
                userEmailText.text = acct.email ?: "No Email"
                userPhoneText.text = "Signed in via Google"
            } else {
                userNameText.text = "Guest"
                userEmailText.text = "Not signed in"
                userPhoneText.text = ""
            }
        }

        // Logout logic
        logoutButton.setOnClickListener {
            sharedPreferences.edit().clear().apply()  // Clear local session

            if (!manualLogin) {
                // Sign out from Google if it was a Google login
                googleSignInClient.signOut().addOnCompleteListener {
                    goToLogin()
                }
            } else {
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
