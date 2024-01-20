package com.expert.blive.Agora.agoraLive.activity;

import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import com.expert.blive.utils.CommonUtils;

public class ProfileData {

    private FragmentActivity activity;
    private ProfileMvvm profileMvvm;
    followCallBack followCallBack;



    public ProfileData(FragmentActivity activity, followCallBack followCallBack) {
        this.activity = activity;
        profileMvvm = new ProfileMvvm();
        this.followCallBack = followCallBack;
    }
    public interface followCallBack {
        void followInterfaceCall(OtherUserDataModel otherUserDataModel);
    }

    public void getProfileData(String otherUserId) {
        String myId = CommonUtils.getUserId();
        Log.i("myId", myId);
        profileMvvm.otherUserProfile(activity, otherUserId, myId).observe(activity, new Observer<OtherUserDataModel>() {
            @Override
            public void onChanged(OtherUserDataModel otherUserDataModel) {
                followCallBack.followInterfaceCall(otherUserDataModel);
            }
        });
    }




}
