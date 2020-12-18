package com.example.cleanarchitecturemovieapi.presentation.ui.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Movie
import com.example.domain.MovieCallBack
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MovieViewModel( val movie: MovieCallBack
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