package com.example.playlistmaker.common.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.playlistmaker.common.data.db.dao.FavoriteTracksDao

@Database(version = 1, entities = [TrackEntity::class])
abstract class AppDatabase : RoomDatabase(){

    abstract fun favoriteTracksDao(): FavoriteTracksDao

}