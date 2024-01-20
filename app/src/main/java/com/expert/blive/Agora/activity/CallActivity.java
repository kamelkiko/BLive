package com.expert.blive.Agora.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Rational;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.Agora.AllPendingRequestAdapter;
import com.expert.blive.Agora.ChatMessageModel;
import com.expert.blive.Agora.GiftsListModel;
import com.expert.blive.Agora.RequestMultiLiveAdapter;
import com.expert.blive.Agora.UserJoinedAdapter;
import com.expert.blive.Agora.ViewerAdapter;
import com.expert.blive.Agora.agoraLive.activity.OtherUserDataModel;
import com.expert.blive.HomeActivity.Leaderboard.MainLeaderboardFragment;
import com.expert.blive.HomeMainActivity;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.EmojiGiftAdapter;
import com.expert.blive.Adapter.MultiLiveVideoAdapter;
import com.expert.blive.Agora.CommentAdapter;
import com.expert.blive.Agora.GiftAdapter;
import com.expert.blive.Agora.GoLiveModelClass;
import com.expert.blive.Agora.HeartModel;
import com.expert.blive.Agora.agoraLive.activity.BaseActivityTwo;
import com.expert.blive.Agora.agoraLive.activity.GiftBottomSheetFragment;
import com.expert.blive.Agora.agoraLive.activity.UserDetailsFragment;
import com.expert.blive.Agora.agoraLive.adapters.AdapterMessagesAgora;
import com.expert.blive.Agora.agoraLive.firebase.FirebaseHelper;
import com.expert.blive.Agora.agoraLive.firebase.models.GetBanUserpojo;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSendGift;
import com.expert.blive.Agora.agoraLive.models.LiveDescriptionModel;
import com.expert.blive.Agora.agoraLive.models.ModelAgoraMessages;
import com.expert.blive.Agora.agoraLive.models.ModelPrivateMessagePlayer;
import com.expert.blive.Agora.agoraLive.models.model.ModelBulletMain;
import com.expert.blive.Agora.heartview.Constant;
import com.expert.blive.Agora.heartview.RtlLinearLayoutManager;


import com.expert.blive.Agora.heartview.UserStatusData;
import com.expert.blive.Agora.openvcall.model.AGEventHandler;
import com.expert.blive.Agora.openvcall.model.ConstantApp;
import com.expert.blive.Agora.openvcall.model.DuringCallEventHandler;

import com.expert.blive.Agora.openvcall.propeller.ui.RecyclerItemClickListener;
import com.expert.blive.Agora.openvcall.ui.layout.SmallVideoViewAdapter;
import com.expert.blive.Agora.openvcall.ui.layout.SmallVideoViewDecoration;
import com.expert.blive.ExtraFragments.MessagesFragment;
import com.expert.blive.ExtraFragments.ThemesFragment;
//import com.expert.blive.ExtraFragments.TopGifterFragment;
import com.expert.blive.ModelClass.ChatInformation.ChatInformation;
import com.expert.blive.ModelClass.EmojiGiftModel;
import com.expert.blive.ModelClass.ModelBulletMessage;
import com.expert.blive.ModelClass.MyWallPaper;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.ModelClass.PrimeGiftModel;
import com.expert.blive.bottomteenpati.GameBottomFragment;

import com.expert.blive.retrofit.GiftSendModel;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.retrofit.SendEmojiGiftModel;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;
import com.expert.blive.R;
import com.expert.blive.databinding.ActivityCallBinding;
import com.expert.blive.databinding.CallCutSelfBinding;
import com.expert.blive.databinding.DialogAllPendingRequestBinding;
import com.expert.blive.databinding.DialogGiftBinding;
import com.expert.blive.databinding.DialogGiftEmojiBinding;
import com.expert.blive.databinding.DialogKickoutJoinedUserBinding;
import com.expert.blive.databinding.DialogLiveUserJoinedBinding;
import com.expert.blive.databinding.DialogRequestMultiLiveBinding;
import com.expert.blive.databinding.DialogRequestMultiliveListBinding;
import com.expert.blive.databinding.DialogReservSheetBinding;
import com.expert.blive.databinding.DialogSendMessageBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

import static android.content.ContentValues.TAG;

public class CallActivity extends BaseActivityTwo implements DuringCallEventHandler, UserDetailsFragment.MentionFriend {

    private OtpRoot details;
    public static final int LAYOUT_TYPE_DEFAULT = 0;
    public static final int LAYOUT_TYPE_SMALL = 1;
    List<Integer> reservedSheet = new ArrayList<>();
    int muteStatus = 0;
    int checkAudioBack=0;
    private static final int DRAW_OVER_OTHER_APP_PERMISSION = 123;
    private final static Logger log = LoggerFactory.getLogger(CallActivity.class);
    public static String liveType = "", status = "";
    public static String liveHostid = "", userIdGif;

    String allowStatus = "";
    private final HashMap<Integer, SurfaceView> mUidsList = new HashMap<>(); // uid = 0 || uid == EngineConfig.mUid
    private final Handler mUIHandler = new Handler();
    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final DatabaseReference ref = firebaseDatabase.getReference().child("userInfoAudioLive");
    private final List<ChatMessageModel> chatMessages = new ArrayList<>();
    private final List<GoLiveModelClass> viewerList = new ArrayList<>();
    private final List<GoLiveModelClass> banList = new ArrayList<>();
    public int mLayoutType = LAYOUT_TYPE_DEFAULT;
    String imageUser = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getImage();
    String usernameUser = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getUsername();
    String nameUser = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getName();
    List<String> list = new ArrayList<>();
    String emojiImage;
    int[] drawableIds = {R.drawable.ic_red_heart, R.drawable.ic_blue_heart, R.drawable.ic_peach_heart, R.drawable.ic_white_heart, R.drawable.ic_pink_heart, R.drawable.ic_green_heart, R.drawable.ic_yello_heart, R.drawable.ic_black_heart,};
    AlertDialog alertDialog;
    BottomSheetDialog requestListMultiBottomSheet;
    CountDownTimer countDownTimer;
    private List<ModelSendGift> coinList = new ArrayList<>();

    int emptyPosition;
    private TextView tv_total_coins;
    BottomSheetDialog sendMessageBottomSheet;
    View view;
    boolean isJoined = false;
    UserJoinedAdapter userJoinedAdapter;
    //    private GridVideoViewContainer mGridVideoViewContainer;
    private RelativeLayout mSmallVideoViewDock;
    private final boolean mVideoMuted = false;
    private final boolean mAudioMuted = false;
    private volatile boolean mMixingAudio = false;
    private volatile int mAudioRouting = Constants.AUDIO_ROUTE_DEFAULT;
    private volatile boolean mFullScreen = false;
    private boolean mIsLandscape = false;
    private SmallVideoViewAdapter mSmallVideoViewAdapter;
    private String getChannelName = "", username = "";
    private String getAccessToken = "", starCount = "0", coin = "0";
    private String liveStatus = "";
    private String name = "", image = "", count = "", followCount = "0", backImage = "", level = "",frame = "",myFrame = "",myGarage = "",mystryMan="";
    private ActivityCallBinding binding;
    private String otherUserId = "";
    private final List<GoLiveModelClass> requestMultiLiveList = new ArrayList<>();
    private final List<String> muteUsers = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private ViewerAdapter viewerAdapter;
    private final List<Drawable> drawablesList = new ArrayList<>();
    private RequestMultiLiveAdapter requestMultiLiveAdapter;
    private boolean isLiveConnected = false;
    //    private CreateLiveHistoryModel createLiveHistory;
    private String liveId = "";
    private Long currentTimeStamp;
    private MvvmViewModelClass viewModelClass;
    private List<PrimeGiftModel.Detail> arrayList = new ArrayList<>();
    private List<EmojiGiftModel.Detail> arrayListEmoji = new ArrayList<>();
    private final List<GoLiveModelClass> liveJoinedUserList = new ArrayList<>();
    private final List<GoLiveModelClass> liveJoinedHostUserList = new ArrayList<>();
    private MultiLiveVideoAdapter   multiLiveVideoAdapter;
    private boolean isMute = false;
    private AllPendingRequestAdapter allPendingRequestAdapter;
    String endLive;
    private long startCount;
    private int counts = 0;
    private String messageType = "";
    private boolean isLiveUser;
    private int coutgetLiveRequest=0;

    public static String getCurrentTime() {
        //date output format
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCallBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());


        viewModelClass = ViewModelProviders.of(this).get(MvvmViewModelClass.class);
        getWindow().setBackgroundDrawableResource(R.drawable.bg_gradient);
        firebaseDatabase.goOnline();
        currentTimeStamp = getCurrentTimeStamp();
        getChannelName = getIntent().getStringExtra("channelName");
        liveType = getIntent().getStringExtra("liveType");
        liveStatus = getIntent().getStringExtra("liveStatus");
        getAccessToken = getIntent().getStringExtra("token");
        starCount = getIntent().getStringExtra("starCount");
        coin = getIntent().getStringExtra("coin");
        otherUserId = getIntent().getStringExtra("userId");
        name = getIntent().getStringExtra("name");
        frame = getIntent().getStringExtra("frame");
        myFrame = getIntent().getStringExtra("myFrame");
        myGarage = getIntent().getStringExtra("myGarage");
        mystryMan=getIntent().getStringExtra("mystryMan");
        starCount = getIntent().getStringExtra("starCount");
        level = getIntent().getStringExtra("level");
        coin = getIntent().getStringExtra("coin");
        image = getIntent().getStringExtra("image");
        count = getIntent().getStringExtra("count");
        followCount = getIntent().getStringExtra("followCount");
        status = getIntent().getStringExtra("status");
        backImage = getIntent().getStringExtra("backImage");
        endLive = App.getSharedpref().getString("endLive");

        setAgora();

        FirebaseHelper.getAdmin(getChannelName, CommonUtils.getUserId(), valueEventListenerAdminTwo);
        FirebaseHelper.giftsListener(getChannelName, giftsEventListener);
        FirebaseHelper.getBanDataLive(getChannelName, CommonUtils.getUserId(), valueEventListener);
        FirebaseHelper.getMuteList(getChannelName, CommonUtils.getUserId(), getMutevalueEventListener);

        getBackGroundImageTheme();
        setData();
        getReverseSheet();
        setFrameDp();
        if (myGarage !=null) {
            binding.muCarsRVImagee.setVisibility(View.VISIBLE);
            CommonUtils.setAnimationTwo(CallActivity.this, myGarage, binding.muCarsRVImagee);
        }
//        if ( background_status == 1){
//
//            binding.backgroundGif.setBackgroundResource(R.drawable.background_audio);
//        }
//        if ( background_status == 2){
//            binding.backgroundGif.setBackgroundResource(R.drawable.bubble_gif);
//        }
        Glide.with(this).load(image).placeholder(R.drawable.girl_image).into(binding.imgHostProfile);
        CommonUtils.setAnimation(CallActivity.this,frame,binding.profieFrame);
        Glide.with(this).load(image).into(binding.imgHostProfileInfo);
        Glide.with(this).load(backImage).placeholder(R.drawable.audio_bg).into(binding.imgBackground);

