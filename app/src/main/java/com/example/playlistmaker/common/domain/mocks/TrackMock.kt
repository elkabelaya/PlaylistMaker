package com.example.playlistmaker.common.domain.mocks

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.example.playlistmaker.common.domain.model.Track

fun Track.Companion.mock1(): Track {
    return Track(
        1,
        trackName = "The Show Must Go On",
        artistName = "Queen",
        trackTime = "04:32",
        imageUrl = null,
        coverUrl = null,
        collectionName = "Innuendo",
        primaryGenreName = "",
        country = "Great Britain",
        previewUrl = "android.resource://com.example.playlistmaker/drawable/ic_error_empty}",
        year = "1991"
    )
}

fun Track.Companion.mock2(): Track {
    var loremIpsum = LoremIpsum(10).values.first()
    return Track(
        1,
        trackName = loremIpsum,
        artistName = loremIpsum,
        trackTime = "04:32",
        imageUrl = null,
        coverUrl = null,
        collectionName = loremIpsum,
        primaryGenreName = "",
        country = loremIpsum,
        previewUrl = "android.resource://com.example.playlistmaker/drawable/ic_error_empty}",
        year = "1991"
    )
}

fun Track.Companion.mock3(): Track {
    return Track(
        1,
        trackName = "artistName = null",
        artistName = null,
        trackTime = "04:32",
        imageUrl = null,
        coverUrl = null,
        collectionName = "Innuendo",
        primaryGenreName = "",
        country = "Great Britain",
        previewUrl = "android.resource://com.example.playlistmaker/drawable/ic_error_empty}",
        year = "1991"
    )
}

fun Track.Companion.mock4(): Track {
    return Track(
        1,
        trackName = "trackTime is empty",
        artistName = "Queen",
        trackTime = "",
        imageUrl = null,
        coverUrl = null,
        collectionName = "Innuendo",
        primaryGenreName = "",
        country = "Great Britain",
        previewUrl = "android.resource://com.example.playlistmaker/drawable/ic_error_empty}",
        year = "1991"
    )
}

fun Track.Companion.mock5(): Track {
    return Track(
        1,
        trackName = "artistName and trackTime are empty",
        artistName = null,
        trackTime = "",
        imageUrl = null,
        coverUrl = null,
        collectionName = "Innuendo",
        primaryGenreName = "",
        country = "Great Britain",
        previewUrl = "android.resource://com.example.playlistmaker/drawable/ic_error_empty}",
        year = "1991"
    )
}

fun Track.Companion.mockList(): List<Track> {
    return listOf(Track.mock1(), Track.mock2(), Track.mock3(), Track.mock4(), Track.mock5())
}
fun Track.Companion.mockSequence(): Sequence<Track> {
    return sequenceOf(Track.mock1(), Track.mock2(), Track.mock3(), Track.mock4(), Track.mock5())
}