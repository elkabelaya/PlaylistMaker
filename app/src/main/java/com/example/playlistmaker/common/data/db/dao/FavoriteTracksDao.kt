package com.example.playlistmaker.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.common.data.db.DbConstants
import com.example.playlistmaker.common.data.db.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: TrackEntity)

    @Query("DELETE " +
            "FROM ${DbConstants.FAVORITE_TRACKS_TABLE} " +
            "WHERE ${DbConstants.FAVORITE_TRACK_ID} = :trackId")
    suspend fun delete(trackId: Long)

    @Query("SELECT * " +
            "FROM ${DbConstants.FAVORITE_TRACKS_TABLE} " +
            "ORDER BY ${DbConstants.FAVORITE_TRACK_PRIMARY_KEY} DESC")
    fun getTracks(): Flow<List<TrackEntity>>

    @Query("SELECT ${DbConstants.FAVORITE_TRACK_ID} " +
            "FROM ${DbConstants.FAVORITE_TRACKS_TABLE}")
    suspend fun getTracksIds(): List<Long>

    @Query("SELECT *  " +
            "FROM ${DbConstants.FAVORITE_TRACKS_TABLE} " +
            "WHERE ${DbConstants.FAVORITE_TRACK_ID} = :trackId")
    suspend fun getTrack(trackId: Long): List<TrackEntity>
}