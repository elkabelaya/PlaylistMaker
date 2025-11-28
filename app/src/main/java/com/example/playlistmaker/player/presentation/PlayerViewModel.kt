package com.example.playlistmaker.player.presentation

import androidx.lifecycle.LiveData
import com.example.playlistmaker.player.domain.api.PlayerState
interface PlayerViewModel {
    fun observePlayerState(): LiveData<PlayerState>
    fun togglePlay()
    fun toggleFavorite()
    fun onPause()
    fun onDestroy()
}
