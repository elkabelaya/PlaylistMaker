package com.example.playlistmaker.common.domain.repository

interface LoopRepository {
    fun post(call:() -> Unit, delay: Long)
    fun clear()
}