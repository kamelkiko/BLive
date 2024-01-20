package com.expert.blive.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.expert.blive.Agora.agoraLive.proxy.ClientProxy;
import com.expert.blive.Agora.agoraLive.rtc.AgoraEventHandler;
import com.expert.blive.Agora.agoraLive.rtc.EventHandler;
import com.expert.blive.Agora.agoraLive.stats.StatsManager;
import com.expert.blive.Agora.agoraLive.utils.Config;
import com.expert.blive.Agora.agoraLive.utils.FileUtil.Constants;
import com.expert.blive.Agora.agoraLive.utils.Global;
import com.expert.blive.Agora.openvcall.model.AGEventHandler;
import com.expert.blive.Agora.openvcall.model.CurrentUserSettings;
import com.expert.blive.Agora.openvcall.model.EngineConfig;
import com.expert.blive.Agora.openvcall.model.MyEngineEventHandler;
import com.expert.blive.R;


import io.agora.rtc.RtcEngine;

public class App extends Application {

    public static Singleton singleton;

    private static App instance;
    private final StatsManager mStatsManager = new StatsManager();

    private Config mConfig;

    private RtcEngine mRtcEngine;
    private RtcEngine mRtc;
    private final EngineConfig mGlobalConfig = new EngineConfig();
    private SharedPreferences mPref;
    private final AgoraEventHandler mHandler = new AgoraEventHandler();


    private MyEngineEventHandler mEventHandler = new MyEngineEventHandler();
    private final CurrentUserSettings mVideoSettings = new CurrentUserSettings();

    @SuppressLint("StaticFieldLeak")
    private static SharedPref sharedpref;

    public ClientProxy proxy() {
        return ClientProxy.instance();
    }


    public static App getAppContext() {
        return instance;
    }


    public SharedPreferences preferences() {
        return mPref;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Context context = getApplicationContext();

        singleton = new Singleton();
        sharedpref = new SharedPref(context);

//        try {
//            mRtcEngine = RtcEngine.create(getApplicationContext(), Constants.AGORA_APP_ID, mHandler);
//            // Sets the channel profile of the Agora RtcEngine.
//            // The Agora RtcEngine differentiates channel profiles and applies different optimization algorithms accordingly. For example, it prioritizes smoothness and low latency for a video call, and prioritizes video quality for a video broadcast.
//            mRtcEngine.setChannelProfile(io.agora.rtc.Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
//            mRtcEngine.enableVideo();
//            mRtcEngine.setLogFile(FileUtil.initializeLogFile(this));
////
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        mPref = getSharedPreferences(Global.Constants.SF_NAME, Context.MODE_PRIVATE);
        mConfig = new Config(this);

//        initConfig();
 createRtcEngine();
        createRtcEngineTwo();

    }

    private void createRtcEngineTwo() {
        Context context = getApplicationContext();
        String appId = context.getString(R.string.agora_app_id);
        if (TextUtils.isEmpty(appId)) {
            throw new RuntimeException("NEED TO use your App ID, get your own ID at https://dashboard.agora.io/");
        }
        mEventHandler = new MyEngineEventHandler();

        try {
            mRtcEngine = RtcEngine.create(context, appId, mEventHandler);

        } catch (Exception e) {

            e.printStackTrace();

            throw new RuntimeException("NEED TO check rtc sdk init fatal error");
        }



    }

    public static SharedPref getSharedpref() {
        return sharedpref;
    }

    public static boolean hasNetwork() {
        return instance.isNetworkConnected();
    }


    public StatsManager statsManager() {
        return mStatsManager;
    }

    public void registerEventHandler(EventHandler handler) {
        mHandler.addHandler(handler);
    }

    public void removeEventHandler(EventHandler handler) {
        mHandler.removeHandler(handler);
    }

    public static Singleton getSingleton() {
        return singleton;

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public Config config() {
        return mConfig;
    }

    public Config configBase() {
        return mConfig;
    }



     public RtcEngine rtcEngine() {
        return mRtc;
    }

    public RtcEngine rtcEngineAudio() {
        return mRtcEngine;
    }


    public EngineConfig engine() {
        return mGlobalConfig;
    }

    public void addEventHandler(AGEventHandler handler) {
        mEventHandler.addEventHandler(handler);
    }

    public void remoteEventHandler(AGEventHandler handler) {
        mEventHandler.removeEventHandler(handler);
    }

    public CurrentUserSettings userSettings() {
        return mVideoSettings;
    }


    public static Singleton getSingletone() {
        return singleton;
    }

    public static App the() {
        return instance;
    }


    private void createRtcEngine() {
        Context context = getApplicationContext();


        try {
            mRtc = RtcEngine.create(context,Constants.AGORA_APP_ID, mHandler);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("NEED TO check rtc sdk init fatal error");
        }

        /*
          Sets the channel profile of the Agora RtcEngine.
          The Agora RtcEngine differentiates channel profiles and applies different optimization
          algorithms accordingly. For example, it prioritizes smoothness and low latency for a
          video call, and prioritizes video quality for a video broadcast.
         */

//        mRtc.disableAudio();
        /*
          Enables the onAudioVolumeIndication callback at a set time interval to report on which
          users are speaking and the speakers' volume.
          Once this method is enabled, the SDK returns the volume indication in the
          onAudioVolumeIndication callback at the set time interval, regardless of whether any user
          is speaking in the channel.
         */
    }

}
