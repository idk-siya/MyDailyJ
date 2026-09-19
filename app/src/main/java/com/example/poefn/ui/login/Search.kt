package com.example.poefn.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poefn.R
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    private lateinit var searchInput: EditText
    private lateinit var searchButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: JournalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        searchInput = findViewById(R.id.searchInput)
        searchButton = findViewById(R.id.searchButton)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        adapter = JournalAdapter(emptyList()) { entry ->

            val intent = Intent(
                this,
                ViewEntryActivity::class.java
            )

            intent.putExtra(
                "ENTRY_ID",
                entry.id
            )

            startActivity(intent)
        }

        recyclerView.adapter = adapter

        searchButton.setOnClickListener {
            searchEntries()
        }
    }

    private fun searchEntries() {

        val query =
            searchInput.text.toString().trim()

        if (query.isEmpty()) {

            searchInput.error =
                "Enter a word or phrase"

            searchInput.requestFocus()

            return
        }

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.api.searchEntries(query)

                if (response.isSuccessful) {

                    val entries =
                        response.body() ?: emptyList()

                    adapter.updateEntries(entries)

                    if (entries.isEmpty()) {

                        Toast.makeText(
                            this@SearchActivity,
                            "No matching entries found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {

                    Toast.makeText(
                        this@SearchActivity,
                        "Search failed",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@SearchActivity,
                    "Unable to connect to MyDailyJ server",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}