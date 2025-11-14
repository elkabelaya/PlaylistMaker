package com.example.playlistmaker.data.mapper

class ImageUrlMapper {
    fun map(imageUrl: String?): String? {
        return imageUrl?.replaceAfterLast('/',"512x512bb.jpg")
    }
}