        binding.txtUserHostName.setText(name);
        binding.txtUserHostNameInfo.setText(name);

        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());
        liveJoinedHostUserList.add(new GoLiveModelClass());

        liveJoinedUserList.add(new GoLiveModelClass());
        liveJoinedUserList.add(new GoLiveModelClass());
        liveJoinedUserList.add(new GoLiveModelClass());
        liveJoinedUserList.add(new GoLiveModelClass());
        liveJoinedUserList.add(new GoLiveModelClass());
        liveJoinedUserList.add(new GoLiveModelClass());
        liveJoinedUserList.add(new GoLiveModelClass());
        liveJoinedUserList.add(new GoLiveModelClass());

        setAcceptedGustHostAdapter(liveJoinedHostUserList, "0");

        if (liveStatus != null && liveStatus.length() != 0) {

            if (!liveStatus.equalsIgnoreCase("host")) {
                ref.child(CommonUtils.getUserId()).child(liveType).removeValue();
                binding.llOptionTheme.setVisibility(View.VISIBLE);
                hitCreateLiveHistoryApi(CommonUtils.getUserId(), getCurrentTime(), "", liveType);
            } else {
                binding.llOptionTheme.setVisibility(View.GONE);
                liveId = getIntent().getStringExtra("liveId");
            }

        } else {

            Log.d(TAG, "onCreate: no live status found");
        }

        getMultiLiveRequest();
        getBanListFirebase();

        getMuteUsers();
        getAllowStatus();
        manageMessageScreen();
        initHeartDrawables();
        binding.txtUserName.setText(name);
        binding.txtFollowers.setText(count);
        Glide.with(this).load(image).placeholder(R.drawable.ic_baseline_account_circle_24).into(binding.imgProfileuser);

        binding.tvStars.setText(starCount);
        binding.tvTotalCoins.setText(coin);
        if (followCount == null) {
            binding.txtFollowers.setText("0");
        } else {
            binding.txtFollowers.setText(CommonUtils.prettyCount(Integer.parseInt(followCount)));
        }

        clicks();
    }

    private void setFrameDp() {
        HashMap<String, String> data = new HashMap<>();
        data.put("userId", CommonUtils.getUserId());
        if (otherUserId !=null){
            data.put("otherUserId", otherUserId);
        }else {
            data.put("otherUserId", liveId);
        }
        viewModelClass.someFunctionality(CallActivity.this,data).observe(CallActivity.this, new Observer<OtherUserDataModel>() {
            @Override
            public void onChanged(OtherUserDataModel response) {
                if (response !=null){
                    if (response.getStatus().equalsIgnoreCase("1")){
                        CommonUtils.setAnimation(CallActivity.this,response.getDetails().getMyFrame(),binding.profieFrame);
                    }else {
                        Toast.makeText(CallActivity.this, ""+response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CallActivity.this, "Technical issue....", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setAgora() {
        rtcEngine().setChannelProfile(io.agora.rtc.Constants.CHANNEL_PROFILE_COMMUNICATION);
        rtcEngine().enableAudio();
        rtcEngine().enableAudioVolumeIndication(100, 3, false);
    }

    private void manageMessageScreen() {
        if (liveStatus.equalsIgnoreCase("host")) {
            binding.rlhostLive.setVisibility(View.GONE);
            binding.rlhost.setVisibility(View.VISIBLE);
        } else {
            binding.rlhostLive.setVisibility(View.VISIBLE);
            binding.rlhost.setVisibility(View.GONE);
            getMessageCount();
        }
    }

    private void clicks() {

        binding.rlMutetheme.setOnClickListener(v -> {

            ThemesFragment themesFragment = new ThemesFragment();
            themesFragment.show(getSupportFragmentManager(),themesFragment.getTag());
        });
        binding.imgProfileuser.setOnClickListener(v -> {
            openOtherUserProfile(otherUserId, true);
        });
        binding.imageGame.setOnClickListener(v -> {

            GameBottomFragment gameBottomFragment = new GameBottomFragment();

            gameBottomFragment.show(getSupportFragmentManager(), gameBottomFragment.getTag());
        });

        binding.llMessage.setOnClickListener(v -> {

            ChatInformation chatInformation = new ChatInformation("Send Message To host", otherUserId, "", "", "", name, image,"","");
            MessagesFragment userDetailsFragment = new MessagesFragment(chatInformation);
            userDetailsFragment.show(getSupportFragmentManager(), userDetailsFragment.getTag());
        });

//        binding.llMeesageLive.setOnClickListener(v -> {
//                ChatInformation chatInformation = new ChatInformation("Send Message To host", otherUserId, "", "", "", name, image);
//                MessagesFragment userDetailsFragment = new MessagesFragment(chatInformation);
//                userDetailsFragment.show(getSupportFragmentManager(), userDetailsFragment.getTag());
//        });
        binding.commentIconHostLive.setOnClickListener(v -> {
            binding.relativeMsgHostLive.setVisibility(View.VISIBLE);
            binding.llRighticons.setVisibility(View.GONE);
            binding.commentIconHostLive.setVisibility(View.GONE);
        });

        binding.llMeesageLiveHostLive.setOnClickListener(v1 -> {
            MessagesFragment userDetailsFragment = new MessagesFragment("1");
            userDetailsFragment.show(getSupportFragmentManager(), userDetailsFragment.getTag());
        });
        binding.imgSendCommentHostLive.setOnClickListener(v -> {

            if (binding.editTextCommentHostLive.getText().toString().equalsIgnoreCase("")) {
                Toast.makeText(this, "Cant send Empty Message", Toast.LENGTH_SHORT).show();
            } else {
                sendChannelMessage(binding.editTextCommentHostLive.getText().toString());
            }

            binding.editTextCommentHostLive.setText("");
            binding.relativeMsgHostLive.setVisibility(View.GONE);
            binding.llRighticons.setVisibility(View.VISIBLE);
            binding.commentIconHostLive.setVisibility(View.VISIBLE);
        });
        binding.rlUserInfo.setOnClickListener(v -> {
            beansDailog();
        });
    }

    public static Drawable LoadImageFromWebURL(String url) {
        try {
            InputStream iStream = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(iStream, "src name");
        } catch (Exception e) {
            return null;
        }
    }

    private void beansDailog() {
        MainLeaderboardFragment beansBottomSheet = new MainLeaderboardFragment();
        beansBottomSheet.show(getSupportFragmentManager(), beansBottomSheet.getTag());
    }
    private void setBackground(String imageBack) {

        Glide.with(CallActivity.this).load(imageBack).error(R.drawable.bubble_gif).into(binding.backgroundGif);
    }

    @Override
    protected void onGlobalLayoutCompleted() {

    }

    private void getGift() {

        ref.child(otherUserId).child(liveType).child(otherUserId).child("gifts").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                if (snapshot.exists()) {
                    GiftModelTwo giftModel = snapshot.getValue(GiftModelTwo.class);
                    showGift(giftModel);
                }
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void showGift(GiftModelTwo giftModel) {
        binding.lottieGift.setVisibility(View.GONE);
        try {
            Glide.with(this).load(giftModel.getGiftPath()).into(binding.lottieGift);
        } catch (Exception e) {

        }
//        binding.lottieGift.setAnimationFromUrl(giftModel.getGiftPath());
        binding.lottieGift.setVisibility(View.VISIBLE);
//        binding.lottieGift.playAnimation();

        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                Log.i("onTick: ", (l / 1000) + "");
            }

            @Override
            public void onFinish() {
                binding.lottieGift.setVisibility(View.GONE);
                ref.child(otherUserId).child(liveType).child(otherUserId).child("gifts").removeValue();
            }
        };
        countDownTimer.start();
    }

    private void getRequestMultiLive() {
        ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                if (snapshot.exists()) {
                    GoLiveModelClass requestModel = snapshot.getValue(GoLiveModelClass.class);
//                    openRequestDialogForMultiLive(requestModel);
                }
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

                GoLiveModelClass requestModel = snapshot.getValue(GoLiveModelClass.class);

                for (int i = 0; i < liveJoinedHostUserList.size(); i++) {

                    if (requestModel.getUserID().equalsIgnoreCase(liveJoinedHostUserList.get(i).getUserID())) {
                        liveJoinedHostUserList.set(i, requestModel);
                    }
                }
                multiLiveVideoAdapter.notifyDataSetChanged();
//                    if (requestModel.)
//                    openRequestDialogForMultiLive(requestModel);
//
            }
            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }
            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void getMultiLiveRequest() {

        ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
//                    Toast.makeText(CallActivity.this, "exists", Toast.LENGTH_SHORT).show();
                    requestMultiLiveList.clear();
                    liveJoinedUserList.clear();
                    liveJoinedHostUserList.clear();
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());
                    liveJoinedHostUserList.add(new GoLiveModelClass());

                    GoLiveModelClass hostUserDetails = new GoLiveModelClass();
                    hostUserDetails.setUserID(otherUserId);
                    hostUserDetails.setUserName(getChannelName);
                    hostUserDetails.setImage(details.getImage());
                    hostUserDetails.setName(name);
                    hostUserDetails.setSvga(myFrame);
                    hostUserDetails.setMyGarage(myGarage);
                    hostUserDetails.setMystryman(mystryMan);
                    if (allowStatus.equals("1")) {
                        liveJoinedUserList.add(hostUserDetails.getPosition(), hostUserDetails);
                    } else {
                        liveJoinedUserList.add(0, hostUserDetails);
                    }

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        GoLiveModelClass goLiveModelClass = snapshot1.getValue(GoLiveModelClass.class);
                        if (goLiveModelClass.getRequestStatus().equalsIgnoreCase("0")) {
                            if (CommonUtils.getUserId().equalsIgnoreCase(otherUserId)) {
                                boolean isSlotAvailable = false;
                                for (int i = 0; i < liveJoinedHostUserList.size(); i++) {
                                    isSlotAvailable = !liveJoinedHostUserList.get(i).getUserID().equalsIgnoreCase("");
                                }
                                if (!isSlotAvailable) {
                                    if (allowStatus.equals("1")) {
                                        setMultiLiveRequestAcceptRejecte(goLiveModelClass, "1");
                                    } else {
                                        openRequestDialogForMultiLive(goLiveModelClass);
                                    }
                                }
                            }
                            requestMultiLiveList.add(goLiveModelClass);
                        } else if (goLiveModelClass.getRequestStatus().equalsIgnoreCase("1")) {
                            liveJoinedUserList.add(goLiveModelClass);
//                            reservedSheet = goLiveModelClass.getReservedList();
//                            Toast.makeText(CallActivity.this, "list empty position " + getListEmptyPosition(), Toast.LENGTH_SHORT).show();

                            if (allowStatus.equals("1")) {
                                liveJoinedHostUserList.set(goLiveModelClass.getPosition(), goLiveModelClass);
                            } else {
                                liveJoinedHostUserList.set(getListEmptyPosition(), goLiveModelClass);
                            }
                        }
                    }

                    if (userJoinedAdapter != null) {
                        userJoinedAdapter.notifyDataSetChanged();
                    }

                    if (multiLiveVideoAdapter != null) {
                        multiLiveVideoAdapter.notifyDataSetChanged();
                    }

                    if (!liveStatus.equalsIgnoreCase("host")) {
                        setRequestListAdapter(requestMultiLiveList);
                        requestMultiLiveAdapter.notifyDataSetChanged();
                    } else {
                        if (allPendingRequestAdapter != null) {
                            allPendingRequestAdapter.notifyDataSetChanged();
                        }
                        setAllPendingAdapter(requestMultiLiveList);
                    }
                    setAcceptedGustHostAdapter(liveJoinedHostUserList, allowStatus);

                    if (multiLiveVideoAdapter != null) {
                        multiLiveVideoAdapter.notifyDataSetChanged();
                    }
                    binding.txtTotalRequest.setText("" + requestMultiLiveList.size());

                } else {
//                    Toast.makeText(CallActivity.this, "not exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void setAllPendingAdapter(List<GoLiveModelClass> requestMultiLiveList) {
        allPendingRequestAdapter = new AllPendingRequestAdapter(this, requestMultiLiveList);

    }

    private int getListEmptyPosition() {
        for (int i = 0; i < liveJoinedHostUserList.size(); i++) {

            if (liveJoinedHostUserList.get(i).getUserID().equalsIgnoreCase("") && !reservedSheet.contains(i)) {

                emptyPosition = i;

                break;
            }
        }
        return emptyPosition;
    }

    private void setAcceptedGustHostAdapter(List<GoLiveModelClass> liveJoinedUserList, String check) {
        multiLiveVideoAdapter = new MultiLiveVideoAdapter(this, liveJoinedUserList, check, muteStatus, new MultiLiveVideoAdapter.Click() {
            @Override
            public void setOnUserKickListener(GoLiveModelClass goLiveModelClass, int pos) {

                if (goLiveModelClass == null) {
                    if (CommonUtils.getUserId().equalsIgnoreCase(otherUserId)) {
                        openBottomSheetReserved(pos);
                    } else {
                        if (allowStatus.equals("1")) {
                            sendRequestForMultiLive(pos);
                        }
                    }

                } else {
                    if (CommonUtils.getUserId().equalsIgnoreCase(otherUserId)) {
                        openKickDialog(goLiveModelClass);
                    } else if (CommonUtils.getUserId().equalsIgnoreCase(goLiveModelClass.getUserID())) {
                        diloagCutselfCall(goLiveModelClass);
                    }
                }
            }

            @Override
            public void micClick(GoLiveModelClass goLiveModelClass) {
                if (CommonUtils.getUserId().equalsIgnoreCase(otherUserId)) {

                    setMuteUnMuteUser(goLiveModelClass, goLiveModelClass.getUserID());
                    multiLiveVideoAdapter.notifyDataSetChanged();
                } else if (CommonUtils.getUserId().equalsIgnoreCase(goLiveModelClass.getUserID())) {
                    if (muteStatus==0){
                        onVoiceMuteClickedSelf();
                        Toast.makeText(CallActivity.this, "edfvr", Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(CallActivity.this, "You Are Mute By Host", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, reservedSheet);
        binding.rvMultiLiveVideos.setAdapter(multiLiveVideoAdapter);

    }

    private void onVoiceMuteClickedSelf() {

        RtcEngine rtcEngine = rtcEngine();
        HashMap<String, Object> data = new HashMap<>();
//        rtcEngine.isSpeakerphoneEnabled();
        if (!isMute) {
            isMute = true;
            rtcEngine.muteLocalAudioStream(true);
            data.put("mute", "1");
        } else {
            isMute = false;
            data.put("mute", "0");
            rtcEngine.muteLocalAudioStream(false);
        }
        ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").child(CommonUtils.getUserId()).updateChildren(data);

    }

    private void diloagCutselfCall(GoLiveModelClass goLiveModelClass) {

        BottomSheetDialog sendGiftUsersDialog = new BottomSheetDialog(this);
        CallCutSelfBinding liveUserJoinedBinding = CallCutSelfBinding.inflate(LayoutInflater.from(this));
        sendGiftUsersDialog.setContentView(liveUserJoinedBinding.getRoot());
        sendGiftUsersDialog.show();
        liveUserJoinedBinding.rlTop.setOnClickListener(v -> {
            setMultiLiveRequestAcceptRejecte(goLiveModelClass, "5");
            sendGiftUsersDialog.dismiss();
        });

        liveUserJoinedBinding.textViewReservedSheet.setOnClickListener(v -> {
            sendGiftUsersDialog.dismiss();
        });
    }

    private void openBottomSheetReserved(int pos) {

        BottomSheetDialog sendGiftUsersDialog = new BottomSheetDialog(this);
        DialogReservSheetBinding liveUserJoinedBinding = DialogReservSheetBinding.inflate(LayoutInflater.from(this));
        sendGiftUsersDialog.setContentView(liveUserJoinedBinding.getRoot());
        sendGiftUsersDialog.show();
        if (reservedSheet != null) {
            if (reservedSheet.contains(pos)) {
                liveUserJoinedBinding.textViewReservedSheet.setText("Remove Reserve");
            } else {
                liveUserJoinedBinding.textViewReservedSheet.setText("Reserve Sheet");
            }
        }
        liveUserJoinedBinding.textViewReservedSheet.setOnClickListener(v -> {
            if (reservedSheet.contains(pos)) {
                reservedSheet.remove(pos);
                sendGiftUsersDialog.dismiss();
                multiLiveVideoAdapter.notifyDataSetChanged();
                Log.d("dataList", "onDataChange: Remove" + reservedSheet);
                ref.child("reservedSheet").child(otherUserId).setValue(reservedSheet);
            } else {
                reservedSheet.add(pos);
                sendGiftUsersDialog.dismiss();

                Log.d("dataList", "onDataChange: Add" + reservedSheet);
                ref.child("reservedSheet").child(otherUserId).setValue(reservedSheet);
                multiLiveVideoAdapter.notifyDataSetChanged();
            }
        });
    }

    private void openKickDialog(GoLiveModelClass goLiveModelClass) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        DialogKickoutJoinedUserBinding dialogKickoutJoinedUserBinding = DialogKickoutJoinedUserBinding.inflate(LayoutInflater.from(this));
        alert.setView(dialogKickoutJoinedUserBinding.getRoot());
        alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            alertDialog.show();
            Glide.with(CallActivity.this).load(goLiveModelClass.getImage()).placeholder(R.drawable.ic_baseline_account_circle_24).into(dialogKickoutJoinedUserBinding.imgProfile);
        } catch (Exception e) {

        }

        dialogKickoutJoinedUserBinding.txtUserName.setText("Do you want to kick out this user: " + goLiveModelClass.getName());

        dialogKickoutJoinedUserBinding.btnAccept.setOnClickListener(view -> {
            setBannedUser(goLiveModelClass);
            ref.child(otherUserId).child(liveType).child(otherUserId).child("banUser").child(goLiveModelClass.getUserID()).removeValue();
            alertDialog.dismiss();

        });

        dialogKickoutJoinedUserBinding.btnRejected.setOnClickListener(view -> {
//            setMultiLiveRequestAcceptRejecte(goLiveModelClass, "3");
//            doLeaveChannel();
            setMultiLiveRequestAcceptRejecte(goLiveModelClass, "5");
//            doLeaveChannel();
//            binding.llOption.setVisibility(View.GONE);
            alertDialog.dismiss();
        });

        dialogKickoutJoinedUserBinding.imgClose.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
    }

    private void setRequestListAdapter(List<GoLiveModelClass> requestMultiLiveList) {
        try {
            requestMultiLiveAdapter = new RequestMultiLiveAdapter(this, requestMultiLiveList, new RequestMultiLiveAdapter.Click() {
                @Override
                public void setOnRequestAcceptListener(GoLiveModelClass goLiveModelClass) {
                    requestListMultiBottomSheet.dismiss();
                    setMultiLiveRequestAcceptRejecte(goLiveModelClass, "1");
                }

                @Override
                public void setOnRequestRejectedListner(GoLiveModelClass goLiveModelClass) {
                    requestListMultiBottomSheet.dismiss();
                    setMultiLiveRequestAcceptRejecte(goLiveModelClass, "2");
                }

                @Override
                public void setOnRemoveUserListener(GoLiveModelClass goLiveModelClass) {
                    requestListMultiBottomSheet.dismiss();
                    setMultiLiveRequestAcceptRejecte(goLiveModelClass, "3");
                }
            });

        } catch (Exception e) {

        }
    }

    @SuppressLint("SetTextI18n")
    private void openRequestDialogForMultiLive(GoLiveModelClass goLiveModelClass) {

        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        DialogRequestMultiLiveBinding requestMultiLiveBinding = DialogRequestMultiLiveBinding.inflate(LayoutInflater.from(this));
        alert.setView(requestMultiLiveBinding.getRoot());
        alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        try {
            alertDialog.show();
            Glide.with(CallActivity.this).load(goLiveModelClass.getImage()).
                    placeholder(R.drawable.ic_baseline_account_circle_24).into(requestMultiLiveBinding.imgProfile);
        } catch (Exception e) {

        }
        requestMultiLiveBinding.txtUserName.setText(goLiveModelClass.getName());
        requestMultiLiveBinding.join.setText(" wants to join with you in live session.");
        requestMultiLiveBinding.btnAccept.setOnClickListener(view -> {
            alertDialog.dismiss();
            setMultiLiveRequestAcceptRejecte(goLiveModelClass, "1");
        });

        requestMultiLiveBinding.btnRejected.setOnClickListener(view -> {
            alertDialog.dismiss();
            setMultiLiveRequestAcceptRejecte(goLiveModelClass, "2");
        });

        requestMultiLiveBinding.imgClose.setOnClickListener(view -> {
            alertDialog.dismiss();
        });

    }

    private void getMessageCount() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("last-Message").child(CommonUtils.getUserId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    counts++;
                } else {
                    binding.lltextMessageHostLive.setVisibility(View.GONE);
                }
                if (counts <= 1) {
                    binding.lltextMessageHostLive.setVisibility(View.GONE);
                } else {
                    binding.lltextMessageHostLive.setVisibility(View.VISIBLE);
                    binding.lltextMessageHostLive.setText(String.valueOf(counts - 1));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setMultiLiveRequestAcceptRejecte(GoLiveModelClass goLiveModelClass, String status) {
        goLiveModelClass.setRequestStatus(status);
        ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").child(goLiveModelClass.getUserID()).setValue(goLiveModelClass);
    }

    private void hitCreateLiveHistoryApi(String userId, String currentTime, String endLive, String liveType) {
        HashMap<String, String> data = new HashMap<>();
        data.put("userId", userId);
        data.put("startLive", currentTime);
        data.put("endLive", endLive);
        data.put("liveType", liveType);

//        new VM().createLiveHistory(this, data).observe(this, new Observer<CreateLiveHistoryModel>() {
//            @Override
//            public void onChanged(CreateLiveHistoryModel createLiveHistoryModel) {
//                if (createLiveHistoryModel.getSuccess().equalsIgnoreCase("1")) {
//                    createLiveHistory = createLiveHistoryModel;
//                }
//                Toast.makeText(CallActivity.this, "" + createLiveHistoryModel.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }
    private void initHeartDrawables() {
        for (Integer i : drawableIds) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), i);
            Drawable drawable = new BitmapDrawable(bitmap);
            drawablesList.add(drawable);
        }
    }
//    private void getHeart() {
//        ref.child(otherUserId).child(liveType).child(otherUserId).child("flyingHeart").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    HeartModel data = snapshot.getValue(HeartModel.class);
//                    int position = Integer.parseInt(data.getPosition());
//                    binding.heartView.add(new HeartDrawable(drawablesList.get(position)));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
//
//
//    }
    private void getMuteUsers() {

        ref.child(otherUserId).child(liveType).child(otherUserId).child("muteUsers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    muteUsers.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        GoLiveModelClass goLiveModelClass = snapshot1.getValue(GoLiveModelClass.class);
                        muteUsers.add(goLiveModelClass.getUserID());
                    }

                    Toast.makeText(CallActivity.this, ""+muteUsers, Toast.LENGTH_SHORT).show();

                    if (liveStatus.equalsIgnoreCase("host")) {
                        if (muteUsers.contains(CommonUtils.getUserId())) {
                            muteStatus = 1;
                            Toast.makeText(CallActivity.this, "You are muted by Host", Toast.LENGTH_SHORT).show();
                            rtcEngine().muteLocalAudioStream(true);
//                            onVoiceMuteClicked(binding.imgMuteMic);
                        } else {
                            muteStatus = 0;

                            Toast.makeText(CallActivity.this, "You are unMute by Host", Toast.LENGTH_SHORT).show();

                            rtcEngine().muteLocalAudioStream(false);

                        }

                        multiLiveVideoAdapter.notifyDataSetChanged();
                    }
                }else {
                    muteStatus = 0;
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void getBanListFirebase() {
        ref.child(otherUserId).child(liveType).child(otherUserId).child("banUser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                banList.clear();
                list.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        GoLiveModelClass goLiveModelClass = snapshot1.getValue(GoLiveModelClass.class);
                        banList.add(goLiveModelClass);
                        list.add(goLiveModelClass.getUserID());
                    }
                    checkUserBanedOrNot(list);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void checkUserBanedOrNot(List<String> banList) {
        for (int i = 0; i < banList.size(); i++) {
            if (banList.contains(CommonUtils.getUserId())) {
                Toast.makeText(this, "your are baned", Toast.LENGTH_SHORT).show();
                finish();
            } else {
//                Toast.makeText(this, "your are not baned", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void getAllowStatus() {
        ref.child(otherUserId).child(liveType).child(otherUserId).child("hostLiveInfo").child("allowStatus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    allowStatus = snapshot.getValue(String.class);
                    setAcceptedGustHostAdapter(liveJoinedHostUserList, allowStatus);

                    if (allowStatus.equals("1")) {

                        updateUi("1");

                    } else {
                        updateUi("0");
                    }
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void updateUi(String data) {

        setAcceptedGustHostAdapter(liveJoinedHostUserList, data);

        multiLiveVideoAdapter.notifyDataSetChanged();

    }

    public void exitLiveStream() {
        ref.child(otherUserId).child(liveType).child(otherUserId).child("hostLiveInfo").child("live").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    boolean b = snapshot.getValue(Boolean.class);
                    if (!b) finish();
                    ref.child("reservedSheet").child(otherUserId).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    // this function create viewer list
    private void firebaseAddDataUnderHostId() {
        GoLiveModelClass goLiveModelClass = new GoLiveModelClass();
        goLiveModelClass.setUserID(CommonUtils.getUserId());
        String image = details.getImage();
        goLiveModelClass.setImage(image);
        goLiveModelClass.setName(details.getName());
        goLiveModelClass.setUserName(CommonUtils.getUserName());
        goLiveModelClass.setSvga(myFrame);
        goLiveModelClass.setMyGarage(myGarage);
        goLiveModelClass.setMystryman(mystryMan);
        goLiveModelClass.setLive(true);
        ref.child(otherUserId).child(liveType).child(otherUserId).child("viewer List").child(CommonUtils.getUserId()).setValue(goLiveModelClass);
    }

    private void sendMessage(ChatMessageModel chatMessageModel, String key) {
        ref.child(otherUserId).child(liveType).child(otherUserId).child("chat comments").child(key).setValue(chatMessageModel);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // To prevent starting the service if the required permission is NOT granted.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this)) {
            //  startService(new Intent(CallActivity.this, FloatingWidgetService.class).putExtra("activity_background", true));

            finish();

        } else {
            //   errorToast();
        }
        Log.i("onPause: ", "run pause mode");
//        rtcEngine().disableAudio();
        if (!liveStatus.equalsIgnoreCase("host")) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

        if (isLiveConnected) {
            rtcEngine().enableAudio();
//            rtcEngine().enableVideo();
        }
        Log.i("onPause: ", "go on onResume");
        if (!liveStatus.equalsIgnoreCase("host")) {
//            stopService(new Intent(this, BackgroundService.class));
//            Singleton.Companion.setStop(false);
            rtcEngine().enableAudio();
//            rtcEngine().enableVideo();
        }
//        Log.i("onResume: ", String.valueOf(Singleton.Companion.isStopLive()));
//        if (Singleton.Companion.isStopLive()) {
//            Singleton.Companion.setStopLive(false);
//            onBackPressed();
//            stopService(new Intent(this, BackgroundService.class));
//        }
    }

    private void hitEndLiveApi(String id, String currentTime) {
        HashMap<String, String> data = new HashMap<>();
        data.put("liveId", id);
        data.put("endLive", currentTime);
//        new VM().endLive(this, data).observe(this, new Observer<CreateLiveHistoryModel>() {
//            @Override
//            public void onChanged(CreateLiveHistoryModel createLiveHistoryModel) {
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Singleton.Companion.setStopLive(false);z
    }
    @Override
    protected void initUIandEvent() {

        if (liveStatus.equalsIgnoreCase("host")) {
            // if user is not host
            firebaseAddDataUnderHostId();
            sendChannelMessage("entered the stream");
            if (liveType.equalsIgnoreCase("singleLive")) {
//                binding.llOption.setVisibility(View.GONE);
//                binding.rlGiftHeart.setVisibility(View.VISIBLE);
//                binding.rlRequestMultiLive.setVisibility(View.GONE);
            } else {
//                binding.llOption.setVisibility(View.GONE);
//                binding.rlGiftHeart.setVisibility(View.VISIBLE);
//                binding.rlRequestMultiLive.setVisibility(View.VISIBLE);
                getMultiLiveRequestStatus();
                //multilive
            }
        } else {

// if user is host
            GoLiveModelClass goLiveModelClass = new GoLiveModelClass();
            goLiveModelClass.setUserID(CommonUtils.getUserId());
            goLiveModelClass.setImage(details.getImage());
            goLiveModelClass.setLiveStatus(liveStatus);
            goLiveModelClass.setLiveType(liveType);
            goLiveModelClass.setSvga(myFrame);
            goLiveModelClass.setMyGarage(myGarage);
            goLiveModelClass.setMystryman(mystryMan);
            goLiveModelClass.setAllowStatus("0");
            goLiveModelClass.setLive(true);
            ref.child(CommonUtils.getUserId()).child(liveType).child(CommonUtils.getUserId()).child("hostLiveInfo").setValue(goLiveModelClass);
//            binding.llOption.setVisibility(View.GONE);
            sendWelcomeMessageFirebase();
            getRequestMultiLive();
        }

        if (liveStatus.equalsIgnoreCase("host")) {
            exitLiveStream();
        }

        setLIveDescription();

        getViewerListFirebase();
//        getHeart();
        getGift();
        commentAdapter = new CommentAdapter(this, chatMessages);
        binding.recyclerAllMessage.setAdapter(commentAdapter);

        viewerAdapter = new ViewerAdapter(this, viewerList, liveStatus, new ViewerAdapter.Click() {
            @Override
            public void onBanned(int position) {

                openOtherUserProfile(viewerList.get(position).getUserID(), false);
//                setBannedUser(, position);
            }
        });
        binding.recyclerViewers.setAdapter(viewerAdapter);


        addEventHandler(this);

        final String encryptionKey = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY);
        final String encryptionMode = getIntent().getStringExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE);

        doConfigEngine(encryptionKey, encryptionMode);

//        mGridVideoViewContainer = findViewById(R.id.grid_video_view_container);
//        mGridVideoViewContainer.setItemEventHandler(new RecyclerItemClickListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
////                onBigVideoViewClicked(view, position);
//            }
//            @Override
//            public void onItemLongClick(View view, int position) {
//            }
//            @Override
//            public void onItemDoubleClick(View view, int position) {
////                onBigVideoViewDoubleClicked(view, position);
//            }
//        });

        if (!liveStatus.equalsIgnoreCase("host")) {
            SurfaceView surfaceV = RtcEngine.CreateRendererView(getApplicationContext());
//            preview(true, surfaceV, 0);
            surfaceV.setZOrderOnTop(false);
            surfaceV.setZOrderMediaOverlay(false);
            mUidsList.put(0, surfaceV); // get first surface view
//            rtcEngine().enableLocalVideo(false);
            rtcEngine().enableLocalAudio(true);
//            rtcEngine().enableAudio();
//            rtcEngine().volumeI = (uid, speakers) {
//                debugPrint('onAudioVolumeIndication $uid');
//            };
        } else {
//            rtcEngine().enableLocalVideo(false);
            rtcEngine().enableLocalAudio(false);
        }

//        mGridVideoViewContainer.initViewContainer(this, 0, mUidsList, mIsLandscape, mGridVideoViewContainer, RecyclerView.VERTICAL); // first is now full view
        rtcEngine().setAudioProfile(Constants.AUDIO_PROFILE_MUSIC_HIGH_QUALITY_STEREO, Constants.AUDIO_SCENARIO_SHOWROOM);
        joinChannel(getChannelName, config().mUid, getAccessToken);

        optional();

        binding.rlMuteMic.setOnClickListener(view -> {
            onVoiceMuteClicked(binding.imgMuteMic);
        });

        binding.commentIcon.setOnClickListener(view -> {
            if (muteLive.equalsIgnoreCase("1")) {
                muteDialog();
                binding.llMessegeEdittext.setVisibility(View.GONE);
                binding.commentIcon.setVisibility(View.VISIBLE);
            } else {
                binding.llMessegeEdittext.setVisibility(View.VISIBLE);
                binding.commentIcon.setVisibility(View.GONE);
                binding.llMessageShareGift.setVisibility(View.GONE);
            }
            CommonUtils.openKeyboard(this);
//            if (binding.edtMessage.getText().toString().trim().equalsIgnoreCase("")) {
//            } else {
//                if (muteLive.equals("1")) {
//                    Toast.makeText(this, "You can not send a message", Toast.LENGTH_SHORT).show();
//                    binding.edtMessage.setText("");
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String mess = "";
//                            mess = binding.edtMessage.getText().toString();
//                            binding.edtMessage.setText("");
//                            sendCustomeMessage(mess, "");
//                        }
//                    });
//                }
//            }
        });
        binding.imgGifts.setOnClickListener(view -> openGiftDialog());

        binding.imgGiftsHostLive.setOnClickListener(v -> openGiftDialog());

        binding.ivLiveshareHostLive.setOnClickListener(v -> {
            CommonUtils.shareLiveStrem(this, liveId);
        });

        binding.ivLiveshare.setOnClickListener(v -> {
            CommonUtils.shareLiveStrem(this, liveId);
        });


        binding.tvBulletMessage.setOnClickListener(v -> {
            messageType = "bullet";
            binding.tvBulletMessage.setBackgroundResource(R.drawable.bg_app_pink_colorgradient_round);
            binding.tvPublicMessage.setBackgroundResource(R.drawable.grey_stroke);
            binding.editTextComment.setHint("One Bullet per 1 Diamond");
        });

        binding.tvPublicMessage.setOnClickListener(v -> {
            messageType = "public";
            binding.tvBulletMessage.setBackgroundResource(R.drawable.bg_app_pink_colorgradient_round);
            binding.tvPublicMessage.setBackgroundResource(R.drawable.grey_stroke);
            binding.editTextComment.setHint("One Public broadcast per 1000 Diamonds");
        });
        binding.imgSendComment.setOnClickListener(v -> {

            CommonUtils.hideKeyboard(this);
            binding.tvPublicMessage.setBackgroundResource(R.drawable.grey_stroke);
            binding.tvBulletMessage.setBackgroundResource(R.drawable.grey_stroke);
            if (messageType.equalsIgnoreCase("bullet") || messageType.equalsIgnoreCase("public")) {
                publicBulletMessage(binding.editTextComment.getText().toString());
                binding.editTextComment.setText("");
                binding.editTextComment.setHint("Say Something..");
                binding.llMessegeEdittext.setVisibility(View.GONE);
                binding.commentIcon.setVisibility(View.VISIBLE);
                CommonUtils.hideKeyboard(this);
            } else {
                if (binding.editTextComment.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(this, "Cant send Empty Message", Toast.LENGTH_SHORT).show();

                } else {
                    sendChannelMessage(binding.editTextComment.getText().toString());
                }
                binding.editTextComment.setText("");
                binding.llMessegeEdittext.setVisibility(View.GONE);
                binding.llMessageShareGift.setVisibility(View.VISIBLE);
                binding.commentIcon.setVisibility(View.VISIBLE);
            }

        });
//        binding.rlEmojiGift.setOnClickListener(v -> {
//            setEmojiGifts();
//        });

        binding.liveSendRequest.setOnClickListener(v -> {
            openAllRequestLiveDialog();
        });
//        binding.rlRequestMultiLive.setOnClickListener(view -> {
////            sendRequestForMultiLive();
//            openAllRequestLiveDialog();
//        });
//
//
        binding.rlMultiLiveRequest.setOnClickListener(view -> {
            openRequestMultiLiveDialog();
//            openAllRequestLiveDialog();
        });
//        binding.rlSendMessage.setOnClickListener(view -> {
//            binding.edtMessage.requestFocus();
//            openDialogForSendMessage("");
//        });
        AudioManager audioManager = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        }

        audioManager.setMode(AudioManager.MODE_NORMAL);
        audioManager.setSpeakerphoneOn(true);

    }
    public void sendChannelMessage(String msg) {

        if (msg.isEmpty()) {
            Toast.makeText(this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        ModelAgoraMessages modelAgoraMessages = new ModelAgoraMessages(msg, imageUser, nameUser, CommonUtils.getUserId(), String.valueOf(level), 1, adminStatuscheck,System.currentTimeMillis(),mystryMan);
        FirebaseHelper.sendMessageFireBase(getChannelName, modelAgoraMessages);

        CommonUtils.hideKeyboard(this);
    }

    private void muteDialog() {
        final AlertDialog.Builder al = new AlertDialog.Builder(this, R.style.dialogStyle);
        al.setTitle("Muted?").setPositiveButton("Okay", (dialog, which) -> dialog.dismiss()).setMessage("You are muted by the Host").show();
    }

    private void openAllRequestLiveDialog() {
        requestListMultiBottomSheet = new BottomSheetDialog(this);
        DialogAllPendingRequestBinding dialogGiftBinding = DialogAllPendingRequestBinding.inflate(LayoutInflater.from(this));
        requestListMultiBottomSheet.setContentView(dialogGiftBinding.getRoot());

        if (allPendingRequestAdapter != null) {
            allPendingRequestAdapter.notifyDataSetChanged();
        }

        dialogGiftBinding.recyclerRequestMultiLive.setAdapter(allPendingRequestAdapter);
        requestListMultiBottomSheet.show();

        List<String> userIds = new ArrayList<>();
        userIds.clear();
        for (int i = 0; i < requestMultiLiveList.size(); i++) {
            userIds.add(requestMultiLiveList.get(i).getUserID());
        }
        final boolean isAlreadySentRequest;

        if (userIds.contains(CommonUtils.getUserId())) {
            isAlreadySentRequest = true;
            dialogGiftBinding.btnApply.setText("Already Applied");

        } else {
            isAlreadySentRequest = false;
            dialogGiftBinding.btnApply.setText("Apply");
        }

        dialogGiftBinding.btnApply.setOnClickListener(view -> {
            if (isAlreadySentRequest) {
                dialogGiftBinding.btnApply.setClickable(false);
            } else {
                dialogGiftBinding.btnApply.setClickable(true);
                sendRequestForMultiLive(0);
                dialogGiftBinding.btnApply.setText("Already Applied");
            }
        });

    }

    private List<LiveDescriptionModel.Detail> listdescription = new ArrayList<>();
    private final List<ModelAgoraMessages> listMessages = new ArrayList<>();
    private AdapterMessagesAgora adapterMessagesAgora;

    private void setLIveDescription() {

//        setupadd(coinList);


        new LiveMvvm().liveDescriptionModelLiveData(this).observe(this, liveDescriptionModel -> {
            if (liveDescriptionModel.getSuccess().equalsIgnoreCase("1")) {
                listdescription = liveDescriptionModel.getDetails();

                ModelAgoraMessages modelAgoraMessages = new ModelAgoraMessages(listdescription.get(0).getFrist(), "image", "userProfile.getUserName()", "CommonUtils.getUserId()", level, 0, "0",System.currentTimeMillis(),mystryMan);
                listMessages.add(modelAgoraMessages);

                modelAgoraMessages = new ModelAgoraMessages(listdescription.get(0).getSec(), "image", "userProfile.getUserName()", "CommonUtils.getUserId()", level, 0, "0",System.currentTimeMillis(),mystryMan);
                listMessages.add(modelAgoraMessages);

                modelAgoraMessages = new ModelAgoraMessages("Welcome to B Live", "image", "userProfile.getUserName()", "CommonUtils.getUserId()", level, 0, "0",System.currentTimeMillis(),mystryMan);
                listMessages.add(modelAgoraMessages);

                setRecycler();

                adapterMessagesAgora.notifyDataSetChanged();
                if (listMessages.size() > 0) {
                    binding.recyclerAllMessage.smoothScrollToPosition(listMessages.size() - 1);
                }
//                    liveDescriptionAdapter=new LiveDescriptionAdapter(AgoraPlayerActivity.this,list);
//                    rv_livedescription.setAdapter(liveDescriptionAdapter);
                getMessageFireBase();

            } else {
            }
        });
    }

    private void setRecycler() {
        adapterMessagesAgora = new AdapterMessagesAgora(this, listMessages, (otherUserId, username, positon) -> {
            if (otherUserId.equalsIgnoreCase(this.otherUserId)) {
                isLiveUser = true;
            }
            openOtherUserProfile(otherUserId, isLiveUser);
        }, "0");
        binding.recyclerAllMessage.setAdapter(adapterMessagesAgora);
//        setupadd(coinList);

    }

    private void getMessageFireBase() {

        FirebaseHelper.getMessage(getChannelName, messageEventLisner);

    }

    private final long enterTime = System.currentTimeMillis();

    ValueEventListener messageEventLisner = new ValueEventListener() {
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

                binding.recyclerAllMessage.scrollToPosition(listMessages.size() - 1);


            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void openDialogForSendMessage(String name) {

        sendMessageBottomSheet = new BottomSheetDialog(this);
        DialogSendMessageBinding dialogGiftBinding = DialogSendMessageBinding.inflate(LayoutInflater.from(this));
        sendMessageBottomSheet.setContentView(dialogGiftBinding.getRoot());

        sendMessageBottomSheet.show();

        dialogGiftBinding.edtMessage.requestFocus();

        dialogGiftBinding.edtMessage.setText(name + " ");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        dialogGiftBinding.rlSend.setOnClickListener(view -> {
            if (dialogGiftBinding.edtMessage.getText().toString().trim().equalsIgnoreCase("")) {
            } else {
                if (muteLive.equals("1")) {
                    Toast.makeText(this, "You can not send a message", Toast.LENGTH_SHORT).show();
                    dialogGiftBinding.edtMessage.setText("");
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String mess = "";
                            mess = dialogGiftBinding.edtMessage.getText().toString();
                            dialogGiftBinding.edtMessage.setText("");
                            sendMessageBottomSheet.dismiss();
                            sendCustomeMessage(mess, "");
                        }
                    });

                }
            }
        });
    }

    private void sendGiftFirebase(PrimeGiftModel.Detail detail) {
        GiftModelTwo giftModel = new GiftModelTwo();
        giftModel.setGiftPath(detail.getImage());
        giftModel.setUserId(CommonUtils.getUserId());
        giftModel.setUserName(details.getName());
        String key = ref.push().getKey();
        ref.child(otherUserId).child(liveType).child(otherUserId).child("gifts").child(key).setValue(giftModel);
        sendCustomeMessage("Sends you gift", detail.getImage());
    }

    private void getMultiLiveRequestStatus() {
        ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").child(CommonUtils.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    GoLiveModelClass goLiveModelClass = snapshot.getValue(GoLiveModelClass.class);
                    if (goLiveModelClass.getRequestStatus().equalsIgnoreCase("1")) {
                        isLiveConnected = true;
                        if (coutgetLiveRequest<=0){
                            Toast.makeText(CallActivity.this, "Request Accepted", Toast.LENGTH_SHORT).show();
                            setViewerGoLiveWithHost();
                            coutgetLiveRequest++;
                            binding.liveSendRequest.setVisibility(View.GONE);

                        }
                    } else if (goLiveModelClass.getRequestStatus().equalsIgnoreCase("2")) {
                        if (isLiveConnected) {
                            binding.liveSendRequest.setVisibility(View.VISIBLE);
                            joinChannel(getChannelName, config().mUid, getAccessToken);
                        }
                        isLiveConnected = false;
                        Toast.makeText(CallActivity.this, "Request Rejected", Toast.LENGTH_SHORT).show();
                    } else if (goLiveModelClass.getRequestStatus().equalsIgnoreCase("3")) {
                        isLiveConnected = false;
                        removeByHostLive();
                        Toast.makeText(CallActivity.this, "Remove By Host", Toast.LENGTH_SHORT).show();
                    } else if (goLiveModelClass.getRequestStatus().equalsIgnoreCase("5")) {
                        Toast.makeText(CallActivity.this, "data", Toast.LENGTH_SHORT).show();
                        rtcEngine().enableLocalAudio(false);
//                        rtcEngine().disableAudio();
                        isLiveConnected = false;
                    } else {
                        isLiveConnected = false;
                        Toast.makeText(CallActivity.this, "Waiting for host to accept request", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void removeByHostLive() {
//        finish();
        deInitUIandEvent();
    }

    private void setViewerGoLiveWithHost() {
        SurfaceView surfaceV = RtcEngine.CreateRendererView(getApplicationContext());
//        preview(true, surfaceV, 0);
        surfaceV.setZOrderOnTop(false);
        surfaceV.setZOrderMediaOverlay(false);
        mUidsList.put(0, surfaceV); // get first surface view
        rtcEngine().enableLocalAudio(true);
//        binding.llOption.setVisibility(View.GONE);
        binding.rlMultiLiveRequest.setVisibility(View.GONE);
        binding.liveSendRequest.setVisibility(View.GONE);
//        binding.rlRequestMultiLive.setVisibility(View.GONE);
//        binding.rlGift.setVisibility(View.GONE);
//        binding.rlEmojiGift.setVisibility(View.VISIBLE);
        isJoined = true;
    }

    private void openRequestMultiLiveDialog() {
        requestListMultiBottomSheet = new BottomSheetDialog(this);
        DialogRequestMultiliveListBinding dialogGiftBinding = DialogRequestMultiliveListBinding.inflate(LayoutInflater.from(this));
        requestListMultiBottomSheet.setContentView(dialogGiftBinding.getRoot());
        dialogGiftBinding.recyclerRequestMultiLive.setAdapter(requestMultiLiveAdapter);


        if (allowStatus.equals("1")) {
            dialogGiftBinding.allowAnyOne.setChecked(true);
        } else {
            dialogGiftBinding.allowAnyOne.setChecked(false);

        }
        dialogGiftBinding.allowAnyOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    updateAnyOne("1");

                } else {
                    updateAnyOne("0");

                }
            }
        });
        requestListMultiBottomSheet.show();
    }

    private void updateAnyOne(String s) {

        Map<String, Object> data = new HashMap<>();
        data.put("allowStatus", s);
        ref.child(CommonUtils.getUserId()).child(liveType).child(CommonUtils.getUserId()).child("hostLiveInfo").updateChildren(data);


    }

    private void sendRequestForMultiLive(int pos) {
        GoLiveModelClass goLiveModelClass = new GoLiveModelClass();
        goLiveModelClass.setUserID(CommonUtils.getUserId());
        String image = details.getImage();
        goLiveModelClass.setImage(image);
        goLiveModelClass.setName(details.getName());
        goLiveModelClass.setUserName(details.getUsername());
        goLiveModelClass.setSvga(myFrame);
        goLiveModelClass.setMyGarage(myGarage);
        goLiveModelClass.setMystryman(mystryMan);
        goLiveModelClass.setPosition(pos);
        goLiveModelClass.setRequestStatus("0");
        // 1 for not mute
        goLiveModelClass.setMute("0");
        ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").child(CommonUtils.getUserId()).setValue(goLiveModelClass);

    }

    private void setEmojiGifts() {
        liveHostid = getIntent().getStringExtra("liveHostId");

        final BottomSheetDialog emojiGiftDialog = new BottomSheetDialog(this);
        DialogGiftEmojiBinding dialogGiftEmojiBinding = DialogGiftEmojiBinding.inflate(LayoutInflater.from(this));
        emojiGiftDialog.setContentView(dialogGiftEmojiBinding.getRoot());
        emojiGiftDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        HashMap<String, String> data = new HashMap<>();
        data.put("userId", CommonUtils.getUserId());

        viewModelClass.emojiGiftModelLiveData(CallActivity.this).observe(this, emojiGiftModel -> {
            if (emojiGiftModel.getSuccess().equalsIgnoreCase("1")) {
                arrayListEmoji = emojiGiftModel.getDetails();
                EmojiGiftAdapter emojiGiftAdapter = new EmojiGiftAdapter(CallActivity.this, arrayListEmoji, new EmojiGiftAdapter.Select() {
                    @Override
                    public void details(EmojiGiftModel.Detail detail) {

                        sendEmojiGift(detail, otherUserId, CommonUtils.getUserId(), detail.getPrimeAccount(), detail.getId(), liveHostid);
                        emojiGiftDialog.dismiss();
                    }
                });
                dialogGiftEmojiBinding.recyclerEmojiGift.setAdapter(emojiGiftAdapter);

            }
        });
        emojiGiftDialog.show();


    }

    private void sendEmojiGift(EmojiGiftModel.Detail detail, String otherUserId, String userId, String primeAccount, String id, String liveHostid) {

        viewModelClass.sendEmojiGiftModelLiveData(CallActivity.this, otherUserId, userId, primeAccount, id, liveHostid).observe(this, new Observer<SendEmojiGiftModel>() {
            @Override
            public void onChanged(SendEmojiGiftModel sendEmojiGiftModel) {
                if (sendEmojiGiftModel.getSuccess().equalsIgnoreCase("1")) {
                    emojiImage = detail.getImage();
                    sendEmojiToHost(detail);

                }
            }
        });
    }

    private void sendEmojiToHost(EmojiGiftModel.Detail detail) {

        GiftModelTwo giftModel = new GiftModelTwo();
        giftModel.setGiftPath(detail.getImage());
        giftModel.setUserId(CommonUtils.getUserId());
        giftModel.setUserName(details.getName());
        String key = ref.push().getKey();
        ref.child(otherUserId).child(liveType).child(otherUserId).child("gifts").child(key).setValue(giftModel);
        sendCustomeMessage("Sends you gift", detail.getImage());
    }

    private void openGiftDialog() {

        GiftBottomSheetFragment giftBottomSheetFragment = new GiftBottomSheetFragment(otherUserId, getChannelName, liveId, "");
        giftBottomSheetFragment.show(getSupportFragmentManager(), giftBottomSheetFragment.getTag());

    }

    private void hitSendGift(PrimeGiftModel.Detail detail, String otherUserId, String userId, String primeAccount, String id, String liveHostid) {

        viewModelClass.sendModelLiveData(CallActivity.this, otherUserId, userId, primeAccount, id, liveHostid).observe(this, new Observer<GiftSendModel>() {
            @Override
            public void onChanged(GiftSendModel giftSendModel) {
                if (giftSendModel.getSuccess().equalsIgnoreCase("1")) {
                }
                sendGiftFirebase(detail);

                Toast.makeText(CallActivity.this, "" + giftSendModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendGiftToHost(PrimeGiftModel.Detail detail) {

        GiftModelTwo giftModel = new GiftModelTwo();
        giftModel.setGiftPath(detail.getImage());
        giftModel.setUserId(CommonUtils.getUserId());
        giftModel.setUserName(details.getName());
        String key = ref.push().getKey();
        ref.child(otherUserId).child(liveType).child(otherUserId).child("gifts").child(key).setValue(giftModel);
        sendCustomeMessage("Sends you gift", detail.getImage());

    }

    private void setGiftListAdapter(List<GiftsListModel.Detail> details, DialogGiftBinding dialogGiftBinding, BottomSheetDialog giftBottomDialogSheet) {

        GiftAdapter giftAdapter = new GiftAdapter(this, details, new GiftAdapter.Click() {
            @Override
            public void setOnSendGiftListener(GiftsListModel.Detail detail) {
                giftBottomDialogSheet.dismiss();
                if (liveType.equalsIgnoreCase("singleLive")) {
                    hitSendGiftApi(CommonUtils.getUserId(), otherUserId, detail.getAmount(), detail.getId(), liveId, detail);
                } else {
                    if (liveJoinedUserList.size() <= 1) {
                        hitSendGiftApi(CommonUtils.getUserId(), otherUserId, detail.getAmount(), detail.getId(), liveId, detail);
                    } else {
                        openDialogUsersLiveJoined(detail);
                    }
                }

            }
        });
        dialogGiftBinding.recyclerGift.setAdapter(giftAdapter);
    }

    private void hitSendGiftApi(String giftSenderId, String giftRecieverId, String amount, String giftId, String liveID, GiftsListModel.Detail detail) {
        HashMap<String, String> data = new HashMap<>();
        data.put("senderId", giftSenderId);
        data.put("diamond", amount);
        data.put("receiverId", giftRecieverId);
        data.put("giftId", giftId);
        data.put("liveId", liveID);
//        new VM().sendGiftApi(this, data).observe(this, new Observer<GiftsListModel>() {
//            @Override
//            public void onChanged(GiftsListModel giftsListModel) {
//                if (giftsListModel.getSuccess().equalsIgnoreCase("1")) {
//                    sendGiftFirebase(detail);
//                }
//            }
//        });
    }

    private void openDialogUsersLiveJoined(GiftsListModel.Detail detail) {
        BottomSheetDialog sendGiftUsersDialog = new BottomSheetDialog(this);
        DialogLiveUserJoinedBinding liveUserJoinedBinding = DialogLiveUserJoinedBinding.inflate(LayoutInflater.from(this));
        sendGiftUsersDialog.setContentView(liveUserJoinedBinding.getRoot());
        userJoinedAdapter = new UserJoinedAdapter(this, liveJoinedUserList, new UserJoinedAdapter.Click() {
            @Override
            public void setOnSendGiftListener(GoLiveModelClass goLiveModelClass) {
                sendGiftUsersDialog.dismiss();
//                sendGiftFirebase(detail);
            }
        });

        liveUserJoinedBinding.recyclerRequestMultiLive.setAdapter(userJoinedAdapter);
        sendGiftUsersDialog.show();
    }

    private void sendFlyingHeartInFirebase() {
//for position
        int size = drawableIds.length;
        final int min = 0;
        final int max = size - 1;
        final int random = new Random().nextInt((max - min) + 1) + min;

//for update listner
        final int minR = 0;
        final int maxR = 1000;
        final int randomR = new Random().nextInt((maxR - minR) + 1) + minR;

        HeartModel heartModel = new HeartModel();
        heartModel.setPosition(String.valueOf(random));
        heartModel.setRandom(String.valueOf(randomR));
        ref.child(otherUserId).child(liveType).child(otherUserId).child("flyingHeart").setValue(heartModel);
    }

    private void openOtherUserProfile(String userId, boolean live) {

        UserDetailsFragment userDetailsFragment;
        if (App.getSingleton().getGiftCheck().equals("1")) {
            userDetailsFragment = new UserDetailsFragment(userId, live, false, false, null, username, liveId, this::mentionName);
        } else {
            userDetailsFragment = new UserDetailsFragment(userId, live, true, false, adminStatuscheck, username, liveId, this::mentionName);
        }
        userDetailsFragment.show(getSupportFragmentManager(), userDetailsFragment.getTag());

    }

    private void setMuteUnMuteUser(GoLiveModelClass goLiveModelClass, String userId) {
        HashMap<String, Object> data = new HashMap<>();


        if (goLiveModelClass.getMute().equalsIgnoreCase("") || goLiveModelClass.getMute().equalsIgnoreCase("0")) {
            data.put("mute", "1");
            ref.child(otherUserId).child(liveType).child(otherUserId).child("muteUsers").child(userId)
                    .setValue(goLiveModelClass);
        } else {
            data.put("mute", "0");
            ref.child(otherUserId).child(liveType).child(otherUserId).child("muteUsers").child(userId)
                    .removeValue();
        }
//        if (!isMute) {
////            isMute = true;
////            rtcEngine.muteLocalAudioStream(true);
//            data.put("mute", "0");
//        } else {
////            isMute = false;
////            data.put("mute", "1");
////            rtcEngine.muteLocalAudioStream(false);
//        }
        ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").child(userId)
                .updateChildren(data);
//        ImageView iv = (ImageView) view;
//        if (isMute) {
//            iv.setImageResource(R.drawable.ic_baseline_mic_off_24);
//        } else {
//            iv.setImageResource(R.drawable.ic_baseline_mic_24);
//        }
    }

    private void setBannedUser(GoLiveModelClass goLiveModelClass) {
        ref.child(otherUserId).child(liveType).child(otherUserId).child("banUser").child(goLiveModelClass.getUserID()).setValue(goLiveModelClass);
    }

    private void getViewerListFirebase() {
        ref.child(otherUserId).child(liveType).child(otherUserId).child("viewer List").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    viewerList.clear();

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        GoLiveModelClass goLiveModelClass = dataSnapshot.getValue(GoLiveModelClass.class);

                        if (goLiveModelClass.isLive()) {
                            viewerList.add(goLiveModelClass);
                        }
                    }
                    viewerAdapter.notifyDataSetChanged();
                    binding.recyclerViewers.scrollToPosition(viewerList.size() - 1);
                    String entryEffect= viewerList.get(viewerList.size()-1).getMyGarage();
                    binding.muCarsRVImagee.setVisibility(View.VISIBLE);
                    CommonUtils.setAnimationTwo(CallActivity.this,entryEffect,binding.muCarsRVImagee);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
    }

    private Long getCurrentTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp.getTime());
        return timestamp.getTime();
    }

    private void sendCustomeMessage(String mess, String gift) {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setGift(gift);
        chatMessageModel.setImage(details.getImage());
        chatMessageModel.setKey(ref.push().getKey());
        chatMessageModel.setMessage(mess);
        chatMessageModel.setAdminStatus(adminStatuscheck);

        chatMessageModel.setName(details.getName());
        chatMessageModel.setUserId(CommonUtils.getUserId());
        chatMessageModel.setTimeStamp(getCurrentTimeStamp());
        sendMessage(chatMessageModel, chatMessageModel.getKey());
    }

    private void sendJoinedMessage() {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setGift("");
        chatMessageModel.setImage(details.getImage());
        chatMessageModel.setKey(ref.push().getKey());
        chatMessageModel.setMessage("joined Stream");
        chatMessageModel.setAdminStatus(adminStatuscheck);
        chatMessageModel.setName(details.getName());
        chatMessageModel.setUserId(CommonUtils.getUserId());
        chatMessageModel.setTimeStamp(getCurrentTimeStamp());
        sendMessage(chatMessageModel, chatMessageModel.getKey());
    }

    private void sendWelcomeMessageFirebase() {
        ChatMessageModel chatMessageModel = new ChatMessageModel();
        chatMessageModel.setGift("");
        chatMessageModel.setImage(details.getImage());
        chatMessageModel.setKey(ref.push().getKey());
        chatMessageModel.setMessage("Welcome to Nico Live");
        chatMessageModel.setName(details.getName());
        chatMessageModel.setUserId(CommonUtils.getUserId());
        chatMessageModel.setTimeStamp(getCurrentTimeStamp());
        sendMessage(chatMessageModel, chatMessageModel.getKey());
    }

    private void deleterLiveStreamViewers() {
        ref.child(otherUserId).child(liveType).child(otherUserId).child("viewer List").child(CommonUtils.getUserId()).child("live").setValue(false);
//        sendChannelMessage("Left stream");
    }

    private void setLiveStreamOffline() {
        HashMap<String, Boolean> data = new HashMap<>();
        data.put("live", false);
        ref.child(CommonUtils.getUserId()).child(liveType).child(CommonUtils.getUserId()).child("hostLiveInfo").setValue(data);
        ref.child(CommonUtils.getUserId()).child(liveType).removeValue();
    }

    private void onBigVideoViewClicked(View view, int position) {
        log.debug("onItemClick " + view + " " + position + " " + mLayoutType);
        toggleFullscreen();
    }

    private void onBigVideoViewDoubleClicked(View view, int position) {
        log.debug("onItemDoubleClick " + view + " " + position + " " + mLayoutType);

        if (mUidsList.size() < 2) {

            return;

        }
//        com.expert.nicolive.Agora.heartview.UserStatusData user = mGridVideoViewContainer.getItem(position);
//        int uid = (user.mUid == 0) ? config().mUid : user.mUid;

//        if (mLayoutType == LAYOUT_TYPE_DEFAULT && mUidsList.size() != 1) {
//            switchToSmallVideoView(uid);
//        } else {
//            switchToDefaultVideoView();
//        }
    }
    private void onSmallVideoViewDoubleClicked(View view, int position) {
        log.debug("onItemDoubleClick small " + view + " " + position + " " + mLayoutType);

        switchToDefaultVideoView();
    }

    private void showOrHideStatusBar(boolean hide) {
        // May fail on some kinds of devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            View decorView = getWindow().getDecorView();
            int uiOptions = decorView.getSystemUiVisibility();

            if (hide) {
                uiOptions |= View.SYSTEM_UI_FLAG_FULLSCREEN;
            } else {
                uiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
            }
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void toggleFullscreen() {
        mFullScreen = !mFullScreen;
        showOrHideCtrlViews(mFullScreen);
        mUIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showOrHideStatusBar(mFullScreen);
            }
        }, 200); // action bar fade duration
    }

    private void showOrHideCtrlViews(boolean hide) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            if (hide) {
                ab.hide();
            } else {
                ab.show();
            }
        }
    }
    private void optional() {
        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
    }

    private void optionalDestroy() {
    }

    private int getVideoEncResolutionIndex() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int videoEncResolutionIndex = pref.getInt(ConstantApp.PrefManager.PREF_PROPERTY_VIDEO_ENC_RESOLUTION, ConstantApp.DEFAULT_VIDEO_ENC_RESOLUTION_IDX);
        if (videoEncResolutionIndex > ConstantApp.VIDEO_DIMENSIONS.length - 1) {
            videoEncResolutionIndex = ConstantApp.DEFAULT_VIDEO_ENC_RESOLUTION_IDX;

            // save the new value
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt(ConstantApp.PrefManager.PREF_PROPERTY_VIDEO_ENC_RESOLUTION, videoEncResolutionIndex);
            editor.apply();
        }
        return videoEncResolutionIndex;
    }

    private int getVideoEncFpsIndex() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int videoEncFpsIndex = pref.getInt(ConstantApp.PrefManager.PREF_PROPERTY_VIDEO_ENC_FPS, ConstantApp.DEFAULT_VIDEO_ENC_FPS_IDX);
        if (videoEncFpsIndex > ConstantApp.VIDEO_FPS.length - 1) {
            videoEncFpsIndex = ConstantApp.DEFAULT_VIDEO_ENC_FPS_IDX;

            // save the new value
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt(ConstantApp.PrefManager.PREF_PROPERTY_VIDEO_ENC_FPS, videoEncFpsIndex);
            editor.apply();
        }
        return videoEncFpsIndex;
    }

    private void doConfigEngine(String encryptionKey, String encryptionMode) {
        VideoEncoderConfiguration.VideoDimensions videoDimension = ConstantApp.VIDEO_DIMENSIONS[getVideoEncResolutionIndex()];
        VideoEncoderConfiguration.FRAME_RATE videoFps = ConstantApp.VIDEO_FPS[getVideoEncFpsIndex()];
        configEngine(videoDimension, videoFps, encryptionKey, encryptionMode);
    }

    public void onSwitchSpeakerClicked(View view) {
        RtcEngine rtcEngine = rtcEngine();
        /*
          Enables/Disables the audio playback route to the speakerphone.
          This method sets whether the audio is routed to the speakerphone or earpiece.
          After calling this method, the SDK returns the onAudioRouteChanged callback
          to indicate the changes.
         */
        rtcEngine.setEnableSpeakerphone(mAudioRouting != Constants.AUDIO_ROUTE_SPEAKERPHONE);
    }

    @Override
    protected void deInitUIandEvent() {
        if (!liveStatus.equalsIgnoreCase("host")) {
            setLiveStreamOffline();
//            hitEndLiveApi(createLiveHistory.getDetails().getId(), getCurrentTime());
        } else {
            setViewerExitInMultiLive("5");
        }

        if (isLiveConnected) {
            setViewerExitInMultiLive("2");
        }

        deleterLiveStreamViewers();
        optionalDestroy();
        doLeaveChannel();
        removeEventHandler(this);
        mUidsList.clear();

    }

    private void setViewerExitInMultiLive(String status) {
        GoLiveModelClass goLiveModelClass = new GoLiveModelClass();
        goLiveModelClass.setUserID(CommonUtils.getUserId());
        String image = details.getImage();
        goLiveModelClass.setImage(image);
        goLiveModelClass.setName(details.getName());
        goLiveModelClass.setSvga(myFrame);
        goLiveModelClass.setMyGarage(myGarage);
        goLiveModelClass.setMystryman(mystryMan);
        goLiveModelClass.setUserName(CommonUtils.getUserName());
        goLiveModelClass.setRequestStatus(status);
        setMultiLiveRequestAcceptRejecte(goLiveModelClass, status);
    }

    private void doLeaveChannel() {
        leaveChannel(config().mChannel);
//        preview(false, null, 0);
    }
    public void onHangupClicked(View view) {
        log.info("onHangupClicked " + view);

        finish();
    }
    private SurfaceView getLocalView() {
        for (HashMap.Entry<Integer, SurfaceView> entry : mUidsList.entrySet()) {
            if (entry.getKey() == 0 || entry.getKey() == config().mUid) {
                return entry.getValue();
            }
        }
        return null;
    }
//    private void doHideTargetView(int targetUid, boolean hide) {
//        HashMap<Integer, Integer> status = new HashMap<>();
//        status.put(targetUid, hide ? com.expert.nicolive.Agora.heartview.UserStatusData.VIDEO_MUTED : com.expert.nicolive.Agora.heartview.UserStatusData.DEFAULT_STATUS);
//        if (mLayoutType == LAYOUT_TYPE_DEFAULT) {
////            mGridVideoViewContainer.notifyUiChanged(mUidsList, targetUid, status, null);
//        } else if (mLayoutType == LAYOUT_TYPE_SMALL) {
////            com.expert.nicolive.Agora.heartview.UserStatusData bigBgUser = mGridVideoViewContainer.getItem(0);
//            if (bigBgUser.mUid == targetUid) { // big background is target view
////                mGridVideoViewContainer.notifyUiChanged(mUidsList, targetUid, status, null);
//            } else { // find target view in small video view list
//                log.warn("SmallVideoViewAdapter call notifyUiChanged " + mUidsList + " " + (bigBgUser.mUid & 0xFFFFFFFFL) + " target: " + (targetUid & 0xFFFFFFFFL) + "==" + targetUid + " " + status);
//                mSmallVideoViewAdapter.notifyUiChanged(mUidsList, bigBgUser.mUid, status, null);
//            }
//        }
//    }
    public void onVoiceMuteClicked(View view) {
//        log.info("onVoiceMuteClicked " + view + " " + mUidsList.size() + " video_status: " + mVideoMuted + " audio_status: " + mAudioMuted);
//        if (mUidsList.size() == 0) {
//            return;
//        }
        RtcEngine rtcEngine = rtcEngine();
        HashMap<String, Object> data = new HashMap<>();
        if (!isMute) {
            isMute = true;
            rtcEngine.muteLocalAudioStream(true);
            data.put("mute", "0");

        } else {
            isMute = false;
            data.put("mute", "1");
            rtcEngine.muteLocalAudioStream(false);
        }
        ref.child(otherUserId).child(liveType).child(otherUserId).child("multiLiveRequest").child(CommonUtils.getUserId()).updateChildren(data);

        ImageView iv = (ImageView) view;
        if (isMute) {
            iv.setImageResource(R.drawable.ic_baseline_mic_off_24);
        } else {
            iv.setImageResource(R.drawable.ic_baseline_mic_24);
        }


    }

    public void onMixingAudioClicked(View view) {
        log.info("onMixingAudioClicked " + view + " " + mUidsList.size() + " video_status: " + mVideoMuted + " audio_status: " + mAudioMuted + " mixing_audio: " + mMixingAudio);

        if (mUidsList.size() == 0) {
            return;
        }

        mMixingAudio = !mMixingAudio;

        RtcEngine rtcEngine = rtcEngine();
        if (mMixingAudio) {
            rtcEngine.startAudioMixing(Constant.MIX_FILE_PATH, false, false, -1);
        } else {
            rtcEngine.stopAudioMixing();
        }

        ImageView iv = (ImageView) view;
//        iv.setImageResource(mMixingAudio ? R.drawable.btn_audio_mixing : R.drawable.btn_audio_mixing_off);
    }

    @Override
    public void onUserJoined(int uid) {
        log.debug("onUserJoined " + (uid & 0xFFFFFFFFL));

//ToDo

//        doRenderRemoteUi(uid);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                notifyMessageChanged(new Message(new User(0, null), "user " + (uid & 0xFFFFFFFFL) + " joined"));
            }
        });
    }

    @Override
    public void onFirstRemoteVideoDecoded(int uid, int width, int height, int elapsed) {
        log.debug("onFirstRemoteVideoDecoded " + (uid & 0xFFFFFFFFL) + " " + width + " " + height + " " + elapsed);
    }

    @Override
    public void onLeaveChannel(IRtcEngineEventHandler.RtcStats stats) {
    }

    private void doRenderRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                if (mUidsList.containsKey(uid)) {
                    return;
                }

                /*
                  Creates the video renderer view.
                  CreateRendererView returns the SurfaceView type. The operation and layout of the
                  view are managed by the app, and the Agora SDK renders the view provided by the
                  app. The video display view must be created using this method instead of
                  directly calling SurfaceView.
                 */
                SurfaceView surfaceV = RtcEngine.CreateRendererView(getApplicationContext());
                mUidsList.put(uid, surfaceV);

                boolean useDefaultLayout = mLayoutType == LAYOUT_TYPE_DEFAULT;

                surfaceV.setZOrderOnTop(true);
                surfaceV.setZOrderMediaOverlay(true);

                /*
                  Initializes the video view of a remote user.
                  This method initializes the video view of a remote stream on the local device. It affects only the video view that the local user sees.
                  Call this method to bind the remote video stream to a video view and to set the rendering and mirror modes of the video view.
                 */


                rtcEngine().setupRemoteVideo(new VideoCanvas(surfaceV, VideoCanvas.RENDER_MODE_HIDDEN, uid));

                if (useDefaultLayout) {
                    log.debug("doRenderRemoteUi LAYOUT_TYPE_DEFAULT " + (uid & 0xFFFFFFFFL));
                    switchToDefaultVideoView();
                } else {
                    int bigBgUid = mSmallVideoViewAdapter == null ? uid : mSmallVideoViewAdapter.getExceptedUid();
                    log.debug("doRenderRemoteUi LAYOUT_TYPE_SMALL " + (uid & 0xFFFFFFFFL) + " " + (bigBgUid & 0xFFFFFFFFL));
                    switchToSmallVideoView(bigBgUid);
                }
//                notifyMessageChanged(new Message(new User(0, null), "video from user " + (uid & 0xFFFFFFFFL) + " decoded"));
            }
        });
    }

    @Override
    public void onJoinChannelSuccess(String channel, final int uid, int elapsed) {
        log.debug("onJoinChannelSuccess " + channel + " " + (uid & 0xFFFFFFFFL) + " " + elapsed);
    }

    @Override
    public void onUserOffline(int uid, int reason) {
        log.debug("onUserOffline " + (uid & 0xFFFFFFFFL) + " " + reason);
        doRemoveRemoteUi(uid);
    }

    @Override
    public void onUserJoined(int uid, int elapsed) {

    }

    @Override
    public void onLastmileQuality(int quality) {

    }

    @Override
    public void onLastmileProbeResult(IRtcEngineEventHandler.LastmileProbeResult result) {

    }

    @Override
    public void onLocalVideoStats(IRtcEngineEventHandler.LocalVideoStats stats) {

    }

    @Override
    public void onRtcStats(IRtcEngineEventHandler.RtcStats stats) {

    }

    @Override
    public void onNetworkQuality(int uid, int txQuality, int rxQuality) {

    }

    @Override
    public void onRemoteVideoStats(IRtcEngineEventHandler.RemoteVideoStats stats) {

    }

    @Override
    public void onRemoteAudioStats(IRtcEngineEventHandler.RemoteAudioStats stats) {

    }

    @Override
    public void activeSpeaker(int uId) {
        Toast.makeText(this, "active"+uId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onExtraCallback(final int type, final Object... data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                doHandleExtraCallback(type, data);
            }
        });
    }

    @Override
    public void onRemoteVideoStateChanged(int uid, int state, int reason, int elapsed) {

        if (state == 0) {

        } else {
            doRenderRemoteUi(uid);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("onRemoteVide", "" + uid + "  " + state + "  ");

            }
        });

    }

    private void doHandleExtraCallback(int type, Object... data) {
        int peerUid;
        boolean muted;

        switch (type) {
            case AGEventHandler.EVENT_TYPE_ON_USER_AUDIO_MUTED:
                peerUid = (Integer) data[0];
                muted = (boolean) data[1];

                if (mLayoutType == LAYOUT_TYPE_DEFAULT) {
                    HashMap<Integer, Integer> status = new HashMap<>();
                    status.put(peerUid, muted ? UserStatusData.AUDIO_MUTED : UserStatusData.DEFAULT_STATUS);
//                    mGridVideoViewContainer.notifyUiChanged(mUidsList, config().mUid, status, null);
                }

                break;

            case AGEventHandler.EVENT_TYPE_ON_USER_VIDEO_MUTED:
                peerUid = (Integer) data[0];
                muted = (boolean) data[1];

//                doHideTargetView(peerUid, muted);

                break;

            case AGEventHandler.EVENT_TYPE_ON_USER_VIDEO_STATS:
                IRtcEngineEventHandler.RemoteVideoStats stats = (IRtcEngineEventHandler.RemoteVideoStats) data[0];

                if (Constant.SHOW_VIDEO_INFO) {
                    if (mLayoutType == LAYOUT_TYPE_DEFAULT) {
//                        mGridVideoViewContainer.addVideoInfo(stats.uid, new com.expert.nicolive.Agora.heartview.VideoInfoData(stats.width, stats.height, stats.delay, stats.rendererOutputFrameRate, stats.receivedBitrate));
                        int uid = config().mUid;
                        int profileIndex = getVideoEncResolutionIndex();
                        String resolution = getResources().getStringArray(R.array.string_array_resolutions)[profileIndex];
                        String fps = getResources().getStringArray(R.array.string_array_frame_rate)[profileIndex];

                        String[] rwh = resolution.split("x");
                        int width = Integer.valueOf(rwh[0]);
                        int height = Integer.valueOf(rwh[1]);

//                        mGridVideoViewContainer.addVideoInfo(uid, new VideoInfoData(width > height ? width : height,
//                                width > height ? height : width,
//                                0, Integer.valueOf(fps), Integer.valueOf(0)));
                    }
                } else {
//                    mGridVideoViewContainer.cleanVideoInfo();
                }

                break;

            case AGEventHandler.EVENT_TYPE_ON_SPEAKER_STATS:
                IRtcEngineEventHandler.AudioVolumeInfo[] infos = (IRtcEngineEventHandler.AudioVolumeInfo[]) data[0];

                if (infos.length == 1 && infos[0].uid == 0) { // local guy, ignore it
                    break;
                }

                if (mLayoutType == LAYOUT_TYPE_DEFAULT) {
                    HashMap<Integer, Integer> volume = new HashMap<>();

                    for (IRtcEngineEventHandler.AudioVolumeInfo each : infos) {
                        peerUid = each.uid;
                        int peerVolume = each.volume;

                        if (peerUid == 0) {
                            continue;
                        }
                        volume.put(peerUid, peerVolume);
                    }
//                    mGridVideoViewContainer.notifyUiChanged(mUidsList, config().mUid, null, volume);

                    ///here notify the data

                }

                break;

            case AGEventHandler.EVENT_TYPE_ON_APP_ERROR:
                int subType = (int) data[0];

                if (subType == ConstantApp.AppError.NO_CONNECTION_ERROR) {
                    String msg = getString(R.string.msg_connection_error);
//                    notifyMessageChanged(new Message(new User(0, null), msg));
                    showLongToast(msg);
                }

                break;

            case AGEventHandler.EVENT_TYPE_ON_DATA_CHANNEL_MSG:

                peerUid = (Integer) data[0];
                final byte[] content = (byte[]) data[1];
//                notifyMessageChanged(new Message(new User(peerUid, String.valueOf(peerUid)), new String(content)));

                break;

            case AGEventHandler.EVENT_TYPE_ON_AGORA_MEDIA_ERROR: {
                int error = (int) data[0];
                String description = (String) data[1];

//                notifyMessageChanged(new Message(new User(0, null), error + " " + description));

                break;
            }

            case AGEventHandler.EVENT_TYPE_ON_AUDIO_ROUTE_CHANGED:
                notifyHeadsetPlugged((int) data[0]);

                break;

        }
    }

    private void requestRemoteStreamType(final int currentHostCount) {
        log.debug("requestRemoteStreamType " + currentHostCount);
    }

    private void doRemoveRemoteUi(final int uid) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }

                Object target = mUidsList.remove(uid);
                if (target == null) {
                    return;
                }

                int bigBgUid = -1;
                if (mSmallVideoViewAdapter != null) {
                    bigBgUid = mSmallVideoViewAdapter.getExceptedUid();
                }

                log.debug("doRemoveRemoteUi " + (uid & 0xFFFFFFFFL) + " " + (bigBgUid & 0xFFFFFFFFL) + " " + mLayoutType);

                if (mLayoutType == LAYOUT_TYPE_DEFAULT || uid == bigBgUid) {
                    switchToDefaultVideoView();
                } else {
                    switchToSmallVideoView(bigBgUid);
                }

