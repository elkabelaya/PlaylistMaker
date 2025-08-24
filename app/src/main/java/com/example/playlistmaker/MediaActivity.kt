package com.example.playlistmaker

import android.os.Bundle

class MediaActivity : AppCompatActivityWithToolBar() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        setupToolBar(getResources().getString(R.string.main_media))
    }
}