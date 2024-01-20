package com.expert.blive.ExtraFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.expert.blive.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import io.agora.rtc.video.BeautyOptions;

public class AgoraBeautifyFragment extends BottomSheetDialogFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private Button btn_set_beauty;
    private View view;
    private SeekBar seekbar_lightening_brightness, seekbar_contrast, seekbar_smoothness, seekbar_redness;
    private int BEAUTY_EFFECT_DEFAULT_CONTRAST = 0;
    private float BEAUTY_EFFECT_DEFAULT_LIGHTNESS = 0.7f;
    private float BEAUTY_EFFECT_DEFAULT_SMOOTHNESS = 0.5f;
    private float BEAUTY_EFFECT_DEFAULT_REDNESS = 0.1f;
    private boolean isEnabled = true;
    private Switch switch_enbale_filters;
    private BeautyOptions myBeautyOptions;
    private TextView beauty;

    onBeautySet onBeautySet;

    public interface onBeautySet {

        void onBeautySetListener(BeautyOptions beautyOptions, boolean isEnabled);
    }

    public AgoraBeautifyFragment() {
// Required empty public constructor
    }

    public AgoraBeautifyFragment(BeautyOptions myBeautyOptions, onBeautySet onBeautySet) {
        this.onBeautySet = onBeautySet;
        this.myBeautyOptions = myBeautyOptions;
    }

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_agora_beautify, container, false);
        findIds(view);

        BEAUTY_EFFECT_DEFAULT_CONTRAST = myBeautyOptions.lighteningContrastLevel;
        BEAUTY_EFFECT_DEFAULT_LIGHTNESS = myBeautyOptions.lighteningLevel;
        BEAUTY_EFFECT_DEFAULT_SMOOTHNESS = myBeautyOptions.smoothnessLevel;
        BEAUTY_EFFECT_DEFAULT_REDNESS = myBeautyOptions.rednessLevel;

        Log.i("Agora Contrast", String.valueOf(BEAUTY_EFFECT_DEFAULT_CONTRAST));
        Log.i("Agora Lightness", String.valueOf(BEAUTY_EFFECT_DEFAULT_LIGHTNESS));
        Log.i("Agora Smoothness", String.valueOf(BEAUTY_EFFECT_DEFAULT_SMOOTHNESS));
        Log.i("Agora Redness", String.valueOf(BEAUTY_EFFECT_DEFAULT_REDNESS));

        seekbar_contrast.setProgress(BEAUTY_EFFECT_DEFAULT_CONTRAST);

        float valBright= BEAUTY_EFFECT_DEFAULT_LIGHTNESS * 10;
        seekbar_lightening_brightness.setProgress((int) valBright);

        float valSmooth= BEAUTY_EFFECT_DEFAULT_SMOOTHNESS * 10;
        seekbar_smoothness.setProgress((int) valSmooth);

        float valRedness= BEAUTY_EFFECT_DEFAULT_REDNESS * 10;
        seekbar_redness.setProgress((int) valRedness);

        Log.i("Agora Progress Contrast", String.valueOf(seekbar_contrast.getProgress()));
        Log.i("Agora Progress Lightness", String.valueOf(seekbar_lightening_brightness.getProgress()));
        Log.i("Agora Progress Smoothness", String.valueOf(seekbar_smoothness.getProgress()));
        Log.i("Agora Progress Redness", String.valueOf(seekbar_redness.getProgress()));
        return view;
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
        bottomSheet.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.trans)));
        layoutParams.height = displaySize(getActivity())[1] * 60 / 100;
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

    private void findIds(View view) {
        beauty = view.findViewById(R.id.tv_beauty);
        beauty.setOnClickListener(this);
        switch_enbale_filters = view.findViewById(R.id.switch_enbale_filters);
        btn_set_beauty = view.findViewById(R.id.btn_set_beauty);
        seekbar_lightening_brightness = view.findViewById(R.id.seekbar_lightening_brightness);
        seekbar_contrast = view.findViewById(R.id.seekbar_contrast);
        seekbar_smoothness = view.findViewById(R.id.seekbar_smoothness);
        seekbar_redness = view.findViewById(R.id.seekbar_redness);

        seekbar_lightening_brightness.setOnSeekBarChangeListener(this);
        seekbar_contrast.setOnSeekBarChangeListener(this);
        seekbar_smoothness.setOnSeekBarChangeListener(this);
        seekbar_redness.setOnSeekBarChangeListener(this);
        btn_set_beauty.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_set_beauty:
                BeautyOptions USER_DEFINED_BEAUTY_OPTIONS = new BeautyOptions(
                        BEAUTY_EFFECT_DEFAULT_CONTRAST,
                        BEAUTY_EFFECT_DEFAULT_LIGHTNESS,
                        BEAUTY_EFFECT_DEFAULT_SMOOTHNESS,
                        BEAUTY_EFFECT_DEFAULT_REDNESS);


                onBeautySet.onBeautySetListener(USER_DEFINED_BEAUTY_OPTIONS, switch_enbale_filters.isChecked());
                dismiss();
                break;
        }
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seekbar_lightening_brightness:
                float modi_light_bright = (float) progress / 10;
                BEAUTY_EFFECT_DEFAULT_LIGHTNESS = modi_light_bright;
                Log.i("Agora Beautify LIGHTNESS", String.valueOf(BEAUTY_EFFECT_DEFAULT_LIGHTNESS));
                break;

            case R.id.seekbar_contrast:

                BEAUTY_EFFECT_DEFAULT_CONTRAST = progress;
                Log.i("Agora Beautify CONTRAST", String.valueOf(BEAUTY_EFFECT_DEFAULT_CONTRAST));
                break;

            case R.id.seekbar_smoothness:
                float modi_smooth = (float) progress / 10;
                BEAUTY_EFFECT_DEFAULT_SMOOTHNESS = modi_smooth;
                Log.i("Agora Beautify SMOOTHNESS", String.valueOf(BEAUTY_EFFECT_DEFAULT_LIGHTNESS));
                break;

            case R.id.seekbar_redness:
                float modi_redness = (float) progress / 10;
                BEAUTY_EFFECT_DEFAULT_REDNESS = modi_redness;
                Log.i("Agora Beautify REDENESS", String.valueOf(BEAUTY_EFFECT_DEFAULT_REDNESS));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}