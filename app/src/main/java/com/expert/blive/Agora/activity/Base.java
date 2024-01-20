package com.expert.blive.Agora.activity;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.expert.blive.Agora.agoraLive.utils.FileUtil.Constants;
import com.expert.blive.Agora.heartview.Constant;
import com.expert.blive.Agora.openvcall.EngineConfig;
import com.expert.blive.Agora.openvcall.model.AGEventHandler;
import com.expert.blive.Agora.openvcall.model.ConstantApp;
import com.expert.blive.Agora.openvcall.model.CurrentUserSettings;
import com.expert.blive.Agora.openvcall.model.MyEngineEventHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import io.agora.rtc.RtcEngine;
import io.agora.rtc.internal.EncryptionConfig;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;


public abstract class Base extends AppCompatActivity {
    private RtcEngine  mRtcEngine= null;
    private EngineConfig mConfig ;
    private MyEngineEventHandler mEventHandler= null;
    private CurrentUserSettings mVideoSettings= new  CurrentUserSettings();

    private final static Logger log = LoggerFactory.getLogger(BaseActivity.class);

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRtc(getApplication());



    }

    private void setRtc(Application application) {

        Application context = application;


        mEventHandler =  new MyEngineEventHandler();
        try {
            mRtcEngine = RtcEngine.create(context, Constants.AGORA_APP_ID, mEventHandler);
        } catch (Exception e) {

            Toast.makeText(context, "23r32ezS23", Toast.LENGTH_SHORT).show();

        }


        mRtcEngine.setChannelProfile(io.agora.rtc.Constants.CHANNEL_PROFILE_COMMUNICATION);
        mRtcEngine.enableVideo();
        /*
          Enables the onAudioVolumeIndication callback at a set time interval to report on which
          users are speaking and the speakers' volume.
          Once this method is enabled, the SDK returns the volume indication in the
          onAudioVolumeIndication callback at the set time interval, regardless of whether any user
          is speaking in the channel.
         */mRtcEngine.enableAudioVolumeIndication(200, 3, false);

        mConfig =  new  EngineConfig();
        final View layout = findViewById(Window.ID_ANDROID_CONTENT);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                initUIandEvent();
            }
        });

    }

        /*
          Sets the channel profile of the Agora RtcEngine.
          The Agora RtcEngine differentiates channel profiles and applies different optimization
          algorithms accordingly. For example, it prioritizes smoothness and low latency for a
          video call, and prioritizes video quality for a video broadcast.


         */
        // Enables the video module.








    protected abstract void initUIandEvent();

    protected abstract void deInitUIandEvent();

    protected void permissionGranted() {
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
            if (isFinishing()) {
                return;
            }

            boolean checkPermissionResult = checkSelfPermissions();

            if ((Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
                // so far we do not use OnRequestPermissionsResultCallback
            }
        }, 500);
    }

    private boolean checkSelfPermissions() {
        return checkSelfPermission(Manifest.permission.RECORD_AUDIO, ConstantApp.PERMISSION_REQ_ID_RECORD_AUDIO) &&
                checkSelfPermission(Manifest.permission.CAMERA, ConstantApp.PERMISSION_REQ_ID_CAMERA);
    }

    @Override
    protected void onDestroy() {
        deInitUIandEvent();
        super.onDestroy();
    }

    public final void closeIME(View v) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0); // 0 force close IME
        v.clearFocus();
    }

    public boolean checkSelfPermission(String permission, int requestCode) {
        log.debug("checkSelfPermission " + permission + " " + requestCode);
        if (ContextCompat.checkSelfPermission(this,
                permission)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{permission},
                    requestCode);
            return false;
        }

        if (Manifest.permission.CAMERA.equals(permission)) {
            permissionGranted();
        }
        return true;
    }



    protected void addEventHandler(AGEventHandler handler) {
    mEventHandler.addEventHandler(handler);


    }

    protected void removeEventHandler(AGEventHandler handler) {
        mEventHandler.removeEventHandler(handler);
    }

    protected CurrentUserSettings vSettings() {
        return mVideoSettings;
    }

    public final void showLongToast(final String msg) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        log.debug("onRequestPermissionsResult " + requestCode + " " + Arrays.toString(permissions) + " " + Arrays.toString(grantResults));
        switch (requestCode) {
            case ConstantApp.PERMISSION_REQ_ID_RECORD_AUDIO: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkSelfPermission(Manifest.permission.CAMERA, ConstantApp.PERMISSION_REQ_ID_CAMERA);
                } else {
                    finish();
                }
                break;
            }
            case ConstantApp.PERMISSION_REQ_ID_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted();
                } else {
                    finish();
                }
                break;
            }
        }
    }
    protected RtcEngine rtcEngine() {
        return mRtcEngine;
    }

    protected EngineConfig config() {
        return mConfig;
    }

    protected int virtualKeyHeight() {
        boolean hasPermanentMenuKey = ViewConfiguration.get(getApplication()).hasPermanentMenuKey();
        if (hasPermanentMenuKey) {
            return 0;
        }

        // Also can use getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = getWindowManager().getDefaultDisplay();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(metrics);
        } else {
            display.getMetrics(metrics);
        }

        int fullHeight = metrics.heightPixels;
        int fullWidth = metrics.widthPixels;

        if (fullHeight < fullWidth) {
            fullHeight ^= fullWidth;
            fullWidth ^= fullHeight;
            fullHeight ^= fullWidth;
        }

        display.getMetrics(metrics);

        int newFullHeight = metrics.heightPixels;
        int newFullWidth = metrics.widthPixels;

        if (newFullHeight < newFullWidth) {
            newFullHeight ^= newFullWidth;
            newFullWidth ^= newFullHeight;
            newFullHeight ^= newFullWidth;
        }

        int virtualKeyHeight = fullHeight - newFullHeight;

        if (virtualKeyHeight > 0) {
            return virtualKeyHeight;
        }

        virtualKeyHeight = fullWidth - newFullWidth;

        return virtualKeyHeight;
    }

    protected final int getStatusBarHeight() {
        // status bar height
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        if (statusBarHeight == 0) {
            log.error("Can not get height of status bar");
        }

        return statusBarHeight;
    }

    protected final int getActionBarHeight() {
        // action bar height
        int actionBarHeight = 0;
        final TypedArray styledAttributes = this.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize}
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        if (actionBarHeight == 0) {
            log.error("Can not get height of action bar");
        }

        return actionBarHeight;
    }


    protected void preview(boolean start, SurfaceView view, int uid) {
        if (start) {
            mRtcEngine.setupLocalVideo(new VideoCanvas(view, VideoCanvas.RENDER_MODE_HIDDEN, uid));
            mRtcEngine.startPreview();
        } else {
            mRtcEngine.stopPreview();
        }
    }

    public final void joinChannel(final String channel, int uid, String accessTo) {
        Log.i("joinChannel: ", channel);
        Log.i("joinChannel: ", accessTo);
        String accessToken = accessTo;
        if (TextUtils.equals(accessToken, "") || TextUtils.equals(accessToken, "<#YOUR ACCESS TOKEN#>")) {
            accessToken = null; // default, no token
        }

        mRtcEngine.joinChannel(accessToken, channel, "OpenVCall", uid);
        mConfig.mChannel = channel;
        enablePreProcessor();
        log.debug("joinChannel " + channel + " " + uid);
    }

    public final void leaveChannel(String channel) {
        log.debug("leaveChannel " + channel);
        mConfig.mChannel = null;
        disablePreProcessor();
        mRtcEngine.leaveChannel();
        mConfig.reset();
    }

    /**
     * Enables image enhancement and sets the options.
     */
    protected void enablePreProcessor() {
        if (Constant.BEAUTY_EFFECT_ENABLED) {
            mRtcEngine.setBeautyEffectOptions(true, Constant.BEAUTY_OPTIONS);
        }
    }

    public final void setBeautyEffectParameters(float lightness, float smoothness, float redness) {
        Constant.BEAUTY_OPTIONS.lighteningLevel = lightness;
        Constant.BEAUTY_OPTIONS.smoothnessLevel = smoothness;
        Constant.BEAUTY_OPTIONS.rednessLevel = redness;
    }


    /**
     * Disables image enhancement.
     */
    protected void disablePreProcessor() {
        // do not support null when setBeautyEffectOptions to false
        mRtcEngine.setBeautyEffectOptions(false, Constant.BEAUTY_OPTIONS);
    }

    protected void configEngine(VideoEncoderConfiguration.VideoDimensions videoDimension, VideoEncoderConfiguration.FRAME_RATE fps, String encryptionKey, String encryptionMode) {
        EncryptionConfig config = new EncryptionConfig();
        if (!TextUtils.isEmpty(encryptionKey)) {
            config.encryptionKey = encryptionKey;

            if (TextUtils.equals(encryptionMode, "AES-128-XTS")) {
                config.encryptionMode = EncryptionConfig.EncryptionMode.AES_128_XTS;
            } else if (TextUtils.equals(encryptionMode, "AES-256-XTS")) {
                config.encryptionMode = EncryptionConfig.EncryptionMode.AES_256_XTS;
            }
            mRtcEngine.enableEncryption(true, config);
        } else {
            mRtcEngine.enableEncryption(false, config);
        }

        log.debug("configEngine " + videoDimension + " " + fps + " " + encryptionMode);
        // Set the Resolution, FPS. Bitrate and Orientation of the video
        mRtcEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(videoDimension,
                fps,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));
    }
}
