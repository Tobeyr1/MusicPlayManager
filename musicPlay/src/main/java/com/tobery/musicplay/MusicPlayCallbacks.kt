package com.tobery.musicplay


abstract class OnMusicPlayProgressListener{

    abstract fun onPlayProgress(currPos: Long, duration: Long)
}

abstract class OnMusicPlayStateListener{

    abstract fun onPlayState(playbackStage: PlayManger)
}