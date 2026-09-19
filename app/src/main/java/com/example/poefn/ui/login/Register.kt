package com.example.poefn.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.poefn.R
import com.example.poefn.model.RegisterRequest
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var registerButton: Button
    private lateinit var loginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        nameInput = findViewById(R.id.nameInput)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput)
        registerButton = findViewById(R.id.registerButton)
        loginText = findViewById(R.id.loginText)

        registerButton.setOnClickListener {
            registerUser()
        }

        loginText.setOnClickListener {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }
    }

    private fun registerUser() {

        val name = nameInput.text.toString().trim()
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()
        val confirmPassword = confirmPasswordInput.text.toString()

        if (name.isEmpty()) {
            nameInput.error = "Enter your name"
            nameInput.requestFocus()
            return
        }

        if (email.isEmpty()) {
            emailInput.error = "Enter your email"
            emailInput.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Enter a valid email"
            emailInput.requestFocus()
            return
        }

        if (password.isEmpty()) {
            passwordInput.error = "Enter a password"
            passwordInput.requestFocus()
            return
        }

        if (password.length < 8) {
            passwordInput.error =
                "Password must be at least 8 characters"
            passwordInput.requestFocus()
            return
        }

        if (confirmPassword.isEmpty()) {
            confirmPasswordInput.error =
                "Confirm your password"
            confirmPasswordInput.requestFocus()
            return
        }

        if (password != confirmPassword) {
            confirmPasswordInput.error =
                "Passwords do not match"
            confirmPasswordInput.requestFocus()
            return
        }

        lifecycleScope.launch {

            try {

                val response = RetrofitClient.api.register(
                    RegisterRequest(
                        name = name,
                        email = email,
                        password = password
                    )
                )

                if (response.isSuccessful &&
                    response.body()?.success == true
                ) {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Account created successfully",
                        Toast.LENGTH_LONG
                    ).show()

                    startActivity(
                        Intent(
                            this@RegisterActivity,
                            LoginActivity::class.java
                        )
                    )

                    finish()

                } else {

                    Toast.makeText(
                        this@RegisterActivity,
                        response.body()?.message
                            ?: "Registration failed",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@RegisterActivity,
                    "Unable to connect to MyDailyJ server",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}