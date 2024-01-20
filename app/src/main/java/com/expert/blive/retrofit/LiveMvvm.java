package com.expert.blive.retrofit;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.expert.blive.ModelClass.CountryList;
import com.expert.blive.ModelClass.ModelAgoraLiveUsers;
import com.expert.blive.Agora.agoraLive.models.LiveDescriptionModel;
import com.expert.blive.utils.CommonUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveMvvm extends ViewModel {
    ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);


    private MutableLiveData<ModelAgoraLiveUsers> stopAgoraLiveData;

    public LiveData<ModelAgoraLiveUsers> stopAgoraBroadcasting(final Activity activity, String userId) {
        stopAgoraLiveData = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            apiInterface.stopLiveAgora(userId).enqueue(new Callback<ModelAgoraLiveUsers>() {
                @Override
                public void onResponse(@NonNull Call<ModelAgoraLiveUsers> call, @NonNull Response<ModelAgoraLiveUsers> response) {
                    if (response.body() != null) {
//                        Log.i("Agora :Get live Users:Message", response.body().getMessage());
                        stopAgoraLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ModelAgoraLiveUsers> call, @NonNull Throwable t) {
//                    Log.i("Agora :Get live Users:Error", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return stopAgoraLiveData;
    }


    private MutableLiveData<GiftCategoryModel> giftCategoryModelMutableLiveData;

    public LiveData<GiftCategoryModel> giftCategory(Activity activity) {
        giftCategoryModelMutableLiveData = new MutableLiveData<>();
//        CommonUtils.showProgress(activity,"Loading...");
        apiInterface.giftCategory().enqueue(new Callback<GiftCategoryModel>() {
            @Override
            public void onResponse(@NonNull Call<GiftCategoryModel> call, @NonNull Response<GiftCategoryModel> response) {
//                CommonUtils.dismissProgress();
                if (response.body() != null) {
                    giftCategoryModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<GiftCategoryModel> call, @NonNull Throwable t) {
//                CommonUtils.dismissProgress();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return giftCategoryModelMutableLiveData;
    }

    private MutableLiveData<Map> giftBoxCoin;

    public LiveData<Map> getGiftBox(Activity activity, String userId, String liveId, String box) {
        giftBoxCoin = new MutableLiveData<>();
        apiInterface.giftBoxCoin(userId, liveId, box).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {
                if (response.body() != null) {
                    giftBoxCoin.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return giftBoxCoin;
    }


    private MutableLiveData<Map> mliveRequest;

    public LiveData<Map> liveRequest(Activity activity, String userId, String request) {
        mliveRequest = new MutableLiveData<>();
        CommonUtils.showProgressDialog(activity);
        apiInterface.liveRequest(userId, request).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {
                CommonUtils.dismissDialog();
                if (response.body() != null) {
                    mliveRequest.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {
                CommonUtils.dismissDialog();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return mliveRequest;
    }

    private MutableLiveData<LiveDescriptionModel> liveDescription;

    public LiveData<LiveDescriptionModel> liveDescriptionModelLiveData(Activity activity) {
        liveDescription = new MutableLiveData<>();
        apiInterface.liveDescription().enqueue(new Callback<LiveDescriptionModel>() {
            @Override
            public void onResponse(@NonNull Call<LiveDescriptionModel> call, @NonNull Response<LiveDescriptionModel> response) {
                if (response.body() != null) {
                    liveDescription.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LiveDescriptionModel> call, @NonNull Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return liveDescription;
    }

    private MutableLiveData<ModelBanLiveList> banListData;

    public LiveData<ModelBanLiveList> getBanList(final Activity activity, String userId) {
        banListData = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            apiInterface.getBanLiveList(userId).enqueue(new Callback<ModelBanLiveList>() {
                @Override
                public void onResponse(@NonNull Call<ModelBanLiveList> call, @NonNull Response<ModelBanLiveList> response) {
                    if (response.body() != null) {
                        banListData.postValue(response.body());
                        Log.i("Agora Ban List", response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ModelBanLiveList> call, @NonNull Throwable t) {
                    Log.i("Agora Ban List", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return banListData;
    }

    private MutableLiveData<Map> banLiveUserdata;

    public LiveData<Map> banLiveUser(final Activity activity, String userIdLive, String userIdViewer) {
        banLiveUserdata = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            apiInterface.banUserFromLive(userIdLive, userIdViewer).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {
                    if (response.body() != null) {
                        banLiveUserdata.postValue(response.body());
                        Log.i("Agora Live Ban", Objects.requireNonNull(response.body().get("message")).toString());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {
                    Log.i("Agora Live Ban", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }

        return banLiveUserdata;
    }

    private MutableLiveData<Map> publicBulletMessage;

    public LiveData<Map> publicBulletMsg(Activity activity, String userId, String type) {
        publicBulletMessage = new MutableLiveData<>();
        apiInterface.publicBulletMessage(userId, type).enqueue(new Callback<Map>() {
            @Override
            public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {
                if (response.body() != null) {
                    publicBulletMessage.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return publicBulletMessage;
    }

    private MutableLiveData<ModelAgoraLiveUsers> agoraLiveUsersMutableLiveData;

    public LiveData<ModelAgoraLiveUsers> getAgoraLiveUsers(final Activity activity, String userId, String latitude, String longitude, String type, String country) {
        agoraLiveUsersMutableLiveData = new MutableLiveData<>();
//        if (CommonUtils.isNetworkConnected(activity)) {
        apiInterface.getAgoraLiveList(userId, latitude, longitude, type, country).enqueue(new Callback<ModelAgoraLiveUsers>() {
            @Override
            public void onResponse(@NonNull Call<ModelAgoraLiveUsers> call, @NonNull Response<ModelAgoraLiveUsers> response) {
                if (response.body() != null) {
                    agoraLiveUsersMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelAgoraLiveUsers> call, @NonNull Throwable t) {
                Log.i("Agora :Get live", t.getMessage());
            }
        });
//        }
//        else {
//            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
//        }
        return agoraLiveUsersMutableLiveData;
    }

    private MutableLiveData<ModelAgoraLiveUsers> getHighestCoinAndFollowingLiveUserList;

    public LiveData<ModelAgoraLiveUsers> getHighestCoinAndFollowingLiveUserList(final Activity activity, String userId, String latitude, String longitude, String type) {
        getHighestCoinAndFollowingLiveUserList = new MutableLiveData<>();
//        if (CommonUtils.isNetworkConnected(activity)) {
        apiInterface.getHighestCoinAndFollowingLiveUserList(userId, latitude, longitude, type).enqueue(new Callback<ModelAgoraLiveUsers>() {
            @Override
            public void onResponse(@NonNull Call<ModelAgoraLiveUsers> call, @NonNull Response<ModelAgoraLiveUsers> response) {
                if (response.body() != null) {
                    getHighestCoinAndFollowingLiveUserList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelAgoraLiveUsers> call, @NonNull Throwable t) {
                Log.i("Agora :Get live", t.getMessage());
            }
        });
        return getHighestCoinAndFollowingLiveUserList;
    }

    private MutableLiveData<Map> getApplyForHost;

    public LiveData<Map> getApplyForHost(Activity activity, HashMap<String, RequestBody> data, MultipartBody.Part image) {
        getApplyForHost = new MutableLiveData<>();
        CommonUtils.showProgressDialog(activity);
        if (CommonUtils.isNetworkConnected(activity)) {
            apiInterface.getApplyForHost(data, image).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {
                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        getApplyForHost.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {
                    CommonUtils.dismissDialog();
                    Log.i("Agora :Get live", t.getMessage());
                }
            });
        } else {
            CommonUtils.dismissDialog();
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return getApplyForHost;
    }

    private MutableLiveData<CountryList> getCountries;

    public LiveData<CountryList> getCountries(Activity activity) {
        getCountries = new MutableLiveData<>();
//        CommonUtils.showProgress(activity,"Loading...");
        apiInterface.getCountries().enqueue(new Callback<CountryList>() {
            @Override
            public void onResponse(@NonNull Call<CountryList> call, @NonNull Response<CountryList> response) {
//                CommonUtils.dismissProgress();
                if (response.body() != null) {
                    getCountries.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CountryList> call, @NonNull Throwable t) {
//                CommonUtils.dismissProgress();
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return getCountries;
    }


}
