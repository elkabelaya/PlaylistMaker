package com.example.playlistmaker.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.common.data.db.DbConstants
import com.example.playlistmaker.common.data.db.TrackEntity

@Dao
interface TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: TrackEntity)

    @Query("""
        DELETE 
        FROM ${DbConstants.TRACKS_TABLE}
        WHERE ${DbConstants.TRACK_ID} IN (
            SELECT ${DbConstants.TRACK_ID}
            FROM ${DbConstants.TRACKS_TABLE}
            WHERE ${DbConstants.TRACK_ID} NOT IN (
                SELECT ${DbConstants.TRACK_ID} 
                FROM ${DbConstants.TRACK_FAVORITE_TABLE})
            AND ${DbConstants.TRACK_ID} NOT IN (
                SELECT ${DbConstants.TRACK_ID} 
                FROM ${DbConstants.TRACK_PLAYLIST_TABLE})
        )
        """
    )
    suspend fun deleteZombies()
}