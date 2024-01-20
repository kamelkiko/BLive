package com.expert.blive.Agora.agoraLive.activity;

import android.app.Activity;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;
import com.expert.blive.utils.CommonUtils;
import com.expert.blive.Agora.GiftModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoMvvm extends ViewModel {
    ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);

    private MutableLiveData<Map> liveGiftSenddata;

    public LiveData<Map> giftLiveSend(final Activity activity, String userId, String coin, String giftUserId, String giftId, String pkHostId, String liveId) {
        liveGiftSenddata = new MutableLiveData<>();
        CommonUtils.showProgressDialog(activity);

        if (CommonUtils.isNetworkConnected(activity)){
            apiInterface.giftLiveSend(userId, coin, giftUserId, giftId, pkHostId, liveId).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (response.body() != null) {
                        CommonUtils.dismissDialog();
                        liveGiftSenddata.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return liveGiftSenddata;
    }
    private MutableLiveData<GiftModel> lievGiftData;

    public LiveData<GiftModel> sendLiveGift(final Activity activity, String userId, String giftCategoryId) {
        lievGiftData = new MutableLiveData<>();
//        CommonUtils.showProgress(activity, "Loading...");
        if (CommonUtils.isNetworkConnected(activity)) {
            apiInterface.getLiveGifts(userId, giftCategoryId).enqueue(new Callback<GiftModel>() {
                @Override
                public void onResponse(Call<GiftModel> call, Response<GiftModel> response) {
                    if (response.body() != null) {
//                        CommonUtils.dismissProgress();
                        lievGiftData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<GiftModel> call, Throwable t) {
//                    CommonUtils.dismissProgress();
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        return lievGiftData;
    }

    private MutableLiveData<GetLiveFriendsListPojo> getLiveFriendsDetails;

    public LiveData<GetLiveFriendsListPojo> getLiveFRiendsResults(final Activity activity, String userId) {

        getLiveFriendsDetails = new MutableLiveData<>();


        if (CommonUtils.isNetworkConnected(activity)) {

            CommonUtils.showProgressDialog(activity);
            apiInterface.getLiveFriends(userId).enqueue(new Callback<GetLiveFriendsListPojo>() {
                @Override
                public void onResponse(Call<GetLiveFriendsListPojo> call, Response<GetLiveFriendsListPojo> response) {
                    CommonUtils.dismissDialog();
                    if (response.body() != null) {

                        getLiveFriendsDetails.postValue(response.body());

                    }
                }

                @Override
                public void onFailure(Call<GetLiveFriendsListPojo> call, Throwable t) {
                    CommonUtils.dismissDialog();

                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

        return getLiveFriendsDetails;

    }
}
