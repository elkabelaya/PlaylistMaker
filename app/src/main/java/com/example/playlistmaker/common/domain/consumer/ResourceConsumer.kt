package com.example.playlistmaker.common.domain.consumer

import com.example.playlistmaker.common.domain.model.Resource

interface ResourceConsumer<T> {
    fun consume(resource: Resource<T>)
}