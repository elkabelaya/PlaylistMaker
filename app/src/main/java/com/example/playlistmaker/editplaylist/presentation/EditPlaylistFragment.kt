package com.example.playlistmaker.editplaylist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.signature.ObjectKey
import com.example.playlistmaker.R
import com.example.playlistmaker.common.domain.model.Playlist
import com.example.playlistmaker.common.presentation.utils.FragmentWithToolBar
import com.example.playlistmaker.common.presentation.utils.observeOnce
import com.example.playlistmaker.common.presentation.utils.onTextChanged
import com.example.playlistmaker.common.presentation.utils.showAppToast
import com.example.playlistmaker.databinding.FragmentEditplaylistBinding
import com.example.playlistmaker.editplaylist.di.editplaylistModules
import com.example.playlistmaker.editplaylist.domain.model.EditPlaylistMode
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules
import org.koin.core.parameter.parametersOf


class EditPlaylistFragment : FragmentWithToolBar() {

    private var _binding: FragmentEditplaylistBinding? = null
    private val binding get() = _binding!!

    private val playlist: Playlist? by lazy {
        requireArguments().getSerializable(INTENT_KEY) as? Playlist
    }
    private val viewModel: EditPlaylistViewModel by viewModel() {
        parametersOf(playlist)
    }

    private val pickMedia = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.addImage(uri)
    }
    private lateinit var closeDialog: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(editplaylistModules)
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(editplaylistModules)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditplaylistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolBar()
        setupViewModel()
        setupFields()
        setupDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolBar() {
        setupToolBar(
            getString(R.string.empty_title),
            true,
            binding.toolbar
        ) {
            if (viewModel.observeState().value?.needDialog == true) {
                closeDialog.show()
            } else {
                close()
            }
        }
    }
    private fun setupFields() {
        binding.addPicture.setOnClickListener { pickImage() }
        binding.imageView.setOnClickListener { pickImage() }

        binding.name.hint = getString(R.string.editplaylist_name)
        binding.name.input.onTextChanged {
            viewModel.changeName(it)
        }

        binding.description.hint = getString(R.string.editplaylist_description)
        binding.description.input.onTextChanged {
            viewModel.changeDescription(it)
        }

        binding.createButton.setOnClickListener {
            viewModel.save()
            close()
            if (viewModel.observeMode().value == EditPlaylistMode.NEW) {
                showAppToast(
                    requireContext(),
                    getString(R.string.editplaylist_created_toast, binding.name.input.text.toString().trim())
                )
            }
        }
    }

    private fun setupViewModel() {
        viewModel.observeMode().observeOnce(viewLifecycleOwner) { mode ->
            when (mode) {
                EditPlaylistMode.NEW -> {
                    binding.toolbar.toolBar.title = getString(R.string.editplaylist_new_title)
                    binding.createButton.setText(getString(R.string.editplaylist_create_button))
                } else -> {
                    binding.toolbar.toolBar.title = getString(R.string.editplaylist_title)
                    binding.createButton.setText(getString(R.string.editplaylist_save_button))
                }
            }
        }

        viewModel.observePlayList().observeOnce(viewLifecycleOwner) { playlist ->
            binding.name.input.setText(playlist.name)
            binding.description.input.setText(playlist.description)
        }

        viewModel.observeImage().observe(viewLifecycleOwner) { url ->
            if (url.isNullOrEmpty()) {
                binding.imageView.isVisible = false
                binding.addPicture.isVisible = true
            } else {
                binding.imageView.isVisible = true
                binding.addPicture.isVisible = false
                Glide.with(binding.imageView)
                    .load(url)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.bg_placeholder)
                    .transform(CenterCrop(), RoundedCorners(binding.imageView.resources.getDimensionPixelSize(R.dimen.radius_xs)))
                    .into(binding.imageView)
            }
        }

        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            binding.createButton.isEnabled = state.isSaveEnabled
        }
    }

    private fun setupDialog() {
        closeDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.editplaylist_dialog_title)
            .setMessage(R.string.editplaylist_unsave_dialog_description)
            .setNeutralButton(R.string.editplaylist_unsave_dialog_neutral) { dialog, which ->
                //do nothing
            }
            .setPositiveButton(R.string.editplaylist_unsave_dialog_positive) { dialog, which ->
                close()
            }
    }

    private fun pickImage() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    companion object {
        const val INTENT_KEY = "INTENT_KEY"

        fun createArgs(): Bundle = Bundle()

        fun createArgs(playList: Playlist): Bundle =
            Bundle().apply {
                putSerializable(INTENT_KEY, playList)
            }
    }

}