package com.example.playlistmaker.common.data.mapper

class ImageUrlMapper {
    fun map(imageUrl: String?): String? {
        return imageUrl?.replaceAfterLast('/',"512x512bb.jpg")
    }
}