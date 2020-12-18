package com.example.cleanarchitecturemovieapi.presentation.ui.detail

import com.example.cleanarchitecturemovieapi.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.episode_number.*

class SeriesEpisodeAdapter(
    val num : String
):Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.txt_epi_num.text = num
    }

    override fun getLayout() = R.layout.episode_number
}