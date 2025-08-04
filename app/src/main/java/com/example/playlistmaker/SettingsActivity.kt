package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate


class SettingsActivity : AppCompatActivityWithToolBar() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupToolBar(getResources().getString(R.string.main_settings))
        setupNightMode()
        setupListeners()
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode", "IntentReset")
    private fun setupListeners() {
        val nightSwitch = findViewById<Switch>(R.id.switch_night_mode)
        val shareButton = findViewById<Button>(R.id.share)
        val supportButton = findViewById<Button>(R.id.support)
        val agreementButton = findViewById<Button>(R.id.agreement)

        nightSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            val newMode =
                if (isChecked) {
                   AppCompatDelegate.MODE_NIGHT_YES
                } else {
                    AppCompatDelegate.MODE_NIGHT_NO
                }
            AppCompatDelegate.setDefaultNightMode(newMode)
            val sharedPreferences = getSharedPreferences(THEME_PREFERENCIES_KEY, Context.MODE_PRIVATE)
            sharedPreferences.edit().putInt(NIGHT_PREFERENCIES_KEY, newMode).apply()
        }

        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/html")
            intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.settings_share_url))
            startActivity(intent)
        }

        supportButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            val recipient = getResources().getString(R.string.settings_support_email)
            val subject = getResources().getString(R.string.settings_support_subject)
            val body = getResources().getString(R.string.settings_support_body)
            intent.setData(Uri.parse("mailto:${recipient}?subject=${subject}&body=${body}"));
            startActivity(intent)
        }

        agreementButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(getResources().getString(R.string.settings_agreement_url)))
            startActivity(intent)
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun setupNightMode() {
        val nightSwitch = findViewById<Switch>(R.id.switch_night_mode)
        val sharedPreferences = getSharedPreferences(THEME_PREFERENCIES_KEY, Context.MODE_PRIVATE)
        val savedMode = sharedPreferences.getInt(NIGHT_PREFERENCIES_KEY, AppCompatDelegate.getDefaultNightMode())
        AppCompatDelegate.setDefaultNightMode(savedMode)
        nightSwitch.isChecked = savedMode == AppCompatDelegate.MODE_NIGHT_YES
    }

    companion object {
        const val THEME_PREFERENCIES_KEY = "THEME_PREFERENCIES_KEY"
        const val NIGHT_PREFERENCIES_KEY = "NIGHT_PREFERENCIES_KEY"
    }
}