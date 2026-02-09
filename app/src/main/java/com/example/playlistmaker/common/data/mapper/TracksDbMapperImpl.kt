package com.example.playlistmaker.common.data.mapper

import com.example.playlistmaker.common.data.db.TrackEntity
import com.example.playlistmaker.common.data.repository.TracksDbMapper
import com.example.playlistmaker.common.domain.model.Track

class TracksDbMapperImpl: TracksDbMapper {
    override fun map(track: Track): TrackEntity {
        return TrackEntity(
            trackId = track.trackId,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTime = track.trackTime,
            imageUrl = track.imageUrl,
            coverUrl = track.coverUrl,
            collectionName = track.collectionName,
            year = track.year,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl
        )
    }

    override fun map(entity: TrackEntity): Track {
        return Track(
            entity.trackId,
            entity.trackName,
            entity.artistName,
            entity.trackTime,
            entity.imageUrl,
            entity.coverUrl,
            entity.collectionName,
            entity.year,
            entity.primaryGenreName,
            entity.country,
            entity.previewUrl
        )
    }
}