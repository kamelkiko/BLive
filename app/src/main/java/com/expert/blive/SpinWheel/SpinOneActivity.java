package com.expert.blive.SpinWheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.expert.blive.SpinWheel.SkUtil.AppUtil;
import com.expert.blive.SpinWheel.SkUtil.PreferencesManager;
import com.expert.blive.SpinWheel.SkUtil.SoundManagerClass;
import com.expert.blive.SpinWheel.SkUtil.SpinWheelManager;
import com.expert.blive.SpinWheel.SkUtil.SpinWinnerActivity;
import com.expert.blive.R;

import java.util.Random;

public class SpinOneActivity extends AppCompatActivity implements View.OnClickListener {
    public static Handler mHandler;
    int _StopWeelPoint = 0;
    private Button _btnClose;
    private Button _btnSpinn;
    private ConstraintLayout _coinAnimations;
    private Typeface _font;
    private ImageView _imgWheel;
    private TextView _lblScreenTitle;
    private SpinWheelManager _spinWheelManager;
    private TextView _txtCoin;
    private TextView _txtFreeSpin;
    private TextView _txtResult;
    int degree = 0;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_one);

        onInit();
        onVerb();
        onEvent();
        onRefreshData();
        initHandler();

    }

    private void onInit() {
        this._txtFreeSpin = (TextView) findViewById(R.id.txtFreeSpin);
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.coinAnimations);
        this._coinAnimations = constraintLayout;
        constraintLayout.setVisibility(View.GONE);
        this._txtResult = (TextView) findViewById(R.id.txtResult);
        this._imgWheel = (ImageView) findViewById(R.id.SpinerWheel);
        this._btnSpinn = (Button) findViewById(R.id.btnSpeen);
        this._txtCoin = (TextView) findViewById(R.id.txtCoin);
        this._lblScreenTitle = (TextView) findViewById(R.id.lblScreenTitle);
        this._btnClose = (Button) findViewById(R.id.btnClose);
    }
    private void onVerb() {
        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "CarterOne.ttf");
        this._font = createFromAsset;
        this._txtFreeSpin.setTypeface(createFromAsset);
        this._txtResult.setTypeface(this._font);
        this._txtCoin.setTypeface(this._font);
        this._lblScreenTitle.setTypeface(this._font);

        this._spinWheelManager = new SpinWheelManager(new SpinWheelManager.FollowersDetailsAdapterListener() {

            @Override
            public void Start(Animation animation) {

                SoundManagerClass.SpinSound(SpinOneActivity.this);

            }

            @Override
            public void Stop(Animation animation) {
                SpinOneActivity.this._coinAnimations.setVisibility(View.VISIBLE);
                final int GetDailySpinerValue = AppUtil.GetDailySpinerValue("DailySpin", SpinOneActivity.this._StopWeelPoint);
                if (GetDailySpinerValue != 8) {
                    SpinOneActivity.this._txtResult.setText("Congratulations \n You Win");
                    SoundManagerClass.WinnerSound(SpinOneActivity.this);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            Intent intent = new Intent(SpinOneActivity.this, SpinWinnerActivity.class);
                            intent.putExtra("winnerCoin", GetDailySpinerValue);
                            intent.putExtra("type", "");
                            //SpinOneActivity.this.startActivity(intent);
                        }
                    }, 3000);
                    return;
                }
                SpinOneActivity.this._txtResult.setText("Better Luck \n Next Time");
                SoundManagerClass.LoserSound(SpinOneActivity.this);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(SpinOneActivity.this, SpinLossActivity.class);
                        intent.putExtra("winnerCoin", "0");
                        intent.putExtra("type", "");
                        //SpinOneActivity.this.startActivity(intent);
                    }
                }, 3000);
            }
        });
    }
    private void onEvent() {
        this._btnClose.setOnClickListener(this);
        this._btnSpinn.setOnClickListener(this);
    }
    public void onClick(View view) {
        SoundManagerClass.ButtonSound(this);
        if (view == this._btnClose) {

            mHandler = null;
            super.onBackPressed();
        } else if (view == this._btnSpinn) {
            int GetTodaySpin = PreferencesManager.GetTodaySpin(this);
            if (GetTodaySpin > 0) {
                onSpeenWhell();
                PreferencesManager.SetTodaySpin(this, GetTodaySpin - 1);
                return;
            }
            AppUtil.ShowDiloag(this, this._font, "sorry you have no enough \n Free Spin", false);
        }
    }
    private void onSpeenWhell() {
        this._btnSpinn.setClickable(false);
        this._btnClose.setClickable(false);
        int nextInt = new Random().nextInt(12);
        this._StopWeelPoint = nextInt;
        int GetSpinValue = AppUtil.GetSpinValue("12", nextInt) + 3600;
        this.degree = GetSpinValue;
        this._spinWheelManager.onStartSpin(this._imgWheel, GetSpinValue);
    }
    private void onResetData() {
        this._coinAnimations.setVisibility(View.GONE);
        this._btnSpinn.setClickable(true);
        this._btnClose.setClickable(true);
    }
    private void onRefreshData() {
        TextView textView = this._txtFreeSpin;
        textView.setText("Free Spin : " + PreferencesManager.GetTodaySpin(this));
        TextView textView2 = this._txtCoin;
        textView2.setText(PreferencesManager.GetCoins(this) + "");
    }
    private void initHandler() {
        mHandler = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message message) {
                if (message.what == 100) {
                    SpinOneActivity.this.onRefreshData();
                    return false;
                } else if (message.what != 680) {
                    return false;
                } else {
                    SpinOneActivity.this.onResetData();
                    return false;
                }
            }
        });
    }

}