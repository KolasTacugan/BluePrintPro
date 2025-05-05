package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LandingActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var userNameText: TextView
    private lateinit var userRoleText: TextView
    private lateinit var signOutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing2)

        // Bind views
        userNameText = findViewById(R.id.userName)
        userRoleText = findViewById(R.id.userPhone) // Renamed in XML but storing role
        signOutBtn = findViewById(R.id.signoutBtn)

        // Google Sign-In client
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Get last signed-in account
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            val displayName = account.displayName
            val email = account.email

            userNameText.text = displayName ?: "Unknown User"
            userRoleText.text = email ?: "No Email"
        }

        // Sign-out button behavior
        signOutBtn.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        googleSignInClient.signOut().addOnCompleteListener {
            val intent = Intent(this@LandingActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
