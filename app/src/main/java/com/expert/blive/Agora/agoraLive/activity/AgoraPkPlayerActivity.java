package com.expert.blive.Agora.agoraLive.activity;

import static android.content.ContentValues.TAG;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.FOLLOWERS;
import static com.expert.blive.Agora.agoraLive.ui.VideoGridContainer.fakeViewController;
import static com.expert.blive.Agora.agoraLive.ui.VideoGridContainer.fakeViewControllerThree;
import static com.expert.blive.Agora.agoraLive.ui.VideoGridContainer.fakeViewControllerTwo;
import static com.expert.blive.Agora.agoraLive.ui.VideoGridContainer.listMultiLiveData;
import static com.expert.blive.Agora.agoraLive.ui.VideoGridContainer.marginHandleForThreeScreen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.bumptech.glide.gifdecoder.StandardGifDecoder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.gif.GifBitmapProvider;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.expert.blive.HomeActivity.Leaderboard.MainLeaderboardFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Agora.agoraLive.MultiLiveHostsModel;
import com.expert.blive.Agora.agoraLive.UserMultiLiveDetailsModel;
import com.expert.blive.Agora.agoraLive.adapters.AdapterMessagesAgora;
import com.expert.blive.Agora.agoraLive.adapters.AdapterTopCoins;
import com.expert.blive.Agora.agoraLive.firebase.ChildEventListenersObject;
import com.expert.blive.Agora.agoraLive.firebase.FirebaseHelper;
import com.expert.blive.Agora.agoraLive.firebase.models.GetBanUserpojo;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSendGift;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSetUserViewer;
import com.expert.blive.Agora.agoraLive.firebase.models.MultiLiveModelCount;
import com.expert.blive.Agora.agoraLive.models.LiveDescriptionModel;
import com.expert.blive.Agora.agoraLive.models.ModelAgoraMessages;
import com.expert.blive.Agora.agoraLive.models.ModelGetToken;
import com.expert.blive.Agora.agoraLive.models.ModelPrivateMessagePlayer;
import com.expert.blive.Agora.agoraLive.models.model.ModelBulletMain;
import com.expert.blive.Agora.agoraLive.stats.LocalStatsData;
import com.expert.blive.Agora.agoraLive.stats.RemoteStatsData;
import com.expert.blive.Agora.agoraLive.stats.StatsData;
import com.expert.blive.Agora.agoraLive.ui.GiftAnimWindow;
import com.expert.blive.Agora.agoraLive.ui.VideoGridContainer;
import com.expert.blive.Agora.agoraLive.utils.Config;
import com.expert.blive.Agora.agoraLive.utils.services.PlayerExitService;
import com.expert.blive.ExtraFragments.MessagesFragment;
//import com.expert.blive.ExtraFragments.TopGifterFragment;
import com.expert.blive.ModelClass.ChatInformation.ChatInformation;
import com.expert.blive.ModelClass.EndPkLive;
import com.expert.blive.ModelClass.ModelBulletMessage;
import com.expert.blive.ModelClass.ModelPkTimeLimit;
import com.expert.blive.ModelClass.ModelUserEntry;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;
import com.expert.blive.retrofit.GetWinnerPkBattlePojo;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.AppConstants;
import com.expert.blive.utils.CommonUtils;
import com.google.android.exoplayer2.Player;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.opensource.svgaplayer.SVGAImageView;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.video.VideoEncoderConfiguration;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgoraPkPlayerActivity extends RtcBaseActivity implements View.OnClickListener, Player.EventListener, UserDetailsFragment.MentionFriend {
    private static final String LIVE_REAL_NAME = "q";
    public static String channelName, boxStatus;
    int liveUserId = 0;
    private SVGAImageView profileFrame,muCarsRVImagee;
    private String otherUserImage = "";
    private VideoGridContainer mVideoGridContainer;
    private List<UserMultiLiveDetailsModel> listMultiLiveDataList = new ArrayList<>();
    //    GridAdapter mVideoGridContainer;
    private ImageView mMuteAudioBtn, iv_smallEntryGif, img_small_video, comment_icon, iv_otherHostImage;
    private ImageView img_follow_unfollow, llMessage;
    private ImageView iv_smallGif;
    private Activity activity;
    RelativeLayout rlCommentBackground;
    private AdapterTopCoins adapterTopCoins;
    private AdapterUserList adapterUserList;

//    private AdaptertWatchLive adaptertWatchLive;
    private final List<ModelSendGift> coinList = new ArrayList<>();
    private AdapterTopGifters adapterTopGifters;
    private MvvmViewModelClass viewModelClass;
    private EditText edit_text_comment;
    private String userChannelId, liveLevel, liveStarCount, liveRealName = "", liveId = "", hostType = "", messageType = "", sound,videoId,frame = "",garage = "";
    private VideoEncoderConfiguration.VideoDimensions mVideoDimension;
    private final List<ModelAgoraMessages> listMessages = new ArrayList<>();
    private AdapterMessagesAgora adapterMessagesAgora;
    private RecyclerView recycler_comment, recyclerViewersOne;
    private TextView tv_views_live;
    private TextView tv_smallGiftName;

    private LottieAnimationView animationReword;
    private int hostId = 0;
    private TextView smallGiftGiftName;
    private TextView tvBigGiftName, iv_profiles;
    private RecyclerView recyclerTopGift;
    private final List<ModelSetUserViewer> listViewers = new ArrayList<>();
    private Config.UserProfile userProfile;
    private TextView tv_total_coins, tv_level, tv_mainBulletMessage, tv_stars, tv_description1, tv_description2, tv_description3, tvBigGiftNameTo, tv_publicMessage, tv_bulletMessage;
    private RelativeLayout rl_viewpager, llMessageEdittext, rlPublicMessage, rl_coins, rl_hideLayout, rl_smallEntry;
    public static String muteLive = "0";

    private LinearLayout ll_WinnerLooser, ll_otheruserDetails,ll_Looser, ll_message_share_gift, ll_giftBox, ll_bigGiftMessage, ll_star_gained, ll_comments;
    private LiveMvvm liveMvvm;
    private List<LiveDescriptionModel.Detail> list = new ArrayList<>();
    //    AndExoPlayerView andExoPlayerView;
    GifImageView gifImageView;
    String pkBattleId = "";
    String otherChannelName = "";
    String otherPkUserId = "";
    private int level;
    private CountDownTimer countDownTimerPkBattle;
    AdapterHostsJoiners adapterHostsJoiners;

    private boolean requestStatus = false;
    private boolean isLive = false;
    private boolean isBattlePlayed = false;
    private TextView tv_tv_timeLeft, smallEntryGiftName, tv_smallEntryName, tv_giftboxcount, upperCoins, bottomCoins, tv_upperHostName, tv_bottomHostName, tv_bottomHostId, tv_hideLayout, tv_showLayout, tv_publicMessageText;
    private final int goneInt = 8, visibleInt = 1;
    private RelativeLayout rl_smallGift;
    private CircleImageView smallGiftImage, cv_bigGiftImage, smallEntryImage;
    //    LottieAnimationView animationView;
    String liveBox, box, myLevel, token, agoraToken, count,mySteriousMan,floating;
    int mute = 0;
    View viewMultiLive;
    RecyclerView viewPager_add;
    CirclePageIndicator indicator;
    private ImageView iv_giftImage, imgWinner, imgLooser;
    int delay = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_agora_pk_player);


        activity = AgoraPkPlayerActivity.this;
        getWindow().setBackgroundDrawableResource(R.drawable.bg_gradient);
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        liveMvvm = new ViewModelProvider(this).get(LiveMvvm.class);
        liveId = getIntent().getStringExtra("liveid");
        agoraToken = getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_RTM_TOKEN);
        channelName = getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_CHANNEL_NAME);
        token = getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_TOKEN);
        count = getIntent().getExtras().getString(AgoraLiveMainActivity.COUNT);
        liveRealName = getIntent().getExtras().getString(AgoraLiveMainActivity.LIVE_REAL_NAME);
        frame = getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_LIVE_FRAME);
        garage = getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_LIVE_GARAGE);
        mySteriousMan = getIntent().getExtras().getString("mySterious");
        FirebaseHelper.getHostId(channelName, valueEventListenerHost);
        App.getSingleton().setMyPkBattle("1");


        Log.d(TAG, "onCreate: +"+token);
        Log.d(TAG, "onCreate: +"+channelName);

        VideoGridContainer.MAX_USER = Integer.parseInt(count);


        userChannelId = getIntent().getExtras().getString(AppConstants.OTHER_USER_ID);
        liveLevel = getIntent().getExtras().getString(AgoraLiveMainActivity.USER_LEVEL);
        liveStarCount = getIntent().getExtras().getString(AgoraLiveMainActivity.USER_STAR_COUNT);


        hostType = getIntent().getExtras().getString("hostType");

        boxStatus = getIntent().getExtras().getString("boxstatus");
        box = getIntent().getExtras().getString("box");
        String folowStatus = getIntent().getExtras().getString(AgoraLiveMainActivity.FOLLOWER_STATUS);


        if (hostType.equalsIgnoreCase("1")) {
            findViewById(R.id.live_sendRequest).setVisibility(View.VISIBLE);
        } else if (hostType.equalsIgnoreCase("2")) {
            findViewById(R.id.live_sendRequest).setVisibility(View.GONE);
        }

        App.getSingleton().setMyPk("1");

        initUI();
        setRecycler();
        setLIveDescription();
