package com.practicum.playlistmaker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.databinding.ActivityMediatekaBinding

class MediatekaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediatekaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediatekaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}