package com.example.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button


class SettingsActivity : AppCompatActivityWithToolBar() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setupToolBar(getResources().getString(R.string.main_settings))
        setupListeners()
    }

    fun setupListeners() {
        val shareButton = findViewById<Button>(R.id.share)
        val supportButton = findViewById<Button>(R.id.support)
        val agreementButton = findViewById<Button>(R.id.agreement)

        shareButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/html")
            intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.settings_share_url))
            startActivity(intent)
        }

        supportButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, getResources().getString(R.string.settings_support_email))
            intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.settings_support_subject))
            intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.settings_support_body))
            startActivity(intent)
        }

        agreementButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(getResources().getString(R.string.settings_agreement_url)))
            startActivity(intent)
        }
    }
}