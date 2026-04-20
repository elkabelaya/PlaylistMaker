package com.example.playlistmaker.player.data.repository

import android.media.MediaPlayer
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.player.domain.repository.PlayerRepository

class PlayerRepositoryImpl(): PlayerRepository {
    private var mediaPlayer: MediaPlayer = MediaPlayer()
    private var isPrepared: Boolean = false

    private var onChange: ((Boolean) -> Unit)? = null

    var track: Track? = null

    override fun set(track: Track) {
        this.track = track
    }

    override fun setupPlayer(
        onPrepared: () -> Unit,
        onComplete: () -> Unit,
        onChange: (Boolean) -> Unit
    ) {
        if (!isPrepared) {
            try {
                mediaPlayer.setDataSource(track?.previewUrl ?: "")
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
        this.onChange = onChange
    }

    override fun time(): Int {
        return mediaPlayer.currentPosition
    }

    override fun play() {
        if (isPrepared && !mediaPlayer.isPlaying) {
            mediaPlayer.start()
            onChange?.invoke(true)
        }
    }

    override fun pause() {
        if (isPrepared && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            onChange?.invoke(false)
        }
    }

    override fun onFragmentPause() {
        pause()
    }

    override fun onFragmentResume() {
        //do nothing
    }

    override fun destroy() {
        pause()
        if (isPrepared) {
            isPrepared = false
            mediaPlayer.release()
        }
    }
}