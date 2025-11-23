package com.example.playlistmaker.player.domain.api

interface PlayerInteractor {
    fun time(): String
    fun togglePlay()
    fun pause()
    fun release()
    fun onPrepared()
    fun onComplete()
}