package com.example.playlistmaker.common.data.model

import com.google.gson.annotations.SerializedName

data class TrackDto(
    val trackName: String, // Название композиции, не отображаем трек в списке, если нет поля
    val artistName: String?, // Имя исполнителя
    @SerializedName("trackTimeMillis")
    val trackTime: Long?, // Продолжительность трека
    @SerializedName("artworkUrl100")
    val imageUrl: String?, // Ссылка на изображение обложки
    val collectionName: String?, // Название альбома
    val releaseDate: String?, // Год релиза трека
    val primaryGenreName: String?, // Жанр трека
    val country: String?, // Страна исполнителя
    val previewUrl: String? // Ссылка на отрывок трека
)

data class TracksDto(
    val results: List<TrackDto>
)