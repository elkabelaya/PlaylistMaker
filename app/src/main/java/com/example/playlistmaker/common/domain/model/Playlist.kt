package com.example.playlistmaker.common.domain.model

import java.io.Serializable

data class Playlist(
    val id: Long,
    val name: String,
    val description: String? = null,
    val coverUrl: String? = null,
    val count: String = "",
    val duration: String = ""
): Serializable {
    companion object
}

typealias Playlists = List<Playlist>

fun Playlist.Companion.empty(): Playlist {
    return Playlist(id = 0, name = "")
}
