package com.example.playlistmaker.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.domain.model.Email
import com.example.playlistmaker.domain.model.Navigation
import com.example.playlistmaker.domain.repository.NavigatorRepository

class NavigatorRepositoryImpl(private val context: Context): NavigatorRepository {
    override fun navigateTo(navigation: Navigation) {
        val displayIntent = Intent(context, navigation.activity)
        displayIntent.putExtra(navigation.key, navigation.data)
        context.startActivity(displayIntent)
    }

    override fun shareLink(link: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/html")
        intent.putExtra(
            Intent.EXTRA_TEXT,link
        )
        context.startActivity(intent)
    }

    override fun openWeb(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(link))
        context.startActivity(intent)
    }

    override fun openEmail(email: Email) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:${email.recipient}?subject=${email.subject}&body=${email.body}"));
        context.startActivity(intent)
    }
}