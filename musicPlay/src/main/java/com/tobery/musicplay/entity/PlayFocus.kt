package com.tobery.musicplay.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*songInfo : 当前播放的音频信息
audioFocusState：焦点状态，4 个值： STATE_NO_FOCUS -> 当前没有音频焦点 STATE_HAVE_FOCUS -> 所请求的音频焦点当前处于保持状态 STATE_LOSS_TRANSIENT -> 音频焦点已暂时丢失 STATE_LOSS_TRANSIENT_DUCK -> 音频焦点已暂时丢失，但播放时音量可能会降低
playerCommand：播放指令，3 个值： DO_NOT_PLAY -> 不要播放 WAIT_FOR_CALLBACK -> 等待回调播放 PLAY_WHEN_READY -> 可以播放
volume：焦点变化后推荐设置的音量，两个值： VOLUME_DUCK -> 0.2f VOLUME_NORMAL -> 1.0f
*/
@Parcelize
data class PlayFocus(
    var songInfo: MusicInfo?, var audioFocusState: Int, var playerCommand: Int, var volume: Float
) : Parcelable
