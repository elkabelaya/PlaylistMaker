package com.example.playlistmaker.settings.presentation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.api.ModeInteractor
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.search.domain.api.SearchInteractor
import com.example.playlistmaker.search.domain.api.SearchNavigatorInteractor
import com.example.playlistmaker.search.domain.api.SearchState
import com.example.playlistmaker.settings.domain.api.SettingsNavigatorInteractor


class SettingsViewModelImpl(val context: Context): SettingsViewModel, ViewModel() {
    private val darkModeLiveData = MutableLiveData(false)
    override fun observeDarkMode(): LiveData<Boolean> = darkModeLiveData

    private lateinit var modeInteractor: ModeInteractor
    private lateinit var navigatorInteractor: SettingsNavigatorInteractor
    private var clickDebounceUseCase = Creator.provideClickDebounceUseCase()

    override fun switch(isDark: Boolean) {
        modeInteractor.switchTheme(isDark)
        darkModeLiveData.postValue(modeInteractor.darkTheme)
    }

    override fun share() {
        if (clickDebounceUseCase.canClickDebounced()) {
            navigatorInteractor.navigateToShare()
        }
    }

    override fun support() {
        if (clickDebounceUseCase.canClickDebounced()) {
            navigatorInteractor.navigateToMail()
        }
    }

    override fun agreement() {
        if (clickDebounceUseCase.canClickDebounced()) {
            navigatorInteractor.navigateToAgreement()
        }
    }

    init {
        modeInteractor = Creator.provideModeInteractor(context)
        navigatorInteractor = Creator.provideSettingsNavigatorInteractor(context)
        darkModeLiveData.postValue(modeInteractor.darkTheme)
    }

    companion object {
        fun getFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModelImpl(context)
            }
        }

        const val INTENT_KEY = "INTENT_KEY"
        private const val PLAYER_DEBOUNCE_DELAY = 300L
    }
}