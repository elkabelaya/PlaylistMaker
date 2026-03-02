package com.example.playlistmaker.playlist.presentation

import android.R.attr.visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.doOnDetach
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.presentation.TracksAdapter
import com.example.playlistmaker.common.presentation.utils.FragmentWithToolBar
import com.example.playlistmaker.common.presentation.utils.onResult
import com.example.playlistmaker.common.presentation.utils.setup
import com.example.playlistmaker.common.presentation.utils.setupVerticalInsets
import com.example.playlistmaker.common.presentation.utils.showAppToast
import com.example.playlistmaker.databinding.FragmentPlaylistBinding
import com.example.playlistmaker.playlist.di.playlistModules
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.parameter.parametersOf

class PlaylistFragment : FragmentWithToolBar() {
    private var _binding: FragmentPlaylistBinding? = null
    private val binding get() = _binding!!
    private val adapter: TracksAdapter = TracksAdapter(
        { item ->
            viewModel.select(item)
        },
        { item ->
            onConfirmDeleteTrack() {
                viewModel.delete(item)
            }
        }
    )
    private lateinit var tracksBottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var menuBottomSheetBehavior: BottomSheetBehavior<View>
    private val viewModel: PlaylistViewModel by viewModel{
        parametersOf(playlist)
    }
    private val playlist: Playlist? by lazy {
        requireArguments().getSerializable(INTENT_KEY) as? Playlist
    }

    private var emptyLayoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(playlistModules)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar(resources.getString(R.string.empty_title), true, binding.toolbar, R.color.yp_black)
        setupButtons()
        setupList()
        setupEmptyListener()
        setupTracksBottomSheet()
        setupMenuBottomSheet()
        setupViewModel()
    }

    private fun setupEmptyListener() {
        emptyLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            tracksBottomSheetBehavior.peekHeight =  binding.emptyBottom.height
        }
        binding.emptyBottom.viewTreeObserver.addOnGlobalLayoutListener(emptyLayoutListener)
        binding.emptyBottom.doOnDetach {
            it.viewTreeObserver.removeOnGlobalLayoutListener(emptyLayoutListener)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(playlistModules)
    }

    fun setupViewModel() {
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            adapter.tracks = state.tracks
            adapter.notifyDataSetChanged()
            binding.tracksBottomSheet.root.visibility = if (state.tracks.count() > 0) View.VISIBLE else View.GONE
            collapseTracksBottomSheet()

            state.playlist?.let {
                setup(it)
                setupMenuBottomSheet(it)
            }
        }

        viewModel.observeDialogState().observe(viewLifecycleOwner) { title ->
            title?.let {
                showShareError(it)
            }
        }
    }
    fun setup(playlist: Playlist) {
        Glide.with(binding.imageView)
            .load(playlist.coverUrl ?: "")
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .onResult(ready = {
                binding.imageView.setBackgroundColor(getColor(requireContext(), R.color.yp_white))
            })
            .placeholder(R.drawable.bg_placeholder)
            .centerCrop()
            .into(binding.imageView)


        setTextOrHide( binding.title,playlist.name)
        setTextOrHide(binding.year, playlist.description)
        setTextOrHide(binding.time, playlist.duration)
        setTextOrHide(binding.count, playlist.count)
    }

    fun setupMenuBottomSheet(playlist: Playlist) {
        binding.menuBottomSheet.menuCard.apply {
            Glide.with(image)
                .load(playlist.coverUrl ?: "")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.bg_placeholder)
                .transform(CenterCrop(),RoundedCorners( image.resources.getDimensionPixelSize(R.dimen.radius_xxxs)))
                .into(image)
            title.text = playlist.name
            tracksCount.text = playlist.count

        }
    }

    fun setupButtons() {
        binding.shareButton.setOnClickListener {
            viewModel.onShare()
        }

        binding.menuButton.setOnClickListener {
            openMenuBottomSheet()
        }
    }

    private fun setupList() {
        binding.tracksBottomSheet.tracksList.layoutManager = LinearLayoutManager(requireContext())
        binding.tracksBottomSheet.tracksList.adapter = adapter
    }
    fun setupTracksBottomSheet() {
        tracksBottomSheetBehavior = BottomSheetBehavior.from(binding.tracksBottomSheet.root)
        collapseTracksBottomSheet()
        binding.tracksBottomSheet.bottomSheetHandle.button.setOnClickListener {
            collapseTracksBottomSheet()
        }
    }

    fun setupMenuBottomSheet() {
        menuBottomSheetBehavior = BottomSheetBehavior.from(binding.menuBottomSheet.root)
        setup(menuBottomSheetBehavior, binding.menuBottomSheet.bottomSheetHandle.button, binding.overlay.overlay)
        binding.menuBottomSheet.menuShareButton.setOnClickListener {
            hideMenuBottomSheet()
            viewModel.onShare()
        }
        binding.menuBottomSheet.menuEditButton.setOnClickListener {
            hideMenuBottomSheet()
            viewModel.onEdit()
        }
        binding.menuBottomSheet.deleteButton.setOnClickListener {
            hideMenuBottomSheet()
            onConfirmDelete() {
                viewModel.onDelete()
                close()
            }

        }
    }

    private fun collapseTracksBottomSheet() {
        tracksBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun openMenuBottomSheet() {
        menuBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun hideMenuBottomSheet() {
        menuBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun setTextOrHide(view: TextView, value: String?) {
        if (value?.trim()?.isNotEmpty() == true) {
            view.text = value
        } else {
            view.visibility = View.GONE
        }
    }

    private fun onConfirmDeleteTrack(onConfirm: ()-> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.playlist_remove_track)
            .setNeutralButton(R.string.playlist_remove_negative) { dialog, which ->
                //do nothing
            }
            .setPositiveButton(R.string.playlist_remove_positive) { dialog, which ->
                onConfirm()
            }
            .show()
    }

    private fun onConfirmDelete(onConfirm: ()-> Unit) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.playlist_remove_confirm, playlist?.name))
            .setNeutralButton(R.string.playlist_remove_negative) { dialog, which ->
                //do nothing
            }
            .setPositiveButton(R.string.playlist_remove_positive) { dialog, which ->
                onConfirm()
            }
            .show()
    }

    private fun showShareError(title: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(title)
            .setPositiveButton(R.string.playlist_share_error_positive) { dialog, which ->
                //do nothing
            }
            .show()
    }

    companion object {
        const val INTENT_KEY = "INTENT_KEY"

        fun createArgs(playlist: Playlist): Bundle =
            Bundle().apply {
                putSerializable(INTENT_KEY, playlist)
            }
    }
}