package com.example.playlistmaker.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = DbConstants.TRACK_FAVORITE_TABLE
)
data class TrackFavoriteEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbConstants.FAVORITE_TRACK_PRIMARY_KEY)
    val favoriteId: Long = 0,
    @ColumnInfo(name = DbConstants.TRACK_ID)
    val trackId: Long,
)