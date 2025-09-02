package com.example.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.fragment.R

class SplashActivity : AppCompatActivity() {
    // Durée du splash screen (en millisecondes)
    private val SPLASH_TIME_OUT: Long = 3000 // 3 secondes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Handler pour lancer MainActivity après un délai
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish() // Fermer la SplashActivity
        }, SPLASH_TIME_OUT)
    }
}