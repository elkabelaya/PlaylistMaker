package com.example.playlistmaker.common.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.playlistmaker.common.data.db.DbConstants
import com.example.playlistmaker.common.data.db.TrackEntities
import com.example.playlistmaker.common.data.db.TrackEntity
import com.example.playlistmaker.common.data.db.TrackFavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTracksDao: TracksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(trackFavorite: TrackFavoriteEntity)

    @Query("DELETE " +
            "FROM ${DbConstants.TRACK_FAVORITE_TABLE}  " +
            "WHERE ${DbConstants.TRACK_ID} = :trackId")
    suspend fun deleteFavorite(trackId: Long)

    @Query("SELECT T.* " +
            "FROM ${DbConstants.TRACK_FAVORITE_TABLE} F " +
            "LEFT JOIN ${DbConstants.TRACKS_TABLE} T " +
            "ON F.${DbConstants.TRACK_ID} = T.${DbConstants.TRACK_ID} " +
            "ORDER BY F.${DbConstants.FAVORITE_TRACK_PRIMARY_KEY} DESC")
    fun getFavoriteTracks(): Flow<TrackEntities>


    @Query("SELECT T.*  " +
            "FROM ${DbConstants.TRACK_FAVORITE_TABLE} F " +
            "LEFT JOIN ${DbConstants.TRACKS_TABLE} T " +
            "ON F.${DbConstants.TRACK_ID} = T.${DbConstants.TRACK_ID} " +
            "WHERE F.${DbConstants.TRACK_ID} = :trackId " +
            "LIMIT 1")
    suspend fun getFavoriteTrack(trackId: Long): List<TrackEntity>
}