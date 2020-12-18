package com.example.cleanarchitecturemovieapi.presentation.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Movie
import com.example.domain.MovieCallBack
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class HomeViewModel(
    val movie: MovieCallBack
) : ViewModel() {

    val responseLiveData: MutableLiveData<List<Movie>> = MutableLiveData()
    fun getMovie() {
        viewModelScope.launch {
            movie.getMovie()
                .catch { e ->
                    Log.i("main", "getPost: ${e.message}")
                }
                .collect { response->
                    responseLiveData.value = response
                }
        }
    }
}