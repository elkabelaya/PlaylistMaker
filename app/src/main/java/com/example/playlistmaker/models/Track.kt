package com.example.playlistmaker.models

import androidx.media3.common.Format
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Track(
    val trackName: String, // Название композиции, не отображаем трек в списке, если нет поля
    val artistName: String?, // Имя исполнителя
    @SerializedName("trackTimeMillis")
    private val trackTime: Int?, // Продолжительность трека
    @SerializedName("artworkUrl100")
    val imageUrl: String?, // Ссылка на изображение обложки
    val collectionName: String?, // Название альбома
    private val releaseDate: String?, // Год релиза трека
    val primaryGenreName: String?, // Жанр трека
    val country: String?, // Страна исполнителя
    val previewUrl: String? // Ссылка на отрывок трека
) : Serializable {
    val formattedTrackTime: String
        get() {
            return try {
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTime)
            } catch (e: Exception) {
                ""
            }
        }

    val year: String
        get() = releaseDate?.substring(0, 4) ?: ""

    val coverUrl: String?
        get() = imageUrl?.replaceAfterLast('/',"512x512bb.jpg")
}

data class Tracks(
    val results: List<Track?>
)
