package com.example.playlistmaker.main.presentation.repository

import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.main.domain.repository.MainNavigatorRepository
import com.example.playlistmaker.media.presentation.MediaActivity
import com.example.playlistmaker.search.presentation.SearchActivity
import com.example.playlistmaker.settings.presentation.SettingsActivity

class MainNavigatorRepositoryImpl(): MainNavigatorRepository {
     override fun getSearchActivity(): Navigation {
         return Navigation(SearchActivity::class.java, null, null)
    }

    override fun getMediaActivity(): Navigation {
        return Navigation(MediaActivity::class.java, null, null)
    }

    override fun getSettingsActivity(): Navigation {
        return Navigation(SettingsActivity::class.java, null, null)
    }
}