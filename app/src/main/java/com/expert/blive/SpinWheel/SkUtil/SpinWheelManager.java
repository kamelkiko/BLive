package com.expert.blive.SpinWheel.SkUtil;

import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class SpinWheelManager {
    public FollowersDetailsAdapterListener onStart;
    public FollowersDetailsAdapterListener onStop;

    public interface FollowersDetailsAdapterListener {
        void Start(Animation animation);

        void Stop(Animation animation);
    }

    public SpinWheelManager(FollowersDetailsAdapterListener followersDetailsAdapterListener) {

        this.onStart = followersDetailsAdapterListener;
        this.onStop = followersDetailsAdapterListener;

    }

    public void onStartSpin(ImageView imageView, int i) {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, (float) i, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setDuration(7200);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationStart(Animation animation) {

                SpinWheelManager.this.onStart.Start(animation);
            }

            public void onAnimationEnd(Animation animation) {

                SpinWheelManager.this.onStop.Stop(animation);
            }
        });
        imageView.startAnimation(rotateAnimation);
    }
}
