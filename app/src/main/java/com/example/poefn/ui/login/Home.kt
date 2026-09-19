package com.example.poefn.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poefn.R
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: JournalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        adapter = JournalAdapter(emptyList()) { entry ->

            val intent = Intent(
                this,
                ViewEntryActivity::class.java
            )

            intent.putExtra("ENTRY_ID", entry.id)

            startActivity(intent)
        }

        recyclerView.adapter = adapter

        findViewById<Button>(R.id.createEntryButton)
            .setOnClickListener {

                startActivity(
                    Intent(
                        this,
                        CreateEntryActivity::class.java
                    )
                )
            }

        findViewById<Button>(R.id.searchButton)
            .setOnClickListener {

                startActivity(
                    Intent(
                        this,
                        SearchActivity::class.java
                    )
                )
            }

        findViewById<Button>(R.id.settingsButton)
            .setOnClickListener {

                startActivity(
                    Intent(
                        this,
                        SettingsActivity::class.java
                    )
                )
            }

        loadEntries()
    }

    override fun onResume() {
        super.onResume()

        loadEntries()
    }

    private fun loadEntries() {

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.api.getEntries()

                if (response.isSuccessful) {

                    adapter.updateEntries(
                        response.body()
                            ?: emptyList()
                    )

                } else {

                    Toast.makeText(
                        this@HomeActivity,
                        "Could not load journal entries",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@HomeActivity,
                    "Server connection failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}