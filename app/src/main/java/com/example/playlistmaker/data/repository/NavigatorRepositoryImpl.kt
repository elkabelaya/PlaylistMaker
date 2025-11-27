package com.example.playlistmaker.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.domain.repository.NavigatorRepository
import com.example.playlistmaker.player.presentation.PlayerActivity
import java.io.Serializable

class NavigatorRepositoryImpl(private val context: Context): NavigatorRepository {
    override fun navigateTo(activity: Class<*>, data: Serializable) {
        val displayIntent = Intent(context, activity)
        displayIntent.putExtra(PlayerActivity.INTENT_KEY, data)
        context.startActivity(displayIntent)
    }

    override fun shareLink(link: String) {
        TODO("Not yet implemented")
    }

    override fun openWeb(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(link))
        context.startActivity(intent)
    }

    override fun openEmail(email: String) {
        TODO("Not yet implemented")
    }
}