package com.example.playlistmaker.common.domain.model

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String? = null,
    val coverUrl: String? = null,
    val count: String = ""
)

typealias Playlists = List<Playlist>