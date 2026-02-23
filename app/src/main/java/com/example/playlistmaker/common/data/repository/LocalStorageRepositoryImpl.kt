package com.example.playlistmaker.common.data.repository
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.example.playlistmaker.common.domain.repository.LocalStorageRepository
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import kotlin.io.path.Path

class LocalStorageRepositoryImpl(val context: Context, val folder: String): LocalStorageRepository {

    override fun saveImage(id: Long, uri: Uri): String {
        val fileName = "$id.jpg"
        val file = File(getDir(), fileName)

        Files.deleteIfExists(file.toPath());

        val inputStream = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)

        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

        return file.absolutePath
    }

    override fun deleteImage(url: String) {
        Files.deleteIfExists(Path( url))
    }

    private fun getDir() : File {
        val filePath = File(context.filesDir, folder)
        if (!filePath.exists()){
            filePath.mkdirs()
        }
        return filePath
    }
}