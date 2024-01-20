package com.expert.blive.Agora.agoraLive.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


import com.expert.blive.Agora.agoraLive.firebase.ChildEventListenersObject;
import com.expert.blive.ModelClass.ModelAgoraLiveUsers;
import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfflineWorkManager extends Worker {

    public OfflineWorkManager(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.i("Work Manager : ", "reached");
        String liveId = getInputData().getString("liveId");
        Log.i("Work Manager live id : ", liveId);
        stopBroadcasting(liveId);
        return Result.success();
    }

    private void stopBroadcasting(String liveId) {
        Log.i("Agora Work Manager : ", "Entered api");
        ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);
        apiInterface.stopLiveAgora(liveId).enqueue(new Callback<ModelAgoraLiveUsers>() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onResponse(Call<ModelAgoraLiveUsers> call, Response<ModelAgoraLiveUsers> response) {
                if (response.body() != null) {
                    Log.i("Agora :Work Manager ", response.body().getMessage());
                    if (response.isSuccessful()) {
                        try {
                            stop();
                            ChildEventListenersObject.getRtcEngine().leaveChannel();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @SuppressLint("RestrictedApi")
            @Override
            public void onFailure(Call<ModelAgoraLiveUsers> call, Throwable t) {
                stop();
            }
        });
    }

    @Override
    public void onStopped() {
        Log.i("Agora Work Manager : ", "stopped");
        super.onStopped();
    }
}
