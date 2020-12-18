package com.example.cleanarchitecturemovieapi.presentation.ui.home

import com.example.cleanarchitecturemovieapi.R
import com.example.data.model.Movie
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.recent_layout.*


class HomeAdapter(
    val movie : Movie,
) : Item(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.recTitle.text = movie.title
        viewHolder.recStarRating.text = movie.voteAverage.toString()
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${movie.posterPath}").into(viewHolder.rec_poster)
    }


    override fun getLayout(): Int {
        return R.layout.recent_layout
    }

}