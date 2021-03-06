package com.example.cleanarchitecturemovieapi.presentation.ui.series

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
import com.example.cleanarchitecturemovieapi.presentation.ui.movie.MovieAdapter
import com.example.cleanarchitecturemovieapi.presentation.ui.movie.MovieViewModel
import com.example.cleanarchitecturemovieapi.presentation.ui.movie.MovieViewModelFactory
import com.example.data.model.Movie
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.series_download_fragment.*
import kotlinx.android.synthetic.main.series_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SeriesDownloadFragment : Fragment(),KodeinAware {
    override val kodein by kodein()
    companion object {
        fun newInstance() = SeriesDownloadFragment()
    }

    private val factory: MovieViewModelFactory by instance()
    private lateinit var viewModel: SeriesDownloadViewModel
    private lateinit var viewModel1: MovieViewModel
    private lateinit var navController : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.series_download_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel1 = ViewModelProvider(this,factory).get(MovieViewModel::class.java)
        viewModel1.getMovie()
        viewModel1.responseLiveData.observe(viewLifecycleOwner, {
            initRecyclerView(it.toMovieAdapter())
            progress_down_series.visibility = View.GONE
            rec_down_series.visibility = View.VISIBLE
        })
    }

    private fun initRecyclerView(toMovieAdapter: List<MovieAdapter>) {
        val movieAdapter =  GroupAdapter<GroupieViewHolder>().apply {
            addAll(toMovieAdapter)
        }
        rec_down_series.apply {
            setHasFixedSize(true)
            adapter = movieAdapter
        }
        movieAdapter.setOnItemClickListener { item, view ->
            navController = Navigation.findNavController(view)
            val movies = item as MovieAdapter
            val bundle = Bundle().also {
                it.putInt("_id", 0)
                it.putInt("watch_down", 0)
                it.putString("Title",movies.movie.title)
                it.putString("Poster",movies.movie.posterPath)
                it.putDouble("rating",movies.movie.voteAverage)
                it.putString("overView",movies.movie.overview)
                it.putString("release",movies.movie.releaseDate)
                it.putBoolean("adult",movies.movie.adult)
                it.putString("trailer",movies.movie.trailer)
            }
            navController.navigate(R.id.action_nav_series_to_detailFragment,bundle)
        }
    }

    private fun List<Movie>.toMovieAdapter() : List<MovieAdapter>{
        return this.map {
            MovieAdapter(it)
        }
    }

}