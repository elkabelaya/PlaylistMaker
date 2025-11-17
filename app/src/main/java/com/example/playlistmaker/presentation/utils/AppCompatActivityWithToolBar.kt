package com.example.playlistmaker.presentation.utils

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.databinding.ActivityMediaBinding
import com.example.playlistmaker.databinding.ToolbarBinding
import com.example.playlistmaker.domain.use_case.ClickDebounceUseCase
import com.google.android.material.appbar.MaterialToolbar

open class AppCompatActivityWithToolBar: AppCompatActivity(), ClickDebounceUseCase {
    private var clickDebounceUseCase = Creator.provideClickDebounceUseCase()
    fun setupToolBar(title: String, root: View, toolbar: ToolbarBinding) {
        setupTopInset(this, root)

        setSupportActionBar(toolbar.toolBar)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //Для кнопки «Назад» экрана «Настройки» добавить обработчик нажатий.
                //Добавить код перехода на главный экран в обработчик.

                onBackPressed()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun canClickDebounced(): Boolean = clickDebounceUseCase.canClickDebounced()
}