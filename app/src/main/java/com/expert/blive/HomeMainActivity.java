package com.expert.blive;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.ExtraFragments.MessagesFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Live.LiveMainFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Popular.PopularMainFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Subscription.SubcriptionFragment;
import com.expert.blive.ModelClass.OtpRoot;

public class HomeMainActivity extends AppCompatActivity {
    public static  int screenStatus = 0 ;
    private ImageView icon_home, icon_real, icon_message, icon_profile, icon_home2, icon_real2, icon_message2, icon_profile2;
    private OtpRoot otpClass;
    private RelativeLayout home_button, real_button, live_button, chat_button, profile_button;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        findID();
        click();
    }

    private void click() {
        icon_home.setVisibility(View.GONE);
        icon_home2.setVisibility(View.VISIBLE);
        icon_real2.setVisibility(View.GONE);
        icon_real.setVisibility(View.VISIBLE);
        icon_message.setVisibility(View.VISIBLE);
        icon_message2.setVisibility(View.GONE);
        icon_profile.setVisibility(View.VISIBLE);
        icon_profile2.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new PopularMainFragment()).addToBackStack(null).commit();

        home_button.setOnClickListener(view -> {
            icon_home.setVisibility(View.GONE);
            icon_home2.setVisibility(View.VISIBLE);
            icon_real2.setVisibility(View.GONE);
            icon_real.setVisibility(View.VISIBLE);
            icon_message.setVisibility(View.VISIBLE);
            icon_message2.setVisibility(View.GONE);
            icon_profile.setVisibility(View.VISIBLE);
            icon_profile2.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new PopularMainFragment()).addToBackStack(null).commit();
        });

        real_button.setOnClickListener(view -> {
            icon_home.setVisibility(View.VISIBLE);
            icon_home2.setVisibility(View.GONE);
            icon_real2.setVisibility(View.VISIBLE);
            icon_real.setVisibility(View.GONE);
            icon_message.setVisibility(View.VISIBLE);
            icon_message2.setVisibility(View.GONE);
            icon_profile.setVisibility(View.VISIBLE);
            icon_profile2.setVisibility(View.GONE);
             getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new SubcriptionFragment()).addToBackStack(null).commit();
        });

        live_button.setOnClickListener(view -> {
            icon_home.setVisibility(View.VISIBLE);
            icon_home2.setVisibility(View.GONE);
            icon_real2.setVisibility(View.GONE);
            icon_real.setVisibility(View.VISIBLE);
            icon_message.setVisibility(View.VISIBLE);
            icon_message2.setVisibility(View.GONE);
            icon_profile.setVisibility(View.VISIBLE);
            icon_profile2.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new LiveMainFragment()).addToBackStack(null).commit();

        });
        chat_button.setOnClickListener(view -> {
            icon_home.setVisibility(View.VISIBLE);
            icon_home2.setVisibility(View.GONE);
            icon_real2.setVisibility(View.GONE);
            icon_real.setVisibility(View.VISIBLE);
            icon_message.setVisibility(View.GONE);
            icon_message2.setVisibility(View.VISIBLE);
            icon_profile.setVisibility(View.VISIBLE);
            icon_profile2.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MessagesFragment()).addToBackStack(null).commit();
        });

        profile_button.setOnClickListener(view -> {
            icon_home.setVisibility(View.VISIBLE);
            icon_home2.setVisibility(View.GONE);
            icon_real2.setVisibility(View.GONE);
            icon_real.setVisibility(View.VISIBLE);
            icon_message.setVisibility(View.VISIBLE);
            icon_message2.setVisibility(View.GONE);
            icon_profile.setVisibility(View.GONE);
            icon_profile2.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new NewprofileFragment()).addToBackStack(null).commit();
        });
    }

    private void findID() {
        home_button = findViewById(R.id.home_button);
        real_button = findViewById(R.id.real_button);
        live_button = findViewById(R.id.live_button);
        chat_button = findViewById(R.id.chat_button);
        profile_button = findViewById(R.id.profile_button);
        icon_home = findViewById(R.id.icon_home);
        icon_real = findViewById(R.id.icon_real);
        icon_message = findViewById(R.id.icon_message);
        icon_profile = findViewById(R.id.icon_profile);
        icon_home2 = findViewById(R.id.icon_home2);
        icon_real2 = findViewById(R.id.icon_real2);
        icon_message2 = findViewById(R.id.icon_message2);
        icon_profile2 = findViewById(R.id.icon_profile2);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            findViewById(R.id.rl_main).setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            if (screenStatus==1){
        }else {
            icon_home.setVisibility(View.GONE);
            icon_home2.setVisibility(View.VISIBLE);
            icon_real2.setVisibility(View.GONE);
            icon_real.setVisibility(View.VISIBLE);
            icon_message.setVisibility(View.VISIBLE);
            icon_message2.setVisibility(View.GONE);
            icon_profile.setVisibility(View.VISIBLE);
            icon_profile2.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new PopularMainFragment()).addToBackStack(null).commit();
        }
    }
}

