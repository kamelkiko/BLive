package com.expert.blive.Agora.agoraLive.utils.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


import com.expert.blive.Agora.agoraLive.firebase.ChildEventListenersObject;
import com.expert.blive.Agora.agoraLive.firebase.FirebaseHelper;


public class PlayerExitService extends Service {

    String firebaseKey, channelName,userId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initVal(intent);
        Log.i("Agora Service Started ", "success");
        return super.onStartCommand(intent, flags, startId);
    }

    private void initVal(Intent intent) {
        try {
            if (intent.getExtras().getString("firebaseKey") != null) {
                firebaseKey = intent.getExtras().getString("firebaseKey");
            }
            channelName = intent.getExtras().getString("liveUsername");
            userId = intent.getExtras().getString("userId");
        } catch (Exception exception) {
        }
    }

    private void leaveChannel() {
        leaveRTC();
        logoutRTM();
        leaveRTMChannel();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        try {
            Log.i("Agora App Class On", "try- user deleted");
            FirebaseHelper.removeUserLeaveChannel(firebaseKey, channelName, ChildEventListenersObject.getViewerChildListsner(), ChildEventListenersObject.getGiftChildListsner());
            FirebaseHelper.removeBanListener(channelName,userId,ChildEventListenersObject.getLiveBanEventListener());
            leaveChannel();
        } catch (Exception e) {
            Log.i("Agora App Class On", e.getMessage());
            e.printStackTrace();
        }
        System.out.println("onTaskRemoved called");
        super.onTaskRemoved(rootIntent);
        this.stopSelf();
    }

    private void leaveRTC() {
        ChildEventListenersObject.getRtcEngine().leaveChannel();
        Log.i("Agora : RTC leave", String.valueOf(ChildEventListenersObject.getRtcEngine().leaveChannel()));
    }

    private void logoutRTM() {
    }

    private void leaveRTMChannel() {
    }
}
