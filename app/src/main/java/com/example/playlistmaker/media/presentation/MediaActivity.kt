package com.example.playlistmaker.media.presentation

import android.os.Bundle
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.utils.AppCompatActivityWithToolBar
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.media.domain.model.MediaTabType
import com.google.android.material.tabs.TabLayoutMediator

class MediaActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivityMediaBinding
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar(getResources().getString(R.string.main_media), binding.root, binding.toolbar)
        setupTabs()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
    }

    private fun setupTabs() {
        binding.viewPager.adapter = MediaViewPagerAdapter(
            fragmentManager = supportFragmentManager,
            lifecycle = lifecycle
        )
        tabMediator = TabLayoutMediator(binding.tab.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                MediaTabType.FAVORITES.ordinal -> tab.text = getString(R.string.media_tab_favorites)
                MediaTabType.PLAYLISTS.ordinal -> tab.text = getString(R.string.media_tab_playlists)
            }
        }
        tabMediator.attach()
    }
}