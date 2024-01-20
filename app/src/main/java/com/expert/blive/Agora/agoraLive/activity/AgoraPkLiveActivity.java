package com.expert.blive.Agora.agoraLive.activity;

import static android.content.ContentValues.TAG;
import static com.expert.blive.Agora.agoraLive.firebase.FirebaseHelper.databaseReferenceParent;
import static com.expert.blive.Agora.agoraLive.ui.VideoGridContainer.fakeViewController;
import static com.expert.blive.Agora.agoraLive.ui.VideoGridContainer.fakeViewControllerThree;
import static com.expert.blive.Agora.agoraLive.ui.VideoGridContainer.fakeViewControllerTwo;
import static com.expert.blive.Agora.agoraLive.ui.VideoGridContainer.isNotHost;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
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
import android.widget.Chronometer;
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
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

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
import com.expert.blive.Agora.agoraLive.MultiLiveHostsModel;
import com.expert.blive.Agora.agoraLive.UserMultiLiveDetailsModel;
import com.expert.blive.Agora.agoraLive.adapters.AdapterMessagesAgora;
import com.expert.blive.Agora.agoraLive.adapters.AdapterPkRequestReceived;
import com.expert.blive.Agora.agoraLive.adapters.AdapterTopCoins;
import com.expert.blive.Agora.agoraLive.adapters.AdapterUserForPkInvite;
import com.expert.blive.Agora.agoraLive.firebase.ChildEventListenersObject;
import com.expert.blive.Agora.agoraLive.firebase.FirebaseHelper;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSendGift;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSetUserViewer;
import com.expert.blive.Agora.agoraLive.firebase.models.MultiLiveModelCount;
import com.expert.blive.Agora.agoraLive.models.LiveDescriptionModel;
import com.expert.blive.Agora.agoraLive.models.ModelAgoraMessages;
import com.expert.blive.Agora.agoraLive.models.ModelGetToken;
import com.expert.blive.Agora.agoraLive.models.ModelPkRequest;
import com.expert.blive.Agora.agoraLive.models.model.ModelBulletMain;
import com.expert.blive.Agora.agoraLive.rtc.RtcEventHandler;
import com.expert.blive.Agora.agoraLive.stats.LocalStatsData;
import com.expert.blive.Agora.agoraLive.stats.RemoteStatsData;
import com.expert.blive.Agora.agoraLive.stats.StatsData;
import com.expert.blive.Agora.agoraLive.ui.GiftAnimWindow;
import com.expert.blive.Agora.agoraLive.ui.VideoGridContainer;
import com.expert.blive.Agora.agoraLive.utils.Config;
import com.expert.blive.Agora.agoraLive.utils.FileUtil.Constants;
import com.expert.blive.Agora.agoraLive.utils.OfflineWorkManager;
import com.expert.blive.Agora.agoraLive.utils.SoundSelected;
import com.expert.blive.Agora.agoraLive.utils.services.LiveExitService;
import com.expert.blive.ExtraFragments.AgoraBeautifyFragment;
import com.expert.blive.ExtraFragments.MessagesFragment;
//import com.expert.blive.ExtraFragments.TopGifterFragment;
import com.expert.blive.HomeActivity.Leaderboard.MainLeaderboardFragment;
import com.expert.blive.ModelClass.EndPkLive;
import com.expert.blive.ModelClass.GrtFriendsLiveDetails;
import com.expert.blive.ModelClass.ModelAgoraLiveUsers;
import com.expert.blive.ModelClass.ModelBulletMessage;
import com.expert.blive.ModelClass.ModelPkTimeLimit;
import com.expert.blive.ModelClass.ModelUserEntry;
import com.expert.blive.ModelClass.OtpRoot;
//import com.expert.nicolive.R;
import com.expert.blive.bottomteenpati.GameBottomFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;
import com.expert.blive.retrofit.GetWinnerPkBattlePojo;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.AppConstants;
import com.expert.blive.utils.CommonUtils;
import com.expert.blive.R;
import com.google.android.exoplayer2.Player;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.opensource.svgaplayer.SVGAImageView;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;
import io.agora.rtc.IAudioEffectManager;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.video.BeautyOptions;
import io.agora.rtc.video.ChannelMediaInfo;
import io.agora.rtc.video.ChannelMediaRelayConfiguration;
import io.agora.rtc.video.VideoEncoderConfiguration;

import okhttp3.RequestBody;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgoraPkLiveActivity extends RtcBaseActivity implements View.OnClickListener, RtcEventHandler, SoundSelected, Player.EventListener, UserDetailsFragment.MentionFriend {
    private String channelName, token, rtmoken, hostType = "", messageType = "";
    private VideoGridContainer mVideoGridContainer;
    private SVGAImageView muCarsRVImagee;
    private ImageView mMuteAudioBtn, imgWinner, imgLooser;
    private String otherUserImage;
    AlertDialog alertDialog;
    String pkId = "", pktype = "", requestUserID = "";
    int count = 0;
    private ImageView iv_smallEntryGif,iv_otherHostImage;
    private ImageView iv_otheiv_pkBattlerHostImage;
    private ImageView iv_smallGif;
    private ImageView mMuteVideoBtn;
    private ImageView live_btn_camera_rotate;
    private ImageView live_btn_torch;
    private ImageView iv_pkBattle;
    private ImageView imageGame;
    long startCount;
    private MediaPlayer mediaPlayer;
    private ImageView iv_pkRequest;
    private Activity activity;
    private LinearLayout ll_starts, ll_user_channel_image, ll_Looser;
    private AdapterTopCoins adapterTopCoins;
    private final List<ModelSendGift> coinList = new ArrayList<>();
    private MvvmViewModelClass viewModelClass;
    private AdapterUserList adapterUserList;
    private EditText edit_text_comment;
    private VideoEncoderConfiguration.VideoDimensions mVideoDimension;
    private final List<ModelAgoraMessages> listMessages = new ArrayList<>();
    private final List<ModelSetUserViewer> listViewers = new ArrayList<>();
    private AdapterMessagesAgora adapterMessagesAgora;
    private RecyclerView recycler_comment, recyclerViewersOne;
    private TextView tv_views_live, iv_profiles, lltextMessage;
    private TextView tv_music;
    private TextView tv_smallGiftName;
    private TextView smallGiftGiftName;
    private TextView tv_mainBulletMessage;
    private TextView tvGiftBoxCount;
    private RecyclerView recyclerTopGift;
    private LinearLayout ll_comments, ll_bigGiftMessage, ll_star_gained, ll_WinnerLooser;
    private TextView tv_closeBattle, tv_total_coins, tv_level, tv_stars, tvBigGiftGiftNameTo, tvBigGiftNameTo, tv_publicMessage, tv_bulletMessage;
    private String level, starCountDefault, getLiveId = "", boxStatus, sound;
    private RelativeLayout rl_coins;
    private RelativeLayout rl_smallGift;
    private RelativeLayout rl_smallEntry;
    private RelativeLayout rlCommentBackground;
    private RelativeLayout rlPublicMessage;
    private ImageView comment_icon;
    private ImageView img_small_video;
    private Chronometer chronometer;
    private LiveMvvm liveMvvm;
    private List<LiveDescriptionModel.Detail> list = new ArrayList<>();
    int maxHostCounts = 1;
    LinearLayout linearLayout, relative_msg, ll_giftBox, llLiveUsers;
    GifImageView gifImageView;
    AdapterPkRequestReceived adapterPkRequestReceived;
    private final List<ModelPkRequest> listPkRequest = new ArrayList<>();
    private Dialog dialogPkRequest;
    private ModelPkRequest pkRequestFinalHost;
    private boolean isBattlePlayed = false;
    private CountDownTimer countDownTimerPkBattle;
    private TextView tv_tv_timeLeft, tv_smallEntryName, smallEntryGiftName, upperCoins, bottomCoins, tv_upperHostName, tv_bottomHostName, tv_bottomHostId, tv_publicMessageText;
    AdapterUserForPkInvite adapterUserForPkInvite;
    List<GrtFriendsLiveDetails.Deatil> listMyLiveFreinds = new ArrayList<>();
    List<ModelAgoraLiveUsers.Detail> listMyLiveFRandom = new ArrayList<>();
    private int timerLimit = 5;
    private long ts;
    private final int visibleInt = 1;
    private final int goneInt = 8;
    private AudioManager audioManager;
    CircleImageView cv_bigGiftImage, smallGiftImage, smallEntryImage, otherUser, userProfileImage;
    LottieAnimationView animationView;
    private String liveBox = "0", box, liveId = "", bool = "",floating;
    private Handler handler;
    private Runnable runnable;
    private static AgoraPkLiveActivity instance;
    View viewMultiLive;
    ScheduledExecutorService exec;
    CirclePageIndicator indicator;
    private SVGAImageView profileFrame;
    private RelativeLayout rl_viewpager;
    private ImageView iv_giftImage, llMessage;
    int delay = 0;
    RequestBody img_rb, userId_rb, description_rb, type_rb;
    private Config.UserProfile userProfile;
    public static String live_status;
    private final MultiLiveHostsModel multiLiveHostsModel = new MultiLiveHostsModel();
    private final ArrayList<UserMultiLiveDetailsModel> listUserMultiLiveDetailsModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_agora_pk_live);

        instance = this;
        getWindow().setBackgroundDrawableResource(R.drawable.bg_gradient);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        liveMvvm = new ViewModelProvider(this).get(LiveMvvm.class);
        activity = AgoraPkLiveActivity.this;
        getLiveId = getIntent().getStringExtra("liveid");
        channelName = getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_CHANNEL_NAME);
        token = getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_TOKEN);
        rtmoken = getIntent().getExtras().getString(AgoraLiveMainActivity.AGORA_RTM_TOKEN);
        level = getIntent().getExtras().getString(AgoraLiveMainActivity.USER_LEVEL, "0");
        starCountDefault = getIntent().getExtras().getString(AgoraLiveMainActivity.USER_STAR_COUNT, "0");
        boxStatus = getIntent().getExtras().getString("boxstatus");
        hostType = getIntent().getExtras().getString("hostType");
        box = getIntent().getExtras().getString("box");
        liveId = getIntent().getExtras().getString(AppConstants.AGORA_LIVE_ID, "");
        bool = getIntent().getExtras().getString("bool");

        Toast.makeText(activity, "liveId="+liveId, Toast.LENGTH_SHORT).show();

        App.getSingleton().setMyPk("0");
        App.getSingleton().setMyPkBattle("1");
        getMessageCount();
        fidIds();
        getTopGifters();
        getDetail();

        OtpRoot modelLoginRegister = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

        multiLiveHostsModel.setChannelName(channelName);
        multiLiveHostsModel.setUserid(modelLoginRegister.getId());
        multiLiveHostsModel.setUserImage(modelLoginRegister.getImage());
        multiLiveHostsModel.setUsername(modelLoginRegister.getUsername());
        //updateFirebaseLiveList(listUserMultiLiveDetailsModel);
        App.getSharedpref().saveString(AppConstants.ID, liveId);

