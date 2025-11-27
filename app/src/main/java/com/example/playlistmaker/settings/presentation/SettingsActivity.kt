package com.example.playlistmaker.settings.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.playlistmaker.R
import com.example.playlistmaker.common.data.repository.NavigatorRepositoryImpl
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.common.presentation.utils.AppCompatActivityWithToolBar

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
            viewModel.agreement()
        }
    }

}