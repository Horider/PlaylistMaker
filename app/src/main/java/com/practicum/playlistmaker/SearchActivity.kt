package com.practicum.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var inputEditText: EditText
    private var searchText: String = "" // Глобальная переменная для хранения текста поискового запроса

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        inputEditText = findViewById(R.id.inputEditText)
        val backButton = findViewById<Button>(R.id.backButtonHeader)
        val buttonClear: ImageView = findViewById(R.id.buttonClear)

        backButton.setOnClickListener {
            finish()
        }

        buttonClear.setOnClickListener {
            inputEditText.text.clear()
            hideKeyboard()
            buttonClear.visibility = View.GONE
        }

        // Добавление TextWatcher для EditText
        inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Обновляем глобальную переменную searchText новым значением
                searchText = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // Если текст пуст, скрываем кнопку очистки, иначе показываем
                buttonClear.visibility = if (s?.isNotEmpty() == true) View.VISIBLE else View.GONE
            }
        })
    }

    companion object {
        const val KEY_EDIT_TEXT_VALUE = "KEY_EDIT_TEXT_VALUE"
    }

    // Сохраняем текущее значение EditText в Bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_EDIT_TEXT_VALUE, inputEditText.text.toString())
    }

    // Восстанавливаем значение EditText из Bundle
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedText = savedInstanceState.getString(KEY_EDIT_TEXT_VALUE) ?: ""
        inputEditText.setText(savedText)
    }

    // Функция для скрытия клавиатуры
    private fun hideKeyboard() {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
