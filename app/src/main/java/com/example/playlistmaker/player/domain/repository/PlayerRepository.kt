package com.example.playlistmaker.player.domain.repository

interface PlayerRepository {
    fun setupPlayer(url: String, onPrepared: () -> Unit, onComplete: () -> Unit)
    fun time(): Int
    fun play()
    fun pause()
    fun destroy()
}