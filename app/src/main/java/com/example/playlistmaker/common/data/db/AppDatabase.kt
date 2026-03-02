package com.example.playlistmaker.common.data.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
import androidx.core.database.getStringOrNull
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.playlistmaker.common.data.db.dao.FavoriteTracksDao
import com.example.playlistmaker.common.data.db.dao.PlaylistsDao
import com.example.playlistmaker.common.data.mapper.TimeMapper


@Database(version = 3,
    entities = [TrackEntity::class,
        PlaylistEntity::class,
        TrackPlaylistEntity::class,
        TrackFavoriteEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteTracksDao(): FavoriteTracksDao
    abstract fun playlistsDao(): PlaylistsDao
    
    companion object {}
}


