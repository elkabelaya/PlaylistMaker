package com.example.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.example.playlistmaker.utils.AppCompatActivityWithToolBar
import com.google.android.material.switchmaterial.SwitchMaterial


class SettingsActivity : AppCompatActivityWithToolBar() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        nightSwitch.isChecked = (applicationContext as App).darkTheme
        nightSwitch.setOnCheckedChangeListener { switcher, isChecked ->
            //Здесь проверка на canClickDebounced не нужна,
            //если в ответ на быстрые клики пользователя
            //одновременно открыть окно ( в других разделах настроек )
            //и переключить тему (в этом блоке кода)
            //то это не будет негативно влиять на UX
            //TODO: убрать комментарий после прохождения ревью
            (applicationContext as App).switchTheme(isChecked)
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