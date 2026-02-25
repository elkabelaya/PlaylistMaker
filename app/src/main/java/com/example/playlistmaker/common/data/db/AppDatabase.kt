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


    companion object {

        val HANDLE_MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
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
                database.execSQL("""
                        CREATE TABLE IF NOT EXISTS ${DbConstants.TRACK_FAVORITE_TABLE} (
                            ${DbConstants.FAVORITE_TRACK_PRIMARY_KEY} INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                            ${DbConstants.TRACK_ID} INTEGER NOT NULL
                        );
                    """
                    .trimIndent())

                database.execSQL("""
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

                database.execSQL("""
                        INSERT INTO ${DbConstants.TRACK_FAVORITE_TABLE} (${DbConstants.TRACK_ID})
                        SELECT ${DbConstants.FAVORITE_TRACKS_ID_DEPRECATED_1_2} FROM ${DbConstants.FAVORITE_TRACKS_TABLE_DEPRECATED_1_2};
                    """
                    .trimIndent())


                database.execSQL("""
                        CREATE TABLE IF NOT EXISTS ${DbConstants.TRACK_PLAYLIST_TABLE} (
                            ${DbConstants.TRACK_ID} INTEGER NOT NULL,
                            ${DbConstants.PLAYLIST_PRIMARY_KEY} INTEGER NOT NULL,
                            PRIMARY KEY (${DbConstants.TRACK_ID}, ${DbConstants.PLAYLIST_PRIMARY_KEY})
                        );
                    """
                    .trimIndent())

                database.execSQL("""
                        CREATE TABLE IF NOT EXISTS ${DbConstants.PLAYLISTS_TABLE} (
                            ${DbConstants.PLAYLIST_PRIMARY_KEY} INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,
                            `name` TEXT NOT NULL, 
                            `description` TEXT, 
                            `coverUrl` TEXT
                        );
                    """
                    .trimIndent())
                database.execSQL("""
                        DROP TABLE ${DbConstants.FAVORITE_TRACKS_TABLE_DEPRECATED_1_2}
                    """
                    .trimIndent())
            }
        }

        val HANDLE_MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                val tracksTempTable = "TRACKS_TEMPORARY_TABLE"
                val tracksPlaylistTempTable = "TRACKS_PLAYLIST_TEMPORARY_TABLE"
                val timeMapper = TimeMapper()

                db.execSQL("""
                    CREATE TABLE ${tracksTempTable} 
                    (`${DbConstants.TRACK_ID}` INTEGER NOT NULL PRIMARY KEY, 
                        `trackName` TEXT NOT NULL, 
                        `artistName` TEXT, 
                        `${DbConstants.TRACK_TIME}` INTEGER NOT NULL, 
                        `imageUrl` TEXT, 
                        `coverUrl` TEXT, 
                        `collectionName` TEXT, 
                        `year` TEXT NOT NULL, 
                        `primaryGenreName` TEXT, 
                        `country` TEXT, 
                        `previewUrl` TEXT
                        );
                    """.trimIndent())

                val cursor = db.query("""
                    SELECT 
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
                    `previewUrl` 
                    FROM ${DbConstants.TRACKS_TABLE};
                """.trimIndent() )

                cursor.use {
                    if (it.moveToFirst()) {
                        do {
                            val transformedTime = timeMapper.map(it.getString(it.getColumnIndex("trackTime")))

                            val values = ContentValues().apply {
                                put(DbConstants.TRACK_ID, it.getInt(it.getColumnIndex(DbConstants.TRACK_ID)))
                                put("trackName", it.getString(it.getColumnIndex("trackName")))
                                put("artistName", it.getString(it.getColumnIndex("artistName")))
                                put(DbConstants.TRACK_TIME, transformedTime)
                                put("imageUrl", it.getString(it.getColumnIndex("imageUrl")))
                                put("coverUrl", it.getStringOrNull(it.getColumnIndex("coverUrl")))
                                put("collectionName", it.getString(it.getColumnIndex("collectionName")))
                                put("year", it.getString(it.getColumnIndex("year")))
                                put("primaryGenreName", it.getString(it.getColumnIndex("primaryGenreName")))
                                put("country", it.getString(it.getColumnIndex("country")))
                                put("previewUrl", it.getString(it.getColumnIndex("previewUrl")))
                            }

                            db.insert(tracksTempTable, conflictAlgorithm = CONFLICT_REPLACE, values)

                        } while (it.moveToNext())
                    }
                }
                db.execSQL("DROP TABLE ${DbConstants.TRACKS_TABLE};")
                db.execSQL("ALTER TABLE ${tracksTempTable} RENAME TO  ${DbConstants.TRACKS_TABLE};")

                db.execSQL("""
                        CREATE TABLE IF NOT EXISTS ${tracksPlaylistTempTable} (
                            ${DbConstants.TRACK_PLAYLIST_PRIMARY_KEY} INTEGER NOT NULL PRIMARY KEY,
                            ${DbConstants.TRACK_ID} INTEGER NOT NULL,
                            ${DbConstants.PLAYLIST_PRIMARY_KEY} INTEGER NOT NULL
                        );
                    """
                    .trimIndent())
                db.execSQL("""
                        INSERT INTO ${tracksPlaylistTempTable} (
                        `${DbConstants.TRACK_ID}`,
                        `${DbConstants.PLAYLIST_PRIMARY_KEY}`)
                        SELECT 
                        `${DbConstants.TRACK_ID}`,
                        `${DbConstants.PLAYLIST_PRIMARY_KEY}`
                        FROM ${DbConstants.TRACK_PLAYLIST_TABLE};
                    """
                    .trimIndent())

                db.execSQL("DROP TABLE ${DbConstants.TRACK_PLAYLIST_TABLE};")
                db.execSQL("ALTER TABLE ${tracksPlaylistTempTable} RENAME TO  ${DbConstants.TRACK_PLAYLIST_TABLE};")
            }
        }

    }
}


