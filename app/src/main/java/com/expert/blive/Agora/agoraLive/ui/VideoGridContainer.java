package com.expert.blive.Agora.agoraLive.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.expert.blive.Agora.agoraLive.MultiLiveHostsModel;
import com.expert.blive.Agora.agoraLive.stats.StatsData;
import com.expert.blive.Agora.agoraLive.stats.StatsManager;
import com.expert.blive.R;
import com.expert.blive.utils.App;

import java.util.LinkedList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class VideoGridContainer extends RelativeLayout implements Runnable {
    public static MutableLiveData<FakeViewControllerModel> fakeViewController = new MutableLiveData<>();
    public static MutableLiveData<FakeViewControllerModel> fakeViewControllerTwo = new MutableLiveData<>();
    public static MutableLiveData<FakeViewControllerModel> fakeViewControllerThree = new MutableLiveData<>();
    public static MutableLiveData<MultiLiveHostsModel> listMultiLiveData = new MutableLiveData<>();
    public static MutableLiveData<Integer> marginHandleForThreeScreen = new MutableLiveData<>();
    public static int MAX_USER;
    int i = 0;
    boolean isLocal, otherUser;
    private static final int STATS_REFRESH_INTERVAL = 2000;
    private static final int STAT_LEFT_MARGIN = 34;
    private static final int STAT_TEXT_SIZE = 10;
    Activity activity;
    private String hostType = "";

    float radiusCornerValue = 30f;

    private final SparseArray<ViewGroup> mUserViewList = new SparseArray<>(MAX_USER);
    private final List<Integer> mUidList = new LinkedList<>();
    private final List<String> imageList = new LinkedList<>();
    private StatsManager mStatsManager;
    private Handler mHandler;
    private int mStatMarginBottom;

    private boolean isNotSwapped = true;
    public static boolean isNotHost = true;

    public OnGridClicked onGridClicked;

    public VideoGridContainer(Context context) {
        super(context);
        init();
    }

    public void onVideoClicked(OnGridClicked onGridClicked) {
        this.onGridClicked = onGridClicked;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public VideoGridContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoGridContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mStatMarginBottom = 68;
        mHandler = new Handler(getContext().getMainLooper());
    }

    public void setStatsManager(StatsManager manager) {
        mStatsManager = manager;
    }

    public void addUserVideoSurface(Activity activity, int uid, SurfaceView surface, boolean isLocal, boolean otherUser, String image, int hostId) {
        this.activity = activity;
        this.isLocal = isLocal;
        this.otherUser = otherUser;

        Log.d("addUserVideoSurface", "addUserVideoSurface: " + uid);

        if (surface == null) {
            return;
        }
        int id = -1;
        if (isLocal) {
            if (mUidList.contains(uid)) {

                Log.d("addUserVideoSurface", "addUserVideoSurface: Abcd");
                mUidList.remove((Integer) uid);
                mUserViewList.remove(uid);

            }

            if (mUidList.size() == MAX_USER) {
                Log.d("addUserVideoSurface", "addUserVideoSurface: sdf");
                mUidList.remove(uid);
                mUserViewList.remove(uid);


            }
            id = 0;
        } else {
            Log.d("addUserVideoSurface", "addUserVideoSurface: sdsd");
            if (mUidList.contains(uid)) {
                mUidList.remove((Integer) uid);
                mUserViewList.remove(uid);
                Log.d("addUserVideoSurface", "addUserVideoSurface: sdcef2fds");

            }

            if (mUidList.size() < MAX_USER) {
                Log.d("addUserVideoSurface", "addUserVideoSurface: erwgomvd");

                id = uid;
            }
        }

        if (App.getSingletone().getMyPk().equalsIgnoreCase("0")) {

            if (id == 0) {
                mUidList.add(uid);
                Log.d("addUserVideoSurface", "addUserVideoSurface: 12321");


            } else {
                mUidList.add(id);
                Log.d("addUserVideoSurface", "addUserVideoSurface: 2332123");


            }

        } else {
            if (hostId == uid || id == 0) {
                mUidList.add(0, uid);

            } else {
                mUidList.add(id);
                Log.d("addUserVideoSurface", "addUserVideoSurface: skjdcdnlsanc");
            }


        }


        if (id != -1) {
            Log.d("addUserVideoSurface", "addUserVideoSurface: gdciajscbjhkbascguyasc");
            mUserViewList.append(uid, createVideoView(surface, uid, image));
            if (mStatsManager != null) {
                mStatsManager.addUserStats(uid, isLocal);
                if (mStatsManager.isEnabled()) {
                    mHandler.removeCallbacks(this);
                    mHandler.postDelayed(this, STATS_REFRESH_INTERVAL);
                }
            }
            requestGridLayout();
        }


//    if (mUidList.size() == 1) {
//        mUidList.add(0, uid);
//    } else if (mUidList.size() == 2) {
//        mUidList.add(1, uid);
//
//    } else if (mUidList.size() == 3) {
//        mUidList.add(2, uid);
//
//    } else if (mUidList.size() == 4) {
//        mUidList.add(3, uid);
//
//    } else if (mUidList.size() == 5) {
//        mUidList.add(4, uid);
//


//        if (isNotHost) {
//            try {
//                listMultiLiveData.observe((LifecycleOwner) getContext(), multiLiveHostsModel -> {
//                    if (mUidList.size() > 1) {
//                        for (int i = 0; i < mUidList.size(); i++) {
//                            if (mUidList.get(i) == Integer.parseInt(multiLiveHostsModel.getUid())) {
//                                if (isNotSwapped) {
//                                    isNotSwapped = false;
//                                    //Collections.swap(mUidList, 0, i);
//                                }
//                            }
//                        }
//                    }
//                });
//            } catch (Exception ignored) {
//            }
//        }


    }

    private ViewGroup createVideoView(SurfaceView surface, int uid, String image) {
        RelativeLayout layout = new RelativeLayout(getContext());

//        layout.setRadius(radiusCornerValue);
        //CardView cardView = new CardView(getContext());

        i++;

        layout.setId(uid);


//        layout.setCardElevation(20);

        LayoutParams videoLayoutParams =
                new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

        CircleImageView imageView = new CircleImageView(getContext());

        try {
            Glide.with(getContext()).load(image).placeholder(R.drawable.logo).into(imageView);
        }
        catch (Exception e){

        }

        if (activity != null) {
            imageView.setBorderColor(activity.getResources().getColor(R.color.app_pink_color));

        }
        imageView.setBorderWidth(2);
        imageView.setPadding(10, 10, 10, 10);
        ImageView ivAudio = new ImageView(getContext());
        LayoutParams ivParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT
                , 20);
        try {

            Glide.with(getContext()).load(R.drawable.ic_close__2_).into(ivAudio);
        }
        catch (Exception e){

        }
//
        ivParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        ivParams.setMargins(10, 10, 10, 10);

        layout.addView(surface, videoLayoutParams);
//        if ( otherUser) {
//            layout.addView(imageView, videoLayoutParams);
//            layout.addView(ivAudio, ivParams);
//            layout.setBackground(activity.getResources().getDrawable(R.drawable.bg_all_live));
////            layout.setUseCompatPadding(true);
//        } else {
//
//
//        }

//        TextView text = new TextView(getContext());
//        text.setId(layout.hashCode());

        LayoutParams textParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        textParams.bottomMargin = mStatMarginBottom;
        textParams.leftMargin = STAT_LEFT_MARGIN;
//        text.setTextColor(Color.WHITE);
//        text.setTextSize(STAT_TEXT_SIZE);
//        text.setText(uid);

        ImageView ivcallCut = new ImageView(getContext());
        ivAudio.setId(layout.hashCode() + 1);
        ivcallCut.setId(layout.hashCode() + 2);


        LayoutParams cut = new LayoutParams(
                20,
                20);


//        ivParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//
//        ivParams.topMargin = 15;
//        ivParams.leftMargin = 15;
//
//

//        try {
//            Glide.with(getContext()).load(R.drawable.btn_audio_enabled).into(ivAudio);
//        } catch (Exception ignored){
//        }


//        ivAudio.setColorFilter(ContextCompat.getColor(getContext(), R.color.red),
//                android.graphics.PorterDuff.Mode.MULTIPLY);
//        ivAudio.setLayoutParams(ivParams);
//        layout.addView(ivAudio);
        ivcallCut.setLayoutParams(cut);
        layout.addView(ivcallCut);


        return layout;
    }

    public void removeUserVideo(int uid, boolean isLocal) {
        if (isLocal && mUidList.contains(0)) {
            mUidList.remove((Integer) 0);
            mUserViewList.remove(0);
        } else if (mUidList.contains(uid)) {
            mUidList.remove((Integer) uid);
            mUserViewList.remove(uid);
        }

        mStatsManager.removeUserStats(uid);

        requestGridLayout();

        if (getChildCount() == 0) {
            mHandler.removeCallbacks(this);
        }
    }

    private void requestGridLayout() {
        removeAllViews();

        layout(mUidList.size());
    }

    private void layout(int size) {

        LayoutParams[] params = getParams(size);

        for (int i = 0; i < size; i++) {

            RelativeLayout view = (RelativeLayout) mUserViewList.get(mUidList.get(i));
//            if (StartLiveScreenFragment.check == 1 || LiveFragment.check_status.equals("1")) {
//                view.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.besso_beso));
//            }
//            view.setCardElevation(20);


//                        ImageView imageView = new ImageView(getContext());

//            imageView.setId(mUidList.get(i) - 2);
//            Paint paint = new Paint();
//            Glide.with(getContext()).load(R.raw.music_sound)
//                    .into(imageView);
//            PorterDuffColorFilter colorFilter =new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
//            paint.setColorFilter(colorFilter);
//            imageView.setLayerPaint(paint);
//
//            view.addView(imageView);
            view.setId(mUidList.get(i));
            view.setOnClickListener(view1 -> {
                try {
                    onGridClicked.onGridClicked(0, view.getId());

                } catch (Exception e) {
                    Log.e("CLICKABLE_ERROR", "layout: " + e.getMessage());
                }
            });

            addView(view, params[i]);
        }
    }

    private LayoutParams[] getParams(int size) {
        // set bottom margin
        WindowManager windowManager = null;
        try {
            windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        }
        catch (Exception e){

        }
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        // set for square video of multi live
        height = width;

        LayoutParams[] array = new LayoutParams[size];

        // update live data to show fake views according to multi live users entered


        if (MAX_USER == 5) {
            fakeViewController.postValue(new FakeViewControllerModel(size, width, height, 500));

        } else if (MAX_USER == 6) {
            fakeViewControllerTwo.postValue(new FakeViewControllerModel(size, width, height, 500));
        } else if (MAX_USER == 9) {
            fakeViewControllerThree.postValue(new FakeViewControllerModel(size, width, height, 500));

        }


        marginHandleForThreeScreen.observe((LifecycleOwner) getContext(), integer -> {
            //bottomMargin.set(integer);
        });


        if (MAX_USER == 2) {
            Log.d("getParamsgetParams", "getParams: data");
            for (int i = 0; i < size; i++) {
                if (i == 0) {


                    if (size == 1) {
                        array[i] = new LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);


                    } else {
                        array[i] = new LayoutParams(
                                width / 2,
                                width);


                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

                    }


                }
                if (i == 1) {

                    array[i] = new LayoutParams(
                            width / 2,
                            width);
                    array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                    array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());
                    array[i].addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);


                }


            }
        }


        if (MAX_USER == 5) {
            if (App.getSingletone().getMyPk().equalsIgnoreCase("0")) {
                for (int i = 0; i < size; i++) {

                    if (i == 0) {
                        array[i] = new LayoutParams(
                                width / 2,
                                height / 2);
                        array[i].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

                        Log.d("getParamsgetParams", "getParams: 1");


                    }
                    if (i == 1) {

                        array[i] = new LayoutParams(
                                width / 2,
                                height / 2);
                        array[i].setMargins(20, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                        Log.d("getParamsgetParams", "getParams: 2");

                    }
                    if (i == 2) {

                        array[i] = new LayoutParams(
                                width / 2,
                                height / 2);
                        Log.d("getParamsgetParams", "getParams: 3");

                        array[i].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

                    }
                    if (i == 3) {

                        Log.d("getParamsgetParams", "getParams: 4");
                        array[i] = new LayoutParams(
                                width / 2 - 30,
                                height / 2);
                        array[i].setMargins(20, 8, 8, 8);
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(2)).getId());
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());


                    }


                }
            } else {
                for (int i = 0; i < size; i++) {

                    if (i == 0) {
                        array[i] = new LayoutParams(
                                width / 2,
                                height / 2);
                        array[i].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

                        Log.d("getParams", "getParams: 1");


                    }
                    if (i == 1) {

                        array[i] = new LayoutParams(
                                width / 2,
                                height / 2);
                        array[i].setMargins(20, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);
                        Log.d("getParamsgetParams", "getParams: 2");

                    }
                    if (i == 2) {

                        array[i] = new LayoutParams(
                                width / 2,
                                height / 2);
                        Log.d("getParamsgetParams", "getParams: 3");

                        array[i].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());


                    }
                    if (i == 3) {

                        Log.d("getParamsgetParams", "getParams: 4");
                        array[i] = new LayoutParams(
                                width / 2 - 30,
                                height / 2);
                        array[i].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(2)).getId());
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());


                    }


                }
            }

        } else if (MAX_USER == 4) {
            if (App.getSingletone().getMyPk().equalsIgnoreCase("0")) {


                for (int i = 0; i < size; i++) {

                    if (i == 0) {
//

//                Toast.makeText(activity, "1" + App.getSingletone().getMyPk(), Toast.LENGTH_SHORT).show();
//                        if (size >= 4) {
//
//
//                            array[i] = new RelativeLayout.LayoutParams(width, height);
//                            array[i].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//                            array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//                            array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//                        } else {

                        array[i] = new LayoutParams(
                                LayoutParams.MATCH_PARENT,
                                LayoutParams.MATCH_PARENT);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);


                        // array[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//                        }
                    }
                    if (i == 1) {

                        array[i] = new LayoutParams(width / 5, height / 3);

                        //array[0].width = width/3;
                        //array[0].height = height/3;
                        array[1].bottomMargin = 750;

                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

                        //array[0].height = height - height/3;
                        //array[0].width = width - width/3;

                    }
                    if (i == 2) {

                        array[i] = new LayoutParams(width / 5, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 1].width = array[i].width;
                        //array[0].height = array[i].height;
//                array[i].height = array[i].height;
                        //array[i - 1].width = array[i].width;
                        //array[i - 1].height = array[i].height;

//
//                    if (size > 3) {
//                        array[1].bottomMargin = 0;
//                    } else {
//                        if (j < 1) {
//                            array[1].bottomMargin = 500;
//                            j++;
//                        }
//                    }
                        array[i].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(1)).getId());
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    }
                    if (i == 3) {
                        array[i] = new LayoutParams(width / 5, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 2].width = array[i].width;
//                array[i - 1].width = array[i].width;
//                array[i - 2].height = array[i].height;
//                array[i - 1].height = array[i].height;


                        //array[0].height = array[i].height;
//                if (j == 1) {
//                    array[1].bottomMargin = height;
//                    Toast.makeText(activity, "a" + j, Toast.LENGTH_SHORT).show();
//                    j++;
//                } else {
//                    Toast.makeText(activity, "b" + j, Toast.LENGTH_SHORT).show();
//
//
//
//                }
//                array[1].addRule(RelativeLayout.ALIGN_BOTTOM, mUserViewList.get(mUidList.get(0)).getId());


                        array[i].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(2)).getId());
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);


