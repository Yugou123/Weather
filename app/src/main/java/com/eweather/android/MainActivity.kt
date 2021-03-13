package com.eweather.android

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button

import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val prefs = getPreferences(Context.MODE_PRIVATE)
        prefs.getString("weather",null)?.let {
            val intent = Intent(this,WeatherActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}