package com.example.playlistmaker.data.mapper

import com.example.playlistmaker.data.model.TracksDto
import com.example.playlistmaker.data.repository.TracksMapper
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.domain.model.Tracks
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Locale

class TracksMapperImpl: TracksMapper {
    override fun map(dto: String?): Tracks {
        val listType = object : TypeToken<Tracks>(){}.type
        return  Gson().fromJson(dto, listType) ?: listOf<Track>()
    }

    override fun map(tracks: Tracks): String {
        return Gson().toJson(tracks)
    }

    override fun map(dto: TracksDto): Tracks {
        return dto.results
            .filter {
                !it.trackName.isNullOrEmpty()
            }
            .map {
                Track(
                    it.trackName!!,
                    it.artistName,
                    formattedTrackTime(it.trackTime),
                    it.imageUrl,
                    coverUrl(it.imageUrl),
                    it.collectionName,
                    year(it.releaseDate),
                    it.primaryGenreName,
                    it.country,
                    it.previewUrl
                )
            }
    }

    private fun formattedTrackTime(trackTime: Int?): String {
        return try {
            SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTime)
        } catch (e: Exception) {
            ""
        }
    }

    private fun year(releaseDate: String?): String {
        return releaseDate?.substring(0, 4) ?: ""
    }
    private fun  coverUrl(imageUrl: String?): String? {
        return imageUrl?.replaceAfterLast('/',"512x512bb.jpg")
    }
}