package com.example.playlistmaker.common.presentation.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity.INPUT_METHOD_SERVICE
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

fun hideKeyboardFrom(context: Context, view: View) {
    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun setupTopInset(activity: ComponentActivity, view: View) {
    activity.enableEdgeToEdge()
    ViewCompat.setOnApplyWindowInsetsListener(view) { view, insets ->
        val statusBar = insets.getInsets(WindowInsetsCompat.Type.statusBars())
        view.updatePadding(top = statusBar.top)
        insets
    }
}

fun dpToPx(dp: Float, context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics).toInt()
}

