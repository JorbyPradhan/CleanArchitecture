package com.example.cleanarchitecturemovieapi.presentation.ui.seriestab

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.cleanarchitecturemovieapi.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_tab_series.*

class TabSeriesFragment : Fragment() {

    private lateinit var navController : NavController
      override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_series, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tabLayout.addTab(tabLayout.newTab().setText("Series Watch"))
        tabLayout.addTab(tabLayout.newTab().setText("Series Watch & Download"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = MySeriesAdapter(requireActivity(), childFragmentManager,
            tabLayout.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

}