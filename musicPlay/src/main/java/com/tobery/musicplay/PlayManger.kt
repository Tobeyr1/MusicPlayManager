package com.tobery.musicplay

class PlayManger {
    companion object {
        const val IDLE = "IDLE"            //初始状态，播完完成，停止播放都会回调该状态
        const val PLAYING = "PLAYING"      //开始播放，播放中
        const val SWITCH = "SWITCH"        //切歌
        const val PAUSE = "PAUSE"          //暂停
        const val BUFFERING = "BUFFERING"  //缓冲
        const val ERROR = "ERROR"          //出错
    }
}
