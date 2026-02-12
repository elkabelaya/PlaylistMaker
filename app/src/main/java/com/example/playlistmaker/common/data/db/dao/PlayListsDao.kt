package com.example.playlistmaker.common.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.common.data.db.DbConstants
import com.example.playlistmaker.common.data.db.TrackEntity
import com.example.playlistmaker.common.data.db.TrackPlaylistEntity
import com.example.playlistmaker.media.domain.model.Playlist
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayListsDao: TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trackToPlaylist: TrackPlaylistEntity)

    @Delete
    suspend fun delete(trackToPlaylist: TrackPlaylistEntity)
}