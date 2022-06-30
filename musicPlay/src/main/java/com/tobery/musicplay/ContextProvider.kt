package com.tobery.musicplay

import com.tobery.musicplay.ApplicationContextProvider.Companion.mContext
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import kotlin.jvm.Volatile
import java.lang.IllegalStateException

class ContextProvider private constructor(
    /**
     * 获取上下文
     */
    val context: Context
) {
    val application: Application
        get() = context.applicationContext as Application

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: ContextProvider? = null

        fun get():ContextProvider = instance?: synchronized(this){
            instance?: ContextProvider(mContext!!)
        }
    }
}