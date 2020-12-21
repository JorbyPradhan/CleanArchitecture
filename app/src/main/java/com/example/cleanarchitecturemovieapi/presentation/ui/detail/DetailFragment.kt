package com.example.cleanarchitecturemovieapi.presentation.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cleanarchitecturemovieapi.R
import com.example.cleanarchitecturemovieapi.presentation.ui.home.HomeAdapter
import com.example.cleanarchitecturemovieapi.presentation.ui.home.HomeViewModel
import com.example.cleanarchitecturemovieapi.presentation.ui.home.HomeViewModelFactory
import com.example.data.model.Movie
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.detail_fragment.*
import kotlinx.android.synthetic.main.series_dialog.view.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DetailFragment : Fragment(),KodeinAware {

    companion object {
        fun newInstance() = DetailFragment()
    }

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()
    private lateinit var title: String
    private lateinit var poster: String
    private var rating: Double = 0.0
    private lateinit var overView: String
    private lateinit var release: String
    private var adult: Boolean = false
    private lateinit var trailer: String
    private var watchDown: Int = -1

  //  private lateinit var viewModel: DetailViewModel
  private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        watchDown = arguments?.getInt("watch_down", -1)!!
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
        viewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        val id = arguments?.getInt("_id", -1)!!

        if (watchDown == 1) {
            movie_downLoad.visibility = View.GONE
            if (id == 0) {
                rec_epi_num.visibility = View.VISIBLE
                txt_display_epi.visibility = View.VISIBLE
                movie_watch.visibility = View.GONE
                movie_downLoad.visibility = View.GONE
                getEpinum()
            }

        } else {
            //download and watch
            if (id == 0) {
                rec_epi_num.visibility = View.VISIBLE
                txt_display_epi.visibility = View.VISIBLE
                movie_watch.visibility = View.GONE
                movie_downLoad.visibility = View.GONE
                getEpinum()
            }
        }
        setData()
        bindUI()
    }

    private fun getEpinum() {
        val num = listOf<String>("1", "2", "3", "4", "5", "6", "7", "8")
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
            customDialog(episodeNum.num, title, watchDown)
            Toast.makeText(context, episodeNum.num, Toast.LENGTH_SHORT).show()
        }
    }

    private fun customDialog(num: String, title: String, watch: Int) {
        val mDialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.series_dialog, null)
        val mBuilder = AlertDialog.Builder(requireContext())
            .setView(mDialogView)
            .setTitle("$title - Episode-$num")
        if (watch == 1) {
            //watch only
            mDialogView.dai_movie_downLoad.visibility = View.GONE
        } else {
            mDialogView.dai_movie_downLoad.visibility = View.VISIBLE
        }

        val alertDialog = mBuilder.show()
        mDialogView.txt_hd.setOnClickListener {
            mDialogView.txt_hd.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_layer)
            mDialogView.txt_sd.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_star_rating)
        }
        mDialogView.txt_sd.setOnClickListener {
            mDialogView.txt_sd.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_layer)
            mDialogView.txt_hd.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_star_rating)
        }
        mDialogView.dai_movie_watch.setOnClickListener {
            mDialogView.dai_movie_watch.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_layer)
            mDialogView.dai_movie_downLoad.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_star_rating)
        }
        mDialogView.dai_movie_downLoad.setOnClickListener {
            mDialogView.dai_movie_downLoad.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_layer)
            mDialogView.dai_movie_watch.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.background_star_rating)
        }
    }

    private fun List<String>.toEpisode(): List<SeriesEpisodeAdapter> {
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
        detail_movie_cover.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.scale_animation)
        detail_movie_release.text = release

        movie_watch.setOnClickListener {
            choosePlayer(trailer)
        }


    }
    private fun bindUI() {
        viewModel.getMovie()
        viewModel.responseLiveData.observe(viewLifecycleOwner, {
            initRecyclerView(it.toHomeAdapter())
        })
    }
    private fun List<Movie>.toHomeAdapter() : List<HomeAdapter>{
        return this.map {
            HomeAdapter(it)
        }
    }

    private fun initRecyclerView(toHomeAdapter: List<HomeAdapter>) {
        val mAdapter = GroupAdapter<GroupieViewHolder>().apply {
            addAll(toHomeAdapter)
        }
        rec_similar.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { item, view ->
            val mItem = item as HomeAdapter
            if (mItem.movie.title != title) {
                poster = item.movie.posterPath
                title = item.movie.title
                rating = item.movie.voteAverage
                overView = item.movie.overview
                release = item.movie.releaseDate
                adult = item.movie.adult
                trailer = item.movie.trailer
                setData()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    parentFragmentManager.beginTransaction().detach(this).commitNow()
                    parentFragmentManager.beginTransaction().attach(this).commitNow()
                } else {
                    parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
                }
            }

        }
    }

    private fun choosePlayer(link :String) {
        val builder : MaterialStyledDialog.Builder = MaterialStyledDialog.Builder(requireContext())
        builder.apply {
            title = "Notice!"
            description= "Choose Player"
            setIcon(R.drawable.ic_cinema)
            isDialogAnimation = true
            positive = "Own Player"
            onPositive {
              /*  Intent(activity,WatchActivity::class.java).also {
                    it.putExtra("Link",link)
                    startActivity(it)
                }*/
            }
            negative = "MX Player"
            onNegative {
                openWithMXPlayer(link)
            }
        }
        val dialog : MaterialStyledDialog = builder.build()
        dialog.show()

    }
    private fun openWithMXPlayer(link: String) {
        val appInstalledOrNot : Boolean = appInstalledOrNot("com.mxtech.videoplayer.ad")
        val appInstalledOrNot2 : Boolean = appInstalledOrNot("com.mxtech.videoplayer.pro")
        val str2 : String
        if (appInstalledOrNot || appInstalledOrNot2){
            val str3 : String
            if (appInstalledOrNot2){
                str2 = "com.mxtech.videoplayer.pro"
                str3 = "com.mxtech.videoplayer.ActivityScreen"
            }else{
                str2 = "com.mxtech.videoplayer.ad"
                str3 = "com.mxtech.videoplayer.ad.ActivityScreen"
            }
            try {
                Intent(Intent.ACTION_VIEW).also {
                    it.setDataAndType(Uri.parse(link),"application/x-mpegURL")
                    it.`package` = str2
                    it.setClassName(str2,str3)
                    startActivity(it)
                }
                return
            }catch (e : Exception){
                e.fillInStackTrace()
                Log.d("errorMx", e.message!!)
                return

            }
        }
        try {
            startActivity(
                Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=com.mxtech.videoplayer.ad"))
            )
        } catch (e2: ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=com.mxtech.videoplayer.ad")))
        }


    }
    private fun appInstalledOrNot(str : String) : Boolean{
        return try{
            activity?.packageManager?.getPackageInfo(str, PackageManager.GET_ACTIVITIES)
            true
        }catch (e : PackageManager.NameNotFoundException){
            false
        }
    }

}