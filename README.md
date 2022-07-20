# MusicPlayManager [![](https://jitpack.io/v/Tobeyr1/MusicPlayManager.svg)](https://jitpack.io/#Tobeyr1/MusicPlayManager)
Used to  package the StarrySky library(二次封装音乐播放器)
---------------------------
**MusicPlayManager EncapsulateStarrySky, and realize custom notification bar and unified command style,And add permission checking tools, non intrusive access context tools, logging tools, etc**

# Quick Setup
**Add it in your root build.gradle at the end of repositories:**

```java
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```
**Then add the dependency:**
```java
dependencies {
	        implementation 'com.github.Tobeyr1:MusicPlayManager:1.0.2-alpha'
	}
```
# Basic Usage
**Adding permissions to Androidmanifest.xml**
```java
 <uses-permission android:name="android.permission.INTERNET"/>
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.READ_PHONE_STATE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission
        android:name="android.permission.WRITE_EXTERNAL_STORAGE"
        tools:ignore="ScopedStorage" />

    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```
**Use**
### Java版初始化
```java
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
      } 
      checks.requestPermissions(APP_PERMISSIONS, it ->{
            if (it){
              //  MusicPlay.initConfig(this,new PlayConfig());
            }else {

            }
            return null;
        });
```
### Kotlin版初始化
```kotlin
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
                    notificationClass = "com.tobery.app.JavaActivity" //设置target页面
                    ,
                    defaultNotificationConfig = MusicNotificationConfig.create {
                        targetClass { "com.tobery.musicplay.NotificationReceiver" }
                        targetClassBundle {
                            val bundle = Bundle()
                            bundle.putString("title", "我是点击通知栏转跳带的参数")
                            bundle.putString("targetClass", "com.tobery.app.JavaActivity")

                            return@targetClassBundle bundle
                        }
                        smallIconRes { //自定义通知栏小图标
                            R.drawable.ic_music_cover
                        }
                    }
                ))
            }
        }
    }
```
### java版权限检查工具使用方法:
```java
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
        //必须要在oncreate里面注册，否则会崩溃提示异常
        checks = new PermissionChecks(this);
      } 
      //然后在需要使用的地方调用即可
      checks.requestPermissions(APP_PERMISSIONS, it ->{
            if (it){ //权限全部通过
              
            }else {

            }
            return null;
        });
      //调用单个权限检查
       checks.requestPermission(Manifest.permission.READ_PHONE_STATE,it ->{
            return null;
        });
```
### kotlin权限检查工具使用方法:
```kotlin
private var checks: PermissionChecks? = null

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //必须要在oncreate里面注册，否则会崩溃提示异常
        checks = PermissionChecks(this)
        //调用单个权限检查
         checks!!.requestPermission(Manifest.permission.READ_PHONE_STATE){
            if (it){
                
            }
        }
        checks!!.requestPermissions(arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
        )){
            if (it){//lambda表达式
               
            }
    }
    
```
### java使用打印日志
```java
//可以打印任何对象
ViewExtensionKt.printLog(playbackStage.getSongInfo().getSongUrl());
```
### kotlin使用打印日志
```kotlin
"是否有下一首$hasNextSong".printLog()
xxx.printLog()
```
**External methods**
| MusicPlay.playMusicById() | 通过songId播放|
|--|--|
|  MusicPlay.playMusicByUrl()| 通过歌曲url播放|
|--|--|
|  MusicPlay.playMusicByInfo()| 通过歌曲信息播放 |
|--|--|
|  MusicPlay.playMusicByList(songList,0)| 播放歌曲列表，并从指定下标开始|
|--|--|
|  MusicPlay.pauseMusic()| 暂停 |
|--|--|
|  MusicPlay.restoreMusic()| 恢复播放|
|--|--|
|  MusicPlay.setRepeatMode()| 设置播放模式以及是否循环 |
|--|--|
|  MusicPlay.getRepeatMode()| 获取当前播放模式 |
|--|--|
|  MusicPlay.skipToNext()| 下一首 |
|--|--|
|  MusicPlay.skipToPrevious()| 上一首 |
|--|--|
|  MusicPlay.stopMusic()| 停止 |
|--|--|
|  MusicPlay.isPlaying()| 是否播放中 |
|--|--|
|  MusicPlay.onPlayProgressListener()| 进度监听 |
|--|--|
|  MusicPlay.onPlayStateListener()| 状态监听 |
|--|--|
|  MusicPlay.seekTo()| 跳至指定进度，并可以觉得是否播放 |
|--|--|
|  MusicPlay.addPlayList()| 添加播放队列 |
|--|--|
|  MusicPlay.clearPlayList()| 清空播放队列 |
|--|--|
#### 还有好对方法，如设置音量大小、倍速、更新播放队列、开关通知栏等等。
