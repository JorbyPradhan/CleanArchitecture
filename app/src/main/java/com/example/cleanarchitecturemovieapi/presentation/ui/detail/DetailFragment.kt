package com.example.cleanarchitecturemovieapi.presentation.ui.detail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.cleanarchitecturemovieapi.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }
    private lateinit var title: String
    private lateinit var poster: String
    private var rating: Double = 0.0
    private lateinit var overView: String
    private lateinit var release: String
    private var adult: Boolean = false
    private lateinit var trailer :  String

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString("Title", null).toString()
        poster = arguments?.getString("Poster", null).toString()
        rating = arguments?.getDouble("rating", 0.0)!!
        overView = arguments?.getString("overView", null).toString()
        release = arguments?.getString("release", null).toString()
        // mId = arguments?.getInt("id", 0)!!
        adult = arguments?.getBoolean("adult", false)!!
        //  it.putString("trailer",movies.movie.trailer)
        trailer = arguments?.getString("trailer", null).toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        val id = arguments?.getInt("_id",-1)!!
        if (id == 0) {
            rec_epi_num.visibility = View.VISIBLE
            txt_display_epi.visibility = View.VISIBLE
            movie_watch.visibility = View.GONE
            movie_downLoad.visibility = View.GONE
            getEpinum()
        }
        setData()
    }
    private fun getEpinum() {
        val num = listOf<String>("1","2","3","4","5","6","7","8")
        initEpiRecyclerView(num.toEpisode())
        //val mAdapter =
    }
    private fun initEpiRecyclerView(toEpisode: List<SeriesEpisodeAdapter>) {
        val mAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(toEpisode)
        }
        rec_epi_num.apply {
            setHasFixedSize(true)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { item, view ->
            val episodeNum = item as SeriesEpisodeAdapter
           // customDialog(episodeNum.num,title)
            Toast.makeText(context, episodeNum.num,Toast.LENGTH_SHORT).show()
        }
    }
    private fun List<String>.toEpisode() : List<SeriesEpisodeAdapter>{
        return this.map {
            SeriesEpisodeAdapter(it)
        }
    }
    private fun setData() {
        Glide.with(requireActivity())
            .load("https://image.tmdb.org/t/p/w500/${poster}")
            .error(R.mipmap.ic_launcher)
            .into(detail_movie_cover)

        Glide.with(requireActivity())
            .load("https://image.tmdb.org/t/p/w500/${poster}")
            .error(R.mipmap.ic_launcher)
            .into(detail_movie_img)
        detail_movie_title.text = title
        detail_textStarRating.text = "$rating"
        detail_movie_desc.text = overView
        detail_movie_cover.animation = AnimationUtils.loadAnimation(requireContext(),R.anim.scale_animation)
        detail_movie_release.text = release
    }

}