package com.example.playlistmaker.player.domain.api

import com.example.playlistmaker.search.domain.api.SearchState

interface PlayerInteractor {
    fun setup(url: String)
    fun onState(state: (PlayerState) -> Unit)
    fun time(): String
    fun togglePlay()
    fun pause()
    fun release()
    fun onPrepared()
    fun onComplete()
}