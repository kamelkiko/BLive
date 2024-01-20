package com.expert.blive.Agora.agoraLive.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.gifdecoder.GifDecoder;
import com.bumptech.glide.gifdecoder.GifHeaderParser;
import com.bumptech.glide.gifdecoder.StandardGifDecoder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.gif.GifBitmapProvider;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.nio.ByteBuffer;

public class GiftAnimWindow extends Dialog {
    private static final String TAG = GiftAnimWindow.class.getSimpleName();
    private String mResource;
    int delay = 0;

    public GiftAnimWindow(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setAnimResource(String resource) {
        mResource = resource;
    }

    @Override
    public void show() {
        FrameLayout layout = new FrameLayout(getContext());
        AppCompatImageView imageView = new AppCompatImageView(getContext());
        layout.addView(imageView);
        setContentView(layout);
        boolean isImage = (!mResource.contains(".gif"));
        if (isImage) {
            delay = 3;
            if (getContext() != null)
                Glide.with(getContext()).load(mResource).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.i("Agora Gift Image : ", e.getMessage());
                        final Handler threadHandler = new Handler();

//                        Timer timer = new Timer();
//                        timer.schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//
//                                dismiss();
//
//                            }
//                        }, 1000);
//                        ExampleThread exampleThread = new ExampleThread(5000);
//                        exampleThread.run();

                        new Thread() {
                            @Override
                            public void run() {
                                threadHandler.postDelayed(new Runnable() {
                                    public void run() {
                                        Log.i("hba", e.getMessage());
                                        dismiss();
                                    }
                                }, 1000);
                            }
                        }.start();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        final Handler threadHandler = new Handler();


//                        Timer timer = new Timer();
//                        timer.schedule(new TimerTask() {
//                            @Override
//                            public void run() {
//
//                                dismiss();
//
//                            }
//                        }, 1000);


//                        ExampleThread exampleThread = new ExampleThread(5000);
//                        exampleThread.run();
                        new Thread() {
                            @Override
                            public void run() {
                                threadHandler.postDelayed(new Runnable() {
                                    public void run() {

                                        dismiss();
                                    }
                                }, 5000);
                            }
                        }.start();
                        Log.i("Agora Gift Image : ", "ready to load");
                        return false;
                    }
                }).into(imageView);

        } else {

            Glide.with(getContext()).asGif().load(mResource).listener(new RequestListener<GifDrawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                            Target<GifDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GifDrawable resource, Object model,
                                               Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
                    GiftGifDrawable giftDrawable = getSelfStoppedGifDrawable(resource);
                    for (int i = 0; i < giftDrawable.gifDecoder.getFrameCount(); i++) {

                        delay += giftDrawable.gifDecoder.getDelay(i);
                    }
//                    Example example = new Example(3000);
//                    new Thread(example).start();
//                    Timer timer = new Timer();
//                    timer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//
//                            dismiss();
//
//                        }
//                    }, 3000);
                    new Handler(getContext().getMainLooper()).postDelayed(GiftAnimWindow.this::dismiss, 3000);
                    return false;
                }

            }).into(imageView);
        }
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    private GiftGifDrawable getSelfStoppedGifDrawable(GifDrawable drawable) {
        GifBitmapProvider provider = new GifBitmapProvider(Glide.get(getContext()).getBitmapPool());
        Transformation transformation = drawable.getFrameTransformation();
        if (transformation == null) {
            transformation = new CenterCrop();
        }

        ByteBuffer byteBuffer = drawable.getBuffer();
        StandardGifDecoder decoder = new StandardGifDecoder(provider);
        decoder.setData(new GifHeaderParser().setData(byteBuffer).parseHeader(), byteBuffer);
        Bitmap bitmap = drawable.getFirstFrame();
        if (bitmap == null) {
            decoder.advance();
            bitmap = decoder.getNextFrame();
        }

        return new GiftGifDrawable(getContext(), decoder, transformation, 0, 0, bitmap);
    }

    public static class GiftGifDrawable extends GifDrawable {
        GifDecoder gifDecoder;

        GiftGifDrawable(Context context, GifDecoder gifDecoder, Transformation<Bitmap> frameTransformation,
                        int targetFrameWidth, int targetFrameHeight, Bitmap firstFrame) {
            super(context, gifDecoder, frameTransformation, targetFrameWidth, targetFrameHeight, firstFrame);
            this.gifDecoder = gifDecoder;
        }
    }

    public class Example implements Runnable {
        long sec;

        public Example(long sec) {
            this.sec = sec;

        }

        @Override
        public void run() {
            try {
                Thread.sleep(sec);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
