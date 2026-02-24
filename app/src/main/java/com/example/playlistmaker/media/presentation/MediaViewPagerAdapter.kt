package com.example.playlistmaker.media.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.playlistmaker.media.domain.model.MediaTabType
import com.example.playlistmaker.media.presentation.favorites.MediaFavoritesFragment
import com.example.playlistmaker.media.presentation.playlists.MediaPlaylistsFragment

class MediaViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return MediaTabType.entries.count()
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            MediaTabType.FAVORITES.ordinal -> MediaFavoritesFragment.newInstance()
            else -> MediaPlaylistsFragment.newInstance()
        }
    }
}