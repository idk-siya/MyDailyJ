package com.example.poefn

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.poefn.ui.login.LoginActivity


class MainActivity : AppCompatActivity() {

    private val splashRunnable = Runnable {
        if (!isFinishing && !isDestroyed) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Show the MyDailyJ splash screen
        setContentView(R.layout.activity_main)

        // Wait 2 seconds, then open the Login screen
        window.decorView.postDelayed(splashRunnable, 2000)
    }

    override fun onDestroy() {
        // Cancel the pending callback to avoid leaks/crashes
        window.decorView.removeCallbacks(splashRunnable)
        super.onDestroy()
    }
}
