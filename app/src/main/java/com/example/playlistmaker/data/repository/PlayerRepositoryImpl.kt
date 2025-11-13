package com.example.playlistmaker.data.repository

import android.media.MediaPlayer
import com.example.playlistmaker.domain.repository.PlayerRepository

class PlayerRepositoryImpl(val url: String): PlayerRepository {
    private var mediaPlayer:MediaPlayer = MediaPlayer()
    override lateinit var onPrepared: () -> Unit
    override lateinit var onComplete: () -> Unit

    init {
        setupPlayer()
    }

    private fun setupPlayer() {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            onPrepared()
        }
        mediaPlayer.setOnCompletionListener {
            onComplete()
        }
    }

    override fun time(): Int {
        return mediaPlayer.currentPosition
    }

    override fun play() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun destroy() {
        pause()
        mediaPlayer.release()
    }
}