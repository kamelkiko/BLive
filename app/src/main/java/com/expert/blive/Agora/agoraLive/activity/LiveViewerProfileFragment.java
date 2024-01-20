package com.expert.blive.Agora.agoraLive.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.expert.blive.Agora.agoraLive.adapters.LiveProfileViewerAdapter;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSetUserViewer;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;

public class
LiveViewerProfileFragment extends BottomSheetDialogFragment {
    private View view;
    private RecyclerView recyclerView;
    private LiveMvvm liveMvvm;
    List<ModelSetUserViewer> listviewers = new ArrayList<>();
LiveProfileViewerAdapter liveProfileViewerAdapter;
    private boolean isLiveUser = false;
    boolean value;
    private SwipeRefreshLayout swipeRefreshLayout;
    ScheduledExecutorService exec;


    public LiveViewerProfileFragment() {
// Required empty public constructor
    }

    public LiveViewerProfileFragment(List<ModelSetUserViewer> listviewers, boolean value) {
        this.listviewers = listviewers;
        this.value = value;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_liveprofileviewer, container, false);
        liveMvvm = new LiveMvvm();
        findIds();
        setAdapter();
        return view;
    }


    private void setAdapter() {
        liveProfileViewerAdapter = new LiveProfileViewerAdapter(requireContext(), listviewers, new LiveProfileViewerAdapter.SelectUser() {
            @Override
            public void onLiveUserViewerSelected(int position, String userId) {
                openOtherUserProfile(userId, false);
            }
        });
        recyclerView.setAdapter(liveProfileViewerAdapter);
    }

    private void openOtherUserProfile(String otherUserId, boolean isLiveUserId) {
//        try {
//            UserDetailsFragment userDetailsFragment = new UserDetailsFragment(otherUserId, isLiveUserId, false, false, value);
//            userDetailsFragment.show(getChildFragmentManager(), userDetailsFragment.getTag());
//            isLiveUser = false;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupRatio(bottomSheetDialog);
            }
        });
        return dialog;
    }

    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
// bottomSheet.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.trans1)));
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = displaySize(getActivity())[1] * 35 / 100;
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private static int[] displaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.i("height", String.valueOf(height));
        Log.i("width", String.valueOf(width));
        return new int[]{width, height};
    }


    private void findIds() {
        recyclerView = view.findViewById(R.id.rv_profileViewers);
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
//execute code
                setAdapter();
                Log.i("adapter", "positiver");
            }
        };

        Timer timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1000, 2000);
    }


    @Override
    public void onResume() {
        super.onResume();

    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        exec.shutdownNow();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        exec.shutdownNow();
//    }
}
