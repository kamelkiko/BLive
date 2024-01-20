package com.expert.blive.Agora.agoraLive.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.expert.blive.Agora.agoraLive.adapters.AdapterPrivateMessagesPlayer;
import com.expert.blive.utils.App;
import com.expert.blive.Agora.agoraLive.models.ModelPrivateMessagePlayer;
import com.expert.blive.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;


public class PrivateMessagesFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private View view;
    private RecyclerView recycler_private;
    private AdapterPrivateMessagesPlayer adapterPrivateMessagesPlayer;
    private List<ModelPrivateMessagePlayer> list = new ArrayList<>();
    private EditText edit_text_comment;

    private String liveImage, liveUsername, liveUserId;
    private ProgressBar progress_bar;
    private RelativeLayout rl_main;

    public PrivateMessagesFragment() {
        // Required empty public constructor
    }

    public PrivateMessagesFragment(String liveImage, String liveUsername, String liveUserId ) {
        this.liveImage = liveImage;
        this.liveUsername = liveUsername;
        this.liveUserId = liveUserId;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_private_messages, container, false);

        list = App.getSingleton().getPrivateMessagePlayers();
        findIds();
        setRecycler();
        return view;
    }

    private void setRecycler() {
        adapterPrivateMessagesPlayer = new AdapterPrivateMessagesPlayer(getActivity(), list);
        recycler_private.setAdapter(adapterPrivateMessagesPlayer);
//        recycler_private.smoothScrollToPosition(list.size()-1);
        progress_bar.setVisibility(View.GONE);
        rl_main.setVisibility(View.VISIBLE);
    }

    private void findIds() {
        view.findViewById(R.id.img_send).setOnClickListener(this);
        view.findViewById(R.id.close_img).setOnClickListener(this);
        rl_main = view.findViewById(R.id.rl_main);
        progress_bar = view.findViewById(R.id.progress_bar);
        edit_text_comment = view.findViewById(R.id.edit_text_comment);
        recycler_private = view.findViewById(R.id.recycler_private);

        edit_text_comment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    try {
                        String message = edit_text_comment.getText().toString();
                        if (message.isEmpty()) {
                            Toast.makeText(getActivity(), "Message Empty", Toast.LENGTH_SHORT).show();
                        } else {
                            sendPeerMessage(liveUserId, message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    handled = true;
                }
                return handled;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_img:
                dismiss();
                break;

            case R.id.img_send:
                String message = edit_text_comment.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(getActivity(), "Message Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendPeerMessage(liveUserId, message);
                break;
        }
    }

    public void sendPeerMessage(String dst, String content) {


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
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = displaySize(getActivity())[1] * 65 / 100;
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

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
//   AgoraPlayerActivity.isPrivateMessageOpened = false;
    }

}