package com.expert.blive.retrofit;


import com.expert.blive.ModelClass.PosterImageRoot;
import com.expert.blive.ModelClass.RewordRoot;
import com.expert.blive.ModelClass.VIP.BuyVipRoot;
import com.expert.blive.ModelClass.VIP.VipRoot;
import com.expert.blive.mvvm.TokenGenerateModel;
import com.expert.blive.Adapter.CommentRoot;
import com.expert.blive.Agora.GiftModel;
import com.expert.blive.Agora.agoraLive.activity.GetLiveFriendsListPojo;
import com.expert.blive.Agora.agoraLive.activity.OtherUserDataModel;
import com.expert.blive.Agora.agoraLive.models.LiveDescriptionModel;
import com.expert.blive.Agora.agoraLive.models.ModelGetToken;
import com.expert.blive.Agora.firebase.PKLiveUserModel;
import com.expert.blive.ModelClass.Banner.BannerRoot;
import com.expert.blive.ModelClass.BlockUsersRoot;
import com.expert.blive.ModelClass.CheckStatusRoot;
import com.expert.blive.ModelClass.CoinHistoryRoot;
import com.expert.blive.ModelClass.CoinsAddedModel;
import com.expert.blive.ModelClass.CoinsDeductedModel;
import com.expert.blive.ModelClass.CountryList;
import com.expert.blive.ModelClass.DetailCountry;
import com.expert.blive.ModelClass.EmojiGiftModel;
import com.expert.blive.ModelClass.EndPkLive;
import com.expert.blive.ModelClass.ExchangeCoin;
import com.expert.blive.ModelClass.Family.FamilyRoot;
import com.expert.blive.ModelClass.FollowingDataModel;
import com.expert.blive.ModelClass.FollowingRoot;
import com.expert.blive.ModelClass.FolowCountStatus;
import com.expert.blive.ModelClass.GifRoot;
import com.expert.blive.ModelClass.GiftTopGifters;
import com.expert.blive.ModelClass.GrtFriendsLiveDetails;
import com.expert.blive.ModelClass.Levels.LevelRoot;
import com.expert.blive.ModelClass.LiveUserModel;
import com.expert.blive.ModelClass.LiveUserRequestRoot;
import com.expert.blive.ModelClass.LogOutClass;
import com.expert.blive.ModelClass.ModelAgoraLiveUsers;
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
import java.util.HashMap;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiInterFace {

    @FormUrlEncoded
    @POST("loginPhone")
    Call<Map> loginPhone(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("registerUsers")
    Call<RegisterRoot> register(@Field("username") String username,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("device_type") String device_type,
                                @Field("reg_id") String reg_id,
                                @Field("deviceId") String device_id,
                                @Field("latitude") String latitude,
                                @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("usersLogin")
    Call<RegisterRoot> usersLogin(@Field("username") String username,
                                  @Field("password") String password,
                                  @Field("device_type") String device_type,
                                  @Field("reg_id") String reg_id,
                                  @Field("deviceId") String device_id);

    @FormUrlEncoded
    @POST("endLive")
    Call<Map> endLive(@Field("userId") String userId);

    @GET("getPrimeGift")
    Call<EmojiGiftModel> getEmojiGifts();

    @FormUrlEncoded
    @POST("deductCoin")
    Call<CoinsDeductedModel> deductCoin(@Field("userId") String userId,
                                        @Field("amount") String amount);


    @FormUrlEncoded
    @POST("addCoin")
    Call<CoinsAddedModel> addCoin(@Field("userId") String userId,
                                  @Field("amount") String amount);

    @FormUrlEncoded
    @POST("sendGift")
    Call<SendEmojiGiftModel> sendEmojiGift(@Field("senderId") String senderId,
                                           @Field("receiverId") String receiverId,
                                           @Field("diamond") String amount,
                                           @Field("giftId") String giftId,
                                           @Field("liveId") String liveId);

    @GET("getPrimeGift")
    Call<PrimeGiftModel> getGifts();

    @FormUrlEncoded
    @POST("sendGift")
    Call<GiftSendModel> sendGift(@Field("senderId") String senderId,
                                 @Field("receiverId") String receiverId,
                                 @Field("diamond") String amount,
                                 @Field("giftId") String giftId,
                                 @Field("liveId") String liveId);

    @FormUrlEncoded
    @POST("liveMultiLiveToken")
    Call<TokenGenerateModel> generateToken(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("getLiveMultiLive")
    Call<LiveUserModel> getLiveMultiLive(@Field("userId") String userId);


    @FormUrlEncoded
    @POST("loginRegisterUser")
    Call<OtpClass> otpVerify(@Field("phone") String phone,
                             @Field("otp") String otp,
                             @Field("deviceId") String deviceId,
                             @Field("reg_id") String reg_id,
                             @Field("country") String country,
                             @Field("device_type") String device_type);

    @FormUrlEncoded
    @POST("userLoginNc")
    Call<OtpClass> loginWithUserName(@Field("username") String username,
                                     @Field("password") String password,
                                     @Field("reg_id") String reg_id,
                                     @Field("device_type") String device_type,
                                     @Field("deviceId") String deviceId);

    @Multipart
    @POST("updateUserProfile")
    Call<OtpClass> updateUser(@Part("name") RequestBody name,
                              @Part("gender") RequestBody gender,
                              @Part("bio") RequestBody dob,
                              @Part("latitude") RequestBody latitude,
                              @Part("longitude") RequestBody longitude,
                              @Part("id") RequestBody id,
                              @Part("password") RequestBody password,
                              @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("logoutUser")
    Call<LogOutClass> logout_user(@Field("userId") String id);

    @Multipart
    @POST("uploadVideosNew")
    Call<UploadClass> upload_user(@Part("userId") RequestBody userId,
                                  @Part("hashTag") RequestBody hashTag,
                                  @Part("allowDownloads") RequestBody allowDownloads,
                                  @Part("description") RequestBody description,
                                  @Part("allowComment") RequestBody allowComment,
                                  @Part("allowDuetReact") RequestBody allowDuetReact,
                                  @Part("viewVideo") RequestBody viewVideo,
                                  @Part MultipartBody.Part videoPath,
                                  @Part MultipartBody.Part thumbnail);

    @FormUrlEncoded
    @POST("getVideoo")
    Call<ShowVideoClass> show_video(@Field("startLimit") String startLimit,
                                    @Field("userId") String userId);

    @FormUrlEncoded
    @POST("liveRequest")
    Call<Map> liveRequest(@Field("userId") String userId,
                          @Field("request") String request);

    @FormUrlEncoded
    @POST("publicBulletMessage")
    Call<Map> publicBulletMessage(@Field("userId") String userId,
                                  @Field("type") String type);

    @FormUrlEncoded
    @POST("banLiveUserList")
    Call<ModelBanLiveList> getBanLiveList(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("userBlock")
    Call<Map> banUserFromLive(@Field("userId") String userIdLive,
                              @Field("blockUserId") String userIdViewer);

    @FormUrlEncoded
    @POST("agoraToken")
    Call<ModelGetToken> setAgoraChannel(@Field("channelName") String channelName,
                                        @Field("userId") String userId,
                                        @Field("latitude") String latitude,
                                        @Field("longitude") String longitude,
                                        @Field("hostType") String hostType,
                                        @Field("bool") int bool,
                                        @Field("count") String count);

    @FormUrlEncoded
    @POST("RTMTokenGenrate")
    Call<Map> otherRtmToken(@Field("channelName") String userId);

    @GET("dummyAPp")
    Call<LiveDescriptionModel> liveDescription();

    @FormUrlEncoded
    @POST("userInfo")
    Call<OtherUserDataModel> userProfileData(@Field("userId") String userId,
                                             @Field("loginId") String loginId);

    @FormUrlEncoded
    @POST("boxOpenAPi")
    Call<Map> giftBoxCoin(@Field("userId") String userId,
                          @Field("liveId") String liveId,
                          @Field("box") String box);

    @FormUrlEncoded
    @POST("archivedLive")
    Call<ModelAgoraLiveUsers> stopLiveAgora(@Field("id") String id);

    @GET("liveGiftCategory")
    Call<GiftCategoryModel> giftCategory();

    @FormUrlEncoded
    @POST("getPkUsers")
    Call<PKLiveUserModel> getPkLiveList(@Field("userId") String userId,
                                        @Field("type") String type);

    @FormUrlEncoded
    @POST("sendLiveGift")
    Call<Map> giftLiveSend(@Field("userId") String userId,
                           @Field("coin") String coin,
                           @Field("giftUserId") String giftUserId,
                           @Field("giftId") String giftId,
                           @Field("pkId") String pkHostId,
                           @Field("liveId") String liveId);

    @FormUrlEncoded
    @POST("liveGift")
    Call<GiftModel> getLiveGifts(@Field("userId") String userId,
                                 @Field("giftCategoryId") String giftCategoryId);

    @FormUrlEncoded
    @POST("getLiveUserList")
//    if you want all live user then send 1 in type , if not send 0
    Call<ModelAgoraLiveUsers> getAgoraLiveList(@Field("userId") String userId,
                                               @Field("latitude") String latitude,
                                               @Field("longitude") String longitude,
                                               @Field("type") String type,
                                               @Field("country") String country);

    @FormUrlEncoded
    @POST("get_popular_live_user")
    Call<ModelAgoraLiveUsers> get_popular_live_user(@Field("userId")String userId,
                                                    @Field("country")String country);


    @FormUrlEncoded
    @POST("getHighestCoinAndFollowingLiveUserList")
//    if you want all live user then send 1 in type , if not send 0
    Call<ModelAgoraLiveUsers> getHighestCoinAndFollowingLiveUserList(@Field("userId") String userId,
                                                                     @Field("latitude") String latitude,
                                                                     @Field("longitude") String longitude,
                                                                     @Field("type") String type);


    @Multipart
    @POST("hostAPi")
//    if you want all live user then send 1 in type , if not send 0
    Call<Map> getApplyForHost(@PartMap HashMap<String, RequestBody> data, @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("myLiveFriends")
    Call<GetLiveFriendsListPojo> getLiveFriends(@Field("userId") String userId);

    @GET("getCountries")
    Call<CountryList> getCountries();

    @FormUrlEncoded
    @POST("searchUsers")
    Call<SearchUserRoot> getUsersList(@Field("search") String search,
                                      @Field("userId") String userId);

    @FormUrlEncoded
    @POST("getUserLiveRequestStatus")
    Call<LiveUserRequestRoot> checkStatus(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("userFollow")
    Call<FollowingRoot> followUser(@Field("userId") String userId,
                                   @Field("followingUserId") String followingUserId);


    @FormUrlEncoded
    @POST("getUserUploadVedios")
    Call<VideoRoot2> getUserVideo(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("myLiveStreams")
    Call<MyLiveStreamRoot> getLiveMyStream(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("getFollowingVideos")
    Call<ShowVideoClass> getFollowingVideos(@Field("userId") String userId);

    @GET("getAllUserUploadedVideos")
    Call<ShowVideoClass> getAllVideos();

    @Multipart
    @POST("applyAgency")
    Call<Map> getApplyAgency(@PartMap HashMap<String, RequestBody> data,
                             @Part MultipartBody.Part image,
                             @Part MultipartBody.Part aadharCardFront,
                             @Part MultipartBody.Part panCardFrontPhoto,
                             @Part MultipartBody.Part aadharCardBack,
                             @Part MultipartBody.Part govt_photoId_proof);

    @FormUrlEncoded
    @POST("getAgencyStatus")
    Call<CheckStatusRoot> checkAgencyStatus(@Field("username") String userId);

    @GET("getUserLevels")
    Call<LevelRoot> getLevels();

    @GET("getBanner")
    Call<BannerRoot>getBanner();

    @Multipart
    @POST("userPoster")
    Call<PosterRoot> postPoster(@Part("userId") RequestBody userId,
                                @Part MultipartBody.Part image);

    @FormUrlEncoded
    @POST("socialLoginNc")
    Call<OtpClass> socialLogin(@Field("name") String username,
                               @Field("socialId") String social_id,
                               @Field("email") String email,
                               @Field("reg_id") String reg_id,
                               @Field("latitude") String latitude,
                               @Field("longitude") String longitude,
                               @Field("device_id") String device_id,
                               @Field("country")String country);

    @FormUrlEncoded
    @POST("getUserCoin")
    Call<TotalCoinModal> getCoin(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("userCoinRecieveHistory")
    Call<CoinHistoryRoot> getReceiveCoin(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("userCoinSendHistory")
    Call<CoinHistoryRoot> getSendCoin(@Field("userId") String userId);


    @FormUrlEncoded
    @POST("userLevel")
    Call<MyLevelRoot> getMyLevel(@Field("userId") String userId);


    @FormUrlEncoded
    @POST("userTalentLevel")
    Call<MyTalentLevelRoot> getMyTalentLevel(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("getPurchasedCoin")
    Call<Map> getPurchasedCoin(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("addPurchaseCoin")
    Call<SpinOneModal> addPurchaseCoin(@Field("userId") String userId,
                                       @Field("coin") String coin);

    @FormUrlEncoded
    @POST("minPurchaseCoin")
    Call<SpinOneModal> minPurchaseCoin(@Field("userId") String userId,
                                       @Field("coin") String coin);

    @GET("getUserTalentLevels")
    Call<LevelRoot> getTalentLevels();

    @GET("getGif")
    Call<GifRoot> getGif();

    @FormUrlEncoded
    @POST("setSingleGif")
    Call<GetSingleGifRoot> setUserSingleGif(@Field("userId") String userId,
                                            @Field("gifId") String gifId);

    @FormUrlEncoded
    @POST("getPosterImage")
    Call<PosterImageRoot> getPosterImage(@Field("userId") String userId);

    @Multipart
    @POST("addPosterImage")
    Call<Map> addPosterImage(@Part("userId") RequestBody userId,
                             @Part MultipartBody.Part gifId);

    @FormUrlEncoded
    @POST("getSingleGif")
    Call<GetSingleGifRoot> getUserSingleGif(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("someFunctionality")
    Call<OtherUserDataModel> someFunctionality(@FieldMap HashMap<String, String> data);


    @GET("getCountryDetails")
    Call<DetailCountry> getCountry();

    @GET("getAudioLiveImages")
    Call<StoreImages> getAudioLiveImages();

    @GET("getAllCountries")
    Call<DetailCountry> getAllCountries();

    @FormUrlEncoded
    @POST("weeklyGiftAmount")
    Call<GiftTopGifters> weeklyGiftAmount(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("getTopGifter")
    Call<GiftTopGifters> montlyGiftAmount(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("topGifter")
    Call<TopGifterModel> topGifter(@Field("type") String data);


    @FormUrlEncoded
    @POST("getFollowing")
    Call<FollowingDataModel> getFollowing(@Field("userId") String data);

    @FormUrlEncoded
    @POST("getFollowers")
    Call<FollowingDataModel> getFollowers(@Field("userId") String data);

    @FormUrlEncoded
    @POST("getFriends")
    Call<FollowingDataModel> getFriends(@Field("userId") String data);

    @FormUrlEncoded
    @POST("getAllCounts")
    Call<FolowCountStatus> getAllCounts(@Field("userId") String data);


    @FormUrlEncoded
    @POST("pkBattleArchieved")
    Call<Map> pkBattleArchieved(@Field("pkId") String data);


    @FormUrlEncoded
    @POST("dailyGiftAmount")
    Call<GiftTopGifters> getGiftAmount(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("pkBattle")
    Call<PkBattleModel> pkBattle(@FieldMap HashMap<String, String> data);


    @FormUrlEncoded
    @POST("pkBattleArchieved")
    Call<EndPkLive> archivedPkBattle(@FieldMap HashMap<String, String> data);

    @FormUrlEncoded
    @POST("getPkBattle")
    Call<PKLiveUserModel>getPkBattle(@Field("userId") String s);

    @FormUrlEncoded
    @POST("getpkResult")
    Call<GetWinnerPkBattlePojo> getpkResult(@Field("battleId") String s);

    @FormUrlEncoded
    @POST("getFriendsLiveList")
    Call<GrtFriendsLiveDetails> getFriendsLiveList(@Field("userId") String s);

    @FormUrlEncoded
    @POST("myWallpapers")
    Call<MyWallPaper> myWallpapers(@Field("userId") String s);

    @FormUrlEncoded
    @POST("userStats")
    Call<MothlyModel> userStats(@Field("userId") String s);


    @FormUrlEncoded
    @POST("prurchaseWallpaper")
    Call<PrurchaseWallpaper> prurchaseWallpaper(@FieldMap HashMap<String, String> s);

    @FormUrlEncoded
    @POST("applyWallpaper")
    Call<Map> applyWallpaper(@FieldMap HashMap<String, String> s);

    @FormUrlEncoded
    @POST("exchangeCoins")
    Call<ExchangeCoin> exchangeCoins(@FieldMap HashMap<String, String> s);

    @FormUrlEncoded
    @POST("userAllStats")
    Call<MonthlyHistory> userAllStats(@Field("userId") String s);

    @FormUrlEncoded
    @POST("likeUnlike")
    Call<VideoRoot> likeUnlike(@Field("videoId") String videoId,
                               @Field("otherUserId") String otherUserId);

    @FormUrlEncoded
    @POST("commentsOnUserUploadVideo")
    Call<VideoRoot> commentOnVideo(@Field("userId") String userId,
                                   @Field("videoId") String videoId,
                                   @Field("comment") String comment);

    @FormUrlEncoded
    @POST("getUserVideoComments")
    Call<CommentRoot> videoComments(@Field("videoId") String videoId);

    @FormUrlEncoded
    @POST("userBlock")
    Call<BlockUsersRoot> userBlock(@Field("userId") String userId,
                                   @Field("blockUserId") String blockUserId);

    @FormUrlEncoded
    @POST("getBlockedUsers")
    Call<BlockUsersRoot> getBlockedUsers(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("setVistors")
    Call<VisitRoot> setVistors(@Field("userId") String userId,
                               @Field("otherUserId") String otherUserId);

    @FormUrlEncoded
    @POST("getVisitors")
    Call<FollowingDataModel> getVisitors(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("getFrameByLevel")
    Call<RootFrames> getFrame(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("purchaseFrames")
    Call<RootFrames> purchaseFrames(@Field("userId") String userId,
                                    @Field("frameId") String gifId);

    @FormUrlEncoded
    @POST("getPurchaseFrame")
    Call<RootFrames> getPurchaseFrame(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("applyFrame")
    Call<RootFrames> appliedFrames(@Field("userId") String userId,
                                   @Field("frameId") String frameId);

    @FormUrlEncoded
    @POST("getAppliedFrame")
    Call<RootGetFrame> getAppliedFrames(@Field("userId") String userId,
                                        @Field("frameId") String frameId);

    @FormUrlEncoded
    @POST("likeAndDislikeComments")
    Call<CommentRoot> likeAndDislikeComments(@Field("userId") String userId,
                                             @Field("commentId") String commentId);

    @FormUrlEncoded
    @POST("myVideos")
    Call<VideoRoot2> myVideos(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("myLikes")
    Call<VideoRoot2> myLikes(@Field("id") String userId);


    @FormUrlEncoded
    @POST("shareVideo")
    Call<ShareVideoRoot> shareVideo(@Field("userId") String userId,
                                    @Field("videoId") String videoId);

    @FormUrlEncoded
    @POST("viewVideo")
    Call<ShareVideoRoot> viewVideo(@Field("userId") String userId,
                                   @Field("videoId") String videoId);

    @Multipart
    @POST("createFamily")
    Call<FamilyRoot> createFamily(@Part("userId")RequestBody userId,
                                  @Part("familyName")RequestBody familyName,
                                  @Part("familyDescription")RequestBody familyDescription,
                                  @Part MultipartBody.Part familyImage);
    @FormUrlEncoded
    @POST("getUserFamilyDetails")
    Call<FamilyRoot> getUserFamilyDetails(@Field("userId")String userId);

    @FormUrlEncoded
    @POST("deleteFamily")
    Call<FamilyRoot> deleteFamily(@Field("userId")String userId);

    @FormUrlEncoded
    @POST("send_invite_to_user")
    Call<FamilyRoot> send_invite_to_user(@Field("userId")String userId,
                                         @Field("familyId")String familyId,
                                         @Field("leaderId")String leaderId);

    @FormUrlEncoded
    @POST("my_invitations")
    Call<FamilyRoot> my_invitations(@Field("userId")String userId);

    @FormUrlEncoded
    @POST("all_users")
    Call<FamilyRoot> all_users(@Field("userId")String userId,
                               @Field("familyId")String familyId);

    @FormUrlEncoded
    @POST("all_family_details")
    Call<FamilyRoot> all_family_details(@Field("userId")String userId);

    @FormUrlEncoded
    @POST("send_join_request")
    Call<FamilyRoot> send_join_request(@Field("userId")String userId,
                                       @Field("familyId")String familyId);

    @FormUrlEncoded
    @POST("get_join_requests")
    Call<FamilyRoot> get_join_requests(@Field("leaderId")String leaderId);

    @FormUrlEncoded
    @POST("accept_reject_join_requests")
    Call<FamilyRoot> accept_reject_join_requests(@Field("leaderId")String leaderId,
                                                 @Field("request_id")String request_id,
                                                 @Field("accept")String accept);

    @FormUrlEncoded
    @POST("get_family_details")
    Call<FamilyRoot> get_family_details(@Field("familyId")String familyId);

    @FormUrlEncoded
    @POST("remove_member")
    Call<FamilyRoot> remove_member(@Field("leaderId")String leaderId,
                                   @Field("userId")String userId);

    @FormUrlEncoded
    @POST("leave_family")
    Call<FamilyRoot> leave_family(@Field("userId")String userId);

    @FormUrlEncoded
    @POST("checkBanUserDevice")
    Call<RegisterRoot> checkBanUserDevice(@Field("id")String id,
                                          @Field("deviceId")String deviceId);
    @FormUrlEncoded
    @POST("getGarage")
    Call<RootFrames> getGarage(@Field("userId")String userId);

    @FormUrlEncoded
    @POST("userPurchaseGarage")
    Call<RootFrames> userPurchaseGarage(@Field("userId")String userId,
                                        @Field("garageId")String garageId);

    @FormUrlEncoded
    @POST("getUserGarage")
    Call<RootFrames> getUserGarage(@Field("userId")String userId);

    @FormUrlEncoded
    @POST("setGarage")
    Call<RootFrames> setGarage(@Field("userId")String userId,
                               @Field("garageId")String garageId,
                               @Field("type") String type);
    @FormUrlEncoded
    @POST("deleteVideo")
    Call<RootFrames> deleteVideo(@Field("user_id")String user_id,
                                 @Field("video_id")String video_id);

    @FormUrlEncoded
    @POST("buyVip")
    Call<BuyVipRoot> buyVip(@Field("userId") String userId,
                            @Field("vipId") String vipId);

    @GET("get_vips")
    Call<VipRoot> get_vips(@Query("userId")String userId,
                           @Query("type")String type);

    @GET("get_my_claim")
    Call<RewordRoot> get_my_claim(@Query("userId")String userId);

    @GET("get_claim_dates")
    Call<RewordRoot> get_claim_dates(@Query("userId")String userId);

    @FormUrlEncoded
    @POST("change_settings")
    Call<RewordRoot> change_settings(@Field("userId") String userId,
                                     @Field("switch") String switchh,
                                     @Field("what") String what);
}
