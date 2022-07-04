package com.tobery.app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import com.tobery.musicplay.MusicPlay;
import com.tobery.musicplay.PermissionChecks;
import com.tobery.musicplay.PlayConfig;
import com.tobery.musicplay.MusicInfo;

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
                MusicPlay.initConfig(this,new PlayConfig());
            }else {

            }
            return null;
        });
        findViewById(R.id.bt_one).setOnClickListener(v -> {
          //  http://p1.music.126.net/kPDkBh_W3DmDKJZCUeFCrA==/19138099393621521.jpg
            MusicInfo songInfo = new MusicInfo();
            songInfo.setSongId("11");
            songInfo.setSongUrl("http://music.163.com/song/media/outer/url?id=33894312");
            songInfo.setArtist("歌手");
            songInfo.setSongName("海阔天空");
            songInfo.setSongCover("http://p1.music.126.net/kPDkBh_W3DmDKJZCUeFCrA==/19138099393621521.jpg");
            MusicPlay.playMusicByInfo(songInfo);
           // MusicPlay.playMusicByUrl("http://music.163.com/song/media/outer/url?id=33894312");
           // StarrySky.with().playMusicByUrl("http://music.163.com/song/media/outer/url?id=33894312");
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       // MusicPlay.stopMusic();
    }
}