package com.almuwahhid.themoviedb.app.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.almuwahhid.themoviedb.R
import com.almuwahhid.themoviedb.databinding.AdapterCarouselBinding
import com.almuwahhid.themoviedb.resources.GlobalConfig
import com.almuwahhid.themoviedb.resources.data.entity.FavMovie
import com.almuwahhid.themoviedb.resources.util.base.BaseAdapter
import com.almuwahhid.themoviedb.resources.util.base.BaseViewHolder
import com.almuwahhid.themoviedb.resources.util.ext.bounceEffect
import com.bumptech.glide.Glide

class CarouselAdapter : BaseAdapter<FavMovie, AdapterCarouselBinding, OnCarouselClick>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterCarouselBinding
        get() = AdapterCarouselBinding::inflate

    override fun itemViewHolder(v: AdapterCarouselBinding): RecyclerView.ViewHolder {
        return CarouselViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        index: Int,
        count: Int,
        item: FavMovie,
        callback: OnItemClick<OnCarouselClick, FavMovie>?
    ) {
        (holder as CarouselViewHolder).onBind(index, count, item, callback)
    }

    override fun onBindLoading(holder: RecyclerView.ViewHolder) {
        (holder as CarouselViewHolder).onBindLoading()
    }

    class CarouselViewHolder(val binding: AdapterCarouselBinding) : BaseViewHolder<OnCarouselClick, FavMovie>(binding) {
        override fun onBind(
            index: Int,
            count: Int,
            item: FavMovie,
            callback: OnItemClick<OnCarouselClick, FavMovie>?
        ): Unit = with(binding) {
            adapter.visibility = View.VISIBLE
            loading.visibility = View.GONE

            adapter.setOnClickListener {
                callback?.onClick(OnCarouselClick.OnClick, item)
            }

            btnFav.setOnClickListener {
                btnFav.bounceEffect(context = root.context)
                callback?.onClick(OnCarouselClick.OnFavorited, item)
            }

            if (item.flag) imgFavorite.setImageResource(R.drawable.ic_love_fill)
            else imgFavorite.setImageResource(R.drawable.ic_love_outline)

            Log.d("backdrop ", "${GlobalConfig.IMG_API}${item.backdrop_path}")
            Glide.with(root)
                .load("${GlobalConfig.IMG_API}${item.backdrop_path}")
                .placeholder(R.drawable.ic_movie_placeholder  )
                .into(imgAvatar)

        }

        override fun onBindLoading() = with(binding) {
            adapter.visibility = View.GONE
            loading.visibility = View.VISIBLE
        }
    }

    fun updateCarousel(favMovie: FavMovie) {
        data.get(data.indexOfFirst { it.id == favMovie.id }).flag = favMovie.flag
        notifyDataSetChanged()
    }
}

sealed class OnCarouselClick{
    object OnClick : OnCarouselClick()
    object OnFavorited : OnCarouselClick()
}