//                array[3].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(0)).getId());

                        //array[3].addRule(RelativeLayout.ALIGN_TOP, mUserViewList.get(mUidList.get(i - 1)).getId());
                    }

//
////
//                    if (i == 4) {
//
//
//                        array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
//                        //array[0].width = array[i].width;
//                        array[i].bottomMargin = 750;
//
//                        array[i].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(3)).getId());
//                        array[i].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//
//                    }
//                    if (i == 5) {
//                        array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
//                        //array[0].width = array[i].width;
//
//                        array[i].bottomMargin = 750;
//
//                        array[i].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(4)).getId());
//                        array[i].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    }
//                    if (i == 6) {
//                        array[6] = new RelativeLayout.LayoutParams(width / 4, height / 4);
//                        //array[0].width = array[i].width;
//                        array[1].width = array[i].width;
//                        array[2].width = array[i].width;
//                        array[3].width = array[i].width;
//                        array[4].width = array[i].width;
//                        array[5].width = array[i].width;
//
//                        //array[0].height = array[i].height;
//                        array[1].height = array[i].height;
//                        array[2].height = array[i].height;
//                        array[3].height = array[i].height;
//                        array[4].height = array[i].height;
//                        array[5].height = array[i].height;
//
//                        array[6].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(5)).getId());
//                        array[6].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    }
//                    if (i == 7) {
//                        array[7] = new RelativeLayout.LayoutParams(width / 4, height / 4);
//                        //array[0].width = array[i].width;
//                        array[1].width = array[i].width;
//                        array[2].width = array[i].width;
//                        array[3].width = array[i].width;
//                        array[4].width = array[i].width;
//                        array[5].width = array[i].width;
//                        array[6].width = array[i].width;
//
//                        //array[0].height = array[i].height;
//                        array[1].height = array[i].height;
//                        array[2].height = array[i].height;
//                        array[3].height = array[i].height;
//                        array[4].height = array[i].height;
//                        array[5].height = array[i].height;
//                        array[6].height = array[i].height;
//
//                        array[7].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(3)).getId());
//                        array[7].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//                    }
//                    if (i == 8) {
//
//                        array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
//                        array[0].width = width / 3;
//                        array[1].addRule(RelativeLayout.BELOW, 0);
//                        array[1].addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
//                        array[1].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());
//                        array[1].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//                        array[2].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//                        array[2].addRule(RelativeLayout.RIGHT_OF, 0);
//                        array[2].addRule(RelativeLayout.ALIGN_TOP, 0);
//                        array[2].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());
//                        array[3].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
//                        array[3].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(2)).getId());
//
//                        array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
//                        array[0].width = array[i].width;
//                        array[0].height = array[i].height;
//                        array[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//                        array[0].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//
//                        array[1].width = array[i].width;
//                        array[1].height = array[i].height;
//                        array[1].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(0)).getId());
//                        array[1].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//
//                        array[2].width = array[i].width;
//                        array[2].height = array[i].height;
//                        array[2].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(1)).getId());
//                        array[2].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//
//                        array[3].width = array[i].width;
//                        array[3].height = array[i].height;
//                        array[3].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());
//                        array[3].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//
//                        array[4].width = array[i].width;
//                        array[4].height = array[i].height;
//                        array[4].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
//                        array[4].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(3)).getId());
//
//                        array[5].width = array[i].width;
//                        array[5].height = array[i].height;
//                        array[5].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(2)).getId());
//                        array[5].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(4)).getId());
//
//
//                        array[6].width = array[i].width;
//                        array[6].height = array[i].height;
//                        array[6].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(3)).getId());
//                        array[6].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//
//
//                        array[7].width = array[i].width;
//                        array[7].height = array[i].height;
//                        array[7].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(4)).getId());
//                        array[7].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(6)).getId());
//
//                        array[8].width = array[i].width;
//                        array[8].height = array[i].height;
//                        array[8].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(5)).getId());
//                        array[8].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(7)).getId());
//                    }
                }

            } else {
                for (int i = 0; i < size; i++) {
                    if (i == 0) {

//                Toast.makeText(activity, "1" + App.getSingletone().getMyPk(), Toast.LENGTH_SHORT).show();


                        array[i] = new LayoutParams(
                                LayoutParams.MATCH_PARENT,
                                LayoutParams.MATCH_PARENT);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);


                        // array[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

                    }
                    if (i == 1) {

                        array[i] = new LayoutParams(width / 5, height / 3);
                        //array[0].width = width/3;
                        //array[0].height = height/3;if
                        if (size <= 3) {
                            array[1].bottomMargin = 500;
                        }


                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

                        //array[0].height = height - height/3;
                        //array[0].width = width - width/3;

                    }
                    if (i == 2) {

                        array[i] = new LayoutParams(width / 5, height / 3);
                        if (size <= 3) {
                            array[1].bottomMargin = 500;
                        }

                        //array[0].width = array[i].width;
//                array[i - 1].width = array[i].width;
                        //array[0].height = array[i].height;
//                array[i].height = array[i].height;
                        //array[i - 1].width = array[i].width;
                        //array[i - 1].height = array[i].height;

//
//                    if (size > 3) {
//                        array[1].bottomMargin = 0;
//                    } else {
//                        if (j < 1) {
//                            array[1].bottomMargin = 500;
//                            j++;
//                        }
//                    }
                        array[i].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(1)).getId());
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    }
                    if (i == 3) {
                        if (size <= 3) {
                            array[1].bottomMargin = 500;
                        }

                        array[i] = new LayoutParams(width / 5, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 2].width = array[i].width;
//                array[i - 1].width = array[i].width;
//                array[i - 2].height = array[i].height;
//                array[i - 1].height = array[i].height;


                        //array[0].height = array[i].height;
//                if (j == 1) {
//                    array[1].bottomMargin = height;
//                    Toast.makeText(activity, "a" + j, Toast.LENGTH_SHORT).show();
//                    j++;
//                } else {
//                    Toast.makeText(activity, "b" + j, Toast.LENGTH_SHORT).show();
//
//
//
//                }
//                array[1].addRule(RelativeLayout.ALIGN_BOTTOM, mUserViewList.get(mUidList.get(0)).getId());


                        array[i].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(2)).getId());
