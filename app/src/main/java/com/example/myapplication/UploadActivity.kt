package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.*
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import java.io.File
import java.io.FileOutputStream
import java.util.*

class UploadActivity : Activity() {

    private lateinit var uploadBox: FrameLayout
    private lateinit var fileNameTextView: TextView
    private val PICK_PDF_REQUEST = 1
    private var fileUri: Uri? = null

    private val cloudinary by lazy {
        val config = hashMapOf(
            "cloud_name" to "dmlsl1ff3",
            "api_key" to "611912686692629",
            "api_secret" to "18ZLpaFKXrQpujuUA19dMV4u_uc"
        )
        Cloudinary(config)
    }

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

        uploadBox = findViewById(R.id.uploadBox)
        fileNameTextView = findViewById(R.id.fileName)

        uploadBox.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK) {
            val uri = data?.data
            if (uri != null) {
                fileUri = uri
                Log.d("UploadActivity", "Selected URI: $uri")

                val fileName = getFileNameFromUri(uri)
                fileNameTextView.text = fileName

                uploadPdfToCloudinary(uri)
            } else {
                Toast.makeText(this, "No file selected.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "File selection failed.", Toast.LENGTH_SHORT).show()
        }
    }

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

    private fun uploadPdfToCloudinary(pdfUri: Uri) {
        Thread {
            try {
                // Convert Uri to File
                val inputStream = contentResolver.openInputStream(pdfUri)
                val tempFile = File.createTempFile("upload_", ".pdf", cacheDir)
                val outputStream = FileOutputStream(tempFile)

                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()

                // Upload to Cloudinary as "raw"
                val uploadResult = cloudinary.uploader().upload(tempFile, ObjectUtils.asMap(
                    "resource_type", "raw"
                ))

                val pdfUrl = uploadResult["url"] as String
                Log.d("UploadActivity", "PDF uploaded successfully. URL: $pdfUrl")

                runOnUiThread {
                    Toast.makeText(this, "Upload successful!\nURL: $pdfUrl", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                Log.e("UploadActivity", "Upload failed: ${e.message}", e)
                runOnUiThread {
                    Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }
}
