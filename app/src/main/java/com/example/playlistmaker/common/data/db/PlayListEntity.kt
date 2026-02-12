package com.example.playlistmaker.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbConstants.Companion.PLAYLISTS_TABLE)
data class PlayListEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbConstants.Companion.PLAYLIST_PRIMARY_KEY)
    val id: Long = 0,
    @ColumnInfo(name = DbConstants.Companion.TRACK_ID)
    val trackId: Long,
    val trackName: String,
    val artistName: String?,
    val trackTime: String,
    val imageUrl: String?,
    val coverUrl: String?,
    val collectionName: String?,
    val year: String,
    val primaryGenreName: String?,
    val country: String?,
    val previewUrl: String?
)