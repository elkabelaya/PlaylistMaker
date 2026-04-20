package com.example.playlistmaker.player.data.repository

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.player.domain.repository.PlayerRepository
import com.example.playlistmaker.player.services.PlayerService

class ServicePlayerRepositoryImpl(val context: Context): PlayerRepository {
    private var track: Track? = null
    private var playerService: PlayerService? = null
    private var onPrepared: (() -> Unit)? = null
    private var onComplete: (() -> Unit)? = null
    private var onChange: ((Boolean) -> Unit)? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as PlayerService.PlayerServiceBinder
            playerService = binder.getService()
            playerService?.setupPlayer(
                {
                    onPrepared?.invoke()
                },
                {
                    onComplete?.invoke()
                },
                {
                    onChange?.invoke(it)
                }
            )

        }
        override fun onServiceDisconnected(name: ComponentName?) {
            playerService = null
        }
    }

    override fun set(track: Track) {
        this.track = track
    }

    override fun setupPlayer(
        onPrepared: () -> Unit,
        onComplete: () -> Unit,
        onChange: (Boolean) -> Unit
    ) {
        this.onPrepared = onPrepared
        this.onComplete = onComplete
        this.onChange = onChange

        val intent = Intent(context, PlayerService::class.java).apply {
            putExtra(PlayerService.INTENT_KEY, track)
        }
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun time(): Int {
        return playerService?.time() ?: 0
    }

    override fun play() {
        playerService?.play()
    }

    override fun pause() {
        playerService?.pause()
    }

    override fun onFragmentPause() {
        playerService?.onFragmentPause()
    }

    override fun onFragmentResume() {
        playerService?.onFragmentResume()
    }

    override fun destroy() {
        context.unbindService(serviceConnection)
        playerService = null
    }
}