package com.example.playlistmaker.common.data.repository

import com.example.playlistmaker.common.data.model.TrackDto
import com.example.playlistmaker.common.data.model.TracksDto
import com.example.playlistmaker.common.domain.model.Tracks

interface TracksMapper {
    fun map(dto: TracksDto): Tracks
    fun map(dto: Tracks): TracksDto
}