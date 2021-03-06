package com.tobery.musicplay

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.lzx.starrysky.SongInfo
import com.lzx.starrysky.StarrySky
import com.lzx.starrysky.utils.getTargetClass
import com.tobery.musicplay.util.printLog

/**
 * 处理通知栏点击的广播
 */
public class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        //songInfo是当前播放的音频信息
        val songInfo = intent?.getParcelableExtra<SongInfo?>("songInfo")
        //bundleInfo是你在配置通知栏的那个bundle，里面可以拿到你自定义的参数
        val bundleInfo = intent?.getBundleExtra("bundleInfo")
        val targetClass = bundleInfo?.getString("targetClass")?.getTargetClass()
        "目标类${targetClass?.name}".printLog()
        StarrySky.getActivityStack().forEach{
            it?.printLog()
        }
        if (StarrySky.getActivityStack().isNullOrEmpty()) {
           /* val mainIntent = Intent(context, clazz)
            mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
*/
            val targetIntent = Intent(context, targetClass)
            targetIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            targetIntent.putExtra("songId", songInfo?.songId)
            //val intents = arrayOf(mainIntent, targetIntent)
            val intents = arrayOf(targetIntent)
            context?.startActivities(intents)
        } else {
            "不为空".printLog()
            val targetIntent = Intent(context, targetClass)
            targetIntent.putExtra("songId", songInfo?.songId)
            targetIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            context?.startActivity(targetIntent)
        }
    }
}