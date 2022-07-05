package com.tobery.app

import android.Manifest
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tobery.musicplay.MusicPlay
import com.tobery.musicplay.MusicPlay.stopMusic
import com.tobery.musicplay.PermissionChecks
import com.tobery.musicplay.PlayConfig

class MainActivity : AppCompatActivity() {

    private lateinit var mService: TestService
    private var mBound: Boolean = false
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
                MusicPlay.initConfig(this, PlayConfig())
            }
        }

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