package com.example.cleanarchitecturemovieapi.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.MovieCallBack

@Suppress("UNCHECKED_CAST")
class MovieViewModelFactory(
    val movie : MovieCallBack
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(movie) as T
    }
}