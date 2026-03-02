package com.example.playlistmaker.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbConstants.TRACKS_TABLE)
data class TrackEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbConstants.TRACK_ID)
    val trackId: Long,
    val trackName: String,
    val artistName: String?,
    @ColumnInfo(name = DbConstants.TRACK_TIME)
    val trackTime: Long,
    val imageUrl: String?,
    val coverUrl: String?,
    val collectionName: String?,
    val year: String,
    val primaryGenreName: String?,
    val country: String?,
    val previewUrl: String?
)

typealias TrackEntities = List<TrackEntity>