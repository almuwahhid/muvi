package com.almuwahhid.themoviedb.app.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.almuwahhid.themoviedb.R
import com.almuwahhid.themoviedb.databinding.AdapterMovieBinding
import com.almuwahhid.themoviedb.resources.GlobalConfig
import com.almuwahhid.themoviedb.resources.data.entity.Movie
import com.almuwahhid.themoviedb.resources.util.base.BaseAdapter
import com.almuwahhid.themoviedb.resources.util.base.BaseViewHolder
import com.almuwahhid.themoviedb.resources.util.base.OnClickAction
import com.bumptech.glide.Glide

// TODO : BaseAdapter with databinding supported
class MovieAdapter : BaseAdapter<Movie, AdapterMovieBinding, OnClickAction>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterMovieBinding
        get() = AdapterMovieBinding::inflate

    override fun itemViewHolder(v: AdapterMovieBinding): RecyclerView.ViewHolder {
        return MovieViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        index: Int,
        count: Int,
        item: Movie,
        callback: OnItemClick<OnClickAction, Movie>?
    ) {
        (holder as MovieViewHolder).onBind(index, count, item, callback)
    }

    override fun onBindLoading(holder: RecyclerView.ViewHolder) {
        (holder as MovieViewHolder).onBindLoading()
    }

    class MovieViewHolder(val binding: AdapterMovieBinding) : BaseViewHolder<OnClickAction, Movie>(binding) {

        override fun onBindLoading() = with(binding) {
            adapter.visibility = View.GONE
            loading.visibility = View.VISIBLE
        }

        override fun onBind(
            index: Int,
            count: Int,
            item: Movie,
            callback: OnItemClick<OnClickAction, Movie>?
        ) = with(binding) {
            adapter.visibility = View.VISIBLE
            loading.visibility = View.GONE
            tvMovie.text = item.original_title
            tvRate.text = "${item.vote_average}"
            tvOverview.text = item.overview
            Glide.with(root)
                .load("${GlobalConfig.IMG_API}${item.poster_path}")
                .placeholder(R.drawable.ic_movie_placeholder  )
                .into(imgAvatar)

            adapter.setOnClickListener {
                callback?.onClick(OnClickAction.OnClick, item)
            }
        }
    }

}