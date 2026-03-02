package com.example.playlistmaker.common.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.playlistmaker.common.data.db.DbConstants
import com.example.playlistmaker.common.data.db.PlaylistCount
import com.example.playlistmaker.common.data.db.PlaylistCounts
import com.example.playlistmaker.common.data.db.PlaylistEntities
import com.example.playlistmaker.common.data.db.PlaylistEntity
import com.example.playlistmaker.common.data.db.TrackEntities
import com.example.playlistmaker.common.data.db.TrackEntity
import com.example.playlistmaker.common.data.db.TrackPlaylistEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PlaylistsDao: TracksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlaylist(playlist: PlaylistEntity): Long

    @Update
    suspend fun updatePlaylist(playlist: PlaylistEntity)

    @Query("DELETE " +
            "FROM ${DbConstants.TRACK_PLAYLIST_TABLE}  " +
            "WHERE ${DbConstants.PLAYLIST_PRIMARY_KEY} = :playlistId")
    suspend fun deleteTracks(playlistId: Long)
    @Query("DELETE " +
            "FROM ${DbConstants.PLAYLISTS_TABLE}  " +
            "WHERE ${DbConstants.PLAYLIST_PRIMARY_KEY} = :playlistId")
    suspend fun deletePlaylist(playlistId: Long)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trackToPlaylist: TrackPlaylistEntity)

    @Query(
        """
            DELETE 
            FROM ${DbConstants.TRACK_PLAYLIST_TABLE} 
            WHERE ${DbConstants.TRACK_ID} = :trackId
            AND  ${DbConstants.PLAYLIST_PRIMARY_KEY} = :playlistId
            """)
    suspend fun delete(trackId: Long, playlistId: Long)

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
    fun getPlaylistCounts(): Flow<PlaylistCounts>

    @Query(
        """
            SELECT P.${DbConstants.PLAYLIST_PRIMARY_KEY} as id, P.name, P.description, P.coverUrl, 
            count(T.${DbConstants.TRACK_ID}) AS tracksCount, sum(T.${DbConstants.TRACK_TIME}) AS milliseconds
            FROM ${DbConstants.PLAYLISTS_TABLE} P
            LEFT JOIN ${DbConstants.TRACK_PLAYLIST_TABLE} TP  ON P.${DbConstants.PLAYLIST_PRIMARY_KEY} = TP.${DbConstants.PLAYLIST_PRIMARY_KEY} 
            LEFT JOIN ${DbConstants.TRACKS_TABLE} T ON T.${DbConstants.TRACK_ID} = TP.${DbConstants.TRACK_ID} 
            WHERE P.${DbConstants.PLAYLIST_PRIMARY_KEY} = :playlistId
            GROUP BY P.${DbConstants.PLAYLIST_PRIMARY_KEY}
            """)
    fun getPlaylistCount(playlistId: Long): Flow<PlaylistCount?>

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
    fun getPlaylist(playlistId: Long): Flow<PlaylistEntity>

    @Query(
        """
            SELECT T.* 
            FROM ${DbConstants.TRACK_PLAYLIST_TABLE} TP
            LEFT JOIN ${DbConstants.TRACKS_TABLE} T
            ON T.${DbConstants.TRACK_ID} = TP.${DbConstants.TRACK_ID}
            WHERE TP.${DbConstants.PLAYLIST_PRIMARY_KEY} = :playlistId
            ORDER BY TP.${DbConstants.TRACK_PLAYLIST_PRIMARY_KEY} DESC
            """)
    fun getTracks(playlistId: Long): Flow<TrackEntities>
}