package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.player.domain.repository.PlayerRepository
import com.example.playlistmaker.player.domain.api.PlayerInteractor
import com.example.playlistmaker.player.domain.model.PlayerState
import com.example.playlistmaker.player.domain.repository.PlayerDefaultsRepository
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerInteractorImpl(
    private val playerRepository: PlayerRepository,
    private val playerDefaultsRepository: PlayerDefaultsRepository
): PlayerInteractor {
    private var sendState:((PlayerState)-> Unit)? = null
    private var playerState: PlayerState = PlayerState.Default(time())
        set(value) {
            field = value
            sendState?.let {
                it(value)
            }
        }
    private var dateFormatter = SimpleDateFormat("mm:ss", Locale.getDefault())

    override fun setup(url: String) {
        playerRepository.setupPlayer(url, {onPrepared()}, {onComplete()})
    }

    override fun onState(state: (PlayerState) -> Unit) {
        sendState = state
    }

    override fun time(): String {
        return try {
            dateFormatter.format(playerRepository.time())
        } catch (e: Exception) {
            playerDefaultsRepository.getEmptyTime()
        }
    }
    override fun onPrepared() {
        playerState = PlayerState.Prepared(time())
    }

    override fun onComplete() {
        playerState = PlayerState.Prepared(time())
    }

    override fun togglePlay() {
        when(playerState) {
            is PlayerState.Playing -> {
                pause()
            }
            is PlayerState.Prepared, is PlayerState.Paused -> {
                play()
            }
            is PlayerState.Default -> {}
        }
    }

    override fun pause() {
        playerRepository.pause()
        playerState = PlayerState.Paused(time())
    }

    fun play() {
        playerRepository.play()
        playerState = PlayerState.Playing(time())
    }

    override fun release() {
        playerRepository.destroy()
    }
}