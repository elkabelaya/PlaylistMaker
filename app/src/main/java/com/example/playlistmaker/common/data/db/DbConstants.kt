package com.example.playlistmaker.common.data.db

class DbConstants {
    companion object {
        const val DB_FILE_NAME = "database.db"
        const val FAVORITE_TRACKS_TABLE_DEPRECATED_1_2 = "favorite_tracks_table"
        const val TRACKS_TABLE = "tracks_table"
        const val TRACK_ID = "track_id"

        const val FAVORITE_TRACK_TABLE = "favorite_track_table"
        const val FAVORITE_TRACK_PRIMARY_KEY = "favorite_track_primary_id"

        const val PLAYLISTS_TABLE = "playlists_table"
        const val PLAYLIST_PRIMARY_KEY = "playlist_primary_id"
        const val TRACK_PLAYLIST_TABLE = "track_playlist_table"

        const val TRACK_FAVORITE_TABLE = "track_favorite_table"
    }

}