//                    array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);


//                array[3].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(0)).getId());

                        //array[3].addRule(RelativeLayout.ALIGN_TOP, mUserViewList.get(mUidList.get(i - 1)).getId());
                    }


//
//                    if (i == 4) {
//
//
//                        array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
//                        //array[0].width = array[i].width;
//
//                        array[i].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(3)).getId());
//                        array[i].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                        array[1].bottomMargin = 0;
//                    }
//                    if (i == 5) {
//                        array[5] = new RelativeLayout.LayoutParams(width / 3, height / 3);
//                        //array[0].width = array[i].width;
//                        array[1].width = array[i].width;
//                        array[2].width = array[i].width;
//                        array[3].width = array[i].width;
//                        array[4].width = array[i].width;
//
//                        //array[0].height = array[i].height;
//                        array[1].height = array[i].height;
//                        array[2].height = array[i].height;
//                        array[3].height = array[i].height;
//                        array[4].height = array[i].height;
//
//                        array[5].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(4)).getId());
//                        array[5].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    }
//                    if (i == 6) {
//                        array[6] = new RelativeLayout.LayoutParams(width / 4, height / 4);
//                        //array[0].width = array[i].width;
//                        array[1].width = array[i].width;
//                        array[2].width = array[i].width;
//                        array[3].width = array[i].width;
//                        array[4].width = array[i].width;
//                        array[5].width = array[i].width;
//
//                        //array[0].height = array[i].height;
//                        array[1].height = array[i].height;
//                        array[2].height = array[i].height;
//                        array[3].height = array[i].height;
//                        array[4].height = array[i].height;
//                        array[5].height = array[i].height;
//
//                        array[6].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(5)).getId());
//                        array[6].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//                    }
//                    if (i == 7) {
//                        array[7] = new RelativeLayout.LayoutParams(width / 4, height / 4);
//                        //array[0].width = array[i].width;
//                        array[1].width = array[i].width;
//                        array[2].width = array[i].width;
//                        array[3].width = array[i].width;
//                        array[4].width = array[i].width;
//                        array[5].width = array[i].width;
//                        array[6].width = array[i].width;
//
//                        //array[0].height = array[i].height;
//                        array[1].height = array[i].height;
//                        array[2].height = array[i].height;
//                        array[3].height = array[i].height;
//                        array[4].height = array[i].height;
//                        array[5].height = array[i].height;
//                        array[6].height = array[i].height;
//
//                        array[7].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(3)).getId());
//                        array[7].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//                    }
//                    if (i == 8) {
//
//                        array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
//                        array[0].width = width / 3;
//                        array[1].addRule(RelativeLayout.BELOW, 0);
//                        array[1].addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
//                        array[1].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());
//                        array[1].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//                        array[2].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//                        array[2].addRule(RelativeLayout.RIGHT_OF, 0);
//                        array[2].addRule(RelativeLayout.ALIGN_TOP, 0);
//                        array[2].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());
//                        array[3].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
//                        array[3].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(2)).getId());
//
//                        array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
//                        array[0].width = array[i].width;
//                        array[0].height = array[i].height;
//                        array[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//                        array[0].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//
//                        array[1].width = array[i].width;
//                        array[1].height = array[i].height;
//                        array[1].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(0)).getId());
//                        array[1].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//
//                        array[2].width = array[i].width;
//                        array[2].height = array[i].height;
//                        array[2].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(1)).getId());
//                        array[2].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
//
//                        array[3].width = array[i].width;
//                        array[3].height = array[i].height;
//                        array[3].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());
//                        array[3].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//
//                        array[4].width = array[i].width;
//                        array[4].height = array[i].height;
//                        array[4].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
//                        array[4].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(3)).getId());
//
//                        array[5].width = array[i].width;
//                        array[5].height = array[i].height;
//                        array[5].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(2)).getId());
//                        array[5].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(4)).getId());
//
//
//                        array[6].width = array[i].width;
//                        array[6].height = array[i].height;
//                        array[6].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(3)).getId());
//                        array[6].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
//
//
//                        array[7].width = array[i].width;
//                        array[7].height = array[i].height;
//                        array[7].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(4)).getId());
//                        array[7].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(6)).getId());
//
//                        array[8].width = array[i].width;
//                        array[8].height = array[i].height;
//                        array[8].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(5)).getId());
//                        array[8].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(7)).getId());
//                    }
                }
            }
        } else if (MAX_USER == 6) {
            if (App.getSingletone().getMyPk().equalsIgnoreCase("0")) {
                for (int i = 0; i < size; i++) {


                    if (i == 0) {

                        int w = width / 3 + width / 3;
                        array[i] = new LayoutParams(
                                w - 20,
                                w);
                        array[0].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

                    }
                    if (i == 1) {

                        array[i] = new LayoutParams(width / 3, height / 3);

                        //array[0].width = width/3;
                        //array[0].height = height/3;
//                    array[0].setMargins(8, 120, 8, 8);
                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());

                        //array[0].height = height - height/3;
                        //array[0].width = width - width/3;

                    }
                    if (i == 2) {

                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 1].width = array[i].width;
                        //array[0].height = array[i].height;
//                array[i].height = array[i].height;
                        //array[i - 1].width = array[i].width;
                        //array[i - 1].height = array[i].height;

//
//                    if (size > 3) {
//                        array[1].bottomMargin = 0;
//                    } else {
//                        if (j < 1) {
//                            array[1].bottomMargin = 500;
//                            j++;
//                        }
//                    }
                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    }
                    if (i == 3) {
                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 2].width = array[i].width;
//                array[i - 1].width = array[i].width;
//                array[i - 2].height = array[i].height;
//                array[i - 1].height = array[i].height;


                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        //array[0].height = array[i].height;
//                if (j == 1) {
//                    array[1].bottomMargin = height;
//                    Toast.makeText(activity, "a" + j, Toast.LENGTH_SHORT).show();
//                    j++;
//                } else {
//                    Toast.makeText(activity, "b" + j, Toast.LENGTH_SHORT).show();
//
//
//
//                }
//                array[1].addRule(RelativeLayout.ALIGN_BOTTOM, mUserViewList.get(mUidList.get(0)).getId());


                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(2)).getId());
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());
//                    array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);


