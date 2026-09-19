package com.example.poefn.ui.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.poefn.R
import com.example.poefn.model.JournalEntry
import kotlinx.coroutines.launch

class CreateEntryActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var contentInput: EditText
    private lateinit var saveEntryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_entry)

        titleInput = findViewById(R.id.titleInput)
        contentInput = findViewById(R.id.contentInput)
        saveEntryButton = findViewById(R.id.saveEntryButton)

        saveEntryButton.setOnClickListener {
            createEntry()
        }
    }

    private fun createEntry() {

        val title = titleInput.text.toString().trim()
        val content = contentInput.text.toString().trim()

        if (title.isEmpty()) {
            titleInput.error = "Enter a title"
            titleInput.requestFocus()
            return
        }

        if (content.isEmpty()) {
            contentInput.error = "Enter your journal text"
            contentInput.requestFocus()
            return
        }

        /*
         * The API/database should create the official
         * createdAt timestamp.
         *
         * These values are temporary placeholders until
         * the backend assigns the logged-in user and timestamp.
         */

        val entry = JournalEntry(
            id = 0,
            userId = 0,
            title = title,
            content = content,
            createdAt = "",
            updatedAt = null
        )

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.api.createEntry(entry)

                if (response.isSuccessful) {

                    Toast.makeText(
                        this@CreateEntryActivity,
                        "Journal entry saved",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()

                } else {

                    Toast.makeText(
                        this@CreateEntryActivity,
                        "Unable to save journal entry",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@CreateEntryActivity,
                    "Server connection failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}