package com.example.playlistmaker.media.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.utils.AppCompatActivityWithToolBar
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.databinding.FragmentSearchBinding
import com.example.playlistmaker.media.di.mediaModules
import com.example.playlistmaker.media.domain.model.MediaTabType
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.context.loadKoinModules

class MediaFragment : Fragment() {

    private var _binding: ActivityMediaBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(mediaModules)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ActivityMediaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setContentView(binding.root)
//        setupToolBar(getResources().getString(R.string.main_media), binding.root, binding.toolbar)
        setupTabs()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabMediator.detach()
        unloadKoinModules(mediaModules)
    }
    private fun setupTabs() {
        binding.viewPager.adapter = MediaViewPagerAdapter(
            fragmentManager = parentFragmentManager,
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