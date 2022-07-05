package com.tobery.app

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tobery.musicplay.MusicPlay.stopMusic

class MainActivity : AppCompatActivity() {

    private lateinit var mService: TestService
    private var mBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv_bt).setOnClickListener {
            startActivity(Intent(this,JavaActivity::class.java))
            /*Intent(this, TestService::class.java).also { intent ->
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }*/
        }
    }

    private val connection = object : ServiceConnection {

        @SuppressLint("MissingPermission")
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TestService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMusic()
    }

}