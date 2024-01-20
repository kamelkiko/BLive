package com.expert.blive.HomeActivity.Profile.Family;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.HomeMainActivity;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.MemberAdapter;
import com.expert.blive.ModelClass.Family.FamilyDetails;
import com.expert.blive.ModelClass.Family.FamilyRoot;
import com.expert.blive.ModelClass.Family.Member;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class MyFamilyFragment extends Fragment implements MemberAdapter.Callback {

    private RelativeLayout createFamily,joinFamily,layout2;
    private LinearLayout layout1;
    private ImageView inviteIV,familyBatchImg,more,more2;
    private TextView familyBatchFamilyName,FamilyDescriptionTv;
    private RelativeLayout rlLoading,inviteButton;
    private NestedScrollView scrollView;
    private MemberAdapter memberAdapter;
    private RecyclerView familyMemberRv;
    List<Member> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_family, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);
        clicks(view);
        hitApi();


        HomeMainActivity.screenStatus = 4;
        rlLoading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
    }
    private void hitApi() {

        new MvvmViewModelClass().getUserFamilyDetails(requireActivity(),CommonUtils.getUserId()).observe(requireActivity(), new Observer<FamilyRoot>() {

            @Override
            public void onChanged(FamilyRoot familyRoot) {
                if (familyRoot !=null){
                    if (familyRoot.getStatus()==1){
                        rlLoading.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.VISIBLE);
                        layout1.setVisibility(View.GONE);
                        sendData(familyRoot.getDetails());
                        getData(familyRoot.getDetails());


                        list = familyRoot.getDetails().getMembers();
                        memberAdapter = new MemberAdapter(list,requireContext(),MyFamilyFragment.this);
                        familyMemberRv.setAdapter(memberAdapter);
                        if (familyRoot.getDetails().getAdmin_id().equalsIgnoreCase(CommonUtils.getUserId())){
                            more.setVisibility(View.VISIBLE);
                            more2.setVisibility(View.GONE);
                            inviteButton.setVisibility(View.VISIBLE);
                        }else {
                            more.setVisibility(View.GONE);
                            more2.setVisibility(View.VISIBLE);
                            inviteButton.setVisibility(View.GONE);
                        }
                    }else {
                        rlLoading.setVisibility(View.GONE);
                        layout1.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.GONE);

                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();

                }
            }

            private void getData(FamilyDetails details) {

                familyBatchFamilyName.setText(details.getFamilyName());
                FamilyDescriptionTv .setText(details.getFamilyDescription());
                Glide.with(familyBatchImg.getContext()).load(details.getFamilyImage()).error(R.drawable.app_logo).into(familyBatchImg);



            }

            private void sendData(FamilyDetails details) {

                inviteButton.setOnClickListener(view -> {
                    if(details!=null){

                        InviteUserFragment.data = details;

                        InviteUserFragment.userCheck = 1;

                        InviteUserFragment.backPress=1;

                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new InviteUserFragment()).addToBackStack(null).commit();
                    }

                });

            }


        });
    }

    private void clicks(View view) {
        more2.setOnClickListener(view1 -> {
            leaveFanclub();
        });

        view.findViewById(R.id.back).setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit();

        });
        view.findViewById(R.id.back2).setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit();

        });

        more.setOnClickListener(view1 -> {
            DeleteFmaliy();
        });

        inviteButton.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new InviteUserFragment()).addToBackStack(null).commit();
        });

        joinFamily.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new JoinfamilyFragment()).addToBackStack(null).commit();

        });

        createFamily.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new CreatefamilyFragment()).addToBackStack(null).commit();
        });

    }

    private void leaveFanclub() {

            final Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.leave_fanclub);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            dialog.findViewById(R.id.yesText).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new MvvmViewModelClass().leave_family(CommonUtils.getUserId()).observe(requireActivity(), new Observer<FamilyRoot>() {

                        @Override
                        public void onChanged(FamilyRoot familyRoot) {
                            if (familyRoot !=null){
                                if (familyRoot.getStatus()==1){
                                    Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    rlLoading.setVisibility(View.GONE);
                                    scrollView.setVisibility(View.VISIBLE);
                                    layout2.setVisibility(View.GONE);
                                    layout1.setVisibility(View.VISIBLE);

                                }else {
                                    Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    rlLoading.setVisibility(View.GONE);
                                    layout1.setVisibility(View.GONE);
                                    layout2.setVisibility(View.VISIBLE);
                                }
                            }else {
                                Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            dialog.findViewById(R.id.noText).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    dialog.dismiss();
                }
            });
    }

    private void DeleteFmaliy() {

        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.delete_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        dialog.findViewById(R.id.yesText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new MvvmViewModelClass().deleteFamily(requireActivity(),CommonUtils.getUserId()).observe(requireActivity(), new Observer<FamilyRoot>() {

                    @Override
                    public void onChanged(FamilyRoot familyRoot) {
                        if (familyRoot !=null){
                            if (familyRoot.getStatus()==1){
                                Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                                layout2.setVisibility(View.GONE);
                                layout1.setVisibility(View.VISIBLE);
                                dialog.dismiss();
                            }else {
                                Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                                layout2.setVisibility(View.VISIBLE);
                                layout1.setVisibility(View.GONE);
                            }
                        }else {
                            Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        dialog.findViewById(R.id.noText).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }


    private void finds(View view) {

        createFamily=view.findViewById(R.id.createFamily);
        joinFamily=view.findViewById(R.id.joinFamily);
        layout1=view.findViewById(R.id.layout1);
        layout2=view.findViewById(R.id.layout2);
        inviteIV=view.findViewById(R.id.inviteIV);
        familyBatchImg=view.findViewById(R.id.familyBatchImg);
        familyBatchFamilyName=view.findViewById(R.id.familyBatchFamilyName);
        FamilyDescriptionTv=view.findViewById(R.id.FamilyDescriptionTv);
        familyMemberRv=view.findViewById(R.id.familyMemberRv);
        more=view.findViewById(R.id.more);
        rlLoading=view.findViewById(R.id.rlLoading);
        scrollView=view.findViewById(R.id.Scroll);
        inviteButton=view.findViewById(R.id.inviteButton);
        more2=view.findViewById(R.id.more2);
    }

    @Override
    public void removeMember(Member member) {

        String memberId = member.getUserId();


        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.remove_member);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        dialog.findViewById(R.id.yesText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MvvmViewModelClass().remove_member(requireActivity(),CommonUtils.getUserId(),memberId).observe(requireActivity(), new Observer<FamilyRoot>() {

                    @Override
                    public void onChanged(FamilyRoot familyRoot) {
                        if (familyRoot !=null){
                            if (familyRoot.getStatus()==1){
                                Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }else {
                                Toast.makeText(requireContext(), ""+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }else {
                            Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        dialog.findViewById(R.id.noText).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }

}