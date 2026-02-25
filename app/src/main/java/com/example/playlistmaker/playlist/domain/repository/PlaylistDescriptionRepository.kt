package com.example.playlistmaker.playlist.domain.repository

import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks

interface PlaylistDescriptionRepository {
    fun getDescription(playlist: Playlist, tracks: Tracks): String
    fun getEmptyDescription(): String
}