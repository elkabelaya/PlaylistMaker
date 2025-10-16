package com.example.playlistmaker

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.transition.Visibility
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.models.Track
import com.example.playlistmaker.utils.AppCompatActivityWithToolBar
import java.text.SimpleDateFormat
import java.util.Locale

class PlayerActivity : AppCompatActivityWithToolBar() {
    var isFavorite: Boolean = false
    private var mediaPlayer = MediaPlayer()
    private var playerState = STATE_DEFAULT
    private val handler = Handler(Looper.getMainLooper())

    lateinit var addView: ImageButton
    lateinit var playView: ImageButton
    lateinit var favoriteView: ImageButton
    lateinit var timeView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupToolBar(getResources().getString(R.string.empty_title))
        val track: Track? = intent.getSerializableExtra(INTENT_KEY) as? Track
        track?.let {
            setupTrack(track)
        }
        track?.previewUrl?.let { url ->
            setupPlayer(url)
        }
        setupButtons()
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        pausePlayer()
        mediaPlayer.release()
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
        setTextOrHide(R.id.duration_label, R.id.duration, track.formattedTrackTime)
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
            when(playerState) {
                STATE_PLAYING -> {
                    pausePlayer()
                }
                STATE_PREPARED, STATE_PAUSED -> {
                    startPlayer()
                }
            }
        }

        favoriteView.setOnClickListener {
            isFavorite = !isFavorite
            favoriteView.setImageResource( if(isFavorite) R.drawable.ic_player_heart_fill else R.drawable.ic_player_heart_stroke )
        }
    }

    private fun setupPlayer(url: String) {
        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playView.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            pausePlayer()
            timeView.text = getString(R.string.player_empty_time)
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

    private fun startPlayer() {
        mediaPlayer.start()
        playView.setImageResource(R.drawable.ic_player_pause_button)
        playerState = STATE_PLAYING

        handler.postDelayed( object: Runnable {
            override fun run() {
                timeView.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
                handler.postDelayed(this, PLAYER_DEBOUNCE_DELAY)
            }
        }
        , PLAYER_DEBOUNCE_DELAY)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playView.setImageResource(R.drawable.ic_player_play_button)
        playerState = STATE_PAUSED
        handler.removeCallbacksAndMessages(null)
    }

    companion object {
        const val INTENT_KEY = "INTENT_KEY"

        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val PLAYER_DEBOUNCE_DELAY = 300L

    }
}