//                array[3].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(0)).getId());

                        //array[3].addRule(RelativeLayout.ALIGN_TOP, mUserViewList.get(mUidList.get(i - 1)).getId());
                    }
                    if (i == 4) {


                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(3)).getId());
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(2)).getId());

                    }
                    if (i == 5) {
                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[5].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(4)).getId());
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(2)).getId());
                    }

                }
            } else {
                for (int i = 0; i < size; i++) {


                    if (i == 0) {

                        int w = width / 3 + width / 3;
                        array[i] = new LayoutParams(
                                w - 20,
                                w);
                        array[0].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

                    }
                    if (i == 1) {

                        array[i] = new LayoutParams(width / 3, height / 3);

                        //array[0].width = width/3;
                        //array[0].height = height/3;
//                    array[0].setMargins(8, 120, 8, 8);
                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());

                        //array[0].height = height - height/3;
                        //array[0].width = width - width/3;

                    }
                    if (i == 2) {

                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 1].width = array[i].width;
                        //array[0].height = array[i].height;
//                array[i].height = array[i].height;
                        //array[i - 1].width = array[i].width;
                        //array[i - 1].height = array[i].height;

//
//                    if (size > 3) {
//                        array[1].bottomMargin = 0;
//                    } else {
//                        if (j < 1) {
//                            array[1].bottomMargin = 500;
//                            j++;
//                        }
//                    }
                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    }
                    if (i == 3) {
                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 2].width = array[i].width;
