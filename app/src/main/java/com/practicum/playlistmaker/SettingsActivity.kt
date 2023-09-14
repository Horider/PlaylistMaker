package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.databinding.ActivityMediatekaBinding
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backButtonHeader.setOnClickListener {
            finish()
        }

        binding.shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Playlist Maker")
            val shareMessage = getString(R.string.android_development_course)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Поделиться приложением"))
        }

        binding.supportButton.setOnClickListener {
            val message = getString(R.string.text_letter)
            val subjectMessage = getString(R.string.subject_letter)
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_to)))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subjectMessage)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(shareIntent, "Отправить сообщение"))
        }

        binding.userAgreementButton.setOnClickListener {
            val linkUserAgreement = getString(R.string.practicum_offer)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUserAgreement))
            startActivity(intent)
        }
    }
}