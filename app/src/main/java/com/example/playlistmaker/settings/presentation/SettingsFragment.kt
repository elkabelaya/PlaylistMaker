package com.example.playlistmaker.settings.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.utils.FragmentWithToolBar
import com.example.playlistmaker.databinding.FragmentSettingsBinding
import com.example.playlistmaker.settings.di.settingsModules
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules

class SettingsFragment : FragmentWithToolBar() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(settingsModules)
    }


override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    _binding = FragmentSettingsBinding.inflate(inflater, container, false)
    return binding.root
}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolBar(resources.getString(R.string.main_settings), false, binding.toolbar)
        setupViewModel()
        setupListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(settingsModules)
    }

    private fun setupViewModel() {
        viewModel.observeDarkMode().observe(viewLifecycleOwner) { mode ->
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