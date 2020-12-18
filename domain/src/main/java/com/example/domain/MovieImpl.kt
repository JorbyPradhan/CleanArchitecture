package com.example.domain

import com.example.data.model.Movie
import com.example.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow


class MovieImpl(
    private val repo : MovieRepository
) : MovieCallBack{
    override fun getMovie(): Flow<List<Movie>> {
        return repo.getPost()
    }

}