package com.expert.blive.HomeActivity.HomeMainCategory.Subscription.category;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.danikula.videocache.HttpProxyCacheServer;
import com.expert.blive.ModelClass.Video.VideoDetail2;
import com.expert.blive.R;
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

import org.jetbrains.annotations.NotNull;


public class ExoPlayerViewFragment extends Fragment implements Player.EventListener {

    private HttpProxyCacheServer proxy;
    public static SimpleExoPlayer previous_player;
    private PlayerView video_player;
    private ImageView back;
    private VideoDetail2 videoDetail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exo_player_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findIds(view);
        onClicks();

        DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        proxy = getProxy();
        videoDetail = (VideoDetail2) getArguments().getSerializable("key");
        String proxyUrl = proxy.getProxyUrl(videoDetail.getVideoPath());
        final SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(requireActivity(), trackSelector);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(requireActivity(),
                Util.getUserAgent(requireActivity(), "Vito Live"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(proxyUrl));
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

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(@Nullable @org.jetbrains.annotations.Nullable Object obj) {
                return super.equals(obj);
            }

            @NonNull
            @NotNull
            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @NonNull
            @NotNull
            @Override
            public String toString() {
                return super.toString();
            }

            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        });


        video_player.setPlayer(player);
        player.setPlayWhenReady(true);
        previous_player = player;
        player.addVideoListener(new VideoListener() {
            @Override
            public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {

                if (pixelWidthHeightRatio > 1.1 || height < width) {

                    video_player.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

                } else {

                    video_player.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);

                }
            }

            @Override


            public void onRenderedFirstFrame() {

            }
        });

    }

    private void findIds(View view) {

        back = view.findViewById(R.id.back);

        video_player = view.findViewById(R.id.video_player);

    }

    private void onClicks() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

    }


    public HttpProxyCacheServer getProxy() {
        return proxy == null ? (proxy = newProxy()) : proxy;
    }

    private HttpProxyCacheServer newProxy() {

        return new HttpProxyCacheServer.Builder(requireActivity())

                .maxCacheSize(1024 * 1024 * 1024)

                .maxCacheFilesCount(20)

                .build();
    }

    public void Release_Previous_Player() {

        if (previous_player != null) {

            previous_player.removeListener(this);
            previous_player.release();

        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (previous_player != null) {

            Release_Previous_Player();

        }

    }

    @Override
    public void onStop() {
        super.onStop();

        if (previous_player != null) {

            Release_Previous_Player();

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (previous_player != null) {

            Release_Previous_Player();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.GONE);
    }
}