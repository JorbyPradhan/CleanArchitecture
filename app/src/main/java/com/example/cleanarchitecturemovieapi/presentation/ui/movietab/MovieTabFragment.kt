package com.example.cleanarchitecturemovieapi.presentation.ui.movietab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cleanarchitecturemovieapi.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_movie_tab.*
import kotlinx.android.synthetic.main.fragment_tab_series.*

class MovieTabFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_tab, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tab_movie_Layout.addTab(tab_movie_Layout.newTab().setText("Movie Watch"))
        tab_movie_Layout.addTab(tab_movie_Layout.newTab().setText("Movie Watch & Download"))
        tab_movie_Layout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = MyMovieAdapter(requireActivity(), childFragmentManager,
            tab_movie_Layout.tabCount)
        viewMoviePager.adapter = adapter
        viewMoviePager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_movie_Layout))
        tab_movie_Layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewMoviePager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}