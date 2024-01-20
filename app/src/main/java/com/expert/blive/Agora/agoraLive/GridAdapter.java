package com.expert.blive.Agora.agoraLive;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.expert.blive.Agora.agoraLive.stats.StatsData;
import com.expert.blive.Agora.agoraLive.stats.StatsManager;
import com.expert.blive.Agora.agoraLive.ui.FakeViewControllerModel;
import com.expert.blive.Agora.agoraLive.ui.OnGridClicked;
import com.expert.blive.R;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GridAdapter extends RelativeLayout implements Runnable {
    public static MutableLiveData<FakeViewControllerModel> fakeViewController = new MutableLiveData<>();
    public static MutableLiveData<MultiLiveHostsModel> listMultiLiveData = new MutableLiveData<>();
    public static MutableLiveData<Integer> marginHandleForThreeScreen = new MutableLiveData<>();
    private static final int MAX_USER = 9;
    private static final int STATS_REFRESH_INTERVAL = 2000;
    private static final int STAT_LEFT_MARGIN = 34;
    private static final int STAT_TEXT_SIZE = 10;
    private String hostType = "";

    float radiusCornerValue = 30f;

    private final SparseArray<ViewGroup> mUserViewList = new SparseArray<>(MAX_USER);
    private final List<Integer> mUidList = new LinkedList();
    private StatsManager mStatsManager;
    private Handler mHandler;
    private int mStatMarginBottom;

    private boolean isNotSwapped = true;
    public static boolean isNotHost = true;

    public OnGridClicked onGridClicked;

    public GridAdapter(Context context) {
        super(context);
        init();
    }

    public void onVideoClicked(OnGridClicked onGridClicked) {
        this.onGridClicked = onGridClicked;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public GridAdapter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridAdapter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.user_7);
        mStatMarginBottom = 68;
        mHandler = new Handler(getContext().getMainLooper());
    }

    public void setStatsManager(StatsManager manager) {
        mStatsManager = manager;
    }

    public void addUserVideoSurface(int uid, SurfaceView surface, boolean isLocal) {
        if (surface == null) {
            return;
        }
        int id = -1;
        if (isLocal) {
            if (mUidList.contains(0)) {
                mUidList.remove((Integer) 0);
                mUserViewList.remove(0);
            }

            if (mUidList.size() == MAX_USER) {
                mUidList.remove(0);
                mUserViewList.remove(0);
            }
            id = 0;
        } else {
            if (mUidList.contains(uid)) {
                mUidList.remove((Integer) uid);
                mUserViewList.remove(uid);
            }

            if (mUidList.size() < MAX_USER) {
                id = uid;
            }
        }

        if (id == 0) mUidList.add(0, uid);
        else mUidList.add(uid);

        if (isNotHost) {
            try {
                listMultiLiveData.observe((LifecycleOwner) getContext(), multiLiveHostsModel -> {
                    if (mUidList.size() > 1) {
                        for (int i = 0; i < mUidList.size(); i++) {
                            if (mUidList.get(i) == Integer.parseInt(multiLiveHostsModel.getUid())) {
                                if (isNotSwapped) {
                                    isNotSwapped = false;
                                    //Collections.swap(mUidList, 0, i);
                                }
                            }
                        }
                    }
                });
            } catch (Exception ignored) {
            }
        }

        if (id != -1) {

            mUserViewList.append(uid, createVideoView(surface, uid));


            if (mStatsManager != null) {
                mStatsManager.addUserStats(uid, isLocal);
                if (mStatsManager.isEnabled()) {
                    mHandler.removeCallbacks(this);
                    mHandler.postDelayed(this, STATS_REFRESH_INTERVAL);
                }
            }
            requestGridLayout();
        }
    }

    private ViewGroup createVideoView(SurfaceView surface, int uid) {
        CardView layout = new CardView(getContext());
        layout.setRadius(radiusCornerValue);
        //CardView cardView = new CardView(getContext());

        layout.setId(uid);

        layout.setCardElevation(20);
        LayoutParams videoLayoutParams =
                new LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        layout.addView(surface, videoLayoutParams);

        TextView text = new TextView(getContext());
        text.setId(layout.hashCode());

        LayoutParams textParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        textParams.bottomMargin = mStatMarginBottom;
        textParams.leftMargin = STAT_LEFT_MARGIN;
        text.setTextColor(Color.WHITE);
        text.setTextSize(STAT_TEXT_SIZE);

        ImageView ivAudio = new ImageView(getContext());
        ivAudio.setId(layout.hashCode() + 1);

        LayoutParams ivParams = new LayoutParams(
                40,
                40);

        ivParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        ivParams.topMargin = 15;
        ivParams.leftMargin = 15;

        try {
            Glide.with(getContext()).load(R.raw.music_sound).into(ivAudio);
        } catch (Exception ignored) {
        }
        ivAudio.setColorFilter(ContextCompat.getColor(getContext(), R.color.red),
                android.graphics.PorterDuff.Mode.MULTIPLY);

        ivAudio.setLayoutParams(ivParams);

        layout.addView(ivAudio);
        layout.addView(text, textParams);
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
            CardView view = (CardView) mUserViewList.get(mUidList.get(i));
//            if (StartLiveScreenFragment.check == 1 || LiveFragment.check_status.equals("1")) {
//                view.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.besso_beso));
//            }
            view.setCardElevation(20);
            /*            ImageView imageView = new ImageView(getContext());

            imageView.setId(mUidList.get(i) - 2);
            Paint paint = new Paint();
            Glide.with(getContext()).load(R.raw.sound)
                    .into(imageView);
            PorterDuffColorFilter colorFilter =new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
            paint.setColorFilter(colorFilter);
            imageView.setLayerPaint(paint);

            view.addView(imageView);*/
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
        AtomicInteger bottomMargin = new AtomicInteger();
        int bottomMarginFake = 500;

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        // set for square video of multi live
        height = width;

        LayoutParams[] array = new LayoutParams[size];

        // update live data to show fake views according to multi live users entered
        fakeViewController.postValue(new FakeViewControllerModel(size, width, height, bottomMarginFake));

        marginHandleForThreeScreen.observe((LifecycleOwner) getContext(), integer -> {
            //bottomMargin.set(integer);
        });


        for (int i = 0; i < size; i++) {
            if (i == 0) {
                array[0] = new RelativeLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                array[0].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                // array[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            } else if (i == 1) {
                array[1] = new RelativeLayout.LayoutParams(width / 3, height / 3);

                //array[0].width = width/3;
                //array[0].height = height/3;
                Log.e("getParams", "1");
                array[1].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                //array[0].height = height - height/3;
                //array[0].width = width - width/3;

            } else if (i == 2) {
                Log.e("TAG", "getParams: 2");

                array[2] = new RelativeLayout.LayoutParams(width / 3, height / 3);
                //array[0].width = array[i].width;
                array[1].width = array[2].width;
                //array[0].height = array[i].height;
                array[1].height = array[2].height;
                //array[i - 1].width = array[i].width;
                //array[i - 1].height = array[i].height;

                array[2].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(1)).getId());
                array[2].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            } else if (i == 3) {
                Log.e("TAG", "getParams: 3");

                array[3] = new RelativeLayout.LayoutParams(width / 3, height / 3);
                //array[0].width = array[i].width;
                array[1].width = array[3].width;


                //array[0].height = array[i].height;
                array[1].height = array[3].height;


                array[3].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(2)).getId());
                array[3].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                //array[3].addRule(RelativeLayout.ALIGN_TOP, mUserViewList.get(mUidList.get(i - 1)).getId());
            } else if (i == 4) {
                Log.e("TAG", "getParams: sd");

                array[4] = new RelativeLayout.LayoutParams(width / 3, height / 3);
                //array[0].width = array[i].width;
                array[1].width = array[4].width;


                //array[0].height = array[i].height;
                array[1].height = array[4].height;


                array[4].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(1)).getId());
                array[4].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);

            } else if (i == 5) {
                array[5] = new RelativeLayout.LayoutParams(width / 3, height / 3);
                //array[0].width = array[i].width;
                array[1].width = array[i].width;
                array[2].width = array[i].width;
                array[3].width = array[i].width;
                array[4].width = array[i].width;

                //array[0].height = array[i].height;
                array[1].height = array[i].height;
                array[2].height = array[i].height;
                array[3].height = array[i].height;
                array[4].height = array[i].height;

                array[5].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(4)).getId());
                array[5].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            } else if (i == 6) {
                array[6] = new RelativeLayout.LayoutParams(width / 4, height / 4);
                //array[0].width = array[i].width;
                array[1].width = array[i].width;
                array[2].width = array[i].width;
                array[3].width = array[i].width;
                array[4].width = array[i].width;
                array[5].width = array[i].width;

                //array[0].height = array[i].height;
                array[1].height = array[i].height;
                array[2].height = array[i].height;
                array[3].height = array[i].height;
                array[4].height = array[i].height;
                array[5].height = array[i].height;

                array[6].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(5)).getId());
                array[6].addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            } else if (i == 7) {
                array[7] = new RelativeLayout.LayoutParams(width / 4, height / 4);
                //array[0].width = array[i].width;
                array[1].width = array[i].width;
                array[2].width = array[i].width;
                array[3].width = array[i].width;
                array[4].width = array[i].width;
                array[5].width = array[i].width;
                array[6].width = array[i].width;

                //array[0].height = array[i].height;
                array[1].height = array[i].height;
                array[2].height = array[i].height;
                array[3].height = array[i].height;
                array[4].height = array[i].height;
                array[5].height = array[i].height;
                array[6].height = array[i].height;

                array[7].addRule(RelativeLayout.ABOVE, mUserViewList.get(mUidList.get(3)).getId());
                array[7].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            } else {

                /*array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
                array[0].width = width / 3;
                array[1].addRule(RelativeLayout.BELOW, 0);
                array[1].addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
                array[1].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(0)).getId());
                array[1].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                array[2].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                array[2].addRule(RelativeLayout.RIGHT_OF, 0);
                array[2].addRule(RelativeLayout.ALIGN_TOP, 0);
                array[2].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());
                array[3].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
                array[3].addRule(RelativeLayout.RIGHT_OF, mUserViewList.get(mUidList.get(2)).getId());*/

/*                array[i] = new RelativeLayout.LayoutParams(width / 3, height / 3);
                array[0].width = array[i].width;
                array[0].height = array[i].height;
                array[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                array[0].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

                array[1].width = array[i].width;
                array[1].height = array[i].height;
                array[1].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(0)).getId());
                array[1].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

                array[2].width = array[i].width;
                array[2].height = array[i].height;
                array[2].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(1)).getId());
                array[2].addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);

                array[3].width = array[i].width;
                array[3].height = array[i].height;
                array[3].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(0)).getId());
                array[3].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

                array[4].width = array[i].width;
                array[4].height = array[i].height;
                array[4].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(1)).getId());
                array[4].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(3)).getId());

                array[5].width = array[i].width;
                array[5].height = array[i].height;
                array[5].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(2)).getId());
                array[5].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(4)).getId());


                array[6].width = array[i].width;
                array[6].height = array[i].height;
                array[6].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(3)).getId());
                array[6].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);


                array[7].width = array[i].width;
                array[7].height = array[i].height;
                array[7].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(4)).getId());
                array[7].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(6)).getId());

                array[8].width = array[i].width;
                array[8].height = array[i].height;
                array[8].addRule(RelativeLayout.BELOW, mUserViewList.get(mUidList.get(5)).getId());
                array[8].addRule(RelativeLayout.LEFT_OF, mUserViewList.get(mUidList.get(7)).getId());*/
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
