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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.InviteAdapter;
import com.expert.blive.ModelClass.Family.FamilyDetail;
import com.expert.blive.ModelClass.Family.FamilyDetails;
import com.expert.blive.ModelClass.Family.FamilyRoot;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import java.util.List;

public class InviteUserFragment extends Fragment implements InviteAdapter.Callback {

    private RecyclerView familyMemberRv;
    private InviteAdapter inviteAdapter;
    private EditText search_list;
    private ImageView back;
    public static FamilyDetails data;
    public static int userCheck = 1, backPress = 1;
    private List<FamilyDetail> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invite_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);
        clicks();
        getList();


    }

    private void clicks() {

        back.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new MainFamilyFragment()).addToBackStack(null).commit();
        });
    }

    private void getList() {

        String familyId = data.getId();
        Toast.makeText(requireContext(), "userId"+familyId, Toast.LENGTH_SHORT).show();



        new MvvmViewModelClass().all_users(CommonUtils.getUserId(),familyId).observe(requireActivity(), new Observer<FamilyRoot>() {

            @Override
            public void onChanged(FamilyRoot followingDataModel) {
                if (followingDataModel !=null){
                    if (followingDataModel.getStatus()==1){
//                        Toast.makeText(requireContext(), ""+followingDataModel.getMessage(), Toast.LENGTH_SHORT).show();

                        list = followingDataModel.getDetail();
                        inviteAdapter = new InviteAdapter(list,requireContext(),InviteUserFragment.this);
                        familyMemberRv.setAdapter(inviteAdapter);
                    }else {
//                        Toast.makeText(requireContext(), ""+followingDataModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(requireContext(), "technical issue...", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    private void finds(View view) {
        familyMemberRv=view.findViewById(R.id.familyMemberRv);
        search_list=view.findViewById(R.id.search_list);
        back=view.findViewById(R.id.back);
    }

    @Override
    public void inviteUser(FamilyDetail detail, TextView textView) {
        String familyId = data.getId();
        String userID = detail.getId();
        Toast.makeText(requireContext(), "familyId"+familyId, Toast.LENGTH_SHORT).show();
        Toast.makeText(requireContext(), "userID"+userID, Toast.LENGTH_SHORT).show();

        new MvvmViewModelClass().send_invite_to_user(userID,familyId,CommonUtils.getUserId()).observe(requireActivity(), new Observer<FamilyRoot>() {

            @Override
            public void onChanged(FamilyRoot familyRoot) {
                if (familyRoot !=null){
                    if (familyRoot.getStatus()==1){
//                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        textView.setText("Invited");
                    }else if (familyRoot.getStatus()==2){
//                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        textView.setText("Invite");
                    }
                    else{
//                        Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        textView.setText("Invite");
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}