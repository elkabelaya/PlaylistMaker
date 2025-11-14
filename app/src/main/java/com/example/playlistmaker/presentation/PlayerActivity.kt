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
import com.example.playlistmaker.domain.api.PlayerInteractor
import com.example.playlistmaker.domain.api.PlayerInteractor.Companion.STATE_PAUSED
import com.example.playlistmaker.domain.api.PlayerInteractor.Companion.STATE_PLAYING
import com.example.playlistmaker.domain.api.PlayerInteractor.Companion.STATE_PREPARED
import com.example.playlistmaker.domain.model.Track
import com.example.playlistmaker.presentation.utils.AppCompatActivityWithToolBar

class PlayerActivity : AppCompatActivityWithToolBar() {
    var isFavorite: Boolean = false


    private val handler = Handler(Looper.getMainLooper())

    lateinit var addView: ImageButton
    lateinit var playView: ImageButton
    lateinit var favoriteView: ImageButton
    lateinit var timeView: TextView
    var playerInteractor: PlayerInteractor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupToolBar(getResources().getString(R.string.empty_title))
        val track: Track? = intent.getSerializableExtra(INTENT_KEY) as? Track
        track?.let {
            setupTrack(track)
        }
        track?.previewUrl?.let { url ->
            playerInteractor = Creator.providePlayerInteractor(url) { state ->
                handler.removeCallbacksAndMessages(null)

                when (state){
                    STATE_PREPARED -> {
                        playView.isEnabled = true
                        timeView.text = "00:00"
                        playView.setImageResource(R.drawable.ic_player_play_button)
                    }
                    STATE_PLAYING -> {
                        playView.setImageResource(R.drawable.ic_player_pause_button)

                        handler.postDelayed(
                            object: Runnable {
                                override fun run() {
                                    timeView.text = playerInteractor?.time()
                                    handler.postDelayed(this, PLAYER_DEBOUNCE_DELAY)
                                }
                            },
                            PLAYER_DEBOUNCE_DELAY
                        )
                    }
                   STATE_PAUSED -> {
                       playView.setImageResource(R.drawable.ic_player_play_button)

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
        val imageView = findViewById<ImageView>(R.id.image)
        Glide.with(imageView)
            .load(track.coverUrl)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .transform(RoundedCorners(imageView.resources.getDimensionPixelSize(R.dimen.radius_m)))
            .into(imageView)

        timeView = findViewById<TextView>(R.id.time)
        timeView.text = getString(R.string.player_empty_time)

        setTextOrHide(null, R.id.title,track.trackName)
        setTextOrHide(null, R.id.artist, track.artistName)
        setTextOrHide(R.id.duration_label, R.id.duration, track.trackTime)
        setTextOrHide(R.id.album_label, R.id.album, track.collectionName)
        setTextOrHide(R.id.year_label, R.id.year, track.year)
        setTextOrHide(R.id.genre_label, R.id.genre, track.primaryGenreName)
        setTextOrHide(R.id.country_label, R.id.country, track.country)
    }

    fun setupButtons() {
        addView = findViewById<ImageButton>(R.id.add)
        playView = findViewById<ImageButton>(R.id.play)
        favoriteView = findViewById<ImageButton>(R.id.favorite)

        addView.setOnClickListener {
            //do nothing by now
        }

        playView.isEnabled = false
        playView.setOnClickListener {
            playerInteractor?.togglePlay()
        }

        favoriteView.setOnClickListener {
            isFavorite = !isFavorite
            favoriteView.setImageResource( if(isFavorite) R.drawable.ic_player_heart_fill else R.drawable.ic_player_heart_stroke )
        }
    }

    fun setTextOrHide(labelId: Int?, viewId: Int, value: String?) {
        var labelView:TextView? = null
        if (labelId != null) {
            labelView = findViewById<TextView>(labelId)
        }
        val view = findViewById<TextView>(viewId)
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

