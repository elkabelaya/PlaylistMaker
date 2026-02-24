package com.example.playlistmaker.newplaylist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.utils.FragmentWithToolBar
import com.example.playlistmaker.common.presentation.utils.onTextChanged
import com.example.playlistmaker.common.presentation.utils.showAppToast
import com.example.playlistmaker.databinding.FragmentNewplaylistBinding
import com.example.playlistmaker.newplaylist.di.newplaylistModules
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.GlobalContext.loadKoinModules
import org.koin.core.context.GlobalContext.unloadKoinModules


class NewPlaylistFragment : FragmentWithToolBar() {

    private var _binding: FragmentNewplaylistBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewPlaylistViewModel by viewModel()

    private val pickMedia = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        viewModel.addImage(uri)
    }
    private lateinit var closeDialog: MaterialAlertDialogBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(newplaylistModules)
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(newplaylistModules)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewplaylistBinding.inflate(inflater, container, false)
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
            getString(R.string.newplaylist_title),
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

        binding.name.hint = getString(R.string.newplaylist_name)
        binding.name.input.onTextChanged {
            viewModel.changeName(it)
        }

        binding.description.hint = getString(R.string.newplaylist_description)
        binding.description.input.onTextChanged {
            viewModel.changeDescription(it)
        }

        binding.createButton.setOnClickListener {
            viewModel.save()
            close()

            showAppToast(requireContext(),
                getString(R.string.newplaylist_toast, binding.name.input.text.toString().trim())
            )
        }
    }

    private fun setupViewModel() {
        viewModel.observeState().observe(viewLifecycleOwner) { state ->
            if (state.imageUrl.isNullOrEmpty()) {
               binding.imageView.isVisible = false
               binding.addPicture.isVisible = true
            } else {
                binding.imageView.isVisible = true
                binding.addPicture.isVisible = false
                Glide.with(binding.imageView)
                    .load(state.imageUrl)
                    .placeholder(R.drawable.bg_placeholder)
                    .transform(CenterCrop(), RoundedCorners(binding.imageView.resources.getDimensionPixelSize(R.dimen.radius_xs)))
                    .into(binding.imageView)
            }
            binding.createButton.isEnabled = state.isSaveEnabled
        }
    }

    private fun setupDialog() {
        closeDialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.newplaylist_dialog_title)
            .setMessage(R.string.newplaylist_dialog_description)
            .setNeutralButton(R.string.newplaylist_dialog_neutral) { dialog, which ->
                //do nothing
            }
            .setPositiveButton(R.string.newplaylist_dialog_positive) { dialog, which ->
                close()
            }
    }

    private fun pickImage() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }



    companion object {

        fun createArgs(): Bundle = Bundle()
    }

}