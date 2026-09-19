package com.example.poefn.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.poefn.R
import com.example.poefn.model.LoginRequest
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var registerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton)
        registerText = findViewById(R.id.registerText)

        loginButton.setOnClickListener {
            loginUser()
        }

        registerText.setOnClickListener {
            startActivity(
                Intent(this, RegisterActivity::class.java)
            )
        }
    }

    private fun loginUser() {

        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString()

        if (email.isEmpty()) {
            emailInput.error = "Enter your email"
            emailInput.requestFocus()
            return
        }

        if (password.isEmpty()) {
            passwordInput.error = "Enter your password"
            passwordInput.requestFocus()
            return
        }

        lifecycleScope.launch {

            try {

                val response = RetrofitClient.api.login(
                    LoginRequest(
                        email = email,
                        password = password
                    )
                )

                if (isFinishing) return@launch

                if (response.isSuccessful &&
                    response.body()?.success == true
                ) {

                    Toast.makeText(
                        this@LoginActivity,
                        "Login successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this@LoginActivity,
                            HomeActivity::class.java
                        )
                    )

                    finish()

                } else {

                    Toast.makeText(
                        this@LoginActivity,
                        response.body()?.message
                            ?: "Invalid email or password",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                if (!isFinishing) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Unable to connect to MyDailyJ server",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}