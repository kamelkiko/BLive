package com.expert.blive.Agora.heartview;


import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;

public class HeartDrawable {

    private Drawable mDrawable;

    private Transformation mTransformation;

    private Animation mAnimation;

    private boolean mVisible;

    public HeartDrawable(Drawable drawable) {
        mVisible = true;
        mDrawable = drawable;
        mTransformation = new Transformation();
    }

    public void setAnimation(Animation anim) {
        mAnimation = anim;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setBounds(int left, int top, int right, int bottom) {
        if (mDrawable != null) {
            mDrawable.setBounds(left, top, right, bottom);
        }
    }

    public boolean isVisible() {
        return mVisible;
    }

    public void setVisible(boolean visible) {
        mVisible = visible;
    }

    public void draw(Canvas canvas) {
        if (isVisible()) {
            Drawable d = getDrawable();
            if (d != null) {
                int savedCount = canvas.save();
                if (mAnimation != null) {
                    mAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), mTransformation);
                    canvas.concat(mTransformation.getMatrix());
                    d.setAlpha((int)(255 * mTransformation.getAlpha()));
                }
                d.draw(canvas);
                canvas.restoreToCount(savedCount);
            }
        }
    }
}