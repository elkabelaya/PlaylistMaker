package com.example.playlistmaker.settings.presentation

import androidx.lifecycle.LiveData

interface SettingsViewModel {
    fun observeDarkMode(): LiveData<Boolean>
    fun switch(isDarck: Boolean)
    fun share()
    fun support()
    fun agreement()
}