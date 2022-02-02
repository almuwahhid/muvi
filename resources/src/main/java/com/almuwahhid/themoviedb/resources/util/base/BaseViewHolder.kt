package com.almuwahhid.themoviedb.resources.util.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<V, T>(v : ViewBinding) :
    RecyclerView.ViewHolder(v.root) {
    abstract fun onBind(index: Int, count: Int, item: T, callback: BaseAdapter.OnItemClick<V, T>?)
    abstract fun onBindLoading()
}