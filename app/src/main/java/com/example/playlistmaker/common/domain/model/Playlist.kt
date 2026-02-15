package com.example.playlistmaker.common.domain.model

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String?,
    val coverUrl: String?
)

typealias Playlists = List<Playlist>