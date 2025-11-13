package com.example.playlistmaker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.search.SearchActivity
import com.example.playlistmaker.ui.utils.ClickDebouncer
import com.example.playlistmaker.ui.utils.ClickDebouncerInterface
import com.example.playlistmaker.ui.utils.setupTopInset

class MainActivity : AppCompatActivity(), ClickDebouncerInterface {
    private var clickDebouncer = ClickDebouncer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTopInset(this, R.id.main)

        val searchButton = findViewById<Button>(R.id.search)
        val mediaButton = findViewById<Button>(R.id.media)
        val settingsButton = findViewById<Button>(R.id.settings)

        searchButton.setOnClickListener {
            if (canClickDebounced()) {
                val displayIntent = Intent(this, SearchActivity::class.java)
                startActivity(displayIntent)
            }
        }

        mediaButton.setOnClickListener {
            if (canClickDebounced()) {
                val displayIntent = Intent(this, MediaActivity::class.java)
                startActivity(displayIntent)
            }
        }

        settingsButton.setOnClickListener {
            if (canClickDebounced()) {
                val displayIntent = Intent(this, SettingsActivity::class.java)
                startActivity(displayIntent)
            }
        }
    }

    override fun canClickDebounced(): Boolean = clickDebouncer.canClickDebounced()
}