package com.tobery.musicplay

import android.os.Bundle
import com.lzx.starrysky.notification.INotification
import com.tobery.musicplay.notification.MusicNotificationManager.Companion.CUSTOM_NOTIFICATION_FACTORY
import com.tobery.musicplay.notification.MusicNotificationConfig
import com.tobery.musicplay.notification.MusicNotificationManager
import com.tobery.musicplay.util.GlideImageLoader
import com.tobery.musicplay.util.printLog


data class PlayConfig(
    var defaultNotificationSwitch: Boolean = true, //通知栏开关
    var notificationType: Int = INotification.CUSTOM_NOTIFICATION, //通知栏类型 1系统 2 自定义
    var isOpenCache: Boolean = true, // 开启cache
    var cacheFilePath: String = "musicStarrySkyCache",
    var cacheByteNum: Long = 1024 * 1024 * 1024,
    var isAutoManagerFocus: Boolean = false, //是否自动处理焦点
    var defaultImageLoader: GlideImageLoader = GlideImageLoader(), //默认glide框架
    var defaultPermissionIntercept: PermissionInterceptor = PermissionInterceptor(),
    var notificationClass: String = "com.tobery.app.MainActivity",
    var factory: MusicNotificationManager.NotificationFactory = CUSTOM_NOTIFICATION_FACTORY,
    var defaultNotificationConfig: MusicNotificationConfig = MusicNotificationConfig.create {
        targetClass { "com.tobery.musicplay.NotificationReceiver" }
        targetClassBundle {
            val bundle = Bundle()
            bundle.putString("title", "我是点击通知栏转跳带的参数")
            "当前切换是否成功$notificationClass".printLog()
            bundle.putString("targetClass", notificationClass)
            //参数自带当前音频播放信息，不用自己传
            return@targetClassBundle bundle
        }
        smallIconRes {
            -1
        }
        pendingIntentMode { MusicNotificationConfig.MODE_BROADCAST }
    }
)
