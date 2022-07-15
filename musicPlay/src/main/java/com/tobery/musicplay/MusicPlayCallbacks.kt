package com.tobery.musicplay

import com.tobery.musicplay.entity.PlayFocus
import com.tobery.musicplay.entity.PlayManger

abstract class OnMusicPlayProgressListener{

    abstract fun onPlayProgress(currPos: Long, duration: Long)
}

abstract class OnMusicPlayStateListener{

    abstract fun onPlayState(playbackStage: PlayManger)
}

abstract class OnFocusListener{

    abstract fun onFocusChange(focusInfo: PlayFocus)
}

abstract class OnNetWorkChangeListener{

    abstract fun onNetWorkChange(isAvailable: Boolean)
}