//        findViewById(R.id.iv_story_share).setOnClickListener(v -> shareStory());

        initCreateValues();

        adapterPkRequestReceived = new AdapterPkRequestReceived(activity, listPkRequest, pos -> {
            newPkRequestAlert(listPkRequest.get(pos));
            if (dialogPkRequest != null) {
                dialogPkRequest.dismiss();
            }
        });
        setFrame();

        if (VideoGridContainer.MAX_USER == 5) {
            iv_pkBattle.setVisibility(View.GONE);
            findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);
            controlFakeViewer();
        } else if (VideoGridContainer.MAX_USER == 6) {
            iv_pkBattle.setVisibility(View.GONE);
            findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);

            controlFakeViewerTwo();
        } else if (VideoGridContainer.MAX_USER == 9) {
            findViewById(R.id.ll_linearDummy).setVisibility(View.VISIBLE);

            iv_pkBattle.setVisibility(View.GONE);
            controlFakeViewerThree();

        }
    }

    private void getDetail() {

        HashMap<String, String> data = new HashMap<>();
        data.put("userId", CommonUtils.getUserId());
        data.put("otherUserId", CommonUtils.getUserId());
        viewModelClass.someFunctionality(this, data).observe(AgoraPkLiveActivity.this, response -> {
            if (response !=null){
                if (response.getStatus().equalsIgnoreCase("1")){
                    floating = String.valueOf(response.getDetails().getBuy());
                }
            }
        });
    }

    private void setFrame() {
        viewModelClass.getAppliedFrames(AgoraPkLiveActivity.this, CommonUtils.getUserId(), "0").observe(AgoraPkLiveActivity.this, rootFrames -> {
            if (rootFrames != null) {
                if (rootFrames.getSuccess().equals(1)) {
                    CommonUtils.setAnimation(AgoraPkLiveActivity.this,rootFrames.getDetails().getImage(),profileFrame);
                    App.getSharedpref().saveModel(AppConstants.FRAMEINFO, rootFrames.getDetails());
                }
            } else {
                Toast.makeText(AgoraPkLiveActivity.this, "Technical issue.....", Toast.LENGTH_SHORT).show();
            }
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


            ;

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


            userNine.setOnClickListener(view -> {


//                openDialogGift(dataInvite);

            });
            imageView.setOnClickListener(view -> {

//                openDialogGift(dataInvite);

            });
            userEight.setOnClickListener(view -> {
//                openDialogGift(dataInvite);

            });
            userSeven.setOnClickListener(view -> {
//                openDialogGift(dataInvite);

            });
            userSix.setOnClickListener(view -> {
//                openDialogGift(dataInvite);

            });
            userFive.setOnClickListener(view -> {
//                openDialogGift(dataInvite);

            });
            userFourImage.setOnClickListener(view -> {
//                openDialogGift(dataInvite);

            });
            userThreeImage.setOnClickListener(view -> {
//                openDialogGift(dataInvite);

            });
            userTwoImage.setOnClickListener(view -> {
//                openDialogGift(dataInvite);

            });


            if (fakeViewControllerModel.sizeOfList > 0) {

                linearLayoutTwo.setVisibility(View.VISIBLE);

                if (fakeViewControllerModel.sizeOfList == 1) {

                    imageView.setVisibility(View.INVISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 2) {
                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 3) {
                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 4) {
                    imageView.setVisibility(View.INVISIBLE);

                    userTwoImage.setVisibility(View.INVISIBLE);

                    userThreeImage.setVisibility(View.INVISIBLE);

                    userFourImage.setVisibility(View.INVISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 5) {
                    imageView.setVisibility(View.INVISIBLE);

                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);

                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.INVISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 6) {
                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);

                    userFourImage.setVisibility(View.INVISIBLE);

                    userThreeImage.setVisibility(View.INVISIBLE);

                    userFive.setVisibility(View.INVISIBLE);

                    userSix.setVisibility(View.INVISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 7) {
                    imageView.setVisibility(View.INVISIBLE);

                    userTwoImage.setVisibility(View.INVISIBLE);

                    userThreeImage.setVisibility(View.INVISIBLE);

                    userFourImage.setVisibility(View.INVISIBLE);

                    userFive.setVisibility(View.INVISIBLE);

                    userSix.setVisibility(View.INVISIBLE);

                    userSeven.setVisibility(View.INVISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 8) {
                    imageView.setVisibility(View.INVISIBLE);

                    userTwoImage.setVisibility(View.INVISIBLE);

                    userThreeImage.setVisibility(View.INVISIBLE);

                    userFourImage.setVisibility(View.INVISIBLE);

                    userFive.setVisibility(View.INVISIBLE);

                    userSix.setVisibility(View.INVISIBLE);

                    userSeven.setVisibility(View.INVISIBLE);

                    userEight.setVisibility(View.INVISIBLE);

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
//                openDialogGift(dataInvite);
            });
            userFive.setOnClickListener(view -> {
//                openDialogGift(dataInvite);
            });
            userThreeImage.setOnClickListener(view -> {
//                openDialogGift(dataInvite);
            });

            userSix.setOnClickListener(view -> {
//                openDialogGift(dataInvite);
            });
            userFourImage.setOnClickListener(view -> {
//                openDialogGift(dataInvite);
            });


            if (fakeViewControllerModel.sizeOfList > 0) {

                linearLayoutTwo.setVisibility(View.VISIBLE);

                if (fakeViewControllerModel.sizeOfList == 1) {


                    imageView.setVisibility(View.INVISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 2) {
                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 3) {
                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);
                    userThreeImage.setVisibility(View.INVISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 4) {
                    imageView.setVisibility(View.INVISIBLE);

                    userTwoImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 5) {
                    imageView.setVisibility(View.INVISIBLE);

                    userTwoImage.setVisibility(View.INVISIBLE);
                    userFourImage.setVisibility(View.INVISIBLE);
                    userFive.setVisibility(View.INVISIBLE);

                } else {
                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.INVISIBLE);

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

    private void getTopGifters() {


    }

    private void fidIds() {
        recyclerViewersOne = findViewById(R.id.recyclerViewersOne);
        ll_starts = findViewById(R.id.ll_starts);
        ll_starts.setOnClickListener(this);
        llMessage = findViewById(R.id.llMeesageLive);
        profileFrame = findViewById(R.id.profileFrame);

        llMessage.setOnClickListener(view -> {
            MessagesFragment userDetailsFragment = new MessagesFragment("1");
            userDetailsFragment.show(getSupportFragmentManager(), userDetailsFragment.getTag());
        });

    }


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


            LinearLayout linearLayoutOne = findViewById(R.id.llFakeViewCreate);

            ImageView imageView, userTwoImage, userThreeImage, userFourImage;

            imageView = findViewById(R.id.userOneImage);
            userTwoImage = findViewById(R.id.userTwoImage);
            userThreeImage = findViewById(R.id.userThreeImage);
            userFourImage = findViewById(R.id.userFourImage);


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


            userTwoImage.setOnClickListener(view -> {

//                openDialogGift(dataInvite);
            });
            userFourImage.setOnClickListener(view -> {

//                openDialogGift(dataInvite);
            });
            userThreeImage.setOnClickListener(view -> {

//                openDialogGift(dataInvite);
            });

            if (fakeViewControllerModel.sizeOfList > 0) {

                linearLayoutOne.setVisibility(View.VISIBLE);


                if (fakeViewControllerModel.sizeOfList == 1) {

                    imageView.setVisibility(View.INVISIBLE);
                    userTwoImage.setVisibility(View.VISIBLE);
                    userThreeImage.setVisibility(View.VISIBLE);
                    userFourImage.setVisibility(View.VISIBLE);

                } else if (fakeViewControllerModel.sizeOfList == 2) {
                    imageView.setVisibility(View.INVISIBLE);

                    userTwoImage.setVisibility(View.INVISIBLE);

                    userThreeImage.setVisibility(View.VISIBLE);
                    userFourImage.setVisibility(View.VISIBLE);


                } else if (fakeViewControllerModel.sizeOfList == 3) {
                    imageView.setVisibility(View.INVISIBLE);

                    userTwoImage.setVisibility(View.INVISIBLE);

                    userThreeImage.setVisibility(View.INVISIBLE);

                    userFourImage.setVisibility(View.VISIBLE);

                } else {
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

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Video.Media.DATA};
        @SuppressLint("Recycle") Cursor cursor = getApplicationContext().getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    public static Bitmap loadBitmapFromView(Context context, View v) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        v.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.EXACTLY));
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        Bitmap returnedBitmap = Bitmap.createBitmap(v.getMeasuredWidth(),
                v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(returnedBitmap);
        v.draw(c);

        return returnedBitmap;
    }

    private void updateLiveTime() {
        exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            String time = df.format(new Date());
            HashMap<String, String> map = new HashMap<>();
            map.put("channelName", channelName);
            map.put("lastTime", time);
            map.put("liveId", liveId);
            Log.i("time", time);
            FirebaseHelper.updateLiveTime(channelName, map);
        }, 0, 15, TimeUnit.SECONDS);
    }

    public static AgoraPkLiveActivity getInstance() {
        return instance;
    }

    public void enableAgora() {
        rtcEngine().enableVideo();
        rtcEngine().enableAudio();
    }

    public void disableAgora() {
        rtcEngine().disableVideo();
        rtcEngine().disableAudio();
    }


    private void initCreateValues() {
        try {
//            initMessageChatManager();
            initUI();
            initData();
            initUserIcon();
//            joinRTmChannel();
            setRecycler();
            setUserProfile();
            initServicesAsync();
            setLIveDescription();
        } catch (Exception ignored) {
        }

        if (App.getSingleton().getAudioLive().equalsIgnoreCase("1")) {
            App.getSingleton().setAudioLive("");
            gifImageView.setVisibility(View.VISIBLE);
            mVideoGridContainer.setVisibility(View.GONE);
            live_btn_torch.setVisibility(View.GONE);
            iv_pkBattle.setVisibility(View.GONE);
            live_btn_camera_rotate.setVisibility(View.GONE);
        }

        if (App.getSingleton().getHostType().equalsIgnoreCase("1")) {
            iv_pkBattle.setVisibility(View.VISIBLE);
            iv_pkRequest.setVisibility(View.VISIBLE);
            iv_pkRequest.setVisibility(View.VISIBLE);
        }


        if (App.getSingleton().getHostType().equalsIgnoreCase("1")) {
            viewMultiLive.setVisibility(View.VISIBLE);
        }


    }


    @SuppressLint("SetTextI18n")
    private void newPkRequestAlert(ModelPkRequest listPkRequest) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        final View confirmdailog = layoutInflater.inflate(R.layout.multi_request_dailog, null);
        final AlertDialog dailogbox = new AlertDialog.Builder(activity).create();
        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dailogbox.setView(confirmdailog);
        dailogbox.setCanceledOnTouchOutside(false);

        TextView tv_text = confirmdailog.findViewById(R.id.tv_text);
        tv_text.setText(listPkRequest.getMyName() + " " + "wants to join a PK battle with you.");

        ImageView iv_image = confirmdailog.findViewById(R.id.iv_image);
        iv_image.setVisibility(View.VISIBLE);

        iv_image.setImageResource(R.drawable.ic_user1);

        try {
            Glide.with(AgoraPkLiveActivity.this).load(listPkRequest.getImage()).placeholder(R.drawable.ic_user1).into(iv_image);
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }

        Button acceptBtn = confirmdailog.findViewById(R.id.confirmBooking);

        acceptBtn.setEnabled(true);

        acceptBtn.setOnClickListener(v -> hitGenerateToken(listPkRequest, dailogbox));

        confirmdailog.findViewById(R.id.btn_editBooking).setOnClickListener(view -> {
            FirebaseHelper.deletePkRequest(listPkRequest, listPkRequest.getRequestChannelName(), channelName);
            dailogbox.dismiss();
        });
        if (!activity.isFinishing()) {
            //show dialog
            dailogbox.show();
        }

    }

    private void hitGenerateToken(ModelPkRequest listPkRequest, AlertDialog dailogbox) {

        HashMap<String, String> data = new HashMap<>();


        data.put("userId", userId);
        data.put("liveId", liveId);
        data.put("otherUserId", listPkRequest.getRequestUserID());
        data.put("otherLiveId", listPkRequest.getLiveId());

        requestUserID = listPkRequest.getLiveId();

        viewModelClass.pkBattle(this, data).observe(this, pkBattleModel -> {
            if (pkBattleModel.getStatus() == 1) {

                isNotHost = false;

                ChannelMediaInfo channelMediaInfo = new ChannelMediaInfo(channelName, token, 0);
                ChannelMediaRelayConfiguration mediaRelayConfiguration = new ChannelMediaRelayConfiguration();
                mediaRelayConfiguration.setSrcChannelInfo(channelMediaInfo);

                ChannelMediaInfo mediaInfo = new ChannelMediaInfo(listPkRequest.getRequestChannelName(), listPkRequest.getToken(), 0);
                mediaRelayConfiguration.setDestChannelInfo(listPkRequest.getRequestChannelName(), mediaInfo);

                rtcEngine().startChannelMediaRelay(mediaRelayConfiguration);

                mMuteAudioBtn.setActivated(true);
                cameraFocus();

                listPkRequest.setRequestStatus("1");
                listPkRequest.setOtherImage(image);
                listPkRequest.setPkBattleId(pkBattleModel.getDetails().toString());

                pkId = pkBattleModel.getDetails().toString();

                pktype = "1";
                rtcEngine().setEnableSpeakerphone(true);
                VideoGridContainer.MAX_USER = 2;
                FirebaseHelper.sendPkRequest(listPkRequest, listPkRequest.getRequestChannelName(), channelName);


//            pkBattleUpdate(listPkRequest);
                setTimerStart(listPkRequest);

                listPkRequest.setRequestStatus("1");
                tv_closeBattle.setVisibility(View.GONE);
                if (dialogPkRequest != null) {
                    dialogPkRequest.dismiss();
                }

                dailogbox.dismiss();

            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void alertDailog(MultiLiveModelCount multiLiveModelCount) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        final View confirmdailog = layoutInflater.inflate(R.layout.multi_request_dailog, null);
        final AlertDialog dailogbox = new AlertDialog.Builder(activity).create();
        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dailogbox.setView(confirmdailog);
        dailogbox.setCanceledOnTouchOutside(false);

        TextView tv_text = confirmdailog.findViewById(R.id.tv_text);
        tv_text.setText(multiLiveModelCount.getName() + " " + getString(R.string.wants_to_join_with_you_in_live_session));

        ImageView iv_image = confirmdailog.findViewById(R.id.iv_image);
        iv_image.setVisibility(View.VISIBLE);

        iv_image.setImageResource(R.drawable.ic_user1);

        try {
            Glide.with(AgoraPkLiveActivity.this).load(multiLiveModelCount.getImage()).placeholder(R.drawable.ic_user1).into(iv_image);
        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }


        Button acceptBtn = confirmdailog.findViewById(R.id.confirmBooking);

        acceptBtn.setEnabled(true);

        acceptBtn.setOnClickListener(v -> {
            otherUserImage = multiLiveModelCount.getImage();
            listUserMultiLiveDetailsModel.add(new UserMultiLiveDetailsModel("", multiLiveModelCount.getName(), multiLiveModelCount.getImage(), multiLiveModelCount.getUserId()));


            acceptBtn.setEnabled(false);
            multiLiveModelCount.setRequestStatus("1");
//                rtcEngine().enableAudio();
            sendMultiRequest(multiLiveModelCount);
//                audioInSpeaker();
//                rtcEngine().setDefaultAudioRouteToSpeakerphone(true);
            dailogbox.dismiss();
        });
        confirmdailog.findViewById(R.id.btn_editBooking).setOnClickListener(view -> {
//                FirebaseHelper.removeMultiRequest(multiLiveModelCount.getUserId());
            multiLiveModelCount.setRequestStatus("0");
            sendMultiRequest(multiLiveModelCount);
            dailogbox.dismiss();
        });
        if (!activity.isFinishing()) {
            //show dialog
            dailogbox.show();
        }
    }


    private void setTimerStart(ModelPkRequest modelPkRequest) {

        layoutHideUnHide(visibleInt);
        hideShowPkBatleIcons(true);
        ts = new Date().getTime();

        ModelPkTimeLimit modelPkTimeLimit = new ModelPkTimeLimit();
        modelPkTimeLimit.setTs(ts);
        modelPkTimeLimit.setTimeLimitPkbattle(modelPkRequest.getTimeLimit());
        modelPkTimeLimit.setPkBattleId(modelPkRequest.getPkBattleId());
        modelPkTimeLimit.setRequestedCoins(modelPkRequest.getRequestedCoins());
        modelPkTimeLimit.setOtherCoins(modelPkRequest.getOtherCoins());

        modelPkTimeLimit.setRequestName(modelPkRequest.getMyName());
        modelPkTimeLimit.setOtherName(modelPkRequest.getOtherName());
        modelPkTimeLimit.setRequestChannelID(modelPkRequest.getRequestChannelName());
        modelPkTimeLimit.setOtherChannelID(modelPkRequest.getOtherChannelName());
        modelPkTimeLimit.setRequestedImage(modelPkRequest.getRequestedImage());
        modelPkTimeLimit.setOtherImage(modelPkRequest.getOtherImage());
        modelPkTimeLimit.setOtherId(modelPkRequest.getOtherUserId());
        modelPkTimeLimit.setRequestedId(modelPkRequest.getRequestUserID());
        modelPkTimeLimit.setStatus(modelPkRequest.getRequestStatus());

        FirebaseHelper.setTimerForAudience(channelName, modelPkRequest.getOtherChannelName(), modelPkTimeLimit, true);


        isBattlePlayed = true;
        long dur = (modelPkRequest.getTimeLimit() * 60L) * 1000;

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

                Log.d("onFinish", "onFinish: " + pkId);

                calculateGifts(pkId);

            }
        };
        countDownTimerPkBattle.start();
    }

    private void layoutHideUnHide(int check) {

        findViewById(R.id.tv_upperHostCoins).setVisibility(check);
        findViewById(R.id.tv_bottomHostCoins).setVisibility(check);
        findViewById(R.id.ll_otheruserDetails).setVisibility(check);
        findViewById(R.id.viewPkLine).setVisibility(check);
        findViewById(R.id.rl_contqaier).setVisibility(check);
        findViewById(R.id.ll_time).setVisibility(check);
        findViewById(R.id.ll_linearDummy).setVisibility(check);

    }

    private void calculateGifts(String pkHostId) {
        HashMap<String, String> data = new HashMap<>();
        data.put("type", "");
        data.put("pkId", pkId);


        viewModelClass.archivedPkBattle(this,data).observe(this, endPkLive -> {
        });
        viewModelClass.getpkResult(pkId).observe(AgoraPkLiveActivity.this, getWinnerPkBattlePojo -> {
            if (getWinnerPkBattlePojo.getStatus() == 1) {
                winLossDailog(getWinnerPkBattlePojo.getDetails());
            }
        });





    }

    @SuppressLint("SetTextI18n")
    private void winLossDailog(List<GetWinnerPkBattlePojo.Detail> getWinnerPkBattlePojo) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_to_left);
        Animation animationTwo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_to_right);

        ll_WinnerLooser.setVisibility(View.VISIBLE);


        if (getWinnerPkBattlePojo.get(0).getResult().equalsIgnoreCase("tie")) {
            ll_Looser.setVisibility(View.GONE);
            imgWinner.setImageResource(R.drawable.draw);
            imgWinner.startAnimation(animation);
        } else {
            if (getWinnerPkBattlePojo.get(0).getId().equalsIgnoreCase(CommonUtils.getUserId())) {
                imgWinner.setImageResource(R.drawable.winnerpk);
                imgLooser.setImageResource(R.drawable.looser);
            } else {
                imgWinner.setImageResource(R.drawable.looser);
                imgLooser.setImageResource(R.drawable.winnerpk);
            }
            imgWinner.startAnimation(animation);
            imgLooser.startAnimation(animationTwo);
        }


        new Handler().postDelayed(() -> {
            ll_Looser.setVisibility(View.VISIBLE);
            ll_WinnerLooser.setVisibility(View.GONE);
            hideShowPkBatleIcons(false);
            removePkBattle();
        }, 4000);

        new Handler().postDelayed(() -> {
        }, animationTwo.getDuration());

    }

    private void removePkBattle() {

        layoutHideUnHide(goneInt);

        pkRequestFinalHost.setRequestStatus("2");

        if (pkRequestFinalHost.getRequestUserID().equalsIgnoreCase(userId)) {
            FirebaseHelper.sendPkRequest(pkRequestFinalHost, channelName, pkRequestFinalHost.getOtherChannelName());
        } else {
            FirebaseHelper.sendPkRequest(pkRequestFinalHost, pkRequestFinalHost.getRequestChannelName(), channelName);
        }


        isBattlePlayed = false;
        VideoGridContainer.MAX_USER = 4;
        hideShowPkBatleIcons(false);
        tv_tv_timeLeft.setVisibility(View.GONE);
        rtcEngine().stopChannelMediaRelay();

        pkId = "";
        ModelPkTimeLimit modelPkTimeLimit = new ModelPkTimeLimit();
        modelPkTimeLimit.setTs(ts);
        modelPkTimeLimit.setTimeLimitPkbattle(pkRequestFinalHost.getTimeLimit());
        modelPkTimeLimit.setPkBattleId(pkRequestFinalHost.getPkBattleId());
        modelPkTimeLimit.setRequestedCoins(pkRequestFinalHost.getRequestedCoins());
        modelPkTimeLimit.setOtherCoins(pkRequestFinalHost.getOtherCoins());
        modelPkTimeLimit.setRequestName(pkRequestFinalHost.getMyName());
        modelPkTimeLimit.setOtherName(pkRequestFinalHost.getOtherName());
        modelPkTimeLimit.setRequestChannelID(pkRequestFinalHost.getRequestChannelName());
        modelPkTimeLimit.setOtherChannelID(pkRequestFinalHost.getOtherChannelName());
        modelPkTimeLimit.setRequestedImage(pkRequestFinalHost.getRequestedImage());
        modelPkTimeLimit.setOtherImage(pkRequestFinalHost.getOtherImage());
        modelPkTimeLimit.setOtherId(pkRequestFinalHost.getOtherUserId());
        modelPkTimeLimit.setRequestedId(pkRequestFinalHost.getRequestUserID());
        modelPkTimeLimit.setStatus(pkRequestFinalHost.getRequestStatus());

        FirebaseHelper.setTimerForAudience(channelName, pkRequestFinalHost.getOtherChannelName(), modelPkTimeLimit, false);

    }

    private void hideShowPkBatleIcons(boolean hideStatus) {
        if (hideStatus) {
            rl_viewpager.setVisibility(View.GONE);
            rl_coins.setVisibility(View.VISIBLE);
            iv_pkRequest.setVisibility(View.GONE);
            iv_pkBattle.setVisibility(View.GONE);
        } else {
            isBattlePlayed = false;
            rl_viewpager.setVisibility(View.VISIBLE);
            rl_coins.setVisibility(View.GONE);
            iv_pkRequest.setVisibility(View.VISIBLE);
            iv_pkBattle.setVisibility(View.VISIBLE);
        }
    }

    private void dailog_liveFriends() {
        String userId = CommonUtils.getUserId();
        Log.i("userid", userId);

            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.dialog_live_users);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



            RecyclerView rv_usersForInvite = dialog.findViewById(R.id.rv_usersForInvite);

            viewModelClass.getFriendsLiveList(CommonUtils.getUserId()).observe(AgoraPkLiveActivity.this, getLiveFriendsListPojo -> {
                if (getLiveFriendsListPojo.getStatus()==1) {


                    dialog.findViewById(R.id.tv_response0).setVisibility(View.GONE);
                    rv_usersForInvite.setVisibility(View.VISIBLE);
                    listMyLiveFreinds = getLiveFriendsListPojo.getDeatils();
                    adapterUserForPkInvite = new AdapterUserForPkInvite(activity, listMyLiveFreinds, pos -> {
                        sendPkAlertDialog(listMyLiveFreinds.get(pos));
                        dialog.dismiss();
                    });
                } else {
                    dialog.findViewById(R.id.tv_response0).setVisibility(View.VISIBLE);
                    rv_usersForInvite.setVisibility(View.GONE);
                }

                rv_usersForInvite.setAdapter(adapterUserForPkInvite);

                dialog.show();
            });



            ImageView close = dialog.findViewById(R.id.close_dialog);
            close.setOnClickListener(v -> dialog.dismiss());

    }

    String name = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getName();

    @SuppressLint("SetTextI18n")
    private void sendPkAlertDialog(GrtFriendsLiveDetails.Deatil listMyLiveFreinds) {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        final View confirmDialog = layoutInflater.inflate(R.layout.sendpk_request_dailog, null);
        final AlertDialog dialogBox = new AlertDialog.Builder(activity).create();
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBox.setView(confirmDialog);
        dialogBox.setCanceledOnTouchOutside(false);

        TextView tv_text = confirmDialog.findViewById(R.id.tv_text);
        tv_text.setText("Do you want to send a PK battle request to" + " " + listMyLiveFreinds.getChannelName());

        TextView btn1, btn2, btn3;

        btn1 = confirmDialog.findViewById(R.id.tv_5min);
        btn2 = confirmDialog.findViewById(R.id.tv_10min);
        btn3 = confirmDialog.findViewById(R.id.tv_15min);

        Button yes, no;
        timerLimit = 5;
        yes = confirmDialog.findViewById(R.id.confirmBooking);
        no = confirmDialog.findViewById(R.id.btn_editBooking);

        yes.setText("Send");
        no.setText("Cancel");

        btn1.setOnClickListener(view -> {
            setButtonTimerBg(btn1, btn2, btn3);
            timerLimit = 5;
        });
        btn2.setOnClickListener(view -> {
            setButtonTimerBg(btn2, btn1, btn3);
            timerLimit = 10;
        });
        btn3.setOnClickListener(view -> {
            setButtonTimerBg(btn3, btn1, btn2);
            timerLimit = 15;
        });


        yes.setOnClickListener(v -> {
            ModelPkRequest modelPkRequest = new ModelPkRequest();
            modelPkRequest.setRequestUserID(userId);
            modelPkRequest.setRequestChannelName(channelName);
            modelPkRequest.setToken(token);
            modelPkRequest.setImage(image);
            modelPkRequest.setOtherUserId(listMyLiveFreinds.getUserId());
            modelPkRequest.setOtherToken(listMyLiveFreinds.getToken());
            modelPkRequest.setOtherChannelName(listMyLiveFreinds.getUsername());
            modelPkRequest.setRtmToken(rtmoken);
            modelPkRequest.setTimeLimit(timerLimit);
            modelPkRequest.setMyName(name);
            modelPkRequest.setRequestStatus("2");
            modelPkRequest.setRequestedCoins("0");
            modelPkRequest.setOtherCoins("0");
            modelPkRequest.setLiveId(getIntent().getExtras().getString(AppConstants.AGORA_LIVE_ID));
            modelPkRequest.setHostType(App.getSingleton().getHostType());
            modelPkRequest.setOtherName(listMyLiveFreinds.getName());
            modelPkRequest.setPkBattleId(String.valueOf(new Date().getTime()));
            FirebaseHelper.sendPkRequest(modelPkRequest, channelName, listMyLiveFreinds.getUsername());
            dialogBox.dismiss();
        });
        no.setOnClickListener(view -> dialogBox.dismiss());
        dialogBox.show();
    }

    private void setButtonTimerBg(TextView btn1, TextView btn2, TextView btn3) {
        btn1.setBackgroundResource(R.drawable.button_bg);
        btn1.setTextColor(ContextCompat.getColor(this, R.color.white));
        btn2.setBackgroundResource(R.drawable.stroke_primary);
        btn2.setTextColor(ContextCompat.getColor(this, R.color.purple));
        btn3.setBackgroundResource(R.drawable.stroke_primary);
        btn3.setTextColor(ContextCompat.getColor(this, R.color.purple));
    }

    @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
    private void dailog_pkRequest() {
        if (App.getSharedpref().isLogin(activity)) {
            dialogPkRequest = new Dialog(activity);
            dialogPkRequest.setContentView(R.layout.dialog_live_users);
            dialogPkRequest.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialogPkRequest.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            RecyclerView rv_usersForInvite = dialogPkRequest.findViewById(R.id.rv_usersForInvite);

            TextView tv_text = dialogPkRequest.findViewById(R.id.tv_text);
            tv_text.setText("Pk Battle Requests");

            ImageView close = dialogPkRequest.findViewById(R.id.close_dialog);
            close.setOnClickListener(v -> dialogPkRequest.dismiss());

            if (adapterPkRequestReceived != null) {
                adapterPkRequestReceived.notifyDataSetChanged();
            }

            rv_usersForInvite.setAdapter(adapterPkRequestReceived);

            dialogPkRequest.show();
        }
    }

    ValueEventListener pkRequestStatusEventListener = new ValueEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {

                ModelPkRequest modelPkRequest = snapshot.getValue(ModelPkRequest.class);

                if (modelPkRequest.getRequestStatus().equalsIgnoreCase("1")) {

                    pkRequestFinalHost = modelPkRequest;
                    hideShowPkBatleIcons(true);
                    layoutHideUnHide(visibleInt);

                    if (modelPkRequest.getRequestUserID().equalsIgnoreCase(userId)) {
                        upperCoins.setText(modelPkRequest.getRequestedCoins() + " Beans");
                        bottomCoins.setText(modelPkRequest.getOtherCoins() + " Beans");

                        tv_bottomHostName.setText(modelPkRequest.getOtherName());
                        tv_bottomHostId.setText(modelPkRequest.getOtherChannelName());
                        tv_upperHostName.setText(modelPkRequest.getMyName());
                        try {
                            Glide.with(activity).load(modelPkRequest.getOtherImage()).placeholder(R.drawable.ic_user1).into(iv_otherHostImage);
                        } catch (Exception ignored) {

                        }

                    } else {
                        bottomCoins.setText(modelPkRequest.getRequestedCoins() + " Beans");
                        upperCoins.setText(modelPkRequest.getOtherCoins() + " Beans");

                        tv_upperHostName.setText(modelPkRequest.getOtherName());
                        tv_bottomHostName.setText(modelPkRequest.getMyName());
                        tv_bottomHostId.setText(modelPkRequest.getOtherChannelName());
                        try {
                            Glide.with(activity).load(modelPkRequest.getRequestedImage()).placeholder(R.drawable.app_logo).into(iv_otherHostImage);
                        } catch (Exception ignored) {

                        }
                    }

                    if (!isBattlePlayed) {

                        if (countDownTimerPkBattle != null) {
                            countDownTimerPkBattle.cancel();
                        }
                        VideoGridContainer.MAX_USER = 2;
                        setTimerStart(modelPkRequest);

                        ChannelMediaInfo channelMediaInfo = new ChannelMediaInfo(channelName, token, 0);
                        ChannelMediaRelayConfiguration mediaRelayConfiguration =
                                new ChannelMediaRelayConfiguration();
                        mediaRelayConfiguration.setSrcChannelInfo(channelMediaInfo);

                        ChannelMediaInfo mediaInfo = new ChannelMediaInfo(modelPkRequest.getOtherChannelName(),
                                        modelPkRequest.getOtherToken(), 0);

                        mediaRelayConfiguration.setDestChannelInfo(modelPkRequest.getOtherChannelName(), mediaInfo);

                        pktype = "2";
                        pkId = modelPkRequest.getPkBattleId();

                        cameraFocus();

                        rtcEngine().startChannelMediaRelay(mediaRelayConfiguration);


                    }

                } else if (modelPkRequest.getRequestStatus().equalsIgnoreCase("2")) {

                    calculateGifts(pkId);

                } else if (modelPkRequest.getRequestStatus().equalsIgnoreCase("4")) {
                    rtcEngine().stopChannelMediaRelay();
                    layoutHideUnHide(goneInt);
                    hideShowPkBatleIcons(false);
                    removeTimeAudience();
                    if (countDownTimerPkBattle != null) {
                        countDownTimerPkBattle.cancel();
                        tv_tv_timeLeft.setVisibility(View.GONE);
                    }

                }
            } else {
                rtcEngine().stopChannelMediaRelay();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void removeTimeAudience() {

        ModelPkTimeLimit modelPkTimeLimit = new ModelPkTimeLimit();
        FirebaseHelper.setTimerForAudience(channelName, pkRequestFinalHost.getOtherChannelName(), modelPkTimeLimit, false);

    }
//    private void pkBattleUpdate(ModelPkRequest modelPkRequest) {
//        ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);
//        apiInterface.pkHostMembers(modelPkRequest.getPkBattleId(), modelPkRequest.getRequestUserID(),
//                modelPkRequest.getOtherUserId()).enqueue(new Callback<Map>() {
//            @Override
//            public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {
//
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {
//
//            }
//        });
//    }
    private void setLIveDescription() {

        setupadd(coinList);

        setRecycler();

        liveMvvm.liveDescriptionModelLiveData(this).observe(AgoraPkLiveActivity.this, liveDescriptionModel -> {
            if (liveDescriptionModel.getSuccess().equalsIgnoreCase("1")) {
                list = liveDescriptionModel.getDetails();
                ModelAgoraMessages modelAgoraMessages = new ModelAgoraMessages(list.get(0).getFrist(), "image", "userProfile.getUserName()", "CommonUtils.getUserId()", level, 0, "0",System.currentTimeMillis(),"");
                listMessages.add(modelAgoraMessages);
                modelAgoraMessages = new ModelAgoraMessages(list.get(0).getSec(), "image", "userProfile.getUserName()", "CommonUtils.getUserId()", level, 0, "0",System.currentTimeMillis(),"");
                listMessages.add(modelAgoraMessages);
                modelAgoraMessages = new ModelAgoraMessages("Welcome to B Live", "image", "userProfile.getUserName()", "CommonUtils.getUserId()", level, 0, "0",System.currentTimeMillis(),"");
                listMessages.add(modelAgoraMessages);
                adapterMessagesAgora.notifyItemInserted(adapterMessagesAgora.getItemCount());
                if (listMessages.size() > 0) {
                    recycler_comment.smoothScrollToPosition(listMessages.size() - 1);


                }
                getMessageFireBase();
            }
        });
    }

    private void setUserProfile() {
        userProfile = new Config.UserProfile();
        String name = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getName();
        if (name.isEmpty()) {
            userProfile.setUserName(username);
        } else {
            userProfile.setUserName(name);
        }
        userProfile.setImageUrl(image);
        userProfile.setRtmToken(rtmoken);
        userProfile.setRtcToken(token);
        userProfile.setUserId(CommonUtils.getUserId());
        userProfile.setProfileIcon(LoadImageFromWebURL(image));
        userProfile.setToken(rtmoken);
        configRTM().getUserProfile().setToken(token);
        configRTM().initGiftList(activity);
        configRTM().getUserProfile().setUserId(CommonUtils.getUserId());
        configRTM().setAppId(AppConstants.AGORA_APP_ID);
    }

    String userId = CommonUtils.getUserId();

    private void setRecycler() {
        adapterMessagesAgora = new AdapterMessagesAgora(activity, listMessages, (otherUserId, username, positon) -> {
            if (otherUserId.equalsIgnoreCase(userId)) {
                isLiveUser = true;
            }
            openOtherUserProfile(otherUserId, isLiveUser);
        }, "0");
        recycler_comment.setAdapter(adapterMessagesAgora);
        setupadd(coinList);

    }

    private boolean isLiveUser = false;

    private void openOtherUserProfile(String otherUserId, boolean isLiveUserId) {

        UserDetailsFragment userDetailsFragment = new UserDetailsFragment(otherUserId, true, false, false, null, username, liveId, this::mentionName);
        userDetailsFragment.show(getSupportFragmentManager(), userDetailsFragment.getTag());
        isLiveUser = false;

    }

    private void newDialog(Activity activity, String time) {
        chronometer.stop();
        Rect displayRectangle = new Rect();
        final AlertDialog.Builder builder = new AlertDialog.Builder(AgoraPkLiveActivity.this, R.style.CustomAlertDialog);
        builder.setCancelable(false);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_liveended, viewGroup, false);
        Window window = AgoraPkLiveActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        builder.setView(dialogView);
        dialogView.setMinimumWidth((int) (displayRectangle.width() * 0.7f));
        dialogView.setMinimumHeight((int) (displayRectangle.height() * 0.7f));

        alertDialog = builder.create();
        Button bt_ok = dialogView.findViewById(R.id.bt_ok);
        bt_ok.setOnClickListener(view -> {
            alertDialog.dismiss();
            AgoraPkLiveActivity.super.onBackPressed();
                leaveChannel();
        });

        try {
            TextView timetv = dialogView.findViewById(R.id.tv_time);
            timetv.setText(time);
            ImageView imageView = dialogView.findViewById(R.id.iv_imageliveuse);
            OtherUserDataModel otherUserDataModel = App.getSharedpref().getModel(AppConstants.OTHER_USER_MODEL, OtherUserDataModel.class);
            Picasso.with(this).load(otherUserDataModel.getDetails().getImage()).into(imageView);
        } catch (Exception ignored) {

        }

        if (AgoraPkLiveActivity.this != null) {
            if (alertDialog != null) {
                alertDialog.show();
            }
        }
    }

    ChildEventListener giftsEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.i("Agora Gift Received : ", snapshot.getKey());
            ModelSendGift modelSendGift = snapshot.getValue(ModelSendGift.class);

            boolean o = false;
            setCoins(modelSendGift);
//            showImage(modelSendGift);
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
    ChildEventListener viewersEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            Log.i("childEvenet", snapshot.getKey());
            ModelSetUserViewer modelSetUserViewer = snapshot.getValue(ModelSetUserViewer.class);
            Log.i("Agora", Objects.requireNonNull(modelSetUserViewer).getUserId());
            Log.i("Agora ", modelSetUserViewer.getImage());
            Log.i("Agora  ", modelSetUserViewer.getLevel());

            if (!modelSetUserViewer.getUserId().equalsIgnoreCase(userId)) {
                listViewers.add(0, modelSetUserViewer);
            }
            getViewCounts();
            setAdapters(listViewers);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Log.i("Agora onChildRemoved : ", snapshot.getKey());
            try {
                ModelSetUserViewer modelSetUserViewer = snapshot.getValue(ModelSetUserViewer.class);
                for (int i = 0; i < listViewers.size(); i++) {
                    assert modelSetUserViewer != null;
                    if (listViewers.get(i).getUserId().equalsIgnoreCase(modelSetUserViewer.getUserId())) {
                        listViewers.remove(i);
                        adapterUserList.notifyDataSetChanged();
                        String entryEffect= listViewers.get(listViewers.size()).getGarage();
                        Toast.makeText(activity, "entryEffect :- "+entryEffect, Toast.LENGTH_SHORT).show();
                        muCarsRVImagee.setVisibility(View.VISIBLE);
                        CommonUtils.setAnimationTwo(AgoraPkLiveActivity.this,entryEffect,muCarsRVImagee);
                    }
                }
            } catch (Exception ignored) {
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

    private void setAdapters(List<ModelSetUserViewer> listViewers) {

        adapterUserList = new AdapterUserList(listViewers, number -> openOtherUserProfile(number, true));
        recyclerViewersOne.setAdapter(adapterUserList);

    }

    @SuppressLint("SetTextI18n")
    private void getViewCounts() {
        tv_views_live.setText(listViewers.size() + "");

        if (listViewers.size() <= 0) {
            iv_profiles.setVisibility(View.GONE);
        } else {
            iv_profiles.setVisibility(View.VISIBLE);
        }

        iv_profiles.setText(listViewers.size() + "");
    }

    @SuppressLint("SetTextI18n")
    private void setCoins(ModelSendGift modelSendGift) {

        if (isBattlePlayed) {

            int a;

            if (pkRequestFinalHost.getRequestUserID().equalsIgnoreCase(userId)) {

                a = Integer.parseInt(pkRequestFinalHost.getRequestedCoins()) + Integer.parseInt(String.valueOf(modelSendGift.getGiftPrice()));
                pkRequestFinalHost.setRequestedCoins(a + "");
                pkRequestFinalHost.setOtherCoins(pkRequestFinalHost.getOtherCoins());

                FirebaseHelper.sendPkRequest(pkRequestFinalHost, channelName, pkRequestFinalHost.getOtherChannelName());
            } else {
                a = Integer.parseInt(pkRequestFinalHost.getOtherCoins()) + Integer.parseInt(String.valueOf(modelSendGift.getGiftPrice()));
                pkRequestFinalHost.setOtherCoins(a + "");
                pkRequestFinalHost.setRequestedCoins(pkRequestFinalHost.getRequestedCoins());

                FirebaseHelper.sendPkRequest(pkRequestFinalHost, pkRequestFinalHost.getRequestChannelName(), channelName);
            }

            ModelPkTimeLimit modelPkTimeLimit = new ModelPkTimeLimit();
            modelPkTimeLimit.setTs(ts);
            modelPkTimeLimit.setTimeLimitPkbattle(pkRequestFinalHost.getTimeLimit());
            modelPkTimeLimit.setPkBattleId(pkRequestFinalHost.getPkBattleId());
            modelPkTimeLimit.setRequestedCoins(pkRequestFinalHost.getRequestedCoins());
            modelPkTimeLimit.setOtherCoins(pkRequestFinalHost.getOtherCoins());

            modelPkTimeLimit.setRequestName(pkRequestFinalHost.getMyName());
            modelPkTimeLimit.setOtherName(pkRequestFinalHost.getOtherName());
            modelPkTimeLimit.setRequestChannelID(pkRequestFinalHost.getRequestChannelName());
            modelPkTimeLimit.setOtherChannelID(pkRequestFinalHost.getOtherChannelName());

            modelPkTimeLimit.setOtherId(pkRequestFinalHost.getOtherUserId());
            modelPkTimeLimit.setRequestedId(pkRequestFinalHost.getRequestUserID());
            modelPkTimeLimit.setStatus(pkRequestFinalHost.getRequestStatus());
            modelPkTimeLimit.setRequestedmainCoins(pkRequestFinalHost.getRequestMainCoins());
            modelPkTimeLimit.setOtherMainCoins(pkRequestFinalHost.getOtherMainCoins());


            if (pkRequestFinalHost.getRequestUserID().equalsIgnoreCase(userId)) {
                FirebaseHelper.setTimerForAudience(channelName, pkRequestFinalHost.getOtherChannelName(), modelPkTimeLimit, true);
            } else {
                FirebaseHelper.setTimerForAudience(channelName, pkRequestFinalHost.getRequestChannelName(), modelPkTimeLimit, true);
            }
        }

        tv_total_coins.setText(modelSendGift.getLivebox());
    }

    @SuppressLint("SetTextI18n")
    private void showImage(ModelSendGift modelSendGift) {

        String image = modelSendGift.getGiftImage();

        Log.i("gift", "showImage: gift==" + image);

//        iv_giftImage.setVisibility(View.VISIBLE);

        if (modelSendGift.getGiftPrice() <= 500000000) {
            tv_smallGiftName.setText(modelSendGift.getName());
            smallGiftGiftName.setText("sent a " + modelSendGift.getGiftName());
            Glide.with(activity).load(modelSendGift.getUserImage()).placeholder(R.drawable.app_logo).into(smallGiftImage);
            Log.i("gift", "showImage: userImage==" + modelSendGift.getUserImage());
            iv_smallGif.setVisibility(View.VISIBLE);
            Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
            rl_smallGift.startAnimation(animation2);
            final Handler threadHandler = new Handler();
            new Thread() {
                @Override
                public void run() {
                    threadHandler.postDelayed(() -> {
                        rl_smallGift.clearAnimation();
                        //rl_smallGift.setVisibility(View.GONE);
                    }, animation2.getDuration() + 2000);
                }
            }.start();
            startAnimantion(modelSendGift.getThumbnail());
            Glide.with(activity).asGif().load(modelSendGift.getThumbnail()).into(iv_smallGif);
            Log.i("gift", "showImage: thumbnail==" + modelSendGift.getThumbnail());

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
                                    threadHandler.postDelayed(() -> iv_giftImage.setVisibility(View.GONE), 1000);
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
                                    }, 5000);
                                }
                            }.start();
                            Log.i("Agora Gift Image : ", "ready to load");
                            return false;
                        }
                    }).into(iv_giftImage);
            }
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
                            }, delay + 1500);
                        }
                    }.start();
