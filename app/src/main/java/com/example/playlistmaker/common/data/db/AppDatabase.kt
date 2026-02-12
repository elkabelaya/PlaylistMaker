package com.example.playlistmaker.common.data.db

import androidx.room.AutoMigration
import androidx.room.ColumnInfo
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.playlistmaker.common.data.db.dao.FavoriteTracksDao
import com.example.playlistmaker.common.data.db.dao.PlayListsDao


@Database(version = 2,
    entities = [TrackEntity::class,
        PlayListEntity::class,
        TrackPlaylistEntity::class,
        TrackFavoriteEntity::class
    ],
//    autoMigrations = [
//        AutoMigration(
//            from = 1,
//            to = 2,
//            spec = AppDatabase.AutoMigration12::class
//        )
//    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteTracksDao(): FavoriteTracksDao
    abstract fun playlistsDao(): PlayListsDao


    @DeleteColumn("${DbConstants.TRACKS_TABLE}",
            "${DbConstants.FAVORITE_TRACK_PRIMARY_KEY}")
    @RenameTable("${DbConstants.FAVORITE_TRACKS_TABLE_DEPRECATED_1_2}",
        "${DbConstants.TRACKS_TABLE}")
    class  AutoMigration12 : AutoMigrationSpec

    companion object {

        val HANDLE_MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `${DbConstants.TRACKS_TABLE}`
                        (`${DbConstants.TRACK_ID}` INTEGER NOT NULL, 
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
                database.execSQL("""
                        CREATE TABLE IF NOT EXISTS ${DbConstants.TRACK_FAVORITE_TABLE} (
                            ${DbConstants.FAVORITE_TRACK_PRIMARY_KEY} INTEGER PRIMARY KEY,
                            ${DbConstants.TRACK_ID} INTEGER
                        );
                    """
                    .trimIndent())

                database.execSQL("""
                    ALTER TABLE ${DbConstants.FAVORITE_TRACKS_TABLE_DEPRECATED_1_2}
                        DROP COLUMN IF EXISTS ${DbConstants.FAVORITE_TRACKS_PRIMARY_KEY_DEPRECATED_1_2}
                    """
                    .trimIndent())

                database.execSQL("""
                        INSERT INTO ${DbConstants.TRACKS_TABLE}
                        SELECT * FROM ${DbConstants.FAVORITE_TRACKS_TABLE_DEPRECATED_1_2};
                    """
                    .trimIndent())

                database.execSQL("""
                        INSERT INTO ${DbConstants.TRACK_FAVORITE_TABLE} (${DbConstants.TRACK_ID})
                        SELECT ${DbConstants.FAVORITE_TRACKS_ID_DEPRECATED_1_2} FROM ${DbConstants.FAVORITE_TRACKS_TABLE_DEPRECATED_1_2};
                    """
                    .trimIndent())


                database.execSQL("""
                        CREATE TABLE IF NOT EXISTS ${DbConstants.TRACK_PLAYLIST_TABLE} (
                            ${DbConstants.TRACK_ID} INTEGER PRIMARY KEY,
                            ${DbConstants.PLAYLIST_PRIMARY_KEY} INTEGER PRIMARY KEY
                        );
                    """
                    .trimIndent())
            }
        }

    }
}


