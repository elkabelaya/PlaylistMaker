package com.example.playlistmaker.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbConstants.TRACK_PLAYLIST_TABLE)
data class TrackPlaylistEntity (
    @PrimaryKey
    @ColumnInfo(name = DbConstants.TRACK_ID)
    val trackId: Long = 0,
    @ColumnInfo(name = DbConstants.PLAYLIST_PRIMARY_KEY)
    val playlistId: Long,
)