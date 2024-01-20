package com.expert.blive.HomeActivity.Profile.Family;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.MemberAdapter;
import com.expert.blive.ModelClass.Family.FamilyDetail;
import com.expert.blive.ModelClass.Family.FamilyRoot;
import com.expert.blive.ModelClass.Family.Member;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class FamilyprofileFragment extends Fragment implements MemberAdapter.Callback{

    private ImageView familyBatchImg;
    private TextView familyBatchFamilyName,FamilyDescriptionTv;
    private RecyclerView recycler;
    private AppCompatButton joinFamilyBtn;
    private RecyclerView familyMemberRv;
    private MemberAdapter memberAdapter;
    List<Member> list = new ArrayList<>();
    public static FamilyDetail detail;
    public static int userCheck = 1, backPress = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_familyprofile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        finds(view);
        hitApi();
        setData(detail);
        clicks(view);
    }

    private void hitApi() {
        String familyId = detail.getId();
        new MvvmViewModelClass().get_family_details(familyId).observe(requireActivity(), new Observer<FamilyRoot>() {

            @Override
            public void onChanged(FamilyRoot familyRoot) {
                if (familyRoot !=null){
                    if (familyRoot.getStatus()==1){
//                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();

                        memberAdapter = new MemberAdapter(list,requireContext(),FamilyprofileFragment.this);
                        familyMemberRv.setAdapter(memberAdapter);

                    }else {
//                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void clicks(View view) {
        view.findViewById(R.id.back).setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new JoinfamilyFragment()).addToBackStack(null).commit();
        });
        joinFamilyBtn.setOnClickListener(view1 -> {
            sendRequest();
        });

    }

    private void sendRequest() {

        String familyId = detail.getId();

        new MvvmViewModelClass().send_join_request(CommonUtils.getUserId(),familyId).observe(requireActivity(), new Observer<FamilyRoot>() {

            @Override
            public void onChanged(FamilyRoot familyRoot) {
                if (familyRoot !=null){
                    if (familyRoot.getStatus()==1){
//                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        joinFamilyBtn.setText("Requested");
                    }else {
//                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        joinFamilyBtn.setText("Join");
                    }
                }
            }
        });
    }

    private void setData(FamilyDetail detail) {

        familyBatchFamilyName.setText(detail.getFamilyName());
        Glide.with(familyBatchImg.getContext()).load(detail.getFamilyImage()).error(R.drawable.app_logo).into(familyBatchImg);
        FamilyDescriptionTv.setText(detail.getFamilyDescription());
    }
    private void finds(View view) {

        familyBatchImg=view.findViewById(R.id.familyBatchImg);
        familyBatchFamilyName=view.findViewById(R.id.familyBatchFamilyName);
        FamilyDescriptionTv=view.findViewById(R.id.FamilyDescriptionTv);
        recycler=view.findViewById(R.id.familyMemberRv);
        joinFamilyBtn=view.findViewById(R.id.joinFamilyBtn);
        familyMemberRv=view.findViewById(R.id.familyMemberRv);

    }

    @Override
    public void removeMember(Member member) {

    }

}