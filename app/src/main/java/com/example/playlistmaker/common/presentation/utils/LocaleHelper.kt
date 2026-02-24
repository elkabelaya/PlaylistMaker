package com.example.playlistmaker.common.presentation.utils

import android.content.Context
import java.util.Locale

object LocaleHelper {
    fun onAttach(context: Context, lang: String): Context? {
        return setLocale(context, lang)
    }

    private fun setLocale(context: Context, language: String): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.getResources().getConfiguration()
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)

        return context.createConfigurationContext(configuration)
    }

}