package com.expert.blive.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.expert.blive.ModelClass.OtpClass;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.google.firebase.database.annotations.NotNull;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CommonUtils {
   private static ProgressBar progressBar;
    private static ProgressDialog progressDialog;
    private static AlertDialog alertDialog;


//    public static void backImageClick(View view, Activity activity) {
//        view.findViewById(R.id.imageBack).setOnClickListener(v -> {
//            activity.onBackPressed();
//        });
//    }


    public static void shareVideoDownload(final Context context, final String url) {

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Sharing...");
        progressDialog.show();



    }

    public static void shareLiveStrem(Context context, String id) {
    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
    shareIntent.setType("text/plain");
    shareIntent.getPackage();
    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Insert Subject here");
    String shareMessage = "Hello there,iam invited you to join Ep Live Live Stream.";
    shareMessage = shareMessage + "https://bomshort.live/index.php/api/BessoLive/getSingleLiveUserList?streamId=" + id;
    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMessage);
    context.startActivity(Intent.createChooser(shareIntent, "Share via"));
}
    @SuppressLint("MissingPermission")
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
    public static void openKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.toggleSoftInputFromWindow(view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

//    public static void prettyCount(number: Number): String? {
//        String  suffix = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')
//        val numValue = number.toLong()
//        val value = Math.floor(Math.log10(numValue.toDouble())).toInt()
//        val base = value / 3
//        return if (value >= 3 && base < suffix.size) {
//            DecimalFormat("#0.0").format(
//                    numValue / Math.pow(
//                            10.0,
//                            (base * 3).toDouble()
//                    )
//            ) + suffix[base]
//        } else {
//            DecimalFormat("#,##0").format(numValue)
//        }
//    }

    public static void showProgressDialog(Activity activity) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        View mview = activity.getLayoutInflater().inflate(R.layout.progress_dialog, null);
        alert.setView(mview);
        alertDialog = alert.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        progressBar = mview.findViewById(R.id.wp7progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }

    public static void dismissDialog() {
        progressBar.setVisibility(View.GONE);
        alertDialog.dismiss();
    }
    public static RequestBody getRequestBodyText(String data) {

        return RequestBody.create(MediaType.parse("text/plain"), data);

    }

    public static MultipartBody.Part  getFileData(String path, String parameter) {

        if (path != null) {

            File file = new File(path);

            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            return MultipartBody.Part.createFormData(parameter, file.getName(), requestFile);

        } else {

            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), "");

            return MultipartBody.Part.createFormData(parameter, "", requestFile);

        }
    }

    public static String getUserId() {
        return App.getSharedpref().getString(AppConstant.USERID);
    }

    public static void backPressToExitAppInFragment(final Activity activity, View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (i == KeyEvent.KEYCODE_BACK) {
                        activity.finishAffinity();
                        return true;
                    }
                }
                return false;
            }
        });
    }


    public static String getImage() {

            return App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getImage();


    }

    public static String getName() {

            return App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getName();


    }

    public static String getUserName() {
        if (App.getSharedpref().getString("session").equalsIgnoreCase("1")) {
            return App.getSharedpref().getModel(AppConstant.USER_INFO, OtpClass.class).getDetails().getUsername();
        } else {
            return "";
        }
    }

    public static String prettyCount(Number number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.00").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat().format(numValue);
        }
    }

    public static String printDifference(String startDate, String endDate) {
        //milliseconds
        Date date1 = null;
        Date date2 = null;



        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            date1 = simpleDateFormat.parse(startDate);
            date2 = simpleDateFormat.parse(endDate);

//            printDifference(date1, date2);

        } catch (ParseException e) {
            e.printStackTrace();

        }

        String amount = "";


        long difference = date2.getTime() - date1.getTime();
        int  days = (int) (difference / (1000*60*60*24));
        int    hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        int  min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

        hours = (hours < 0 ? -hours : hours);
        min = (min < 0 ? -min : min);

        if (hours<=0){
            amount = min+"min";

            if (min<=0){
                amount = "less then  min";
            }else {
                amount = min+"min";
            }
        }else {

                amount = hours+ "hr";


        }

        return amount;

    }

    public static void  setAnimation(Context context, String image, SVGAImageView imageView) {

        SVGAParser parser = new SVGAParser(context);

        try {
            parser.decodeFromURL(new URL(image), new SVGAParser.ParseCompletion() {

                @Override
                public void onComplete(@com.google.firebase.database.annotations.NotNull SVGAVideoEntity videoItem) {

                    SVGADrawable drawable = new SVGADrawable(videoItem);
                    imageView.setImageDrawable(drawable);
                    imageView.startAnimation();
                }

                @Override
                public void onError() {

                }
            }, new SVGAParser.PlayCallback() {

                @Override
                public void onPlay(@NonNull List<? extends File> list) {

                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
    public static void setAnimationTwo(Context context, String image, SVGAImageView imageView) {

        SVGAParser    parser = new SVGAParser(context);

        try {
            parser.decodeFromURL(new URL(image), new SVGAParser.ParseCompletion() {

                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {

//                    videoItem.getVideoSize();
//                    imageView.setVideoItem(videoItem);
//                    imageView.startAnimation();

                    SVGADynamicEntity dynamicEntity = new SVGADynamicEntity();
                    dynamicEntity.setDynamicImage(image, "91"); // Here is the KEY implementation.
//                   */
                    SVGADrawable drawable = new SVGADrawable(videoItem);
                    imageView.setImageDrawable(drawable);
                    imageView.startAnimation();

                    imageView.setCallback(new SVGACallback() {
                        @Override
                        public void onPause() {

                        }

                        @Override
                        public void onFinished() {

                        }

                        @Override
                        public void onRepeat() {

                            //SVGASoundManager.INSTANCE.release();

                            videoItem.setMovieItem(null);
                            videoItem.clear();


                            drawable.clear();
                            imageView.clear();
                            imageView.setClearsAfterDetached(true);

                            imageView.setVideoItem(null);
                            imageView.setImageDrawable(null);

                        }

                        @Override
                        public void onStep(int i, double v) {

                        }
                    });

                }

                @Override
                public void onError() {

                }
            },null);
        } catch (Exception e) {
            Toast.makeText(context, "aa" + e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

}
