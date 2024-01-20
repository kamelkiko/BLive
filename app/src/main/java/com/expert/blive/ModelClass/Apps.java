package com.expert.blive.ModelClass;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;


import com.expert.blive.Agora.openvcall.model.AGEventHandler;
import com.expert.blive.Agora.openvcall.model.CurrentUserSettings;
import com.expert.blive.Agora.openvcall.model.EngineConfig;
import com.expert.blive.Agora.openvcall.model.MyEngineEventHandler;
import com.expert.blive.R;
import com.expert.blive.utils.AppPreferences;
import com.expert.blive.utils.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.agora.rtc.Constants;
import io.agora.rtc.RtcEngine;

public class Apps extends Application {

    public static Singleton singleton;
    private Context context;
    private Logger loggerFactory = LoggerFactory.getLogger(this.getClass());
    private RtcEngine mRtcEngine;
    private EngineConfig mConfig;
    private MyEngineEventHandler mEventHandler;
    private CurrentUserSettings mVideoSettings = new CurrentUserSettings();


    public static Apps instance;

    public static AppPreferences appPreference1;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        singleton = new Singleton();

        context = getApplicationContext();
        appPreference1 = new AppPreferences(context, "helo kings");
        createRtcEngine();

    }


    public RtcEngine rtcEngine() {
        return mRtcEngine;
    }

    public EngineConfig config() {
        return mConfig;
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


    public static AppPreferences getAppPreference() {
        return appPreference1;
    }

    public static Singleton getSingletone() {
        return singleton;
    }

    public static Apps the() {
        return instance;
    }


    private void createRtcEngine() {
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

        /*
          Sets the channel profile of the Agora RtcEngine.
          The Agora RtcEngine differentiates channel profiles and applies different optimization
          algorithms accordingly. For example, it prioritizes smoothness and low latency for a
          video call, and prioritizes video quality for a video broadcast.
         */
        mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
        // Enables the video module.
        mRtcEngine.enableVideo();
        /*
          Enables the onAudioVolumeIndication callback at a set time interval to report on which
          users are speaking and the speakers' volume.
          Once this method is enabled, the SDK returns the volume indication in the
          onAudioVolumeIndication callback at the set time interval, regardless of whether any user
          is speaking in the channel.
         */
        mRtcEngine.enableAudioVolumeIndication(200, 3, false);
        mConfig = new EngineConfig();
    }

}
