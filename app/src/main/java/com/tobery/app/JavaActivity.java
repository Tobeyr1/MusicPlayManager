package com.tobery.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import com.tobery.musicplay.MusicPlay;
import com.tobery.musicplay.OnMusicPlayProgressListener;
import com.tobery.musicplay.OnMusicPlayStateListener;
import com.tobery.musicplay.PermissionChecks;
import com.tobery.musicplay.PlayConfig;
import com.tobery.musicplay.MusicInfo;
import com.tobery.musicplay.PlayManger;
import com.tobery.musicplay.ViewExtensionKt;

import java.util.ArrayList;

public class JavaActivity extends AppCompatActivity {

    PermissionChecks checks;

    private final String[] APP_PERMISSIONS = new ArrayList<String>(){
        {
            add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            add(Manifest.permission.READ_EXTERNAL_STORAGE);
            add(Manifest.permission.RECORD_AUDIO);
            add(Manifest.permission.READ_PHONE_STATE);
        }
    }.toArray(new String[0]);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);
        checks = new PermissionChecks(this);
        checks.requestPermissions(APP_PERMISSIONS, it ->{
            if (it){
               // MusicPlay.initConfig(this,new PlayConfig());
            }else {

            }
            return null;
        });
        findViewById(R.id.bt_one).setOnClickListener(v -> {

            MusicInfo songInfo = new MusicInfo();
            songInfo.setSongId("11");
            songInfo.setSongUrl("http://music.163.com/song/media/outer/url?id=33894312");
            songInfo.setArtist("歌手");
            songInfo.setSongName("海阔天空");
            songInfo.setSongCover("http://p3.music.126.net/Uyj-KRGb9ZnwuPLYEe739Q==/109951167614293336.jpg");
            MusicPlay.playMusicByInfo(songInfo);

            MusicPlay.onPlayStateListener(this, new OnMusicPlayStateListener() {

                @Override
                public void onPlayState(@NonNull String playbackStage) {
                    switch (playbackStage){
                        case PlayManger.IDLE:
                            ViewExtensionKt.printLog("空闲");
                            break;
                        case PlayManger.PLAYING:
                            ViewExtensionKt.printLog("播放");
                            break;
                        case PlayManger.BUFFERING:
                            ViewExtensionKt.printLog("缓冲");
                            break;
                        case PlayManger.PAUSE:
                            ViewExtensionKt.printLog("暂停");
                            break;
                    }
                }
            });

            MusicPlay.onPlayProgressListener(this, new OnMusicPlayProgressListener() {
                @Override
                public void onPlayProgress(long currPos, long duration) {
                    ViewExtensionKt.printLog("当前进度"+currPos);
                }
            });

        });
    }

}