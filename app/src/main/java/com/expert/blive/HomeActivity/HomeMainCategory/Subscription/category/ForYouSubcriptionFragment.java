package com.expert.blive.HomeActivity.HomeMainCategory.Subscription.category;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.expert.blive.RealPathUtil;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.CommentDetails;
import com.expert.blive.Adapter.CommentRoot;
import com.expert.blive.Adapter.CommentsAdapter;
import com.expert.blive.Adapter.FollowingSubscriptionAdapter;
import com.expert.blive.Adapter.ShareAdapter;
import com.expert.blive.HomeActivity.HomeMainCategory.Live.LiveCategory.PostLiveFragment;
import com.expert.blive.ModelClass.ChatInformation.ChatInformation;
import com.expert.blive.ModelClass.FollowingDataModel;
import com.expert.blive.ModelClass.ShowVideoClass;
import com.expert.blive.ModelClass.ShowVideoDetail;
import com.expert.blive.ModelClass.Video.ShareVideoRoot;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.CommonUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForYouSubcriptionFragment extends Fragment implements FollowingSubscriptionAdapter.FollowingVideoCallback , CommentsAdapter.Callback {

    private RecyclerView following_subs_RV;
    private FollowingSubscriptionAdapter followingSubscriptionAdapter;
    private LinearLayout upload_video;
    private String stringPhotoPath;
    List<ShowVideoDetail> details;
    private int k = 0;
    int currentItems, totalItem, scrollOutItems, page = 1;
    boolean isScrolling;
    private LinearLayoutManager layoutManager;
    int currentPage = -1;
    ImageView plus;
    TextView reelsNotFound ;
    SimpleExoPlayer privious_player;
    boolean is_visible_to_user = true;
    boolean is_user_stop_video = false;
    SwipeRefreshLayout pullToRefresh;
    private MvvmViewModelClass viewModelClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_for_you_subcription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        findIds(view);
        getVideo();
        swipeDown();

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(following_subs_RV);


        followingSubscriptionAdapter = new FollowingSubscriptionAdapter(new ArrayList<>(), ForYouSubcriptionFragment.this, requireContext());
        following_subs_RV.setAdapter(followingSubscriptionAdapter);
        uploadVideo();
    }

    private void swipeDown() {

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getVideo();

                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void uploadVideo() {

        upload_video.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                int permission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
//                if (permission != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
//                } else {
                    Intent intent;
                    intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("video/*");
                    startActivityForResult(intent, 2);

//                }

            }
        });
    }

    private void getVideo() {

        Log.d(TAG, "getVideo: " + CommonUtils.getUserId());

        viewModelClass.showVideoClassLiveData(requireActivity(), "", CommonUtils.getUserId()).observe(requireActivity(), new Observer<ShowVideoClass>() {

            @Override
            public void onChanged(ShowVideoClass showVideoClass) {

                if (showVideoClass !=null){
                    if (showVideoClass.getSuccess().equalsIgnoreCase("1")) {
                        Log.d(TAG, "onChanged: " + showVideoClass.getDetails().size());

                        details = showVideoClass.getDetails();
                        followingSubscriptionAdapter.loadData(details);
                        followingSubscriptionAdapter.notifyDataSetChanged();

                        setRecycler();
                        reelsNotFound.setVisibility(View.GONE);
                    }else if(showVideoClass.getSuccess().equalsIgnoreCase("0")) {
                        following_subs_RV.setVisibility(View.GONE);
                        reelsNotFound.setVisibility(View.VISIBLE);
                    } else {
                        reelsNotFound.setVisibility(View.GONE);
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void setRecycler() {

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        following_subs_RV.setItemAnimator(new DefaultItemAnimator());

        following_subs_RV.setLayoutManager(layoutManager);

        // this is the scroll listener of recycler view which will tell the current item number
        following_subs_RV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);
                //here we find the current item number

                currentItems = layoutManager.getChildCount();

                totalItem = layoutManager.getItemCount();
                scrollOutItems = layoutManager.findFirstVisibleItemPosition();

                final int scrollOffset = recyclerView.computeVerticalScrollOffset();
                final int height = recyclerView.getHeight();
                int page_no = scrollOffset / height;
                if (page_no != currentPage) {
                    currentPage = page_no;
                    App.getSingleton().setSetPosition(currentPage);
                    Release_Privious_Player();
                    Toast.makeText(requireContext(), "aaaaa"+App.getSingleton().getSetPosition(), Toast.LENGTH_SHORT).show();
                    Set_Player(App.getSingleton().getSetPosition());
//                    Log.i("scroolll", "current:" + currentPage);
//                    Log.i("scroolll", "page:" + page_no);
                    // Log.i("scroolll","scrolled:"+scrollOutItems);

                }

                if (page_no + 2 == details.size()) {
//                    android.util.Log.d(TAG, "onScrolled: "+detailArrayList.size());
                    //data fetch
                    isScrolling = false;
                    page = page + 2;
                    //callApiVideoList(String.valueOf(page));
//                    Log.i("scroolll", "pageNewList:" + String.valueOf(page));
                }
            }
        });
//        callApiVideoList(String.valueOf(page));
    }

    private void Set_Player(int currentPage) {

        try {
//            viewVideo.viewVideoAPi(list.get(currentPage).getId());
            final ShowVideoDetail item = details.get(currentPage);

            viewCount(item);

            DefaultTrackSelector trackSelector = new DefaultTrackSelector();
//            HttpProxyCacheServer proxy = App.getProxy(requireContext());
//            String proxyUrl = proxy.getProxyUrl((item.getVideoPath()));
            final SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(requireActivity(), trackSelector);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(requireActivity(),
                    Util.getUserAgent(requireActivity(), "StayOnn"));
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(item.getVideoPath()));
//        Log.d("resp", proxyUrl);

            player.prepare(videoSource);
            player.setRepeatMode(Player.REPEAT_MODE_ALL);
            player.addListener(new Player.EventListener() {

                @Override
                public void onTimelineChanged(Timeline timeline, int reason) {

                }

                @Override
                public void onTimelineChanged(Timeline timeline, @Nullable @org.jetbrains.annotations.Nullable Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                }

                @Override
                public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {

                }

                @Override
                public void onIsPlayingChanged(boolean isPlaying) {

                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });

            View layout = layoutManager.findViewByPosition(currentPage);

            final PlayerView playerView = layout.findViewById(R.id.imgUserVideoImage);
            playerView.setPlayer(player);

            player.setPlayWhenReady(true);
            privious_player = player;
            player.addVideoListener(new VideoListener() {

                @Override
                public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

                    if (pixelWidthHeightRatio > 1.1 || height < width) {
                        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    } else {
                        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    }
                }

                @Override
                public void onRenderedFirstFrame() {

                }
            });
            final RelativeLayout mainlayout = layout.findViewById(R.id.rl_main_video);
            playerView.setOnTouchListener(new View.OnTouchListener() {

                private GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                        super.onFling(e1, e2, velocityX, velocityY);
                        float deltaX = e1.getX() - e2.getX();
                        float deltaXAbs = Math.abs(deltaX);
                        if ((deltaXAbs > 100) && (deltaXAbs < 1000)) {
                            if (deltaX > 0) {
                                Log.d(TAG, "onFling: ");
                            }
                        }
                        return true;
                    }

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {

                        super.onSingleTapUp(e);
                        if (!player.getPlayWhenReady()) {
                            is_user_stop_video = false;
                            privious_player.setPlayWhenReady(true);
                        } else {
                            is_user_stop_video = true;
                            privious_player.setPlayWhenReady(false);
                        }
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {

                        super.onLongPress(e);
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {

                        if (!player.getPlayWhenReady()) {
                            is_user_stop_video = false;
                            privious_player.setPlayWhenReady(true);
                        }

//                        if (App.getSharedpref().isLogin(getActivity())) {
//                            Show_heart_on_DoubleTap(item, mainlayout, e);
//                            hitApiLike(list.get(currentPage).getId(), list.get(currentPage).getUserId());
//
//                        } else {
//                            privious_player.setPlayWhenReady(false);
////                            openDialog();
////                            Toast.makeText(getActivity(), "Please Login into video", Toast.LENGTH_SHORT).show();
//                        }
                        return super.onDoubleTap(e);
                    }
                });

                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    gestureDetector.onTouchEvent(event);
                    return true;
                }
            });
            playerView.setVisibility(View.VISIBLE);

        } catch (Exception e) {
            Log.d(TAG, "Set_Player: " + e.getMessage());
        }
    }

    private void viewCount(ShowVideoDetail item) {

        String videoId = item.getId();

        viewModelClass.viewVideo(CommonUtils.getUserId(),videoId).observe(requireActivity(), new Observer<ShareVideoRoot>() {

            @Override
            public void onChanged(ShareVideoRoot shareVideoRoot) {
                if (shareVideoRoot !=null){
                    if (shareVideoRoot.success){
//                        Toast.makeText(requireContext(), ""+shareVideoRoot.getMessage(), Toast.LENGTH_SHORT).show();
                    }else {
//                        Toast.makeText(requireContext(), ""+shareVideoRoot.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void Release_Privious_Player() {

        if (privious_player != null) {

            privious_player.removeListener(new Player.EventListener() {

                @Override
                public void onTimelineChanged(Timeline timeline, int reason) {

                }

                @Override
                public void onTimelineChanged(Timeline timeline, @Nullable @org.jetbrains.annotations.Nullable Object manifest, int reason) {

                }

                @Override
                public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

                }

                @Override
                public void onLoadingChanged(boolean isLoading) {

                }

                @Override
                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

                }

                @Override
                public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {

                }

                @Override
                public void onIsPlayingChanged(boolean isPlaying) {

                }

                @Override
                public void onRepeatModeChanged(int repeatMode) {

                }

                @Override
                public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

                }

                @Override
                public void onPlayerError(ExoPlaybackException error) {

                }

                @Override
                public void onPositionDiscontinuity(int reason) {

                }

                @Override
                public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

                }

                @Override
                public void onSeekProcessed() {

                }
            });
            privious_player.release();
        }


    }

    @Override
    public void onStop() {

        super.onStop();
        if (privious_player != null) {
            privious_player.setPlayWhenReady(false);
            privious_player.getPlaybackState();
            Release_Privious_Player();
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            Toast.makeText(requireContext(), "vefvefd", Toast.LENGTH_SHORT).show();
        } else {
            getVideo();
        }
    }
    @Override
    public void onResume() {

        super.onResume();
        Toast.makeText(requireContext(), "b"+App.getSingleton().getSetPosition(), Toast.LENGTH_SHORT).show();

        Set_Player(App.getSingleton().getSetPosition());

        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
//        if ((privious_player != null && (is_visible_to_user && !is_user_stop_video))) {
//            privious_player.setPlayWhenReady(true);
//        } else {
//            setRecycler();
//        }
    }

    @Override
    public void onPause() {

        super.onPause();

        if (privious_player != null) {
            privious_player.setPlayWhenReady(false);
            privious_player.getPlaybackState();
            Release_Privious_Player();
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();

        Release_Privious_Player();
    }

    private void findIds(View view) {

        following_subs_RV = view.findViewById(R.id.following_subs_RV);
        upload_video = view.findViewById(R.id.upload_video);
        reelsNotFound = view.findViewById(R.id.reels_not_found);
        pullToRefresh = view.findViewById(R.id.pullToRefresh);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            assert data != null;
            Uri imageUriPhotos = data.getData();

            stringPhotoPath = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos);

            Fragment fragment = new PostLiveFragment();
            Bundle bundle = new Bundle();
            bundle.putString("path", stringPhotoPath);
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).addToBackStack(null).commit();

//            circular_image.setImageURI(imageUriPhotos);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {

            Toast.makeText(requireContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(requireContext(), "Image Uploading Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void call_video(int position) {

    }

    @Override
    public void videoLike(ShowVideoDetail showVideoDetail,  ImageView likeUnlike, TextView textView) {

        String VideoId = showVideoDetail.getId();
        viewModelClass.likeUnlike(VideoId, CommonUtils.getUserId()).observe(requireActivity(), videoRoot -> {
            if (videoRoot != null) {

                if (videoRoot.getStatus() == 1) {

                    if(videoRoot.isLikeStatus()) {
                        likeUnlike.setColorFilter(Color.RED);
                        textView.setText(videoRoot.getLikeStatusChange());
                        Toast.makeText(requireContext(), "  " + videoRoot.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else if (videoRoot.getStatus()==0){
                    likeUnlike.setColorFilter(Color.WHITE);
                    textView.setText(videoRoot.getLikeStatusChange());
                    Toast.makeText(requireContext(), "  " + videoRoot.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void commentOnVideo(ShowVideoDetail detail) {

        String videoId = detail.getId();

        RecyclerView rvComments;
        EditText comment;
        ImageView sendButton;

        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.comment_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        rvComments = dialog.findViewById(R.id.rvComments);
        comment = dialog.findViewById(R.id.edtComment);
        sendButton = dialog.findViewById(R.id.sendImg);
        dialog.show();

        getComments(videoId, rvComments);

        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                viewModelClass.videoRootLiveData(CommonUtils.getUserId(), videoId, comment.getText().toString()).observe(requireActivity(), response -> {
                    if (response != null) {
                        if (response.getSuccess().equals("1")) {
                            getComments(videoId, rvComments);
                            comment.setText(null);
//                            Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void shareVideo(ShowVideoDetail detail) {


        RecyclerView rvComment;

        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.sharelist);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        rvComment = dialog.findViewById(R.id.rvComments);

        getlist(rvComment,detail);

    }

    private void getlist(RecyclerView rvComment, ShowVideoDetail details) {
        viewModelClass.getFollowing(CommonUtils.getUserId()).observe(requireActivity(), new Observer<FollowingDataModel>() {

            @Override
            public void onChanged(FollowingDataModel followingDataModel) {
                if (followingDataModel !=null){
                    if (followingDataModel.getStatus().equalsIgnoreCase("1")){
                        rvComment.setAdapter(new ShareAdapter(followingDataModel.getDetails(), requireContext(), new ShareAdapter.Callback() {

                            @Override
                            public void sendVideo(FollowingDataModel.Detail detail,TextView textView) {

                                Toast.makeText(requireContext(), "sendVideo", Toast.LENGTH_SHORT).show();

                                if(followingDataModel.getStatus().equalsIgnoreCase("1")) {

                                    sendMessage(detail, details);
                                    textView.setText("Undo");
                                }else if (followingDataModel.getStatus().equalsIgnoreCase("0")){
                                    textView.setText("Send");
                                }
                            }


                        }));
                    }
                }
            }
        });
    }


    private void sendMessage(FollowingDataModel.Detail detail, ShowVideoDetail details) {

        String otherUserId = detail.getId();

        String thumbnail = details.getThumbnail();

        String videoId = details.getId();

        Toast.makeText(requireContext(), "otherUseId "+otherUserId, Toast.LENGTH_SHORT).show();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("chat-Panel@" + otherUserId + CommonUtils.getUserId());

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String random_key_generator = databaseReference.push().getKey();

                        databaseReference.child(random_key_generator).setValue(new ChatInformation(videoId, CommonUtils.getUserId(),
                                random_key_generator, getDate(), getTime(), CommonUtils.getName(), CommonUtils.getImage(), "1",thumbnail)).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(requireContext(), "seccessfully added1", Toast.LENGTH_SHORT).show();
                                DatabaseReference databaseReference;

                                if (!otherUserId.equals(CommonUtils.getUserId())) {

                                    databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("last-otherUserId").child(otherUserId).child(CommonUtils.getUserId());
                                } else {
                                    databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("last-otherUserId").child(CommonUtils.getUserId()).child(otherUserId);

                                }
                                saveLastMessage(databaseReference, new ChatInformation(videoId, CommonUtils.getUserId(),
                                        random_key_generator, getDate(), getTime(), CommonUtils.getName(), CommonUtils.getImage(), "1",thumbnail));

                            }
                        });


                } else {


                    Log.d(ContentValues.TAG, "onDataChange: else call");

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference("chat-Panel@" + CommonUtils.getUserId() + otherUserId);

                    String random_key_generator = databaseReference.push().getKey();

                    ChatInformation chatInformation=new ChatInformation(videoId, CommonUtils.getUserId(),
                            random_key_generator, getDate(), getTime(), CommonUtils.getName(), CommonUtils.getImage(), "1",thumbnail);
                    databaseReference.child(random_key_generator).setValue(chatInformation).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(requireContext(), "seccessfully added1", Toast.LENGTH_SHORT).show();

                            DatabaseReference databaseReference;


                            if (!otherUserId.equals(CommonUtils.getUserId())) {
                                databaseReference = FirebaseDatabase.getInstance()
                                        .getReference("last-otherUserId").child(otherUserId).child(CommonUtils.getUserId());

                            } else {
                                databaseReference = FirebaseDatabase.getInstance()
                                        .getReference("last-otherUserId").child(CommonUtils.getUserId()).child(otherUserId);

                            }

                            saveLastMessage(databaseReference, new ChatInformation(videoId, CommonUtils.getUserId(),
                                    random_key_generator, getDate(), getTime(), CommonUtils.getName(), CommonUtils.getImage(), "1",thumbnail)
                            );


                        }
                    });
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
    
    private void getComments(String videoId, RecyclerView rvComments) {

        Toast.makeText(requireContext(), "1   "+videoId, Toast.LENGTH_SHORT).show();
        viewModelClass.commentRootLiveData(requireActivity(), videoId).observe(requireActivity(), response -> {
            if (response != null) {
                if (response.getSuccess().equals("1")) {
                    rvComments.setAdapter(new CommentsAdapter(response.getDetails(), requireContext(),ForYouSubcriptionFragment.this ));
                } else {
                    Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void commentLikes(CommentDetails details, ImageView likeUnlike, ImageView likeUnlike2) {

        String commentId = details.getVideoCommentId();

        Toast.makeText(requireContext(), ""+commentId, Toast.LENGTH_SHORT).show();

        viewModelClass.likeAndDislikeComments(CommonUtils.getUserId(),commentId).observe(requireActivity(), new Observer<CommentRoot>() {

            @Override
            public void onChanged(CommentRoot commentRoot) {
                if (commentRoot !=null){

                    if (commentRoot.getSuccess().equalsIgnoreCase("1")){
                        Toast.makeText(requireContext(), ""+commentRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        likeUnlike2.setVisibility(View.VISIBLE);
                        likeUnlike.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(requireContext(), ""+commentRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        likeUnlike.setVisibility(View.VISIBLE);
                        likeUnlike2.setVisibility(View.GONE);
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String getDate() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        Date date = new Date();
        return formatter.format(date);

    }

    private String getTime() {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);


    }
    private void saveLastMessage(DatabaseReference databaseReference, ChatInformation chatInformation) {

        Toast.makeText(requireContext(), ""+CommonUtils.getImage(), Toast.LENGTH_SHORT).show();
        Toast.makeText(requireContext(), ""+CommonUtils.getName(), Toast.LENGTH_SHORT).show();


//        Toast.makeText(getContext(), MessageFragment.other_userId + " " + MessageFragment.userId, Toast.LENGTH_SHORT).show();
        databaseReference.setValue(chatInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

}