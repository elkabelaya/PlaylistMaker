package com.example.playlistmaker

import android.os.Bundle
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

class PlayerActivity : AppCompatActivityWithToolBar() {
    var isPlaying: Boolean = false
    var isFavorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupToolBar(getResources().getString(R.string.empty_title))
        val track: Track? = intent.getSerializableExtra(INTENT_KEY) as? Track
        if (track != null) {
            setupTrack(track)
        }
        setupButtons()
    }

    fun setupTrack(track: Track) {
        val imageView = findViewById<ImageView>(R.id.image)
        Glide.with(imageView)
            .load(track.coverUrl)
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .transform(RoundedCorners(imageView.resources.getDimensionPixelSize(R.dimen.radius_m)))
            .into(imageView)

        setTextOrHide(null, R.id.title,track.trackName)
        setTextOrHide(null, R.id.artist, track.artistName)
        setTextOrHide(R.id.duration_label, R.id.duration, track.formattedTrackTime)
        setTextOrHide(R.id.album_label, R.id.album, track.collectionName)
        setTextOrHide(R.id.year_label, R.id.year, track.year)
        setTextOrHide(R.id.genre_label, R.id.genre, track.primaryGenreName)
        setTextOrHide(R.id.country_label, R.id.country, track.country)
    }

    fun setupButtons() {
        val addView = findViewById<ImageButton>(R.id.add)
        val playView = findViewById<ImageButton>(R.id.play)
        val favoriteView = findViewById<ImageButton>(R.id.favorite)
        addView.setOnClickListener {
            //do nothing by now
        }

        playView.setOnClickListener {
            isPlaying = !isPlaying
            playView.setImageResource( if(isPlaying) R.drawable.ic_player_pause_button else R.drawable.ic_player_play_button )
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
    }
}