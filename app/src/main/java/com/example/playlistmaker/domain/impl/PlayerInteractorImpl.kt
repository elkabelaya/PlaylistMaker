package com.example.playlistmaker.domain.impl

import com.example.playlistmaker.domain.api.PlayerInteractor
import com.example.playlistmaker.domain.api.PlayerInteractor.Companion.STATE_DEFAULT
import com.example.playlistmaker.domain.api.PlayerInteractor.Companion.STATE_PAUSED
import com.example.playlistmaker.domain.api.PlayerInteractor.Companion.STATE_PLAYING
import com.example.playlistmaker.domain.api.PlayerInteractor.Companion.STATE_PREPARED
import com.example.playlistmaker.domain.repository.PlayerRepository
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerInteractorImpl(private val playerRepository: PlayerRepository,
    val onState:(Int)-> Unit): PlayerInteractor {

    private var playerState = STATE_DEFAULT
        set(value) {
            field = value
            onState(value)
        }
    private var dateFormatter = SimpleDateFormat("mm:ss", Locale.getDefault())

    override fun time(): String {
        return dateFormatter.format(playerRepository.time())
    }
    override fun onPrepared() {
        playerState = STATE_PREPARED
    }

    override fun onComplete() {
        playerState = STATE_PREPARED
    }

    override fun togglePlay() {
        when(playerState) {
            STATE_PLAYING -> {
                pause()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                play()
            }
        }
    }

    override fun pause() {
        playerRepository.pause()
        playerState = STATE_PAUSED
    }

    fun play() {
        playerRepository.play()
        playerState = STATE_PLAYING
    }

    override fun release() {
        playerRepository.destroy()
    }
}