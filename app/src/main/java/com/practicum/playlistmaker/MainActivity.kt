package com.practicum.playlistmaker

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.searchButton.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
        binding.mediatekaButton.setOnClickListener {
            startActivity(Intent(this, MediatekaActivity::class.java))
        }
        binding.settingButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
