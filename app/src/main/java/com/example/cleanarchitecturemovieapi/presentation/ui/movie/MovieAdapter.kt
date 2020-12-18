package com.example.cleanarchitecturemovieapi.presentation.ui.movie


import com.example.cleanarchitecturemovieapi.R
import com.example.data.model.Movie
import com.squareup.picasso.Picasso
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item.*


class MovieAdapter(
    val movie : Movie
):Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        Picasso.get().load("https://image.tmdb.org/t/p/w500/${movie.posterPath}").into(viewHolder.movie_poster)
        viewHolder.textTitle.text = movie.title
        viewHolder.textStarRating.text = movie.voteAverage.toString()

    }

    override fun getLayout(): Int = R.layout.item
}