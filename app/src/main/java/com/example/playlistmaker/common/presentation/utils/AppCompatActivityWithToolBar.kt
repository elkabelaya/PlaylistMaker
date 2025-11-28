package com.example.playlistmaker.common.presentation.utils

import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.playlistmaker.databinding.ToolbarBinding

open class AppCompatActivityWithToolBar: AppCompatActivity() {
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
}