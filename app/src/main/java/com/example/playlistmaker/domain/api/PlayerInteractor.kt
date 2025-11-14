package com.example.playlistmaker.domain.api

interface PlayerInteractor {
    fun time(): String
    fun togglePlay()
    fun pause()
    fun release()
    fun onPrepared()
    fun onComplete()

    companion object {
       const val STATE_DEFAULT = 0
       const val STATE_PREPARED = 1
       const val STATE_PLAYING = 2
       const val STATE_PAUSED = 3

    }
}
