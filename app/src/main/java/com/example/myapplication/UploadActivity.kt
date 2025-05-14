package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.*
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class UploadActivity : Activity() {

    private lateinit var uploadBox: FrameLayout
    private lateinit var fileNameTextView: TextView
    private val PICK_PDF_REQUEST = 1
    private var fileUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        val profileBtn = findViewById<ImageView>(R.id.profileBtn)
        val walletBtn = findViewById<ImageView>(R.id.walletBtn)
        val homeBtn = findViewById<ImageView>(R.id.homeBtn)

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

        // Link the views
        uploadBox = findViewById(R.id.uploadBox)
        fileNameTextView = findViewById(R.id.fileName)

        // Set onClickListener for uploadBox to pick PDF
        uploadBox.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST)
        }
    }

    // Handle the result of the file picker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                fileUri = uri
                Log.d("UploadActivity", "Selected URI: $uri")

                // Get the file name from URI and update the TextView
                val fileName = getFileNameFromUri(uri)
                fileNameTextView.text = fileName

                // Upload the PDF to Firebase
                uploadPdfToFirebase(uri)
            } else {
                Toast.makeText(this, "No file selected.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "File selection failed.", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to extract the file name from the URI
    private fun getFileNameFromUri(uri: Uri): String {
        return try {
            var name: String? = null
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (it.moveToFirst() && nameIndex >= 0) {
                    name = it.getString(nameIndex)
                }
            }
            name ?: uri.lastPathSegment?.substringAfterLast("/") ?: "Selected_File.pdf"
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("UploadActivity", "Error getting file name from URI", e)
            "Selected_File.pdf"
        }
    }

    // Function to upload the PDF to Firebase Storage
    private fun uploadPdfToFirebase(pdfUri: Uri) {
        try {
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val pdfRef = storageRef.child("pdfs/${UUID.randomUUID()}.pdf")

            pdfRef.putFile(pdfUri)
                .addOnSuccessListener {
                    Log.d("UploadActivity", "PDF uploaded successfully")
                    Toast.makeText(this, "PDF uploaded successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e("UploadActivity", "Upload failed: ${e.message}", e)
                    Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Exception) {
            Log.e("UploadActivity", "Error uploading file", e)
            Toast.makeText(this, "Upload error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
