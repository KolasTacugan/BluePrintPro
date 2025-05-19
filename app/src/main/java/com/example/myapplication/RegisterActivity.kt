package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.model.User
import com.example.myapplication.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignInAccount



class RegisterActivity : Activity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_GOOGLE_SIGN_UP = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val edittextFirstName= findViewById<EditText>(R.id.edit_first_name)
        val edittextLastName= findViewById<EditText>(R.id.edit_last_name)
        val editPhone = findViewById<EditText>(R.id.edit_phone)
        val edittextUsername = findViewById<EditText>(R.id.edit_email)
        val edittextPassword = findViewById<EditText>(R.id.edit_password)
        val edittextConfirmPassword = findViewById<EditText>(R.id.edit_confirm_password)
        val buttonRegister = findViewById<Button>(R.id.button_register)
        val buttongotologin = findViewById<TextView>(R.id.text_gotologin)

        buttonRegister.setOnClickListener {
            val firstName = edittextFirstName.text.toString().trim()
            val lastName = edittextLastName.text.toString().trim()
            val phone = editPhone.text.toString().trim()
            val username = edittextUsername.text.toString().trim()
            val password = edittextPassword.text.toString().trim()
            val confirmPassword = edittextConfirmPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_LONG).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
            } else {
                val user = User(
                    username = username,
                    password = password,
                    firstName = firstName,
                    lastName = lastName,
                    phone = phone
                )

                ApiClient.api.registerUser(user).enqueue(object : Callback<Map<String, Boolean>> {
                    override fun onResponse(
                        call: Call<Map<String, Boolean>>,
                        response: Response<Map<String, Boolean>>
                    ) {
                        if (response.isSuccessful && response.body()?.get("success") == true) {
                            Toast.makeText(this@RegisterActivity, "Registration Successful", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@RegisterActivity, "Registration Failed", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Map<String, Boolean>>, t: Throwable) {
                        Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                        Log.e("Registration", "Network error: ", t)
                    }
                })
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val googleSignupButton = findViewById<ImageButton>(R.id.google_signup_button)


        googleSignupButton.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_GOOGLE_SIGN_UP)
        }


        buttongotologin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_GOOGLE_SIGN_UP) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignUp(task)
        }
    }

    private fun handleGoogleSignUp(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)

            val email = account?.email ?: ""
            val firstName = account?.givenName ?: ""
            val lastName = account?.familyName ?: ""

            // Send this data to your backend to register the user
            val user = User(
                username = email,
                password = "google_oauth", // placeholder or generated
                firstName = firstName,
                lastName = lastName,
                phone = "" // let user add later
            )

            ApiClient.api.registerUser(user).enqueue(object : Callback<Map<String, Boolean>> {
                override fun onResponse(call: Call<Map<String, Boolean>>, response: Response<Map<String, Boolean>>) {
                    if (response.isSuccessful && response.body()?.get("success") == true) {
                        Toast.makeText(this@RegisterActivity, "Google Sign Up Successful", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Google Sign Up Failed", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Boolean>>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                    Log.e("GoogleSignUp", "Failure", t)
                }
            })

        } catch (e: ApiException) {
            Log.w("GoogleSignUp", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "Google sign up failed", Toast.LENGTH_SHORT).show()
        }
    }

}
