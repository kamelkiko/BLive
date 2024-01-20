package com.expert.blive.Agora.openvcall.ui.layout;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.R;


public class VideoUserStatusHolder extends RecyclerView.ViewHolder {
    public final RelativeLayout mMaskView;

    public final ImageView mAvatar;
    public final ImageView mIndicator;

    public final LinearLayout mVideoInfo;

    public final TextView mMetaData;
    public View view;

    public VideoUserStatusHolder(View v) {
        super(v);

        mMaskView = (RelativeLayout) v.findViewById(R.id.user_control_mask);
        mAvatar = (ImageView) v.findViewById(R.id.default_avatar);
        mIndicator = (ImageView) v.findViewById(R.id.indicator);

        mVideoInfo = (LinearLayout) v.findViewById(R.id.video_info_container);

        mMetaData = (TextView) v.findViewById(R.id.video_info_metadata);
        view = (FrameLayout) v.findViewById(R.id.video_view_container);
    }
}
