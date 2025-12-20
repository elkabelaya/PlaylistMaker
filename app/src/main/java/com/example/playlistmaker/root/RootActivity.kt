package com.example.playlistmaker.root

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import com.example.playlistmaker.R
import com.example.playlistmaker.common.presentation.utils.setupTopInset
import com.example.playlistmaker.databinding.ActivityRootBinding
import com.example.playlistmaker.media.presentation.MediaFragment
import com.example.playlistmaker.player.di.playerModules
import com.example.playlistmaker.search.presentation.SearchFragment
import com.example.playlistmaker.settings.presentation.SettingsFragment
import org.koin.androidx.fragment.android.setupKoinFragmentFactory
import org.koin.androidx.scope.ScopeActivity
import org.koin.core.context.loadKoinModules
import com.example.playlistmaker.root.di.rootModule
import org.koin.core.context.GlobalContext.unloadKoinModules

class RootActivity : ScopeActivity() {
    private lateinit var binding: ActivityRootBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(rootModule)
        enableEdgeToEdge()
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTopInset(this, binding.root)

        if (savedInstanceState == null) {
//            supportFragmentManager.commit {
//                this.add(R.id.rootFragmentContainerView, SettingsFragment())
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(rootModule)
    }
}