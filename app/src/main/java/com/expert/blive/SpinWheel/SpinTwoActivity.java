package com.expert.blive.SpinWheel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.expert.blive.ModelClass.CoinsAddedModel;
import com.expert.blive.ModelClass.CoinsDeductedModel;
import com.expert.blive.ModelClass.TotalCoinModal;
import com.expert.blive.SpinWheel.SkUtil.AppUtil;
import com.expert.blive.SpinWheel.SkUtil.PreferencesManager;
import com.expert.blive.SpinWheel.SkUtil.SoundManagerClass;
import com.expert.blive.SpinWheel.SkUtil.SpinWheelManager;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import java.util.Random;

public class SpinTwoActivity extends AppCompatActivity implements View.OnClickListener{
    public static Handler mHandler;
    int _StopWeelPoint = 0;
    int _betCounter = 0;
    private Button _btnClose;
    private ImageView _btnMinusBet;
    private ImageView _btnPluseBet;
    private Button _btnSpinn;
    private ConstraintLayout _coinAnimations;
    private Typeface _font;
    private ImageView _imgWheel;
    private TextView _lblScreenTitle;
    private SpinWheelManager _spinWheelManager;
    private TextView _txtBetValue;
    private TextView _txtCoin;
    private TextView _txtResult;
    int degree = 0;
    String totalCoin, count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spin_two);


        onInit();
        getTotalCoin();
        onVerb();
        onEvent();
        onResetData();
        onRefreshData();
        initHandler();


    }

    private void onInit() {
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.coinAnimations);
        this._coinAnimations = constraintLayout;
        constraintLayout.setVisibility(View.GONE);
        this._txtResult = (TextView) findViewById(R.id.txtResult);
        this._btnPluseBet = (ImageView) findViewById(R.id.btnPluseBet);
        this._txtBetValue = (TextView) findViewById(R.id.txtBetValue);
        this._btnMinusBet = (ImageView) findViewById(R.id.btnMinusBet);
        this._imgWheel = (ImageView) findViewById(R.id.SpinerWheel);
        this._btnSpinn = (Button) findViewById(R.id.btnSpeen);
        this._txtCoin = (TextView) findViewById(R.id.txtCoin);
        this._lblScreenTitle = (TextView) findViewById(R.id.lblScreenTitle);
        this._btnClose = (Button) findViewById(R.id.btnClose);
    }
    private void getTotalCoin() {
        new MvvmViewModelClass().getCoinRootLiveData(CommonUtils.getUserId()).observe(SpinTwoActivity.this, new Observer<TotalCoinModal>() {
            @Override
            public void onChanged(TotalCoinModal totalCoinModal) {
                if (totalCoinModal.getSuccess().equalsIgnoreCase("1")) {
                    _txtCoin.setText(totalCoinModal.getDetails().getPurchasedCoin());

                    totalCoin = totalCoinModal.getDetails().getPurchasedCoin();

                } else {
                    Toast.makeText(SpinTwoActivity.this, "" + totalCoinModal.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void onVerb() {
        Typeface createFromAsset = Typeface.createFromAsset(getAssets(), "cantora_one.ttf");
        this._font = createFromAsset;
        this._txtResult.setTypeface(createFromAsset);
        this._txtCoin.setTypeface(this._font);
        this._lblScreenTitle.setTypeface(this._font);
        this._txtBetValue.setTypeface(this._font);
        this._spinWheelManager = new SpinWheelManager(new SpinWheelManager.FollowersDetailsAdapterListener() {
            @Override
            public void Start(Animation animation) {
                SoundManagerClass.SpinSound(SpinTwoActivity.this);
            }

            @Override
            public void Stop(Animation animation) {
                SpinTwoActivity.this._coinAnimations.setVisibility(View.VISIBLE);
                final int GetDailySpinerValue = AppUtil.GetDailySpinerValue("BigWinner", SpinTwoActivity.this._StopWeelPoint) * AppUtil.BigWinnerBetPrice[SpinTwoActivity.this._betCounter];
                if (GetDailySpinerValue != 0) {
                    SpinTwoActivity.this._txtResult.setText("Congratulations \n You Win");
                    SoundManagerClass.WinnerSound(SpinTwoActivity.this);

                    Dialog alertDialog = new Dialog(SpinTwoActivity.this);
                    alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    alertDialog.setContentView(R.layout.winner_layout);
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    TextView textView = alertDialog.findViewById(R.id.txtmsg);
                    Button btnOk = alertDialog.findViewById(R.id.btnOk);

                    textView.setText("Congratulations \n You Win " + GetDailySpinerValue + " Coins");


                    btnOk.setOnClickListener(v -> {

                        getTotalCoin();

                        new MvvmViewModelClass().coinsAddedModelLiveData(SpinTwoActivity.this, CommonUtils.getUserId(), String.valueOf(GetDailySpinerValue)).observe(SpinTwoActivity.this, new Observer<CoinsAddedModel>() {
                            @Override
                            public void onChanged(CoinsAddedModel coinsAddedModel) {
                                if (coinsAddedModel.getStatus().equalsIgnoreCase("1"))
                                {
                                    Toast.makeText(SpinTwoActivity.this, ""+coinsAddedModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(SpinTwoActivity.this, ""+coinsAddedModel.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                        alertDialog.dismiss();

                    });

                    alertDialog.show();


                    return;
                }
                SpinTwoActivity.this._txtResult.setText("Better Luck \n Next Time");
                SoundManagerClass.LoserSound(SpinTwoActivity.this);

            }
        });
    }
    private void onEvent() {
        this._btnClose.setOnClickListener(this);
        this._btnSpinn.setOnClickListener(this);
        this._btnPluseBet.setOnClickListener(this);
        this._btnMinusBet.setOnClickListener(this);
    }
    private void onBetCounter(int i) {
        TextView textView = this._txtBetValue;
        textView.setText(AppUtil.BigWinnerBetPrice[i] + "");
        count = String.valueOf(AppUtil.BigWinnerBetPrice[i]);

    }
    public void onClick(View view) {
        SoundManagerClass.ButtonSound(this);
        if (view == this._btnClose) {

            AppUtil.StopButtoSomeTime(this, view);
            mHandler = null;
            super.onBackPressed();
        } else if (view == this._btnPluseBet) {
            int length = AppUtil.BigWinnerBetPrice.length - 1;
            int i = this._betCounter;
            if (length > i) {
                int i2 = i + 1;
                this._betCounter = i2;
                onBetCounter(i2);
            }
        } else if (view == this._btnMinusBet) {
            int i3 = this._betCounter;
            if (i3 != 0) {
                int i4 = i3 - 1;
                this._betCounter = i4;
                onBetCounter(i4);
            }
        } else if (view == this._btnSpinn) {
            int GetCoins = Integer.parseInt(totalCoin);
            if (GetCoins >= AppUtil.BigWinnerBetPrice[this._betCounter]) {
                onSpeenWhell();

                PreferencesManager.SetCoins(this, GetCoins - AppUtil.BigWinnerBetPrice[this._betCounter]);

                new MvvmViewModelClass().coinsDeductedModelLiveData(SpinTwoActivity.this, CommonUtils.getUserId(), String.valueOf(AppUtil.BigWinnerBetPrice[this._betCounter])).observe(SpinTwoActivity.this, new Observer<CoinsDeductedModel>() {
                    @Override
                    public void onChanged(CoinsDeductedModel coinsDeductedModel) {
                        if (coinsDeductedModel.getStatus().equalsIgnoreCase("1"))
                        {
                            Toast.makeText(SpinTwoActivity.this, ""+coinsDeductedModel.getMessage(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SpinTwoActivity.this, ""+coinsDeductedModel.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                return;
            }
            AppUtil.ShowDiloag(this, this._font, "sorry you have no enough \n Coin To play", false);
        }
    }
    private void onSpeenWhell() {
        this._btnSpinn.setClickable(false);
        this._btnClose.setClickable(false);
        this._btnPluseBet.setClickable(false);
        this._btnMinusBet.setClickable(false);
        int nextInt = new Random().nextInt(12);
        this._StopWeelPoint = nextInt;
        int GetSpinValue = AppUtil.GetSpinValue("12", nextInt) + 3600;
        this.degree = GetSpinValue;
        this._spinWheelManager.onStartSpin(this._imgWheel, GetSpinValue);
    }
    private void onRefreshData() {
        TextView textView = this._txtCoin;
        textView.setText(totalCoin);
    }
    private void onResetData() {
        this._coinAnimations.setVisibility(View.GONE);
        this._btnSpinn.setClickable(true);
        this._btnClose.setClickable(true);
        this._btnPluseBet.setClickable(true);
        this._btnMinusBet.setClickable(true);
        this._betCounter = 0;
        TextView textView = this._txtBetValue;
        textView.setText("" + AppUtil.BigWinnerBetPrice[this._betCounter]);
    }
    private void initHandler() {
        mHandler = new Handler(new Handler.Callback() {
            public boolean handleMessage(Message message) {
                if (message.what == 100) {
                    SpinTwoActivity.this.onRefreshData();
                    return false;
                } else if (message.what != 680) {
                    return false;
                } else {
                    SpinTwoActivity.this.onResetData();
                    return false;
                }
            }
        });
    }


}