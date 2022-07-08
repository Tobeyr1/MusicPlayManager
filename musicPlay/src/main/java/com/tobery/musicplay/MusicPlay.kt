package com.tobery.musicplay

import android.app.Application
import android.content.Context
import androidx.annotation.FloatRange
import androidx.lifecycle.LifecycleOwner
import com.lzx.starrysky.GlobalPlaybackStageListener
import com.lzx.starrysky.OnPlayProgressListener
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.manager.PlaybackStage
import com.lzx.starrysky.notification.NotificationConfig
import com.tobery.musicplay.MusicPlay.convert
import com.tobery.musicplay.SpConstant.REPEAT_MODE_NONE
import com.tobery.musicplay.entity.MusicCache
import com.tobery.musicplay.entity.MusicInfo
import com.tobery.musicplay.entity.PlayManger
import com.tobery.musicplay.notification.MusicNotificationConfig
import com.tobery.musicplay.util.printLog
import java.io.File

object MusicPlay {

    private var config: PlayConfig? = null

    @JvmStatic
    fun initConfig(context: Context,config: PlayConfig = PlayConfig()){
       val cacheFile = File.createTempFile(config.cacheFilePath,null,context.cacheDir)
       StarrySky.init(context.applicationContext as Application)
           .apply {
               setOpenCache(config.isOpenCache)
               setCacheDestFileDir(cacheFile.absolutePath)
               setCacheMaxBytes(config.cacheByteNum)
               setAutoManagerFocus(config.isAutoManagerFocus)
               setImageLoader(config.defaultImageLoader)
               //addInterceptor(config.defaultPermissionIntercept)
               setNotificationSwitch(config.defaultNotificationSwitch)
               setNotificationType(config.notificationType)
               setNotificationConfig(config.defaultNotificationConfig.convert())
               setNotificationFactory(config.factory)
           }
           .apply()
        setRepeatMode()
        this.config = config
    }

    //进度监听
    @JvmStatic
    fun onPlayProgressListener(callback: OnMusicPlayProgressListener? = null){
        StarrySky.with().setOnPlayProgressListener(object : OnPlayProgressListener{
            override fun onPlayProgress(currPos: Long, duration: Long) {
                callback?.onPlayProgress(currPos,duration)
            }
        })
    }

    //局部状态监听
    @JvmStatic
    fun onPlayStateListener(owner: LifecycleOwner, callback: OnMusicPlayStateListener? = null){
        StarrySky.with().playbackState().observe(owner){
            callback?.onPlayState(it.revert())
        }
    }
    //全局状态监听
    @JvmStatic
    fun setGlobalPlaybackStageListener(callback: OnMusicPlayStateListener? = null){
        StarrySky.setGlobalPlaybackStageListener(object : GlobalPlaybackStageListener{
            override fun onPlaybackStageChange(stage: PlaybackStage) {
                callback?.onPlayState(stage.revert())
            }

        })
    }

    //移动进度
    @JvmStatic
    fun seekTo(progress: Long,isPlayWhenPaused: Boolean = true){
        StarrySky.with().seekTo(progress,isPlayWhenPaused)
    }

    //获取速度 1为正常播放
    @JvmStatic
    fun getPlaySpeed():Int = StarrySky.with().getPlaybackSpeed().toInt()

    //改变语速
    @JvmStatic
    fun changeSpeed(multiple: Float){
        StarrySky.with().onDerailleur(false,multiple)
    }

    //设置音量
    @JvmStatic
    fun setVolume(@FloatRange(from = 0.0,to = 1.0) volume: Float){
        StarrySky.with().setVolume(volume)
    }

    //设置播放模式
    @JvmStatic
    fun setRepeatMode(mode: Int = REPEAT_MODE_NONE,isLoop: Boolean = false){
        StarrySky.with().setRepeatMode(mode,isLoop)
    }

    //获取播放模式
    @JvmStatic
    fun getRepeatMode(): Int = StarrySky.with().getRepeatMode().repeatMode

