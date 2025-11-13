package com.example.playlistmaker.ui

import android.os.Bundle
import com.example.playlistmaker.R
import com.example.playlistmaker.ui.utils.AppCompatActivityWithToolBar

class MediaActivity : AppCompatActivityWithToolBar() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        setupToolBar(getResources().getString(R.string.main_media))
    }
}