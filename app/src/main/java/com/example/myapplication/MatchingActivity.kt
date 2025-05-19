package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.ArchitectAdapter
import com.example.myapplication.model.Architect
import com.example.myapplication.network.ApiClient
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MatchingActivity : Activity(), CoroutineScope {

    private lateinit var architectAdapter: ArchitectAdapter
    private lateinit var recyclerView: RecyclerView

    private lateinit var styleSpinner: Spinner
    private lateinit var budgetSpinner: Spinner
    private lateinit var typeSpinner: Spinner
    private lateinit var locationSpinner: Spinner

    private var allArchitects: List<Architect> = emptyList()

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matching)

        // Initialize views
        styleSpinner = findViewById(R.id.styleSpinner)
        budgetSpinner = findViewById(R.id.budgetSpinner)
        typeSpinner = findViewById(R.id.typeSpinner)
        locationSpinner = findViewById(R.id.locationSpinner)
        recyclerView = findViewById(R.id.architectRecyclerView)
        val backBtn = findViewById<ImageView>(R.id.backButton)

        // Setup spinners with data from strings.xml
        setupSpinner(styleSpinner, R.array.style_options)
        setupSpinner(budgetSpinner, R.array.budget_options)
        setupSpinner(typeSpinner, R.array.type_options)
        setupSpinner(locationSpinner, R.array.location_options)

        // Setup RecyclerView
        architectAdapter = ArchitectAdapter(this, emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = architectAdapter

        // Setup spinner listeners
        val spinnerListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                filterArchitects()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        styleSpinner.onItemSelectedListener = spinnerListener
        budgetSpinner.onItemSelectedListener = spinnerListener
        typeSpinner.onItemSelectedListener = spinnerListener
        locationSpinner.onItemSelectedListener = spinnerListener

        // Back navigation
        backBtn.setOnClickListener {
            val intent = Intent(this, LandingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        // Load architects on launch
        loadAllArchitects()
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

    private fun loadAllArchitects() {
        launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    ApiClient.api.getArchitects()
                }
                allArchitects = response
                architectAdapter.updateList(allArchitects)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MatchingActivity, "Failed to load architects", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun filterArchitects() {
        val selectedStyle = styleSpinner.selectedItem.toString()
        val selectedBudget = budgetSpinner.selectedItem.toString()
        val selectedType = typeSpinner.selectedItem.toString()
        val selectedLocation = locationSpinner.selectedItem.toString()

        val isAllAny = selectedStyle == "Any" &&
                selectedBudget == "Any" &&
                selectedType == "Any" &&
                selectedLocation == "Any"

        launch {
            try {
                val filtered = withContext(Dispatchers.IO) {
                    if (isAllAny) {
                        // No filters: fetch all architects
                        ApiClient.api.getArchitects()
                    } else {
                        // Apply only non-"Any" filters
                        ApiClient.api.getArchitects(
                            designStyles = selectedStyle.takeIf { it != "Any" },
                            laborCost = selectedBudget.takeIf { it != "Any" },
                            specializations = selectedType.takeIf { it != "Any" },
                            serviceLocation = selectedLocation.takeIf { it != "Any" }
                        )
                    }
                }

                architectAdapter.updateList(filtered)

                if (filtered.isEmpty()) {
                    Toast.makeText(this@MatchingActivity, "No matching architects found", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this@MatchingActivity, "Error filtering architects", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
