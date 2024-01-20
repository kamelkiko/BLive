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
import android.widget.TextView;
import android.widget.Toast;

import com.expert.blive.HomeMainActivity;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.JoinfamilyAdapter;
import com.expert.blive.ModelClass.Family.FamilyDetail;
import com.expert.blive.ModelClass.Family.FamilyRoot;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import java.util.List;

public class InvitesFragment extends Fragment implements JoinfamilyAdapter.Callback{

    private JoinfamilyAdapter joinfamilyAdapter;
    private RecyclerView familyMemberRv;
    private TextView text;
    private List<FamilyDetail> details;
    private RelativeLayout rlLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);
        getList();
        HomeMainActivity.screenStatus = 4;
        rlLoading.setVisibility(View.VISIBLE);
        familyMemberRv.setVisibility(View.GONE);
    }

    private void getList() {

        new MvvmViewModelClass().my_invitations(CommonUtils.getUserId()).observe(requireActivity(), new Observer<FamilyRoot>() {

            @Override
            public void onChanged(FamilyRoot familyRoot) {
                if (familyRoot !=null){
                    if (familyRoot.getStatus()==1){
                        rlLoading.setVisibility(View.GONE);
                        familyMemberRv.setVisibility(View.VISIBLE);
                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();

                        details = familyRoot.getDetail();

                        joinfamilyAdapter = new JoinfamilyAdapter(details,requireContext(),InvitesFragment.this);
                        familyMemberRv.setAdapter(joinfamilyAdapter);
                    }else {
                        rlLoading.setVisibility(View.GONE);
                        text.setVisibility(View.VISIBLE);
                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void finds(View view) {
        familyMemberRv=view.findViewById(R.id.familyMemberRv);
        text=view.findViewById(R.id.text);
        rlLoading=view.findViewById(R.id.rlLoading);
    }

    @Override
    public void onResume() {
        super.onResume();

        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }

    @Override
    public void openProfile(FamilyDetail familyDetail) {

    }

}