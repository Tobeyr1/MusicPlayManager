package com.tobery.musicplay.notification

import android.content.Context
import com.lzx.starrysky.notification.*

class MusicNotificationManager {

    interface NotificationFactory : NotificationManager.NotificationFactory {
        override fun build(context: Context, config: NotificationConfig?): INotification
    }

    fun getSystemNotification(context: Context, config: NotificationConfig?) = SYSTEM_NOTIFICATION_FACTORY.build(context, config)

    fun getCustomNotification(context: Context, config: NotificationConfig?) = CUSTOM_NOTIFICATION_FACTORY.build(context, config)

    companion object {
        val SYSTEM_NOTIFICATION_FACTORY: NotificationFactory = object : NotificationFactory {
            override fun build(
                context: Context, config: NotificationConfig?
            ): INotification {
                return if (config == null) SystemNotification(context) else SystemNotification(context, config)
            }
        }

        val CUSTOM_NOTIFICATION_FACTORY: NotificationFactory = object : NotificationFactory {
            override fun build(
                context: Context, config: NotificationConfig?
            ): INotification {
                return if (config == null) DefaultCustomNotification(context) else DefaultCustomNotification(context, config)
            }
        }
    }
}