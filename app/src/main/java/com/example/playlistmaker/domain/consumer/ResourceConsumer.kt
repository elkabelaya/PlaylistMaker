package com.example.playlistmaker.domain.consumer

import com.example.playlistmaker.domain.model.Resource

interface ResourceConsumer<T> {
    fun consume(resource: Resource<T>)
}