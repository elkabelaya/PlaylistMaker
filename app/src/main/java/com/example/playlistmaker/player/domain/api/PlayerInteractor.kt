package com.example.playlistmaker.player.domain.api

import com.example.playlistmaker.common.domain.api.StateFullInteractor
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.player.domain.model.PlayerState

interface PlayerInteractor: StateFullInteractor<PlayerState> {
    fun setup(track: Track)
    fun time(): String
    fun togglePlay()
    fun onPause()
    fun onResume()
    fun destroy()
    fun onPrepared()
    fun onComplete()
}