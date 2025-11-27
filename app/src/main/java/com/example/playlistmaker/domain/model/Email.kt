package com.example.playlistmaker.domain.model

import java.io.Serializable

data class Email(
    val recipient: String,
    val subject: String,
    val body: String
)

