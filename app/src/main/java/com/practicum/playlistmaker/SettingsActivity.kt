package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.App.Companion.DARK_THEME_KEY
import com.practicum.playlistmaker.App.Companion.DARK_THEME_SETTINGS
import com.practicum.playlistmaker.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "https://practicum.yandex.ru/profile/android-developer/"
            )
            intent.type = "text/plain"
            startActivity(intent)
        }

        binding.buttonSupport.setOnClickListener {
            val message = getString(R.string.message)
            val subject = getString(R.string.subject)
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("horider@yandex.ru"))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.email_to)))
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(shareIntent, "Отправить сообщение"))
        }

        binding.buttonTerms.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://yandex.ru/legal/practicum_offer/")
                )
            )
        }

        val sheredPref = getSharedPreferences(DARK_THEME_SETTINGS, MODE_PRIVATE)

        binding.themeSwitcher.isChecked = sheredPref.getBoolean(DARK_THEME_KEY, false)

        binding.themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)
            sheredPref.edit()
                .putBoolean(DARK_THEME_KEY, binding.themeSwitcher.isChecked)
                .apply()
        }
    }
}