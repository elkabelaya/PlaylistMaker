package com.example.playlistmaker.main.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.presentation.MediaActivity
import com.example.playlistmaker.settings.presentation.SettingsActivity
import com.example.playlistmaker.search.presentation.SearchActivity
import com.example.playlistmaker.presentation.utils.setupTopInset

class MainActivity : AppCompatActivity(), ClickDebounceUseCase {
    private lateinit var binding: ActivityMainBinding
    private var clickDebounceUseCase = Creator.provideClickDebounceUseCase()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopInset(this, binding.root)

        binding.searchButton.setOnClickListener {
            if (canClickDebounced()) {
                val displayIntent = Intent(this, SearchActivity::class.java)
                startActivity(displayIntent)
            }
        }

        binding.mediaButton.setOnClickListener {
            if (canClickDebounced()) {
                val displayIntent = Intent(this, MediaActivity::class.java)
                startActivity(displayIntent)
            }
        }

        binding.settingsButton.setOnClickListener {
            if (clickDebounceUseCase.canClickDebounced()) {
                val displayIntent = Intent(this, SettingsActivity::class.java)
                startActivity(displayIntent)
            }
        }
    }

    override fun canClickDebounced(): Boolean = clickDebounceUseCase.canClickDebounced()
}