package com.tobery.musicplay

import android.Manifest
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.intercept.InterceptCallback
import com.lzx.starrysky.intercept.StarrySkyInterceptor
import com.lzx.starrysky.utils.showToast
import com.tobery.musicplay.util.ContextProvider
import com.tobery.musicplay.util.PermissionChecks
import com.tobery.musicplay.util.printLog

class PermissionInterceptor  : StarrySkyInterceptor(){

    private lateinit var musicPermission: PermissionChecks

    override fun process(songInfo: SongInfo?, callback: InterceptCallback) {
        "进入".printLog()
        if (songInfo == null) {
            callback.onInterrupt("SongInfo is null")
            return
        }
        val hasPermission = SpConstant.HAS_PERMISSION
        if (hasPermission) {
            callback.onNext(songInfo)
            return
        }
        "拦截器获取上下文${ContextProvider.get().context}".printLog()
        "拦截器获取上下文${ContextProvider.get().context.packageName}".printLog()
        musicPermission.requestPermissions(arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            "是否判断权限$it".printLog()
            if (it){
                SpConstant.HAS_PERMISSION = true
                callback.onNext(songInfo)
            }else{
                SpConstant.HAS_PERMISSION = false
                callback.onInterrupt("没有权限，播放失败")
                ContextProvider.get().context.showToast("没有权限，播放失败")
            }
        }
    }

    override fun getTag(): String = "PermissionInterceptor"
}