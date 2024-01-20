package com.expert.blive.Agora.agoraLive.activity;

import android.os.Bundle;
import android.view.SurfaceView;

import com.expert.blive.Agora.agoraLive.utils.FileUtil.FileUtil;
import com.expert.blive.utils.App;
import com.expert.blive.Agora.activity.BaseActivity;
import com.expert.blive.Agora.agoraLive.rtc.EventHandler;
import com.expert.blive.Agora.agoraLive.utils.FileUtil.Constants;


import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

public abstract class RtcBaseActivity extends BaseActivity implements EventHandler {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerRtcEventHandler(this);
        configEngineVideo();
        joinChannel(App.getSingleton().getAgoraToken());
    }

    private void configEngineVideo() {
        VideoEncoderConfiguration configEngineuration = new VideoEncoderConfiguration(
                Constants.VIDEO_DIMENSIONS[configEngine().getVideoDimenIndex()],
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
        );
        configEngineuration.mirrorMode = Constants.VIDEO_MIRROR_MODES[configEngine().getMirrorEncodeIndex()];
        rtcEngine().setVideoEncoderConfiguration(configEngineuration);
        rtcEngine().setChannelProfile(io.agora.rtc.Constants.CHANNEL_PROFILE_LIVE_BROADCASTING);
        // Enables the video module.

        // Enables the video module.
        rtcEngine().enableVideo();
        rtcEngine().setLogFile(FileUtil.initializeLogFile(this));
        rtcEngine().enableAudio();
    }

    private void joinChannel(String token) {
        // Initialize token, extra info here before joining channel
        // 1. Users can only see each other after they join the
        // same channel successfully using the same app id.
        // 2. One token is only valid for the channel name and uid that
        // you use to generate this token.

//        Log.i("agora Token mine",token);


        rtcEngine().joinChannel(token, configEngine().getChannelName(), "", 0);
    }

    protected SurfaceView   prepareRtcVideo(int uid, boolean local) {
        // Render local/remote video on a SurfaceView

        SurfaceView surface = RtcEngine.CreateRendererView(getApplicationContext());
        if (local) {
            rtcEngine().setupLocalVideo(
                    new VideoCanvas(
                            surface,
                            VideoCanvas.RENDER_MODE_HIDDEN,
                            uid,
                            Constants.VIDEO_MIRROR_MODES[configEngine().getMirrorLocalIndex()]
                    )
            );
        } else {
            rtcEngine().setupRemoteVideo(
                    new VideoCanvas(
                            surface,
                            VideoCanvas.RENDER_MODE_HIDDEN,
                            uid,
                            Constants.VIDEO_MIRROR_MODES[configEngine().getMirrorRemoteIndex()]
                    )
            );
        }
        return surface;
    }

    protected void removeRtcVideo(int uid, boolean local) {
        if (local) {
            rtcEngine().setupLocalVideo(null);
        } else {
            rtcEngine().setupRemoteVideo(new VideoCanvas(null, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeRtcEventHandler(this);
        rtcEngine().leaveChannel();
    }

}
