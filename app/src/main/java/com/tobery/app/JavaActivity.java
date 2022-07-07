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

    ArrayList<MusicInfo> songList = new ArrayList<>();

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
           // MusicPlay.playMusicByInfo(songInfo);
            songList.add(songInfo);
            MusicInfo songInfo1 = new MusicInfo();
            songInfo1.setSongId("22");
            songInfo1.setSongUrl("http://m8.music.126.net/20220706153844/41cc70c399540e0ff8cf639114e157ce/ymusic/9429/7fbc/3727/d0647c73bcc77006b94ce6ad9ae620f7.flac");
            songInfo1.setArtist("SHINee");
            songInfo1.setSongName("누난 너무 예뻐 (Replay)");
            songInfo1.setSongCover("http://p4.music.126.net/YG0CmRSmeIOGa7REEceCWA==/910395627822214.jpg");
            MusicPlay.playMusicByList(songList,0);

            MusicPlay.onPlayStateListener(this, new OnMusicPlayStateListener() {

                @Override
                public void onPlayState(@NonNull PlayManger playbackStage) {
                    switch (playbackStage.getStage()){
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
                        case PlayManger.SWITCH:
                            ViewExtensionKt.printLog("切歌"+playbackStage.getSongInfo().getSongUrl());
                            break;
                    }
                }
            });

            MusicPlay.onPlayProgressListener( new OnMusicPlayProgressListener() {
                @Override
                public void onPlayProgress(long currPos, long duration) {
                   // ViewExtensionKt.printLog("当前进度"+currPos);
                }
            });

        });
    }

}