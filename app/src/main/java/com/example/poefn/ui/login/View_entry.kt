package com.example.poefn.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.poefn.R
import kotlinx.coroutines.launch

class ViewEntryActivity : AppCompatActivity() {

    private lateinit var titleText: TextView
    private lateinit var contentText: TextView
    private lateinit var dateText: TextView
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button

    private var entryId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_view_entry)

        titleText = findViewById(R.id.titleText)
        contentText = findViewById(R.id.contentText)
        dateText = findViewById(R.id.dateText)
        editButton = findViewById(R.id.editButton)
        deleteButton = findViewById(R.id.deleteButton)

        entryId = intent.getIntExtra("ENTRY_ID", -1)

        if (entryId == -1) {
            Toast.makeText(
                this,
                "Journal entry not found",
                Toast.LENGTH_LONG
            ).show()

            finish()
            return
        }

        editButton.setOnClickListener {

            val intent = Intent(
                this,
                EditEntryActivity::class.java
            )

            intent.putExtra("ENTRY_ID", entryId)

            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            showDeleteConfirmation()
        }

        loadEntry()
    }

    override fun onResume() {
        super.onResume()

        if (entryId != -1) {
            loadEntry()
        }
    }

    private fun loadEntry() {

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.api.getEntry(entryId)

                if (response.isSuccessful) {

                    val entry = response.body()

                    if (entry != null) {

                        titleText.text = entry.title

                        contentText.text = entry.content

                        dateText.text =
                            "Created: ${entry.createdAt}"

                    } else {

                        Toast.makeText(
                            this@ViewEntryActivity,
                            "Entry not found",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }

                } else {

                    Toast.makeText(
                        this@ViewEntryActivity,
                        "Could not load journal entry",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@ViewEntryActivity,
                    "Server connection failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun showDeleteConfirmation() {

        AlertDialog.Builder(this)
            .setTitle("Delete Entry")
            .setMessage(
                "Are you sure you want to delete this journal entry?"
            )
            .setPositiveButton("Delete") { _, _ ->

                deleteEntry()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteEntry() {

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.api.deleteEntry(entryId)

                if (response.isSuccessful) {

                    Toast.makeText(
                        this@ViewEntryActivity,
                        "Entry deleted",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()

                } else {

                    Toast.makeText(
                        this@ViewEntryActivity,
                        "Could not delete entry",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@ViewEntryActivity,
                    "Server connection failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}