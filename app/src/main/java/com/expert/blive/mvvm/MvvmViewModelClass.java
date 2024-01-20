package com.expert.blive.mvvm;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.expert.blive.Adapter.CommentRoot;
import com.expert.blive.Agora.agoraLive.activity.OtherUserDataModel;
import com.expert.blive.Agora.firebase.PKLiveUserModel;
import com.expert.blive.ModelClass.Banner.BannerRoot;
import com.expert.blive.ModelClass.BlockUsersRoot;
import com.expert.blive.ModelClass.CheckStatusRoot;
import com.expert.blive.ModelClass.CoinHistoryRoot;
import com.expert.blive.ModelClass.CoinsAddedModel;
import com.expert.blive.ModelClass.CoinsDeductedModel;
import com.expert.blive.ModelClass.DetailCountry;
import com.expert.blive.ModelClass.EmojiGiftModel;
import com.expert.blive.ModelClass.EndPkLive;
import com.expert.blive.ModelClass.ExchangeCoin;
import com.expert.blive.ModelClass.Family.FamilyRoot;
import com.expert.blive.ModelClass.FollowingDataModel;
import com.expert.blive.ModelClass.FollowingRoot;
import com.expert.blive.ModelClass.FolowCountStatus;
import com.expert.blive.ModelClass.ModelAgoraLiveUsers;
import com.expert.blive.ModelClass.PosterImageRoot;
import com.expert.blive.ModelClass.RewordRoot;
import com.expert.blive.ModelClass.VIP.BuyVipRoot;
import com.expert.blive.ModelClass.VIP.VipRoot;
import com.expert.blive.retrofit.GetSingleGifRoot;
import com.expert.blive.ModelClass.GifRoot;
import com.expert.blive.ModelClass.GiftTopGifters;
import com.expert.blive.ModelClass.GrtFriendsLiveDetails;
import com.expert.blive.ModelClass.Levels.LevelRoot;
import com.expert.blive.ModelClass.LiveUserModel;
import com.expert.blive.ModelClass.LiveUserRequestRoot;
import com.expert.blive.ModelClass.LogOutClass;
import com.expert.blive.ModelClass.MonthlyHistory;
import com.expert.blive.ModelClass.MothlyModel;
import com.expert.blive.ModelClass.MyLevelRoot;
import com.expert.blive.ModelClass.MyTalentLevelRoot;
import com.expert.blive.ModelClass.MyWallPaper;
import com.expert.blive.ModelClass.OtpClass;
import com.expert.blive.ModelClass.PkBattleModel;
import com.expert.blive.ModelClass.Poster.PosterRoot;
import com.expert.blive.ModelClass.PrimeGiftModel;
import com.expert.blive.ModelClass.PrurchaseWallpaper;
import com.expert.blive.ModelClass.RegisterRoot;
import com.expert.blive.ModelClass.ShowVideoClass;
import com.expert.blive.ModelClass.SpinOneModal;
import com.expert.blive.ModelClass.Store.RootFrames;
import com.expert.blive.ModelClass.Store.RootGetFrame;
import com.expert.blive.ModelClass.StoreImages;
import com.expert.blive.ModelClass.TopGifterModel;
import com.expert.blive.ModelClass.TotalCoinModal;
import com.expert.blive.ModelClass.UploadClass;
import com.expert.blive.ModelClass.Video.ShareVideoRoot;
import com.expert.blive.ModelClass.Video.VideoRoot2;
import com.expert.blive.ModelClass.VideoRoot;
import com.expert.blive.ModelClass.VisitRoot;
import com.expert.blive.ModelClass.myLiveStream.MyLiveStreamRoot;
import com.expert.blive.ModelClass.searchUser.SearchUserRoot;
import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;
import com.expert.blive.retrofit.GetWinnerPkBattlePojo;
import com.expert.blive.retrofit.GiftSendModel;
import com.expert.blive.retrofit.SendEmojiGiftModel;
import com.expert.blive.utils.CommonUtils;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MvvmViewModelClass extends ViewModel {

    ApiInterFace apiInterFace = BaseUrl.getRetrofitClient().create(ApiInterFace.class);

    private MutableLiveData<Map> unique;

    public LiveData<Map> checkEmailOrPhone(Activity activity, String phone) {

        unique = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);

            apiInterFace.loginPhone(phone).enqueue(new Callback<Map>() {

                @Override
                public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {

                    CommonUtils.dismissDialog();

                    if (response.body() != null) {

                        unique.postValue(response.body());

                    } else {
                        Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();

                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return unique;


    }

    private MutableLiveData<OtpClass> verify;

    public LiveData<OtpClass> checkOtpVerfication(Activity activity, String phone, String otp, String deviceId, String regId, String country, String device_type) {

        verify = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);

            apiInterFace.otpVerify(phone, otp, deviceId, regId, country, device_type).enqueue(new Callback<OtpClass>() {

                @Override
                public void onResponse(@NonNull Call<OtpClass> call, @NonNull Response<OtpClass> response) {

                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        verify.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<OtpClass> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return verify;
    }

    private MutableLiveData<OtpClass> update;

    public LiveData<OtpClass> UpdateUserProfile(Activity activity, RequestBody name, RequestBody gender, RequestBody dob, RequestBody latitude, RequestBody longitude, RequestBody id, RequestBody password, MultipartBody.Part image) {

        update = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);

            apiInterFace.updateUser(name, gender, dob, latitude, longitude, id, password, image).enqueue(new Callback<OtpClass>() {

                @Override
                public void onResponse(@NonNull Call<OtpClass> call, @NonNull Response<OtpClass> response) {

                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        update.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<OtpClass> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return update;
    }

    private MutableLiveData<LogOutClass> logout;

    public LiveData<LogOutClass> logOutClassLiveData(Activity activity, String id) {

        logout = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);

            apiInterFace.logout_user(id).enqueue(new Callback<LogOutClass>() {

                @Override
                public void onResponse(@NonNull Call<LogOutClass> call, @NonNull Response<LogOutClass> response) {

                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        logout.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LogOutClass> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return logout;
    }

    private MutableLiveData<UploadClass> upload;

    public LiveData<UploadClass> uploadPostLiveData(Activity activity, RequestBody userId, RequestBody hashTag, RequestBody allowDownloads,
                                                    RequestBody description, RequestBody allowComment, RequestBody allowDuetReact,
                                                    RequestBody viewVideo, MultipartBody.Part videoPath, MultipartBody.Part thumbnail) {

        upload = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.upload_user(userId, hashTag, allowDownloads, description, allowComment, allowDuetReact, viewVideo, videoPath, thumbnail)
                    .enqueue(new Callback<UploadClass>() {

                        @Override
                        public void onResponse(@NonNull Call<UploadClass> call, @NonNull Response<UploadClass> response) {

                            CommonUtils.dismissDialog();
                            if (response.body() != null) {
                                upload.postValue(response.body());
                            } else {
                                Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UploadClass> call, @NonNull Throwable t) {

                            CommonUtils.dismissDialog();
                            Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return upload;
    }

    private MutableLiveData<ShowVideoClass> showVideo;

    public LiveData<ShowVideoClass> showVideoClassLiveData(Activity activity, String startLimit, String userId) {

        showVideo = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.show_video(startLimit, userId).enqueue(new Callback<ShowVideoClass>() {

                @Override
                public void onResponse(@NonNull Call<ShowVideoClass> call, @NonNull Response<ShowVideoClass> response) {

                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        showVideo.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ShowVideoClass> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return showVideo;
    }


    private final MutableLiveData<Map> endLive = new MutableLiveData<>();

    public LiveData<Map> endLive(Activity activity, String userId) {

        apiInterFace.endLive(userId).enqueue(new Callback<Map>() {

            @Override
            public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {

                if (response.code() == 200) {
                    endLive.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return endLive;
    }

    private MutableLiveData<EmojiGiftModel> emojiGiftModelMutableLiveData;

    public LiveData<EmojiGiftModel> emojiGiftModelLiveData(Activity activity) {

        emojiGiftModelMutableLiveData = new MutableLiveData<>();

        apiInterFace.getEmojiGifts().enqueue(new Callback<EmojiGiftModel>() {

            @Override
            public void onResponse(@NonNull Call<EmojiGiftModel> call, @NonNull Response<EmojiGiftModel> response) {

                if (response.body() != null) {
                    emojiGiftModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<EmojiGiftModel> call, @NonNull Throwable t) {

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return emojiGiftModelMutableLiveData;
    }

    private MutableLiveData<SendEmojiGiftModel> sendEmojiGiftModelMutableLiveData;

    public LiveData<SendEmojiGiftModel> sendEmojiGiftModelLiveData(Activity activity, String senderId, String receiverId, String diamond, String giftId, String liveId) {

        sendEmojiGiftModelMutableLiveData = new MutableLiveData<>();

        apiInterFace.sendEmojiGift(senderId, receiverId, diamond, giftId, liveId).enqueue(new Callback<SendEmojiGiftModel>() {

            @Override
            public void onResponse(@NonNull Call<SendEmojiGiftModel> call, @NonNull Response<SendEmojiGiftModel> response) {

                if (response.body() != null) {
                    sendEmojiGiftModelMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<SendEmojiGiftModel> call, @NonNull Throwable t) {

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return sendEmojiGiftModelMutableLiveData;
    }

    private final MutableLiveData<TokenGenerateModel> getGenerateToke = new MutableLiveData<>();

    public LiveData<TokenGenerateModel> getGenerateModel(Activity activity, HashMap<String, String> data) {

        apiInterFace.generateToken(data).enqueue(new Callback<TokenGenerateModel>() {

            @Override
            public void onResponse(@NonNull Call<TokenGenerateModel> call, @NonNull Response<TokenGenerateModel> response) {

                if (response.body() != null) {
                    getGenerateToke.postValue(response.body());
                } else {
                    Toast.makeText(activity, "Body Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<TokenGenerateModel> call, @NonNull Throwable t) {

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return getGenerateToke;
    }


    private MutableLiveData<PrimeGiftModel> primeGiftModelMutableLiveData;

    public LiveData<PrimeGiftModel> giftModelLiveData(Activity activity) {

        primeGiftModelMutableLiveData = new MutableLiveData<>();

        apiInterFace.getGifts().enqueue(new Callback<PrimeGiftModel>() {

            @Override
            public void onResponse(@NonNull Call<PrimeGiftModel> call, @NonNull Response<PrimeGiftModel> response) {

                if (response.body() != null) {
                    primeGiftModelMutableLiveData.postValue(response.body());
                } else {
                    Toast.makeText(activity, "Gift not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<PrimeGiftModel> call, @NonNull Throwable t) {

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return primeGiftModelMutableLiveData;
    }

    private MutableLiveData<GiftSendModel> sendModelMutableLiveData;

    public LiveData<GiftSendModel> sendModelLiveData(Activity activity, String senderId, String receiverId, String diamond, String giftId, String liveId) {

        sendModelMutableLiveData = new MutableLiveData<>();

        apiInterFace.sendGift(senderId, receiverId, diamond, giftId, liveId).enqueue(new Callback<GiftSendModel>() {

            @Override
            public void onResponse(@NonNull Call<GiftSendModel> call, @NonNull Response<GiftSendModel> response) {

                if (response.body() != null) {
                    sendModelMutableLiveData.postValue(response.body());
                } else {
                    Toast.makeText(activity, "Gift not Send", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(@NonNull Call<GiftSendModel> call, @NonNull Throwable t) {

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        return sendModelMutableLiveData;
    }


    private final MutableLiveData<LiveUserModel> getLiveUserApi = new MutableLiveData<>();

    public LiveData<LiveUserModel> getLiveUserApi(Activity activity, String userId) {

        apiInterFace.getLiveMultiLive(userId).enqueue(new Callback<LiveUserModel>() {

            @Override
            public void onResponse(@NonNull Call<LiveUserModel> call, @NonNull Response<LiveUserModel> response) {

                if (response.body() != null) {
                    getLiveUserApi.postValue(response.body());
                } else {
                    Toast.makeText(activity, "Body Null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LiveUserModel> call, @NonNull Throwable t) {

                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        return getLiveUserApi;
    }

    private MutableLiveData<SearchUserRoot> searchUserRootMutableLiveData;

    public LiveData<SearchUserRoot> searchUserRootLiveData(Activity activity,String search, String userId) {

        searchUserRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getUsersList(search, userId).enqueue(new Callback<SearchUserRoot>() {

            @Override
            public void onResponse(@NotNull Call<SearchUserRoot> call, @NotNull Response<SearchUserRoot> response) {

                assert response.body() != null;
                Log.d("onResponse", "fjkfkljf: "+response.body().getMessage());

                    searchUserRootMutableLiveData.postValue(response.body());


            }

            @Override
            public void onFailure(@NotNull Call<SearchUserRoot> call, @NotNull Throwable t) {

                Log.d("onResponse", "fjkfkljf: onfial "+t.getMessage());
                searchUserRootMutableLiveData.postValue(null);

            }
        });
        return searchUserRootMutableLiveData;
    }

    private MutableLiveData<LiveUserRequestRoot> checkStatusRootMutableLiveData;

    public LiveData<LiveUserRequestRoot> checkStatusRootLiveData(String userId) {

        checkStatusRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.checkStatus(userId).enqueue(new Callback<LiveUserRequestRoot>() {

            @Override
            public void onResponse(@NotNull Call<LiveUserRequestRoot> call, @NotNull Response<LiveUserRequestRoot> response) {

                checkStatusRootMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<LiveUserRequestRoot> call, @NotNull Throwable t) {

                checkStatusRootMutableLiveData.postValue(null);
            }
        });

        return checkStatusRootMutableLiveData;
    }

    public MutableLiveData<FollowingRoot> followingRootMutableLiveData;

    public LiveData<FollowingRoot> followingRootLiveData(String userId, String followingUserId) {

        followingRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.followUser(userId, followingUserId).enqueue(new Callback<FollowingRoot>() {

            @Override
            public void onResponse(@NotNull Call<FollowingRoot> call, @NotNull Response<FollowingRoot> response) {

                followingRootMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<FollowingRoot> call, @NotNull Throwable t) {

                followingRootMutableLiveData.postValue(null);
            }
        });
        return followingRootMutableLiveData;
    }

    public MutableLiveData<VideoRoot2> getVideoRootMutableLiveData;

    public LiveData<VideoRoot2> getVideoRootLiveData(String userId) {

        getVideoRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getUserVideo(userId).enqueue(new Callback<VideoRoot2>() {

            @Override
            public void onResponse(@NotNull Call<VideoRoot2> call, @NotNull Response<VideoRoot2> response) {

                getVideoRootMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<VideoRoot2> call, @NotNull Throwable t) {

                getVideoRootMutableLiveData.postValue(null);

            }
        });

        return getVideoRootMutableLiveData;
    }

    public MutableLiveData<MyLiveStreamRoot> myLiveStreamRootMutableLiveData;

    public LiveData<MyLiveStreamRoot> myLiveStreamRootLiveData(String userId) {

        myLiveStreamRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getLiveMyStream(userId).enqueue(new Callback<MyLiveStreamRoot>() {

            @Override
            public void onResponse(@NotNull Call<MyLiveStreamRoot> call, @NotNull Response<MyLiveStreamRoot> response) {

                myLiveStreamRootMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<MyLiveStreamRoot> call, @NotNull Throwable t) {

                myLiveStreamRootMutableLiveData.postValue(null);
            }
        });

        return myLiveStreamRootMutableLiveData;
    }

    private MutableLiveData<ShowVideoClass> getFollowingVideosMutableLiveData;

    public LiveData<ShowVideoClass> getFollowingVideosLiveData(Activity activity,String userId) {

        getFollowingVideosMutableLiveData = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.getFollowingVideos(userId).enqueue(new Callback<ShowVideoClass>() {

                @Override
                public void onResponse(@NonNull Call<ShowVideoClass> call, @NonNull Response<ShowVideoClass> response) {

                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        getFollowingVideosMutableLiveData.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ShowVideoClass> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return getFollowingVideosMutableLiveData;


    }


    private MutableLiveData<ShowVideoClass> getAllVideosMutableLiveData;

    public LiveData<ShowVideoClass> getAllVideosLiveData(Activity activity) {

        getAllVideosMutableLiveData = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.getAllVideos().enqueue(new Callback<ShowVideoClass>() {

                @Override
                public void onResponse(@NonNull Call<ShowVideoClass> call, @NonNull Response<ShowVideoClass> response) {

                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        getAllVideosMutableLiveData.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical  Issue", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ShowVideoClass> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return getAllVideosMutableLiveData;
    }

    public MutableLiveData<Map> agencyMutableLiveData;

    public LiveData<Map> agencyLiveData(HashMap<String, RequestBody> data, MultipartBody.Part image, MultipartBody.Part aadharCardFront,
                                        MultipartBody.Part panCardFrontPhoto, MultipartBody.Part aadharCardBack, MultipartBody.Part govt_photoId_proof) {

        agencyMutableLiveData = new MutableLiveData<>();

        apiInterFace.getApplyAgency(data, image, aadharCardFront, panCardFrontPhoto, aadharCardBack, govt_photoId_proof).enqueue(new Callback<Map>() {

            @Override
            public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {

                agencyMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<Map> call, @NotNull Throwable t) {

                agencyMutableLiveData.postValue(null);

            }
        });

        return agencyMutableLiveData;
    }

    public MutableLiveData<CheckStatusRoot> checkStatusRootAgencyMutableLiveData;

    public LiveData<CheckStatusRoot> agencyStatusRootLiveData(String username) {

        checkStatusRootAgencyMutableLiveData = new MutableLiveData<>();

        apiInterFace.checkAgencyStatus(username).enqueue(new Callback<CheckStatusRoot>() {

            @Override
            public void onResponse(@NotNull Call<CheckStatusRoot> call, @NotNull Response<CheckStatusRoot> response) {

                checkStatusRootAgencyMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<CheckStatusRoot> call, @NotNull Throwable t) {

                checkStatusRootAgencyMutableLiveData.postValue(null);
            }
        });

        return checkStatusRootAgencyMutableLiveData;
    }

    public MutableLiveData<LevelRoot> levelRootMutableLiveData;

    public LiveData<LevelRoot> levelRootLiveData() {

        levelRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getLevels().enqueue(new Callback<LevelRoot>() {

            @Override
            public void onResponse(@NotNull Call<LevelRoot> call, @NotNull Response<LevelRoot> response) {

                levelRootMutableLiveData.setValue(response.body());

            }
            @Override
            public void onFailure(@NotNull Call<LevelRoot> call, @NotNull Throwable t) {

                levelRootMutableLiveData.setValue(null);
            }
        });
        return levelRootMutableLiveData;
    }

    private MutableLiveData<BannerRoot> bannerRootMutableLiveData;

    public LiveData<BannerRoot> bannerRootLiveData() {

        bannerRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getBanner().enqueue(new Callback<BannerRoot>() {

            @Override
            public void onResponse(@NotNull Call<BannerRoot> call, @NotNull Response<BannerRoot> response) {

                if (response.body() !=null) {
                    bannerRootMutableLiveData.setValue(response.body());
                }else {
                    bannerRootMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(@NotNull Call<BannerRoot> call, @NotNull Throwable t) {

                bannerRootMutableLiveData.setValue(null);

            }
        });

        return bannerRootMutableLiveData;
    }

    private MutableLiveData<PosterRoot> posterRootMutableLiveData;

    public LiveData<PosterRoot> posterRootLiveData(RequestBody userId, MultipartBody.Part image) {

        posterRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.postPoster(userId, image).enqueue(new Callback<PosterRoot>() {

            @Override
            public void onResponse(@NonNull Call<PosterRoot> call, @NonNull Response<PosterRoot> response) {

                posterRootMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NonNull Call<PosterRoot> call, @NonNull Throwable t) {

                posterRootMutableLiveData.postValue(null);
            }
        });
        return posterRootMutableLiveData;
    }

    public MutableLiveData<OtpClass> socialLoginRootMutableLiveData;

    public LiveData<OtpClass> socialLoginRootLiveData(Activity activity,String username, String social_id, String email, String reg_id, String latitude, String longitude, String device_id,String country ) {

        socialLoginRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.socialLogin(username, social_id, email, reg_id, latitude, longitude, device_id,country).enqueue(new Callback<OtpClass>() {
            @Override
            public void onResponse(@NotNull Call<OtpClass> call, @NotNull Response<OtpClass> response) {
                if (response.body() !=null) {
                    socialLoginRootMutableLiveData.postValue(response.body());
                }else {
                    socialLoginRootMutableLiveData.postValue(null);
                }
            }
            @Override
            public void onFailure(@NotNull Call<OtpClass> call, @NotNull Throwable t) {

                Toast.makeText(activity, "onFailure    "+t.getMessage(), Toast.LENGTH_SHORT).show();

                socialLoginRootMutableLiveData.postValue(null);
            }
        });
        return socialLoginRootMutableLiveData;
    }

    public MutableLiveData<TotalCoinModal> getCoinRootMutableLiveData;

    public LiveData<TotalCoinModal> getCoinRootLiveData(String userId) {

        getCoinRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getCoin(userId).enqueue(new Callback<TotalCoinModal>() {

            @Override
            public void onResponse(@NotNull Call<TotalCoinModal> call, @NotNull Response<TotalCoinModal> response) {

                getCoinRootMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<TotalCoinModal> call, @NotNull Throwable t) {

                getCoinRootMutableLiveData.postValue(null);

            }
        });

        return getCoinRootMutableLiveData;
    }

    public MutableLiveData<CoinHistoryRoot> coinReceiveHistoryRootMutableLiveData;

    public LiveData<CoinHistoryRoot> coinReceiveHistoryRootLiveData(String userId) {

        coinReceiveHistoryRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getReceiveCoin(userId).enqueue(new Callback<CoinHistoryRoot>() {

            @Override
            public void onResponse(@NotNull Call<CoinHistoryRoot> call, @NotNull Response<CoinHistoryRoot> response) {

                coinReceiveHistoryRootMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<CoinHistoryRoot> call, @NotNull Throwable t) {

                coinReceiveHistoryRootMutableLiveData.postValue(null);
            }
        });

        return coinReceiveHistoryRootMutableLiveData;
    }

    public MutableLiveData<CoinHistoryRoot> coinSendHistoryRootMutableLiveData;

    public LiveData<CoinHistoryRoot> coinSendHistoryRootLiveData(String userId) {

        coinSendHistoryRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getSendCoin(userId).enqueue(new Callback<CoinHistoryRoot>() {

            @Override
            public void onResponse(@NotNull Call<CoinHistoryRoot> call, @NotNull Response<CoinHistoryRoot> response) {

                coinSendHistoryRootMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(@NotNull Call<CoinHistoryRoot> call, @NotNull Throwable t) {

                coinSendHistoryRootMutableLiveData.postValue(null);
            }
        });

        return coinSendHistoryRootMutableLiveData;
    }


    public MutableLiveData<MyLevelRoot> myLevelRootMutableLiveData;

    public LiveData<MyLevelRoot> myLevelRootLiveData(String userId) {

        myLevelRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getMyLevel(userId).enqueue(new Callback<MyLevelRoot>() {

            @Override
            public void onResponse(@NotNull Call<MyLevelRoot> call, @NotNull Response<MyLevelRoot> response) {

                myLevelRootMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<MyLevelRoot> call, @NotNull Throwable t) {

                myLevelRootMutableLiveData.postValue(null);

            }
        });

        return myLevelRootMutableLiveData;
    }

    public MutableLiveData<MyTalentLevelRoot> myTalentLevelRootMutableLiveData;

    public LiveData<MyTalentLevelRoot> myTalentLevelRootLiveData(String userId) {

        myTalentLevelRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getMyTalentLevel(userId).enqueue(new Callback<MyTalentLevelRoot>() {

            @Override
            public void onResponse(@NotNull Call<MyTalentLevelRoot> call, @NotNull Response<MyTalentLevelRoot> response) {

                myTalentLevelRootMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<MyTalentLevelRoot> call, @NotNull Throwable t) {

                myTalentLevelRootMutableLiveData.postValue(null);
            }
        });

        return myTalentLevelRootMutableLiveData;
    }

    public MutableLiveData<SpinOneModal> addPurchaseCoin;

    public LiveData<SpinOneModal> addPurchaseCoin(String userId, String coin) {

        addPurchaseCoin = new MutableLiveData<>();

        apiInterFace.addPurchaseCoin(userId, coin).enqueue(new Callback<SpinOneModal>() {

            @Override
            public void onResponse(@NotNull Call<SpinOneModal> call, @NotNull Response<SpinOneModal> response) {

                addPurchaseCoin.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<SpinOneModal> call, @NotNull Throwable t) {

                addPurchaseCoin.postValue(null);
            }
        });

        return addPurchaseCoin;
    }

    public MutableLiveData<SpinOneModal> minPurchaseCoin;

    public LiveData<SpinOneModal> minPurchaseCoin(String userId, String coin) {

        minPurchaseCoin = new MutableLiveData<>();

        apiInterFace.minPurchaseCoin(userId, coin).enqueue(new Callback<SpinOneModal>() {

            @Override
            public void onResponse(@NotNull Call<SpinOneModal> call, @NotNull Response<SpinOneModal> response) {

                minPurchaseCoin.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<SpinOneModal> call, @NotNull Throwable t) {

                minPurchaseCoin.postValue(null);
            }
        });

        return minPurchaseCoin;
    }


    public MutableLiveData<LevelRoot> levelTalentRootMutableLiveData;

    public LiveData<LevelRoot> levelTalentRootLiveData() {

        levelTalentRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getTalentLevels().enqueue(new Callback<LevelRoot>() {

            @Override
            public void onResponse(@NotNull Call<LevelRoot> call, @NotNull Response<LevelRoot> response) {

                levelTalentRootMutableLiveData.setValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<LevelRoot> call, @NotNull Throwable t) {

                levelTalentRootMutableLiveData.setValue(null);

            }
        });

        return levelTalentRootMutableLiveData;
    }

    public MutableLiveData<Map> getPurchasedCoin;

    public LiveData<Map> getPurchasedCoin(String userId) {

        getPurchasedCoin = new MutableLiveData<>();

        apiInterFace.getPurchasedCoin(userId).enqueue(new Callback<Map>() {

            @Override
            public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {

                getPurchasedCoin.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<Map> call, @NotNull Throwable t) {

                getPurchasedCoin.postValue(null);
            }
        });

        return getPurchasedCoin;
    }

    public MutableLiveData<OtpClass> loginWithUserNameMutableLiveData;

    public LiveData<OtpClass> loginWithUserNameLiveData(String username, String password, String reg_id, String device_type, String deviceId) {

        loginWithUserNameMutableLiveData = new MutableLiveData<>();

        apiInterFace.loginWithUserName(username, password, reg_id, device_type, deviceId).enqueue(new Callback<OtpClass>() {

            @Override
            public void onResponse(@NotNull Call<OtpClass> call, @NotNull Response<OtpClass> response) {

                loginWithUserNameMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<OtpClass> call, @NotNull Throwable t) {

                loginWithUserNameMutableLiveData.postValue(null);

            }
        });

        return loginWithUserNameMutableLiveData;
    }

    public MutableLiveData<GifRoot> gifRootMutableLiveData;

    public LiveData<GifRoot> gifRootLiveData() {

        gifRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getGif().enqueue(new Callback<GifRoot>() {

            @Override
            public void onResponse(@NotNull Call<GifRoot> call, @NotNull Response<GifRoot> response) {

                gifRootMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<GifRoot> call, @NotNull Throwable t) {

                gifRootMutableLiveData.postValue(null);

            }
        });

        return gifRootMutableLiveData;
    }

    public MutableLiveData<GetSingleGifRoot> setSingleGifRootMutableLiveData;

    public LiveData<GetSingleGifRoot> setSingleGifRootLiveData(String userId, String gifId) {

        setSingleGifRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.setUserSingleGif(userId, gifId).enqueue(new Callback<GetSingleGifRoot>() {

            @Override
            public void onResponse(@NotNull Call<GetSingleGifRoot> call, @NotNull Response<GetSingleGifRoot> response) {

                setSingleGifRootMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<GetSingleGifRoot> call, @NotNull Throwable t) {

                setSingleGifRootMutableLiveData.postValue(null);

            }
        });

        return setSingleGifRootMutableLiveData;

    }

    public MutableLiveData<PosterImageRoot> getPosterImage;

    public LiveData<PosterImageRoot> getPosterImage(String userId) {

        getPosterImage = new MutableLiveData<>();

        apiInterFace.getPosterImage(userId).enqueue(new Callback<PosterImageRoot>() {

            @Override
            public void onResponse(@NotNull Call<PosterImageRoot> call, @NotNull Response<PosterImageRoot> response) {

                getPosterImage.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<PosterImageRoot> call, @NotNull Throwable t) {

                getPosterImage.postValue(null);

            }
        });

        return getPosterImage;

    }

    public MutableLiveData<Map> addPosterImage;

    public LiveData<Map> addPosterImage(RequestBody userId, MultipartBody.Part gifId) {

        addPosterImage = new MutableLiveData<>();

        apiInterFace.addPosterImage(userId, gifId).enqueue(new Callback<Map>() {

            @Override
            public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {

                addPosterImage.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<Map> call, @NotNull Throwable t) {

                addPosterImage.postValue(null);

            }
        });

        return addPosterImage;

    }

    public MutableLiveData<GetSingleGifRoot> getSingleGifRootMutableLiveData;

    public LiveData<GetSingleGifRoot> getSingleGifRootLiveData(String userId) {

        getSingleGifRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.getUserSingleGif(userId).enqueue(new Callback<GetSingleGifRoot>() {

            @Override
            public void onResponse(@NotNull Call<GetSingleGifRoot> call, @NotNull Response<GetSingleGifRoot> response) {

                getSingleGifRootMutableLiveData.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<GetSingleGifRoot> call, @NotNull Throwable t) {

                getSingleGifRootMutableLiveData.postValue(null);

            }
        });

        return getSingleGifRootMutableLiveData;
    }

    public MutableLiveData<OtherUserDataModel> someFunctionality;

    public LiveData<OtherUserDataModel> someFunctionality(Activity activity,HashMap<String, String> data) {

        for (Map.Entry <String, String> entry : data.entrySet()){
            Log.d("Entry", "someFunctionality: "+entry.getKey()+ " : " + entry.getValue());
        }
        someFunctionality = new MutableLiveData<>();

        apiInterFace.someFunctionality(data).enqueue(new Callback<OtherUserDataModel>() {

            @Override
            public void onResponse(@NotNull Call<OtherUserDataModel> call, @NotNull Response<OtherUserDataModel> response) {
                if(response.body() !=null){
                    someFunctionality.setValue(response.body());
                }else {
                    someFunctionality.setValue(null);

                }

            }

            @Override
            public void onFailure(@NotNull Call<OtherUserDataModel> call, @NotNull Throwable t) {

                Log.d("FAIL", "onFailure: " + t.getMessage());
                Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                someFunctionality.setValue(null);
            }
        });
        return someFunctionality;
    }

    MutableLiveData<DetailCountry> getCountry;

    public LiveData<DetailCountry> getCountry(final Activity activity) {

        getCountry = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.getCountry().enqueue(new Callback<DetailCountry>() {

                @Override
                public void onResponse(@NonNull Call<DetailCountry> call, @NonNull Response<DetailCountry> response) {

                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        getCountry.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DetailCountry> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return getCountry;
    }


    private MutableLiveData<CoinsAddedModel> coinsAddedModelMutableLiveData;

    public LiveData<CoinsAddedModel> coinsAddedModelLiveData(Activity activity, String userId, String coin) {

        coinsAddedModelMutableLiveData = new MutableLiveData<>();

        apiInterFace.addCoin(userId, coin).enqueue(new Callback<CoinsAddedModel>() {

            @Override
            public void onResponse(@NotNull Call<CoinsAddedModel> call, @NotNull Response<CoinsAddedModel> response) {

                if (response.body() != null) {
                    coinsAddedModelMutableLiveData.setValue(response.body());

                }

            }

            @Override
            public void onFailure(@NotNull Call<CoinsAddedModel> call, @NotNull Throwable t) {

                coinsAddedModelMutableLiveData.setValue(null);
            }
        });
        return coinsAddedModelMutableLiveData;
    }


    private MutableLiveData<CoinsDeductedModel> coinsDeductedModelMutableLiveData;

    public LiveData<CoinsDeductedModel> coinsDeductedModelLiveData(Activity activity, String userId, String coin) {

        coinsDeductedModelMutableLiveData = new MutableLiveData<>();

        apiInterFace.deductCoin(userId, coin).enqueue(new Callback<CoinsDeductedModel>() {

            @Override
            public void onResponse(@NotNull Call<CoinsDeductedModel> call, @NotNull Response<CoinsDeductedModel> response) {

                if (response.body() != null) {
                    coinsDeductedModelMutableLiveData.setValue(response.body());

                }

            }

            @Override
            public void onFailure(@NotNull Call<CoinsDeductedModel> call, @NotNull Throwable t) {

                coinsDeductedModelMutableLiveData.setValue(null);
            }
        });
        return coinsDeductedModelMutableLiveData;
    }

    MutableLiveData<DetailCountry> getAllCountries;

    public LiveData<DetailCountry> getAllCountries(final Activity activity) {

        getAllCountries = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.getAllCountries().enqueue(new Callback<DetailCountry>() {

                @Override
                public void onResponse(@NonNull Call<DetailCountry> call, @NonNull Response<DetailCountry> response) {

                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        getAllCountries.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<DetailCountry> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return getAllCountries;
    }

    MutableLiveData<GiftTopGifters> weeklyGiftAmount;

    public LiveData<GiftTopGifters> weeklyGiftAmount(final Activity activity, HashMap<String, String> data) {

        weeklyGiftAmount = new MutableLiveData<>();
        if ((CommonUtils.isNetworkConnected(activity))) {

            apiInterFace.weeklyGiftAmount(data).enqueue(new Callback<GiftTopGifters>() {

                @Override
                public void onResponse(@NonNull Call<GiftTopGifters> call, @NonNull Response<GiftTopGifters> response) {

                    if (response.body() != null) {
                        weeklyGiftAmount.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GiftTopGifters> call, @NonNull Throwable t) {

                    Log.i("updatePassword", t.getMessage());
                }
            });
        }
        return weeklyGiftAmount;
    }

    MutableLiveData<GiftTopGifters> montlyGiftAmount;


    public LiveData<GiftTopGifters> montlyGiftAmount(final Activity activity, HashMap<String, String> data) {

        montlyGiftAmount = new MutableLiveData<>();
        if ((CommonUtils.isNetworkConnected(activity))) {

            apiInterFace.montlyGiftAmount(data).enqueue(new Callback<GiftTopGifters>() {

                @Override
                public void onResponse(@NonNull Call<GiftTopGifters> call, @NonNull Response<GiftTopGifters> response) {

                    if (response.body() != null) {
                        montlyGiftAmount.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<GiftTopGifters> call, @NonNull Throwable t) {

                    Log.i("updatePassword", t.getMessage());
                }
            });
        }
        return montlyGiftAmount;
    }

    public MutableLiveData<TopGifterModel> topGifter;

    public LiveData<TopGifterModel> topGifter(String userId) {

        topGifter = new MutableLiveData<>();

        apiInterFace.topGifter(userId).enqueue(new Callback<TopGifterModel>() {

            @Override
            public void onResponse(@NotNull Call<TopGifterModel> call, @NotNull Response<TopGifterModel> response) {

                topGifter.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<TopGifterModel> call, @NotNull Throwable t) {

                topGifter.postValue(null);

            }
        });

        return topGifter;
    }


    public MutableLiveData<FollowingDataModel> getFollowing;

    public LiveData<FollowingDataModel> getFollowing(String userId) {

        getFollowing = new MutableLiveData<>();

        apiInterFace.getFollowing(userId).enqueue(new Callback<FollowingDataModel>() {

            @Override
            public void onResponse(@NotNull Call<FollowingDataModel> call, @NotNull Response<FollowingDataModel> response) {

                getFollowing.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<FollowingDataModel> call, @NotNull Throwable t) {

                getFollowing.postValue(null);

            }
        });

        return getFollowing;
    }

    public MutableLiveData<FollowingDataModel> getFollowers;

    public LiveData<FollowingDataModel> getFollowers(String userId) {

        getFollowers = new MutableLiveData<>();

        apiInterFace.getFollowers(userId).enqueue(new Callback<FollowingDataModel>() {

            @Override
            public void onResponse(@NotNull Call<FollowingDataModel> call, @NotNull Response<FollowingDataModel> response) {

                getFollowers.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<FollowingDataModel> call, @NotNull Throwable t) {

                getFollowers.postValue(null);

            }
        });

        return getFollowers;
    }

    public MutableLiveData<FollowingDataModel> getFriends;

    public LiveData<FollowingDataModel> getFriends(String userId) {

        getFriends = new MutableLiveData<>();

        apiInterFace.getFriends(userId).enqueue(new Callback<FollowingDataModel>() {

            @Override
            public void onResponse(@NotNull Call<FollowingDataModel> call, @NotNull Response<FollowingDataModel> response) {

                getFriends.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<FollowingDataModel> call, @NotNull Throwable t) {

                getFriends.postValue(null);

            }
        });

        return getFriends;
    }


    public MutableLiveData<FolowCountStatus> getAllCounts;

    public LiveData<FolowCountStatus> getAllCounts(String userId) {

        getAllCounts = new MutableLiveData<>();

        apiInterFace.getAllCounts(userId).enqueue(new Callback<FolowCountStatus>() {

            @Override
            public void onResponse(@NotNull Call<FolowCountStatus> call, @NotNull Response<FolowCountStatus> response) {

                getAllCounts.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<FolowCountStatus> call, @NotNull Throwable t) {

                getAllCounts.postValue(null);

            }
        });

        return getAllCounts;
    }

    public MutableLiveData<Map> pkBattleArchieved;

    public LiveData<Map> pkBattleArchieved(String userId) {

        pkBattleArchieved = new MutableLiveData<>();

        apiInterFace.pkBattleArchieved(userId).enqueue(new Callback<Map>() {

            @Override
            public void onResponse(@NotNull Call<Map> call, @NotNull Response<Map> response) {

                pkBattleArchieved.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<Map> call, @NotNull Throwable t) {

                pkBattleArchieved.postValue(null);

            }
        });

        return pkBattleArchieved;
    }

    MutableLiveData<PkBattleModel> pkBattle;


    public LiveData<PkBattleModel> pkBattle(final Activity activity, HashMap<String, String> data) {

        pkBattle = new MutableLiveData<>();
        if ((CommonUtils.isNetworkConnected(activity))) {

            apiInterFace.pkBattle(data).enqueue(new Callback<PkBattleModel>() {

                @Override
                public void onResponse(@NonNull Call<PkBattleModel> call, @NonNull Response<PkBattleModel> response) {

                    if (response.body() != null) {
                        pkBattle.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PkBattleModel> call, @NonNull Throwable t) {

                    Log.i("updatePassword", t.getMessage());
                }
            });
        }
        return pkBattle;
    }

    public MutableLiveData<PKLiveUserModel> getPkLiveList;

    public LiveData<PKLiveUserModel> getPkLiveList(String userId, String type) {

        getPkLiveList = new MutableLiveData<>();

        apiInterFace.getPkLiveList(userId, type).enqueue(new Callback<PKLiveUserModel>() {

            @Override
            public void onResponse(@NotNull Call<PKLiveUserModel> call, @NotNull Response<PKLiveUserModel> response) {

                getPkLiveList.postValue(response.body());

            }

            @Override
            public void onFailure(@NotNull Call<PKLiveUserModel> call, @NotNull Throwable t) {

                getPkLiveList.postValue(null);

            }
        });

        return getPkLiveList;
    }


    MutableLiveData<EndPkLive> archivedPkBattle;


    public LiveData<EndPkLive> archivedPkBattle(final Activity activity, HashMap<String, String> data) {

        archivedPkBattle = new MutableLiveData<>();
        if ((CommonUtils.isNetworkConnected(activity))) {

            apiInterFace.archivedPkBattle(data).enqueue(new Callback<EndPkLive>() {

                @Override
                public void onResponse(@NonNull Call<EndPkLive> call, @NonNull Response<EndPkLive> response) {

                    if (response.body() != null) {
                        archivedPkBattle.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<EndPkLive> call, @NonNull Throwable t) {

                    Log.i("updatePassword", t.getMessage());
                }
            });
        }
        return archivedPkBattle;
    }

    public MutableLiveData<PKLiveUserModel> getPkBattle;

    public LiveData<PKLiveUserModel> getPkBattle(String s) {

        getPkBattle = new MutableLiveData<>();


        apiInterFace.getPkBattle(s).enqueue(new Callback<PKLiveUserModel>() {

            @Override
            public void onResponse(@NotNull Call<PKLiveUserModel> call, @NotNull Response<PKLiveUserModel> response) {

                if (response.body() != null) {
                    getPkBattle.setValue(response.body());

                }

            }

            @Override
            public void onFailure(@NotNull Call<PKLiveUserModel> call, @NotNull Throwable t) {

                getPkBattle.setValue(null);

            }
        });

        return getPkBattle;
    }


    public MutableLiveData<GetWinnerPkBattlePojo> getpkResult;

    public LiveData<GetWinnerPkBattlePojo> getpkResult(String s) {

        getpkResult = new MutableLiveData<>();


        apiInterFace.getpkResult(s).enqueue(new Callback<GetWinnerPkBattlePojo>() {

            @Override
            public void onResponse(@NotNull Call<GetWinnerPkBattlePojo> call, @NotNull Response<GetWinnerPkBattlePojo> response) {

                if (response.body() != null) {
                    getpkResult.setValue(response.body());

                }

            }

            @Override
            public void onFailure(@NotNull Call<GetWinnerPkBattlePojo> call, @NotNull Throwable t) {

                getpkResult.setValue(null);

            }
        });

        return getpkResult;
    }

    public MutableLiveData<GrtFriendsLiveDetails> getFriendsLiveList;

    public LiveData<GrtFriendsLiveDetails> getFriendsLiveList(String s) {

        getFriendsLiveList = new MutableLiveData<>();


        apiInterFace.getFriendsLiveList(s).enqueue(new Callback<GrtFriendsLiveDetails>() {

            @Override
            public void onResponse(@NotNull Call<GrtFriendsLiveDetails> call, @NotNull Response<GrtFriendsLiveDetails> response) {

                if (response.body() != null) {
                    getFriendsLiveList.setValue(response.body());

                }

            }

            @Override
            public void onFailure(@NotNull Call<GrtFriendsLiveDetails> call, @NotNull Throwable t) {

                getFriendsLiveList.setValue(null);

            }
        });

        return getFriendsLiveList;
    }


    public MutableLiveData<MyWallPaper> myWallpapers;

    public LiveData<MyWallPaper> myWallpapers(String s) {

        myWallpapers = new MutableLiveData<>();


        apiInterFace.myWallpapers(s).enqueue(new Callback<MyWallPaper>() {

            @Override
            public void onResponse(@NotNull Call<MyWallPaper> call, @NotNull Response<MyWallPaper> response) {

                if (response.body() != null) {
                    myWallpapers.setValue(response.body());

                }

            }

            @Override
            public void onFailure(@NotNull Call<MyWallPaper> call, @NotNull Throwable t) {

                myWallpapers.setValue(null);

            }
        });

        return myWallpapers;
    }

    public MutableLiveData<MothlyModel> userStats;

    public LiveData<MothlyModel> userStats(String s) {

        userStats = new MutableLiveData<>();


        apiInterFace.userStats(s).enqueue(new Callback<MothlyModel>() {

            @Override
            public void onResponse(@NotNull Call<MothlyModel> call, @NotNull Response<MothlyModel> response) {

                if (response.body() != null) {
                    userStats.setValue(response.body());

                }

            }

            @Override
            public void onFailure(@NotNull Call<MothlyModel> call, @NotNull Throwable t) {

                userStats.setValue(null);

            }
        });

        return userStats;
    }


    MutableLiveData<PrurchaseWallpaper> prurchaseWallpaper;


    public LiveData<PrurchaseWallpaper> prurchaseWallpaper(final Activity activity, HashMap<String, String> data) {

        prurchaseWallpaper = new MutableLiveData<>();
        if ((CommonUtils.isNetworkConnected(activity))) {

            apiInterFace.prurchaseWallpaper(data).enqueue(new Callback<PrurchaseWallpaper>() {

                @Override
                public void onResponse(@NonNull Call<PrurchaseWallpaper> call, @NonNull Response<PrurchaseWallpaper> response) {

                    if (response.body() != null) {
                        prurchaseWallpaper.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<PrurchaseWallpaper> call, @NonNull Throwable t) {

                    Log.i("updatePassword", t.getMessage());
                }
            });
        }
        return prurchaseWallpaper;
    }

    MutableLiveData<Map> applyWallpaper;


    public LiveData<Map> applyWallpaper(final Activity activity, HashMap<String, String> data) {

        applyWallpaper = new MutableLiveData<>();
        if ((CommonUtils.isNetworkConnected(activity))) {

            apiInterFace.applyWallpaper(data).enqueue(new Callback<Map>() {

                @Override
                public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {

                    if (response.body() != null) {
                        applyWallpaper.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {

                    Log.i("updatePassword", t.getMessage());
                }
            });
        }
        return applyWallpaper;
    }

    MutableLiveData<ExchangeCoin> exchangeCoins;


    public LiveData<ExchangeCoin> exchangeCoins(final Activity activity, HashMap<String, String> data) {

        exchangeCoins = new MutableLiveData<>();
        if ((CommonUtils.isNetworkConnected(activity))) {

            apiInterFace.exchangeCoins(data).enqueue(new Callback<ExchangeCoin>() {

                @Override
                public void onResponse(@NonNull Call<ExchangeCoin> call, @NonNull Response<ExchangeCoin> response) {

                    if (response.body() != null) {
                        exchangeCoins.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ExchangeCoin> call, @NonNull Throwable t) {

                    Log.i("updatePassword", t.getMessage());
                }
            });
        }
        return exchangeCoins;
    }


    MutableLiveData<MonthlyHistory> userAllStats;


    public LiveData<MonthlyHistory> userAllStats(final Activity activity, String data) {

        userAllStats = new MutableLiveData<>();
        if ((CommonUtils.isNetworkConnected(activity))) {

            apiInterFace.userAllStats(data).enqueue(new Callback<MonthlyHistory>() {

                @Override
                public void onResponse(@NonNull Call<MonthlyHistory> call, @NonNull Response<MonthlyHistory> response) {

                    if (response.body() != null) {
                        userAllStats.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MonthlyHistory> call, @NonNull Throwable t) {

                    Log.i("updatePassword", t.getMessage());
                }
            });
        }
        return userAllStats;
    }

    MutableLiveData<StoreImages> getAudioLiveImages;

    public LiveData<StoreImages> getAudioLiveImages(final Activity activity) {

        getAudioLiveImages = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.getAudioLiveImages().enqueue(new Callback<StoreImages>() {

                @Override
                public void onResponse(@NonNull Call<StoreImages> call, @NonNull Response<StoreImages> response) {

                    CommonUtils.dismissDialog();
                    if (response.body() != null) {
                        getAudioLiveImages.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<StoreImages> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return getAudioLiveImages;
    }

    private MutableLiveData<VideoRoot> mutableLiveData;

    public LiveData<VideoRoot> likeUnlike(String videoId, String otherUserId) {

        mutableLiveData = new MutableLiveData<>();

        apiInterFace.likeUnlike(videoId, otherUserId).enqueue(new Callback<VideoRoot>() {

            @Override
            public void onResponse(@NonNull Call<VideoRoot> call, @NonNull Response<VideoRoot> response) {

                if (response.body() != null) {
                    mutableLiveData.postValue(response.body());
                } else {
                    mutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideoRoot> call, @NonNull Throwable t) {

                mutableLiveData.postValue(null);
            }
        });
        return mutableLiveData;
    }

    private MutableLiveData<VideoRoot> videoRootMutableLiveData;

    public LiveData<VideoRoot> videoRootLiveData(String userId, String videoId, String comment) {


        videoRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.commentOnVideo(userId, videoId, comment).enqueue(new Callback<VideoRoot>() {

            @Override
            public void onResponse(@NonNull Call<VideoRoot> call, @NonNull Response<VideoRoot> response) {

                if (response.body() != null) {
                    videoRootMutableLiveData.postValue(response.body());
                } else {
                    videoRootMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideoRoot> call, @NonNull Throwable t) {

                videoRootMutableLiveData.postValue(null);

            }
        });
        return videoRootMutableLiveData;
    }


    private MutableLiveData<CommentRoot> commentRootMutableLiveData;

    public LiveData<CommentRoot> commentRootLiveData(Activity activity, String videoId) {


        commentRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.videoComments(videoId).enqueue(new Callback<CommentRoot>() {

            @Override
            public void onResponse(@NonNull Call<CommentRoot> call, @NonNull Response<CommentRoot> response) {

                if (response.body() != null) {
                    commentRootMutableLiveData.postValue(response.body());
                } else {
                    commentRootMutableLiveData.postValue(null);
                    Toast.makeText(activity, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentRoot> call, @NonNull Throwable t) {

                commentRootMutableLiveData.postValue(null);
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return commentRootMutableLiveData;
    }

    private MutableLiveData<RegisterRoot> registerMutableLiveData;

    public LiveData<RegisterRoot> registerLiveData(Activity activity, String username, String password, String email, String reg_id, String device_type, String deviceId, String longituude, String latitude) {

        registerMutableLiveData = new MutableLiveData<>();

        apiInterFace.register(username, email, password, reg_id, device_type, deviceId, longituude, latitude).enqueue(new Callback<RegisterRoot>() {

            @Override
            public void onResponse(@NotNull Call<RegisterRoot> call, @NotNull Response<RegisterRoot> response) {

                if (response.body() != null) {
                    registerMutableLiveData.postValue(response.body());
                } else {
                    registerMutableLiveData.postValue(null);

                }

            }

            @Override
            public void onFailure(@NotNull Call<RegisterRoot> call, @NotNull Throwable t) {

                registerMutableLiveData.postValue(null);

            }
        });

        return registerMutableLiveData;
    }

    private MutableLiveData<RegisterRoot> userLoginMutableLiveData;

    public LiveData<RegisterRoot> userLoginLiveData(Activity activity, String username, String password, String reg_id, String device_type, String deviceId, String longituude, String latitude) {

        userLoginMutableLiveData = new MutableLiveData<>();

        apiInterFace.usersLogin(username, password, device_type, reg_id, deviceId).enqueue(new Callback<RegisterRoot>() {

            @Override
            public void onResponse(@NotNull Call<RegisterRoot> call, @NotNull Response<RegisterRoot> response) {

                if (response.body() != null) {
                    userLoginMutableLiveData.postValue(response.body());
                } else {
                    userLoginMutableLiveData.postValue(null);

                }

            }

            @Override
            public void onFailure(@NotNull Call<RegisterRoot> call, @NotNull Throwable t) {

                userLoginMutableLiveData.postValue(null);

            }
        });

        return userLoginMutableLiveData;
    }

    private MutableLiveData<BlockUsersRoot> userBlockMutableLiveData;

    public LiveData<BlockUsersRoot> userBlockLiveData(Activity activity, String userId, String blockUserId) {

        userBlockMutableLiveData = new MutableLiveData<>();

        apiInterFace.userBlock(userId, blockUserId).enqueue(new Callback<BlockUsersRoot>() {

            @Override
            public void onResponse(@NotNull Call<BlockUsersRoot> call, @NotNull Response<BlockUsersRoot> response) {

                if (response.body() != null) {
                    userBlockMutableLiveData.postValue(response.body());
                } else {
                    userBlockMutableLiveData.postValue(null);

                }

            }

            @Override
            public void onFailure(@NotNull Call<BlockUsersRoot> call, @NotNull Throwable t) {

                userBlockMutableLiveData.postValue(null);

            }
        });

        return userBlockMutableLiveData;
    }

    private MutableLiveData<BlockUsersRoot> getBlockedUsersMutableLiveData;

    public LiveData<BlockUsersRoot> getBlockedUsersLiveData(Activity activity, String userId) {

        getBlockedUsersMutableLiveData = new MutableLiveData<>();

        apiInterFace.getBlockedUsers(userId).enqueue(new Callback<BlockUsersRoot>() {

            @Override
            public void onResponse(@NotNull Call<BlockUsersRoot> call, @NotNull Response<BlockUsersRoot> response) {

                if (response.body() != null) {
                    getBlockedUsersMutableLiveData.postValue(response.body());
                } else {
                    getBlockedUsersMutableLiveData.postValue(null);

                }

            }

            @Override
            public void onFailure(@NotNull Call<BlockUsersRoot> call, @NotNull Throwable t) {

                getBlockedUsersMutableLiveData.postValue(null);

            }
        });

        return getBlockedUsersMutableLiveData;
    }

    private MutableLiveData<VisitRoot> mutableLiveData1;

    public LiveData<VisitRoot> setVistors(String userId,String otherUserId){

        mutableLiveData1 = new MutableLiveData<>();

        apiInterFace.setVistors(userId,otherUserId).enqueue(new Callback<VisitRoot>() {

            @Override
            public void onResponse(@NonNull Call<VisitRoot> call, @NonNull Response<VisitRoot> response) {

                if (response.body() !=null){
                    mutableLiveData1.postValue(response.body());
                }else {
                    mutableLiveData1.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<VisitRoot> call, @NonNull Throwable t) {

                mutableLiveData1.postValue(null);
            }
        });
        return mutableLiveData1;
    }
    private MutableLiveData<FollowingDataModel> mutableLiveData2;

    public LiveData<FollowingDataModel> getVisitors(String userId){

        mutableLiveData2 = new MutableLiveData<>();

       apiInterFace.getVisitors(userId).enqueue(new Callback<FollowingDataModel>() {

           @Override
           public void onResponse(@NonNull Call<FollowingDataModel> call, @NonNull Response<FollowingDataModel> response) {

                   mutableLiveData2.postValue(response.body());

           }

           @Override
           public void onFailure(@NonNull Call<FollowingDataModel> call, @NonNull Throwable t) {

               mutableLiveData2.postValue(null);
           }
       });
       return mutableLiveData2;
    }

    //------------------------------------------getFrames-  -----------------------------------------------//

    private MutableLiveData<RootFrames> getFrameMLD;

    public LiveData<RootFrames> getFrames(Activity activity, String userId) {
        getFrameMLD = new MutableLiveData<>();

        if (CommonUtils.isNetworkConnected(activity)) {

            apiInterFace.getFrame(userId).enqueue(new Callback<RootFrames>() {
                @Override
                public void onResponse(@NonNull Call<RootFrames> call, @NonNull Response<RootFrames> response) {
                    if (response.body() != null) {

                        getFrameMLD.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RootFrames> call, @NonNull Throwable t) {

                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
        return getFrameMLD;
    }

    //----------------------------------------Purchased Frame ---------------------------------------//

    private MutableLiveData<RootFrames> purchaseFramesMLD;

    public LiveData<RootFrames> purchaseFrames(Activity activity, String userId, String frameId) {
        purchaseFramesMLD = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.purchaseFrames(userId, frameId).enqueue(new Callback<RootFrames>() {
                @Override
                public void onResponse(@NonNull Call<RootFrames> call, @NonNull Response<RootFrames> response) {
                    if (response.body() != null) {
                        CommonUtils.dismissDialog();
                        purchaseFramesMLD.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<RootFrames> call, @NonNull Throwable t) {
                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();

        }
        return purchaseFramesMLD;
    }

    //=============================appliedFrame ==================================================//

    private MutableLiveData<RootFrames> AppliedFramesMLD;

    public LiveData<RootFrames> appliedFrames(Activity activity, String userId, String frameId) {
        AppliedFramesMLD = new MutableLiveData<>();
        apiInterFace.appliedFrames(userId, frameId).enqueue(new Callback<RootFrames>() {
            @Override
            public void onResponse(@NonNull Call<RootFrames> call, @NonNull Response<RootFrames> response) {
                if (response.body() != null) {

                    AppliedFramesMLD.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RootFrames> call, @NonNull Throwable t) {
                CommonUtils.dismissDialog();
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("reportList", t.getMessage());
            }
        });

        return AppliedFramesMLD;
    }

    private MutableLiveData<RootFrames> getPurchaseFrameMLD;

    public LiveData<RootFrames> getPurchaseFrame(Activity activity, String userId) {
        getPurchaseFrameMLD = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {

            apiInterFace.getPurchaseFrame(userId).enqueue(new Callback<RootFrames>() {
                @Override
                public void onResponse(@NonNull Call<RootFrames> call, @NonNull Response<RootFrames> response) {
                    if (response.body() != null) {

                        getPurchaseFrameMLD.postValue(response.body());
                    }

                }

                @Override
                public void onFailure(@NonNull Call<RootFrames> call, @NonNull Throwable t) {

                    Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("reportList", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();

        }
        return getPurchaseFrameMLD;
    }

    //===================================getAppliedFrames=========================================//

    private MutableLiveData<RootGetFrame> getAppliedFramesMLD;

    public LiveData<RootGetFrame> getAppliedFrames(Activity activity, String userId, String frameId) {
        getAppliedFramesMLD = new MutableLiveData<>();
        apiInterFace.getAppliedFrames(userId, frameId).enqueue(new Callback<RootGetFrame>() {
            @Override
            public void onResponse(@NonNull Call<RootGetFrame> call, @NonNull Response<RootGetFrame> response) {
                if (response.body() != null) {

                    getAppliedFramesMLD.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RootGetFrame> call, @NonNull Throwable t) {
                Toast.makeText(activity, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("reportList", t.getMessage());
            }
        });
        return getAppliedFramesMLD;
    }

    private MutableLiveData<CommentRoot> likeAndDislikeComments;

    public LiveData<CommentRoot> likeAndDislikeComments(String userId,String commentId){

        likeAndDislikeComments = new MutableLiveData<>();

        apiInterFace.likeAndDislikeComments(userId,commentId).enqueue(new Callback<CommentRoot>() {

            @Override
            public void onResponse(@NonNull Call<CommentRoot> call, @NonNull Response<CommentRoot> response) {
                if (response.body() !=null){
                    likeAndDislikeComments.postValue(response.body());
                }else {
                    likeAndDislikeComments.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommentRoot> call, @NonNull Throwable t) {

                likeAndDislikeComments.postValue(null);
            }
        });
        return likeAndDislikeComments;
    }
    private MutableLiveData<VideoRoot2> myVideos;

    public LiveData<VideoRoot2> myVideos(String userId){

        myVideos = new MutableLiveData<>();

        apiInterFace.myVideos(userId).enqueue(new Callback<VideoRoot2>() {

            @Override
            public void onResponse(@NonNull Call<VideoRoot2> call, @NonNull Response<VideoRoot2> response) {
                if (response.body() !=null){
                    myVideos.postValue(response.body());
                }else {
                    myVideos.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideoRoot2> call, @NonNull Throwable t) {

                myVideos.postValue(null);
            }
        });
        return myVideos;
    }

    private MutableLiveData<VideoRoot2> myLikes;

    public LiveData<VideoRoot2> myLikes(String userId){

        myLikes = new MutableLiveData<>();

        apiInterFace.myLikes(userId).enqueue(new Callback<VideoRoot2>() {

            @Override
            public void onResponse(@NonNull Call<VideoRoot2> call, @NonNull Response<VideoRoot2> response) {
                if (response.body() !=null){
                    myLikes.postValue(response.body());
                }else {
                    myLikes.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideoRoot2> call, @NonNull Throwable t) {

                myLikes.postValue(null);
            }
        });
        return myLikes;
    }

    private MutableLiveData<ShareVideoRoot>  shareVideoRootMutableLiveData;

    public LiveData<ShareVideoRoot> shareVideo(Activity activity,String userId,String videoId){

        shareVideoRootMutableLiveData = new MutableLiveData<>();

        apiInterFace.shareVideo(userId,videoId).enqueue(new Callback<ShareVideoRoot>() {

            @Override
            public void onResponse(@NonNull Call<ShareVideoRoot> call, @NonNull Response<ShareVideoRoot> response) {
                if (response.body() != null){
                    shareVideoRootMutableLiveData.postValue(response.body());
                }else {
                    shareVideoRootMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShareVideoRoot> call, @NonNull Throwable t) {
                Toast.makeText(activity, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

//                shareVideoRootMutableLiveData.postValue(null);
            }
        });
        return shareVideoRootMutableLiveData;
    }

    private MutableLiveData<ShareVideoRoot>  viewVideo;

    public LiveData<ShareVideoRoot> viewVideo(String userId,String videoId){

        viewVideo = new MutableLiveData<>();

        apiInterFace.viewVideo(userId,videoId).enqueue(new Callback<ShareVideoRoot>() {

            @Override
            public void onResponse(@NonNull Call<ShareVideoRoot> call, @NonNull Response<ShareVideoRoot> response) {
                if (response.body() != null){
                    viewVideo.postValue(response.body());
                }else {
                    viewVideo.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ShareVideoRoot> call, @NonNull Throwable t) {

                viewVideo.postValue(null);
            }
        });
        return viewVideo;
    }
    private MutableLiveData<FamilyRoot> createFamily;

    public LiveData<FamilyRoot> createFamily(Activity activity, RequestBody userId, RequestBody familyName, RequestBody familyDescription, MultipartBody.Part familyImage){

        createFamily = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.createFamily(userId, familyName, familyDescription, familyImage).enqueue(new Callback<FamilyRoot>() {

                @Override
                public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                    if (response.body() != null) {

                        CommonUtils.dismissDialog();
                        createFamily.postValue(response.body());
                    } else {
                        CommonUtils.dismissDialog();

                        createFamily.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();
                    createFamily.postValue(null);
                }
            });
        } else{
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return createFamily;
    }
    private MutableLiveData<FamilyRoot> getUserFamilyDetails;

    public LiveData<FamilyRoot> getUserFamilyDetails(Activity activity,String userId){

        getUserFamilyDetails = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.getUserFamilyDetails(userId).enqueue(new Callback<FamilyRoot>() {

                @Override
                public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                    if (response.body() != null) {
                        CommonUtils.dismissDialog();

                        getUserFamilyDetails.postValue(response.body());

                    } else {
                        CommonUtils.dismissDialog();

                        getUserFamilyDetails.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {
                    CommonUtils.dismissDialog();

                    getUserFamilyDetails.postValue(null);
                }
            });
        }else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();

        }
        return getUserFamilyDetails;
    }

    private MutableLiveData<FamilyRoot> deleteFamily;

    public LiveData<FamilyRoot> deleteFamily(Activity activity,String userId) {

        deleteFamily = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.deleteFamily(userId).enqueue(new Callback<FamilyRoot>() {

                @Override
                public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                    if (response.body() != null) {
                        CommonUtils.dismissDialog();

                        deleteFamily.postValue(response.body());
                    } else {
                        CommonUtils.dismissDialog();

                        deleteFamily.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {

                    CommonUtils.dismissDialog();

                    deleteFamily.postValue(null);
                }
            });
        }else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();

        }
        return deleteFamily;
    }

    private MutableLiveData<FamilyRoot> send_invite_to_user;

    public LiveData<FamilyRoot> send_invite_to_user(String userId,String familyId,String leaderId){

        send_invite_to_user = new MutableLiveData<>();

        apiInterFace.send_invite_to_user(userId,familyId,leaderId).enqueue(new Callback<FamilyRoot>() {

            @Override
            public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                if (response.body() != null) {
                    send_invite_to_user.postValue(response.body());
                }else {
                    send_invite_to_user.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {

                send_invite_to_user.postValue(null);
            }
        });
        return send_invite_to_user;
    }

    private MutableLiveData<FamilyRoot> my_invitations;

    public LiveData<FamilyRoot> my_invitations(String userId){

        my_invitations = new MutableLiveData<>();

        apiInterFace.my_invitations(userId).enqueue(new Callback<FamilyRoot>() {

            @Override
            public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                if (response.body() != null) {
                    my_invitations.postValue(response.body());
                }else {
                    my_invitations.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {

                my_invitations.postValue(null);
            }
        });
        return my_invitations;
    }


    private MutableLiveData<FamilyRoot> all_users;

    public LiveData<FamilyRoot> all_users(String userId,String familyId){

        all_users = new MutableLiveData<>();

        apiInterFace.all_users(userId,familyId).enqueue(new Callback<FamilyRoot>() {

            @Override
            public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                if (response.body() != null) {
                    all_users.postValue(response.body());
                }else {
                    all_users.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {

                all_users.postValue(null);
            }
        });
        return all_users;
    }

    private MutableLiveData<FamilyRoot> all_family_details;

    public LiveData<FamilyRoot> all_family_details(String userId){

        all_family_details = new MutableLiveData<>();

        apiInterFace.all_family_details(userId).enqueue(new Callback<FamilyRoot>() {

            @Override
            public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                if (response.body() != null) {
                    all_family_details.postValue(response.body());
                }else {
                    all_family_details.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {

                all_family_details.postValue(null);
            }
        });
        return all_family_details;
    }
    private MutableLiveData<FamilyRoot> send_join_request;

    public LiveData<FamilyRoot> send_join_request(String userId,String familyId){

        send_join_request = new MutableLiveData<>();

        apiInterFace.send_join_request(userId,familyId).enqueue(new Callback<FamilyRoot>() {

            @Override
            public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                if (response.body() != null) {
                    send_join_request.postValue(response.body());
                }else {
                    send_join_request.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {

                send_join_request.postValue(null);
            }
        });
        return send_join_request;
    }

    private MutableLiveData<FamilyRoot> get_join_requests;

    public LiveData<FamilyRoot> get_join_requests(Activity activity,String leaderId){

        get_join_requests = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.get_join_requests(leaderId).enqueue(new Callback<FamilyRoot>() {

                @Override
                public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                    if (response.body() != null) {
                        CommonUtils.dismissDialog();
                        get_join_requests.postValue(response.body());
                    } else {
                        CommonUtils.dismissDialog();
                        get_join_requests.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {
                    CommonUtils.dismissDialog();
                    get_join_requests.postValue(null);
                }
            });
        }else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();
        }
        return get_join_requests;
    }

    private MutableLiveData<FamilyRoot> accept_reject_join_requests;

    public LiveData<FamilyRoot> accept_reject_join_requests(Activity activity,String leaderId,String request_id,String accept){

        accept_reject_join_requests = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.accept_reject_join_requests(leaderId, request_id, accept).enqueue(new Callback<FamilyRoot>() {

                @Override
                public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                    if (response.body() != null) {
                        CommonUtils.dismissDialog();
                        accept_reject_join_requests.postValue(response.body());
                    } else {
                        CommonUtils.dismissDialog();
                        accept_reject_join_requests.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {
                    CommonUtils.dismissDialog();
                    accept_reject_join_requests.postValue(null);
                }
            });
        }else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();

        }
        return accept_reject_join_requests;
    }

    private MutableLiveData<FamilyRoot> get_family_details;

    public LiveData<FamilyRoot> get_family_details(String familyId){

        get_family_details = new MutableLiveData<>();

        apiInterFace.get_family_details(familyId).enqueue(new Callback<FamilyRoot>() {

            @Override
            public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                if (response.body() != null) {
                    get_family_details.postValue(response.body());
                }else {
                    get_family_details.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {

                get_family_details.postValue(null);
            }
        });
        return get_family_details;
    }

    private MutableLiveData<FamilyRoot> remove_member;

    public LiveData<FamilyRoot> remove_member(Activity activity,String leaderId,String userId){

        remove_member = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {
            CommonUtils.showProgressDialog(activity);
            apiInterFace.remove_member(leaderId, userId).enqueue(new Callback<FamilyRoot>() {

                @Override
                public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                    if (response.body() != null) {
                        CommonUtils.dismissDialog();

                        remove_member.postValue(response.body());
                    } else {
                        CommonUtils.dismissDialog();

                        remove_member.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {
                    CommonUtils.dismissDialog();

                    remove_member.postValue(null);
                }
            });
        }else {
            Toast.makeText(activity, "Check  Internet Connection", Toast.LENGTH_SHORT).show();

        }
        return remove_member;
    }

    private MutableLiveData<FamilyRoot> leave_family;

    public LiveData<FamilyRoot> leave_family(String userId){

        leave_family = new MutableLiveData<>();

            apiInterFace.leave_family(userId).enqueue(new Callback<FamilyRoot>() {

                @Override
                public void onResponse(@NonNull Call<FamilyRoot> call, @NonNull Response<FamilyRoot> response) {

                    if (response.body() != null) {
                        leave_family.postValue(response.body());
                    } else {
                        leave_family.postValue(null);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<FamilyRoot> call, @NonNull Throwable t) {

                     leave_family.postValue(null);
                }
            });

        return leave_family;
    }

    private MutableLiveData<RegisterRoot> checkBanUserDevice;

    public LiveData<RegisterRoot> checkBanUserDevice(String id,String deviceId){

        checkBanUserDevice = new MutableLiveData<>();

        apiInterFace.checkBanUserDevice(id,deviceId).enqueue(new Callback<RegisterRoot>() {

            @Override
            public void onResponse(@NonNull Call<RegisterRoot> call, @NonNull Response<RegisterRoot> response) {
                if (response.body() !=null){
                    checkBanUserDevice.postValue(response.body());
                }else {
                    checkBanUserDevice.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterRoot> call, @NonNull Throwable t) {

                checkBanUserDevice.postValue(null);
            }
        });
        return checkBanUserDevice;
    }

    private MutableLiveData<RootFrames> getGarage;

    public LiveData<RootFrames> getGarage(String userId){

        getGarage = new MutableLiveData<>();

        apiInterFace.getGarage(userId).enqueue(new Callback<RootFrames>() {

            @Override
            public void onResponse(@NonNull Call<RootFrames> call, @NonNull Response<RootFrames> response) {
                if (response.body() !=null){
                    getGarage.postValue(response.body());
                }else {
                    getGarage.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RootFrames> call, @NonNull Throwable t) {

                getGarage.postValue(null);
            }
        });
        return getGarage;
    }
    private MutableLiveData<RootFrames> userPurchaseGarage;

    public LiveData<RootFrames> userPurchaseGarage(String userId,String garageId){

        userPurchaseGarage = new MutableLiveData<>();

        apiInterFace.userPurchaseGarage(userId,garageId).enqueue(new Callback<RootFrames>() {

            @Override
            public void onResponse(@NonNull Call<RootFrames> call, @NonNull Response<RootFrames> response) {
                if (response.body() !=null){
                    userPurchaseGarage.postValue(response.body());
                }else {
                    userPurchaseGarage.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RootFrames> call, @NonNull Throwable t) {

                userPurchaseGarage.postValue(null);
            }
        });
        return userPurchaseGarage;
    }

    private MutableLiveData<RootFrames> getUserGarage;

    public LiveData<RootFrames> getUserGarage(String userId){

        getUserGarage = new MutableLiveData<>();

        apiInterFace.getUserGarage(userId).enqueue(new Callback<RootFrames>() {

            @Override
            public void onResponse(@NonNull Call<RootFrames> call, @NonNull Response<RootFrames> response) {
                if (response.body() !=null){
                    getUserGarage.postValue(response.body());
                }else {
                    getUserGarage.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RootFrames> call, @NonNull Throwable t) {

                getUserGarage.postValue(null);
            }
        });
        return getUserGarage;
    }

    private MutableLiveData<RootFrames> setGarage;

    public LiveData<RootFrames> setGarage(String userId,String garageId,String type){

        setGarage = new MutableLiveData<>();

        apiInterFace.setGarage(userId,garageId,type).enqueue(new Callback<RootFrames>() {

            @Override
            public void onResponse(@NonNull Call<RootFrames> call, @NonNull Response<RootFrames> response) {
                if (response.body() !=null){
                        setGarage.postValue(response.body());
                }else {
                    setGarage.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RootFrames> call, @NonNull Throwable t) {

                setGarage.postValue(null);
            }
        });
        return setGarage;
    }

    private MutableLiveData<RootFrames> deleteVideo;

    public LiveData<RootFrames> deleteVideo(String user_id,String video_id){

        deleteVideo = new MutableLiveData<>();

        apiInterFace.deleteVideo(user_id,video_id).enqueue(new Callback<RootFrames>() {

            @Override
            public void onResponse(@NonNull Call<RootFrames> call, @NonNull Response<RootFrames> response) {
                if (response.body() !=null){
                    deleteVideo.postValue(response.body());
                }else {
                    deleteVideo.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RootFrames> call, @NonNull Throwable t) {

                deleteVideo.postValue(null);
            }
        });
        return deleteVideo;
    }

    private MutableLiveData<ModelAgoraLiveUsers> get_popular_live_user;

    public LiveData<ModelAgoraLiveUsers> get_popular_live_user(String userId ,String country){

        get_popular_live_user = new MutableLiveData<>();

        apiInterFace.get_popular_live_user(userId,country).enqueue(new Callback<ModelAgoraLiveUsers>() {

            @Override
            public void onResponse(@NonNull Call<ModelAgoraLiveUsers> call, @NonNull Response<ModelAgoraLiveUsers> response) {
                if (response.body() !=null){
                    get_popular_live_user.postValue(response.body());
                }else {
                    get_popular_live_user.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ModelAgoraLiveUsers> call, @NonNull Throwable t) {

                get_popular_live_user.postValue(null);
            }
        });
        return  get_popular_live_user;
    }
    //--------------------------------------------------------BuyVip----------------------------------------------//
    private MutableLiveData<BuyVipRoot> buyVipRootMutableLiveData;

    public LiveData<BuyVipRoot> buyVip(Activity activity, String userId, String vipId) {
        buyVipRootMutableLiveData = new MutableLiveData<>();
        if (CommonUtils.isNetworkConnected(activity)) {

            apiInterFace.buyVip(userId, vipId).enqueue(new Callback<BuyVipRoot>() {
                @Override
                public void onResponse(@NonNull Call<BuyVipRoot> call, @NonNull Response<BuyVipRoot> response) {

                    if (response.body() != null) {
                        buyVipRootMutableLiveData.postValue(response.body());
                    } else {
                        Toast.makeText(activity, "Technical issue", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<BuyVipRoot> call, @NonNull Throwable t) {
                    buyVipRootMutableLiveData.postValue(null);
                    Toast.makeText(activity, "onFailure " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(activity, "Connect to network", Toast.LENGTH_SHORT).show();
        }
        return buyVipRootMutableLiveData;
    }

    private MutableLiveData<VipRoot> get_vips;

    public LiveData<VipRoot> get_vips(String userId,String type){

        get_vips = new MutableLiveData<>();

        apiInterFace.get_vips(userId,type).enqueue(new Callback<VipRoot>() {
            @Override
            public void onResponse(@NonNull Call<VipRoot> call, @NonNull Response<VipRoot> response) {

                if (response.body()!=null){
                    get_vips.postValue(response.body());
                }else {
                    get_vips.postValue(null);
                }
            }
            @Override
            public void onFailure(@NonNull Call<VipRoot> call, @NonNull Throwable t) {

                get_vips.postValue(null);
            }
        });
        return get_vips;
    }

    private MutableLiveData<RewordRoot> get_my_claim;

    public LiveData<RewordRoot> get_my_claim(String userId){

        get_my_claim = new MutableLiveData<>();

        apiInterFace.get_my_claim(userId).enqueue(new Callback<RewordRoot>() {

            @Override
            public void onResponse(@NonNull Call<RewordRoot> call, @NonNull Response<RewordRoot> response) {
                if (response.body() !=null){
                    get_my_claim.postValue(response.body());
                }else {
                    get_my_claim.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RewordRoot> call, @NonNull Throwable t) {

                get_my_claim.postValue(null);
            }
        });
        return get_my_claim;
    }

    private MutableLiveData<RewordRoot> get_claim_dates;

    public LiveData<RewordRoot> get_claim_dates(String userId){

        get_claim_dates = new MutableLiveData<>();

        apiInterFace.get_claim_dates(userId).enqueue(new Callback<RewordRoot>() {

            @Override
            public void onResponse(@NonNull Call<RewordRoot> call, @NonNull Response<RewordRoot> response) {
                if (response.body() !=null){
                    get_claim_dates.postValue(response.body());
                }else {
                    get_claim_dates.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RewordRoot> call, @NonNull Throwable t) {

                get_claim_dates.postValue(null);
            }
        });
        return get_claim_dates;
    }

    private MutableLiveData<RewordRoot> change_settings;

    public LiveData<RewordRoot> change_settings(String userId,String switchh,String what){

        change_settings = new MutableLiveData<>();

        apiInterFace.change_settings(userId,switchh,what).enqueue(new Callback<RewordRoot>() {

            @Override
            public void onResponse(@NonNull Call<RewordRoot> call, @NonNull Response<RewordRoot> response) {
                if (response.body() !=null){
                    change_settings.postValue(response.body());
                }else {
                    change_settings.postValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RewordRoot> call, @NonNull Throwable t) {

                change_settings.postValue(null);
            }
        });
        return change_settings;
    }
}