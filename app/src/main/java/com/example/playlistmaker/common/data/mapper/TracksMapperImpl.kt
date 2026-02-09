package com.example.playlistmaker.common.data.mapper

import com.example.playlistmaker.common.data.model.TrackDto
import com.example.playlistmaker.common.data.model.TracksDto
import com.example.playlistmaker.common.data.repository.TracksMapper
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks

class TracksMapperImpl: TracksMapper {
    private val timeMapper = TimeMapper()
    private val dateMapper = DateMapper()
    private val urlMapper = ImageUrlMapper()

    override fun map(dto: TracksDto): Tracks {
        return dto.results
            .filter {
                it.trackId > 0 && it.trackName.isNotEmpty()
            }
            .map {
                Track(
                    it.trackId,
                    it.trackName,
                    it.artistName,
                    timeMapper.map(it.trackTime),
                    it.imageUrl,
                    urlMapper.map(it.imageUrl),
                    it.collectionName,
                    dateMapper.mapDate(it.releaseDate),
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl
                )
            }
    }

    override fun map(tracks: Tracks): TracksDto {
        return TracksDto( tracks
            .map {
                TrackDto(
                    it.trackId,
                    it.trackName,
                    it.artistName,
                    timeMapper.map(it.trackTime),
                    it.imageUrl,
                    it.collectionName,
                    dateMapper.mapYear(it.year),
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl
                )
            }
        )
    }
}