//                array[i - 1].width = array[i].width;
//                array[i - 2].height = array[i].height;
//                array[i - 1].height = array[i].height;


                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        //array[0].height = array[i].height;
//                if (j == 1) {
//                    array[1].bottomMargin = height;
//                    Toast.makeText(activity, "a" + j, Toast.LENGTH_SHORT).show();
//                    j++;
//                } else {
//                    Toast.makeText(activity, "b" + j, Toast.LENGTH_SHORT).show();
//
//
//
//                }
//                array[1].addRule(RelativeLayout.ALIGN_BOTTOM, mUserViewList.get(mUidList.get(0)).getId());


                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(2)).getId());
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());
//                    array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE);


//                array[3].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(0)).getId());

                        //array[3].addRule(RelativeLayout.ALIGN_TOP, mUserViewList.get(mUidList.get(i - 1)).getId());
                    }
                    if (i == 4) {


                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(3)).getId());
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(2)).getId());

                    }
                    if (i == 5) {
                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[5].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(4)).getId());
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());

                    }

                }
            }

        } else if (MAX_USER == 9) {
            if (App.getSingletone().getMyPk().equalsIgnoreCase("0")) {
                for (int i = 0; i < size; i++) {


                    if (i == 0) {

                        array[i] = new LayoutParams(
                                width / 3 - 20,
                                width / 3);
                        array[0].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

                    }
                    if (i == 1) {

                        array[i] = new LayoutParams(width / 3, height / 3);

                        //array[0].width = width/3;
                        //array[0].height = height/3;
//                    array[0].setMargins(8, 120, 8, 8);
                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());

                        //array[0].height = height - height/3;
                        //array[0].width = width - width/3;

                    }
                    if (i == 2) {

                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 1].width = array[i].width;
                        //array[0].height = array[i].height;
//                array[i].height = array[i].height;
                        //array[i - 1].width = array[i].width;
                        //array[i - 1].height = array[i].height;

//
//                    if (size > 3) {
//                        array[1].bottomMargin = 0;
//                    } else {
//                        if (j < 1) {
//                            array[1].bottomMargin = 500;
//                            j++;
//                        }
//                    }
                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);

                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(1)).getId());
                    }
                    if (i == 3) {
                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 2].width = array[i].width;