//        joinRTmChannel();
        initData();
        initUserIcon();
        getEntry();


        userProfile = new Config.UserProfile();
        String name = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getName();
        if (name.isEmpty()) {
            userProfile.setUserName(username);
        } else {
            userProfile.setUserName(name);
        }

        userProfile.setImageUrl(image);
        userProfile.setRtmToken(agoraToken);
        userProfile.setRtcToken(token);
        userProfile.setToken(token);

        long userId = Integer.parseInt(CommonUtils.getUserId());
        userProfile.setAgoraUid(userId);
        userProfile.setUserId(CommonUtils.getUserId());
        userProfile.setProfileIcon(LoadImageFromWebURL(image));

        if (hostType.equalsIgnoreCase("3")) {
            gifImageView.setVisibility(View.VISIBLE);
            mVideoGridContainer.setVisibility(View.GONE);
        }
        if (hostType.equalsIgnoreCase("1")) {
            findViewById(R.id.live_sendRequest).setVisibility(View.VISIBLE);
            //rl_commentBakcground.setVisibility(View.VISIBLE);

            //findViewById(R.id.ll_linearDummytwo).setVisibility(View.VISIBLE);
            //rl_commentBakcground.setVisibility(View.VISIBLE);
            //findViewById(R.id.ll_linearDummytwo).setVisibility(View.VISIBLE);
            try {
                viewMultiLive.setVisibility(View.VISIBLE);
            } catch (Exception e) {
            }
        } else if (hostType.equalsIgnoreCase("2")) {
            findViewById(R.id.live_sendRequest).setVisibility(View.GONE);
        }

        if (VideoGridContainer.MAX_USER == 5) {
            findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);
            controlFakeViewer();
        } else if (VideoGridContainer.MAX_USER == 6) {
            findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);
            controlFakeViewerTwo();
        } else if (VideoGridContainer.MAX_USER == 9) {
            controlFakeViewerThree();
        }

        clicks();


        getAllHostFirebase();
    }

    private void getEntry() {

        if (garage !=null){
            muCarsRVImagee.setVisibility(View.VISIBLE);
            CommonUtils.setAnimationTwo(AgoraPkPlayerActivity.this,garage,muCarsRVImagee);
        }
    }

    private void clicks() {
        ll_otheruserDetails.setOnClickListener(this);
        llMessage.setOnClickListener(view -> {

            ChatInformation chatInformation = new ChatInformation("Send Message To host", userChannelId, "", "", "", liveRealName, getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_LIVE_IMAGE),"","");
            MessagesFragment userDetailsFragment = new MessagesFragment(chatInformation);
            userDetailsFragment.show(getSupportFragmentManager(), userDetailsFragment.getTag());
        });
    }

    private void controlFakeViewerThree() {

        LinearLayout linearLayoutTwo = findViewById(R.id.fake_eight);


        ImageView imageView, userTwoImage, userThreeImage, userFourImage, userFive, userSix, userSeven, userEight, userNine;

        imageView = findViewById(R.id.userEightOne);
        userTwoImage = findViewById(R.id.userEightTwo);
        userThreeImage = findViewById(R.id.userEightThree);
        userFourImage = findViewById(R.id.userEightFour);
        userFive = findViewById(R.id.userEightFive);
        userSix = findViewById(R.id.userEightSix);
        userSeven = findViewById(R.id.userEightSeven);
        userEight = findViewById(R.id.userEightEight);
        userNine = findViewById(R.id.userEightNine);
        findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);


        fakeViewControllerThree.observe(this, fakeViewControllerModel -> {

//            if (fakeViewControllerModel.sizeOfList == 2) {
//                //fakeViewControllerModel.sizeOfList = 3;
//                findViewById(R.id.ll_linearDummy).setVisibility(View.GONE);
//                marginHandleForThreeScreen.postValue(700);
//            } else {
//                marginHandleForThreeScreen.postValue(0);
//            }

         /*   if (fakeViewControllerModel.sizeOfList ==2) {

            } else {

            }*/

            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);


            params.width = fakeViewControllerModel.getWidthOfView() / 3 - 20;
            params.height = fakeViewControllerModel.getWidthOfView() / 3;
            params.setMargins(8, 8, 8, 8);

            linearLayoutTwo.setBackground(null);
            imageView.setLayoutParams(params);

            userTwoImage.setLayoutParams(params);
            userThreeImage.setLayoutParams(params);
            userFourImage.setLayoutParams(params);
            userFive.setLayoutParams(params);
            userSix.setLayoutParams(params);
            userSeven.setLayoutParams(params);
            userEight.setLayoutParams(params);
            userNine.setLayoutParams(params);

            imageView.setBackgroundColor(getResources().getColor(R.color.black));
            imageView.setImageResource(R.drawable.ic_baseline_add_24);
            userTwoImage.setBackgroundColor(getResources().getColor(R.color.black));
            userTwoImage.setImageResource(R.drawable.ic_baseline_add_24);

            userThreeImage.setBackgroundColor(getResources().getColor(R.color.black));
            userThreeImage.setImageResource(R.drawable.ic_baseline_add_24);
            userFourImage.setBackgroundColor(getResources().getColor(R.color.black));
            userFourImage.setImageResource(R.drawable.ic_baseline_add_24);
            userFive.setBackgroundColor(getResources().getColor(R.color.black));
            userFive.setImageResource(R.drawable.ic_baseline_add_24);
            userSix.setBackgroundColor(getResources().getColor(R.color.black));
            userSix.setImageResource(R.drawable.ic_baseline_add_24);
            userSeven.setBackgroundColor(getResources().getColor(R.color.black));
            userSeven.setImageResource(R.drawable.ic_baseline_add_24);
            userEight.setBackgroundColor(getResources().getColor(R.color.black));
            userEight.setImageResource(R.drawable.ic_baseline_add_24);

            userNine.setBackgroundColor(getResources().getColor(R.color.black));
            userNine.setImageResource(R.drawable.ic_baseline_add_24);



            if (fakeViewControllerModel.sizeOfList > 0) {

                linearLayoutTwo.setVisibility(View.VISIBLE);

                if (fakeViewControllerModel.sizeOfList == 1) {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.VISIBLE);
                    userThreeImage.setVisibility(View.VISIBLE);
                    userFourImage.setVisibility(View.VISIBLE);
                    userFive.setVisibility(View.VISIBLE);
                    userSix.setVisibility(View.VISIBLE);
                    userSeven.setVisibility(View.VISIBLE);
                    userEight.setVisibility(View.VISIBLE);
                    userNine.setVisibility(View.VISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 2) {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.VISIBLE);
                    userFourImage.setVisibility(View.VISIBLE);
                    userFive.setVisibility(View.VISIBLE);
                    userSix.setVisibility(View.VISIBLE);
                    userSeven.setVisibility(View.VISIBLE);
                    userEight.setVisibility(View.VISIBLE);
                    userNine.setVisibility(View.VISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 3) {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.VISIBLE);
                    userFive.setVisibility(View.VISIBLE);
                    userSix.setVisibility(View.VISIBLE);
                    userSeven.setVisibility(View.VISIBLE);
                    userEight.setVisibility(View.VISIBLE);
                    userNine.setVisibility(View.VISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 4) {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.VISIBLE);
                    userSix.setVisibility(View.VISIBLE);
                    userSeven.setVisibility(View.VISIBLE);
                    userEight.setVisibility(View.VISIBLE);
                    userNine.setVisibility(View.VISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 5) {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.INVISIBLE);
                    userSix.setVisibility(View.VISIBLE);
                    userSeven.setVisibility(View.VISIBLE);
                    userEight.setVisibility(View.VISIBLE);
                    userNine.setVisibility(View.VISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 6) {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.INVISIBLE);
                    userSix.setVisibility(View.INVISIBLE);
                    userSeven.setVisibility(View.VISIBLE);
                    userEight.setVisibility(View.VISIBLE);
                    userNine.setVisibility(View.VISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 7) {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.INVISIBLE);
                    userSix.setVisibility(View.INVISIBLE);
                    userSeven.setVisibility(View.INVISIBLE);
                    userEight.setVisibility(View.VISIBLE);
                    userNine.setVisibility(View.VISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 8) {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.INVISIBLE);
                    userSix.setVisibility(View.INVISIBLE);
                    userSeven.setVisibility(View.INVISIBLE);
                    userEight.setVisibility(View.INVISIBLE);
                    userNine.setVisibility(View.VISIBLE);

                } else {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.INVISIBLE);
                    userSix.setVisibility(View.INVISIBLE);
                    userSeven.setVisibility(View.INVISIBLE);
                    userEight.setVisibility(View.INVISIBLE);
                    userNine.setVisibility(View.INVISIBLE);

                }
            }
        });

    }

    @Override
    protected void onGlobalLayoutCompleted() {
    }

    private void controlFakeViewerTwo() {

        LinearLayout linearLayoutTwo = findViewById(R.id.llFakeViewCreateTwo);


        ImageView imageView, userTwoImage, userThreeImage, userFourImage, userFive, userSix;

        imageView = findViewById(R.id.userOneMain);
        userTwoImage = findViewById(R.id.userTwo);
        userThreeImage = findViewById(R.id.userThree);
        userFourImage = findViewById(R.id.userFour);
        userFive = findViewById(R.id.userFive);
        userSix = findViewById(R.id.userSix);


        fakeViewControllerTwo.observe(this, fakeViewControllerModel -> {

//            if (fakeViewControllerModel.sizeOfList == 2) {
//                //fakeViewControllerModel.sizeOfList = 3;
//                findViewById(R.id.ll_linearDummy).setVisibility(View.GONE);
//                marginHandleForThreeScreen.postValue(700);
//            } else {
//                marginHandleForThreeScreen.postValue(0);
//            }

         /*   if (fakeViewControllerModel.sizeOfList ==2) {

            } else {

            }*/

            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            int width = outMetrics.widthPixels;
            int height = outMetrics.heightPixels;


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            LinearLayout.LayoutParams paramsThree = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams paramsFour = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.width = fakeViewControllerModel.getWidthOfView() / 3 + fakeViewControllerModel.getWidthOfView() / 3;
            params.height = fakeViewControllerModel.getWidthOfView() / 3 + fakeViewControllerModel.getWidthOfView() / 3;

            paramsThree.width = fakeViewControllerModel.getWidthOfView() / 3 - 20;
            paramsThree.height = fakeViewControllerModel.getHeightOfView() / 3;

            paramsFour.width = fakeViewControllerModel.getWidthOfView() / 3 - 10;
            paramsFour.height = fakeViewControllerModel.getHeightOfView() / 3;
            params.setMargins(8, 8, 8, 8);
            paramsThree.setMargins(0, 8, 8, 8);
            paramsFour.setMargins(8, 8, 8, 8);

            linearLayoutTwo.setBackground(null);
            imageView.setLayoutParams(params);

            userTwoImage.setLayoutParams(paramsThree);
            userThreeImage.setLayoutParams(paramsThree);
            userFourImage.setLayoutParams(paramsThree);
            userFive.setLayoutParams(paramsFour);
            userSix.setLayoutParams(paramsFour);
            imageView.setBackgroundColor(getResources().getColor(R.color.black));
            imageView.setImageResource(R.drawable.ic_baseline_add_24);
            userTwoImage.setBackgroundColor(getResources().getColor(R.color.black));
            userTwoImage.setImageResource(R.drawable.ic_baseline_add_24);
            userFourImage.setBackgroundColor(getResources().getColor(R.color.black));
            userFourImage.setImageResource(R.drawable.ic_baseline_add_24);
            userThreeImage.setBackgroundColor(getResources().getColor(R.color.black));
            userThreeImage.setImageResource(R.drawable.ic_baseline_add_24);


            userFive.setBackgroundColor(getResources().getColor(R.color.black));
            userFive.setImageResource(R.drawable.ic_baseline_add_24);
            userSix.setBackgroundColor(getResources().getColor(R.color.black));
            userSix.setImageResource(R.drawable.ic_baseline_add_24);


            userTwoImage.setOnClickListener(view -> {

            });
            userFive.setOnClickListener(view -> {

            });
            userThreeImage.setOnClickListener(view -> {

            });

            userSix.setOnClickListener(view -> {

            });
            userFourImage.setOnClickListener(view -> {

            });


            if (fakeViewControllerModel.sizeOfList > 0) {

                linearLayoutTwo.setVisibility(View.VISIBLE);

                if (fakeViewControllerModel.sizeOfList == 1) {


                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.VISIBLE);
                    userThreeImage.setVisibility(View.VISIBLE);
                    userFourImage.setVisibility(View.VISIBLE);
                    userFive.setVisibility(View.VISIBLE);
                    userSix.setVisibility(View.VISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 2) {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.VISIBLE);
                    userFourImage.setVisibility(View.VISIBLE);
                    userFive.setVisibility(View.VISIBLE);
                    userSix.setVisibility(View.VISIBLE);



                } else if (fakeViewControllerModel.sizeOfList == 3) {
                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.VISIBLE);
                    userFive.setVisibility(View.VISIBLE);
                    userSix.setVisibility(View.VISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 4) {
                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.VISIBLE);
                    userSix.setVisibility(View.VISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 5) {
                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.INVISIBLE);
                    userSix.setVisibility(View.VISIBLE);

                } else {
                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.INVISIBLE);
                    userSix.setVisibility(View.INVISIBLE);


                }
            }


//            if (fakeViewControllerModel.sizeOfList == 4) {
////                rlCommentBackground.setVisibility(View.VISIBLE);
//                relative_msg.setBackgroundResource(0);
//                try {
//                    int divideWidth = fakeViewControllerModel.widthOfView / 3;
//                    //int divideHeight = fakeViewControllerModel.heightOfView / 3;
//
//                    int width = fakeViewControllerModel.widthOfView - divideWidth;
//                    int height = fakeViewControllerModel.heightOfView / 3;
//                    findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);
//                    linearLayout.setVisibility(View.VISIBLE);
//
//                    linearLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.user_two));
//
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.MATCH_PARENT);
//
//                    params.width = width;
////                    params.height = height;
//                    //params.bottomMargin = fakeViewControllerModel.bottomMargin;
//                    //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    params.addRule(RelativeLayout.ABOVE, R.id.rl_commentBakcground);
//                    //params.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
//                    linearLayout.setLayoutParams(params);
//
//                    rlCommentBackground.setVisibility(View.VISIBLE);
///*                    rl_commentBakcground.setBackgroundColor(
//                            ContextCompat.getColor(this, getResources().getColor(R.color.black)));
//                    //paramsBlack.addRule(RelativeLayout.BELOW, linearLayout.getId());
//                    paramsBlack.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    paramsBlack.height = fakeViewControllerModel.bottomMargin;
//                    rl_commentBakcground.setLayoutParams(paramsBlack);*/
//                } catch (Exception ignored) {
//                }
//            } else if (fakeViewControllerModel.sizeOfList == 5) {
//                try {
//                    int width = fakeViewControllerModel.widthOfView / 3;
//                    int height = fakeViewControllerModel.heightOfView / 3;
//
//                    linearLayout.setVisibility(View.VISIBLE);
//                    linearLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.user_one));
//
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.MATCH_PARENT);
//                    findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);
//                    params.width = width;
//                    params.height = height;
//                    //params.bottomMargin = fakeViewControllerModel.bottomMargin;
//                    //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    params.addRule(RelativeLayout.ABOVE, R.id.rl_commentBakcground);
//                    //params.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
//                    linearLayout.setLayoutParams(params);
//
////                    rlCommentBackground.setVisibility(View.VISIBLE);
//                } catch (Exception ignored) {
//                }
//            } else if (fakeViewControllerModel.sizeOfList == 7) {
//                try {
//                    int width = fakeViewControllerModel.widthOfView / 4;
//                    int height = fakeViewControllerModel.heightOfView / 4;
//
//                    linearLayout.setVisibility(View.VISIBLE);
//                    linearLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.user_one));
//
//                    findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.MATCH_PARENT);
//
//                    params.width = width;
//                    params.height = height;
//                    //params.bottomMargin = fakeViewControllerModel.bottomMargin;
//                    //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    params.addRule(RelativeLayout.ABOVE, R.id.rl_commentBakcground);
//                    //params.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
//                    linearLayout.setLayoutParams(params);
//
////                    rlCommentBackground.setVisibility(View.VISIBLE);
//
//                } catch (Exception ignored) {
//                }
//            } else if (fakeViewControllerModel.sizeOfList == 1 || fakeViewControllerModel.sizeOfList == 0) {
////                rlCommentBackground.setVisibility(View.GONE);
//                linearLayout.setVisibility(View.GONE);
//                findViewById(R.id.ll_linearDummy).setVisibility(View.GONE);
//            }
        });


    }
    private void getAllHostFirebase() {
        FirebaseHelper.getLiveGuestsList(channelName, listenerMulti);
        //FirebaseHelper.getLiveGuestsList(channelName, multiLiveData);
    }

    ValueEventListener listenerMulti = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                listMultiLiveDataList.clear();
                MultiLiveHostsModel multiLiveHostsModel = snapshot.getValue(MultiLiveHostsModel.class);
                listMultiLiveDataList = multiLiveHostsModel.getUserMultiLiveDetailsModels();
                listMultiLiveData.postValue(multiLiveHostsModel);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    private void controlFakeViewer() {


        fakeViewController.observe(this, fakeViewControllerModel -> {

//            if (fakeViewControllerModel.sizeOfList == 2) {
//                //fakeViewControllerModel.sizeOfList = 3;
//                findViewById(R.id.ll_linearDummy).setVisibility(View.GONE);
//                marginHandleForThreeScreen.postValue(700);
//            } else {
//                marginHandleForThreeScreen.postValue(0);
//            }

         /*   if (fakeViewControllerModel.sizeOfList ==2) {

            } else {

            }*/

            findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);

            LinearLayout linearLayoutOne = findViewById(R.id.llFakeViewCreate);

            ImageView imageView, userTwoImage, userThreeImage, userFourImage;

            imageView = findViewById(R.id.userOneImage);
            userTwoImage = findViewById(R.id.userTwoImage);
            userThreeImage = findViewById(R.id.userThreeImage);
            userFourImage = findViewById(R.id.userFourImage);

            WindowManager windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics outMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(outMetrics);
            int width = outMetrics.widthPixels;
            int height = outMetrics.heightPixels;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.width = fakeViewControllerModel.getWidthOfView() / 2;
            params.height = fakeViewControllerModel.getHeightOfView() / 2;
            params.setMargins(8, 8, 8, 8);
            linearLayoutOne.setBackground(null);
            imageView.setLayoutParams(params);

            userTwoImage.setLayoutParams(params);
            userThreeImage.setLayoutParams(params);
            userFourImage.setLayoutParams(params);

            imageView.setBackgroundColor(getResources().getColor(R.color.black));
            imageView.setImageResource(R.drawable.ic_baseline_add_24);
            userTwoImage.setBackgroundColor(getResources().getColor(R.color.black));
            userTwoImage.setImageResource(R.drawable.ic_baseline_add_24);
            userFourImage.setBackgroundColor(getResources().getColor(R.color.black));
            userFourImage.setImageResource(R.drawable.ic_baseline_add_24);
            userThreeImage.setBackgroundColor(getResources().getColor(R.color.black));
            userThreeImage.setImageResource(R.drawable.ic_baseline_add_24);
            Log.d("controlFakeViewer", "sizeee: "+fakeViewControllerModel.sizeOfList);
            if (fakeViewControllerModel.sizeOfList > 0) {
                Log.d("controlFakeViewer", "controlFakeViewer: "+"1");

                linearLayoutOne.setVisibility(View.VISIBLE);


                if (fakeViewControllerModel.sizeOfList == 1) {
                    Log.d("controlFakeViewer", "controlFakeViewer: "+"2");

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.VISIBLE);
                    userThreeImage.setVisibility(View.VISIBLE);
                    userFourImage.setVisibility(View.VISIBLE);



                } else if (fakeViewControllerModel.sizeOfList == 2) {
                    Log.d("controlFakeViewer", "controlFakeViewer: "+"3");

                    imageView.setVisibility(View.INVISIBLE);

                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.VISIBLE);
                    userFourImage.setVisibility(View.VISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 3) {
                    Log.d("controlFakeViewer", "controlFakeViewer: "+"4");

                    imageView.setVisibility(View.INVISIBLE);

                    userTwoImage.setVisibility(View.INVISIBLE);

                    userThreeImage.setVisibility(View.INVISIBLE);

                    userFourImage.setVisibility(View.VISIBLE);

                } else {
                    Log.d("controlFakeViewer", "controlFakeViewer: "+"5");

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);

                }
            }


