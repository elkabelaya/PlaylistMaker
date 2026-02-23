package com.example.playlistmaker.common.data.mapper

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.common.data.db.PlaylistCount
import com.example.playlistmaker.common.data.db.PlaylistEntity
import com.example.playlistmaker.common.data.db.TrackPlaylistEntity
import com.example.playlistmaker.common.data.repository.PlaylistsDbMapper
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track

class PlaylistsDbMapperImpl(val context: Context): PlaylistsDbMapper {
    override fun map(playlist: Playlist): PlaylistEntity {
        return PlaylistEntity(
            name = playlist.name,
            description = playlist.description,
            coverUrl = playlist.coverUrl
        )
    }
    override fun map(entity: PlaylistCount): Playlist {
        return Playlist(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            coverUrl = entity.coverUrl,
            count = context.resources.getQuantityString(R.plurals.tracks_count, entity.tracksCount, entity.tracksCount)
        )
    }

    override fun fill(entity: PlaylistEntity, playlist: Playlist): PlaylistEntity {
        entity.name = playlist.name
        entity.description = playlist.description
        entity.coverUrl = playlist.coverUrl
        return entity
    }

    override fun map(track: Track, playlist: Playlist): TrackPlaylistEntity {
        return TrackPlaylistEntity(track.trackId, playlist.id)
    }
}