    //下一首
    @JvmStatic
    fun skipToNext(){
        StarrySky.with().skipToNext()
    }

    //是否有下一首
    @JvmStatic
    fun isSkipToNextEnabled(): Boolean = StarrySky.with().isSkipToNextEnabled()

    //上一首
    @JvmStatic
    fun skipToPrevious(){
        StarrySky.with().skipToPrevious()
    }

    //是否有上一首
    @JvmStatic
    fun isSkipToPreviousEnabled(): Boolean = StarrySky.with().isSkipToPreviousEnabled()

    //播放 by id
    @JvmStatic
    fun playMusicById(songId: String?){
        StarrySky.with().playMusicById(songId)
    }

    //播放 by url
    @JvmStatic
    fun playMusicByUrl(songUrl: String){
        StarrySky.with().playMusicByUrl(songUrl)
    }

    //播放 by SongInfo
    @JvmStatic
    fun playMusicByInfo(info: MusicInfo?){
        StarrySky.with().playMusicByInfo(info?.revert())
    }

    //播放列表以及对应歌曲的下标
    //todo 增加播放列表
    @JvmStatic
    fun playMusicByList(mediaList: MutableList<MusicInfo>, index: Int = 0){
        StarrySky.with().playMusic(mediaList.map { it.revert() }.toMutableList(),index)
    }

    //暂停
    @JvmStatic
    fun pauseMusic(){
        if (StarrySky.with().isPlaying()){
            StarrySky.with().pauseMusic()
        }
    }

    //恢复播放
    @JvmStatic
    fun restoreMusic(){
        if (StarrySky.with().isPaused()){
            StarrySky.with().restoreMusic()
        }
    }

    //重播
    @JvmStatic
    fun replayCurrMusic(){
        StarrySky.with().replayCurrMusic()
    }

    //停止播放
    @JvmStatic
    fun stopMusic(){
        StarrySky.with().stopMusic()
    }

    //当前传入music id 是否播放
    @JvmStatic
    fun isCurrMusicIsPlaying(songId: String?){
        StarrySky.with().isCurrMusicIsPlaying(songId)
    }

    //当前传入music id 是否暂停
    @JvmStatic
    fun isCurrMusicIsPaused(songId: String?){
        StarrySky.with().isCurrMusicIsPaused(songId)
    }

    //获取音乐长度
    @JvmStatic
    fun getDuration():Long = StarrySky.with().getDuration()

    //是否正在播放
    @JvmStatic
    fun isPlaying():Boolean = StarrySky.with().isPlaying()

    //获取当前播放音乐信息
    @JvmStatic
    fun getNowPlayingSongInfo(): MusicInfo? = StarrySky.with().getNowPlayingSongInfo()?.convert()

    //获取当前播放的歌曲songId
    @JvmStatic
    fun getNowPlayingSongId(): String = StarrySky.with().getNowPlayingSongId()

    //获取当前播放的歌曲url
    @JvmStatic
    fun getNowPlayingSongUrl(): String = StarrySky.with().getNowPlayingSongUrl()

    //获取当前播放歌曲的下标
    @JvmStatic
    fun getNowPlayingIndex(): Int = StarrySky.with().getNowPlayingIndex()

    //以ms为单位获取当前缓冲的位置。
    @JvmStatic
    fun getBufferedPosition(): Long = StarrySky.with().getBufferedPosition()

    //获取播放队列
    @JvmStatic
    fun getPlayList():MutableList<MusicInfo> = StarrySky.with().getPlayList().map { it.convert() }.toMutableList()

    //更新播放列表
    @JvmStatic
    fun updatePlayList(list: MutableList<MusicInfo>){
        StarrySky.with().updatePlayList(list.map { it.revert() }.toMutableList())
    }

    //添加更多播放列表
    @JvmStatic
    fun addPlayList(musicList: MutableList<MusicInfo>){
        StarrySky.with().addPlayList(musicList.map { it.revert() }.toMutableList())
    }

