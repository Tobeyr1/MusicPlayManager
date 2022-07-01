package com.tobery.app

import android.app.*
import android.bluetooth.BluetoothGatt
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.util.*


class TestService : Service() {

    private val channelId = "com.tobery.app.java"
    private val channelName = "test channel name"


    var mBuilder: NotificationCompat.Builder? = null
    var notificationManager: NotificationManager? = null
    var notifyId = 0

    private val binder = LocalBinder()


    override fun onCreate() {
        super.onCreate()
        initNotification()
    }


    inner class LocalBinder : Binder() {
        fun getService(): TestService = this@TestService
    }

    /**
     *  初始化状态栏
     */
    private fun initNotification() {
        registerNotificationChannel()

        notifyId = 0x975 //(int) System.currentTimeMillis();
        val nfIntent = Intent(this, MainActivity::class.java)
        mBuilder = NotificationCompat.Builder(this, channelId)
        val pi = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                this,
                0,
                nfIntent,
                PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                this,
                0,
                nfIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        mBuilder!!.setContentIntent(pi).setLargeIcon(
            BitmapFactory.decodeResource(
                this.resources,
                R.mipmap.ic_launcher
            )
        )
            .setContentTitle("Kawasaki GPS running")
            .setSmallIcon(R.mipmap.ic_launcher)
            //.setContentText("要显示的内容")
            .setWhen(System.currentTimeMillis())
            .setPriority(Notification.PRIORITY_HIGH)
            .setOngoing(true) //设定为点击后不消失
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mBuilder?.setContentTitle(resources.getString(R.string.app_name))
        }
        //startForeground(notifyId, mBuilder!!.build())
    }

    /**
     * 注册通知通道
     */
    private fun registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = notificationManager!!.getNotificationChannel(channelId)
            if (notificationChannel == null) {
                val channel = NotificationChannel(
                    channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_LOW //NONE 为不弹出横幅，HIGH为弹出横幅
                )
                //是否在桌面icon右上角展示小红点
                channel.enableLights(true)
                //小红点颜色
                channel.lightColor = Color.RED
                //通知显示
                channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                //是否在久按桌面图标时显示此渠道的通知
                channel.setShowBadge(true)
                notificationManager!!.createNotificationChannel(channel)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

}