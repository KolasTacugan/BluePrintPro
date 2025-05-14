package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.app.Activity

class DevelopersActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developers)

        val buttonImg = findViewById<ImageView>(R.id.button_back_dev)
        buttonImg.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish()
        }

        // GitHub button handlers
        val btnNicole = findViewById<Button>(R.id.btn_github_nicole)
        val btnSamantha = findViewById<Button>(R.id.btn_github_samantha)
        val btnGene = findViewById<Button>(R.id.btn_github_gene)

        btnNicole.setOnClickListener {
            openGithub("https://github.com/KolasTacugan")
        }

        btnSamantha.setOnClickListener {
            openGithub("https://github.com/ITsRubica")
        }

        btnGene.setOnClickListener {
            openGithub("https://github.com/JinniHarold")
        }
    }

    private fun openGithub(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
