package com.expert.blive.HomeActivity.HomeMainCategory.Live.LiveCategory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expert.blive.HomeActivity.HomeMainCategory.Live.LiveEndFragment;
import com.expert.blive.R;

import org.jetbrains.annotations.NotNull;

public class PublicLiveFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_public_live, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.public_live).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new LivePublicFragment()).addToBackStack(null).commit());

        view.findViewById(R.id.cancel_live).setOnClickListener(view12 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new LiveEndFragment()).addToBackStack(null).commit());


    }
}