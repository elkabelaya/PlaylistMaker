package com.example.playlistmaker.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = DbConstants.TRACK_PLAYLIST_TABLE,
    primaryKeys = [DbConstants.TRACK_ID, DbConstants.PLAYLIST_PRIMARY_KEY]
)
data class TrackPlaylistEntity (
    @ColumnInfo(name = DbConstants.TRACK_ID)
    val trackId: Long,
    @ColumnInfo(name = DbConstants.PLAYLIST_PRIMARY_KEY)
    val playlistId: Long,
)

typealias TrackPlaylistEntities = List<TrackPlaylistEntity>