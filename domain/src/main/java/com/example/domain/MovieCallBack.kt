package com.example.domain

import com.example.data.model.Movie
import kotlinx.coroutines.flow.Flow


interface MovieCallBack {
    fun getMovie(): Flow<List<Movie>>
}