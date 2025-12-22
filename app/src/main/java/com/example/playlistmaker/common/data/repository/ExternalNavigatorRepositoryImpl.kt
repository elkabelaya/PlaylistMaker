package com.example.playlistmaker.common.data.repository

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.playlistmaker.common.domain.model.Email
import com.example.playlistmaker.common.domain.repository.ExternalNavigatorRepository

class ExternalNavigatorRepositoryImpl(
    private val context: Context
): ExternalNavigatorRepository {

    override fun shareLink(link: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.setType("text/html")
        intent.putExtra(
            Intent.EXTRA_TEXT,link
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun openWeb(link: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(link))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override fun openEmail(email: Email) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("mailto:${email.recipient}?subject=${email.subject}&body=${email.body}"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}