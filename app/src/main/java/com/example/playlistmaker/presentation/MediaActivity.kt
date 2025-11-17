package com.example.playlistmaker.presentation

import android.os.Bundle
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMainBinding
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.presentation.utils.AppCompatActivityWithToolBar
import com.example.playlistmaker.presentation.utils.setupTopInset


class MediaActivity : AppCompatActivityWithToolBar() {
    lateinit var binding: ActivityMediaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar(getResources().getString(R.string.main_media), binding.root, binding.toolbar)
    }
}