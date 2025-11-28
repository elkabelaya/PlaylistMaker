package com.example.playlistmaker.player.domain.repository

interface PlayerRepository {
    fun time(): Int
    fun play()
    fun pause()
    fun destroy()

    var onPrepared: ()-> Unit
    var onComplete: ()-> Unit
}