//            if (fakeViewControllerModel.sizeOfList == 4) {
////                rlCommentBackground.setVisibility(View.VISIBLE);
//                relative_msg.setBackgroundResource(0);
//                try {
//                    int divideWidth = fakeViewControllerModel.widthOfView / 3;
//                    //int divideHeight = fakeViewControllerModel.heightOfView / 3;
//
//                    int width = fakeViewControllerModel.widthOfView - divideWidth;
//                    int height = fakeViewControllerModel.heightOfView / 3;
//                    findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);
//                    linearLayout.setVisibility(View.VISIBLE);
//
//                    linearLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.user_two));
//
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.MATCH_PARENT);
//
//                    params.width = width;
////                    params.height = height;
//                    //params.bottomMargin = fakeViewControllerModel.bottomMargin;
//                    //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    params.addRule(RelativeLayout.ABOVE, R.id.rl_commentBakcground);
//                    //params.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
//                    linearLayout.setLayoutParams(params);
//
//                    rlCommentBackground.setVisibility(View.VISIBLE);
///*                    rl_commentBakcground.setBackgroundColor(
//                            ContextCompat.getColor(this, getResources().getColor(R.color.black)));
//                    //paramsBlack.addRule(RelativeLayout.BELOW, linearLayout.getId());
//                    paramsBlack.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    paramsBlack.height = fakeViewControllerModel.bottomMargin;
//                    rl_commentBakcground.setLayoutParams(paramsBlack);*/
//                } catch (Exception ignored) {
//                }
//            } else if (fakeViewControllerModel.sizeOfList == 5) {
//                try {
//                    int width = fakeViewControllerModel.widthOfView / 3;
//                    int height = fakeViewControllerModel.heightOfView / 3;
//
//                    linearLayout.setVisibility(View.VISIBLE);
//                    linearLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.user_one));
//
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.MATCH_PARENT);
//                    findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);
//                    params.width = width;
//                    params.height = height;
//                    //params.bottomMargin = fakeViewControllerModel.bottomMargin;
//                    //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    params.addRule(RelativeLayout.ABOVE, R.id.rl_commentBakcground);
//                    //params.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
//                    linearLayout.setLayoutParams(params);
//
////                    rlCommentBackground.setVisibility(View.VISIBLE);
//                } catch (Exception ignored) {
//                }
//            } else if (fakeViewControllerModel.sizeOfList == 7) {
//                try {
//                    int width = fakeViewControllerModel.widthOfView / 4;
//                    int height = fakeViewControllerModel.heightOfView / 4;
//
//                    linearLayout.setVisibility(View.VISIBLE);
//                    linearLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.user_one));
//
//                    findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);
//                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                            LinearLayout.LayoutParams.MATCH_PARENT,
//                            LinearLayout.LayoutParams.MATCH_PARENT);
//
//                    params.width = width;
//                    params.height = height;
//                    //params.bottomMargin = fakeViewControllerModel.bottomMargin;
//                    //params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    params.addRule(RelativeLayout.ABOVE, R.id.rl_commentBakcground);
//                    //params.addRule(RelativeLayout.ALIGN_BOTTOM, RelativeLayout.TRUE);
//                    linearLayout.setLayoutParams(params);
//
////                    rlCommentBackground.setVisibility(View.VISIBLE);
//
//                } catch (Exception ignored) {
//                }
//            } else if (fakeViewControllerModel.sizeOfList == 1 || fakeViewControllerModel.sizeOfList == 0) {
////                rlCommentBackground.setVisibility(View.GONE);
//                linearLayout.setVisibility(View.GONE);
//                findViewById(R.id.ll_linearDummy).setVisibility(View.GONE);
//            }
        });
    }


    private void openBottom() {

        Dialog dialogChooseAlbum = new Dialog(this);
        dialogChooseAlbum.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChooseAlbum.setContentView(R.layout.bottom_viewers);
        dialogChooseAlbum.setCanceledOnTouchOutside(true);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialogChooseAlbum.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.5f);
        int dialogWindowHeight = (int) (displayHeight * 0.5f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        dialogChooseAlbum.getWindow().setAttributes(layoutParams);
        dialogChooseAlbum.getWindow().setLayout(layoutParams.width, layoutParams.height);
        dialogChooseAlbum.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 600);
        dialogChooseAlbum.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogChooseAlbum.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTwo;
        dialogChooseAlbum.getWindow().setGravity(Gravity.BOTTOM);

        LinearLayout llGifts, llUser;

        RecyclerView recyclerViewers, recyclerViewersGifts;
        View viewGifts, viewUser;
        TextView txtUser, txtGifts, txtNoViewer, txtNoGifts;
        RelativeLayout rl_gifter, rl_viewer;
        txtNoViewer = dialogChooseAlbum.findViewById(R.id.txtNoViewer);

        recyclerViewers = dialogChooseAlbum.findViewById(R.id.recyclerViewers);

        llUser = dialogChooseAlbum.findViewById(R.id.llUser);
        if (listViewers.size() <= 0) {
            txtNoViewer.setVisibility(View.VISIBLE);
        } else {
            AdaptertWatchLive adaptertWatchLive = new AdaptertWatchLive(listViewers, new AdaptertWatchLive.UserInfo() {
                @Override
                public void sendData(ModelSetUserViewer listviewers) {

                    openOtherUserProfile(listviewers.getUserId(), true);

                }
            });
            recyclerViewers.setAdapter(adaptertWatchLive);
        }

