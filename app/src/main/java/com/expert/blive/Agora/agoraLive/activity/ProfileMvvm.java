package com.expert.blive.Agora.agoraLive.activity;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;
import com.expert.blive.utils.CommonUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileMvvm {
    ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);



    private MutableLiveData<OtherUserDataModel> otherUserData;
    public LiveData<OtherUserDataModel> otherUserProfile(final Activity activity, String userId, String loginId) {
        otherUserData = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            Log.e("UserDetailsCheck","otherUserProfile: "+userId+" "+loginId );
            apiInterface.userProfileData(userId, loginId).enqueue(new Callback<OtherUserDataModel>() {
                @Override
                public void onResponse(Call<OtherUserDataModel> call, Response<OtherUserDataModel> response) {
                    if (response.body() != null) {
                        otherUserData.postValue(response.body());
                        Log.i("profile data", response.body().getMessage());
                    } else {
                        Log.i("profile data", " response body null");
                    }
                }

                @Override
                public void onFailure(Call<OtherUserDataModel> call, Throwable t) {
                    Log.i("profile data", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return otherUserData;
    }


}
