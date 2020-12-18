package com.example.cleanarchitecturemovieapi.presentation.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleanarchitecturemovieapi.R
import com.example.data.model.Movie
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.home_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(),KodeinAware {

    override val kodein by kodein()

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var navController: NavController
    private val factory: HomeViewModelFactory by instance()
    private lateinit var imgSliderAdapter: ImgSliderAdapter
    private lateinit var imgList : ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        imgList = ArrayList()
        imgList.add("https://www.vskills.in/certification/blog/wp-content/uploads/2019/03/Pizza-Hut.png")
        imgList.add("https://image.freepik.com/free-vector/fresh-toothpaste-advertisement-realistic-style_52683-16160.jpg")
        imgList.add("https://www.adgully.com/img/800/202008/sprite-campaign-picture.jpg")

        imgSliderAdapter =  ImgSliderAdapter(imgList,requireContext())
        imageSlider.sliderAdapter = imgSliderAdapter
        imageSlider.setIndicatorAnimation(IndicatorAnimations.THIN_WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        viewModel.getMovie()
        viewModel.responseLiveData.observe(viewLifecycleOwner, {
            initRecyclerView(it.toHomeAdapter())
            txtRecentMovie.visibility = View.VISIBLE
        })
    }

    private fun initRecyclerView(toHomeAdapter: List<HomeAdapter>) {
        val mAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(toHomeAdapter)
        }
        rec_movie.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { item, view ->
            //  val navController = Navigation.findNavController(view)
            navController = Navigation.findNavController(view)
            val movies = item as HomeAdapter
            val bundle = Bundle().also {
                it.putString("Title",movies.movie.title)
                it.putString("Poster",movies.movie.backdropPath)
                it.putDouble("rating",movies.movie.voteAverage)
                it.putString("overView",movies.movie.overview)
                it.putString("release",movies.movie.releaseDate)
                it.putBoolean("adult",movies.movie.adult)
                it.putString("trailer",movies.movie.trailer)
                //    it.putString("runtime",movies.movie.)
            }
            navController.navigate(R.id.action_nav_home_to_detailFragment,bundle)
        }
    }

    private fun List<Movie>.toHomeAdapter():List<HomeAdapter>{
        return this.map {
            HomeAdapter(it)
        }
    }


}