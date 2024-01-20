package com.expert.blive.ExtraFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expert.blive.Adapter.liveVideoUsersRVAdapter;
import com.expert.blive.R;

public class liveVideoUsers extends Fragment {

    private RecyclerView liveVideoRV;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_video_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        liveVideoRV=view.findViewById(R.id.liveVideoRV);

        liveVideoRV.setAdapter(new liveVideoUsersRVAdapter());

        view.findViewById(R.id.lLayoutLvVideo).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new ChatFragment()).addToBackStack(null).commit());

    }
}