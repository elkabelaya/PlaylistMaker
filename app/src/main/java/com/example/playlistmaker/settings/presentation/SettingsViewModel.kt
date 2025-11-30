package com.example.playlistmaker.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class SettingsViewModel : ViewModel(){
    abstract fun observeDarkMode(): LiveData<Boolean>
    abstract fun switch(isDarck: Boolean)
    abstract fun share()
    abstract fun support()
    abstract fun agreement()
}