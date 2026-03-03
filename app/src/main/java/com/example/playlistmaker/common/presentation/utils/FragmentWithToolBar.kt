package com.example.playlistmaker.common.presentation.utils

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ToolbarBinding

open class FragmentWithToolBar: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVerticalInsets(view)
    }

    fun setupToolBar(title: String,
                     hasBack: Boolean = false,
                     toolbar: ToolbarBinding,
                     tintColorId: Int? = null,
                     beforeClose: (()-> Unit)? = null) {
        toolbar.toolBar.title = title
        tintColorId?.let {
             val color = getColor(requireContext(), tintColorId)
             toolbar.toolBar.setTitleTextColor(color)
             toolbar.toolBar.navigationIcon?.setTint(color)
         }
        if (hasBack) {
            toolbar.toolBar.setNavigationOnClickListener {
                if (beforeClose != null) {
                    beforeClose()
                } else {
                    close()
                }
            }
        } else {
            toolbar.toolBar.navigationIcon = null
        }
    }

    fun close() {
        findNavController().navigateUp()
    }

}