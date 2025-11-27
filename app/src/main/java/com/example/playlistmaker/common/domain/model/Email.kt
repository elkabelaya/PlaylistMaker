package com.example.playlistmaker.common.domain.model

import java.io.Serializable

data class Email(
    val recipient: String,
    val subject: String,
    val body: String
)

