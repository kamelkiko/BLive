package com.expert.blive.ExtraFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expert.blive.Adapter.live2RVAdapter;
import com.expert.blive.R;
public class live2Fragment extends Fragment {

    private RecyclerView recycler_view_action_bar,recycler_view_left,recycler_view_right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycler_view_action_bar=view.findViewById(R.id.recycler_view_action_bar);
        recycler_view_left=view.findViewById(R.id.recycler_view_left);
        recycler_view_right=view.findViewById(R.id.recycler_view_right);

        recycler_view_action_bar.setAdapter(new live2RVAdapter());
        recycler_view_left.setAdapter(new live2RVAdapter());
        recycler_view_right.setAdapter(new live2RVAdapter());

        view.findViewById(R.id.img_cross).setOnClickListener(view12 -> requireActivity().onBackPressed());
        view.findViewById(R.id.ll_1).setOnClickListener(view1 -> getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new FollowLiveFragment()).addToBackStack(null).commit());
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.rl_main).setVisibility(View.GONE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.GONE);
    }
}