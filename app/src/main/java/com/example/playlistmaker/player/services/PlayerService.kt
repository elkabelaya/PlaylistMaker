package com.example.playlistmaker.player.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.player.data.repository.PlayerRepositoryImpl
import com.example.playlistmaker.player.domain.repository.PlayerRepository

class PlayerService() : Service(), PlayerRepository {
    private var player: PlayerRepositoryImpl? = null
    private var foreground: PlayerForeground? = null
    private var isPlaying: Boolean = false
    private val binder: IBinder = PlayerServiceBinder()
    private var needStopSelf: Boolean = false

    inner class PlayerServiceBinder : Binder() {
        fun getService(): PlayerService {
            return this@PlayerService
        }
    }

    override fun onCreate() {
        foreground = PlayerForeground(this)
        foreground?.createNotificationChannel()
        super.onCreate()
    }

    override fun onDestroy() {
        foreground?.deleteNotificationChannel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder {
        player = PlayerRepositoryImpl()
        val track = intent?.getSerializableExtra(INTENT_KEY, Track::class.java)
        track?.let {
            set(it)
        }
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        player?.destroy()
        player = null
        if (needStopSelf) {
            //вызов stopSelf() противоречит требованию
            //"Запуск и остановка сервиса производится только при помощи методов bindService() и unbindService()."
            //однако необходим чтобы корректно обработать остановку проигрывания по action в уведомлении
            stopSelf()
        }
        return true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        needStopSelf = true
        when (intent?.action) {
            ACTION_PLAY -> player?.play()
            ACTION_PAUSE -> player?.pause()
        }
        return START_NOT_STICKY
    }

    override fun set(track: Track) {
        player?.set(track)
        foreground?.setTitle(track.artistName + " - " + track.trackName)
    }

    override fun setupPlayer(
        onPrepared: () -> Unit,
        onComplete: () -> Unit,
        onChange: (Boolean) -> Unit
    ) {
        player?.setupPlayer(
            onPrepared,
            {
                isPlaying = false
                foreground?.stop()
                onComplete()
            },
            { isPlaying ->
                this@PlayerService.isPlaying = isPlaying
                foreground?.update(isPlaying)
                onChange(isPlaying)
            }
        )
    }

    override fun time(): Int {
        return player?.time() ?: 0
    }

    override fun play() {
        player?.play()
    }

    override fun pause() {
        player?.pause()
    }


    override fun onFragmentPause() {
        if(isPlaying) {
            foreground?.start(true)
        }
    }

    override fun onFragmentResume() {
        foreground?.stop()
    }

    override fun destroy() {
        foreground?.stop()
        player?.destroy()
    }

    companion object {
        const val INTENT_KEY = "track"
        const val ACTION_PLAY = "com.example.playlistmaker.player.services.PlayerService.ACTION_PLAY"
        const val ACTION_PAUSE = "com.example.playlistmaker.player.services.PlayerService.ACTION_PAUSE"
    }
}