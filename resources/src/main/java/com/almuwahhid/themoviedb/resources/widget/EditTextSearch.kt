package com.almuwahhid.themoviedb.resources.widget

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.EditText
import com.almuwahhid.themoviedb.resources.R
import java.util.*

class EditTextSearch : EditText {
    private var drawableRight: Drawable? = null
    private var actionX = 0
    private var actionY:Int = 0

    private var onTextChange: OnTextChange? = null

    constructor(context: Context) : super(context){
        initTextChangedListener()
    }

    constructor(context: Context, attrs: AttributeSet?) :super(context, attrs){
        initTextChangedListener()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :super(context, attrs, defStyleAttr){
        initTextChangedListener()
    }

    fun initChangedListener(onTextChange: OnTextChange?) {
        this.onTextChange = onTextChange
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        drawableRight = right
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var bounds: Rect?
        if (event?.getAction() == MotionEvent.ACTION_DOWN) {
            actionX = event.x.toInt()
            actionY = event.y.toInt()

            drawableRight?.let { drawableRight ->
                bounds = null
                bounds = drawableRight.bounds

                var x: Int
                var y: Int
                val extraTapArea = 13

                x = (actionX + extraTapArea)
                y = (actionY - extraTapArea)

                x = width - x
                if (x <= 0) {
                    x += extraTapArea
                 }
                if (y <= 0) y = actionY


                if (bounds!!.contains(x, y)) {
                    onCloseEvent()
                    event.action = MotionEvent.ACTION_CANCEL
                    return false
                }
                return super.onTouchEvent(event)
            }
        }
        return super.onTouchEvent(event)
    }

    private fun onCloseEvent() {
        setText("")
        setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if(s.toString().isEmpty()) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        } else {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_ep_circle_close_filled, 0)
        }
        onTextChange?.let {
            it.onChangedText(s.toString())
        }
    }

    fun interface OnTextChange {
        fun onChangedText(s: String)
    }

    private fun initTextChangedListener() {
//        addTextChangedListener(null)
    }
}