package com.tobery.musicplay.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlayManger(var lastSongInfo: MusicInfo? = null,//上一个音频信息（切歌回调时有用）
                      var songInfo: MusicInfo? = null,//当前音频信息
                      var isStop: Boolean = false,//由于播完完成和停止播放都会回调IDEA，如果要做区分的话可以用这个字段，停止就是true，播放完成就是false
                      var errorMsg: String? = null,
                      var stage: String = IDLE
) : Parcelable {
    companion object {
        const val IDLE = "IDLE"            //初始状态，播完完成，停止播放都会回调该状态
        const val PLAYING = "PLAYING"      //开始播放，播放中
        const val SWITCH = "SWITCH"        //切歌
        const val PAUSE = "PAUSE"          //暂停
        const val BUFFERING = "BUFFERING"  //缓冲
        const val ERROR = "ERROR"          //出错
    }
}