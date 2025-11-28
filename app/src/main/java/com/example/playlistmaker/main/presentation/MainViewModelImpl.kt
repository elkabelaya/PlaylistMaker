package com.example.playlistmaker.main.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.common.domain.use_case.ClickDebounceUseCase
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.main.domain.api.MainNavigatorInteractor

class MainViewModelImpl(
    val navigatorInteractor: MainNavigatorInteractor,
    val clickDebounceUseCase: ClickDebounceUseCase
    ): MainViewModel, ViewModel() {

    override fun search() {
        if (clickDebounceUseCase.canClickDebounced()) {
            navigatorInteractor.navigateToSearch()
        }
    }

    override fun media() {
        if (clickDebounceUseCase.canClickDebounced()) {
            navigatorInteractor.navigateToMedia()
        }
    }

    override fun settings() {
        if (clickDebounceUseCase.canClickDebounced()) {
            navigatorInteractor.navigateToSettings()
        }
    }

    companion object {
        fun getFactory(
            navigatorInteractor: MainNavigatorInteractor,
            clickDebounceUseCase: ClickDebounceUseCase
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModelImpl(navigatorInteractor, clickDebounceUseCase)
            }
        }
    }
}