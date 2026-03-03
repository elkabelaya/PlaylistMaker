package com.example.playlistmaker.common.presentation.utils

import android.view.View
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

fun Fragment.setup(behavior: BottomSheetBehavior<View>, handle: View, overlay: View) {
    fun hideBottomSheet() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun openBottomSheet() {
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    hideBottomSheet()

    behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_HIDDEN -> {
                    overlay.visibility = View.GONE
                }
                else -> {
                    overlay.visibility = View.VISIBLE
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            overlay.alpha = 1 - slideOffset
        }
    })


    handle.setOnClickListener {
        hideBottomSheet()
    }

    overlay.setOnClickListener {
        hideBottomSheet()
    }
}