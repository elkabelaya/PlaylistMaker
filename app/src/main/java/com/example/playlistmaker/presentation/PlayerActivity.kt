package com.example.playlistmaker.presentation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.databinding.ActivityPlayerBinding
import com.example.playlistmaker.domain.api.PlayerInteractor
import com.example.playlistmaker.domain.api.PlayerInteractor.Companion.STATE_PAUSED
import com.example.playlistmaker.domain.api.PlayerInteractor.Companion.STATE_PLAYING
import com.example.playlistmaker.domain.api.PlayerInteractor.Companion.STATE_PREPARED
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.presentation.utils.AppCompatActivityWithToolBar

class PlayerActivity : AppCompatActivityWithToolBar() {
    private lateinit var binding: ActivityPlayerBinding
    var isFavorite: Boolean = false


    private val handler = Handler(Looper.getMainLooper())

    var playerInteractor: PlayerInteractor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupToolBar(getResources().getString(R.string.empty_title), binding.root, binding.toolbar)

        val track: Track? = intent.getSerializableExtra(INTENT_KEY) as? Track
        track?.let {
            setupTrack(track)
        }
        track?.previewUrl?.let { url ->
            playerInteractor = Creator.providePlayerInteractor(url) { state ->
                handler.removeCallbacksAndMessages(null)

                when (state){
                    STATE_PREPARED -> {
                        binding.playView.isEnabled = true
                        binding.timeView.text = "00:00"
                        binding.playView.setImageResource(R.drawable.ic_player_play_button)
                    }
                    STATE_PLAYING -> {
                        binding.playView.setImageResource(R.drawable.ic_player_pause_button)

                        handler.postDelayed(
                            object: Runnable {
                                override fun run() {
                                    binding.timeView.text = playerInteractor?.time()
                                    handler.postDelayed(this, PLAYER_DEBOUNCE_DELAY)
                                }
                            },
                            PLAYER_DEBOUNCE_DELAY
                        )
                    }
                   STATE_PAUSED -> {
                       binding.playView.setImageResource(R.drawable.ic_player_play_button)

                   }

                }
            }
        }
        setupButtons()
    }

    override fun onPause() {
        super.onPause()
        playerInteractor?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerInteractor?.release()
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
            playerInteractor?.togglePlay()
        }

        binding.favoriteView.setOnClickListener {
            isFavorite = !isFavorite
            binding.favoriteView.setImageResource( if(isFavorite) R.drawable.ic_player_heart_fill else R.drawable.ic_player_heart_stroke )
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
        private const val PLAYER_DEBOUNCE_DELAY = 300L

    }
}

