package com.example.playlistmaker.root.presentation

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.api.PermissionInteractor

abstract class RootViewModel(): ViewModel() {
    abstract fun onNotification(permitted: Boolean)
}