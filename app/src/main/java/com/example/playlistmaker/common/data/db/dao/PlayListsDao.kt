package com.example.playlistmaker.common.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.playlistmaker.common.data.db.TrackPlaylistEntity

@Dao
interface PlayListsDao: TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trackToPlaylist: TrackPlaylistEntity)

    @Delete
    suspend fun delete(trackToPlaylist: TrackPlaylistEntity)
}