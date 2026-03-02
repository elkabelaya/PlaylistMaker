package com.example.playlistmaker.common.presentation.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding

fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun setupVerticalInsets(view: View) {
    ViewCompat.setOnApplyWindowInsetsListener(view) { view, insets ->
        val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
        val navigationBar = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
        view.updatePadding(top = statusBar.top, bottom = navigationBar.bottom)
        insets
    }
}

fun dpToPx(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics).toInt()
}