//                    new Handler(activity.getMainLooper()).postDelayed(, delay);
                    return false;
                }

            }).into(iv_giftImage);
        }
    }
    private void startAnimantion(String thumbnail) {

//
        img_small_video.setVisibility(View.VISIBLE);
        Glide.with(instance).asGif().load(thumbnail).listener(new RequestListener<GifDrawable>() {
            @Override
            public boolean onLoadFailed(GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {

                final Handler threadHandler = new Handler();
                new Thread() {
                    @Override
                    public void run() {
                        threadHandler.postDelayed(() -> {
//                            Toast.makeText(activity, "hbjh", Toast.LENGTH_SHORT).show();
                            img_small_video.setVisibility(View.GONE);
                        }, 1000);
                    }
                }.start();

                return false;
            }

            @Override
            public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {

                final Handler threadHandler = new Handler();
                new Thread() {
                    @Override
                    public void run() {
                        threadHandler.postDelayed(() -> {
//                            Toast.makeText(activity, "hbjbhh", Toast.LENGTH_SHORT).show();

                            img_small_video.setVisibility(View.GONE);
                        }, 5000);
                    }
                }.start();

                return false;
            }
        }).into(iv_smallGif);
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


    private static class GiftGifDrawable extends GifDrawable {
        GifDecoder gifDecoder;

        GiftGifDrawable(Context context, GifDecoder gifDecoder, Transformation<Bitmap> frameTransformation,
                        int targetFrameWidth, int targetFrameHeight, Bitmap firstFrame) {
            super(context, gifDecoder, frameTransformation, targetFrameWidth, targetFrameHeight, firstFrame);
            this.gifDecoder = gifDecoder;
        }
    }



    private boolean isHeadsetOn(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (am == null)
            return false;

        AudioDeviceInfo[] devices = am.getDevices(AudioManager.GET_DEVICES_OUTPUTS);

        for (AudioDeviceInfo device : devices) {
            if (device.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET
                    || device.getType() == AudioDeviceInfo.TYPE_WIRED_HEADPHONES
                    || device.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP
                    || device.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_SCO) {
                return true;
            }
        }
        return false;
    }

    private void initServicesAsync() {
        new Handler().postDelayed(() -> FirebaseHelper.getfakeCounts(channelName, valueEventListener), 3000);

        // loud speaker
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                    audioManager.setMode(AudioManager.MODE_NORMAL);
                    if (!isHeadsetOn(activity)) {
                        if (!audioManager.isSpeakerphoneOn())
                            audioManager.setSpeakerphoneOn(true);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        thread.start();

        new Thread(() -> {
            startFirebaseHelper();
            updateChildListenersInClass();
            startExitService();
        }).start();
    }

    private void startExitService() {

        Intent intent = new Intent(activity, LiveExitService.class);
        intent.putExtra("liveUsername", channelName);
        intent.putExtra("liveId", getIntent().getExtras().getString(AppConstants.AGORA_LIVE_ID));
        startService(intent);
    }

    private void updateChildListenersInClass() {
        ChildEventListenersObject.setViewerChildListsner(viewersEventListener);
        ChildEventListenersObject.setGiftChildListsner(giftsEventListener);
//        ChildEventListenersObject.setBulletChildListsner(bulletEventListener);
        ChildEventListenersObject.setRtcEngine(rtcEngine());
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

    private void startFirebaseHelper() {
        FirebaseHelper.userName = username;

        Log.i("startFirebaseHelper: ", username);
        FirebaseHelper.setliveUser();
        FirebaseHelper.getViewersLive(username, viewersEventListener);
        FirebaseHelper.giftsListener(username, giftsEventListener);
        FirebaseHelper.getBulletMessage(channelName, valueBulletEvent);
        FirebaseHelper.getpublicBulletMessage(channelName, bulletEventListener);
        FirebaseHelper.getentryUser(channelName, getEntry);
        FirebaseHelper.setLiveStatus(true);


        FirebaseHelper.getPkRequest(channelName, pkRequestChildEventListener);
        FirebaseHelper.getMultiRequest(channelName, multiRequestChildEventListener);

        FirebaseHelper.adminStatus("1");

        FirebaseHelper.adminHost(App.getSingleton().getMaxJoiners());

        if (App.getSingleton().getMaxJoiners().equalsIgnoreCase(String.valueOf(maxHostCounts))) {
            FirebaseHelper.canRequest("0");
        } else {
            FirebaseHelper.canRequest("1");
        }

        FirebaseHelper.getAdminLiveStatus(username, adminStatusListser);
        FirebaseHelper.acceptRejectStatusPkRequest(channelName, pkRequestStatusEventListener);

        databaseReferenceParent.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.exists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
                        stopAllAsync();
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


    ChildEventListener pkRequestChildEventListener = new ChildEventListener() {
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            if (snapshot.exists()) {
                ModelPkRequest modelPkRequest = snapshot.getValue(ModelPkRequest.class);
                listPkRequest.add(modelPkRequest);
                adapterPkRequestReceived.notifyDataSetChanged();
                if (!isBattlePlayed) {
                    newPkRequestAlert(modelPkRequest);
                }
            }
        }

        @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            ModelPkRequest modelPkRequest = snapshot.getValue(ModelPkRequest.class);

            if (modelPkRequest.getRequestStatus().equalsIgnoreCase("1")) {
                pkRequestFinalHost = modelPkRequest;
                hideShowPkBatleIcons(true);
                layoutHideUnHide(visibleInt);
//                Toast.makeText(activity, modelPkRequest.getRequestStatus(), Toast.LENGTH_SHORT).show();
                if (modelPkRequest.getRequestUserID().equalsIgnoreCase(userId)) {
                    upperCoins.setText(modelPkRequest.getRequestedCoins() + " Beans");
                    bottomCoins.setText(modelPkRequest.getOtherCoins() + " Beans");

                    tv_bottomHostName.setText(modelPkRequest.getOtherName());
                    tv_bottomHostId.setText(modelPkRequest.getOtherChannelName());
                    tv_upperHostName.setText(modelPkRequest.getMyName());


                    try {
                        Glide.with(activity).load(modelPkRequest.getOtherImage()).placeholder(R.drawable.ic_user1).into(iv_otherHostImage);
                        Glide.with(activity).load(modelPkRequest.getOtherImage()).placeholder(R.drawable.ic_user1).into(otherUser);
                    } catch (Exception ignored) {
                    }

                } else {
                    bottomCoins.setText(modelPkRequest.getRequestedCoins() + " Beans");
                    upperCoins.setText(modelPkRequest.getOtherCoins() + " Beans");

                    tv_upperHostName.setText(modelPkRequest.getOtherName());
                    tv_bottomHostName.setText(modelPkRequest.getMyName());
                    tv_bottomHostId.setText(modelPkRequest.getOtherChannelName());

                    try {
                        Glide.with(activity).load(modelPkRequest.getRequestedImage()).placeholder(R.drawable.ic_user1).into(iv_otherHostImage);
                        Glide.with(activity).load(modelPkRequest.getRequestedImage()).placeholder(R.drawable.ic_user1).into(otherUser);
                    } catch (Exception ignored) {

                    }
                }
            }
//            hideShowPkBatleIcons(false);
            if (Objects.requireNonNull(modelPkRequest).getRequestStatus().equalsIgnoreCase("4")) {
                hideShowPkBatleIcons(false);
                rtcEngine().stopChannelMediaRelay();
                if (countDownTimerPkBattle != null) {
                    countDownTimerPkBattle.cancel();
                    tv_tv_timeLeft.setVisibility(View.GONE);
                }
                removeTimeAudience();
            }

            if (modelPkRequest.getRequestStatus().equalsIgnoreCase("4")
                    || modelPkRequest.getRequestStatus().equalsIgnoreCase("2")
                    || modelPkRequest.getRequestStatus().equalsIgnoreCase("3")) {
                hideShowPkBatleIcons(false);
                for (int i = 0; i < listPkRequest.size(); i++) {
                    if (listPkRequest.get(i).getPkBattleId().equalsIgnoreCase(modelPkRequest.getPkBattleId())) {
                        listPkRequest.remove(i);
                        adapterPkRequestReceived.notifyItemRemoved(i);
                    }
                }
                layoutHideUnHide(goneInt);
            }

            if (modelPkRequest.getRequestStatus().equalsIgnoreCase("")) {
                listPkRequest.add(modelPkRequest);
                adapterPkRequestReceived.notifyDataSetChanged();
                newPkRequestAlert(modelPkRequest);
                layoutHideUnHide(goneInt);
            }


        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                ModelPkRequest modelPkRequest = snapshot.getValue(ModelPkRequest.class);

                for (int i = 0; i < listPkRequest.size(); i++) {
                    if (listPkRequest.get(i).getLiveId().equalsIgnoreCase(modelPkRequest.getLiveId())) {
                        listPkRequest.remove(i);
                        adapterPkRequestReceived.notifyItemRemoved(i);
                        String entryEffect= listViewers.get(listViewers.size()).getGarage();
                        Toast.makeText(activity, "entryEffect :- "+entryEffect, Toast.LENGTH_SHORT).show();
                        muCarsRVImagee.setVisibility(View.VISIBLE);
                        CommonUtils.setAnimationTwo(AgoraPkLiveActivity.this,entryEffect,muCarsRVImagee);
                    }
                }

            }

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    ChildEventListener multiRequestChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            if (snapshot.exists()) {
                MultiLiveModelCount multiLiveModelCount = snapshot.getValue(MultiLiveModelCount.class);

                if (multiLiveModelCount.getRequestStatus().equalsIgnoreCase("")) {
                    alertDailog(multiLiveModelCount);
                }
            }

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            MultiLiveModelCount multiLiveModelCount = snapshot.getValue(MultiLiveModelCount.class);
            if (multiLiveModelCount.getRequestStatus().equalsIgnoreCase("")) {
                alertDailog(multiLiveModelCount);
            }


            if (multiLiveModelCount.getRequestStatus().equalsIgnoreCase("1")) {
                maxHostCounts += 1;
            } else if (multiLiveModelCount.getRequestStatus().equalsIgnoreCase("5")) {
                maxHostCounts -= 1;
            }

            setCanRequest();
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

    private void setCanRequest() {

        if (App.getSingleton().getMaxJoiners().equalsIgnoreCase(String.valueOf(maxHostCounts))) {
            FirebaseHelper.canRequest("0");
        } else {
            FirebaseHelper.canRequest("1");
        }
    }

    ValueEventListener getEntry = new ValueEventListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                ModelUserEntry modelUserEntry = snapshot.getValue(ModelUserEntry.class);
                String levelz = modelUserEntry.getLevel();
                int level = Integer.parseInt(levelz);
                if (level > 0) {
                    if (level <= 30) {
                        try {
                            Glide.with(AgoraPkLiveActivity.this).load(modelUserEntry.getImage()).into(smallEntryImage);
                            Glide.with(AgoraPkLiveActivity.this).load(modelUserEntry.getGiftimage()).into(iv_smallEntryGif);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tv_smallEntryName.setText(modelUserEntry.getName());
                        smallEntryGiftName.setText("arrived with a " + modelUserEntry.getGiftname());
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
                        try {
                            Glide.with(AgoraPkLiveActivity.this).load(modelUserEntry.getImage()).into(smallEntryImage);
                            Glide.with(AgoraPkLiveActivity.this).load(modelUserEntry.getGiftimage()).into(iv_smallEntryGif);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tv_smallEntryName.setText(modelUserEntry.getName());
                        smallEntryGiftName.setText("arrived with a " + modelUserEntry.getGiftname());
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
                            openDialog(modelUserEntry.getGiftimage());
                            GiftAnimWindow window = new GiftAnimWindow(AgoraPkLiveActivity.this, R.style.gift_anim_window);
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.gravity = Gravity.CENTER;
                            window.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 800);
                            window.setAnimResource(modelUserEntry.getGiftimage());
                            window.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
                FirebaseHelper.removeentryUser(channelName);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void openDialog(String giftimage) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.multiple_spinner_recycler_layout);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);
        Window window = dialog.getWindow();
        ImageView imageView = dialog.findViewById(R.id.imageGif);

//        Toast.makeText(activity, "" + giftimage, Toast.LENGTH_SHORT).show();
        dialog.show();
        boolean isImage = (!giftimage.contains(".gif"));
        if (isImage) {
            delay = 3;

            Glide.with(this).load(giftimage).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    Log.i("Agora Gift Image : ", e.getMessage());
                    final Handler threadHandler = new Handler();
                    new Thread() {
                        @Override
                        public void run() {
                            threadHandler.postDelayed(new Runnable() {
                                public void run() {
                                    Log.i("Agora Gif", e.getMessage());
                                    dialog.dismiss();
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

                                    dialog.dismiss();
                                }
                            }, 5000);
                        }
                    }.start();
                    Log.i("Agora Gift Image : ", "ready to load");
                    return false;
                }
            }).into(imageView);


//            Glide.with(this).asGif().load(giftimage).listener(new RequestListener<GifDrawable>() {
//                @Override
//                public boolean onLoadFailed(@Nullable GlideException e, Object model,
//                                            Target<GifDrawable> target, boolean isFirstResource) {
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(GifDrawable resource, Object model,
//                                               Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
//                    GiftAnimWindow.GiftGifDrawable giftDrawable = getSelfStoppedGifDrawable(resource);
//                    for (int i = 0; i < giftDrawable.gifDecoder.getFrameCount(); i++) {
//                        delay += giftDrawable.gifDecoder.getDelay(i);
//                    }
//                    new Handler(getMainLooper()).postDelayed(GiftAnimWindow.this::dismiss, delay);
//                    return false;
//                }
//
//            }).into(imageView);
        }


    }


    private void audioInSpeaker() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(true);
    }

    private void sendMultiRequest(MultiLiveModelCount multiLiveModelCount) {
        FirebaseHelper.sendMultiRequest(channelName, multiLiveModelCount.getUserId(), multiLiveModelCount);
        rtcEngine().setEnableSpeakerphone(true);
//        rtcEngine().enableAudio();
//        audioInSpeaker();
//        rtcEngine().setDefaultAudioRoutetoSpeakerphone(true);
    }

    @SuppressLint("SetTextI18n")
    private void initUI() {
        iv_giftImage = findViewById(R.id.iv_giftImage);
        rl_viewpager = findViewById(R.id.rl_viewpager);
        muCarsRVImagee = findViewById(R.id.muCarsRVImagee);
        indicator = findViewById(R.id.indicator);
        ll_user_channel_image = findViewById(R.id.ll_user_channel_image);
        ll_Looser = findViewById(R.id.ll_Looser);
        ll_user_channel_image.setOnClickListener(this);
        tv_closeBattle = findViewById(R.id.tv_closeBattle);
        tv_closeBattle.setOnClickListener(this);
        viewMultiLive = findViewById(R.id.viewMutlilive);
        rlPublicMessage = findViewById(R.id.rl_publicmessage);
        iv_smallEntryGif = findViewById(R.id.iv_smallEntryGif);
        rl_smallEntry = findViewById(R.id.rl_smallEntry);
        smallEntryImage = findViewById(R.id.smallEntryImage);
        smallEntryImage = findViewById(R.id.smallEntryImage);
        tv_smallEntryName = findViewById(R.id.tv_smallEntryName);
        smallEntryGiftName = findViewById(R.id.smallEntryGiftName);
        RelativeLayout lay_user_details = findViewById(R.id.lay_user_details);
        lay_user_details.setOnClickListener(this);
        ll_giftBox = findViewById(R.id.ll_giftBox);
        ll_giftBox.setOnClickListener(this);
        tvGiftBoxCount = findViewById(R.id.tv_giftboxcount);
        tvGiftBoxCount.setOnClickListener(this);
        animationView = findViewById(R.id.animationView);
        ll_star_gained = findViewById(R.id.ll_star_gained);
        ll_WinnerLooser = findViewById(R.id.ll_WinnerLooser);
        imgWinner = findViewById(R.id.imgWinner);
        imgLooser = findViewById(R.id.imgLooser);
        ll_star_gained.setOnClickListener(this);
        ImageView ivBox = findViewById(R.id.iv_box);
        ivBox.setOnClickListener(this);
        tv_mainBulletMessage = findViewById(R.id.tv_mainBulletMessage);
        tv_publicMessageText = findViewById(R.id.tv_publicMessageText);
        tv_publicMessage = findViewById(R.id.tv_publicMessage);
        tv_publicMessage.setOnClickListener(this);
        tv_bulletMessage = findViewById(R.id.tv_bulletMessage);
        tv_bulletMessage.setOnClickListener(this);
        iv_smallGif = findViewById(R.id.iv_smallGif);
        tv_smallGiftName = findViewById(R.id.tv_smallGiftName);
        smallGiftGiftName = findViewById(R.id.smallGiftGiftName);
        rl_smallGift = findViewById(R.id.rl_smallGift);
        smallGiftImage = findViewById(R.id.smallGiftImage);
        tvBigGiftGiftNameTo = findViewById(R.id.tv_biggiftGiftName);
        tvBigGiftNameTo = findViewById(R.id.tv_biggiftNAme);
        cv_bigGiftImage = findViewById(R.id.cv_bigGiftImage);
        ll_bigGiftMessage = findViewById(R.id.ll_bigGiftMessage);
//        rlCommentBackground = findViewById(R.id.rl_commentBakcground);
        iv_otherHostImage = findViewById(R.id.iv_otherHostImage);
        otherUser = findViewById(R.id.otherUser);
        userProfileImage = findViewById(R.id.userProfile);
        tv_bottomHostId = findViewById(R.id.tv_bottomHostId);
        tv_bottomHostName = findViewById(R.id.tv_bottomHostName);
        tv_upperHostName = findViewById(R.id.tv_upperHostName);
        rl_coins = findViewById(R.id.rl_coins);
        upperCoins = findViewById(R.id.tv_upperHostCoins);
        bottomCoins = findViewById(R.id.tv_bottomHostCoins);
        iv_pkRequest = findViewById(R.id.iv_pkRequest);
        iv_pkRequest.setOnClickListener(this);
        tv_tv_timeLeft = findViewById(R.id.tv_tv_timeLeft);

        llLiveUsers = findViewById(R.id.llLiveUsers);
        llLiveUsers.setOnClickListener(this);
        gifImageView = findViewById(R.id.gifimageview);
        live_btn_camera_rotate = findViewById(R.id.live_btn_camera_rotate);
        live_btn_torch = findViewById(R.id.live_btn_torch);
        iv_pkBattle = findViewById(R.id.iv_pkBattle);
        imageGame = findViewById(R.id.imageGame);
        iv_pkBattle.setOnClickListener(this);
        imageGame.setOnClickListener(this);

        findViewById(R.id.img_gifts).setOnClickListener(this);

        linearLayout = findViewById(R.id.ll_righticons);
        ImageView ivLiveShare = findViewById(R.id.iv_liveshare);
        ivLiveShare.setOnClickListener(this);
        img_small_video = findViewById(R.id.img_small_video);
        chronometer = findViewById(R.id.simpleChronometer);
        chronometer.start();
        comment_icon = findViewById(R.id.comment_icon);
        comment_icon.setOnClickListener(this);
        relative_msg = findViewById(R.id.relativeMsg);
        ImageView imgPeerMessage = findViewById(R.id.img_peer_message);
        tv_music = findViewById(R.id.tv_music);
        ImageView img_send_comment = findViewById(R.id.img_send_comment);
        tv_stars = findViewById(R.id.tv_stars);
        tv_level = findViewById(R.id.tv_level);
        TextView tv_followers = findViewById(R.id.tv_followers);
        tv_total_coins = findViewById(R.id.tv_total_coins);
        ll_comments = findViewById(R.id.ll_comments);
        tv_views_live = findViewById(R.id.tv_views_live);
        iv_profiles = findViewById(R.id.iv_profiles);
        iv_profiles.setOnClickListener(this);
        lltextMessage = findViewById(R.id.lltextMessage);
        recyclerTopGift = findViewById(R.id.recycler_top_gifters);
        recycler_comment = findViewById(R.id.recycler_comment);
        edit_text_comment = findViewById(R.id.edit_text_comment);

        imgPeerMessage.setOnClickListener(this);

        TextView roomName = findViewById(R.id.live_room_name);
        String name = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getName();

        tv_music.setSelected(true);


        if (starCountDefault != null) {
            startCount = Integer.parseInt(starCountDefault);
        } else {
            startCount = 0;
        }

        if (startCount < 10000) {
            tv_stars.setText("0");
        } else if (startCount < 50000) {
            tv_stars.setText("1");
        } else if (startCount < 200000) {
            tv_stars.setText("2");
        } else if (startCount < 1000000) {
            tv_stars.setText("Rising");
        } else if (startCount < 2000000) {
            tv_stars.setText("Big");
        } else {
            tv_stars.setText("Super");
        }

        if (!name.isEmpty()) {
            roomName.setText(name);
        } else {
            roomName.setText(configEngine().getChannelName());
        }
        roomName.setSelected(true);

        int role = getIntent().getIntExtra(
                "role",
                io.agora.rtc.Constants.CLIENT_ROLE_AUDIENCE);
        boolean isBroadcaster = (role == io.agora.rtc.Constants.CLIENT_ROLE_BROADCASTER);

        mMuteVideoBtn = findViewById(R.id.live_btn_mute_video);
        mMuteVideoBtn.setActivated(isBroadcaster);

        mMuteAudioBtn = findViewById(R.id.live_btn_mute_audio);
        mMuteAudioBtn.setActivated(isBroadcaster);

        findViewById(R.id.lay_live_board_name).setOnClickListener(this);
        ImageView beautyBtn = findViewById(R.id.live_btn_beautification);
        beautyBtn.setActivated(true);

//        BeautyOptions DEFAULT_BEAUTY_OPTIONS = new BeautyOptions(
//                LIGHTENING_CONTRAST_NORMAL,
//                0.5F, 0.5F, 0.5F);

        rtcEngine().setBeautyEffectOptions(beautyBtn.isActivated(), Constants.DEFAULT_BEAUTY_OPTIONS);

        mVideoGridContainer = findViewById(R.id.live_video_grid_layout);
        mVideoGridContainer.setStatsManager(statsManager());

        mVideoGridContainer.onVideoClicked((position, uid) -> {
            for (int i = 0; i < listUserMultiLiveDetailsModel.size(); i++) {
                if (uid != 0) {
                    if (uid == Integer.parseInt(listUserMultiLiveDetailsModel.get(i).getUid())) {

                    }
                }
            }
        });
        img_send_comment.setOnClickListener(this);

        rtcEngine().setClientRole(role);

        if (isBroadcaster) startBroadcast();

        String coins = "0";
        if (coins.equalsIgnoreCase("")) {
            coins = "0";
        }
        tv_total_coins.setText(CommonUtils.prettyCount(Long.parseLong(coins)));
        tv_followers.setText(getIntent().getExtras().getString(AgoraLiveMainActivity.FOLLOWERS));

        findViewById(R.id.img_leave).setOnClickListener(this);
        edit_text_comment.setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                try {
                    sendChannelMessage(edit_text_comment.getText().toString());
                    handled = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    handled = false;
                }

            }
            return handled;
        });


        tv_music.setOnClickListener(this);


    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupadd(List<ModelSendGift> list) {

        AdapterTopGifters adapterTopGifters = new AdapterTopGifters(list, userId -> openOtherUserProfile(userId, false));
        recyclerTopGift.setAdapter(adapterTopGifters);
        adapterTopGifters.notifyDataSetChanged();
    }

    private void initUserIcon() {
        CircleImageView iconView = findViewById(R.id.live_name_board_icon);
        iconView.setOnClickListener(view -> {
            openOtherUserProfile(CommonUtils.getUserId(), true);
        });
        Glide.with(activity).load(image).placeholder(R.drawable.app_logo).into(iconView);
        Glide.with(activity).load(image).placeholder(R.drawable.app_logo).into(userProfileImage);
    }

    private void initData() {
        mVideoDimension = Constants.VIDEO_DIMENSIONS[configEngine().getVideoDimenIndex()];
    }

    private void getMessageFireBase() {
        FirebaseHelper.getMessage(channelName, messageEventLisner);
        //FirebaseHelper.getLiveGuestsList(channelName, multiLiveData);
    }

    private void cameraFocus() {
        // Check whether exposure function is supported and enable exposure.
        boolean shouldSetExposure = rtcEngine().isCameraExposurePositionSupported();
        if (shouldSetExposure) {
            Log.i("Agora:shouldSetExposure", String.valueOf(shouldSetExposure));
            // Set the camera exposure at (50, 100).
            float positionX = 50.0f;
            float positionY = 100.0f;
            rtcEngine().setCameraExposurePosition(positionX, positionY);
        }

        // Check whether auto face-focus function is supported and enable auto-face focus.
        boolean shouldSetFaceMode = rtcEngine().isCameraAutoFocusFaceModeSupported();
        rtcEngine().setCameraAutoFocusFaceModeEnabled(shouldSetFaceMode);

        // Check whether manual focus function is supported and set the focus.
        boolean shouldManualFocus = rtcEngine().isCameraFocusSupported();
        if (shouldManualFocus) {
            Log.i("Agora:shouldManualFocus", String.valueOf(shouldManualFocus));
            // Set the camera focus at (50, 100).
            float positionX = 50.0f;
            float positionY = 100.0f;
            rtcEngine().setCameraFocusPositionInPreview(positionX, positionY);
        }
    }

    @Override
    protected void onGlobalLayoutCompleted() {
    }

    private void startBroadcast() {

        rtcEngine().setClientRole(io.agora.rtc.Constants.CLIENT_ROLE_BROADCASTER);
        SurfaceView surface = prepareRtcVideo(0, true);
//        surface.setBackground(ContextCompat.getDrawable(this,R.drawable.tranparent_round));
        isNotHost = false;

        mVideoGridContainer.addUserVideoSurface(AgoraPkLiveActivity.this, 0, surface, true, false, image, 0);
        mMuteAudioBtn.setActivated(true);
        cameraFocus();
    }

    private void stopBroadcast() {
        rtcEngine().setClientRole(io.agora.rtc.Constants.CLIENT_ROLE_AUDIENCE);
        removeRtcVideo(0, true);
        mVideoGridContainer.removeUserVideo(0, true);
        mMuteAudioBtn.setActivated(false);
    }

    @Override
    public void onJoinChannelSuccess(String channel, int uid, int elapsed) {
        // Do nothing at the moment

        Log.i("Agora:", channel + "  UID " + uid);
        multiLiveHostsModel.setUid(String.valueOf(uid));
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {
        // Do nothing at the moment
    }


    @Override
    public void onUserOffline(final int uid, int reason) {
        runOnUiThread(() -> {
            Log.i("Agora:onUserOffline", String.valueOf(reason));
            removeRemoteUser(uid);
        });
    }

    @Override
    public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
        runOnUiThread(() -> {
            Log.i("Agora:", String.valueOf(elapsed));
            isNotHost = false;
            try {
                UserMultiLiveDetailsModel userMultiLiveDetailsModel = listUserMultiLiveDetailsModel.get(listUserMultiLiveDetailsModel.size() - 1);
                userMultiLiveDetailsModel.setUid(String.valueOf(uid));
                listUserMultiLiveDetailsModel.add(userMultiLiveDetailsModel);
                updateFirebaseLiveList(listUserMultiLiveDetailsModel);
            } catch (Exception exception) {
                Log.d(TAG, "onFirstRemoteVideoDecoded: " + exception.getMessage());

            }
            renderRemoteUser(uid);

        });
    }

    private void renderRemoteUser(int uid) {
        SurfaceView surface;
        surface = prepareRtcVideo(uid, false);
        Log.d("TAGrenderRemoteUser", "renderRemoteUser:");
        if (VideoGridContainer.MAX_USER == 2) {
            mVideoGridContainer.addUserVideoSurface(this, uid, surface, true, false, otherUserImage, uid);
        } else if (VideoGridContainer.MAX_USER == 4) {
            mVideoGridContainer.addUserVideoSurface(this, uid, surface, true, false, otherUserImage, uid);
        }
    }


    private void removeRemoteUser(int uid) {
        removeRtcVideo(uid, false);
        mVideoGridContainer.removeUserVideo(uid, false);


        for (int i = 0; i < listUserMultiLiveDetailsModel.size(); i++) {
            if (listUserMultiLiveDetailsModel.get(i).getUid().equals(String.valueOf(uid))) {
                listUserMultiLiveDetailsModel.remove(i);
            }
        }
        updateFirebaseLiveList(listUserMultiLiveDetailsModel);
    }

    private void updateFirebaseLiveList(ArrayList<UserMultiLiveDetailsModel> listUserMultiLiveDetailsModel) {
        ArrayList<UserMultiLiveDetailsModel> listMulti = new ArrayList<>();

        for (int i = 0; i < listUserMultiLiveDetailsModel.size(); i++) {
            if (listMulti.isEmpty()) {
                listMulti.add(listUserMultiLiveDetailsModel.get(i));
            } else {
                for (int j = 0; j < listMulti.size(); j++) {
                    if (listMulti.get(j) != listUserMultiLiveDetailsModel.get(i)) {
                        listMulti.add(listUserMultiLiveDetailsModel.get(i));
                    }
                }
            }
        }
        multiLiveHostsModel.setUserMultiLiveDetailsModels(listMulti);
        FirebaseHelper.updateLiveGuestsList(channelName, multiLiveHostsModel);
    }

    @Override
    public void onLocalVideoStats(IRtcEngineEventHandler.LocalVideoStats stats) {
        if (!statsManager().isEnabled()) return;

        LocalStatsData data = (LocalStatsData) statsManager().getStatsData(Integer.parseInt(CommonUtils.getUserId()));
        if (data == null) return;

        data.setWidth(mVideoDimension.width);
        data.setHeight(mVideoDimension.height);
        data.setFramerate(stats.sentFrameRate);

        Log.i("Agora:onLocalVideoStats", String.valueOf(mVideoDimension.width));
        Log.i("Agora:onLocalVideoStats", String.valueOf(mVideoDimension.height));
        Log.i("Agora:onLocalVideoStats", String.valueOf(stats.sentFrameRate));
    }

    @Override
    public void onRtcJoinChannelSuccess(String channel, int uid, int elapsed) {

    }

    @Override
    public void onRtcRemoteVideoStateChanged(int uid, int state, int reason, int elapsed) {

    }

    @Override
    public void onRtcStats(IRtcEngineEventHandler.RtcStats stats) {
        if (!statsManager().isEnabled()) return;

        LocalStatsData data = (LocalStatsData) statsManager().getStatsData(Integer.parseInt(CommonUtils.getUserId()));
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
    public void onRtcChannelMediaRelayStateChanged(int state, int code) {

    }

    @Override
    public void onRtcChannelMediaRelayEvent(int code) {

    }

    @Override
    public void onRtcAudioVolumeIndication(IRtcEngineEventHandler.AudioVolumeInfo[] speakers, int totalVolume) {

    }

    @Override
    public void onRtcAudioRouteChanged(int routing) {

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

        Log.i("Agora:", String.valueOf(stats.networkTransportDelay));
        Log.i("Agora:", String.valueOf(stats.jitterBufferDelay));
        Log.i("Agora:", String.valueOf(stats.audioLossRate));
    }

    @Override
    public void activeSpeaker(int uId) {

//        Toast.makeText(activity, "dvd"+uId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        super.finish();
        statsManager().clearAllData();
    }

    public void onLeaveClicked(View view) {
        finish();
    }

    public void onSwitchCameraClicked(View view) {
        rtcEngine().switchCamera();
    }

    BeautyOptions myBeautyOptions = Constants.DEFAULT_BEAUTY_OPTIONS;


    public void onBeautyClicked(View view) {
        AgoraBeautifyFragment agoraBeautifyFragment = new AgoraBeautifyFragment(myBeautyOptions, (beautyOptions, isEnabled) -> {

            rtcEngine().setBeautyEffectOptions(isEnabled, beautyOptions);
            myBeautyOptions = beautyOptions;
        });
        agoraBeautifyFragment.show(getSupportFragmentManager(), agoraBeautifyFragment.getTag());
        Log.i("Agora:onBeautyClicked", String.valueOf(!view.isActivated()));
    }

    public void onMoreClicked(View view) {
        // Do nothing at the moment
    }

    public void onPushStreamClicked(View view) {
        // Do nothing at the moment
    }

    public void onMuteAudioClicked(View view) {
        /*if (mMuteVideoBtn.isActivated()){

        }*/
        if (!mMuteVideoBtn.isActivated()) return;
        rtcEngine().muteLocalAudioStream(view.isActivated());
        view.setActivated(!view.isActivated());
    }

    public void onMuteVideoClicked(View view) {
        if (view.isActivated()) {
            rtcEngine().enableLocalVideo(false);
        } else {
            rtcEngine().enableLocalVideo(true);
        }
        view.setActivated(!view.isActivated());
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_peer_message:

                break;
            case R.id.ll_starts:
//                openBottomTwo();


                break;
            case R.id.iv_profiles:
                openBottom();

                break;
            case R.id.recycler_top_gifters:
                OpenbottomTwo();

                break;
            case R.id.llLiveUsers:
                openBottom();
                break;

            case R.id.tv_music:
                musicBottomSheet();
                break;

            case R.id.img_leave:
                stopLiveDialog();
                break;
            case R.id.imageGame:
                Toast.makeText(activity, "Coming Soon", Toast.LENGTH_SHORT).show();
//                OpenBottomUserData();
                break;

            case R.id.lay_live_board_name:
                openOtherUserProfile(userId, true);
                break;
//            case R.id.img_send_comment:
//                try {
//                    sendChannelMessage(edit_text_comment.getText().toString());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
            case R.id.img_send_comment:
                tv_publicMessage.setBackgroundResource(R.drawable.grey_stroke);
                tv_bulletMessage.setBackgroundResource(R.drawable.grey_stroke);
                if (messageType.equalsIgnoreCase("bullet") || messageType.equalsIgnoreCase("public")) {
                    publicBulletMessage(edit_text_comment.getText().toString());
                    edit_text_comment.setText("");
                    edit_text_comment.setHint("Say Something..");
                    linearLayout.setVisibility(View.VISIBLE);
                    relative_msg.setVisibility(View.GONE);
                    comment_icon.setVisibility(View.VISIBLE);
                    CommonUtils.hideKeyboard(activity);
                } else {
                    try {
                        sendChannelMessage(edit_text_comment.getText().toString());
                        relative_msg.setVisibility(View.GONE);
                        comment_icon.setVisibility(View.VISIBLE);
                        linearLayout.setVisibility(View.VISIBLE);
                        CommonUtils.hideKeyboard(activity);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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

            case R.id.comment_icon:
                comment_icon.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                relative_msg.setVisibility(View.VISIBLE);
                CommonUtils.openKeyboard(activity);
                break;

            case R.id.iv_liveshare:
                Toast.makeText(activity, "Coming Soon", Toast.LENGTH_SHORT).show();
//                CommonUtils.shareLiveStrem(activity, getLiveId);
                break;
            case R.id.img_gifts:
                App.getSingleton().setGiftCheck("1");
                openGiftsDialog();
                break;
            case R.id.iv_pkRequest:

                dailog_pkRequest();

                break;

            case R.id.iv_pkBattle:
                dialog();
//                dailog_liveFriends();
                break;

            case R.id.ll_star_gained:

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

            case R.id.lay_user_details:
                openOtherUserProfile(CommonUtils.getUserId(), true);
                break;

            case R.id.tv_closeBattle:
                if (isBattlePlayed) {
                    ModelPkTimeLimit modelPkTimeLimit = new ModelPkTimeLimit();
                    FirebaseHelper.setTimerForAudience(channelName, pkRequestFinalHost.getOtherChannelName(), modelPkTimeLimit, false);

                    rtcEngine().stopChannelMediaRelay();

                    pkRequestFinalHost.setRequestStatus("4");

                    if (pkRequestFinalHost.getRequestUserID().equalsIgnoreCase(userId)) {
                        FirebaseHelper.sendPkRequest(pkRequestFinalHost, channelName, pkRequestFinalHost.getOtherChannelName());
                    } else {
                        FirebaseHelper.sendPkRequest(pkRequestFinalHost, pkRequestFinalHost.getRequestChannelName(), channelName);
                    }
                }
                break;

            case R.id.ll_user_channel_image:
                beansDailog();
                break;

        }
    }

    private void OpenBottomUserData() {
        getUserCoins();


    }

    private void getUserCoins() {

        viewModelClass.getPurchasedCoin(CommonUtils.getUserId()).observe(this, new Observer<Map>() {
            @Override
            public void onChanged(Map map) {

                long coins = Long.parseLong(map.get("details").toString());
                FirebaseFirestore Db;
                Db = FirebaseFirestore.getInstance();

                DocumentReference teenPati = Db.collection("SpinnerTimerBools").document("TeenPatti");
                DocumentReference Coll = Db.collection("users").document(CommonUtils.getUserId());

                Map<String, Object> TestData = new HashMap<>();
                Map<String, Object> teenPatiData = new HashMap<>();
                TestData.put("Coins", coins);
                TestData.put("bv1", 0);
                TestData.put("bv2", 0);
                TestData.put("bv3", 0);
                TestData.put("bv4", 0);
                TestData.put("bv5", 0);
                TestData.put("bv6", 0);
                TestData.put("bv7", 0);
                TestData.put("bv8", 0);
                TestData.put("YourWager", 0);
                TestData.put("id", CommonUtils.getUserId());

                teenPatiData.put("timerstart", true);

                Coll.set(TestData);
                teenPati.update(teenPatiData);

                GameBottomFragment giftBottomSheetFragment = new GameBottomFragment();
                giftBottomSheetFragment.show(getSupportFragmentManager(), giftBottomSheetFragment.getTag());

            }
        });
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

        dialogChooseAlbum.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 600);
        dialogChooseAlbum.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogChooseAlbum.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTwo;
        dialogChooseAlbum.getWindow().setGravity(Gravity.BOTTOM);

        RecyclerView recyclerViewersGifts;
        TextView txtNoGifts;

        txtNoGifts = dialogChooseAlbum.findViewById(R.id.txtNoGifts);


        recyclerViewersGifts = dialogChooseAlbum.findViewById(R.id.recyclerViewersGifts);

        if (coinList.size() <= 0) {
            txtNoGifts.setVisibility(View.VISIBLE);
        } else {
            adapterTopCoins = new AdapterTopCoins(coinList, new AdapterTopCoins.UserInfo() {
                @Override
                public void sendData(ModelSendGift listviewers) {

                    openOtherUserProfile(listviewers.getUserId(), true);
                }
            });
            recyclerViewersGifts.setAdapter(adapterTopCoins);
        }


        dialogChooseAlbum.show();

    }

    private void openBottomTwo() {


        Dialog dialogChooseAlbum = new Dialog(this);
        dialogChooseAlbum.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogChooseAlbum.setContentView(R.layout.bottom_viewers_two);
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
        RecyclerView recyclerViewersWeekly, recyclerViewersGiftsMonthly;
        View viewGifts, viewUser;
        TextView txtUser, txtGifts, txtNoViewer, txtNoGifts;
        RelativeLayout rl_gifter, rl_viewer;
        txtGifts = dialogChooseAlbum.findViewById(R.id.txtGifts);
        txtNoViewer = dialogChooseAlbum.findViewById(R.id.txtNoViewer);
        txtNoGifts = dialogChooseAlbum.findViewById(R.id.txtNoGifts);
        rl_gifter = dialogChooseAlbum.findViewById(R.id.rl_gifter);
        rl_viewer = dialogChooseAlbum.findViewById(R.id.rl_viewer);
        txtUser = dialogChooseAlbum.findViewById(R.id.txtUser);
        viewGifts = dialogChooseAlbum.findViewById(R.id.viewGifts);
        viewUser = dialogChooseAlbum.findViewById(R.id.viewUser);
        recyclerViewersWeekly = dialogChooseAlbum.findViewById(R.id.recyclerViewersWeekly);
        recyclerViewersGiftsMonthly = dialogChooseAlbum.findViewById(R.id.recyclerViewersGiftsMonthly);
        llGifts = dialogChooseAlbum.findViewById(R.id.llGifts);
        llUser = dialogChooseAlbum.findViewById(R.id.llUser);
        AdaptertWatchLive adaptertWatchLive = new AdaptertWatchLive(listViewers, listviewers -> openOtherUserProfile(listviewers.getUserId(), true));
        recyclerViewersWeekly.setAdapter(adaptertWatchLive);
//        adapterTopCoins = new AdapterTopCoins(coinList);
        recyclerViewersGiftsMonthly.setAdapter(adapterTopCoins);

        if (listViewers.size() <= 0) {
            rl_gifter.setVisibility(View.GONE);
            rl_viewer.setVisibility(View.VISIBLE);
            txtNoViewer.setVisibility(View.VISIBLE);
            recyclerViewersWeekly.setVisibility(View.GONE);


        } else {
            rl_gifter.setVisibility(View.GONE);
            rl_viewer.setVisibility(View.VISIBLE);
            txtNoViewer.setVisibility(View.GONE);
            recyclerViewersWeekly.setVisibility(View.VISIBLE);
            recyclerViewersWeekly.setVisibility(View.VISIBLE);
            adaptertWatchLive.notifyDataSetChanged();
        }


        llGifts.setOnClickListener(view -> {
            rl_gifter.setVisibility(View.VISIBLE);
            rl_viewer.setVisibility(View.GONE);

            txtGifts.setTextColor(getColor(R.color.app_pink_color));
            txtUser.setTextColor(getColor(R.color.black));
            viewGifts.setVisibility(View.VISIBLE);
            viewUser.setVisibility(View.GONE);
            if (coinList.size() <= 0) {
                rl_gifter.setVisibility(View.VISIBLE);
                rl_viewer.setVisibility(View.GONE);
                txtNoGifts.setVisibility(View.VISIBLE);
                recyclerViewersGiftsMonthly.setVisibility(View.GONE);
            } else {
                rl_gifter.setVisibility(View.VISIBLE);
                rl_viewer.setVisibility(View.GONE);
                txtNoGifts.setVisibility(View.GONE);
                recyclerViewersGiftsMonthly.setVisibility(View.VISIBLE);
                adapterTopCoins.notifyDataSetChanged();
            }

        });
        llUser.setOnClickListener(view -> {
            rl_gifter.setVisibility(View.GONE);
            rl_viewer.setVisibility(View.VISIBLE);
            txtGifts.setTextColor(getColor(R.color.black));
            txtUser.setTextColor(getColor(R.color.app_pink_color));
            viewGifts.setVisibility(View.GONE);
            viewUser.setVisibility(View.VISIBLE);
            if (coinList.size() <= 0) {
                rl_gifter.setVisibility(View.GONE);
                rl_viewer.setVisibility(View.VISIBLE);
                txtNoViewer.setVisibility(View.VISIBLE);
                recyclerViewersWeekly.setVisibility(View.GONE);
            } else {
                rl_gifter.setVisibility(View.GONE);
                rl_viewer.setVisibility(View.VISIBLE);
                txtNoViewer.setVisibility(View.GONE);
                recyclerViewersWeekly.setVisibility(View.VISIBLE);
                recyclerViewersWeekly.setVisibility(View.VISIBLE);
                adaptertWatchLive.notifyDataSetChanged();
            }


        });


        dialogChooseAlbum.show();

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
            AdaptertWatchLive adaptertWatchLive = new AdaptertWatchLive(listViewers, listviewers -> openOtherUserProfile(listviewers.getUserId(), true));
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

    private void beansDailog() {
        MainLeaderboardFragment beansBottomSheet = new MainLeaderboardFragment();
        beansBottomSheet.show(getSupportFragmentManager(), beansBottomSheet.getTag());
    }

    private void dialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(AgoraPkLiveActivity.this);
        final View confirmdailog = layoutInflater.inflate(R.layout.dialog_homecamera, null);
        androidx.appcompat.app.AlertDialog dailogbox = new androidx.appcompat.app.AlertDialog.Builder(this).create();
        dailogbox.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        Window window = dailogbox.getWindow();
//        window.setGravity(Gravity.BOTTOM);
//        WindowManager.LayoutParams layoutParams = dailogbox.getWindow().getAttributes();
//        layoutParams.y = 150;
//        dailogbox.setCancelable(false);
//        dailogbox.setCanceledOnTouchOutside(true);
//        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dailogbox.setView(confirmdailog);
        Button close = confirmdailog.findViewById(R.id.bt_close);
        close.setOnClickListener(view -> dailogbox.dismiss());
        TextView golive = confirmdailog.findViewById(R.id.tv_uploadVideo);
        golive.setOnClickListener(view -> {

            dialogPkRequestData();

//            Toast.makeText(activity, "COMING SOON", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(AgoraPkLiveActivity.this, AgoraLiveMainActivity.class));
            dailogbox.dismiss();
        });
        TextView uploadvideo = confirmdailog.findViewById(R.id.goLIve);
        uploadvideo.setOnClickListener(view -> {
            dailog_liveFriends();
//               Toast.makeText(HomeActivity.this, "Under Development", Toast.LENGTH_SHORT).show();
//               startActivity(new Intent(HomeActivity.this,Video_Recoder_A.class));
            dailogbox.dismiss();
        });
        dailogbox.show();
    }

    private void dialogPkRequestData() {

        String userId = CommonUtils.getUserId();
        Log.i("userid", userId);

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_live_users);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView tv_response = dialog.findViewById(R.id.tv_response0);

        RecyclerView rv_usersForInvite = dialog.findViewById(R.id.rv_usersForInvite);

        ImageView close = dialog.findViewById(R.id.close_dialog);
        close.setOnClickListener(v -> dialog.dismiss());


        liveMvvm.getAgoraLiveUsers(AgoraPkLiveActivity.this, CommonUtils.getUserId(), "", "", "1", "").observe(AgoraPkLiveActivity.this, modelAgoraLiveUsers -> {

            if (modelAgoraLiveUsers.getSuccess().equalsIgnoreCase("1")) {
                tv_response.setVisibility(View.GONE);
                rv_usersForInvite.setVisibility(View.VISIBLE);

                listMyLiveFRandom = modelAgoraLiveUsers.getDetails();

                final int minR = 0;
                final int maxR = listMyLiveFRandom.size() - 1;
                final int randomR = new Random().nextInt((maxR - minR) + 1) + minR;

//                Toast.makeText(activity, "" + randomR, Toast.LENGTH_SHORT).show();

                sendPkAlertDialogRandom(listMyLiveFRandom.get(randomR));


            } else {
                tv_response.setVisibility(View.VISIBLE);
                rv_usersForInvite.setVisibility(View.GONE);
            }

        });


    }

    private void  sendPkAlertDialogRandom(ModelAgoraLiveUsers.Detail detail) {

        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        final View confirmDialog = layoutInflater.inflate(R.layout.sendpk_request_dailog, null);
        final AlertDialog dialogBox = new AlertDialog.Builder(activity).create();
        dialogBox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogBox.setView(confirmDialog);
        dialogBox.setCanceledOnTouchOutside(false);

        TextView tv_text = confirmDialog.findViewById(R.id.tv_text);
        tv_text.setText("Do you want to send a PK battle request to" + " " + detail.getName());

//        Glide.with(activity).load(multiLiveModelCount.getImage()).placeholder(R.drawable.ic_user1).into(iv_image);

        TextView btn1, btn2, btn3;

        btn1 = confirmDialog.findViewById(R.id.tv_5min);
        btn2 = confirmDialog.findViewById(R.id.tv_10min);
        btn3 = confirmDialog.findViewById(R.id.tv_15min);

        Button yes, no;
        timerLimit = 5;
        yes = confirmDialog.findViewById(R.id.confirmBooking);
        no = confirmDialog.findViewById(R.id.btn_editBooking);

        yes.setText("Send");
        no.setText("Cancel");

        btn1.setOnClickListener(view -> {
            setButtonTimerBg(btn1, btn2, btn3);
            timerLimit = 5;
        });
        btn2.setOnClickListener(view -> {
            setButtonTimerBg(btn2, btn1, btn3);
            timerLimit = 10;
        });
        btn3.setOnClickListener(view -> {
            setButtonTimerBg(btn3, btn1, btn2);
            timerLimit = 15;
        });


        yes.setOnClickListener(v -> {
            ModelPkRequest modelPkRequest = new ModelPkRequest();
            modelPkRequest.setRequestUserID(userId);
            modelPkRequest.setRequestChannelName(channelName);
            modelPkRequest.setToken(token);
            modelPkRequest.setImage(image);
            modelPkRequest.setOtherUserId(detail.getUserId());
            modelPkRequest.setOtherToken(detail.getToken());
            modelPkRequest.setOtherChannelName(detail.getUsername());
            modelPkRequest.setRtmToken(rtmoken);
            modelPkRequest.setTimeLimit(timerLimit);
            modelPkRequest.setMyName(name);
            modelPkRequest.setRequestStatus("2");
            modelPkRequest.setRequestedCoins("0");
            modelPkRequest.setOtherCoins("0");
            modelPkRequest.setLiveId(getIntent().getExtras().getString(AppConstants.AGORA_LIVE_ID));
            modelPkRequest.setHostType(App.getSingleton().getHostType());
            modelPkRequest.setOtherName(detail.getName());
            modelPkRequest.setPkBattleId(String.valueOf(new Date().getTime()));
            FirebaseHelper.sendPkRequest(modelPkRequest, channelName, detail.getUsername());
            dialogBox.dismiss();
        });
        no.setOnClickListener(view -> dialogBox.dismiss());
        dialogBox.show();

    }

    private void openBox() {
        liveMvvm.getGiftBox(activity, CommonUtils.getUserId(), getLiveId, box).observe(AgoraPkLiveActivity.this, map -> {
            if (Objects.requireNonNull(map.get("success")).toString().equalsIgnoreCase("1")) {
                ll_giftBox.setVisibility(View.GONE);
                animationView.setVisibility(View.VISIBLE);
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            animationView.setVisibility(View.GONE);
//                            ll_giftBox.setVisibility(View.VISIBLE);
//                        }
//                    },5000);
                int oldCoins = 0;
                if (!tv_total_coins.getText().toString().equalsIgnoreCase("")) {
                    oldCoins = Integer.parseInt(tv_total_coins.getText().toString());
                }
                int wincoin = Integer.parseInt(Objects.requireNonNull(map.get("winCoin")).toString());
                int updatedPrice = oldCoins + wincoin;
                tv_total_coins.setText(String.valueOf(updatedPrice));
            } else {
            }
        });
    }

    private void publicBulletMessage(String text) {
        liveMvvm.publicBulletMsg(this, CommonUtils.getUserId(), messageType).observe(AgoraPkLiveActivity.this, map -> {
            if (Objects.requireNonNull(map.get("success")).toString().equalsIgnoreCase("1")) {
                if (messageType.equalsIgnoreCase("public")) {
                    ModelBulletMessage modelBulletMessage = new ModelBulletMessage();
                    modelBulletMessage.setText(text);
                    modelBulletMessage.setUsername(name);
                    FirebaseHelper.sendPublicBulletMessage(channelName, modelBulletMessage);
                    messageType = "";
                } else if (messageType.equalsIgnoreCase("bullet")) {
                    ModelBulletMain modelBulletMain = new ModelBulletMain();
                    modelBulletMain.setTextmesssage(text);
                    modelBulletMain.setUsername(name);
                    FirebaseHelper.sendBulletMessage(channelName, modelBulletMain);
                    messageType = "";
                }
            } else {
                messageType = "";
            }
        });
    }

    @Override
    public void onBackPressed() {
        stopLiveDialog();
    }


    private void openGiftsDialog() {
        GiftBottomSheetFragment giftBottomSheetFragment = new GiftBottomSheetFragment(CommonUtils.getUserId(), channelName, "", "");
        giftBottomSheetFragment.show(getSupportFragmentManager(), giftBottomSheetFragment.getTag());
    }

    public void goOffline() {
//        logoutRTm();
        FirebaseHelper.removeHostId(channelName);
        FirebaseHelper.removeLiveGuestsList(channelName, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {


            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {


            }
        });

        if (VideoGridContainer.MAX_USER == 2) {
            HashMap<String, String> data = new HashMap<>();
            data.put("type", pktype);
            data.put("pkId", pkId);
            data.put("userLiveId", liveId);
            data.put("otherLiveId", requestUserID);
            viewModelClass.archivedPkBattle(this, data).observe(this, new Observer<EndPkLive>() {
                @Override
                public void onChanged(EndPkLive map) {


                }
            });
        } else {
            liveMvvm.stopAgoraBroadcasting(activity, liveId).observe(AgoraPkLiveActivity.this, modelAgoraLiveUsers -> {
                if (modelAgoraLiveUsers.getSuccess().equalsIgnoreCase("1")) {
                    try {

                    } catch (Exception e) {
                        Log.i("Agora : ", e.getMessage());
                    } finally {

                        rtcEngine().leaveChannel();
                        exec.shutdownNow();
                    }
                } else {
                }
            });
        }
    }
