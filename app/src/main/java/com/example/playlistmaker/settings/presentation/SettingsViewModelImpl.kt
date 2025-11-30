package com.example.playlistmaker.settings.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.common.di.Creator
import com.example.playlistmaker.common.domain.api.ModeInteractor

import com.example.playlistmaker.settings.domain.api.SettingsNavigatorInteractor


class SettingsViewModelImpl(
    val modeInteractor: ModeInteractor,
    val navigatorInteractor: SettingsNavigatorInteractor
): SettingsViewModel, ViewModel() {
    private val darkModeLiveData = MutableLiveData(false)
    override fun observeDarkMode(): LiveData<Boolean> = darkModeLiveData
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
        darkModeLiveData.postValue(modeInteractor.darkTheme)
    }

    companion object {
        fun getFactory(modeInteractor: ModeInteractor,
                       navigatorInteractor: SettingsNavigatorInteractor
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsViewModelImpl(modeInteractor, navigatorInteractor)
            }
        }
    }
}