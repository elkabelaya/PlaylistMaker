package com.example.playlistmaker.common.domain.mocks

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track

fun Playlist.Companion.mock1(): Playlist {
    return Playlist(
        1,
        name = "Playlist1",
        description = "Playlist1 Description",
        coverUrl = null,
        count = "12 tracks",
        duration = "14:50"
    )
}

fun Playlist.Companion.mock2(): Playlist {
    return Playlist(
        1,
        name = "Playlist2",
        description = "Playlist2 Description",
        coverUrl = null,
        count = "0 tracks",
        duration = "12:00"
    )
}

fun Playlist.Companion.mockList(): List<Playlist> {
    return listOf(mock1(), mock2())
}