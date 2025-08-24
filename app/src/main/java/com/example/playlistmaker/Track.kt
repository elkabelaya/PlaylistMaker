package com.example.playlistmaker

import com.google.gson.annotations.SerializedName

data class Track(
    val trackName: String, // Название композиции, не отображаем трек в списке, если нет поля
    val artistName: String?, // Имя исполнителя
    @SerializedName("trackTimeMillis")
    val trackTime: Int?, // Продолжительность трека
    @SerializedName("artworkUrl100")
    val imageUrl: String? // Ссылка на изображение обложки
)

data class Tracks(
    val results: List<Track?>
)
