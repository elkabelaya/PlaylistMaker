package com.example.playlistmaker.presentation.utils

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.R
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.domain.use_case.ClickDebounceUseCase
import com.google.android.material.appbar.MaterialToolbar

open class AppCompatActivityWithToolBar: AppCompatActivity(), ClickDebounceUseCase {
    private var clickDebounceUseCase = Creator.provideClickDebounceUseCase()

    fun setupToolBar(title: String) {
        setupTopInset(this, R.id.main)

        val toolbar = findViewById<MaterialToolbar>(R.id.tool_bar)
        setSupportActionBar(toolbar)
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