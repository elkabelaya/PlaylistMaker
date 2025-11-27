package com.example.playlistmaker.main.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.main.domain.api.MainNavigatorInteractor

class MainViewModelImpl(context: Context): MainViewModel, ViewModel() {
    private var clickDebounceUseCase = Creator.provideClickDebounceUseCase()
    private lateinit var navigatorInteractor: MainNavigatorInteractor

    init {
        navigatorInteractor = Creator.provideMainNavigatorInteractor(context)
    }

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
        fun getFactory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MainViewModelImpl(context)
            }
        }
    }
}