package com.example.playlistmaker.common.data.repository

import com.example.playlistmaker.common.data.db.PlaylistEntity
import com.example.playlistmaker.common.data.db.dao.PlaylistsDao
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.common.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class PlaylistsRepositoryImpl(
    val dao: PlaylistsDao,
    val mapper: PlaylistsDbMapper,
    val trackDbMapper: TracksDbMapper
): PlaylistsRepository {
    override suspend fun create(): Long {
        return dao.insertPlaylist(PlaylistEntity(name = ""))
    }

    override suspend fun update(playlist: Playlist) {
        val entity = dao.getPlaylist(playlist.id).first()
        dao.updatePlaylist(mapper.fill(entity, playlist))
    }

    override suspend fun delete(playlistId: Long) {
        dao.deletePlaylist(playlistId)
        dao.deleteTracks(playlistId)
        dao.deleteZombies()
    }

    override suspend fun get(): Flow<Playlists> = flow {
        dao.getPlaylistCounts()
            .collect {
                val playlists = it.mapNotNull {
                    mapper.map(it)

                }
                emit(playlists)
            }
    }

    override suspend fun add(track: Track, inPlaylist: Playlist): Boolean {
        if (dao.hasTrackInPlaylist(track.trackId, inPlaylist.id)) {
            return false
        } else {
            dao.insert(trackDbMapper.map(track))
            dao.insert(mapper.map(track, inPlaylist))
            return true
        }
    }

    override suspend fun remove(track: Track, inPlaylist: Playlist) {
        dao.delete(track.trackId, inPlaylist.id)
        dao.deleteZombies()
    }

    override suspend fun getTracks(playlist: Playlist): Flow<Tracks> = flow {
        dao.getTracks(playlist.id)
            .collect {
                val tracks = it.map {
                    trackDbMapper.map(it)
                }
                emit(tracks)
            }
    }

    override suspend fun getPlaylistUpdates(playlist: Playlist): Flow<Playlist> = flow {
        dao.getPlaylistCount(playlist.id)
            .collect {
                it?.let {
                    emit(mapper.map(it))
                }
            }
    }

}