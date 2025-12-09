package com.example.playlistmaker.player.domain.api

import com.example.playlistmaker.common.domain.api.StateFullInteractor
import com.example.playlistmaker.player.domain.model.PlayerState
import com.example.playlistmaker.search.domain.model.SearchState

interface PlayerInteractor: StateFullInteractor<PlayerState> {
    fun setup(url: String)
    fun time(): String
    fun togglePlay()
    fun pause()
    fun release()
    fun onPrepared()
    fun onComplete()
}