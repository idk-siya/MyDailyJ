package com.example.poefn.ui.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.poefn.R

class SettingsActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var saveSettingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        nameInput = findViewById(R.id.nameInput)
        saveSettingsButton =
            findViewById(R.id.saveSettingsButton)

        saveSettingsButton.setOnClickListener {
            saveSettings()
        }
    }

    private fun saveSettings() {

        val name =
            nameInput.text.toString().trim()

        if (name.isEmpty()) {

            nameInput.error =
                "Enter your name"

            nameInput.requestFocus()

            return
        }

        /*
         * For the final version, this information
         * should be sent to your ASP.NET Core API
         * and stored in Azure SQL.
         */

        Toast.makeText(
            this,
            "Settings saved successfully",
            Toast.LENGTH_SHORT
        ).show()
    }
}