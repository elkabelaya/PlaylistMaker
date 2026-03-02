package com.example.playlistmaker.common.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DbConstants.PLAYLISTS_TABLE)
data class PlaylistEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = DbConstants.PLAYLIST_PRIMARY_KEY)
    val id: Long = 0,
    var name: String,
    var description: String? = null,
    var coverUrl: String? = null
)

typealias PlaylistEntities = List<PlaylistEntity>


data class PlaylistCount (
    val id: Long = 0,
    val name: String,
    val description: String?,
    val coverUrl: String?,
    val tracksCount: Int
)

typealias PlaylistCounts = List<PlaylistCount>