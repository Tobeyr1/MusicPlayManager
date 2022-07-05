package com.tobery.musicplay

import android.os.Bundle
import com.lzx.starrysky.notification.INotification
import com.lzx.starrysky.notification.NotificationConfig
import com.tobery.musicplay.MusicNotificationManager.Companion.CUSTOM_NOTIFICATION_FACTORY


data class PlayConfig(
    val defaultNotificationSwitch: Boolean = true, //通知栏开关
    val notificationType: Int = INotification.CUSTOM_NOTIFICATION, //通知栏类型 1系统 2 自定义
    val isOpenCache: Boolean = true, // 开启cache
    val cacheFilePath: String = "musicStarrySkyCache",
    val cacheByteNum: Long = 1024 * 1024 * 1024,
    val isAutoManagerFocus: Boolean = false, //是否自动处理焦点
    val defaultImageLoader: GlideImageLoader = GlideImageLoader(), //默认glide框架
    val defaultPermissionIntercept: PermissionInterceptor = PermissionInterceptor(),
    val notificationClass: String = "com.tobery.app.MainActivity",
    val factory: MusicNotificationManager.NotificationFactory = CUSTOM_NOTIFICATION_FACTORY,
    val defaultNotificationConfig: MusicNotificationConfig = MusicNotificationConfig.create {
        targetClass { "com.tobery.musicplay.NotificationReceiver" }
        targetClassBundle {
            val bundle = Bundle()
            bundle.putString("title", "我是点击通知栏转跳带的参数")
            bundle.putString("targetClass", notificationClass)
            //参数自带当前音频播放信息，不用自己传
            return@targetClassBundle bundle
        }
        smallIconRes {
            1
        }
        pendingIntentMode { NotificationConfig.MODE_BROADCAST }
    }
)
