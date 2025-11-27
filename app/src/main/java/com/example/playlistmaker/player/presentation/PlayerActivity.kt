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

class PlayerActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivityPlayerBinding
    private lateinit var viewModel: PlayerViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    fun setupViewModel(url: String?) {
        viewModel= ViewModelProvider(this, PlayerViewModelImpl.getFactory(url))
            .get(PlayerViewModelImpl::class.java)
        viewModel.observePlayerState().observe(this) {
            when (it){
                PlayerState.STATE_DEFAULT -> {
                    binding.playView.isEnabled = false
                    binding.timeView.text = "00:00"
                    binding.playView.setImageResource(R.drawable.ic_player_play_button)
                }
                PlayerState.STATE_PREPARED -> {
                    binding.playView.isEnabled = true
                    binding.timeView.text = "00:00"
                    binding.playView.setImageResource(R.drawable.ic_player_play_button)
                }
                PlayerState.STATE_PLAYING -> {
                    binding.playView.setImageResource(R.drawable.ic_player_pause_button)
                }
                PlayerState.STATE_PAUSED -> {
                    binding.playView.setImageResource(R.drawable.ic_player_play_button)
                }
            }
        }

        viewModel.observeProgressTime().observe(this) {
            binding.timeView.text = it
        }

        viewModel.observeFavorite().observe(this) {
            binding.favoriteView.setImageResource( if(it) R.drawable.ic_player_heart_fill else R.drawable.ic_player_heart_stroke )
        }
    }
    fun setupTrack(track: Track) {
        Glide.with(binding.imageView)
            .load(track.coverUrl)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .transform(RoundedCorners(binding.imageView.resources.getDimensionPixelSize(R.dimen.radius_m)))
            .into(binding.imageView)

        binding.timeView.text = getString(R.string.player_empty_time)

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