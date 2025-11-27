package com.example.playlistmaker.domain.model

import java.io.Serializable

data class Navigation (
    val activity: Class<*>,
    val key: String,
    val data: Serializable
)