    //添加一首歌
    @JvmStatic
    fun addSongInfo(musicInfo: MusicInfo){
        StarrySky.with().addSongInfo(musicInfo.revert())
    }

    //添加一首歌,指定位置
    @JvmStatic
    fun addSongInfo(musicInfo: MusicInfo, index: Int){
        StarrySky.with().addSongInfo(index,musicInfo.revert())
    }

    //清除播放列表
    @JvmStatic
    fun clearPlayList(){
        StarrySky.with().clearPlayList()
    }

    //刷新随机列表
    @JvmStatic
    fun updateShuffleSongList(){
        StarrySky.with().updateShuffleSongList()
    }

    //根据当前信息更新下标
    @JvmStatic
    fun updateCurrIndex(){
        StarrySky.with().updateCurrIndex()
    }

    //根据id移除
    @JvmStatic
    fun removeSongInfoById(songId: String?){
        StarrySky.with().removeSongInfo(songId)
    }

    //关闭notification
    @JvmStatic
    fun closeNotification(){
        StarrySky.closeNotification()
    }

    //打开notification
    @JvmStatic
    fun openNotification(){
        StarrySky.openNotification()
    }

    //切换通知栏
    @JvmStatic
    fun changeNotification(notificationType: Int){
        StarrySky.changeNotification(notificationType)
    }

    //获取通知栏类型
    @JvmStatic
    fun getNotificationType(): Int = StarrySky.getNotificationType()

    //获取缓存类
    @JvmStatic
    fun getPlayerCache(context: Context): MusicCache {
        val cache = StarrySky.getPlayerCache()
        "当前cache配置${config?.cacheFilePath}".printLog()
        return MusicCache(
            isOpenCache = cache?.isOpenCache() == true,
            //todo 获取用户配置的config.cache路径
            cacheDirectory = cache?.getCacheDirectory(context, config?.cacheFilePath).toString()
        )
    }

    //对象类全置空
    @JvmStatic
    fun release(){
        StarrySky.release()
    }

    private fun SongInfo.convert(): MusicInfo {
        return MusicInfo(
            songId = this.songId,
            songUrl = this.songUrl,
            songName = this.songName,
            artist = this.artist,
            songCover = this.songCover,
            duration = this.duration,
            decode = this.decode,
            headData = this.headData
        )
    }

    private fun MusicInfo.revert(): SongInfo{
        return SongInfo(
            this.songId,
            this.songUrl,
            this.songName,
            this.artist,
            this.songCover,
            this.duration,
            this.decode,
            this.headData
        )
    }

    private fun MusicNotificationConfig.convert(): NotificationConfig{
       /* val targetClass = this.targetClass
        val targetBundle = this.targetClassBundle
        val pendingIntentMode = this.pendingIntentMode
        val smallIconRes = this.smallIconRes*/
        return NotificationConfig.create {
            targetClass { this@convert.targetClass }
            targetClassBundle {

                //参数自带当前音频播放信息，不用自己传
                return@targetClassBundle this@convert.targetClassBundle
            }
            nextIntent { this@convert.nextIntent }
            playIntent { this@convert.playIntent }
            pauseIntent { this@convert.pauseIntent }
            playOrPauseIntent { this@convert.playOrPauseIntent }
            stopIntent { this@convert.stopIntent }
            pauseDrawableRes { this@convert.pauseDrawableRes }
            downloadIntent { this@convert.downloadIntent }
            skipNextTitle { this@convert.skipNextTitle }
            skipPreviousTitle { this@convert.skipPreviousTitle }
            smallIconRes {
                this@convert.smallIconRes
            }
            pendingIntentMode { this@convert.pendingIntentMode }
        }
    }

    private fun PlaybackStage.revert(): PlayManger {
        val state = this@revert
        return PlayManger(
            lastSongInfo = state.lastSongInfo?.convert(),
            songInfo = state.songInfo?.convert(),
            isStop = state.isStop,
            errorMsg = state.errorMsg,
            stage = state.stage
        )
    }

}