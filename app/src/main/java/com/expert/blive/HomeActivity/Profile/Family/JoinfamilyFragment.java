package com.expert.blive.HomeActivity.Profile.Family;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.expert.blive.HomeMainActivity;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.JoinfamilyAdapter;
import com.expert.blive.ModelClass.Family.FamilyDetail;
import com.expert.blive.ModelClass.Family.FamilyRoot;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import java.util.List;

public class JoinfamilyFragment extends Fragment implements JoinfamilyAdapter.Callback {
    RecyclerView recycler;
    List<FamilyDetail> list;
    JoinfamilyAdapter joinfamilyAdapter;
    SwipeRefreshLayout pullToRefresh;
    RelativeLayout rlLoading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_joinfamily, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        finds(view);
        getData();
        swipeDown();
        clicks(view);


        HomeMainActivity.screenStatus = 4;
        rlLoading.setVisibility(View.VISIBLE);
        recycler.setVisibility(View.GONE);
    }

    private void clicks(View view) {

        view.findViewById(R.id.back).setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new MyFamilyFragment()).addToBackStack(null).commit();
        });
    }

    private void swipeDown() {
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();

                pullToRefresh.setRefreshing(false);
            }
        });
    }

    private void getData() {

        new MvvmViewModelClass().all_family_details(CommonUtils.getUserId()).observe(requireActivity(), new Observer<FamilyRoot>() {

            @Override
            public void onChanged(FamilyRoot familyRoot) {
                if (familyRoot !=null){
                    if (familyRoot.getStatus()==1){
                        rlLoading.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();

                        list = familyRoot.getDetail();
                        joinfamilyAdapter = new JoinfamilyAdapter(list,requireContext(),JoinfamilyFragment.this);
                        recycler.setAdapter(joinfamilyAdapter);
                    }else {
                        rlLoading.setVisibility(View.GONE);
                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void finds(View view) {

        recycler=view.findViewById(R.id.recycler);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
        rlLoading=view.findViewById(R.id.rlLoading);
    }

    @Override
    public void openProfile(FamilyDetail familyDetail) {

        if(familyDetail!=null){

            FamilyprofileFragment.detail = familyDetail;

            FamilyprofileFragment.userCheck = 1;

            FamilyprofileFragment.backPress=1;

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new FamilyprofileFragment()).addToBackStack(null).commit();
        }

    }

}