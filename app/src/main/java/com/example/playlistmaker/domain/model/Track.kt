package com.example.playlistmaker.domain.model

import java.io.Serializable

data class Track(
    val trackName: String, // Название композиции, не отображаем трек в списке, если нет поля
    val artistName: String?, // Имя исполнителя
    val trackTime: String, // Продолжительность трека, mm:ss
    val imageUrl: String?, // Ссылка на изображение обложки
    val coverUrl: String?, // Ссылка на полноформатное изображение обложки
    val collectionName: String?, // Название альбома
    val year: String, // Год релиза трека, YYYY
    val primaryGenreName: String?, // Жанр трека
    val country: String?, // Страна исполнителя
    val previewUrl: String? // Ссылка на отрывок трека
) : Serializable

typealias Tracks = List<Track>

