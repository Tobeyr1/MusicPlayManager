package com.tobery.musicplay.util

import android.util.Log
import android.view.View
import com.tobery.musicplay.util.ClickEvent.lastClickTime

//日志工具
fun Any.printLog() {
    Log.i(this.javaClass.simpleName, toString())
}

const val VIEW_CLICK_INTERVAL_TIME = 800L

object ClickEvent {
    var lastClickTime: Long = 0L
}

//防恶意点击
fun View.setOnSingleClickListener(clickListener: (view: View) -> Unit) {
    setOnClickListener {
        if (lastClickTime + VIEW_CLICK_INTERVAL_TIME < System.currentTimeMillis()) {
            lastClickTime = System.currentTimeMillis()
            clickListener.invoke(this)
        }
    }
}

interface OnSingleClickListener {

    fun onSingleClick(view: View)
}

fun View.setOnSingleClickListener(clickListener: OnSingleClickListener?) {
    setOnClickListener {
        if (lastClickTime + VIEW_CLICK_INTERVAL_TIME < System.currentTimeMillis()) {
            lastClickTime = System.currentTimeMillis()
            clickListener?.onSingleClick(this)
        }
    }
}