//                notifyMessageChanged(new Message(new User(0, null), "user " + (uid & 0xFFFFFFFFL) + " left"));
            }
        });
    }

    private void switchToDefaultVideoView() {
        if (mSmallVideoViewDock != null) {
            mSmallVideoViewDock.setVisibility(View.GONE);
        }
//        mGridVideoViewContainer.initViewContainer(this, config().mUid, mUidsList, mIsLandscape, mGridVideoViewContainer, RecyclerView.VERTICAL);

        mLayoutType = LAYOUT_TYPE_DEFAULT;
        boolean setRemoteUserPriorityFlag = false;
        int sizeLimit = mUidsList.size();
        if (sizeLimit > ConstantApp.MAX_PEER_COUNT + 1) {
            sizeLimit = ConstantApp.MAX_PEER_COUNT + 1;
        }
//        for (int i = 0; i < sizeLimit; i++) {
//            int uid = mGridVideoViewContainer.getItem(i).mUid;
//            if (config().mUid != uid) {
//                if (!setRemoteUserPriorityFlag) {
//                    setRemoteUserPriorityFlag = true;
//                    rtcEngine().setRemoteUserPriority(uid, Constants.USER_PRIORITY_HIGH);
//                    log.debug("setRemoteUserPriority USER_PRIORITY_HIGH " + mUidsList.size() + " " + (uid & 0xFFFFFFFFL));
//                } else {
//                    rtcEngine().setRemoteUserPriority(uid, Constants.USER_PRIORITY_NORMAL);
//                    log.debug("setRemoteUserPriority USER_PRIORITY_NORANL " + mUidsList.size() + " " + (uid & 0xFFFFFFFFL));
//                }
//            }
//        }
    }

    private void switchToSmallVideoView(int bigBgUid) {
        HashMap<Integer, SurfaceView> slice = new HashMap<>(1);
        slice.put(bigBgUid, mUidsList.get(bigBgUid));
        Iterator<SurfaceView> iterator = mUidsList.values().iterator();
        while (iterator.hasNext()) {
            SurfaceView s = iterator.next();
            s.setZOrderOnTop(true);
            s.setZOrderMediaOverlay(true);
        }

        mUidsList.get(bigBgUid).setZOrderOnTop(false);
        mUidsList.get(bigBgUid).setZOrderMediaOverlay(false);

//        mGridVideoViewContainer.initViewContainer(this, bigBgUid, slice, mIsLandscape, mGridVideoViewContainer, RecyclerView.VERTICAL);

        bindToSmallVideoView(bigBgUid);

        mLayoutType = LAYOUT_TYPE_SMALL;

        requestRemoteStreamType(mUidsList.size());
    }

    private void bindToSmallVideoView(int exceptUid) {
        if (mSmallVideoViewDock == null) {
            ViewStub stub = findViewById(R.id.small_video_view_dock);
            mSmallVideoViewDock = (RelativeLayout) stub.inflate();
        }

        boolean twoWayVideoCall = mUidsList.size() == 2;

        RecyclerView recycler = findViewById(R.id.small_video_view_container);

        boolean create = false;

        if (mSmallVideoViewAdapter == null) {
            create = true;
//            mSmallVideoViewAdapter = new SmallVideoViewAdapter(this, config().mUid, exceptUid, mUidsList, binding.gridVideoViewContainer);
            mSmallVideoViewAdapter.setHasStableIds(true);
        }
        recycler.setHasFixedSize(true);

        log.debug("bindToSmallVideoView " + twoWayVideoCall + " " + (exceptUid & 0xFFFFFFFFL));

        if (twoWayVideoCall) {
            recycler.setLayoutManager(new RtlLinearLayoutManager(getApplicationContext(), RtlLinearLayoutManager.HORIZONTAL, false));
        } else {
            recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        }
        recycler.addItemDecoration(new SmallVideoViewDecoration());
        recycler.setAdapter(mSmallVideoViewAdapter);
        recycler.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

            @Override
            public void onItemDoubleClick(View view, int position) {
                //onSmallVideoViewDoubleClicked(view, position);
            }
        }));

        recycler.setDrawingCacheEnabled(true);
        recycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);

        if (!create) {
            mSmallVideoViewAdapter.setLocalUid(config().mUid);
            mSmallVideoViewAdapter.notifyUiChanged(mUidsList, exceptUid, null, null);
        }
        for (Integer tempUid : mUidsList.keySet()) {
            if (config().mUid != tempUid) {
                if (tempUid == exceptUid) {
                    rtcEngine().setRemoteUserPriority(tempUid, Constants.USER_PRIORITY_HIGH);
                    log.debug("setRemoteUserPriority USER_PRIORITY_HIGH " + mUidsList.size() + " " + (tempUid & 0xFFFFFFFFL));
                } else {
                    rtcEngine().setRemoteUserPriority(tempUid, Constants.USER_PRIORITY_NORMAL);
                    log.debug("setRemoteUserPriority USER_PRIORITY_NORANL " + mUidsList.size() + " " + (tempUid & 0xFFFFFFFFL));
                }
            }
        }
        recycler.setVisibility(View.VISIBLE);
        mSmallVideoViewDock.setVisibility(View.VISIBLE);
    }

    public void notifyHeadsetPlugged(final int routing) {
        log.info("notifyHeadsetPlugged " + routing + " " + mVideoMuted);

        mAudioRouting = routing;

        ImageView iv = findViewById(R.id.switch_speaker_id);


//        if (mAudioRouting == Constants.AUDIO_ROUTE_SPEAKERPHONE) {
//            iv.setImageResource(R.drawable.btn_speaker);
//        } else {
//            iv.setImageResource(R.drawable.btn_speaker_off);
//        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mIsLandscape = newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (mLayoutType == LAYOUT_TYPE_DEFAULT) {
            switchToDefaultVideoView();
        } else if (mSmallVideoViewAdapter != null) {
            switchToSmallVideoView(mSmallVideoViewAdapter.getExceptedUid());
        }
    }

    @Override
    public void onBackPressed() {
        final Dialog dialog = new Dialog(CallActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.back_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView minimize = dialog.findViewById(R.id.minimize);
        ImageView exit = dialog.findViewById(R.id.exit);

        minimize.setOnClickListener(v -> {
            dialog.dismiss();
            setPIPScreen();
        });

        exit.setOnClickListener(v -> {
            if (status.equals("1")) {
                startActivity(new Intent(CallActivity.this, HomeMainActivity.class));
                finish();

            } else if (status.equals("2")) {

                FirebaseHelper.removeMessages(getChannelName);
                startActivity(new Intent(CallActivity.this, EndLiveActivity.class));
                finish();

            }
        });

        dialog.show();

    }
    private void setData() {

        new MvvmViewModelClass().myWallpapers(CommonUtils.getUserId()).observe(this, new Observer<MyWallPaper>() {
            @Override
            public void onChanged(MyWallPaper myWallPaper) {
                if (myWallPaper.getStatus()==1){

                    for (int i = 0; i <myWallPaper.getDetails().size() ; i++) {

                        if (myWallPaper.getDetails().get(i).getWallpaperExpire().equalsIgnoreCase("0")){
                            checkAudioBack = 1;
                            break;
                        }

                    }

                    if (checkAudioBack!=1){
                        FirebaseHelper.removeBack(getChannelName);
                    }

                }
            }
        });
    }
    private void setPIPScreen() {
        Display d = getWindowManager().getDefaultDisplay();
        Point p = new Point();
        d.getSize(p);
        int width = p.x;
        int height = p.y;

        Rational ratio = new Rational(width, height);
        PictureInPictureParams.Builder pip_Builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pip_Builder = new PictureInPictureParams.Builder();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pip_Builder.setAspectRatio(ratio).build();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            enterPictureInPictureMode(pip_Builder.build());
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (isInPictureInPictureMode) {
            binding.rlHostA.setVisibility(View.VISIBLE);
            binding.rlChair.setVisibility(View.GONE);
            binding.rlWholeLayout.setVisibility(View.GONE);
        } else {
            Log.i("onPictureInP", "show full screen  " + newConfig.getLayoutDirection());
            startActivity(new Intent(this, getClass())
                    .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            binding.rlHostA.setVisibility(View.GONE);
            binding.rlChair.setVisibility(View.VISIBLE);
            binding.rlWholeLayout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onUserLeaveHint() {


        super.onUserLeaveHint();
        setPIPScreen();
    }

    private void askForSystemOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, DRAW_OVER_OTHER_APP_PERMISSION);
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void mentionName(String name) {


        if (liveStatus.equalsIgnoreCase("host")) {

            binding.editTextComment.setText(name + " ");
            binding.llMessegeEdittext.setVisibility(View.VISIBLE);
            binding.commentIcon.setVisibility(View.GONE);
            binding.llMessageShareGift.setVisibility(View.GONE);

        } else {
            binding.llMessegeEdittextHostLive.setVisibility(View.VISIBLE);
            binding.commentIconHostLive.setVisibility(View.GONE);
            binding.llMessageShareGiftHostLive.setVisibility(View.GONE);
            binding.editTextCommentHostLive.setText(name + " ");

        }

//        openDialogForSendMessage(name);
    }

    private String adminStatuscheck = "";
    ValueEventListener valueEventListenerAdminTwo = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists()) {

//                String userId = String.valueOf(snapshot.getKey());
//                String status = String.valueOf(snapshot.getValue());
                android.util.Log.i("Agora Live Ban userId: ", snapshot.toString());
//                Log.i("Agora Live Ban userId status: ", status);

                adminStatuscheck = snapshot.child("subAdminStatus").getValue(String.class);

//
//                if (adminStatuscheck==null){
//                    Toast.makeText(requireContext(), "sdsd", Toast.LENGTH_SHORT).show();
//
//                }else {
//                    Toast.makeText(requireContext(), "wefsd", Toast.LENGTH_SHORT).show();
//
//          }

//                                GetBanUserpojo getBanUserpojo = snapshot.getValue(GetBanUserpojo.class);
//                                Toast.makeText(requireContext(), ""+getBanUserpojo.getUserId(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(requireContext(), ""+otherUserId, Toast.LENGTH_SHORT).show();
//                                if (Objects.requireNonNull(getBanUserpojo).getUserId().equalsIgnoreCase(otherUserId)) {
//
//
////                        onBackPressed();
//                                        if (getBanUserpojo.getKickStatus().equals("0")){
//
//                                                ban="0";
//
//
//                                                }
//                                        }
//                                        }else {
            } else {
                adminStatuscheck = "";
            }
//
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    };


    @SuppressLint("SetTextI18n")
    private void KickOutDialog(String status) {
        LayoutInflater layoutInflater = LayoutInflater.from(CallActivity.this);
        final View confirmdailog = layoutInflater.inflate(R.layout.dialog_kickout, null);
        final android.app.AlertDialog dailogbox = new android.app.AlertDialog.Builder(CallActivity.this).create();

//        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dailogbox.setView(confirmdailog);
        dailogbox.setCanceledOnTouchOutside(false);

        TextView textView = confirmdailog.findViewById(R.id.tv_status);
        if (status.equalsIgnoreCase("0")) {
            textView.setText("You have been kicked out by this user .");
        } else if (status.equalsIgnoreCase("1")) {
            textView.setText("You have been  and blocked by this user.");
        }

        confirmdailog.findViewById(R.id.okKickOutText).setOnClickListener(v -> {
            finish();
            dailogbox.dismiss();
        });

        if (!(CallActivity.this).isFinishing()) {
//            dailogbox.show();
            //show dialog
        }


        List<ModelPrivateMessagePlayer> list = new ArrayList<>();
        App.getSingleton().setPrivateMessagePlayers(list);
//        ref.child(getChannelName).


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
    private void setcoins(ModelSendGift modelSendGift) {
        try {
            Long priceOfGift = Long.parseLong(String.valueOf(modelSendGift.getGiftPrice()));
            Long oldCoins = Long.parseLong(tv_total_coins.getText().toString());
            Long updatedPrice = oldCoins + priceOfGift;
            tv_total_coins.setText(String.valueOf(updatedPrice));
            String liveStars = modelSendGift.getLiveStars();
            String liveLevel = modelSendGift.getLiveLevel();


            long getOldStar = Long.parseLong(binding.tvStars.getText().toString());

            startCount = startCount + priceOfGift;


            if (startCount < 10000) {
                binding.tvStars.setText("0");
            } else if (startCount < 50000) {
                binding.tvStars.setText("1");
            } else if (startCount < 200000) {
                binding.tvStars.setText("2");
            } else if (startCount < 1000000) {
                binding.tvStars.setText("Rising");
            } else if (startCount < 2000000) {
                binding.tvStars.setText("Big");
            } else {
                binding.tvStars.setText("Super");
            }
            long starCountintz = Long.parseLong(binding.tvStars.getText().toString());

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

    @SuppressLint("SetTextI18n")
    private void showGiftImage(ModelSendGift modelSendGift, String key) {

        Toast.makeText(this, ""+modelSendGift, Toast.LENGTH_SHORT).show();

        binding.lottieGift.setVisibility(View.GONE);
        try {
            Glide.with(this).load(modelSendGift.getGiftImage()).into(binding.lottieGift);
        } catch (Exception e) {

        }
//        binding.lottieGift.setAnimationFromUrl(giftModel.getGiftPath());
        binding.lottieGift.setVisibility(View.VISIBLE);

//        binding.lottieGift.playAnimation();

        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                Log.i("onTick: ", (l / 1000) + "");
            }

            @Override
            public void onFinish() {
                binding.lottieGift.setVisibility(View.GONE);
                FirebaseHelper.deleteGiftAfterRecevingFromFireBase(key, getChannelName);
            }
        };
        countDownTimer.start();
    }


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
                            Toast.makeText(CallActivity.this, "You have been kicked out", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(AgoraPkPlayerActivity.this, HomeLiveActivity.class));
                            finish();
                        });
                    } else if (getBanUserpojo.getKickStatus().equals("1")) {
                        KickOutDialog(getBanUserpojo.getKickStatus());
                        runOnUiThread(() -> {
                            Toast.makeText(CallActivity.this, "You have been Ban", Toast.LENGTH_SHORT).show();
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


    private String muteLive = "0";
    ValueEventListener getMutevalueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {


                muteLive = (String) snapshot.getValue();
//                if (muteLive.equals("0")) {
//
//
//                } else {
//
//                    comment_icon.setVisibility(View.VISIBLE);
//                    llMessageEdittext.setVisibility(View.GONE);
//                    ll_message_share_gift.setVisibility(View.VISIBLE);
//
////                    Toast.makeText(activity, "You are muted from the user", Toast.LENGTH_SHORT).show();
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
//////                            Toast.makeText(activity, "You have been muted by user", Toast.LENGTH_SHORT).show();
////                        }
////                    });
//                }

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    void getReverseSheet() {

        if (!otherUserId.equalsIgnoreCase(CommonUtils.getUserId())) {
            ref.child("reservedSheet").child(otherUserId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            reservedSheet.add(snapshot1.getValue(Integer.class));
                            Log.d("onDataChange", "onDataChange: " + reservedSheet);

                        }

                    } else {
                        reservedSheet.clear();
                    }


                    multiLiveVideoAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }


    private void publicBulletMessage(String text) {
        new LiveMvvm().publicBulletMsg(this, CommonUtils.getUserId(), messageType).observe(this, map -> {
            if (Objects.requireNonNull(map.get("success")).toString().equalsIgnoreCase("1")) {
                if (messageType.equalsIgnoreCase("public")) {
                    ModelBulletMessage modelBulletMessage = new ModelBulletMessage();
                    modelBulletMessage.setText(text);
                    modelBulletMessage.setUsername(name);
                    FirebaseHelper.sendPublicBulletMessage(getChannelName, modelBulletMessage);
                    messageType = "";
                } else if (messageType.equalsIgnoreCase("bullet")) {
                    ModelBulletMain modelBulletMain = new ModelBulletMain();
                    modelBulletMain.setUsername(name);
                    modelBulletMain.setTextmesssage(text);
                    FirebaseHelper.sendBulletMessage(getChannelName, modelBulletMain);
                    messageType = "";
                } else {

                }
            } else {
                messageType = "";
                Toast.makeText(this, map.get("message").toString(), Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(AgoraPkPlayerActivity.this, PurchaseCoinActivity.class));
            }
        });
    }

    private void getBackGroundImageTheme(){

        FirebaseHelper.getAudioBackGround(getChannelName,otherUserId,valueEventListenerBack);

    }

    ValueEventListener valueEventListenerBack = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()){
                String imageBack = snapshot.getValue(String.class);
                setBackground(imageBack);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}
