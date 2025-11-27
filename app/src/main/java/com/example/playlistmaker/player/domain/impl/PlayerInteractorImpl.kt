package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.player.domain.repository.PlayerRepository
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.player.domain.api.PlayerState
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerInteractorImpl(private val playerRepository: PlayerRepository,
                           override val onState:(PlayerState)-> Unit): PlayerInteractor {

    private var playerState: PlayerState = PlayerState.STATE_DEFAULT
        set(value) {
            field = value
            onState(value)
        }
    private var dateFormatter = SimpleDateFormat("mm:ss", Locale.getDefault())

    override fun time(): String {
        return dateFormatter.format(playerRepository.time())
    }
    override fun onPrepared() {
        playerState = PlayerState.STATE_PREPARED
    }

    override fun onComplete() {
        playerState = PlayerState.STATE_PREPARED
    }

    override fun togglePlay() {
        when(playerState) {
            PlayerState.STATE_PLAYING -> {
                pause()
            }
            PlayerState.STATE_PREPARED, PlayerState.STATE_PAUSED -> {
                play()
            }
            PlayerState.STATE_DEFAULT -> {}
        }
    }

    override fun pause() {
        playerRepository.pause()
        playerState = PlayerState.STATE_PAUSED
    }

    fun play() {
        playerRepository.play()
        playerState = PlayerState.STATE_PLAYING
    }

    override fun release() {
        playerRepository.destroy()
    }
}