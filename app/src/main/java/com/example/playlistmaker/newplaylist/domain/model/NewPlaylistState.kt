package com.example.playlistmaker.newplaylist.domain.model

data class NewPlaylistState(val isSaveEnabled: Boolean,
                            val imageUrl: String?,
                            val needDialog: Boolean)

