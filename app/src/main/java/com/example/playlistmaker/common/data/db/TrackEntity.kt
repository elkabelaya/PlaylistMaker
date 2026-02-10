package com.example.playlistmaker.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbConstants.FAVORITE_TRACKS_TABLE)
data class TrackEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbConstants.FAVORITE_TRACK_PRIMARY_KEY)
    val id: Long = 0,
    @ColumnInfo(name = DbConstants.FAVORITE_TRACK_ID)
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