package com.example.playlistmaker.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbConstants.Companion.PLAYLISTS_TABLE)
data class PlayListEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbConstants.Companion.PLAYLIST_PRIMARY_KEY)
    val id: Long = 0,
    val name: String,
    val description: String?,
    val coverUrl: String?
)