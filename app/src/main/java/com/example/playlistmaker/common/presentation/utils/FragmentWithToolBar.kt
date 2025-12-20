package com.example.playlistmaker.common.presentation.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.databinding.ToolbarBinding

open class FragmentWithToolBar: Fragment() {
    fun setupToolBar(title: String, hasBack: Boolean = false, toolbar: ToolbarBinding) {
        toolbar.toolBar.title = title
        if (hasBack) {
            toolbar.toolBar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        } else {
            toolbar.toolBar.navigationIcon = null
        }

    }
}