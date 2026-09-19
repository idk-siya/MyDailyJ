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

class EditEntryActivity : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var contentInput: EditText
    private lateinit var saveButton: Button

    private var entryId: Int = -1
    private var originalEntry: JournalEntry? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_entry)

        titleInput = findViewById(R.id.titleInput)
        contentInput = findViewById(R.id.contentInput)
        saveButton = findViewById(R.id.saveButton)

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

        saveButton.setOnClickListener {
            updateEntry()
        }

        loadEntry()
    }

    private fun loadEntry() {

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.api.getEntry(entryId)

                if (response.isSuccessful) {

                    originalEntry = response.body()

                    originalEntry?.let { entry ->

                        titleInput.setText(entry.title)
                        contentInput.setText(entry.content)

                    } ?: run {

                        Toast.makeText(
                            this@EditEntryActivity,
                            "Entry not found",
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }

                } else {

                    Toast.makeText(
                        this@EditEntryActivity,
                        "Unable to load entry",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@EditEntryActivity,
                    "Server connection failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun updateEntry() {

        val title =
            titleInput.text.toString().trim()

        val content =
            contentInput.text.toString().trim()

        if (title.isEmpty()) {

            titleInput.error = "Enter a title"
            titleInput.requestFocus()

            return
        }

        if (content.isEmpty()) {

            contentInput.error =
                "Enter your journal text"

            contentInput.requestFocus()

            return
        }

        val oldEntry = originalEntry

        if (oldEntry == null) {

            Toast.makeText(
                this,
                "Entry information is not available",
                Toast.LENGTH_LONG
            ).show()

            return
        }

        val updatedEntry = oldEntry.copy(
            title = title,
            content = content
        )

        lifecycleScope.launch {

            try {

                val response =
                    RetrofitClient.api.updateEntry(
                        entryId,
                        updatedEntry
                    )

                if (response.isSuccessful) {

                    Toast.makeText(
                        this@EditEntryActivity,
                        "Entry updated successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()

                } else {

                    Toast.makeText(
                        this@EditEntryActivity,
                        "Unable to update entry",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@EditEntryActivity,
                    "Server connection failed",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}