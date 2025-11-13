package com.example.playlistmaker.domain.consumer

import Resource

interface ResourceConsumer<T> {
    fun consume(resource: Resource<T>)
}