package com.example.playlistmaker.common.data.repository

import com.example.playlistmaker.common.data.db.TrackEntity
import com.example.playlistmaker.common.data.db.TrackFavoriteEntity
import com.example.playlistmaker.common.domain.model.Track

interface TracksDbMapper {
    fun map(track: Track): TrackEntity
    fun mapFavorite(track: Track): TrackFavoriteEntity

    fun map(entity: TrackEntity): Track
}