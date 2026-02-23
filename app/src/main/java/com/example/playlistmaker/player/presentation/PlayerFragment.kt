package com.example.playlistmaker.player.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Track
import com.example.playlistmaker.common.presentation.utils.FragmentWithToolBar
import com.example.playlistmaker.common.presentation.utils.showAppToast
import com.example.playlistmaker.databinding.FragmentPlayerBinding
import com.example.playlistmaker.player.di.playerModules
import com.example.playlistmaker.player.domain.model.PlayerState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.parameter.parametersOf

class PlayerFragment : FragmentWithToolBar() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!
    private val adapter: PlayerPlaylistsAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private val viewModel: PlayerViewModel by viewModel{
        parametersOf(track)
    }
    private val track: Track? by lazy {
        requireArguments().getSerializable(INTENT_KEY) as? Track
    }

    init {
        adapter = PlayerPlaylistsAdapter() { item ->
            viewModel.select(item)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(playerModules)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar(resources.getString(R.string.empty_title), true, binding.toolbar)

        track?.let {
            setupTrack(it)
        }

        setupViewModel()
        setupButtons()
        setupList()
        setupBottomSheet()
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

    fun setupViewModel() {
        viewModel.observeState().observe(viewLifecycleOwner) {
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

        viewModel.observeFavorite().observe(viewLifecycleOwner) {
            binding.favoriteView.setImageResource(if (it == true ) R.drawable.ic_player_heart_fill else R.drawable.ic_player_heart_stroke)
        }

        viewModel.observePlaylists().observe(viewLifecycleOwner) {
            adapter.playlists = it
            adapter.notifyDataSetChanged()
        }

        viewModel.observeToast().observe(viewLifecycleOwner) {
            it?.let {
                hideBottomSheet()
                showAppToast(requireContext(), it)
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
        binding.playView.isEnabled = false
        binding.playView.setOnClickListener {
            viewModel.togglePlay()
        }

        binding.favoriteView.setOnClickListener {
            viewModel.toggleFavorite()
        }

        binding.addView.setOnClickListener {
            openBottomSheet()
        }

    }

    private fun setupList() {
        binding.playlists.layoutManager = LinearLayoutManager(requireContext())
        binding.playlists.adapter = adapter
    }
    fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        hideBottomSheet()


        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.overlay.visibility = View.GONE
                    }
                    else -> {
                        binding.overlay.visibility = View.VISIBLE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.overlay.alpha = 1 - slideOffset
            }
        })

        binding.createButton.setOnClickListener {
            viewModel.createPlaylist()
        }

        binding.bottomSheetHandle.button.setOnClickListener {
            hideBottomSheet()
        }

        binding.overlay.setOnClickListener {
            hideBottomSheet()
        }
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun openBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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

        fun createArgs(track: Track): Bundle =
            Bundle().apply {
                putSerializable(INTENT_KEY, track)
            }
    }
}