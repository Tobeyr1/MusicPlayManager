package com.tobery.musicplay

import android.annotation.SuppressLint
import com.lzx.starrysky.utils.KtPreferences

@SuppressLint("StaticFieldLeak")
object SpConstant: KtPreferences() {
    var HAS_PERMISSION by booleanPref()
    //播放模式
    const val REPEAT_MODE_NONE = 100     //顺序播放
    const val REPEAT_MODE_ONE = 200      //单曲播放
    const val REPEAT_MODE_SHUFFLE = 300  //随机播放
    const val REPEAT_MODE_REVERSE = 400  //倒序播放

    //
    const val VOLUME_DUCK = 0.2f
    const val VOLUME_NORMAL = 1.0f


    /** 当前没有音频焦点. */
    const val STATE_NO_FOCUS = 0
    /**所请求的音频焦点当前处于保持状态.*/
    const val STATE_HAVE_FOCUS = 1
    /** 音频焦点已暂时丢失. */
    const val STATE_LOSS_TRANSIENT = 2
    /** 音频焦点已暂时丢失，但播放时音量可能会降低 */
    const val STATE_LOSS_TRANSIENT_DUCK = 3
    /** 不要播放 */
    const val DO_NOT_PLAY = -1
    /** 等待回调播放 */
    const val WAIT_FOR_CALLBACK = 0
    /** 可以播放 */
    const val PLAY_WHEN_READY = 1

}