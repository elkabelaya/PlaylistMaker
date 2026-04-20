package com.example.playlistmaker.player.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.example.playlistmaker.R

class PlayerForeground(val service: Service) {
    private val notificationManager: NotificationManager
    private var title: String? = null
    private var isInForeground: Boolean = false

    init {
        notificationManager = service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun setTitle(title: String) {
        this.title = title
    }
    fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            "Player service",
            NotificationManager.IMPORTANCE_LOW
        )
        channel.description = "Service for playing music"

        notificationManager.createNotificationChannel(channel)
    }

    fun deleteNotificationChannel() {
        notificationManager.deleteNotificationChannel(NOTIFICATION_CHANNEL_ID)
    }

    private fun createNotification(isPlaying: Boolean): Notification {
        val playAction = Intent(service.applicationContext, PlayerService::class.java).apply {
            if (isPlaying) {
                action = PlayerService.ACTION_PAUSE
            } else {
                action = PlayerService.ACTION_PLAY
            }
        }

        val playPending = PendingIntent.getService(
            service.applicationContext, 0, playAction,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(service, NOTIFICATION_CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(
                if (isPlaying) R.drawable.ic_player_pause_button else R.drawable.ic_player_play_button,
                service.getString(if (isPlaying) R.string.player_pause else R.string.player_play),
                playPending
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()
    }

    fun start(isPlaying: Boolean) {
        if (!isInForeground) {
            ServiceCompat.startForeground(
                service,
                SERVICE_NOTIFICATION_ID,
                createNotification(isPlaying),
                ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
            )
            isInForeground = true
        }
    }

    fun stop() {
        if (isInForeground) {
            ServiceCompat.stopForeground(service, ServiceCompat.STOP_FOREGROUND_REMOVE)
            isInForeground = false
        }
    }

    fun update(isPlaying: Boolean) {
        if (isInForeground) {
            if (isPlaying) {
                val notification = createNotification(isPlaying)
                notificationManager.notify(SERVICE_NOTIFICATION_ID, notification)
            } else {
                notificationManager.cancel(SERVICE_NOTIFICATION_ID)
                stop()
            }
        }
    }


    companion object {
        const val SERVICE_NOTIFICATION_ID = 100
        const val NOTIFICATION_CHANNEL_ID = "player_service_channel"
    }
}