package com.example.playlistmaker.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.common.presentation.utils.setupTopInset
import com.example.playlistmaker.common.di.Creator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopInset(this, binding.root)
        setupViewModel()
        setupListeners()
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

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this,
            MainViewModelImpl.getFactory(
                Creator.provideMainNavigatorInteractor(this),
                Creator.provideClickDebounceUseCase()
            )
        ).get(MainViewModelImpl::class.java)
    }
}