package com.expert.blive.SpinWheel.SkUtil;

import static com.wajahatkarim3.easyflipview.EasyFlipView.*;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import com.expert.blive.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;


public class AppUtil {
    public static int[] BigWinnerBetPrice = {100, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 300, DEFAULT_FLIP_DURATION, 500, 1000};
    private static Dialog _myAlertDialog = null;
    public static int icn_candy = 0;
    public static int icn_cap = 1;
    public static int icn_diamond_purpul = 2;
    public static int icn_doller = 5;
    public static int icn_gold = 5;
    public static int icn_hat_blue = 4;

    public static int GetDateCurrentAvtar(int i) {
        return i == 1 ? R.drawable.avtar_b : i == 2 ? R.drawable.avtar_c : i == 3 ? R.drawable.avtar_d : i == 4 ? R.drawable.avtar_e : i == 5 ? R.drawable.avtar_f : i == 6 ? R.drawable.avtar_g : i == 7 ? R.drawable.avtar_h : i == 8 ? R.drawable.avtar_i : i == 9 ? R.drawable.avtar_j : i == 10 ? R.drawable.avtar_k : R.drawable.avtar_a;
    }

    public static void StopButtoSomeTime(Context context, final View view) {
        view.setClickable(false);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                view.setClickable(true);
            }
        }, 1000);
    }

    public static String GetDateTime() {
        return DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
    }

    public static int getTodayDailyBonuse(Context context) {
        int nextInt = new Random().nextInt(10);
        if (nextInt == 0) {
            return 1000;
        }
        if (nextInt == 1) {
            return 100;
        }
        if (nextInt == 2) {
            return ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        }
        if (nextInt == 3) {
            return 300;
        }
        if (nextInt == 4) {
            return DEFAULT_FLIP_DURATION;
        }
        if (nextInt == 5) {
            return 500;
        }
        if (nextInt == 6) {
            return 600;
        }
        if (nextInt == 7) {
            return 700;
        }
        if (nextInt == 8) {
            return 800;
        }
        return nextInt == 9 ? 900 : 1000;
    }

    public static int GetSpinValue(String str, int i) {
        if (str == "12") {
            return new int[]{0, 330, 300, 270, 240, 210, 180, 150, 120, 90, 60, 30}[i];
        }
        if (str == "10") {
            return new int[]{0, 324, 288, 252, 216, 180, 144, 108, 72, 36}[i];
        }
        if (str == "16") {
            return new int[]{0, 337, 315, 292, 270, 247, 225, 202, 180, 157, 134, 112, 90, 67, 45, 22}[i];
        }
        return 0;
    }

    public static int GetDailySpinerValue(String str, int i) {
        if (str == "DailySpin") {
            return new int[]{100, 50, 0, 150, 100, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, 50, 0, 150, 100, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION}[i];
        }
        if (str == "FreeCombo") {
            return new int[]{0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2}[i];
        }
        if (str == "Lucky") {
        //    return new int[]{0, 1000, ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED, AdError.SERVER_ERROR_CODE, PathInterpolatorCompat.MAX_NUM_POINTS}[i];
        }
        if (str == "BigWinner") {
            return new int[]{0, 2, 0, 2, 0, 3, 0, 2, 0, 3, 0, 1}[i];
        }
        if (str == "TrueFalse") {
            return new int[]{2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0}[i];
        }
        return 1000;
    }

    public static String GetSpinrType(int i) {
        return new String[]{"Loss", "Scratch Card", "Loss", "FlipCard", "Loss", "Slot Matchin", "Loss", "7Up 7Down", "Loss", "Spin Wheell"}[i];
    }

    public static Boolean GetWinnerObject(int i, int i2) {
        return Boolean.valueOf(new int[]{1, 2, 3, 1, 2, 3, 1, 2, 1, 1, 4, 2, 1, 3, 2, 1}[i2] == i);
    }

    public static int GetScratchCardPrice(Context context) {
        return new int[]{100, 50, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, 300, 150, DEFAULT_FLIP_DURATION, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 500, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 100, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 300, DEFAULT_FLIP_DURATION, 500, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION}[new Random().nextInt(16)];
    }

    public static CardObjectVO[] GetScratchIconList() {
        CardObjectVO[] cardObjectVOArr = {new CardObjectVO(R.drawable.icn_ball, "ball", 0), new CardObjectVO(R.drawable.icn_candy, "candy", 0), new CardObjectVO(R.drawable.icn_cap, "cap", 0), new CardObjectVO(R.drawable.icn_cheri, "cheri", 0), new CardObjectVO(R.drawable.icn_diamond_gold, "diamond", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_hat_green, "hat", 0), new CardObjectVO(R.drawable.icn_sandle_purpul, "sandle", 0)};
        int nextInt = new Random().nextInt(8);
        int nextInt2 = new Random().nextInt(8);
        int nextInt3 = new Random().nextInt(8);
        ArrayList arrayList = new ArrayList();
        arrayList.add(cardObjectVOArr[nextInt]);
        arrayList.add(cardObjectVOArr[nextInt]);
        arrayList.add(cardObjectVOArr[nextInt]);
        arrayList.add(cardObjectVOArr[nextInt2]);
        arrayList.add(cardObjectVOArr[nextInt2]);
        arrayList.add(cardObjectVOArr[nextInt2]);
        arrayList.add(cardObjectVOArr[nextInt3]);
        arrayList.add(cardObjectVOArr[nextInt3]);
        arrayList.add(cardObjectVOArr[nextInt3]);
        Collections.shuffle(arrayList);
        return new CardObjectVO[]{(CardObjectVO) arrayList.get(0), (CardObjectVO) arrayList.get(1), (CardObjectVO) arrayList.get(2), (CardObjectVO) arrayList.get(3), (CardObjectVO) arrayList.get(4), (CardObjectVO) arrayList.get(5), (CardObjectVO) arrayList.get(6), (CardObjectVO) arrayList.get(7), (CardObjectVO) arrayList.get(8)};
    }

    public static CardObjectVO[] GetFlipIconList() {
        CardObjectVO[] cardObjectVOArr = {new CardObjectVO(R.drawable.icn_hat_green, "hat_green", 0), new CardObjectVO(R.drawable.icn_cap, "cap", 0), new CardObjectVO(R.drawable.icn_candy, "candy", 0), new CardObjectVO(R.drawable.icn_ball, "ball", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0), new CardObjectVO(R.drawable.icn_down, "down", 0)};
        ArrayList arrayList = new ArrayList();
        arrayList.add(cardObjectVOArr[0]);
        arrayList.add(cardObjectVOArr[1]);
        arrayList.add(cardObjectVOArr[2]);
        arrayList.add(cardObjectVOArr[3]);
        arrayList.add(cardObjectVOArr[4]);
        arrayList.add(cardObjectVOArr[5]);
        arrayList.add(cardObjectVOArr[6]);
        arrayList.add(cardObjectVOArr[7]);
        arrayList.add(cardObjectVOArr[8]);
        arrayList.add(cardObjectVOArr[9]);
        arrayList.add(cardObjectVOArr[10]);
        arrayList.add(cardObjectVOArr[11]);
        arrayList.add(cardObjectVOArr[12]);
        arrayList.add(cardObjectVOArr[13]);
        arrayList.add(cardObjectVOArr[14]);
        arrayList.add(cardObjectVOArr[15]);
        arrayList.add(cardObjectVOArr[16]);
        arrayList.add(cardObjectVOArr[17]);
        arrayList.add(cardObjectVOArr[18]);
        arrayList.add(cardObjectVOArr[19]);
        arrayList.add(cardObjectVOArr[20]);
        arrayList.add(cardObjectVOArr[21]);
        arrayList.add(cardObjectVOArr[22]);
        arrayList.add(cardObjectVOArr[23]);
        arrayList.add(cardObjectVOArr[24]);
        Collections.shuffle(arrayList);
        return new CardObjectVO[]{(CardObjectVO) arrayList.get(0), (CardObjectVO) arrayList.get(1), (CardObjectVO) arrayList.get(2), (CardObjectVO) arrayList.get(3), (CardObjectVO) arrayList.get(4), (CardObjectVO) arrayList.get(5), (CardObjectVO) arrayList.get(6), (CardObjectVO) arrayList.get(7)};
    }

    public static CardObjectVO GetUpDownBetCard(Context context) {
        int nextInt = new Random().nextInt(52);
        return new CardObjectVO(new int[]{R.drawable.c_1, R.drawable.c_2, R.drawable.c_3, R.drawable.c_4, R.drawable.c_5, R.drawable.c_6, R.drawable.d_1, R.drawable.d_2, R.drawable.d_3, R.drawable.d_4, R.drawable.d_5, R.drawable.d_6, R.drawable.h_1, R.drawable.h_2, R.drawable.h_3, R.drawable.h_4, R.drawable.h_5, R.drawable.h_6, R.drawable.s_1, R.drawable.s_2, R.drawable.s_3, R.drawable.s_4, R.drawable.s_5, R.drawable.s_6, R.drawable.c_7, R.drawable.d_7, R.drawable.h_7, R.drawable.s_7, R.drawable.c_8, R.drawable.c_9, R.drawable.c_10, R.drawable.c_11, R.drawable.c_12, R.drawable.c_13, R.drawable.d_8, R.drawable.d_9, R.drawable.d_10, R.drawable.d_11, R.drawable.d_12, R.drawable.d_13, R.drawable.h_8, R.drawable.h_9, R.drawable.h_10, R.drawable.h_11, R.drawable.h_12, R.drawable.h_13, R.drawable.s_8, R.drawable.s_9, R.drawable.s_10, R.drawable.s_11, R.drawable.s_12, R.drawable.s_13}[nextInt], "", nextInt + 1);
    }

    public static void ShowDiloag(final Context context, Typeface typeface, String str, final boolean z) {
        Dialog dialog = new Dialog(context);
        _myAlertDialog = dialog;
        dialog.setContentView(R.layout.spin_common_alert);
        TextView textView = (TextView) _myAlertDialog.findViewById(R.id.txtmsg);
        textView.setTypeface(typeface);
        textView.setText(str);
        ((Button) _myAlertDialog.findViewById(R.id.btnOk)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (AppUtil._myAlertDialog != null) {
                    if (z) {
                       // AppUtil.showAdsClick(context);
                    }
                    SoundManagerClass.ButtonSound(context);
                    AppUtil._myAlertDialog.dismiss();
                    Dialog unused = AppUtil._myAlertDialog = null;
                }
            }
        });
        _myAlertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        _myAlertDialog.show();
    }


    public static void share(Context context, Bitmap bitmap) {
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.share_benner);
        }
        try {
            new ShareImageTask(context, bitmap).execute(new Void[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class ShareImageTask extends AsyncTask<Void, Void, Uri> {
        Bitmap bitmap;
        Context context;

        public ShareImageTask(Context context2, Bitmap bitmap2) {
            this.context = context2;
            this.bitmap = bitmap2;
        }

        public final Uri doInBackground(Void... voidArr) {
            return AppUtil.saveImage(this.context, this.bitmap);
        }

        public void onPostExecute(Uri uri) {
            super.onPostExecute(uri);
            if (uri != null) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.putExtra("android.intent.extra.STREAM", uri);
                intent.putExtra("android.intent.extra.TEXT", "" + "https://play.google.com/store/apps/details?id=" + this.context.getPackageName());
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setType("image/png");
                this.context.startActivity(intent);
            }
        }
    }

    public static Uri saveImage(Context context, Bitmap bitmap) {
        Uri uri;
        try {
            new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "share").mkdirs();
            File file = new File(Environment.getExternalStorageDirectory().getPath(), "wallpaper.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            if (!Environment.getExternalStorageState().equalsIgnoreCase("mounted")) {
                return null;
            }
            if (Build.VERSION.SDK_INT < 24) {
                uri = Uri.parse("file:///" + Environment.getExternalStorageDirectory().getPath() + File.separator + "wallpaper.jpg");
            } else {
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            }
            return uri;
        } catch (IOException unused) {
            return null;
        }
    }

}
