package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.example.myapplication.model.LoginArchi
import com.example.myapplication.model.LoginResponse
import com.example.myapplication.model.LoginUser
import com.example.myapplication.model.User
import com.example.myapplication.network.ApiClient
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : Activity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)

        val edittextUsername = findViewById<EditText>(R.id.edittext_username)
        val edittextPassword = findViewById<EditText>(R.id.edittext_password)
        val buttonLogin = findViewById<Button>(R.id.button_login)
        val textRegister = findViewById<TextView>(R.id.text_register)
        val googleBtn = findViewById<ImageButton>(R.id.google_btn)

        // Google Sign-In setup
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Check if already signed in with Google
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            navigateToLanding()
        }

        textRegister.setOnClickListener {
            startActivity(Intent(this, SetupProfileActivity::class.java))
        }

        buttonLogin.setOnClickListener {
            val username = edittextUsername.text.toString().trim()
            val password = edittextPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please input credentials", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val user = LoginUser(username = username, password = password)
            val archiUser = LoginArchi(username = username, password = password)

            // Try Client login first
            ApiClient.api.loginUser(user).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        val userId = response.body()?.id
                        if (!userId.isNullOrBlank()) {
                            prefs.edit().putString("user_id", userId).apply()
                            Log.d("LoginActivity", "Client userId saved: $userId")
                        } else {
                            Log.e("LoginActivity", "Client login response missing userId")
                        }
                        prefs.edit().putString("user_id", userId).apply()
                        Toast.makeText(this@LoginActivity, "Client Login Successful.", Toast.LENGTH_LONG).show()
                        navigateToLanding()
                    } else {
                        // If client login fails, try architect login
                        ApiClient.api.loginArchi(archiUser).enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                                if (response.isSuccessful && response.body()?.success == true) {
                                    val userId = response.body()?.id
                                    if (!userId.isNullOrBlank()) {
                                        prefs.edit().putString("user_id", userId).apply()
                                        Log.d("LoginActivity", "Architect userId saved: $userId")
                                    } else {
                                        Log.e("LoginActivity", "Architect login response missing userId")
                                    }
                                    prefs.edit().putString("user_id", userId).apply()
                                    Toast.makeText(this@LoginActivity, "Architect Login Successful.", Toast.LENGTH_LONG).show()
                                    navigateToLandingArchi()
                                } else {
                                    Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                Toast.makeText(this@LoginActivity, "Architect login error: ${t.message}", Toast.LENGTH_LONG).show()
                            }
                        })
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Client login error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

        googleBtn.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                task.getResult(ApiException::class.java)
                navigateToLanding()
            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun navigateToLanding() {
        val intent = Intent(this, PaymentActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToLandingArchi() {
        val intent = Intent(this, UploadActivity::class.java)
        startActivity(intent)
        finish()
    }
}