//                array[i - 1].width = array[i].width;
//                array[i - 2].height = array[i].height;
//                array[i - 1].height = array[i].height;


                        array[0].setMargins(8, 8, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        //array[0].height = array[i].height;
//                if (j == 1) {
//                    array[1].bottomMargin = height;
//                    Toast.makeText(activity, "a" + j, Toast.LENGTH_SHORT).show();
//                    j++;
//                } else {
//                    Toast.makeText(activity, "b" + j, Toast.LENGTH_SHORT).show();
//
//
//
//                }
//                array[1].addRule(RelativeLayout.ALIGN_BOTTOM, mUserViewList.get(mUidList.get(0)).getId());

                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
//                    array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);


//                array[3].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(0)).getId());

                        //array[3].addRule(RelativeLayout.ALIGN_TOP, mUserViewList.get(mUidList.get(i - 1)).getId());
                    }
                    if (i == 4) {


                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);

                        array[i].addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());

                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(3)).getId());

                    }
                    if (i == 5) {
                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[5].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(4)).getId());
                    }
                    if (i == 6) {
                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[5].setMargins(8, 8, 8, 8);
                        array[6].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(3)).getId());

//                    array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(4)).getId());
                    }
                    if (i == 7) {

                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[5].setMargins(8, 8, 8, 8);
                        array[6].setMargins(8, 8, 8, 8);
                        array[7].setMargins(8, 8, 8, 8);

                        array[i].addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(4)).getId());
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(6)).getId());
                    }
                    if (i == 8) {

                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[5].setMargins(8, 8, 8, 8);
                        array[6].setMargins(8, 8, 8, 8);
                        array[7].setMargins(8, 8, 8, 8);
                        array[8].setMargins(8, 8, 8, 8);

                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(5)).getId());
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(7)).getId());
                    }
