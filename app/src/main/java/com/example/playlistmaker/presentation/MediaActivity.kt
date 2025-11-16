package com.example.playlistmaker.presentation

import android.os.Bundle
import com.example.playlistmaker.R
import com.example.playlistmaker.presentation.utils.AppCompatActivityWithToolBar

class MediaActivity : AppCompatActivityWithToolBar() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        setupToolBar(getResources().getString(R.string.main_media))
    }
}