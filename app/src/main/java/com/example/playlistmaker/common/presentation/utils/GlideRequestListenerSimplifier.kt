package com.example.playlistmaker.common.presentation.utils

import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.RequestBuilder

fun <T : Any?> RequestBuilder<T>.onResult(
    ready: (() -> Unit)? = null,
    failed: (() -> Unit)? = null
): RequestBuilder<T> = this.listener(object : RequestListener<T> {
    override fun onLoadFailed(
        e: GlideException?,
        model: Any,
        target: Target<T>,
        isFirstResource: Boolean
    ): Boolean {
        failed?.let {it()}
        return false
    }

    override fun onResourceReady(
        resource: T,
        model: Any,
        target: Target<T>,
        dataSource: DataSource,
        isFirstResource: Boolean
    ): Boolean {
        ready?.let {it()}
        return false
    }
})