package com.example.playlistmaker.settings.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import com.example.playlistmaker.search.domain.api.SearchState

interface SettingsViewModel {
    fun observeDarkMode(): LiveData<Boolean>
    fun switch(isDarck: Boolean)
    fun share()
    fun support()
    fun agreement()
}