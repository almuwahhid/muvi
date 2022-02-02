package com.almuwahhid.themoviedb.app.detail.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.almuwahhid.themoviedb.R
import com.almuwahhid.themoviedb.databinding.AdapterAvatarBinding
import com.almuwahhid.themoviedb.resources.GlobalConfig
import com.almuwahhid.themoviedb.resources.util.base.BaseAdapter
import com.almuwahhid.themoviedb.resources.util.base.BaseViewHolder
import com.almuwahhid.themoviedb.resources.util.base.OnClickAction
import com.bumptech.glide.Glide

class AvatarAdapter : BaseAdapter<String, AdapterAvatarBinding, OnClickAction>(){
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> AdapterAvatarBinding
        get() = AdapterAvatarBinding::inflate

    override fun itemViewHolder(v: AdapterAvatarBinding): RecyclerView.ViewHolder {
        return AvatarViewHolder(v)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        index: Int,
        count: Int,
        item: String,
        callback: OnItemClick<OnClickAction, String>?
    ) {
        (holder as AvatarViewHolder).onBind(index, count, item, callback)
    }

    override fun onBindLoading(holder: RecyclerView.ViewHolder) {

    }

    class AvatarViewHolder(val binding : AdapterAvatarBinding) : BaseViewHolder<OnClickAction, String>(binding) {
        override fun onBind(index: Int, count: Int, item: String, callback: OnItemClick<OnClickAction, String>?): Unit = with(binding) {
            Glide.with(root)
                .load("${GlobalConfig.IMG_API}$item")
                .placeholder(R.drawable.ic_movie_placeholder  )
                .into(imgAvatar)
        }

        override fun onBindLoading() {

        }

    }
}