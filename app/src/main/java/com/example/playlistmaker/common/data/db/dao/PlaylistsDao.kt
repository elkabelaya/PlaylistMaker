package com.example.playlistmaker.common.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlistmaker.common.data.db.DbConstants
import com.example.playlistmaker.common.data.db.PlaylistCounts
import com.example.playlistmaker.common.data.db.PlaylistEntity
import com.example.playlistmaker.common.data.db.TrackPlaylistEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PlaylistsDao: TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long

    @Update
    suspend fun updatePlaylist(playlist: PlaylistEntity)
    @Query("DELETE " +
            "FROM ${DbConstants.PLAYLISTS_TABLE}  " +
            "WHERE ${DbConstants.PLAYLIST_PRIMARY_KEY} = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trackToPlaylist: TrackPlaylistEntity)

    @Delete
    suspend fun delete(trackFromPlaylist: TrackPlaylistEntity)

    @Query(
        """
            SELECT P.${DbConstants.PLAYLIST_PRIMARY_KEY} as id, P.name, P.description, P.coverUrl, 
            count(TP.${DbConstants.PLAYLIST_PRIMARY_KEY}) AS tracksCount 
            FROM ${DbConstants.PLAYLISTS_TABLE} P 
            LEFT JOIN ${DbConstants.TRACK_PLAYLIST_TABLE} TP 
            ON P.${DbConstants.PLAYLIST_PRIMARY_KEY} = TP.${DbConstants.PLAYLIST_PRIMARY_KEY} 
            GROUP BY P.${DbConstants.PLAYLIST_PRIMARY_KEY} 
            ORDER BY id DESC
            """)
    fun getPlaylists(): Flow<PlaylistCounts>

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM ${DbConstants.TRACK_PLAYLIST_TABLE} 
            WHERE ${DbConstants.TRACK_ID} = :trackId
            AND ${DbConstants.PLAYLIST_PRIMARY_KEY} = :playlistId
        )
    """)
    fun hasTrackInPlaylist(trackId: Long, playlistId: Long): Boolean

    @Query(
        """
            SELECT * 
            FROM ${DbConstants.PLAYLISTS_TABLE} 
            WHERE ${DbConstants.PLAYLIST_PRIMARY_KEY} = :playlistId
            """)
    fun getPlaylist(playlistId: Long): PlaylistEntity
}