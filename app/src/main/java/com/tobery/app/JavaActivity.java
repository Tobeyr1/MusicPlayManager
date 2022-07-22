package com.tobery.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.tobery.musicplay.MusicPlay;
import com.tobery.musicplay.OnMusicPlayProgressListener;
import com.tobery.musicplay.OnMusicPlayStateListener;
import com.tobery.musicplay.OnNetWorkChangeListener;
import com.tobery.musicplay.entity.MusicInfo;
import com.tobery.musicplay.PlayConfig;
import com.tobery.musicplay.entity.PlayManger;
import com.tobery.musicplay.util.PermissionChecks;
import com.tobery.musicplay.util.ViewExtensionKt;

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
        MusicPlay.isNetworkAvailable(this,this, new OnNetWorkChangeListener() {
            @Override
            public void onNetWorkChange(boolean isAvailable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       // Toast.makeText(JavaActivity.this,"当前网络是否可用"+isAvailable+"",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        MusicPlay.networkGlobalAvailable(new OnNetWorkChangeListener() {
            @Override
            public void onNetWorkChange(boolean isAvailable) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                         Toast.makeText(JavaActivity.this,"当前网络是否可用"+isAvailable+"",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        PlayConfig playConfig = new PlayConfig(

        );
        checks.requestPermissions(APP_PERMISSIONS, it ->{
            if (it){
              //  MusicPlay.initConfig(this,new PlayConfig());
            }else {

            }
            return null;
        });
        ViewExtensionKt.setOnSingleClickListener(findViewById(R.id.bt_one), view -> {
            MusicInfo songInfo = new MusicInfo();
            songInfo.setSongId("33894312");
            songInfo.setSongUrl("http://music.163.com/song/media/outer/url?id=33894312");
            songInfo.setArtist("歌手");
            songInfo.setSongName("海阔天空");
            songInfo.setSongCover("http://p3.music.126.net/Uyj-KRGb9ZnwuPLYEe739Q==/109951167614293336.jpg");
            songList.add(songInfo);
            MusicInfo songInfo1 = new MusicInfo();
            songInfo1.setSongId("1960605228");
            songInfo1.setSongUrl("http://music.163.com/song/media/outer/url?id=1960605228");
            songInfo1.setArtist("龚明威");
            songInfo1.setSongName("起风了");
            songInfo1.setSongCover("http://p3.music.126.net/EI-1VBiCIJ7lF9R7sxFNJA==/109951167611502432.jpg");
            songList.add(songInfo1);
            MusicInfo songInfo2 = new MusicInfo();
            songInfo2.setSongId("1960903012");
            songInfo2.setSongName("黑本子（Black Benz）");
            songInfo2.setSongUrl("http://music.163.com/song/media/outer/url?id=1960903012");
            songInfo2.setArtist("KEY.L刘聪");
            songInfo2.setSongCover("http://p4.music.126.net/yRqakJ-0o6ZN6T-CaU4XvA==/109951167619824931.jpg");
            songList.add(songInfo2);
            MusicInfo songInfo3 = new MusicInfo();
            songInfo3.setSongId("3727");
            songInfo3.setArtist("flc");
            songInfo3.setSongName("测试");
            songInfo3.setSongUrl("http://m7.music.126.net/20220712213650/22fb8c3657f5028a979ab9ead32e3b9d/ymusic/b527/3445/b7c2/8f590a92a0a02a0f80b0ccdb76df7cb2.flac");
            songInfo3.setSongCover("http://p3.music.126.net/EI-1VBiCIJ7lF9R7sxFNJA==/109951167611502432.jpg");
            songList.add(songInfo3);
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
                            ViewExtensionKt.printLog("当前音效"+MusicPlay.getAudioSessionId());
                            break;
                        case PlayManger.BUFFERING:
                            ViewExtensionKt.printLog("缓冲");
                            break;
                        case PlayManger.PAUSE:
                            ViewExtensionKt.printLog("暂停");
                            break;
                        case PlayManger.SWITCH:
                            ViewExtensionKt.printLog("切歌"+playbackStage.getSongInfo().getSongUrl());
                            ViewExtensionKt.printLog("上一首"+playbackStage.getLastSongInfo().getSongName());
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
            return  null;
        });

        ViewExtensionKt.setOnSingleClickListener(findViewById(R.id.bt_two), view -> {
            MusicPlay.saveEffectConfig(true);
            MusicPlay.effectSwitch(true);
            MusicPlay.attachAudioEffect(MusicPlay.getAudioSessionId());
            //获取均衡器支持的预设总数
            short numberOfPresets = MusicPlay.equalizerNumberOfPresets();
            //获取当前的预设
            Short currentPreset = MusicPlay.equalizerCurrentPreset();
            ViewExtensionKt.printLog("当前预设"+currentPreset);
            int preset = 0;
            ViewExtensionKt.printLog("预设数"+numberOfPresets);
            for(short var6 = numberOfPresets; preset < var6; ++preset) {
                String presetName = MusicPlay.equalizerPresetName((short)preset);
                ViewExtensionKt.printLog("预设内容"+presetName);
            }
        });
    }

}