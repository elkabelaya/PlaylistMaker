package com.example.playlistmaker.media.domain.repository

import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track

interface MediaNavigatorRepository {
    fun getPlayer(item: Track): Navigation
    fun getPlaylist(item: Playlist): Navigation
    fun getNewPlayLst(): Navigation
}