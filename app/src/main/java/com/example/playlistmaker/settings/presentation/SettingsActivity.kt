package com.example.playlistmaker.settings.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.data.repository.NavigatorRepositoryImpl
import com.example.playlistmaker.databinding.ActivitySettingsBinding
import com.example.playlistmaker.domain.api.ModeInteractor
import com.example.playlistmaker.presentation.utils.AppCompatActivityWithToolBar

class SettingsActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivitySettingsBinding
    lateinit var modeInteractor: ModeInteractor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar(getResources().getString(R.string.main_settings), binding.root, binding.toolbar)
        modeInteractor = Creator.provideModeInteractor(this)
        setupListeners()
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode", "IntentReset")
    private fun setupListeners() {
        binding.nightSwitch.isChecked = modeInteractor.darkTheme
        binding.nightSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            modeInteractor.switchTheme(isChecked)
        }

        binding.shareButton.setOnClickListener {
            if (canClickDebounced()) {
                val intent = Intent(Intent.ACTION_SEND)
                intent.setType("text/html")
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    getResources().getString(R.string.settings_share_url)
                )
                startActivity(intent)
            }
        }

        binding.supportButton.setOnClickListener {
            if (canClickDebounced()) {
                val intent = Intent(Intent.ACTION_SENDTO)
                val recipient = getResources().getString(R.string.settings_support_email)
                val subject = getResources().getString(R.string.settings_support_subject)
                val body = getResources().getString(R.string.settings_support_body)
                intent.setData(Uri.parse("mailto:${recipient}?subject=${subject}&body=${body}"));
                startActivity(intent)
            }
        }

        binding.agreementButton.setOnClickListener {
            if (canClickDebounced()) {
                NavigatorRepositoryImpl(this).openWeb(getResources().getString(R.string.settings_agreement_url))
            }
        }
    }

}