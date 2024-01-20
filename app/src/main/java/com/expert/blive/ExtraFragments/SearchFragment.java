package com.expert.blive.ExtraFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expert.blive.Adapter.SearchRVAdapter;
import com.expert.blive.R;

public class SearchFragment extends Fragment {

private RecyclerView searchFragRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchFragRV=view.findViewById(R.id.searchFragRV);
        searchFragRV.setAdapter(new SearchRVAdapter());
        view.findViewById(R.id.back_search).setOnClickListener(view1 -> requireActivity().onBackPressed());
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.rl_main).setVisibility(View.GONE);
    }
}