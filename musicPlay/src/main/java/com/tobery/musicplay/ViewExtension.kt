package com.tobery.musicplay

import android.util.Log

//日志工具
fun Any.printLog() {
    Log.i(this.javaClass.simpleName, toString())
}