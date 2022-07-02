package com.tobery.musicplay

import android.content.Context
import com.lzx.starrysky.notification.CustomNotification
import com.lzx.starrysky.notification.INotification
import com.lzx.starrysky.notification.NotificationConfig
import com.lzx.starrysky.notification.NotificationManager
import com.tobery.musicplay.GlideImageLoader
import com.tobery.musicplay.PermissionInterceptor

data class PlayConfig(
    val defaultNotificationSwitch: Boolean = true, //通知栏开关
    val notificationType: Int = INotification.CUSTOM_NOTIFICATION, //通知栏类型 1系统 2 自定义
    val isOpenCache: Boolean = true, // 开启cache
    val cacheFilePath: String = "musicStarrySkyCache",
    val cacheByteNum: Long = 1024 * 1024 * 1024,
    val isAutoManagerFocus: Boolean = false, //是否自动处理焦点
    val defaultImageLoader: GlideImageLoader = GlideImageLoader(), //默认glide框架
    val defaultPermissionIntercept: PermissionInterceptor = PermissionInterceptor(),
    val notificationClass: String = "com.tobery.personalmusic.ui.song.CurrentSongPlayActivity",
    val factory: NotificationManager.NotificationFactory = DEFAULT_CUSTOM_NOTIFICATION_FACTORY
){
    companion object{
        val DEFAULT_CUSTOM_NOTIFICATION_FACTORY: NotificationManager.NotificationFactory = object :
            NotificationManager.NotificationFactory {
            override fun build(
                context: Context, config: NotificationConfig?
            ): INotification {
                return DefaultCustomNotification(context, config!!)
              //  return if (config == null) CustomNotification(context) else DefaultCustomNotification(context, config)
            }
        }
    }
}
