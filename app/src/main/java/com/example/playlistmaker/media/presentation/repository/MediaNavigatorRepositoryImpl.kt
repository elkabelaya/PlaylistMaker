package com.example.playlistmaker.media.presentation.repository

import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Navigation
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.media.domain.repository.MediaNavigatorRepository
import com.example.playlistmaker.editplaylist.presentation.EditPlaylistFragment
import com.example.playlistmaker.player.presentation.PlayerFragment
import com.example.playlistmaker.playlist.presentation.PlaylistFragment

class MediaNavigatorRepositoryImpl(): MediaNavigatorRepository {
    override fun getPlayer(item: Track): Navigation {
        return Navigation(R.id.action_media_playerFragment, PlayerFragment.createArgs(item))
    }

    override fun getPlaylist(item: Playlist): Navigation {
        return Navigation(R.id.action_media_playlistFragment, PlaylistFragment.createArgs(item))
    }


    override fun getNewPlayLst(): Navigation {
        return Navigation(R.id.action_media_newplaylistFragment, EditPlaylistFragment.createArgs())
    }
}