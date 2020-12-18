package com.example.data.repository

import com.example.data.model.Movie
import com.example.data.network.MovieApi
import com.example.data.network.SafeApiRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class MovieRepository(
    private val api : MovieApi
) : SafeApiRequest(){

    fun getPost(): Flow<List<Movie>> = flow {
        val response = apiRequest{api.getPopularMovie()}
        emit(response.results)
    }.flowOn(Dispatchers.IO)

  /*  suspend fun getMovieList() : LiveData<List<Movie>>{
        val movies : MutableLiveData<List<Movie>> = MutableLiveData()
        val response = apiRequest{api.getPopularMovie()}
        movies.postValue(response.results)
        return movies
    }*/

}