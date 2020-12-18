package com.example.data.network

import com.example.data.network.response.MovieResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface MovieApi {

    //  @GET("movie/popular?api_key=7142a40c54b2c690a1f53e697a1d51aa")

    @GET("movieapi")
    suspend fun getPopularMovie(): Response<MovieResponse>//Call<MovieResponse>


    companion object{
        operator fun invoke(

        ) : MovieApi{
            val okHttpClient = OkHttpClient.Builder()
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://raw.githubusercontent.com/JorbyPradhan/Android/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieApi::class.java)
        }
    }
}