package com.tobery.app

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tobery.musicplay.MusicPlay
import com.tobery.musicplay.MusicPlay.stopMusic
import com.tobery.musicplay.PlayConfig
import com.tobery.musicplay.notification.MusicNotificationConfig
import com.tobery.musicplay.util.PermissionChecks
import com.tobery.musicplay.util.setOnSingleClickListener

class MainActivity : AppCompatActivity() {

    private var checks: PermissionChecks? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checks = PermissionChecks(this)
        checks!!.requestPermissions(arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        )){
            if (it){
                MusicPlay.initConfig(this, PlayConfig(
                    notificationClass = "com.tobery.app.JavaActivity"
                    ,
                    defaultNotificationConfig = MusicNotificationConfig.create {
                        targetClass { "com.tobery.musicplay.NotificationReceiver" }
                        targetClassBundle {
                            val bundle = Bundle()
                            bundle.putString("title", "我是点击通知栏转跳带的参数")
                            bundle.putString("targetClass", "com.tobery.app.JavaActivity")

                            return@targetClassBundle bundle
                        }
                        smallIconRes {
                            R.drawable.ic_music_cover
                        }
                    }
                ))
            }
        }

        findViewById<TextView>(R.id.tv_bt).setOnSingleClickListener {
            startActivity(Intent(this,JavaActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }

}