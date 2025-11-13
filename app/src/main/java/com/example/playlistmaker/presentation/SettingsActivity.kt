package com.example.playlistmaker.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.api.ModeInteractor
import com.example.playlistmaker.presentation.utils.AppCompatActivityWithToolBar


class SettingsActivity : AppCompatActivityWithToolBar() {
    lateinit var modeInteractor: ModeInteractor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modeInteractor = Creator.provideModeInteractor(this)
        setContentView(R.layout.activity_settings)
        setupToolBar(getResources().getString(R.string.main_settings))
        setupListeners()
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode", "IntentReset")
    private fun setupListeners() {
        val nightSwitch = findViewById<Switch>(R.id.switch_night_mode)
        val shareButton = findViewById<Button>(R.id.share)
        val supportButton = findViewById<Button>(R.id.support)
        val agreementButton = findViewById<Button>(R.id.agreement)

        nightSwitch.isChecked = modeInteractor.darkTheme
        nightSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            modeInteractor.switchTheme(isChecked)
        }

        shareButton.setOnClickListener {
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

        supportButton.setOnClickListener {
            if (canClickDebounced()) {
                val intent = Intent(Intent.ACTION_SENDTO)
                val recipient = getResources().getString(R.string.settings_support_email)
                val subject = getResources().getString(R.string.settings_support_subject)
                val body = getResources().getString(R.string.settings_support_body)
                intent.setData(Uri.parse("mailto:${recipient}?subject=${subject}&body=${body}"));
                startActivity(intent)
            }
        }

        agreementButton.setOnClickListener {
            if (canClickDebounced()) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(getResources().getString(R.string.settings_agreement_url)))
                startActivity(intent)
            }
        }
    }

}