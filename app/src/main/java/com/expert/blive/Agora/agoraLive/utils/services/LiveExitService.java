package com.expert.blive.Agora.agoraLive.utils.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;


import com.expert.blive.Agora.agoraLive.firebase.ChildEventListenersObject;
import com.expert.blive.Agora.agoraLive.firebase.FirebaseHelper;
import com.expert.blive.ModelClass.ModelAgoraLiveUsers;
import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveExitService extends Service {
    private String channelName, liveId;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initVal(intent);
        Log.i("Agora Service Started ", "success");
        return START_STICKY;
    }

    private void initVal(Intent intent) {
        try{
            channelName = intent.getExtras().getString("liveUsername", "");
            liveId = intent.getExtras().getString("liveId");
        }catch (Exception ignored){}
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        try {
            FirebaseHelper.setLiveStatus(false);
            logoutRTM();
            FirebaseHelper.removePlayerListener(channelName, ChildEventListenersObject.getViewerChildListsner(), ChildEventListenersObject.getGiftChildListsner());
            stopBroadcasting(liveId);
            Log.i("Agora rtc engine : ", String.valueOf(ChildEventListenersObject.getRtcEngine().leaveChannel()));
        } catch (Exception e) {
            Log.i("Class On terminate", e.getMessage());
            e.printStackTrace();
        } finally {
            ChildEventListenersObject.getRtcEngine().leaveChannel();
            Log.i("Leave Service : ", String.valueOf(ChildEventListenersObject.getRtcEngine().leaveChannel()));
        }
        System.out.println("onTaskRemoved called");
    }

    private void stopBroadcasting(String liveId) {
        Log.i("Exit Service : ", "Entered api");
        ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);
        apiInterface.stopLiveAgora(liveId).enqueue(new Callback<ModelAgoraLiveUsers>() {
            @Override
            public void onResponse(Call<ModelAgoraLiveUsers> call, Response<ModelAgoraLiveUsers> response) {
                if (response.body() != null) {
                    Log.i("live Users:Message", response.body().getMessage());
                    if (response.isSuccessful()) {
                        try {

                            Log.i("Service Work Stopped : ", "success");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<ModelAgoraLiveUsers> call, Throwable t) {
                Log.i("live Users:Error", t.getMessage());
            }
        });
    }

    private void logoutRTM(){

    }
}
