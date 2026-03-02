package com.example.playlistmaker.common.data.db.dao.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.playlistmaker.common.data.db.AppDatabase
import com.example.playlistmaker.common.data.db.DbConstants

val AppDatabase.Companion.HANDLE_MIGRATION_1_2: Migration
    get() = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("""
                        CREATE TABLE IF NOT EXISTS `${DbConstants.TRACKS_TABLE}`
                            (`${DbConstants.TRACK_ID}` INTEGER NOT NULL PRIMARY KEY, 
                            `trackName` TEXT NOT NULL, 
                            `artistName` TEXT, 
                            `trackTime` TEXT NOT NULL, 
                            `imageUrl` TEXT, 
                            `coverUrl` TEXT, 
                            `collectionName` TEXT, 
                            `year` TEXT NOT NULL, 
                            `primaryGenreName` TEXT, 
                            `country` TEXT, 
                            `previewUrl` TEXT
                            );
                            """
                .trimIndent())
            db.execSQL("""
                            CREATE TABLE IF NOT EXISTS ${DbConstants.TRACK_FAVORITE_TABLE} (
                                ${DbConstants.FAVORITE_TRACK_PRIMARY_KEY} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                                ${DbConstants.TRACK_ID} INTEGER NOT NULL
                            );
                        """
                .trimIndent())

            db.execSQL("""
                            INSERT INTO ${DbConstants.TRACKS_TABLE} (
                            `${DbConstants.TRACK_ID}`,
                            `trackName`, 
                            `artistName`,
                            `trackTime`,
                            `imageUrl`,
                            `coverUrl`,
                            `collectionName`,
                            `year`,
                            `primaryGenreName`,
                            `country`,
                            `previewUrl`) 
                            SELECT 
                            `${DbConstants.FAVORITE_TRACKS_ID_DEPRECATED_1_2}`,
                            `trackName`, 
                            `artistName`,
                            `trackTime`,
                            `imageUrl`,
                            `coverUrl`,
                            `collectionName`,
                            `year`,
                            `primaryGenreName`,
                            `country`,
                            `previewUrl` 
                            FROM ${DbConstants.FAVORITE_TRACKS_TABLE_DEPRECATED_1_2};
                        """
                .trimIndent())

            db.execSQL("""
                            INSERT INTO ${DbConstants.TRACK_FAVORITE_TABLE} (${DbConstants.TRACK_ID})
                            SELECT ${DbConstants.FAVORITE_TRACKS_ID_DEPRECATED_1_2} FROM ${DbConstants.FAVORITE_TRACKS_TABLE_DEPRECATED_1_2};
                        """
                .trimIndent())


            db.execSQL("""
                            CREATE TABLE IF NOT EXISTS ${DbConstants.TRACK_PLAYLIST_TABLE} (
                                ${DbConstants.TRACK_ID} INTEGER NOT NULL,
                                ${DbConstants.PLAYLIST_PRIMARY_KEY} INTEGER NOT NULL,
                                PRIMARY KEY (${DbConstants.TRACK_ID}, ${DbConstants.PLAYLIST_PRIMARY_KEY})
                            );
                        """
                .trimIndent())

            db.execSQL("""
                            CREATE TABLE IF NOT EXISTS ${DbConstants.PLAYLISTS_TABLE} (
                                ${DbConstants.PLAYLIST_PRIMARY_KEY} INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,
                                `name` TEXT NOT NULL, 
                                `description` TEXT, 
                                `coverUrl` TEXT
                            );
                        """
                .trimIndent())
            db.execSQL("""
                            DROP TABLE ${DbConstants.FAVORITE_TRACKS_TABLE_DEPRECATED_1_2}
                        """
                .trimIndent())
        }
    }