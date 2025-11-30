package com.example.playlistmaker.settings.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.common.presentation.utils.AppCompatActivityWithToolBar
import com.example.playlistmaker.settings.di.settingsModules
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules

class SettingsActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(settingsModules)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar(getResources().getString(R.string.main_settings), binding.root, binding.toolbar)
        setupViewModel()
        setupListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(settingsModules)
    }

    private fun setupViewModel() {
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