//                if (i == 8) {
//
//                    array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
//                    //array[0].width = array[i].width;
//
//                    array[0].setMargins(8, 20, 8, 8);
//                    array[1].setMargins(8, 8, 8, 8);
//                    array[2].setMargins(8, 8, 8, 8);
//                    array[3].setMargins(8, 8, 8, 8);
//                    array[4].setMargins(8, 8, 8, 8);
//                    array[5].setMargins(8, 8, 8, 8);
//                    array[6].setMargins(8, 8, 8, 8);
//                    array[7].setMargins(8, 8, 8, 8);
//                    array[8].setMargins(8, 8, 8, 8);
//
//                    array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//                    array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(5)).getId());
//                    array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(7)).getId());
//                }

                }

            } else {
                for (int i = 0; i < size; i++) {


                    if (i == 0) {

                        array[i] = new LayoutParams(
                                width / 3 - 20,
                                width / 3);
                        array[0].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);

                    }
                    if (i == 1) {

                        array[i] = new LayoutParams(width / 3, height / 3);

                        //array[0].width = width/3;
                        //array[0].height = height/3;
//                    array[0].setMargins(8, 120, 8, 8);
                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());

                        //array[0].height = height - height/3;
                        //array[0].width = width - width/3;

                    }
                    if (i == 2) {

                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 1].width = array[i].width;
                        //array[0].height = array[i].height;