//        AdaptertWatchLive adaptertWatchLive = new AdaptertWatchLive(listViewers, new AdaptertWatchLive.UserInfo() {
//            @Override
//            public void sendData(ModelSetUserViewer listviewers) {
//
//                openOtherUserProfile(listviewers.getUserId(), true);
//
//            }
//        });
//        recyclerViewers.setAdapter(adaptertWatchLive);


        dialogChooseAlbum.show();


    }


    @SuppressLint("SetTextI18n")
    private void setEntry() {

        HashMap<String, String> data = new HashMap<>();
        data.put("userId", CommonUtils.getUserId());
        data.put("otherUserId", CommonUtils.getUserId());
        viewModelClass.someFunctionality(this, data).observe(AgoraPkPlayerActivity.this, otherUserDataModel -> {
            if (otherUserDataModel !=null){
                if (otherUserDataModel.getStatus().equalsIgnoreCase("1")) {
                    floating = String.valueOf(otherUserDataModel.getDetails().getBuy());
                    myLevel = otherUserDataModel.getDetails().getMyLevel();
                    try {
                        level = Integer.parseInt(myLevel);
                    } catch (NumberFormatException ignored) {
                    }
                    if (level > 0) {

                        if (level <= 30) {
                            ModelUserEntry modelUserEntry = new ModelUserEntry();
                            modelUserEntry.setName(otherUserDataModel.getDetails().getName());
                            modelUserEntry.setImage(otherUserDataModel.getDetails().getImage());
                            if (otherUserDataModel.getDetails().getAnimationTitle() == null) {
                                modelUserEntry.setGiftname("No animation");

                            } else {

                                modelUserEntry.setGiftname(otherUserDataModel.getDetails().getAnimationTitle());


                            }

                            modelUserEntry.setGiftimage(otherUserDataModel.getDetails().getAnimationImage());
                            modelUserEntry.setLevel(otherUserDataModel.getDetails().getMyLevel());
                            FirebaseHelper.entryUser(channelName, modelUserEntry);


                            try {
                                Glide.with(activity).load(otherUserDataModel.getDetails().getImage()).into(smallEntryImage);
                                Glide.with(activity).load(otherUserDataModel.getDetails().getAnimationImage()).into(iv_smallEntryGif);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (otherUserDataModel.getDetails().getAnimationTitle() == null) {
                                smallEntryGiftName.setText("arrived with a  No animation");


                            } else {

                                smallEntryGiftName.setText("arrived with a " + otherUserDataModel.getDetails().getAnimationTitle());


                            }
                            tv_smallEntryName.setText(otherUserDataModel.getDetails().getName());


                            rl_smallEntry.setVisibility(View.VISIBLE);
                            Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.reversefast);
                            rl_smallEntry.startAnimation(animation2);
                            final Handler threadHandler = new Handler();
                            new Thread() {
                                @Override
                                public void run() {
                                    threadHandler.postDelayed(() -> {
                                        rl_smallEntry.clearAnimation();
                                        rl_smallEntry.setVisibility(View.GONE);
                                    }, animation2.getDuration());
                                }
                            }.start();
                        } else {
                            ModelUserEntry modelUserEntry = new ModelUserEntry();
                            modelUserEntry.setName(otherUserDataModel.getDetails().getName());
                            modelUserEntry.setImage(otherUserDataModel.getDetails().getImage());
                            if (otherUserDataModel.getDetails().getAnimationTitle() != null) {
                                modelUserEntry.setGiftname(otherUserDataModel.getDetails().getAnimationTitle());

                            } else {
                                modelUserEntry.setGiftname("animation");

                            }
                            modelUserEntry.setGiftimage(otherUserDataModel.getDetails().getAnimationImage());
                            modelUserEntry.setLevel(otherUserDataModel.getDetails().getMyLevel());
                            FirebaseHelper.entryUser(channelName, modelUserEntry);
                            try {
                                Glide.with(activity).load(otherUserDataModel.getDetails().getImage()).into(smallEntryImage);
                                Glide.with(activity).load(otherUserDataModel.getDetails().getAnimationImage()).into(iv_smallEntryGif);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            tv_smallEntryName.setText(otherUserDataModel.getDetails().getName());
                            if (otherUserDataModel.getDetails().getAnimationTitle() == null) {
                                smallEntryGiftName.setText("arrived with a  No animation");


                            } else {

                                smallEntryGiftName.setText("arrived with a " + otherUserDataModel.getDetails().getAnimationTitle());


                            }
                            rl_smallEntry.setVisibility(View.VISIBLE);
                            Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.reversefast);
                            rl_smallEntry.startAnimation(animation2);
                            final Handler threadHandler = new Handler();
                            new Thread() {
                                @Override
                                public void run() {
                                    threadHandler.postDelayed(() -> {
                                        rl_smallEntry.clearAnimation();
                                        rl_smallEntry.setVisibility(View.GONE);
                                    }, animation2.getDuration());
                                }
                            }.start();
                            try {
                                GiftAnimWindow window = new GiftAnimWindow(AgoraPkPlayerActivity.this, R.style.gift_anim_window);
                                window.getWindow().setLayout(1080, 1920);
                                window.setAnimResource(otherUserDataModel.getDetails().getAnimationImage());
                                window.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else {
                    Toast.makeText(activity, "0   "+otherUserDataModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(activity, "Technical issue...", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void setLIveDescription() {

        setupadd(coinList);
        liveMvvm.liveDescriptionModelLiveData(this).observe(AgoraPkPlayerActivity.this, liveDescriptionModel -> {
            if (liveDescriptionModel.getSuccess().equalsIgnoreCase("1")) {
                list = liveDescriptionModel.getDetails();

                ModelAgoraMessages modelAgoraMessages = new ModelAgoraMessages(list.get(0).getFrist(), image, userProfile.getUserName(), CommonUtils.getUserId(), String.valueOf(level), 0, adminStatuscheck,System.currentTimeMillis(),mySteriousMan);
                listMessages.add(modelAgoraMessages);

                modelAgoraMessages = new ModelAgoraMessages(list.get(0).getSec(), image, userProfile.getUserName(), CommonUtils.getUserId(), String.valueOf(level), 0, adminStatuscheck,System.currentTimeMillis(),mySteriousMan);
                listMessages.add(modelAgoraMessages);

                modelAgoraMessages = new ModelAgoraMessages("Welcome to B Live", image, userProfile.getUserName(), CommonUtils.getUserId(), String.valueOf(level), 0, adminStatuscheck,System.currentTimeMillis(),mySteriousMan);
                listMessages.add(modelAgoraMessages);

                adapterMessagesAgora.notifyItemInserted(adapterMessagesAgora.getItemCount());

                setFirebase();
                if (listMessages.size() > 0) {
                    recycler_comment.smoothScrollToPosition(listMessages.size() - 1);
                }



            } else {
//                setFirebase();

//                Toast.makeText(activity, liveDescriptionModel.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

    }


    private boolean isLiveUserProfile = false;

    private void setRecycler() {
        adapterMessagesAgora = new AdapterMessagesAgora(activity, listMessages, (otherUserId, username, positon) -> {
            if (otherUserId.equalsIgnoreCase(userChannelId)) {
                isLiveUserProfile = true;
            }

            boolean isLiveFollow = userChannelId.equalsIgnoreCase(otherUserId);
            openOtherUserProfile(otherUserId, isLiveUserProfile, isLiveFollow);
        }, adminStatuscheck);
        recycler_comment.setAdapter(adapterMessagesAgora);
        adapterMessagesAgora.notifyItemInserted(adapterMessagesAgora.getItemCount());

        //Todo amit
//        adapterMessagesAgora.notifyDataSetChanged();
        if (listMessages.size() > 0) {
            recycler_comment.smoothScrollToPosition(listMessages.size() - 1);
        }


        setupadd(coinList);

    }

    private void openOtherUserProfile(String otherUserId, boolean isLiveUserId) {

        UserDetailsFragment userDetailsFragment = new UserDetailsFragment(otherUserId, false, true, false, adminStatuscheck, channelName, liveId, this::mentionName);
        userDetailsFragment.show(getSupportFragmentManager(), userDetailsFragment.getTag());
        isLiveUserProfile = false;

    }

    private void openOtherUserProfile(String otherUserId, boolean isLiveUserProfile1, boolean isLiveFollow) {


        UserDetailsFragment userDetailsFragment = new UserDetailsFragment(otherUserId, false, true, isLiveFollow, adminStatuscheck, channelName, liveId, this::mentionName);
        userDetailsFragment.show(getSupportFragmentManager(), userDetailsFragment.getTag());
        isLiveUserProfile = false;

    }

    private void openUserDialog() {

        Dialog dialog = new Dialog(AgoraPkPlayerActivity.this);
        dialog.setContentView(R.layout.user_profile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }

    public ChildEventListener bulletMessage = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            if (snapshot.exists()) {
                ModelBulletMessage modelBulletMessage = snapshot.getValue(ModelBulletMessage.class);
                showBulletMessage(modelBulletMessage);
                FirebaseHelper.deleteBulletMessage();
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @SuppressLint("SetTextI18n")
    private void showBulletMessage(ModelBulletMessage modelBulletMessage) {
        rlPublicMessage.setVisibility(View.VISIBLE);
        tv_publicMessageText.setText(modelBulletMessage.getUsername() + " : " + modelBulletMessage.getText());
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movetwo);
        rlPublicMessage.startAnimation(animation2);
        animation2.setRepeatCount(Animation.INFINITE);
        animation2.setRepeatMode(Animation.INFINITE);
        final Handler threadHandler = new Handler();
        new Thread() {
            @Override
            public void run() {
                threadHandler.postDelayed(() -> {
                    rlPublicMessage.clearAnimation();
                    rlPublicMessage.setVisibility(View.GONE);
                }, 180000);
            }
        }.start();
    }


    public ChildEventListener giftsEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.i("Agora Gift Received : ", snapshot.getKey());
            boolean o = false;

            ModelSendGift modelSendGift = snapshot.getValue(ModelSendGift.class);
            setcoins(modelSendGift);
            showGiftImage(modelSendGift, snapshot.getKey());

            if (coinList.size() > 0) {
                for (int i = 0; i < coinList.size(); i++) {

                    if (modelSendGift.getUserId().contains(coinList.get(i).getUserId())) {

                        coinList.get(i).setGiftPrice(modelSendGift.getGiftPrice() + coinList.get(i).getGiftPrice());
                        o = true;
                        break;

                    }
                }
                if (!o) {
                    coinList.add(modelSendGift);

                }
            } else {
                coinList.add(modelSendGift);
            }

//            showImage(modelSendGift);

            Collections.sort(coinList);


            setupadd(coinList);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void startAnimationTwo(String image, String key, String sound, String type) {

        img_small_video.setVisibility(View.VISIBLE);
//


        Glide.with(activity).load(image).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                final Handler threadHandler = new Handler();
                playAudio(sound);
//                ExampleThread exampleThread = new ExampleThread(img_small_video, delay);
//                exampleThread.run();
                new Thread() {
                    @Override
                    public void run() {
                        threadHandler.postDelayed(() -> {
                            stopAudio();
                            img_small_video.setVisibility(View.GONE);
                        }, 2000);
                    }
                }.start();


                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                final Handler threadHandler = new Handler();
                playAudio(sound);
                new Thread() {
                    @Override
                    public void run() {
                        threadHandler.postDelayed(() -> {
                            img_small_video.setVisibility(View.GONE);
                            stopAudio();

                        }, 2000);
                    }
                }.start();

//                ExampleThread exampleThread = new ExampleThread(img_small_video, delay);
//                exampleThread.run();
                Log.i("Agora Gift Image : ", "ready to load");
                return false;
            }
        }).into(img_small_video);
        boolean isImage = (!image.contains(".gif"));
        if (isImage) {
            delay = 3;

            Glide.with(activity).load(image).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    Log.i("Agora Gift Image : ", e.getMessage());
                    final Handler threadHandler = new Handler();
                    new Thread() {
                        @Override
                        public void run() {
                            threadHandler.postDelayed(  new Runnable() {
                                public void run() {

                                    iv_giftImage.setVisibility(View.GONE);

                                    Log.i("Agora Gift Image Load Failed : dismissing dialog", e.getMessage());
//                                    dismiss();
                                }
                            }, 1000);
                        }
                    }.start();
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    final Handler threadHandler = new Handler();
                    new Thread() {
                        @Override
                        public void run() {
                            threadHandler.postDelayed(new Runnable() {
                                public void run() {
//                                    dismiss();
                                    iv_giftImage.setVisibility(View.GONE);
                                }
                            }, 2000);
                        }
                    }.start();
                    Log.i("Agora Gift Image : ", "ready to load");
                    return false;
                }
            }).into(img_small_video);

        } else {
            Glide.with(activity).asGif().load(image).listener(new RequestListener<GifDrawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                            Target<GifDrawable> target, boolean isFirstResource) {


                    img_small_video.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(GifDrawable resource, Object model,
                                               Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                    GiftGifDrawable giftDrawable = getSelfStoppedGifDrawable(resource);
                    for (int i = 0; i < giftDrawable.gifDecoder.getFrameCount(); i++) {
                        delay += giftDrawable.gifDecoder.getDelay(i);
                    }
                    final Handler threadHandler = new Handler();
                    new Thread() {
                        @Override
                        public void run() {
                            threadHandler.postDelayed(new Runnable() {
                                public void run() {
//                                    dismiss();

                                    img_small_video.setVisibility(View.GONE);
                                    FirebaseHelper.deleteGiftAfterRecevingFromFireBase(key, channelName);

                                }
                            }, 1500);
                        }
                    }.start();


//                    new Handler(activity.getMainLooper()).postDelayed(, delay);
                    return false;
                }

            }).into(img_small_video);
        }

    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void playAudio(String sound) {
        mediaPlayer = new MediaPlayer();
        try {
            String url = sound; // your URL here

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        mediaPlayer.setAudioAttributes(new AudioAttributes.Builder().
//                setUsage(AudioAttributes.USAGE_MEDIA).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build());
//
//        try {
//            mediaPlayer.setDataSource("http://www.all-birds.com/Sound/western%20bluebird.wav");
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    @SuppressLint("SetTextI18n")
    private void showGiftImage(ModelSendGift modelSendGift, String key) {


        String image = modelSendGift.getGiftImage();
        if (modelSendGift.getGiftPrice() <= 500000000) {

            if (modelSendGift.getName().equalsIgnoreCase("")) {
                tv_smallGiftName.setText(modelSendGift.getUserName());
            } else {
                tv_smallGiftName.setText(modelSendGift.getName());
            }
            smallGiftGiftName.setText("sent a " + modelSendGift.getGiftName());
            Glide.with(activity).load(modelSendGift.getUserImage()).placeholder(R.drawable.app_logo).into(smallGiftImage);
            rl_smallGift.setVisibility(View.VISIBLE);
            Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
            rl_smallGift.startAnimation(animation2);
            final Handler threadHandler = new Handler();
            new Thread() {
                @Override
                public void run() {
                    threadHandler.postDelayed(() -> {
                        rl_smallGift.clearAnimation();
                        rl_smallGift.setVisibility(View.GONE);
                        sound = modelSendGift.getSound();
                        startAnimationTwo(image, key, sound,modelSendGift.getType());


                    }, animation2.getDuration());
                }
            }.start();
            Glide.with(activity).asGif().load(modelSendGift.getThumbnail()).into(iv_smallGif);
//            GiftAnimWindow window = new GiftAnimWindow(AgoraPkPlayerActivity.this, R.style.gift_anim_window);
//            window.getWindow().setLayout(300,300);
//            window.setAnimResource(image);
//            window.show();

        } else {
            tvBigGiftNameTo.setText(modelSendGift.getName());
            tvBigGiftName.setText("sent a " + modelSendGift.getGiftName());
            Glide.with(activity).load(modelSendGift.getUserImage()).placeholder(R.drawable.app_logo).into(cv_bigGiftImage);
            ll_bigGiftMessage.setVisibility(View.VISIBLE);
            Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
            ll_bigGiftMessage.startAnimation(animation2);
            final Handler threadHandler = new Handler();
            new Thread() {
                @Override
                public void run() {
                    threadHandler.postDelayed(() -> {
                        ll_bigGiftMessage.clearAnimation();
                        ll_bigGiftMessage.setVisibility(View.GONE);
                    }, animation2.getDuration());
                }
            }.start();
//            GiftAnimWindow window = new GiftAnimWindow(AgoraPkPlayerActivity.this, R.style.gift_anim_window);
//            window.setAnimResource(image);
//            window.show();
//        showImage(modelSendGift);


            iv_giftImage.setVisibility(View.VISIBLE);




            boolean isImage = (!image.contains(".gif"));
            delay = 0;
            if (isImage) {
                delay = 3;
                if (activity != null)
                    Glide.with(activity).load(image).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            final Handler threadHandler = new Handler();
                            new Thread() {
                                @Override
                                public void run() {
                                    threadHandler.postDelayed(() -> {
                                        iv_giftImage.setVisibility(View.GONE);
                                    }, 1000);
                                }
                            }.start();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            final Handler threadHandler = new Handler();
                            new Thread() {
                                @Override
                                public void run() {
                                    threadHandler.postDelayed(() -> {
//                                    dismiss();
                                        iv_giftImage.setVisibility(View.GONE);
                                    }, 2000);
                                }
                            }.start();
                            Log.i("Agora Gift Image : ", "ready to load");
                            return false;
                        }
                    }).into(iv_giftImage);

            } else {
                Glide.with(activity).asGif().load(image).listener(new RequestListener<GifDrawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target<GifDrawable> target, boolean isFirstResource) {
                        iv_giftImage.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GifDrawable resource, Object model,
                                                   Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                        GiftGifDrawable giftDrawable = getSelfStoppedGifDrawable(resource);
                        for (int i = 0; i < giftDrawable.gifDecoder.getFrameCount(); i++) {
                            delay += giftDrawable.gifDecoder.getDelay(i);
                        }
                        final Handler threadHandler = new Handler();
                        new Thread() {
                            @Override
                            public void run() {
                                threadHandler.postDelayed(() -> {
//                                    dismiss();
                                    iv_giftImage.setVisibility(View.GONE);
                                }, delay);
                            }
                        }.start();


//                    new Handler(activity.getMainLooper()).postDelayed(, delay);
                        return false;
                    }

                }).into(iv_giftImage);
            }


        }
    }
    private GiftGifDrawable getSelfStoppedGifDrawable(GifDrawable drawable) {
        GifBitmapProvider provider = new GifBitmapProvider(Glide.get(activity).getBitmapPool());
        Transformation transformation = drawable.getFrameTransformation();
        if (transformation == null) {
            transformation = new CenterCrop();
        }

        ByteBuffer byteBuffer = drawable.getBuffer();
        StandardGifDecoder decoder = new StandardGifDecoder(provider);
        decoder.setData(new GifHeaderParser().setData(byteBuffer).parseHeader(), byteBuffer);
        Bitmap bitmap = drawable.getFirstFrame();
        if (bitmap == null) {
            decoder.advance();
            bitmap = decoder.getNextFrame();
        }

        return new GiftGifDrawable(activity, decoder, transformation, 0, 0, bitmap);
    }

    @Override
    public void mentionName(String name) {


        if (muteLive.equalsIgnoreCase("1")) {
            muteDialog();
            llMessageEdittext.setVisibility(View.GONE);
            comment_icon.setVisibility(View.VISIBLE);

        } else {
            llMessageEdittext.setVisibility(View.VISIBLE);

            edit_text_comment.setText(name);
            comment_icon.setVisibility(View.GONE);
            CommonUtils.openKeyboard(activity);
        }

    }


    private static class GiftGifDrawable extends GifDrawable {
        GifDecoder gifDecoder;

        GiftGifDrawable(Context context, GifDecoder gifDecoder, Transformation<Bitmap> frameTransformation,
                        int targetFrameWidth, int targetFrameHeight, Bitmap firstFrame) {
            super(context, gifDecoder, frameTransformation, targetFrameWidth, targetFrameHeight, firstFrame);
            this.gifDecoder = gifDecoder;
        }

    }

    @SuppressLint("SetTextI18n")
    private void setcoins(ModelSendGift modelSendGift) {
        try {
            Long priceOfGift = Long.parseLong(String.valueOf(modelSendGift.getGiftPrice()));
            Long oldCoins = Long.parseLong(tv_total_coins.getText().toString());
            Long updatedPrice = oldCoins + priceOfGift;
            tv_total_coins.setText(String.valueOf(updatedPrice));
            String liveStars = modelSendGift.getLiveStars();
            String liveLevel = modelSendGift.getLiveLevel();
            liveBox = modelSendGift.getLivebox();

            long getOldStar = Long.parseLong(tv_stars.getText().toString());


            long starCountintz = Long.parseLong(tv_stars.getText().toString());

            runOnUiThread(() -> {


                if (getOldStar != starCountintz) {
//                        animationView.setVisibility(View.GONE);
//                        ll_giftBox.setVisibility(View.VISIBLE);
                } else {
//                        animationView.setVisibility(View.VISIBLE);
//                        ll_giftBox.setVisibility(View.GONE);
                }


                //                    Toast.makeText(activity, name + " Sent Gift", Toast.LENGTH_SHORT).show();
            });
            Log.i("Agora Live Stars : ", liveStars);
            Log.i("Agora Live Level : ", liveLevel);
        } catch (Exception e) {
        }
    }

    ChildEventListener liveBanEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            if (snapshot.exists()) {
//                String userId = String.valueOf(snapshot.getKey());
//                String status = String.valueOf(snapshot.getValue());
//                Toast.makeText(activity, userId, Toast.LENGTH_SHORT).show();
                Log.i("Agora Live Ban userId: ", snapshot.toString());
//                Log.i("Agora Live Ban userId status: ", status);


                GetBanUserpojo getBanUserpojo = snapshot.getValue(GetBanUserpojo.class);

//                Toast.makeText(activity, "" + getBanUserpojo.getKickStatus(), Toast.LENGTH_SHORT).show();
                if (getBanUserpojo.getUserId().equalsIgnoreCase(CommonUtils.getUserId())) {
                    if (getBanUserpojo.getKickStatus().equals("0")) {
                        KickOutDialog(getBanUserpojo.getKickStatus());
                        runOnUiThread(() -> {
//                            Toast.makeText(activity, "You have been kicked out", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(AgoraPkPlayerActivity.this, HomeLiveActivity.class));
                            finish();
                        });
                    } else if (getBanUserpojo.getKickStatus().equals("1")) {
                        KickOutDialog(getBanUserpojo.getKickStatus());
                        runOnUiThread(() -> {
//                            Toast.makeText(activity, "You have been Ban", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(AgoraPkPlayerActivity.this, HomeLiveActivity.class));
                            finish();
                        });
                    }


//

                }


            }


        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists()) {

//                String userId = String.valueOf(snapshot.getKey());
//                String status = String.valueOf(snapshot.getValue());
                Log.i("Agora Live Ban userId: ", snapshot.toString());
//                Log.i("Agora Live Ban userId status: ", status);


                GetBanUserpojo getBanUserpojo = snapshot.getValue(GetBanUserpojo.class);
                if (Objects.requireNonNull(getBanUserpojo).getUserId().equalsIgnoreCase(CommonUtils.getUserId())) {


//                        onBackPressed();
                    if (getBanUserpojo.getKickStatus().equals("0")) {
                        KickOutDialog(getBanUserpojo.getKickStatus());
                        runOnUiThread(() -> {
//                            Toast.makeText(activity, "You have been kicked out", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(AgoraPkPlayerActivity.this, HomeLiveActivity.class));
                            finish();
                        });
                    } else if (getBanUserpojo.getKickStatus().equals("1")) {
                        KickOutDialog(getBanUserpojo.getKickStatus());
                        runOnUiThread(() -> {
//                            Toast.makeText(activity, "You have been Ban", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(AgoraPkPlayerActivity.this, HomeLiveActivity.class));
                            finish();
                        });
                    }
//
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    ChildEventListener viewersEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.i("childEvenet", snapshot.getKey());
            ModelSetUserViewer modelSetUserViewer = snapshot.getValue(ModelSetUserViewer.class);

            listViewers.add(0, modelSetUserViewer);

            getViewCounts();

            setAdapters(listViewers);
            if (modelSetUserViewer.getUserId().equalsIgnoreCase(userId)) {
                myFirebaseKey = snapshot.getKey();
                Intent intent = new Intent(activity, PlayerExitService.class);
                intent.putExtra("firebaseKey", myFirebaseKey);
                intent.putExtra("liveUsername", channelName);
                intent.putExtra("userId", userId);
                intent.putExtra("garage",garage);
                intent.putExtra("frame",frame);
                startService(intent);
                Log.i("Agora myFirebaseKey : ", myFirebaseKey);
            }
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            try {
                ModelSetUserViewer modelSetUserViewer = snapshot.getValue(ModelSetUserViewer.class);
                for (int i = 0; i < listViewers.size(); i++) {
                    if (listViewers.get(i).getUserId().equalsIgnoreCase(Objects.requireNonNull(modelSetUserViewer).getUserId())) {
                        listViewers.remove(i);
                        adapterUserList.notifyDataSetChanged();
                    }
                }
            } catch (Exception ignored) {
            }


        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @SuppressLint("SetTextI18n")
    private void getViewCounts() {
        tv_views_live.setText(listViewers.size() + "");
        if (listViewers.size() > 0) {
            iv_profiles.setVisibility(View.VISIBLE);
        }
        iv_profiles.setText(listViewers.size() + "");
    }

    public static String myFirebaseKey = "";
    String userId = CommonUtils.getUserId();


    String myName = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getName();


    private void setFirebase() {
        ModelSetUserViewer modelSetUserViewer = new ModelSetUserViewer();
        modelSetUserViewer.setImage(image);
        modelSetUserViewer.setLevel("0");
        modelSetUserViewer.setUserId(userId);
        String name = !myName.equalsIgnoreCase("") ? myName : username;
        modelSetUserViewer.setName(name);
        modelSetUserViewer.setFrame(frame);
        modelSetUserViewer.setGarage(garage);
//                        sendMultiRequest("7");
        FirebaseHelper.getAdmin(channelName, CommonUtils.getUserId(), valueEventListenerAdminTwo);

        FirebaseHelper.setViewerUser(channelName, modelSetUserViewer);
        FirebaseHelper.getViewersLive(channelName, viewersEventListener);

//                        FirebaseHelper.getFakeViewers(fakeViewChildEventListener);
        FirebaseHelper.getfakeCounts(channelName, fakeCountvalueEventListener);
        FirebaseHelper.giftsListener(channelName, giftsEventListener);
        FirebaseHelper.getBanDataLive(channelName, userId, valueEventListener);
        FirebaseHelper.getMuteList(channelName, userId, getMutevalueEventListener);
        FirebaseHelper.getMessage(channelName, messageEventLisner);

        FirebaseHelper.getMaxRequestStatus(channelName, maxRequestValueEventListener);
        FirebaseHelper.getMyMultiRequest(channelName, userId, myMultiRequestStatus);
        FirebaseHelper.getAdminLiveStatus(channelName, adminStatusListser);

        // pk evenet listner
        FirebaseHelper.getTimerForAudience(channelName, pkBattleTimerEventListener);

        FirebaseHelper.getpublicBulletMessage(channelName, bulletEventListener);
        FirebaseHelper.getBulletMessage(channelName, valueBulletEvent);


        updateChildListenersInClass();
    }

    ValueEventListener valueBulletEvent = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                ModelBulletMain modelBulletMain = snapshot.getValue(ModelBulletMain.class);
                showBulletMainMessage(modelBulletMain);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @SuppressLint("SetTextI18n")
    private void showBulletMainMessage(ModelBulletMain modelBulletMain) {
        tv_mainBulletMessage.setVisibility(View.VISIBLE);
        tv_mainBulletMessage.setText(modelBulletMain.getUsername() + " : " + modelBulletMain.getTextmesssage());
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movethree);
        tv_mainBulletMessage.startAnimation(animation2);
        final Handler threadHandler = new Handler();
        new Thread() {
            @Override
            public void run() {
                threadHandler.postDelayed(() -> {
                    tv_mainBulletMessage.clearAnimation();
                    tv_mainBulletMessage.setVisibility(View.GONE);
                }, animation2.getDuration());
            }
        }.start();
    }

    ValueEventListener pkBattleTimerEventListener = new ValueEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {

                ModelPkTimeLimit modelPkTimeLimit = snapshot.getValue(ModelPkTimeLimit.class);
                if (modelPkTimeLimit.getStatus().equalsIgnoreCase("1")) {
                    if (!isBattlePlayed) {

                        VideoGridContainer.MAX_USER = 2;
                        App.getSingleton().setMyPkBattle("1");
                        pkBattleId = modelPkTimeLimit.getPkBattleId();
                        otherChannelName = modelPkTimeLimit.getOtherChannelID();
                        otherPkUserId = modelPkTimeLimit.getOtherId();

                        long newTs = new Date().getTime();

                        newTs = newTs - modelPkTimeLimit.getTs();

                        setTimerStart(modelPkTimeLimit.getTimeLimitPkbattle(), newTs);
                    }

                    try {
                        if (modelPkTimeLimit.getRequestChannelID().equalsIgnoreCase(channelName)) {
                            upperCoins.setText(modelPkTimeLimit.getRequestedCoins() + " Daimond");
                            bottomCoins.setText(modelPkTimeLimit.getOtherCoins() + " Daimond");

                            tv_bottomHostName.setText(modelPkTimeLimit.getOtherName());
                            tv_bottomHostId.setText(modelPkTimeLimit.getOtherChannelID());
                            try {
                                Glide.with(activity).load(modelPkTimeLimit.getOtherImage()).placeholder(R.drawable.app_logo).into(iv_otherHostImage);
                            } catch (Exception ignored) {

                            }

                        } else {
                            upperCoins.setText(modelPkTimeLimit.getOtherCoins() + " Beans");
                            bottomCoins.setText(modelPkTimeLimit.getRequestedCoins() + " Beans");

                            tv_upperHostName.setText(modelPkTimeLimit.getOtherName());
                            tv_bottomHostName.setText(modelPkTimeLimit.getRequestName());

                            tv_bottomHostId.setText(modelPkTimeLimit.getRequestedId());
                            try {
                                Glide.with(activity).load(modelPkTimeLimit.getRequestedImage()).placeholder(R.drawable.ic_user1).into(iv_otherHostImage);
                            } catch (Exception ignored) {

                            }
                        }

                    } catch (Exception e) {
                        Log.e("ex", e.toString());
                    }

                } else {

                    if (countDownTimerPkBattle != null) {
                        countDownTimerPkBattle.cancel();
                    }

                    if (isBattlePlayed) {
                        battleOver();
                        calculateGifts(modelPkTimeLimit.getPkBattleId());
                    }
                }

            } else {
                layoutHideUnHide(goneInt);
                rl_coins.setVisibility(View.GONE);
                isBattlePlayed = false;
                pkBattleId = "";
                otherPkUserId="";
                otherChannelName="";

                tv_tv_timeLeft.setVisibility(View.GONE);
                if (countDownTimerPkBattle != null) {
                    countDownTimerPkBattle.cancel();
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
    private void layoutHideUnHide(int check) {
        findViewById(R.id.ll_linearDummy).setVisibility(check);
//        findViewById(R.id.view_centerDivide).setVisibility(check);
        findViewById(R.id.tv_upperHostCoins).setVisibility(check);
        findViewById(R.id.tv_bottomHostCoins).setVisibility(check);
        findViewById(R.id.ll_otheruserDetails).setVisibility(check);
        findViewById(R.id.rl_contqaier).setVisibility(check);
        findViewById(R.id.ll_time).setVisibility(check);

        if (check == visibleInt) {
            rl_viewpager.setVisibility(View.GONE);
        } else {
            rl_viewpager.setVisibility(View.VISIBLE);
        }

    }

    private void battleOver() {

        calculateGifts(pkBattleId);


    }


    ChildEventListener adminStatusListser = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            if (snapshot.exists()) {

                if (Objects.requireNonNull(snapshot.getKey()).equalsIgnoreCase("statusLive")) {
                    if (Objects.requireNonNull(snapshot.getValue()).toString().equalsIgnoreCase("0")) {
//                    stopAllAsync();
                        removeRemoteUser(Integer.parseInt(CommonUtils.getUserId()));
                        findViewById(R.id.live_sendRequest).setVisibility(View.GONE);
//                        onBackPressed();
//
                    } else {
                        findViewById(R.id.live_sendRequest).setVisibility(View.VISIBLE);

                    }
                }
            }

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    ValueEventListener maxRequestValueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists()) {
                findViewById(R.id.live_sendRequest).setVisibility(View.VISIBLE);

//                if (snapshot.getValue().toString().equalsIgnoreCase("1")) {
//                } else {
//                    findViewById(R.id.live_sendRequest).setVisibility(View.GONE);
//                }

                FirebaseHelper.getMyMultiRequest(channelName, userId, myMultiRequestStatus);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    ValueEventListener myMultiRequestStatus = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {


                MultiLiveModelCount multiLiveModelCount;

                multiLiveModelCount = snapshot.getValue(MultiLiveModelCount.class);

                if (multiLiveModelCount.getRequestStatus().equalsIgnoreCase("1")) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startBroadcast(Integer.parseInt(multiLiveModelCount.getUserId()));
                        }
                    }, 1000);

                    //findViewById(R.id.live_btn_mute_video).setVisibility(View.GONE);
                    findViewById(R.id.live_sendRequest).setVisibility(View.GONE);
                    findViewById(R.id.ll_sideMenu).setVisibility(View.VISIBLE);
                } else if (snapshot.getValue().toString().equalsIgnoreCase("3")) {
                    findViewById(R.id.live_sendRequest).setVisibility(View.GONE);
                    findViewById(R.id.ll_sideMenu).setVisibility(View.GONE);
                } else if (snapshot.getValue().toString().equalsIgnoreCase("0")) {
                    findViewById(R.id.live_sendRequest).setVisibility(View.GONE);
                    findViewById(R.id.ll_sideMenu).setVisibility(View.GONE);
//                    Toast.makeText(activity, "Live session request rejected", Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    private void setTimerStart(int timeLimit, long newTs) {
        rl_viewpager.setVisibility(View.GONE);
        layoutHideUnHide(visibleInt);
        isBattlePlayed = true;

        rl_coins.setVisibility(View.GONE);
//
        long dur = (timeLimit * 60L) * 1000;
        dur = dur - newTs;
        countDownTimerPkBattle = new CountDownTimer(dur, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                long minutes = (l / 1000) / 60;
                int seconds = (int) ((l / 1000) % 60);
                String sec, min;

                tv_tv_timeLeft.setVisibility(View.VISIBLE);

                sec = String.valueOf(seconds);
                min = String.valueOf(minutes);

                if (minutes < 10) {
                    min = "0" + minutes;
                }

                if (seconds < 10) {
                    sec = "0" + seconds;
                }
                tv_tv_timeLeft.setText("Countdown: " + min + ":" + sec);

            }

            @Override
            public void onFinish() {

                battleOver();

//                calculateGifts(pkBattleId);

                tv_tv_timeLeft.setVisibility(View.GONE);

//                Toast.makeText(activity, "Pk Battle Over", Toast.LENGTH_SHORT).show();

            }
        };

        countDownTimerPkBattle.start();

    }

    private void calculateGifts(String pkHostId) {
        HashMap<String, String> data = new HashMap<>();
        data.put("type", "");
        data.put("pkId", pkHostId);

        viewModelClass.archivedPkBattle(this, data).observe(this, new Observer<EndPkLive>() {
            @Override
            public void onChanged(EndPkLive map) {


                viewModelClass.getpkResult(pkHostId).observe(AgoraPkPlayerActivity.this, getWinnerPkBattlePojo -> {
                    if (getWinnerPkBattlePojo.getStatus() == 1) {
                        winLossDailog(getWinnerPkBattlePojo.getDetails());
                    } else {
//                        Toast.makeText(activity, getWinnerPkBattlePojo.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    @SuppressLint("SetTextI18n")

    private void winLossDailog(List<GetWinnerPkBattlePojo.Detail> getWinnerPkBattlePojo) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left);
        Animation animationTwo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);



        ll_WinnerLooser.setVisibility(View.VISIBLE);

        if (getWinnerPkBattlePojo.get(0).getResult().equalsIgnoreCase("tie")) {
//            Toast.makeText(activity, "Tie", Toast.LENGTH_SHORT).show();
            ll_Looser.setVisibility(View.GONE);
            imgWinner.setImageResource(R.drawable.draw);
            imgWinner.startAnimation(animation);
        } else {
            if (getWinnerPkBattlePojo.get(0).getId().equalsIgnoreCase(userChannelId)) {
                imgWinner.setImageResource(R.drawable.winnerpk);
                imgLooser.setImageResource(R.drawable.looser);
            } else {
                imgWinner.setImageResource(R.drawable.looser);
                imgLooser.setImageResource(R.drawable.winnerpk);
            }
            imgWinner.startAnimation(animation);
            imgLooser.startAnimation(animationTwo);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ll_Looser.setVisibility(View.VISIBLE);
                ll_WinnerLooser.setVisibility(View.GONE);
//                hideShowPkBatleIcons(false);
//                removePkBattle();

                pkBattleId="";
                otherChannelName="";
                otherPkUserId="";

//                Toast.makeText(activity, "Pk Battle Over", Toast.LENGTH_SHORT).show();
                layoutHideUnHide(goneInt);
                VideoGridContainer.MAX_USER = 4;
                rl_coins.setVisibility(View.GONE);
                isBattlePlayed = false;
                tv_tv_timeLeft.setVisibility(View.GONE);

            }
        }, 4000);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, animationTwo.getDuration());

    }


    private void sendMultiRequest(String status) {
//        findViewById(R.id.live_sendRequest).setVisibility(View.GONE);
        MultiLiveModelCount multiLiveModelCount = new MultiLiveModelCount();
        multiLiveModelCount.setRequestStatus(status);
        multiLiveModelCount.setUserId(userId);
        multiLiveModelCount.setImage(image);
        multiLiveModelCount.setName(name);

        FirebaseHelper.sendMultiRequest(channelName, userId, multiLiveModelCount);

        rtcEngine().setEnableSpeakerphone(true);


//        audioInSpeaker();
//        rtcEngine().setDefaultAudioRoutetoSpeakerphone(true);

    }

    private void audioInSpeaker() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(true);
    }


    private void updateChildListenersInClass() {
        try {
            sendChannelMessage("entered the stream");
            setEntry();
        } catch (Exception e) {
            e.printStackTrace();
//            Toast.makeText(activity, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        ChildEventListenersObject.setLiveBanEventListener(liveBanEventListener);
        ChildEventListenersObject.setViewerChildListsner(viewersEventListener);
        ChildEventListenersObject.setGiftChildListsner(giftsEventListener);
        ChildEventListenersObject.setBulletChildListsner(bulletMessage);
        ChildEventListenersObject.setRtcEngine(rtcEngine());
    }

    TextView roomName;

    private void setAdapters(List<ModelSetUserViewer> listViewers) {

        adapterUserList = new AdapterUserList(listViewers, new AdapterUserList.GetData() {
            @Override
            public void getData(String number) {
                openOtherUserProfile(number, false);
            }
        });
        recyclerViewersOne.setAdapter(adapterUserList);
        recyclerViewersOne.setAdapter(adapterUserList);

    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        iv_giftImage = findViewById(R.id.iv_giftImage);
        rl_viewpager = findViewById(R.id.rl_viewpager);
        ll_comments = findViewById(R.id.ll_comments);
        llMessage = findViewById(R.id.llMessage);
        recyclerViewersOne = findViewById(R.id.recyclerViewersOne);
        animationReword = findViewById(R.id.animationReword);
        profileFrame = findViewById(R.id.profileFrame);
        muCarsRVImagee = findViewById(R.id.muCarsRVImagee);
        viewPager_add = findViewById(R.id.viewPager_add);
        indicator = findViewById(R.id.indicator);
        LinearLayout llUserChannelImage = findViewById(R.id.ll_user_channel_image);
        llUserChannelImage.setOnClickListener(this);
        viewMultiLive = findViewById(R.id.viewMutlilive);
        rlPublicMessage = findViewById(R.id.rl_publicmessage);
        iv_smallEntryGif = findViewById(R.id.iv_smallEntryGif);
        rl_smallEntry = findViewById(R.id.rl_smallEntry);
        smallEntryImage = findViewById(R.id.smallEntryImage);
        tv_smallEntryName = findViewById(R.id.tv_smallEntryName);
        smallEntryGiftName = findViewById(R.id.smallEntryGiftName);
        LinearLayout llUsers = findViewById(R.id.llUSers);
        llUsers.setOnClickListener(this);
        ll_giftBox = findViewById(R.id.ll_giftBox);
        ll_giftBox.setOnClickListener(this);
        tv_giftboxcount = findViewById(R.id.tv_giftboxcount);
        tv_giftboxcount.setOnClickListener(this);
//        animationView = findViewById(R.id.animationView);
        ImageView iv_box = findViewById(R.id.iv_box);
        iv_box.setOnClickListener(this);
        ll_star_gained = findViewById(R.id.ll_star_gained);
        tv_mainBulletMessage = findViewById(R.id.tv_mainBulletMessage);
        tv_publicMessageText = findViewById(R.id.tv_publicMessageText);
        tv_publicMessage = findViewById(R.id.tv_publicMessage);
        iv_profiles = findViewById(R.id.iv_profiles);
        iv_profiles.setOnClickListener(this);
        tv_publicMessage.setOnClickListener(this);
        tv_bulletMessage = findViewById(R.id.tv_bulletMessage);
        tv_bulletMessage.setOnClickListener(this);
        tvBigGiftName = findViewById(R.id.tv_biggiftGiftName);
        tvBigGiftNameTo = findViewById(R.id.tv_biggiftNAme);
        cv_bigGiftImage = findViewById(R.id.cv_bigGiftImage);
        ll_otheruserDetails = findViewById(R.id.ll_otheruserDetails);
        ll_bigGiftMessage = findViewById(R.id.ll_bigGiftMessage);
        iv_smallGif = findViewById(R.id.iv_smallGif);
        tv_smallGiftName = findViewById(R.id.tv_smallGiftName);
        smallGiftGiftName = findViewById(R.id.smallGiftGiftName);
        rl_smallGift = findViewById(R.id.rl_smallGift);
        ll_WinnerLooser = findViewById(R.id.ll_WinnerLooser);
        ll_Looser = findViewById(R.id.ll_Looser);
        imgLooser = findViewById(R.id.imgLooser);
        imgWinner = findViewById(R.id.imgWinner);
        smallGiftImage = findViewById(R.id.smallGiftImage);
        tv_showLayout = findViewById(R.id.tv_showLayout);
        tv_showLayout.setOnClickListener(this);
        rl_hideLayout = findViewById(R.id.rl_hideLayout);
        rl_hideLayout.setOnClickListener(this);
        tv_hideLayout = findViewById(R.id.tv_hideLayout);
        tv_hideLayout.setOnClickListener(this);
        rl_coins = findViewById(R.id.rl_coins);
        iv_otherHostImage = findViewById(R.id.iv_otherHostImage);
        tv_bottomHostId = findViewById(R.id.tv_bottomHostId);
        tv_bottomHostName = findViewById(R.id.tv_bottomHostName);
        tv_upperHostName = findViewById(R.id.tv_upperHostName);
        upperCoins = findViewById(R.id.tv_upperHostCoins);
        bottomCoins = findViewById(R.id.tv_bottomHostCoins);
        tv_tv_timeLeft = findViewById(R.id.tv_tv_timeLeft);
        findViewById(R.id.live_sendRequest).setOnClickListener(this);
        gifImageView = findViewById(R.id.gifimageview);
//        andExoPlayerView = findViewById(R.id.andExoPlayerView);
        findViewById(R.id.iv_liveshare).setOnClickListener(this);
        findViewById(R.id.img_send_comment).setOnClickListener(this);
        findViewById(R.id.ivCloseComment).setOnClickListener(this);
//        tv_description1 = findViewById(R.id.tv_description1);
//        tv_description2 = findViewById(R.id.tv_description2);
//        tv_description3 = findViewById(R.id.tv_description3);
        img_small_video = findViewById(R.id.img_small_video);
        comment_icon = findViewById(R.id.comment_icon);
        llMessageEdittext = findViewById(R.id.ll_messege_edittext);
        ll_message_share_gift = findViewById(R.id.ll_message_share_gift);
        tv_stars = findViewById(R.id.tv_stars);
        tv_level = findViewById(R.id.tv_level);
        TextView tvFollowers = findViewById(R.id.tv_followers);
        tv_total_coins = findViewById(R.id.tv_total_coins);
        recyclerTopGift = findViewById(R.id.recycler_top_gifters);
        tv_views_live = findViewById(R.id.tv_views_live);
        recycler_comment = findViewById(R.id.recycler_comment);
        img_follow_unfollow = findViewById(R.id.img_follow_unfollow);
        edit_text_comment = findViewById(R.id.edit_text_comment);
        findViewById(R.id.img_private_message).setOnClickListener(this);

        comment_icon.setOnClickListener(this);
        roomName = findViewById(R.id.live_room_name);
        String coins;
        if (getIntent().getExtras().getString(AgoraLiveMainActivity.COIN) == null) {
            coins = "0";
        } else {
            coins = getIntent().getExtras().getString(AgoraLiveMainActivity.COIN);
        }

        if (coins.equalsIgnoreCase("")) {
            coins = "0";
        }
        tv_total_coins.setText(coins);

        if (liveStarCount != null) {
            tv_stars.setText(liveStarCount);
        } else {
            tv_stars.setText("0");
        }


        tv_level.setText("Level " + liveLevel);
        tvFollowers.setText(getIntent().getExtras().getString(FOLLOWERS));


        String name = configEngine().getChannelName();

        roomName.setText(liveRealName);
        roomName.setSelected(true);
        int role = getIntent().getIntExtra("role", Constants.CLIENT_ROLE_AUDIENCE);
        boolean isBroadcaster = (role == Constants.CLIENT_ROLE_BROADCASTER);

        ImageView mMuteVideoBtn = findViewById(R.id.live_btn_mute_video);
        mMuteVideoBtn.setActivated(isBroadcaster);

        mMuteAudioBtn = findViewById(R.id.live_btn_mute_audio);
        mMuteAudioBtn.setActivated(isBroadcaster);

        ImageView beautyBtn = findViewById(R.id.live_btn_beautification);
        beautyBtn.setActivated(true);
        rtcEngine().setBeautyEffectOptions(beautyBtn.isActivated(),
                com.expert.blive.Agora.agoraLive.utils.FileUtil.Constants.DEFAULT_BEAUTY_OPTIONS);

        mVideoGridContainer = findViewById(R.id.live_video_grid_layout);
        mVideoGridContainer.setStatsManager(statsManager());
        mVideoGridContainer.setHostType(hostType);
        mVideoGridContainer.onVideoClicked((position, uid) -> {
            for (int i = 0; i < listMultiLiveData.getValue().getUserMultiLiveDetailsModels().size(); i++) {
                if (uid != 0) {
                    if (uid == Integer.parseInt(listMultiLiveData.getValue().getUserMultiLiveDetailsModels().get(i).getUid())) {
                        openOtherUserProfile(listMultiLiveData.getValue().getUserMultiLiveDetailsModels().get(i).getUserid(), false);
                    }
                }
            }
        });

        rtcEngine().setClientRole(role);

        if (isBroadcaster) startBroadcast(Integer.parseInt(userChannelId));

        findViewById(R.id.img_gifts).setOnClickListener(this);
        findViewById(R.id.lay_live_board_name).setOnClickListener(this);
        findViewById(R.id.img_leave).setOnClickListener(this);
        img_follow_unfollow.setOnClickListener(this);

        Glide.with(activity).load(R.drawable.ic_checked).into(img_follow_unfollow);

        edit_text_comment.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                try {
                    sendChannelMessage(edit_text_comment.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                handled = true;
            }
            return handled;
        });
    }
    private void setupadd(List<ModelSendGift> list) {

        adapterTopGifters = new AdapterTopGifters(list, userId -> {
//            OpenbottomTwo();
            if (userId.equalsIgnoreCase(CommonUtils.getUserId())) {
                if (userId.equalsIgnoreCase(userChannelId)) {
                    isLiveUserProfile = true;
                }
                openOtherUserProfile(userId, isLiveUserProfile, false);
            }
        });
        recyclerTopGift.setAdapter(adapterTopGifters);
    }

    public void onBeautyClickedz(View view) {
        view.setActivated(!view.isActivated());
        rtcEngine().setBeautyEffectOptions(view.isActivated(),
                com.expert.blive.Agora.agoraLive.utils.FileUtil.Constants.DEFAULT_BEAUTY_OPTIONS);

        Log.i("Agora:onBeautyClicked", String.valueOf(!view.isActivated()));
    }
    boolean isTorchOn = false;
    public void onTorchClicked(View view) {
        boolean torchSupported = rtcEngine().isCameraTorchSupported();
        Log.i("Agora:onTorchClicked", String.valueOf(torchSupported));
        if (isTorchOn) {
            rtcEngine().setCameraTorchOn(false);
            isTorchOn = false;
        } else {
            isTorchOn = true;
            rtcEngine().setCameraTorchOn(true);
        }
//        getMembersInfo();
    }

    public void onCameraRotate(View view) {
        isTorchOn = false;
        rtcEngine().switchCamera();
    }


    private void initUserIcon() {
        CircleImageView iconView = findViewById(R.id.live_name_board_icon);
//        Log.d("AGORA_LIVE_FRAME ", "AGORA_LIVE_FRAME: "+AgoraLiveMainActivity.AGORA_LIVE_FRAME);
        CommonUtils.setAnimation(AgoraPkPlayerActivity.this,frame,profileFrame);
        iconView.setOnClickListener(view -> {
            openOtherUserProfile(userChannelId, true);

        });
        Glide.with(activity).load(getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_LIVE_IMAGE)).placeholder(R.drawable.app_logo).into(iconView);
    }

    private void initData() {

        mVideoDimension = com.expert.blive.Agora.agoraLive.utils.FileUtil.Constants.VIDEO_DIMENSIONS[
                configEngine().getVideoDimenIndex()];
    }

    private void startBroadcast(int uId) {
        isLive = true;

        rtcEngine().setClientRole(Constants.CLIENT_ROLE_BROADCASTER);
        SurfaceView surface = prepareRtcVideo(uId, true);

        mVideoGridContainer.addUserVideoSurface(activity, uId, surface, false, true, image, 0);
        mMuteAudioBtn.setActivated(true);
//        getAllHostFirebase();
    }

    private void stopBroadcast() {
        rtcEngine().setClientRole(Constants.CLIENT_ROLE_AUDIENCE);
        removeRtcVideo(0, true);
        mVideoGridContainer.removeUserVideo(0, true);
        mMuteAudioBtn.setActivated(false);
    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {

//        Toast.makeText(activity, "Join", Toast.LENGTH_SHORT).show();
// Do nothing at the moment
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {


//        Toast.makeText(activity, "userJoin", Toast.LENGTH_SHORT).show();
// Do nothing at the moment
        Log.i("Agora:onUserJoined", String.valueOf(elapsed));
    }

    @Override
    public void onUserOffline(final int uid, int reason) {
        runOnUiThread(() -> {
            Log.i("Agora:onUserOffline", String.valueOf(reason));
//                removeRemoteUser(uid);
            mVideoGridContainer.removeUserVideo(uid, false);

        });
    }

    @Override
    public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
        runOnUiThread(() -> {
            Log.d("onFirstRemoteVideoDecoded", "onFirstRemoteVideoDecoded: " + uid);
            renderRemoteUser(uid);
        });
        liveUserId = uid;
    }

    private void renderRemoteUser(int uid) {

        if (listMultiLiveDataList.size() > 0) {
            for (int i = 0; i < listMultiLiveDataList.size(); i++) {

                if (listMultiLiveDataList.get(i).getUid().equalsIgnoreCase(String.valueOf(uid))) {
                    otherUserImage = listMultiLiveDataList.get(i).getUserImage();
                }
            }
        }


        if (VideoGridContainer.MAX_USER == 2) {
            Log.d(TAG, "renderRemoteUser: ");
        } else {
            if (hostId == 0) {
                FirebaseHelper.setHostId(channelName, uid);
            }
        }

        runOnUiThread(() -> {
            SurfaceView surface;
            if (hostId == 0 || hostId == uid) {
                surface = prepareRtcVideo(uid, false);
                mVideoGridContainer.addUserVideoSurface(activity, uid, surface, false, false, "", uid);
            } else {
                surface = prepareRtcVideo(uid, false);
                mVideoGridContainer.addUserVideoSurface(activity, uid, surface, false, true, otherUserImage, hostId);
            }
        });
    }

    private void removeRemoteUser(int uid) {
        removeRtcVideo(uid, false);
        mVideoGridContainer.removeUserVideo(uid, false);
//        Toast.makeText(activity, "Live Video Ended", Toast.LENGTH_SHORT).show();
        leaveChannel();
        onBackPressed();
    }

    @Override
    public void onLocalVideoStats(IRtcEngineEventHandler.LocalVideoStats stats) {
        if (!statsManager().isEnabled()) return;

        LocalStatsData data = (LocalStatsData) statsManager().getStatsData(0);
        if (data == null) return;

        data.setWidth(mVideoDimension.width);
        data.setHeight(mVideoDimension.height);
        data.setFramerate(stats.sentFrameRate);

        Log.i("Agora:onLocalVideoStats", String.valueOf(mVideoDimension.width));
        Log.i("Agora:onLocalVideoStats", String.valueOf(mVideoDimension.height));
        Log.i("Agora:onLocalVideoStats", String.valueOf(stats.sentFrameRate));
    }

    @Override
    public void onRtcStats(IRtcEngineEventHandler.RtcStats stats) {
        if (!statsManager().isEnabled()) return;

        LocalStatsData data = (LocalStatsData) statsManager().getStatsData(0);
        if (data == null) return;

        data.setLastMileDelay(stats.lastmileDelay);
        data.setVideoSendBitrate(stats.txVideoKBitRate);
        data.setVideoRecvBitrate(stats.rxVideoKBitRate);
        data.setAudioSendBitrate(stats.txAudioKBitRate);
        data.setAudioRecvBitrate(stats.rxAudioKBitRate);
        data.setCpuApp(stats.cpuAppUsage);
        data.setCpuTotal(stats.cpuAppUsage);
        data.setSendLoss(stats.txPacketLossRate);
        data.setRecvLoss(stats.rxPacketLossRate);
    }

    @Override
    public void onNetworkQuality(int uid, int txQuality, int rxQuality) {
        if (!statsManager().isEnabled()) return;

        StatsData data = statsManager().getStatsData(uid);
        if (data == null) return;

        data.setSendQuality(statsManager().qualityToString(txQuality));
        data.setRecvQuality(statsManager().qualityToString(rxQuality));

        Log.i("Agora:onNetworkQuality", String.valueOf(txQuality));
        Log.i("Agora:onNetworkQuality", String.valueOf(rxQuality));
    }

    @Override
    public void onRemoteVideoStats(IRtcEngineEventHandler.RemoteVideoStats stats) {
        if (!statsManager().isEnabled()) return;

        RemoteStatsData data = (RemoteStatsData) statsManager().getStatsData(stats.uid);
        if (data == null) return;

        data.setWidth(stats.width);
        data.setHeight(stats.height);
        data.setFramerate(stats.rendererOutputFrameRate);
        data.setVideoDelay(stats.delay);
    }

    @Override
    public void onRemoteAudioStats(IRtcEngineEventHandler.RemoteAudioStats stats) {
        if (!statsManager().isEnabled()) return;

        RemoteStatsData data = (RemoteStatsData) statsManager().getStatsData(stats.uid);
        if (data == null) return;

        data.setAudioNetDelay(stats.networkTransportDelay);
        data.setAudioNetJitter(stats.jitterBufferDelay);
        data.setAudioLoss(stats.audioLossRate);
        data.setAudioQuality(statsManager().qualityToString(stats.quality));

    }

    @Override
    public void activeSpeaker(int uId) {

//        Toast.makeText(activity, "wefver"+uId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        super.finish();
        statsManager().clearAllData();
    }
    public void onBeautyClicked(View view) {
        view.setActivated(!view.isActivated());
        rtcEngine().setBeautyEffectOptions(view.isActivated(),
                com.expert.blive.Agora.agoraLive.utils.FileUtil.Constants.DEFAULT_BEAUTY_OPTIONS);

        Log.i("Agora:onBeautyClicked", String.valueOf(!view.isActivated()));
    }
    public void onMuteAudioClicked(View view) {

        if (mute == 0) {
            rtcEngine().muteLocalAudioStream(true);
            mMuteAudioBtn.setImageResource(R.drawable.btn_audio_disabled);
            mute = 1;
        } else {
            rtcEngine().muteLocalAudioStream(false);
            mMuteAudioBtn.setImageResource(R.drawable.btn_audio_enabled);
            mute = 0;
        }
    }

    public void onMuteVideoClicked(View view) {
        if (view.isActivated()) {
            rtcEngine().disableVideo();
            rtcEngine().muteLocalVideoStream(true);
        } else {
            rtcEngine().enableVideo();
            rtcEngine().muteLocalVideoStream(false);
        }
        view.setActivated(!view.isActivated());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_private_message:
                String image = getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_LIVE_IMAGE);
                String username = roomName.getText().toString().trim();

                isPrivateMessageOpened = true;
                PrivateMessagesFragment privateMessagesFragment = new PrivateMessagesFragment(image, username, userChannelId);
                privateMessagesFragment.show(getSupportFragmentManager(), privateMessagesFragment.getTag());
                break;
            case R.id.img_leave:
                leaveChannel();
                onBackPressed();

                break;

            case R.id.live_sendRequest:
                alertDailog();
                break;
            case R.id.iv_profiles:
                openBottom();
                break;

            case R.id.img_gifts:
                App.getSingleton().setGiftCheck("2");
                openGiftsDialog();
                break;

            case R.id.img_follow_unfollow:
                followUnfollow();
                break;


            case R.id.lay_live_board_name:
                openOtherUserProfile(userChannelId, true, true);
                break;

            case R.id.img_send_comment:
                CommonUtils.hideKeyboard(activity);
                tv_publicMessage.setBackgroundResource(R.drawable.grey_stroke);
                tv_bulletMessage.setBackgroundResource(R.drawable.grey_stroke);
                if (messageType.equalsIgnoreCase("bullet") || messageType.equalsIgnoreCase("public")) {
                    publicBulletMessage(edit_text_comment.getText().toString());
                    edit_text_comment.setText("");
                    edit_text_comment.setHint("Say Something..");
                    llMessageEdittext.setVisibility(View.GONE);
                    comment_icon.setVisibility(View.VISIBLE);
                    CommonUtils.hideKeyboard(activity);
                } else {
                    try {
                        sendChannelMessage(edit_text_comment.getText().toString());
                        llMessageEdittext.setVisibility(View.GONE);
                        comment_icon.setVisibility(View.VISIBLE);
                        CommonUtils.hideKeyboard(activity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.comment_icon:
                if (muteLive.equalsIgnoreCase("1")) {
                    muteDialog();
                    llMessageEdittext.setVisibility(View.GONE);
                    comment_icon.setVisibility(View.VISIBLE);

                } else {
                    llMessageEdittext.setVisibility(View.VISIBLE);
                    comment_icon.setVisibility(View.GONE);
                }


                CommonUtils.openKeyboard(activity);
                break;
            case R.id.ivCloseComment:
                llMessageEdittext.setVisibility(View.GONE);
                comment_icon.setVisibility(View.VISIBLE);
                break;

            case R.id.iv_liveshare:
                Toast.makeText(activity, "Coming Soon", Toast.LENGTH_SHORT).show();
//                CommonUtils.shareLiveStrem(activity, liveId);
                break;

                case R.id.ll_otheruserDetails:

                    UserDetailsFragment userDetailsFragment = new UserDetailsFragment(otherPkUserId,false,true,false, adminStatuscheck,otherChannelName, pkBattleId, this::mentionName);

                    userDetailsFragment.show(getSupportFragmentManager(), userDetailsFragment.getTag());


                break;

            case R.id.tv_hideLayout:
                rl_hideLayout.setVisibility(View.GONE);
                tv_hideLayout.setVisibility(View.GONE);
                tv_showLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_showLayout:
                rl_hideLayout.setVisibility(View.VISIBLE);
                tv_hideLayout.setVisibility(View.VISIBLE);
                tv_showLayout.setVisibility(View.GONE);
                break;

            case R.id.tv_bulletMessage:
                messageType = "bullet";
                tv_bulletMessage.setBackgroundResource(R.drawable.bg_app_pink_colorgradient_round);
                tv_publicMessage.setBackgroundResource(R.drawable.grey_stroke);
                edit_text_comment.setHint("One Bullet per 1 Diamond");
                break;


            case R.id.tv_publicMessage:
                messageType = "public";
                tv_publicMessage.setBackgroundResource(R.drawable.bg_app_pink_colorgradient_round);
                tv_bulletMessage.setBackgroundResource(R.drawable.grey_stroke);
                edit_text_comment.setHint("One Public broadcast per 1000 Diamonds");
                break;

            case R.id.ll_giftBox:
                openBox();
                break;

            case R.id.tv_giftboxcount:
                openBox();
                break;
            case R.id.iv_box:
                openBox();
                break;

            case R.id.llUSers:

                openBottom();

                break;

            case R.id.ll_user_channel_image:
                beansDailog();
                break;
        }
    }

    private void OpenbottomTwo() {

        Dialog dialogChooseAlbum = new Dialog(this);
        dialogChooseAlbum.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChooseAlbum.setContentView(R.layout.bottom_viewers_top);
        dialogChooseAlbum.setCanceledOnTouchOutside(true);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialogChooseAlbum.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.5f);
        int dialogWindowHeight = (int) (displayHeight * 0.5f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialogChooseAlbum.getWindow().setAttributes(layoutParams);
        dialogChooseAlbum.getWindow().setLayout(layoutParams.width, layoutParams.height);

        dialogChooseAlbum.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT / 2);
        dialogChooseAlbum.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogChooseAlbum.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTwo;
        dialogChooseAlbum.getWindow().setGravity(Gravity.BOTTOM);

        RecyclerView recyclerViewersGifts;


        recyclerViewersGifts = dialogChooseAlbum.findViewById(R.id.recyclerViewersGifts);


        adapterTopCoins = new AdapterTopCoins(coinList, new AdapterTopCoins.UserInfo() {
            @Override
            public void sendData(ModelSendGift listviewers) {
                openOtherUserProfile(listviewers.getUserId(), false);
            }
        });
        recyclerViewersGifts.setAdapter(adapterTopCoins);


        dialogChooseAlbum.show();

    }

    private void beansDailog() {
        MainLeaderboardFragment beansBottomSheet = new MainLeaderboardFragment();
        beansBottomSheet.show(getSupportFragmentManager(), beansBottomSheet.getTag());
    }

    private void openBox() {
        liveMvvm.getGiftBox(activity, CommonUtils.getUserId(), liveId, box).observe(AgoraPkPlayerActivity.this, map -> {
            if (map.get("success").toString().equalsIgnoreCase("1")) {
                ll_giftBox.setVisibility(View.GONE);
//                animationView.setVisibility(View.VISIBLE);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            animationView.setVisibility(View.GONE);
//                            ll_giftBox.setVisibility(View.VISIBLE);
//                        }
//                    },5000);
//                    int oldCoins = 0;
//                    if (!tv_total_coins.getText().toString().equalsIgnoreCase("")) {
//                        oldCoins = Integer.parseInt(tv_total_coins.getText().toString());
//                    }
//                    int wincoin=Integer.parseInt(map.get("winCoin").toString());
//                    int updatedPrice = oldCoins + wincoin;
//                    tv_total_coins.setText(String.valueOf(updatedPrice));
//                Toast.makeText(activity, "Congratulations, You won " + map.get("winCoin").toString() + " Beans", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(activity, Objects.requireNonNull(map.get("message")).toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void publicBulletMessage(String text) {
        liveMvvm.publicBulletMsg(this, CommonUtils.getUserId(), messageType).observe(AgoraPkPlayerActivity.this, map -> {
            if (Objects.requireNonNull(map.get("success")).toString().equalsIgnoreCase("1")) {
                if (messageType.equalsIgnoreCase("public")) {
                    ModelBulletMessage modelBulletMessage = new ModelBulletMessage();
                    modelBulletMessage.setText(text);
                    modelBulletMessage.setUsername(name);
                    FirebaseHelper.sendPublicBulletMessage(channelName, modelBulletMessage);
                    messageType = "";
                } else if (messageType.equalsIgnoreCase("bullet")) {
                    ModelBulletMain modelBulletMain = new ModelBulletMain();
                    modelBulletMain.setUsername(name);
                    modelBulletMain.setTextmesssage(text);
                    FirebaseHelper.sendBulletMessage(channelName, modelBulletMain);
                    messageType = "";
                } else {

                }
            } else {
                messageType = "";
//                Toast.makeText(activity, map.get("message").toString(), Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(AgoraPkPlayerActivity.this, PurchaseCoinActivity.class));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void alertDailog() {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        final View confirmdailog = layoutInflater.inflate(R.layout.multi_request_dailog, null);
        final android.app.AlertDialog dailogbox = new android.app.AlertDialog.Builder(activity).create();
        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dailogbox.setView(confirmdailog);
        dailogbox.setCanceledOnTouchOutside(false);

        TextView tv_text = confirmdailog.findViewById(R.id.tv_text);
        tv_text.setText("Do you want to send a live session share request to" + " " + channelName);

        Button yes, no;

        yes = confirmdailog.findViewById(R.id.confirmBooking);
        no = confirmdailog.findViewById(R.id.btn_editBooking);

        yes.setText("Request");
        no.setText("Cancel");

        yes.setOnClickListener(v -> {
            requestStatus = true;
            sendMultiRequest("");
            dailogbox.dismiss();
        });
        no.setOnClickListener(view -> dailogbox.dismiss());
        dailogbox.show();
    }


    @Override
    public void onBackPressed() {

            leaveChannel();


        if (requestStatus) {
            if (isLive) {
                sendMultiRequest("5");

            } else {
                sendMultiRequest("4");
            }
            FirebaseHelper.removeHost(userId, channelName, removeHostEventListener);
        }

        if (countDownTimerPkBattle != null) {
            countDownTimerPkBattle.cancel();
        }

        FirebaseHelper.removeTimeForAudience(channelName, pkBattleTimerEventListener);

        List<ModelPrivateMessagePlayer> list = new ArrayList<>();
        App.getSingleton().setPrivateMessagePlayers(list);
        FirebaseHelper.removeUserLeaveChannel(myFirebaseKey, channelName, viewersEventListener, giftsEventListener);
        FirebaseHelper.removeBanListener(channelName, userId, liveBanEventListener);
        FirebaseHelper.removeAdminLiveStatus(channelName, adminStatusListser);
        stopService(new Intent(activity, PlayerExitService.class));
        finish();

    }

    ValueEventListener removeHostEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    protected void onDestroy() {

        if (mediaPlayer != null) stopAudio();
        leaveChannel();
        onBackPressed();
        super.onDestroy();
    }


    private void leaveChannel() {
        rtcEngine().leaveChannel();
        listMessages.clear();
    }

    public static Drawable LoadImageFromWebURL(String url) {
        try {
            InputStream iStream = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(iStream, "src name");
        } catch (Exception e) {
            return null;
        }
    }

    String image = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getImage();
    String username = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getUsername();
    String name = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getName();

    public void sendChannelMessage(String msg) {


        if (msg.isEmpty()) {
//            Toast.makeText(activity, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }


        if (userProfile == null) {
            Log.i("Agora:User Profile", "null");
            return;
        }

        edit_text_comment.setText("");
        ModelAgoraMessages modelAgoraMessages = new ModelAgoraMessages(msg, image, userProfile.getUserName(), CommonUtils.getUserId(), String.valueOf(level), 1, adminStatuscheck,System.currentTimeMillis(),mySteriousMan);
        FirebaseHelper.sendMessageFireBase(channelName, modelAgoraMessages);

        listMessages.add(modelAgoraMessages);
        adapterMessagesAgora.notifyItemInserted(adapterMessagesAgora.getItemCount());
        recycler_comment.smoothScrollToPosition(listMessages.size() - 1);
        CommonUtils.hideKeyboard(activity);
    }

    static boolean isPrivateMessageOpened = false;

    private void screeenshotNot() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        screeenshotNot();
        if (sound != null) {
            playAudio(sound);
        }

        rtcEngine().enableAudio();
        rtcEngine().enableVideo();

        FirebaseHelper.databaseReferenceParent.removeEventListener(giftsEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(adminStatusListser);
        FirebaseHelper.databaseReferenceParent.removeEventListener(fakeCountvalueEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(getMutevalueEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(valueEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(maxRequestValueEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(myMultiRequestStatus);
        FirebaseHelper.databaseReferenceParent.removeEventListener(pkBattleTimerEventListener);


        FirebaseHelper.databaseReferenceParent.removeEventListener(viewersEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(giftsEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(valueBulletEvent);
        FirebaseHelper.databaseReferenceParent.removeEventListener(bulletEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(valueBulletEvent);
    }

    private void renewToken(String channelName) {
        if (CommonUtils.isNetworkConnected(activity)) {
            ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);

            apiInterface.setAgoraChannel(channelName, CommonUtils.getUserId(), "", "", hostType, 0, String.valueOf(VideoGridContainer.MAX_USER)).enqueue(new Callback<ModelGetToken>() {
                @Override
                public void onResponse(@NonNull Call<ModelGetToken> call, @NonNull Response<ModelGetToken> response) {
                    try {
                        if (response.body() != null) {
                            if (response.body().getSuccess().equalsIgnoreCase("1")) {
                                configEngine().setChannelName(channelName);
                                App.getSingleton().setAgoraToken(response.body().getDetails().getToke());
                                finish();
                            } else {
//                                Toast.makeText(activity, response.body().getSuccess(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
//                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ModelGetToken> call, @NonNull Throwable t) {
//                    Toast.makeText(activity, "4" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
//            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

        @SuppressLint("UseCompatLoadingForDrawables")
        public void followUnfollow() {
            img_follow_unfollow.setOnClickListener(view -> {
                viewModelClass.followingRootLiveData(CommonUtils.getUserId(), userChannelId).observe(this, followingRoot -> {
                    if (followingRoot.getSuccess().equals("1")) {
                        if (followingRoot.isFollowing_status()) {
                            img_follow_unfollow.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_done_24));

                            img_follow_unfollow.setVisibility(View.GONE);

                        } else {
                            img_follow_unfollow.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_add_24));


                        }
                    }
                });

            });

    }
    private void openGiftsDialog() {

        GiftBottomSheetFragment giftBottomSheetFragment = new GiftBottomSheetFragment(userChannelId, channelName, liveId, pkBattleId);
        giftBottomSheetFragment.show(getSupportFragmentManager(), giftBottomSheetFragment.getTag());
    }

    @SuppressLint("SetTextI18n")
    private void KickOutDialog(String status) {
        LayoutInflater layoutInflater = LayoutInflater.from(AgoraPkPlayerActivity.this);
        final View confirmdailog = layoutInflater.inflate(R.layout.dialog_kickout, null);
        final AlertDialog dailogbox = new AlertDialog.Builder(AgoraPkPlayerActivity.this).create();

        dailogbox.setView(confirmdailog);
        dailogbox.setCanceledOnTouchOutside(false);

        TextView textView = confirmdailog.findViewById(R.id.tv_status);
        if (status.equalsIgnoreCase("0")) {
            textView.setText("You have been kicked out by this user .");
        } else if (status.equalsIgnoreCase("1")) {
            textView.setText("You have been  and blocked by this user.");
        }


        confirmdailog.findViewById(R.id.okKickOutText).setOnClickListener(v -> {
            activity.finish();
            dailogbox.dismiss();

        });

        if (!((Activity) activity).isFinishing()) {

        }

            leaveChannel();
        List<ModelPrivateMessagePlayer> list = new ArrayList<>();
        App.getSingleton().setPrivateMessagePlayers(list);
        FirebaseHelper.removeUserLeaveChannel(myFirebaseKey, channelName, viewersEventListener, giftsEventListener);
        FirebaseHelper.removeBanListener(channelName, userId, liveBanEventListener);
        stopService(new Intent(activity, PlayerExitService.class));
    }

    ChildEventListener fakeViewChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            ModelSetUserViewer modelSetUserViewer = snapshot.getValue(ModelSetUserViewer.class);

            assert modelSetUserViewer != null;
            if (!modelSetUserViewer.getUserId().equalsIgnoreCase(userId)) {
                listViewers.add(modelSetUserViewer);
            }
            getViewCounts();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            ModelSetUserViewer modelSetUserViewer = snapshot.getValue(ModelSetUserViewer.class);
            for (int i = 0; i < listViewers.size(); i++) {
                assert modelSetUserViewer != null;
                if (listViewers.get(i).getUserId().equalsIgnoreCase(modelSetUserViewer.getUserId())) {
                    listViewers.remove(i);
                    adapterUserList.notifyDataSetChanged();
                }
            }

            getViewCounts();
        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    ValueEventListener fakeCountvalueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                FirebaseHelper.getFakeViewers(fakeViewChildEventListener, Integer.parseInt(String.valueOf(snapshot.getValue())));
            } else {


                FirebaseHelper.getfakeCounts("defaultFakeCounts", fakeCountvalueEventListener);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    ValueEventListener getMutevalueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {


                muteLive = (String) snapshot.getValue();
                assert muteLive != null;
                if (muteLive.equals("0")) {

                    llMessageEdittext.setVisibility(View.GONE);
                    ll_message_share_gift.setVisibility(View.VISIBLE);
                    comment_icon.setVisibility(View.VISIBLE);
                } else {

                    comment_icon.setVisibility(View.VISIBLE);
                    llMessageEdittext.setVisibility(View.GONE);
                    ll_message_share_gift.setVisibility(View.VISIBLE);
                    muteDialog();
                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void muteDialog() {
        final AlertDialog.Builder al = new AlertDialog.Builder(AgoraPkPlayerActivity.this, R.style.dialogStyle);
        al.setTitle("Muted?").setPositiveButton("Okay", (dialog, which) -> dialog.dismiss()).setMessage("You are muted by the Host").show();
    }


    ValueEventListener bulletEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                ModelBulletMessage modelBulletMessage = snapshot.getValue(ModelBulletMessage.class);
                showBulletMessage(modelBulletMessage);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
    private final long enterTime = System.currentTimeMillis();
    ValueEventListener messageEventLisner = new ValueEventListener() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            listMessages.subList(3, listMessages.size()).clear();
            if (snapshot.exists()) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelAgoraMessages chatMessageModel = dataSnapshot.getValue(ModelAgoraMessages.class);

                    assert chatMessageModel != null;
                    if (chatMessageModel.getTimeStamp() >= enterTime) {
                        listMessages.add(chatMessageModel);
                    }
                }
                adapterMessagesAgora.notifyDataSetChanged();
                recycler_comment.scrollToPosition(listMessages.size() - 1);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };

    private String adminStatuscheck = "";
    ValueEventListener valueEventListenerAdminTwo = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists()) {
                android.util.Log.i("Agora Live Ban userId: ", snapshot.toString());
                adminStatuscheck = snapshot.child("subAdminStatus").getValue(String.class);
            } else {
                adminStatuscheck = "";
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
    ValueEventListener valueEventListenerHost = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists()) {
                Log.i("Agora Live Ban userId: ", snapshot.toString());
                hostId = snapshot.getValue(Integer.class);
//                Toast.makeText(activity, "" + hostId, Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };
}