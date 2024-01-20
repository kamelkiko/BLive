package com.expert.blive.SpinWheel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.expert.blive.SpinActivity;
import com.expert.blive.databinding.ActivitySpinNewBinding;

public class SpinNewActivity extends AppCompatActivity {
    ActivitySpinNewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpinNewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnSpin1.setOnClickListener(v -> {
            startActivity(new Intent(SpinNewActivity.this, SpinActivity.class));
        });

        binding.btnSpin2.setOnClickListener(v -> {
            startActivity(new Intent(SpinNewActivity.this, SpinTwoActivity.class));
        });
    }
}