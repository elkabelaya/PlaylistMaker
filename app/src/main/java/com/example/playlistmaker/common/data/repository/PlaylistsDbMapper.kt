package com.example.playlistmaker.common.data.repository

import com.example.playlistmaker.common.data.db.PlaylistCount
import com.example.playlistmaker.common.data.db.PlaylistEntity
import com.example.playlistmaker.common.data.db.TrackPlaylistEntity
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track

interface PlaylistsDbMapper {
    fun map(playlist: Playlist): PlaylistEntity
    fun map(entity: PlaylistCount): Playlist
    fun fill(entity: PlaylistEntity, playlist: Playlist): PlaylistEntity
    fun map(track: Track, playlist: Playlist): TrackPlaylistEntity
}