package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.model.FileUpload
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class AddBlueprintActivity : Activity() {

    private lateinit var blueprintNameEditText: EditText
    private lateinit var priceEditText: EditText
    private lateinit var checkModern: CheckBox
    private lateinit var checkContemporary: CheckBox
    private lateinit var checkTraditional: CheckBox
    private lateinit var checkMinimalist: CheckBox
    private lateinit var uploadButton: Button

    private lateinit var userId: String
    private lateinit var pdfUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blueprint)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userId = intent.getStringExtra("userID") ?: ""
        pdfUrl = intent.getStringExtra("pdfUrl") ?: ""

        Log.d("AddBlueprint", "Received userId: $userId")
        Log.d("AddBlueprint", "Received pdfUrl: $pdfUrl")

        blueprintNameEditText = findViewById(R.id.blueprintName)
        priceEditText = findViewById(R.id.price)
        checkModern = findViewById(R.id.checkModern)
        checkContemporary = findViewById(R.id.checkContemporary)
        checkTraditional = findViewById(R.id.checkTraditional)
        checkMinimalist = findViewById(R.id.checkMinimalist)
        uploadButton = findViewById(R.id.uploadButton)

        uploadButton.setOnClickListener {
            uploadBlueprintToBackend()
        }
    }

    private fun uploadBlueprintToBackend() {
        val name = blueprintNameEditText.text.toString().trim()
        val price = priceEditText.text.toString().trim()
        val styles = mutableListOf<String>()

        if (checkModern.isChecked) styles.add("Modern")
        if (checkContemporary.isChecked) styles.add("Contemporary")
        if (checkTraditional.isChecked) styles.add("Traditional")
        if (checkMinimalist.isChecked) styles.add("Minimalist")

        if (name.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val fileUpload = FileUpload(
            userID = userId,
            bpURL = pdfUrl,
            bpName = name,
            bpPrice = price,
            designStyles = styles
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val client = HttpClient(CIO) {
                    install(ContentNegotiation) {
                        json(Json { ignoreUnknownKeys = true })
                    }
                }

                val response: HttpResponse = client.post("http://192.168.254.105:8080/uploadFile") {
                    contentType(ContentType.Application.Json)
                    setBody(fileUpload)
                }

                val responseText = response.bodyAsText()

                runOnUiThread {
                    if (response.status == HttpStatusCode.OK) {
                        Toast.makeText(this@AddBlueprintActivity, "Upload successful!", Toast.LENGTH_SHORT).show()
                        Log.d("AddBlueprint", "Server response: $responseText")
                        startActivity(Intent(this@AddBlueprintActivity, UploadActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@AddBlueprintActivity, "Upload failed: $responseText", Toast.LENGTH_LONG).show()
                        Log.e("AddBlueprint", "Server error: $responseText")
                    }
                }

                client.close()

            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@AddBlueprintActivity, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e("AddBlueprint", "Exception: ${e.message}", e)
                }
            }
        }
    }
}
