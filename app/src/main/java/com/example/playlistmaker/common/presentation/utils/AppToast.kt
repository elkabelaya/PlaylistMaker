package com.example.playlistmaker.common.presentation.utils

// Utils.kt
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.example.playlistmaker.R

fun showAppToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.app_toast, null)

    val textView = view.findViewById<TextView>(R.id.toast_text)

    textView.text = message

    val toast = Toast(context)
    toast.duration = duration
    toast.view = view

    toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 0)
    toast.show()
}