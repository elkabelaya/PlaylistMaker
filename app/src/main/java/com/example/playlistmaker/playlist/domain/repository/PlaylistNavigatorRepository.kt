package com.example.playlistmaker.playlist.domain.repository

import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track

interface PlaylistNavigatorRepository {
    fun getPlayListEdit(playlist: Playlist): Navigation
    fun getPlayer(track: Track): Navigation
}