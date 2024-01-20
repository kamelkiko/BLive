package com.expert.blive.Agora.agoraLive.utils;

import androidx.annotation.NonNull;


import com.expert.blive.utils.App;
import com.expert.blive.Agora.agoraLive.rtc.AgoraRtcHandler;
import com.expert.blive.Agora.agoraLive.rtc.RtcEventHandler;

import io.agora.rtc.Constants;
import io.agora.rtc.RtcEngine;

public class AgoraEngine {

    private static final String TAG = AgoraEngine.class.getSimpleName();

    private RtcEngine mRtcEngine;
    private AgoraRtcHandler mRtcEventHandler = new AgoraRtcHandler();


    public AgoraEngine(@NonNull App application, String appId) {
        try {
            mRtcEngine = RtcEngine.create(application, appId, mRtcEventHandler);
            mRtcEngine.enableVideo();
            mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
            mRtcEngine.enableDualStreamMode(false);
            mRtcEngine.setLogFile(UserUtil.rtcLogFilePath(application));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RtcEngine rtcEngine() {
        return mRtcEngine;
    }


    public void registerRtcHandler(RtcEventHandler handler) {
        if (mRtcEventHandler != null) mRtcEventHandler.registerEventHandler(handler);
    }

    public void removeRtcHandler(RtcEventHandler handler) {
        if (mRtcEventHandler != null) mRtcEventHandler.removeEventHandler(handler);
    }

    public void release() {
        if (mRtcEngine != null) RtcEngine.destroy();

    }
}
