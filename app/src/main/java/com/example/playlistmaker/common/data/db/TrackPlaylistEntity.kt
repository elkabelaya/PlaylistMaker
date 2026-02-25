package com.example.playlistmaker.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbConstants.TRACK_PLAYLIST_TABLE)
data class TrackPlaylistEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbConstants.TRACK_PLAYLIST_PRIMARY_KEY)
    val id: Long = 0,
    @ColumnInfo(name = DbConstants.TRACK_ID)
    val trackId: Long,
    @ColumnInfo(name = DbConstants.PLAYLIST_PRIMARY_KEY)
    val playlistId: Long,
)

typealias TrackPlaylistEntities = List<TrackPlaylistEntity>