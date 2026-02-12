package com.example.playlistmaker.common.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.common.data.db.DbConstants
import com.example.playlistmaker.common.data.db.TrackEntity
import com.example.playlistmaker.common.data.db.TrackFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTracksDao: TracksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(track: TrackFavoriteEntity)

    @Delete
    suspend fun delete(track: TrackFavoriteEntity)

//    @Query("SELECT T.* " +
//            "FROM ${DbConstants.FAVORITE_TRACK_TABLE} F " +
//            "LEFT JOIN ${DbConstants.TRACKS_TABLE} T " +
//            "ON F.${DbConstants.TRACK_ID} = T.${DbConstants.TRACK_ID} " +
//            "ORDER BY T.${DbConstants.FAVORITE_TRACK_PRIMARY_KEY} DESC")
    @Query("SELECT * " +
            "FROM ${DbConstants.TRACKS_TABLE}")
    fun getTracks(): Flow<List<TrackEntity>>


//    @Query("SELECT T.*  " +
//            "FROM ${DbConstants.FAVORITE_TRACK_TABLE} F " +
//            "LEFT JOIN ${DbConstants.TRACKS_TABLE} T " +
//            "ON F.${DbConstants.TRACK_ID} = T.${DbConstants.TRACK_ID} " +
//            "WHERE F.${DbConstants.TRACK_ID} = :trackId")
    @Query("SELECT * " +
            "FROM ${DbConstants.TRACKS_TABLE} " +
            "WHERE ${DbConstants.TRACK_ID} = :trackId")
    suspend fun getTrack(trackId: Long): List<TrackEntity>
}