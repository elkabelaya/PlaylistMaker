package com.example.playlistmaker.common.domain.repository

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import kotlinx.coroutines.flow.Flow
enum class Permission {
    NOTIFICATIONS
}

interface PermissionsRepository {
    fun save(permitted: Boolean, permission: Permission)
    fun get(permission: Permission): Boolean
}