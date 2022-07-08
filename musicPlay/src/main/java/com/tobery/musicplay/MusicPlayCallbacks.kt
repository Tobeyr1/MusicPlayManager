package com.tobery.musicplay

import com.tobery.musicplay.entity.PlayManger

abstract class OnMusicPlayProgressListener{

    abstract fun onPlayProgress(currPos: Long, duration: Long)
}

abstract class OnMusicPlayStateListener{

    abstract fun onPlayState(playbackStage: PlayManger)
}