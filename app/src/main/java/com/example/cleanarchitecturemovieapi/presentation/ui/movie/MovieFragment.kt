package com.example.cleanarchitecturemovieapi.presentation.ui.movie

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.cleanarchitecturemovieapi.R
import com.example.data.model.Movie
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.movie_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MovieFragment : Fragment(),KodeinAware {

    override val kodein by kodein()

    companion object {
        fun newInstance() = MovieFragment()
    }

    private val factory: MovieViewModelFactory by instance()
    private lateinit var viewModel: MovieViewModel
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,factory).get(MovieViewModel::class.java)
        viewModel.getMovie()
        viewModel.responseLiveData.observe(viewLifecycleOwner, {
            initRecyclerView(it.toMovieAdapter())
            progress.visibility = View.GONE
            movie_rec.visibility = View.VISIBLE
        })
    }

    private fun initRecyclerView(toMovieAdapter: List<MovieAdapter>) {
        val movieAdapter =  GroupAdapter<GroupieViewHolder>().apply {
            addAll(toMovieAdapter)
        }
        movie_rec.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
        }
        movieAdapter.setOnItemClickListener { item, view ->
            navController = Navigation.findNavController(view)
            val movies = item as MovieAdapter
            val bundle = Bundle().also {
                it.putInt("_id", 1)
                it.putString("Title",movies.movie.title)
                it.putString("Poster",movies.movie.posterPath)
                it.putDouble("rating",movies.movie.voteAverage)
                it.putString("overView",movies.movie.overview)
                it.putString("release",movies.movie.releaseDate)
                it.putBoolean("adult",movies.movie.adult)
                it.putString("trailer",movies.movie.trailer)
            }
            navController.navigate(R.id.action_nav_movie_to_detailFragment,bundle)
        }
    }

    private fun List<Movie>.toMovieAdapter() : List<MovieAdapter>{
        return this.map {
            MovieAdapter(it)
        }
    }

}