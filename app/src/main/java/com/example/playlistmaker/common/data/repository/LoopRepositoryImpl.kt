package com.example.playlistmaker.common.data.repository

import android.os.Handler
import android.os.Looper
import com.example.playlistmaker.common.domain.repository.LoopRepository

class LoopRepositoryImpl(): LoopRepository {

    private val handler = Handler(Looper.getMainLooper())
    private val token = Object()

    override fun post(call: () -> Unit, delay: Long) {
        handler.postDelayed({ call() }, token,delay,)
    }

    override fun clear() {
        handler.removeCallbacksAndMessages(token)
    }
}