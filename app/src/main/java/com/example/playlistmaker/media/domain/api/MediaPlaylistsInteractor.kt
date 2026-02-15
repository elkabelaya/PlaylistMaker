package com.example.playlistmaker.media.domain.api

import com.example.playlistmaker.common.domain.model.ErrorState
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import kotlinx.coroutines.flow.Flow

interface MediaPlaylistsInteractor {
    fun getPlayLists(): Flow<Pair<Playlists?, ErrorState?>>
    fun add(playlist: Playlist)
}
