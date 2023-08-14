package com.practicum.playlistmaker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backButton = findViewById<Button>(R.id.backButtonHeader)
        val shareButton = findViewById<Button>(R.id.shareButton)
        val supportButton = findViewById<Button>(R.id.supportButton)
        val userAgreementButton = findViewById<Button>(R.id.userAgreementButton)

        backButton.setOnClickListener {
            finish()
        }

        shareButton.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Playlist Maker")
            val shareMessage = "https://practicum.yandex.ru/profile/android-developer/"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Поделиться приложением"))
        }

        supportButton.setOnClickListener {
            val message = "Спасибо разработчикам и разработчицам за крутое приложение!"
            val subjectMessage = "Сообщение разработчикам и разработчицам приложения Playlist Maker"
            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.type = "text/html"
            shareIntent.putExtra(Intent.EXTRA_EMAIL, "horider@yandex.ru")
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subjectMessage)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(shareIntent, "Отправить сообщение"))
        }

        userAgreementButton.setOnClickListener {
            val linkUserAgreement = "https://yandex.ru/legal/practicum_offer/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkUserAgreement))
            startActivity(intent)
        }
    }
}