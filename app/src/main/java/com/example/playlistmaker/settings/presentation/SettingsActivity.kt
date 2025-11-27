package com.example.playlistmaker.settings.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.data.repository.NavigatorRepositoryImpl
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.domain.api.ModeInteractor
import com.example.playlistmaker.presentation.utils.AppCompatActivityWithToolBar
import com.example.playlistmaker.search.presentation.SearchViewModel
import com.example.playlistmaker.search.presentation.SearchViewModelImpl

class SettingsActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivitySettingsBinding
    lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar(getResources().getString(R.string.main_settings), binding.root, binding.toolbar)
        setupViewModel()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, SettingsViewModelImpl.getFactory(this))
            .get(SettingsViewModelImpl::class.java)
        viewModel.observeDarkMode().observe(this) { mode ->
            binding.nightSwitch.isChecked = mode
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode", "IntentReset")
    private fun setupListeners() {

        binding.nightSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            viewModel.switch(isChecked)
        }

        binding.shareButton.setOnClickListener {
            viewModel.share()
        }

        binding.supportButton.setOnClickListener {
            viewModel.support()
        }

        binding.agreementButton.setOnClickListener {
            if (canClickDebounced()) {
                NavigatorRepositoryImpl(this).openWeb(getResources().getString(R.string.settings_agreement_url))
            }
        }
    }

}