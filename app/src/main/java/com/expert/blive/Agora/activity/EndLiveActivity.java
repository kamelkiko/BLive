package com.expert.blive.Agora.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.expert.blive.databinding.ActivityEndLiveBinding;

public class EndLiveActivity extends AppCompatActivity {
    ActivityEndLiveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEndLiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        onClicks();


    }

    private void onClicks() {

        binding.backButton.setOnClickListener(view -> {


            onBackPressed();
        });
    }
}