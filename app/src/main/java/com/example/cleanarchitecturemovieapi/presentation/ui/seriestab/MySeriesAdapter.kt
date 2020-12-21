package com.example.cleanarchitecturemovieapi.presentation.ui.seriestab

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.cleanarchitecturemovieapi.presentation.ui.series.SeriesDownloadFragment
import com.example.cleanarchitecturemovieapi.presentation.ui.series.SeriesFragment

class MySeriesAdapter(var context: Context,
                      fm: FragmentManager,
                      var totalTabs: Int
) :
    FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SeriesFragment.newInstance()
            }
            1 -> {
                SeriesDownloadFragment.newInstance()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}