package com.expert.blive.ExtraFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expert.blive.R;

public class FindFriends extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_find_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.findFriendsSearchImg).setOnClickListener(view1 -> {
//
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.main_frame_layout,new SearchFragment()).addToBackStack(null).commit();
//
//        });

        view.findViewById(R.id.back_img_findfriend).setOnClickListener(view12 -> requireActivity().onBackPressed());
        view.findViewById(R.id.findFriendsSearchImg).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new LeadtherboardFragment()).addToBackStack(null).commit());
    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.GONE);
    }
}