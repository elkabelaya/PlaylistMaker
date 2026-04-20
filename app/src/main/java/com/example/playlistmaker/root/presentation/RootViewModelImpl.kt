package com.example.playlistmaker.root.presentation

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.domain.api.PermissionInteractor

class RootViewModelImpl(val permissionInteractor: PermissionInteractor): RootViewModel() {
    override fun onNotification(permitted: Boolean) {
        permissionInteractor.registerNotifications(permitted)
    }
}