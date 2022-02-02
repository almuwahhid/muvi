package com.almuwahhid.themoviedb.resources.widget

import android.content.Context
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EndlessLayoutManager(context: Context) : LinearLayoutManager(context, HORIZONTAL, false) {
    private var _position = 0

    internal fun updatePosition() {
        _position = findFirstVisibleItemPosition()
    }

    fun startSliding(size : Int) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            scrollToPosition(_position)
            if(_position == size-1) _position = 0
            else _position += 1
        }
    }
}