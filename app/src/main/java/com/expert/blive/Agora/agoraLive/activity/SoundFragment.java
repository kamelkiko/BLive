package com.expert.blive.Agora.agoraLive.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.expert.blive.Agora.agoraLive.adapters.AdapterAgoraSounds;
import com.expert.blive.Agora.agoraLive.models.ModelAgoraSounds;
import com.expert.blive.Agora.agoraLive.utils.SoundSelected;
import com.expert.blive.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class SoundFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    AdapterAgoraSounds adapterAgoraSounds;
    private View view;
    private ProgressBar progress_bar;
    private RecyclerView recycler_music_live;
    private List<ModelAgoraSounds.Detail> list;
    private int playingSoundId;
    private ImageView img_pause_songs;
    private LinearLayoutManager linearLayoutManager;
    private boolean isPlaying = false;
    private MaterialCardView card_playing;
    private TextView tv_playing_sound_name;
    private String currentTrack, currentSoundPath;

    public SoundFragment() {
        // Required empty public constructor
    }

    public SoundFragment(int playingSoundId, boolean isPlaying, SoundSelected soundSelected) {
        this.playingSoundId = playingSoundId;
        this.soundSelected = soundSelected;
        this.isPlaying = isPlaying;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sound, container, false);
        findIds();
        return view;
    }

//    private void setRecycler() {
//        LiveMvvm liveMvvm = ViewModelProviders.of(SoundFragment.this).get(LiveMvvm.class);
//        liveMvvm.getLiveAgoraSounds(getActivity()).observe(getActivity(), new Observer<ModelAgoraSounds>() {
//            @Override
//            public void onChanged(ModelAgoraSounds modelAgoraSounds) {
//                if (modelAgoraSounds.getSuccess().equalsIgnoreCase("1")) {
//                    progress_bar.setVisibility(View.GONE);
//                    recycler_music_live.setVisibility(View.VISIBLE);
//                    list = modelAgoraSounds.getDetails();
//                    adapterAgoraSounds = new AdapterAgoraSounds(getActivity(), list, new AdapterAgoraSounds.Select() {
//                        @Override
//                        public void onSelected(int position, String soundPath, String soundId, String soundTitle) {
//                            Log.i("soundsFrahgment selected : ", soundId);
//                            Log.i("soundsFrahgment selected : ", soundPath);
//                            currentTrack = soundTitle;
//                            soundSelected.onLiveSoundSelected(soundPath, soundId,soundTitle);
//
//                            card_playing.setVisibility(View.VISIBLE);
//                            tv_playing_sound_name.setText("Playing :"+soundTitle);
//                            dismiss();
//                        }
//
//                        @Override
//                        public void onStopped(int position, String soundPath, String soundId) {
//                            soundSelected.onSoundStopped(soundPath, soundId);
//                        }
//                    });
//                    Context context;
//                    linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//                    recycler_music_live.setLayoutManager(linearLayoutManager);
//                    recycler_music_live.setAdapter(adapterAgoraSounds);
//                    if (isPlaying) {
//                        checkPlayingSound(String.valueOf(playingSoundId), list);
//                    }
//                } else {
//                    Toast.makeText(getActivity(), modelAgoraSounds.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }

    private void findIds() {
        card_playing = view.findViewById(R.id.card_playing);
        tv_playing_sound_name = view.findViewById(R.id.tv_playing_sound_name);
        img_pause_songs = view.findViewById(R.id.img_pause_songs);
        progress_bar = view.findViewById(R.id.progress_bar);
        recycler_music_live = view.findViewById(R.id.recycler_music_live);

        img_pause_songs.setOnClickListener(this);

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
        layoutParams.height = displaySize(getActivity())[1] * 75 / 100;
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

    SoundSelected soundSelected;

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private void checkPlayingSound(String soundIdSelected, List<ModelAgoraSounds.Detail> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equalsIgnoreCase(soundIdSelected)) {
                tv_playing_sound_name.setText("Playing :"+list.get(i).getSoundTitle());
                currentSoundPath = list.get(i).getSoundPath();
                break;
            }
        }

        if (isPlaying) {
            img_pause_songs.setVisibility(View.VISIBLE);
            card_playing.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_pause_songs:
                card_playing.setVisibility(View.GONE);
                soundSelected.onSoundStopped(currentSoundPath, String.valueOf(playingSoundId));
                break;
        }
    }
}