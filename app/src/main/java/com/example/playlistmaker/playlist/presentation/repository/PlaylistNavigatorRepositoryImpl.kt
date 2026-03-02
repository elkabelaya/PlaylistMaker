package com.example.playlistmaker.playlist.presentation.repository

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.editplaylist.presentation.EditPlaylistFragment
import com.example.playlistmaker.player.domain.repository.PlayerNavigatorRepository
import com.example.playlistmaker.player.presentation.PlayerFragment
import com.example.playlistmaker.playlist.domain.repository.PlaylistNavigatorRepository

class PlaylistNavigatorRepositoryImpl(val context: Context): PlaylistNavigatorRepository {


    override fun getPlayListEdit(playlist: Playlist): Navigation {
        return Navigation(R.id.action_playlist_editPlaylistFragment, EditPlaylistFragment.createArgs(playlist))
    }

    override fun getPlayer(track: Track): Navigation {
        return Navigation(R.id.action_playlist_playerFragment, PlayerFragment.createArgs(track))
    }
}