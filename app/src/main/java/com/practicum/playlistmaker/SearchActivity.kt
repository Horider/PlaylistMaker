package com.practicum.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.practicum.playlistmaker.databinding.ActivitySearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity() {

    private var searchText: String = "" // Глобальная переменная для хранения текста поискового запроса
    private lateinit var binding: ActivitySearchBinding

    private val itunesBaseUrl = "https://itunes.apple.com/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesSearchService = retrofit.create(SearchITunesApi::class.java)

    private val tracks = ArrayList<Track>()
    private val adapter = TrackAdapter()

    private val callback = object : Callback<TracksResponse> {
        override fun onResponse(
            call: Call<TracksResponse>,
            response: Response<TracksResponse>
        ) {
            if (response.code() == 200) {
                tracks.clear()
                if (response.body()?.results?.isNotEmpty() == true) {
                    tracks.addAll(response.body()?.results!!)
                    showSearchResult(StatusSearch.SUCCESS)
                }
                if (tracks.isEmpty()) {
                    showSearchResult(StatusSearch.EMPTY_SEARCH)
                } else {
                    showSearchResult(StatusSearch.SEARCH_FAILURE)
                }
            } else {
                showSearchResult(StatusSearch.SEARCH_FAILURE)
            }
        }

        override fun onFailure(call: Call<TracksResponse>, t: Throwable) {
            tracks.clear()
            showSearchResult(StatusSearch.SEARCH_FAILURE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backButtonHeader.setOnClickListener {
            finish()
        }

        binding.buttonClear.setOnClickListener {
            binding.inputEditText.text.clear()
            hideKeyboard()
            binding.placeholder.visibility = View.GONE
        }

        // Добавление TextWatcher для EditText
        binding.inputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Обновляем глобальную переменную searchText новым значением
                searchText = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                // Если текст пуст, скрываем кнопку очистки, иначе показываем
                binding.buttonClear.visibility =
                    if (s?.isNotEmpty() == true) View.VISIBLE else View.GONE
            }
        })

        adapter.tracks = tracks
        binding.rvSearchList.adapter = adapter

        binding.inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (binding.inputEditText.text.isNotEmpty()) {
                    itunesSearchService.search(binding.inputEditText.text.toString())
                        .enqueue(callback)
                }
                true
            }
            false
        }
        binding.buttonUpdate.setOnClickListener {
            itunesSearchService.search(binding.inputEditText.text.toString()).enqueue(callback)
        }
    }

    companion object {
        const val KEY_EDIT_TEXT_VALUE = "KEY_EDIT_TEXT_VALUE"
    }

    // Сохраняем текущее значение EditText в Bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_EDIT_TEXT_VALUE, binding.inputEditText.text.toString())
    }

    // Восстанавливаем значение EditText из Bundle
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val savedText = savedInstanceState.getString(KEY_EDIT_TEXT_VALUE) ?: ""
        binding.inputEditText.setText(savedText)
    }

    // Функция для скрытия клавиатуры
    private fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun showSearchResult(searchStatus: StatusSearch) {
        when (searchStatus) {
            StatusSearch.EMPTY_SEARCH -> {
                adapter.notifyDataSetChanged()
                binding.placeholder.visibility = View.VISIBLE
                binding.placeholderImage.setImageResource(R.drawable.ic_nothing_found)
                binding.placeholderText.setText(R.string.nothing_found)
                binding.buttonUpdate.visibility = View.GONE
            }

            StatusSearch.SEARCH_FAILURE -> {
                adapter.notifyDataSetChanged()
                binding.placeholder.visibility = View.VISIBLE
                binding.placeholderImage.setImageResource(R.drawable.ic_failure)
                binding.placeholderText.setText(R.string.failure_text)
                binding.buttonUpdate.visibility = View.VISIBLE
            }

            StatusSearch.SUCCESS -> {
                adapter.notifyDataSetChanged()
            }
        }
    }
}