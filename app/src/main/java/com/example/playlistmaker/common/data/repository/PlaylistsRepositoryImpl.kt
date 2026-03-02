package com.example.playlistmaker.common.data.repository

import com.example.playlistmaker.common.data.db.dao.PlaylistsDao
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.repository.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistsRepositoryImpl(
    val dao: PlaylistsDao,
    val mapper: PlaylistsDbMapper,
    val trackDbMapper: TracksDbMapper
): PlaylistsRepository {
    override suspend fun insert(playlist: Playlist): Long {
        return dao.insertPlaylist(mapper.map(playlist))
    }

    override suspend fun update(playlist: Playlist) {
        val entity = dao.getPlaylist(playlist.id)
        dao.updatePlaylist(mapper.fill(entity, playlist))
    }

    override suspend fun delete(playlistId: Long) {
        dao.deletePlaylist(playlistId)
        dao.deleteZombies()
    }

    override suspend fun get(): Flow<Playlists> = flow {
        dao.getPlaylists()
            .collect {
                val playlists = it.map {
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

}