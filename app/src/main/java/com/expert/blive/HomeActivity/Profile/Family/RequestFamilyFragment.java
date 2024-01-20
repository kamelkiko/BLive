package com.expert.blive.HomeActivity.Profile.Family;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.expert.blive.HomeMainActivity;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.FanclubRequestAdapter;
import com.expert.blive.ModelClass.Family.FamilyDetail;
import com.expert.blive.ModelClass.Family.FamilyRoot;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import java.util.List;

public class RequestFamilyFragment extends Fragment implements FanclubRequestAdapter.Callback {

    private RecyclerView familyMemberRv;
    private FanclubRequestAdapter fanclubRequestAdapter;
    List<FamilyDetail> list;
    private RelativeLayout rlLoading;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_family, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finnds(view);
        getList();
        HomeMainActivity.screenStatus = 4;
        rlLoading.setVisibility(View.VISIBLE);
        familyMemberRv.setVisibility(View.GONE);
    }

    private void getList() {
        new MvvmViewModelClass().get_join_requests(requireActivity(),CommonUtils.getUserId()).observe(requireActivity(), new Observer<FamilyRoot>() {

            @Override
            public void onChanged(FamilyRoot familyRoot) {
                if (familyRoot !=null){
                    if (familyRoot.getStatus()==1){
                        rlLoading.setVisibility(View.GONE);
                        familyMemberRv.setVisibility(View.VISIBLE);
                        list = familyRoot.getDetail();
                        fanclubRequestAdapter = new FanclubRequestAdapter(list,requireContext(),RequestFamilyFragment.this);
                        familyMemberRv.setAdapter(fanclubRequestAdapter);
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

    private void finnds(View view) {
        familyMemberRv=view.findViewById(R.id.familyMemberRv);
        rlLoading=view.findViewById(R.id.rlLoading);
    }


    @Override
    public void acceptRequest(FamilyDetail detail) {
        String requestId = detail.getRequest_id();
        Toast.makeText(requireContext(), ""+requestId, Toast.LENGTH_SHORT).show();

        new MvvmViewModelClass().accept_reject_join_requests(requireActivity(),CommonUtils.getUserId(),requestId,"1").observe(requireActivity(), new Observer<FamilyRoot>() {

            @Override
            public void onChanged(FamilyRoot familyRoot) {
                if (familyRoot !=null){
                    if (familyRoot.getStatus()==1){
                        rlLoading.setVisibility(View.GONE);
                        familyMemberRv.setVisibility(View.VISIBLE);
                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        getList();
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

}