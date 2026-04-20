package com.example.playlistmaker.player.domain.impl

import com.example.playlistmaker.common.domain.model.Track
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

    override fun setup(track: Track) {
        playerRepository.set(track)
        playerRepository.setupPlayer(
            {onPrepared()},
            {onComplete()},
            {isPlaying ->
                if (isPlaying) {
                    playerState = PlayerState.Playing(time())
                } else {
                    playerState = PlayerState.Paused(time())
                }
            })
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
        playerState = PlayerState.Prepared(playerDefaultsRepository.getEmptyTime())
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

    override fun onPause() {
        playerRepository.onFragmentPause()
    }

    override fun onResume() {
        playerRepository.onFragmentResume()
    }

    private fun pause() {
        playerRepository.pause()
    }

    private fun play() {
        playerRepository.play()
    }

    override fun destroy() {
        playerRepository.destroy()
    }
}