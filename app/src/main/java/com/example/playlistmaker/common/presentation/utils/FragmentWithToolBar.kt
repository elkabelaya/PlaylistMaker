package com.example.playlistmaker.common.presentation.utils

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.playlistmaker.databinding.ToolbarBinding

open class FragmentWithToolBar: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupVerticalInsets(view)
    }

    fun setupToolBar(title: String,
                     hasBack: Boolean = false,
                     toolbar: ToolbarBinding,
                     beforeClose: (()-> Unit)? = null) {
        toolbar.toolBar.title = title
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