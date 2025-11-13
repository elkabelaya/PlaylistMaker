package com.example.playlistmaker.data.repository

import com.example.playlistmaker.data.model.TrackDto
import com.example.playlistmaker.data.model.TracksDto
import com.example.playlistmaker.domain.model.Tracks

interface TracksMapper {
    fun map(dto: TracksDto): Tracks
    fun map(dto: Tracks): TracksDto
}