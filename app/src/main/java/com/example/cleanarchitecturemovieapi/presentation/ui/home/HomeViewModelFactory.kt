package com.example.cleanarchitecturemovieapi.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.MovieCallBack

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory  (
    private val movie : MovieCallBack
) : ViewModelProvider.NewInstanceFactory(){

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(movie) as T
    }
}