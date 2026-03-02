package com.example.playlistmaker.playlist.presentation.repository

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.playlist.domain.repository.PlaylistDescriptionRepository

class PlaylistDescriptionRepositoryImpl(val context: Context): PlaylistDescriptionRepository {
    override fun getDescription(playlist: Playlist, tracks: Tracks): String {
        val sb = StringBuilder()
        sb.appendLine(playlist.name)
        playlist.description?.takeIf { it.isNotBlank() }?.let {
            sb.appendLine(it)
        }
        sb.appendLine(playlist.count)
        tracks.forEachIndexed { idx, track ->
            val artist = track.artistName ?: ""
            sb.appendLine("${idx + 1}. ${artist} - ${track.trackName} (${track.trackTime})")
        }

        return sb.toString()
    }

    override fun getEmptyDescription(): String {
        return context.getString(R.string.playlist_no_share_title)
    }

}