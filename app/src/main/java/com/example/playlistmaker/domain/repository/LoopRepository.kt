package com.example.playlistmaker.domain.repository

interface LoopRepository {
    fun post(call:() -> Unit, delay: Long)
    fun clear()
}