package com.example.playlistmaker.media.domain.model

import com.example.playlistmaker.common.domain.model.Track

data class Playlist(
    val image: String,
    val title: String,
    val count: Int
)

typealias Playlists = List<Playlist>