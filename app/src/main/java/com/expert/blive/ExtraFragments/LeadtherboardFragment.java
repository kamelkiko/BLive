package com.expert.blive.ExtraFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expert.blive.R;


public class LeadtherboardFragment extends Fragment {
    private RecyclerView LeaderboardRV;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_leadtherboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LeaderboardRV=view.findViewById(R.id.LeaderboardRV);

//        LeaderboardRV.setAdapter(new LeatherBoardRVAdapter());


        view.findViewById(R.id.img_back_ranking).setOnClickListener(view1 -> requireActivity().onBackPressed());

//        view.findViewById(R.id.leaderboardTV).setOnClickListener(view1 -> {
//
//            getActivity().getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.profileFramlayout,new WalletFragments()).addToBackStack(null).commit();
//        });


    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.GONE);
    }
}