package com.example.playlistmaker.player.data.repository

import android.media.MediaPlayer
import com.example.playlistmaker.player.domain.repository.PlayerRepository

class PlayerRepositoryImpl(val url: String): PlayerRepository {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var isPrepared: Boolean = false
    override lateinit var onPrepared: () -> Unit
    override lateinit var onComplete: () -> Unit

    init {
        setupPlayer()
    }

    private fun setupPlayer() {
        if (!isPrepared) {
            try {
                mediaPlayer.setDataSource(url)
                mediaPlayer.prepareAsync()
                mediaPlayer.setOnPreparedListener {
                    isPrepared = true
                    onPrepared()
                }
                mediaPlayer.setOnCompletionListener {
                    onComplete()
                }
            } catch (ex: Exception){}
        }
    }

    override fun time(): Int {
        return mediaPlayer.currentPosition
    }

    override fun play() {
        if (isPrepared && !mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }
    }

    override fun pause() {
        if (isPrepared && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    override fun destroy() {
        pause()
        if (isPrepared) {
            isPrepared = false
            mediaPlayer.release()
        }
    }
}