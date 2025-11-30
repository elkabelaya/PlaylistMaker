package com.example.playlistmaker.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.common.presentation.utils.setupTopInset
import com.example.playlistmaker.main.di.mainModules
import com.example.playlistmaker.search.di.searchModules
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(mainModules)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopInset(this, binding.root)
        setupListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(mainModules)
    }

    private fun setupListeners() {
        binding.searchButton.setOnClickListener {
            viewModel.search()
        }

        binding.mediaButton.setOnClickListener {
            viewModel.media()
        }

        binding.settingsButton.setOnClickListener {
            viewModel.settings()
        }
    }
}