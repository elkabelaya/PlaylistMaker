package com.example.playlistmaker.common.data.repository

import com.example.playlistmaker.common.data.db.dao.FavoriteTracksDao
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.common.domain.repository.FavoriteTracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteTracksRepositoryImpl(
    val dao: FavoriteTracksDao,
    val mapper: TracksDbMapper): FavoriteTracksRepository {

    override suspend fun add(track: Track) {
        dao.insert(mapper.map(track))
        dao.insertFavorite(mapper.mapFavorite(track))
    }

    override suspend fun delete(trackId: Long) {
        dao.deleteFavorite(trackId)
        dao.deleteZombies()
    }

    override suspend fun get(): Flow<Tracks> = flow {
        dao.getFavoriteTracks()
        .collect {
            val tracks = it.map {
                mapper.map(it)
            }
            emit(tracks)
        }
    }

    override suspend fun find(trackid: Long): Track? {
       val tracks = dao.getFavoriteTrack(trackid)
       if (tracks.count() > 0) {
            return mapper.map(tracks.first())
       } else {
           return null
       }
    }
}