//    private void leaveChannel() {
//        rtmChannel = mMessageManager.mRtmChannel;
//
//        Log.i("Agora rtmchannel", String.valueOf(rtmChannel != null));
//
//        rtmChannel.leave(new ResultCallback<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Log.i("Agora:RTM Channel left", "success");
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(ErrorInfo errorInfo) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
////                        Toast.makeText(activity, errorInfo.getErrorDescription(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//                Log.i("Agora:RTM Channel left", errorInfo.getErrorDescription());
//            }
//        });
//    }

    private void leaveChannel() {
        rtcEngine().leaveChannel();
        FirebaseHelper.removeMessages(channelName);
        FirebaseHelper.removeKickOutUser(username);

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

    public void sendChannelMessage(String msg) throws IOException {
        if (msg.isEmpty()) {
//            Toast.makeText(activity, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userProfile == null) {
            Log.i("Agora:user profile", "null");
            return;
        }
        edit_text_comment.setText("");
        ModelAgoraMessages modelAgoraMessages = new ModelAgoraMessages(msg, image, userProfile.getUserName(), CommonUtils.getUserId(), String.valueOf(level), 1, "0",System.currentTimeMillis(),"");
        FirebaseHelper.sendMessageFireBase(channelName, modelAgoraMessages);
        
        listMessages.add(modelAgoraMessages);
        adapterMessagesAgora.notifyItemInserted(adapterMessagesAgora.getItemCount());
        recycler_comment.smoothScrollToPosition(listMessages.size() - 1);
        CommonUtils.hideKeyboard(activity);

    }

    @Override
    protected void onStop() {
        rtcEngine().disableVideo();
        rtcEngine().disableAudio();
        handler = new Handler();
        runnable = this::goOffline;
        handler.postDelayed(runnable, 30000);
        super.onStop();
    }

    @Override
    protected void onPause() {
        Log.i("Agora : onPause:", "onPause");
        startTimer();
        Log.i("Agora: timer  null", "onPause");
        if (alertDialog != null) {
            alertDialog.dismiss();
            alertDialog=null;
        }
        super.onPause();
    }

    CountDownTimer countDownTimer = null;

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(180000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.i("Agora : timer : ", String.valueOf(millisUntilFinished));
            }

            public void onFinish() {
//                rtcEngine().leaveChannel();
//                logoutRTm();
                goOffline();
//                FirebaseHelper.removePlayerListener(username, viewersEventListener, giftsEventListener);
//                FirebaseHelper.setLiveStatus(false);
//                stopService(new Intent(activity, LiveExitService.class));
                stopAllAsync();

                finish();
                Log.i("Agora : rtc leave : ", String.valueOf(rtcEngine().leaveChannel()));
            }
        };

        countDownTimer.start();
    }

    @Override
    protected void onResume() {
//        screeenshotNot();
        if (sound != null) playAudio(sound);
        rtcEngine().enableAudio();
        rtcEngine().enableVideo();

        FirebaseHelper.databaseReferenceParent.removeEventListener(adminStatusListser);
        FirebaseHelper.databaseReferenceParent.removeEventListener(pkRequestChildEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(pkRequestStatusEventListener);
        FirebaseHelper.databaseReferenceParent3.removeEventListener(valueEventListener);


        FirebaseHelper.databaseReferenceParent.removeEventListener(viewersEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(giftsEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(valueBulletEvent);
        FirebaseHelper.databaseReferenceParent.removeEventListener(bulletEventListener);
        FirebaseHelper.databaseReferenceParent.removeEventListener(getEntry);
        FirebaseHelper.databaseReferenceParent.removeEventListener(multiRequestChildEventListener);

        super.onResume();

        updateLiveTime();
        rtcEngine().enableAudio();
        rtcEngine().enableVideo();
        if (runnable != null && handler != null) {
            handler.removeCallbacks(runnable);
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
            Log.i("Agora : timer : ", "stopped");
        }
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
                                Log.i("Agora:RtmToken", response.body().getDetails().getRtmToken());
                                Log.i("Agora:ChannelName", response.body().getDetails().getChannelName());

                                configEngine().setChannelName(channelName);
                                App.getSingleton().setAgoraToken(response.body().getDetails().getToke());
                                finish();
                            } else {
                            }
                        }
                    } catch (Exception e) {
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ModelGetToken> call, @NonNull Throwable t) {
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLiveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.dialogStyle);
        builder.setTitle("Stop Live Streaming ?").setPositiveButton("Yes", (dialog, which) -> {
            dialog.dismiss();
            stopAllAsync();
        }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).setMessage("Are you sure you want to stop live streaming ?").show();
    }

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

    ValueEventListener bulletEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {
                ModelBulletMessage modelBulletMessage = snapshot.getValue(ModelBulletMessage.class);
                showBulletMessage(Objects.requireNonNull(modelBulletMessage));
                FirebaseHelper.deleteBulletMessage();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    private void stopAllAsync() {
        leaveChannel();
        rtcEngine().leaveChannel();

        Log.i("Agora RTC LEAVE Channel", String.valueOf(rtcEngine().leaveChannel()));
        FirebaseHelper.removePlayerListener(username, viewersEventListener, giftsEventListener);

        if (isBattlePlayed) {

            ModelPkTimeLimit modelPkTimeLimit = new ModelPkTimeLimit();
            FirebaseHelper.setTimerForAudience(channelName, pkRequestFinalHost.getOtherChannelName(), modelPkTimeLimit, false);

            rtcEngine().stopChannelMediaRelay();

            pkRequestFinalHost.setRequestStatus("4");

            if (pkRequestFinalHost.getRequestUserID().equalsIgnoreCase(userId)) {
                FirebaseHelper.sendPkRequest(pkRequestFinalHost, channelName, pkRequestFinalHost.getOtherChannelName());
            } else {
                FirebaseHelper.sendPkRequest(pkRequestFinalHost, pkRequestFinalHost.getRequestChannelName(), channelName);
            }
        }

        FirebaseHelper.setLiveStatus(false);
        FirebaseHelper.adminStatus("0");
        FirebaseHelper.adminHost("0");
        stopService(new Intent(activity, LiveExitService.class));
//        goOffline();
        long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
//        player.setPlayWhenReady(false);
//        liveEndedDialog(elapsedMillis);
        int h = (int) (elapsedMillis / 3600000);
        int m = (int) (elapsedMillis - h * 3600000) / 60000;
        int s = (int) (elapsedMillis - h * 3600000 - m * 60000) / 1000;
        String t = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
// chronometer.setText(t);


        goOffline();

        newDialog(activity, t);
//        newDialog1(activity, t);
    }


    @Override
    protected void onDestroy() {
        if (mediaPlayer != null) stopAudio();
        stopAllAsync();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        startWorkManager();
        super.onDestroy();
    }

    private void startWorkManager() {
        Map<String, Object> map = new HashMap<>();
        map.put("liveId", getIntent().getExtras().getString(AppConstants.AGORA_LIVE_ID));
        Data data = new Data.Builder().putAll(map).build();
        WorkManager mWorkManager = WorkManager.getInstance();
        OneTimeWorkRequest mRequest = new OneTimeWorkRequest.Builder(OfflineWorkManager.class).setInputData(data).build();
        mWorkManager.enqueue(mRequest);
    }


    private void musicBottomSheet() {
        Log.i("Agora : on Music Sheet", String.valueOf(isSoundPlaying));
        SoundFragment soundFragment = new SoundFragment(playingSoundId, isSoundPlaying, this);
        soundFragment.show(getSupportFragmentManager(), soundFragment.getTag());
    }

    int playingSoundId = -1;
    IAudioEffectManager manager;
    static boolean isSoundPlaying = false;

    @Override
    public void onLiveSoundSelected(String soundPath, String soundId, String soundTitle) {
        playingSoundId = Integer.parseInt(soundId);
        manager.preloadEffect(playingSoundId, soundPath);
        manager.playEffect(
                playingSoundId,  // The sound ID of the audio effect file to be played.
                soundPath,  // The file path of the audio effect file.
                -1,   // The number of playback loops. -1 means an infinite loop.
                1.0,  // Sets the spatial position of the effect. 0 means the effect shows ahead.
                100,  // Sets the volume. The value ranges between 0 and 100. 100 is the original volume.
                4.0,
                true // Sets whether to publish the audio effect.
        );
        isSoundPlaying = true;
        Log.i("Agora : Sound Playing :", String.valueOf(isSoundPlaying));
        double volume = manager.getEffectsVolume();
        Log.i("Agora : Sound Volume : ", String.valueOf(volume));
        manager.setEffectsVolume(100);
        tv_music.setText(soundTitle);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onSoundStopped(String soundPath, String id) {
        manager.stopAllEffects();
        tv_music.setText("Music");
    }

    @Override
    public void soundProgress(int progress) {
        Log.i("Agora", String.valueOf(progress));
        rtcEngine().setAudioMixingPosition(progress);
    }


    public void onBeautyClickedz(View view) {
        view.setActivated(!view.isActivated());
        rtcEngine().setBeautyEffectOptions(view.isActivated(),
                Constants.DEFAULT_BEAUTY_OPTIONS);

        Log.i("Agora:onBeautyClicked", String.valueOf(!view.isActivated()));
    }

    ChildEventListener getFakeChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            ModelSetUserViewer modelSetUserViewer = snapshot.getValue(ModelSetUserViewer.class);
            Log.i("Agora  Listener : ", Objects.requireNonNull(modelSetUserViewer).getUserId());
            Log.i("Agora  Listener : ", modelSetUserViewer.getImage());
            Log.i("Agora  Listener : ", modelSetUserViewer.getLevel());
            if (!modelSetUserViewer.getUserId().equalsIgnoreCase(userId)) {
                listViewers.add(modelSetUserViewer);
                Collections.shuffle(listViewers);
            }
            getViewCounts();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            Log.i("Agora onChildRemoved : ", snapshot.getKey());
            ModelSetUserViewer modelSetUserViewer = snapshot.getValue(ModelSetUserViewer.class);
            for (int i = 0; i < listViewers.size(); i++) {
                if (listViewers.get(i).getUserId().equalsIgnoreCase(Objects.requireNonNull(modelSetUserViewer).getUserId())) {
                    listViewers.remove(i);
                    adapterUserList.notifyDataSetChanged();
                    String entryEffect= listViewers.get(listViewers.size()).getGarage();
                    muCarsRVImagee.setVisibility(View.VISIBLE);
                    CommonUtils.setAnimationTwo(AgoraPkLiveActivity.this,entryEffect,muCarsRVImagee);
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

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists()) {
                FirebaseHelper.getFakeViewers(getFakeChildEventListener, Integer.parseInt(String.valueOf(snapshot.getValue())));
            } else {


                FirebaseHelper.getfakeCounts("defaultFakeCounts", valueEventListener);
//                FirebaseHelper.getFakeViewers(fakeViewChildEventListener, 10);
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

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
                        startAnimationTwo(image, key, sound);


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
            tvBigGiftGiftNameTo.setText("sent a " + modelSendGift.getGiftName());
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


    private void startAnimationTwo(String image, String key, String sound) {

        img_small_video.setVisibility(View.VISIBLE);
        Glide.with(activity).load(image).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                final Handler threadHandler = new Handler();
                playAudio(sound);

                new Thread() {
                    @Override
                    public void run() {
                        stopAudio();
                        threadHandler.postDelayed(() -> img_small_video.setVisibility(View.GONE), 1000);
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
                            FirebaseHelper.deleteGiftAfterRecevingFromFireBase(key, channelName);
                        }, 2000);
                    }
                }.start();
                Log.i("Agora Gift Image : ", "ready to load");
                return false;
            }
        }).into(img_small_video);
    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private final long enterTime = System.currentTimeMillis();
    ValueEventListener messageEventLisner = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
//            if (snapshot.exists()) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    ModelAgoraMessages chatMessageModel = dataSnapshot.getValue(ModelAgoraMessages.class);
//                    chatMessageModel.getAdminStatus();
//                    listMessages.add(chatMessageModel);
//                }
//                adapterMessagesAgora.notifyDataSetChanged();
//                recycler_comment.smoothScrollToPosition(listMessages.size() - 1);
//            }
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

    private void screeenshotNot() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void mentionName(String name) {
        comment_icon.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
        relative_msg.setVisibility(View.VISIBLE);
        edit_text_comment.setText(name);
        CommonUtils.openKeyboard(activity);
    }

    private void getMessageCount() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("last-Message").child(CommonUtils.getUserId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    count++;
                } else {
                    lltextMessage.setVisibility(View.GONE);
                }
                if (count <= 1) {
                    lltextMessage.setVisibility(View.GONE);
                } else {
                    lltextMessage.setVisibility(View.VISIBLE);
                    lltextMessage.setText(String.valueOf(count - 1));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}


