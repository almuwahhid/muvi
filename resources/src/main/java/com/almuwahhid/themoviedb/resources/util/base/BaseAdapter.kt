package com.almuwahhid.themoviedb.resources.util.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, R : ViewBinding, V> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var callback: Unit
    protected var data: MutableList<T> = arrayListOf()

    private var _binding: R? = null
    val binding get() = _binding!!

    private var _isloading: Boolean = false
    val isloading get() = _isloading

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> R

    private var onItemClick: OnItemClick<V, T>? = null

    protected abstract fun itemViewHolder(
        v : R
    ): RecyclerView.ViewHolder

    protected abstract fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        index: Int,
        count: Int,
        item: T,
        callback: OnItemClick<V, T>?
    )

    protected abstract fun onBindLoading(
        holder: RecyclerView.ViewHolder
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        _binding = bindingInflater.invoke(LayoutInflater.from(parent.context), parent, false)
        return itemViewHolder(requireNotNull(_binding))
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if(_isloading) {
            onBindLoading(holder)
        } else {
            onBindViewHolder(holder, position, itemCount, get(position), onItemClick)
        }

    }

    override fun getItemCount(): Int {
        if(isloading) return 3
        else return data.size
    }

    fun setItemCallback(callback: (type : V, item: T) -> Unit) {
        onItemClick = object : OnItemClick<V, T> {
            override fun onClick(category: V, item: T) {
                callback(category, item)
            }
        }
    }

    fun add(t: T) {
        data.add(t)
        notifyItemInserted(itemCount - 1)
    }

    fun add(isRefresh : Boolean, t: List<T>) {
        if(isRefresh) data.clear()
        data.addAll(t)
        notifyDataSetChanged()
    }

    fun add(t: T, position: Int) {
        data.add(position, t)
        notifyDataSetChanged()
    }

    fun update(t: T, position: Int) {
        data[position] = t
        notifyItemChanged(position)
    }

    fun initLoading(loading : Boolean) {
        _isloading = loading
        notifyDataSetChanged()
    }

    fun move(from: Int, to: Int) {
        val dataTemp = data[from]
        data.removeAt(from)
        if (to > from) {
            data.add(to, dataTemp)
        } else {
            var newTo = to - 1
            if (newTo == -1) {
                newTo = 0
            }
            data.add(newTo, dataTemp)
        }
        notifyItemMoved(from, to)
    }

    fun addAll(data: List<T>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    operator fun get(position: Int): T {
        return data.get(position)
    }

    fun get(): MutableList<T> {
        return data
    }

    interface OnItemClick<V, T> {
        fun onClick(category : V, item: T)
    }
}

sealed class OnClickAction {
    object OnClick : OnClickAction()
}

