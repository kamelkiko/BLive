package com.expert.blive.HomeActivity.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.expert.blive.Adapter.RewordAdapter;
import com.expert.blive.ModelClass.RewordRoot;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.CommonUtils;

import java.util.List;

public class RewordFragment extends Fragment {

    private RecyclerView recycler;
    private RewordAdapter rewordAdapter;
    private List<RewordRoot.Detail> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reword, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);
        getData();
    }

    private void getData() {
        new MvvmViewModelClass().get_claim_dates(CommonUtils.getUserId()).observe(requireActivity(), new Observer<RewordRoot>() {
            @Override
            public void onChanged(RewordRoot response) {
                if (response !=null){
                    if (response.getStatus()==1){
                        list = response.getDetails();
                        rewordAdapter = new RewordAdapter(list);
                        recycler.setAdapter(rewordAdapter);
                    }else {
                        Toast.makeText(requireContext(), ""+response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue......", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void finds(View view) {
        recycler = view.findViewById(R.id.recycler);

    }

}