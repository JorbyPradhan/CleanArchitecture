package com.example.cleanarchitecturemovieapi.presentation.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.cleanarchitecturemovieapi.R
import com.smarteist.autoimageslider.SliderViewAdapter

class ImgSliderAdapter(
    private val imgList :ArrayList<String>,
    private val context : Context) : SliderViewAdapter<ImgSliderAdapter.SliderViewHolder>(){

    class SliderViewHolder(itemView : View) : SliderViewAdapter.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById<ImageView>(R.id.iv_auto_image_slider)
    }

    override fun getCount() = imgList.size

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        return SliderViewHolder(LayoutInflater.from(parent?.context).inflate(
            R.layout.img_slider_layout,parent,false
        ))
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        Glide.with(context!!)
            .load(imgList[position])
            .fitCenter()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(viewHolder!!.imageView)
    }

}
