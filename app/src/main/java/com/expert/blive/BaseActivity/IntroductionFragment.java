package com.expert.blive.BaseActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expert.blive.HomeMainActivity;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;

import org.jetbrains.annotations.NotNull;

public class IntroductionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_introduction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setSESSION();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setSESSION() {
        new Handler().postDelayed(() -> {
            if (App.getSharedpref().getString(AppConstant.SESSION).equalsIgnoreCase("1")) {
                startActivity(new Intent(requireActivity(), HomeMainActivity.class));
            } else {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new LoginFragment()).commit();
            }
        }, 3000);
    }
}