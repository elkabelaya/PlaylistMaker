package com.example.playlistmaker.common.data.repository

import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Playlists
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.domain.model.Tracks
import com.example.playlistmaker.common.domain.repository.Permission
import com.example.playlistmaker.common.domain.repository.PermissionsRepository
import kotlinx.coroutines.flow.Flow

class PermissionsRepositoryImpl: PermissionsRepository {
    private var permissions = mutableMapOf<Permission, Boolean>()
    override fun save(permitted: Boolean, permission: Permission) {
        permissions[permission] = permitted
    }

    override fun get(permission: Permission): Boolean {
        return permissions[permission] ?: false
    }
}