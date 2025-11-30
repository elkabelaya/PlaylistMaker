package com.example.playlistmaker.player.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.player.domain.api.PlayerState
import com.example.playlistmaker.common.presentation.utils.AppCompatActivityWithToolBar
import com.example.playlistmaker.player.di.playerModules
import com.example.playlistmaker.search.di.searchModules
import com.example.playlistmaker.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules
import kotlin.getValue

class PlayerActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivityPlayerBinding
    private val viewModel: PlayerViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(playerModules)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar(getResources().getString(R.string.empty_title), binding.root, binding.toolbar)
        val track: Track? = intent.getSerializableExtra(INTENT_KEY) as? Track
        track?.let {
            setupTrack(track)
        }
        setupViewModel(track?.previewUrl)
        setupButtons()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
        unloadKoinModules(playerModules)
    }

    fun setupViewModel(url: String?) {
        viewModel.setup(url)
        viewModel.observePlayerState().observe(this) {
            when (it){
                is PlayerState.Default -> {
                    binding.playView.isEnabled = false
                    binding.timeView.text = it.time
                    binding.playView.setImageResource(R.drawable.ic_player_play_button)
                }
                is PlayerState.Prepared -> {
                    binding.playView.isEnabled = true
                    binding.timeView.text = it.time
                    binding.playView.setImageResource(R.drawable.ic_player_play_button)
                }
                is PlayerState.Playing -> {
                    binding.timeView.text = it.time
                    binding.playView.setImageResource(R.drawable.ic_player_pause_button)
                }
                is PlayerState.Paused -> {
                    binding.timeView.text = it.time
                    binding.playView.setImageResource(R.drawable.ic_player_play_button)
                }
            }
        }
    }
    fun setupTrack(track: Track) {
        Glide.with(binding.imageView)
            .load(track.coverUrl)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .transform(RoundedCorners(binding.imageView.resources.getDimensionPixelSize(R.dimen.radius_m)))
            .into(binding.imageView)

        setTextOrHide(null, binding.title,track.trackName)
        setTextOrHide(null, binding.artist, track.artistName)
        setTextOrHide(binding.durationLabel, binding.duration, track.trackTime)
        setTextOrHide(binding.albumLabel, binding.album, track.collectionName)
        setTextOrHide(binding.yearLabel, binding.year, track.year)
        setTextOrHide(binding.genreLabel, binding.genre, track.primaryGenreName)
        setTextOrHide(binding.countryLabel, binding.country, track.country)
    }

    fun setupButtons() {
        binding.addView.setOnClickListener {
            //do nothing by now
        }

        binding.playView.isEnabled = false
        binding.playView.setOnClickListener {
            viewModel.togglePlay()
        }

        binding.favoriteView.setOnClickListener {
            viewModel.toggleFavorite()
        }
    }

    fun setTextOrHide(labelView: TextView?, view: TextView, value: String?) {
        if (value?.trim()?.isNotEmpty() == true) {
            view.text = value
        } else {
            if (labelView != null) {
                labelView.visibility = View.GONE
            }
            view.visibility = View.GONE
        }
    }

    companion object {
        const val INTENT_KEY = "INTENT_KEY"
    }
}