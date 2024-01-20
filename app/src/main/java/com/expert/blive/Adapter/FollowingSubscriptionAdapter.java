package com.expert.blive.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.expert.blive.HomeActivity.HomeMainCategory.Subscription.DoubleClickListener;
import com.expert.blive.ModelClass.ShowVideoDetail;
import com.expert.blive.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowingSubscriptionAdapter extends RecyclerView.Adapter<FollowingSubscriptionAdapter.ViewHolder> {


    List<ShowVideoDetail> details;

    FollowingVideoCallback followingVideoCallback;

    Context context;


    private int j = 0;

    public FollowingSubscriptionAdapter(List<ShowVideoDetail> details, FollowingVideoCallback followingVideoCallback, Context context) {

        this.followingVideoCallback = followingVideoCallback;
        this.context = context;
        this.details = details;
    }

    public interface FollowingVideoCallback {

        void call_video(int position);

        void videoLike(ShowVideoDetail showVideoDetail, ImageView likeUnlike,TextView textView);

        void commentOnVideo(ShowVideoDetail detail);

        void shareVideo(ShowVideoDetail detail);

    }

    @NonNull
    @NotNull
    @Override
    public FollowingSubscriptionAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.following_subs_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FollowingSubscriptionAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        doubleClick(holder, position, 0);

        if (details.get(position).likeStatus) {
            holder.like_button.setColorFilter(Color.RED);
         doubleClick(holder,position,1);
        } else {
            holder.like_button.setColorFilter(Color.WHITE);
        }

        Glide.with(context).load(details.get(position).getImage()).placeholder(R.drawable.app_logo).into(holder.profile_img1);
        Glide.with(context).load(details.get(position).getThumbnail()).placeholder(R.drawable.app_logo).into(holder.sound_image_layout);
        holder.txtName.setText(details.get(position).getName());
        holder.description.setText(details.get(position).getDescription());
        holder.hashTagTV.setText(details.get(position).getHashtagTitle());

        holder.reliteLikesTV.setText(details.get(position).getLikeCount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (followingVideoCallback != null) {
                    followingVideoCallback.call_video(position);
                    notifyDataSetChanged();
                }
            }
        });


        if (holder.isPlaying()) {
            Log.e("TAG1", "play");
            holder.releasePlayer();
            holder.intiPlayer(details.get(position).getDownloadPath());
        } else {
            Log.e("TAG1", "empty");
            holder.intiPlayer(details.get(position).getDownloadPath());
        }

        holder.like_button.setOnClickListener(view -> {
            if (details.get(position).likeStatus) {
                holder.like_button.setColorFilter(Color.RED);
                followingVideoCallback.videoLike(details.get(position),holder.like_button,holder.reliteLikesTV);
//         doubleClick(holder,position,1);
            } else {
                followingVideoCallback.videoLike(details.get(position),holder.like_button,holder.reliteLikesTV);
                holder.like_button.setColorFilter(Color.WHITE);
            }


        });

        holder.reliteCommentslLayout.setOnClickListener(view -> followingVideoCallback. commentOnVideo(details.get(position)));

        holder.userCommentTV.setText(details.get(position).getCommentCount());

        holder.ll_share.setOnClickListener(view -> {
            followingVideoCallback.shareVideo(details.get(position));
        });


    }

    @Override
    public int getItemCount() {
        return (details != null && details.size() != 0 ? details.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgUserVideoImage, like_button,sound_image_layout;
        SimpleExoPlayerView exoPlayerView;
        SimpleExoPlayer exoPlayer;
        private RelativeLayout clickRL;
        LottieAnimationView relite_lottie_like;
        private long playbackPosition;
        private int currentWindow;
        private boolean playWhenReady;
        ProgressBar progressBar;
        private CircleImageView profile_img1;
        private TextView txtName, reliteLikesTV, userCommentTV,description,hashTagTV;
        private Context context;
        private LinearLayout reliteCommentslLayout,ll_share;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            exoPlayerView = itemView.findViewById(R.id.imgUserVideoImage);
            profile_img1 = itemView.findViewById(R.id.profile_img1);
            txtName = itemView.findViewById(R.id.nameuserMyVideo);
            like_button = itemView.findViewById(R.id.like_button);
            reliteLikesTV = itemView.findViewById(R.id.reliteLikesTV);
            relite_lottie_like = itemView.findViewById(R.id.relite_lottie_like);
            clickRL = itemView.findViewById(R.id.clickRL);
            reliteCommentslLayout = itemView.findViewById(R.id.reliteCommentslLayout);
            userCommentTV = itemView.findViewById(R.id.userCommentTV);
            description = itemView.findViewById(R.id.description);
            sound_image_layout = itemView.findViewById(R.id.sound_image_layout);
            hashTagTV = itemView.findViewById(R.id.heshTagTV);
            ll_share = itemView.findViewById(R.id.ll_share);
        }

        private void intiPlayer(String url) {

            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                exoPlayer = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
                Uri videoURI = Uri.parse(url);
                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
                exoPlayerView.setPlayer(exoPlayer);
                exoPlayer.prepare(mediaSource);
                exoPlayer.setPlayWhenReady(true);
            } catch (Exception e) {
                Log.e("MainAcvtivity", " exoplayer error " + e.toString());
            }
        }

        public boolean isPlaying() {

            return exoPlayer != null
                    && exoPlayer.getPlaybackState() != Player.STATE_ENDED
                    && exoPlayer.getPlaybackState() != Player.STATE_IDLE
                    && exoPlayer.getPlayWhenReady();
        }

        public void releasePlayer() {

            if (exoPlayer != null) {
                playbackPosition = exoPlayer.getCurrentPosition();
                currentWindow = exoPlayer.getCurrentWindowIndex();
                playWhenReady = exoPlayer.getPlayWhenReady();
                exoPlayer.release();
                exoPlayer = null;
            }

        }

    }

    private void doubleClick(ViewHolder holder, int position, int check) {

        holder.clickRL.setOnClickListener(new DoubleClickListener() {

            @Override
            public void onDoubleClick() {
                followingVideoCallback.videoLike(details.get(position), holder.like_button, holder.reliteLikesTV);

                if (j == 2) {

                    holder.like_button.setColorFilter(Color.RED);


                    Toast.makeText(context, "double", Toast.LENGTH_SHORT).show();

                    holder.relite_lottie_like.setVisibility(View.VISIBLE);
                    holder.relite_lottie_like.playAnimation();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            holder.relite_lottie_like.cancelAnimation();
                            holder.relite_lottie_like.setVisibility(View.GONE);

                        }
                    }, 1000);

                } else {
                    holder.relite_lottie_like.setVisibility(View.VISIBLE);
                    holder.relite_lottie_like.playAnimation();

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {

                        @Override
                        public void run() {

                            holder.relite_lottie_like.cancelAnimation();
                            holder.relite_lottie_like.setVisibility(View.GONE);
                        }
                    }, 1000);
                }
            }
        });
    }

    public void loadData(List<ShowVideoDetail> details) {

        this.details = details;
    }

}
