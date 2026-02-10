package com.example.playlistmaker.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.common.presentation.StateFullViewModel
import com.example.playlistmaker.player.domain.model.PlayerState
abstract class PlayerViewModel: StateFullViewModel<PlayerState>, ViewModel() {
    abstract fun observeFavorite(): LiveData<Boolean>
    abstract fun setup(url: String?)
    abstract fun togglePlay()
    abstract fun toggleFavorite()
    abstract fun onPause()
    abstract fun onDestroy()
}
