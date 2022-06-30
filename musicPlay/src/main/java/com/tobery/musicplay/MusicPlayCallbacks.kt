package com.tobery.musicplay

import com.lzx.starrysky.manager.PlaybackStage

abstract class OnMusicPlayProgressListener{

    abstract fun onPlayProgress(currPos: Long, duration: Long)
}

abstract class OnMusicPlayStateListener{

    abstract fun onPlayState(playbackStage: PlaybackStage)
}