//                array[i].height = array[i].height;
                        //array[i - 1].width = array[i].width;
                        //array[i - 1].height = array[i].height;

//
//                    if (size > 3) {
//                        array[1].bottomMargin = 0;
//                    } else {
//                        if (j < 1) {
//                            array[1].bottomMargin = 500;
//                            j++;
//                        }
//                    }
                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);

                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(1)).getId());
                    }
                    if (i == 3) {
                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;
//                array[i - 2].width = array[i].width;
//                array[i - 1].width = array[i].width;
//                array[i - 2].height = array[i].height;
//                array[i - 1].height = array[i].height;


                        array[0].setMargins(8, 8, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        //array[0].height = array[i].height;
//                if (j == 1) {
//                    array[1].bottomMargin = height;
//                    Toast.makeText(activity, "a" + j, Toast.LENGTH_SHORT).show();
//                    j++;
//                } else {
//                    Toast.makeText(activity, "b" + j, Toast.LENGTH_SHORT).show();
//
//
//
//                }
//                array[1].addRule(RelativeLayout.ALIGN_BOTTOM, mUserViewList.get(mUidList.get(0)).getId());

                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());
//                    array[i].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);


//                array[3].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(0)).getId());

                        //array[3].addRule(RelativeLayout.ALIGN_TOP, mUserViewList.get(mUidList.get(i - 1)).getId());
                    }
                    if (i == 4) {


                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);

                        array[i].addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());

                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(3)).getId());

                    }
                    if (i == 5) {
                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[5].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(4)).getId());
                    }
                    if (i == 6) {
                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[5].setMargins(8, 8, 8, 8);
                        array[6].setMargins(8, 8, 8, 8);
                        array[i].addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(3)).getId());

//                    array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(4)).getId());
                    }
                    if (i == 7) {

                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[5].setMargins(8, 8, 8, 8);
                        array[6].setMargins(8, 8, 8, 8);
                        array[7].setMargins(8, 8, 8, 8);

                        array[i].addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(4)).getId());
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(6)).getId());
                    }
                    if (i == 8) {

                        array[i] = new LayoutParams(width / 3, height / 3);
                        //array[0].width = array[i].width;

                        array[0].setMargins(8, 20, 8, 8);
                        array[1].setMargins(8, 8, 8, 8);
                        array[2].setMargins(8, 8, 8, 8);
                        array[3].setMargins(8, 8, 8, 8);
                        array[4].setMargins(8, 8, 8, 8);
                        array[5].setMargins(8, 8, 8, 8);
                        array[6].setMargins(8, 8, 8, 8);
                        array[7].setMargins(8, 8, 8, 8);
                        array[8].setMargins(8, 8, 8, 8);

                        array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(5)).getId());
                        array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(7)).getId());
                    }
//                if (i == 8) {
//
//                    array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
//                    //array[0].width = array[i].width;
//
//                    array[0].setMargins(8, 20, 8, 8);
//                    array[1].setMargins(8, 8, 8, 8);
//                    array[2].setMargins(8, 8, 8, 8);
//                    array[3].setMargins(8, 8, 8, 8);
//                    array[4].setMargins(8, 8, 8, 8);
//                    array[5].setMargins(8, 8, 8, 8);
//                    array[6].setMargins(8, 8, 8, 8);
//                    array[7].setMargins(8, 8, 8, 8);
//                    array[8].setMargins(8, 8, 8, 8);
//
//                    array[i].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//                    array[i].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(5)).getId());
//                    array[i].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(7)).getId());
//                }

                }
            }


        }


        return array;

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearAllVideo();
    }

    private void clearAllVideo() {
        removeAllViews();
        mUserViewList.clear();
        mUidList.clear();
        mHandler.removeCallbacks(this);
    }

    @Override
    public void run() {
        if (mStatsManager != null && mStatsManager.isEnabled()) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                RelativeLayout layout = (RelativeLayout) getChildAt(i);
                TextView text = layout.findViewById(layout.hashCode());
                if (text != null) {
                    StatsData data = mStatsManager.getStatsData(mUidList.get(i));
                    String info = data != null ? data.toString() : null;
                    if (info != null) text.setText(info);
                }
            }
            mHandler.postDelayed(this, STATS_REFRESH_INTERVAL);
        }
    }

}
