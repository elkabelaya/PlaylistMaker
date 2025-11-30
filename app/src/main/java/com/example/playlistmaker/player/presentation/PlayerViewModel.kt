package com.example.playlistmaker.player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.player.domain.api.PlayerState
abstract class PlayerViewModel: ViewModel() {
    abstract fun setup(url: String?)
    abstract fun observePlayerState(): LiveData<PlayerState>
    abstract fun togglePlay()
    abstract fun toggleFavorite()
    abstract fun onPause()
    abstract fun onDestroy()
}
