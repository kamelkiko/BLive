package com.expert.blive.HomeActivity.Profile.OtherOption;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import com.expert.blive.Agora.agoraLive.activity.OtherUserDataModel;
import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.CommonUtils;
import java.util.HashMap;

public class OtherOptionFragment extends Fragment {
    private LinearLayout block_user, store ;
    private RelativeLayout InvisibleVisitor,Mysterious;
    private ImageView back;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch invisibleVisitor,mysterious;
    private MvvmViewModelClass viewModelClass;
    private String switchStatus ,status;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_other_option, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findId(view);
        account();
        hitApi();
        onClick();

    }

    private void account() {

        if (NewprofileFragment.accountCheck.equalsIgnoreCase("1")){
            invisibleVisitor.setChecked(true);
            switchStatus = "1";
        }else {
            invisibleVisitor.setChecked(false);
            switchStatus = "0";
        }
        if (NewprofileFragment.mysteriousCheck.equalsIgnoreCase("1")){
            mysterious.setChecked(true);
            status = "1";
        }else {
            mysterious.setChecked(false);
            status = "0";
        }

    }

    private void hitApi() {
        HashMap<String, String> data = new HashMap<>();
        data.put("userId", CommonUtils.getUserId());
        data.put("otherUserId", CommonUtils.getUserId());
        viewModelClass.someFunctionality(requireActivity(),data).observe(requireActivity(), response -> {
            if (response !=null){if(response.getStatus().equalsIgnoreCase("1")){getStatus(response.getDetails());}}
        });
    }

    private void getStatus(OtherUserDataModel.Details details) {
        if (details.getBuy()){
            invisibleVisitor.setVisibility(View.VISIBLE);
            mysterious.setVisibility(View.VISIBLE);
        }else {
            invisibleVisitor.setVisibility(View.GONE);
            mysterious.setVisibility(View.GONE);
            InvisibleVisitor.setOnClickListener(view -> Toast.makeText(requireContext(), "Please Buy VIP", Toast.LENGTH_SHORT).show());
            Mysterious.setOnClickListener(view -> Toast.makeText(requireContext(), "Please Buy VIP", Toast.LENGTH_SHORT).show());
        }
    }
    private void onClick() {

        mysterious.setOnCheckedChangeListener((compoundButton, b) -> {
            if (status == "0"){
                status = "1";
                hitApiMysterious(status);
            }else {
                status = "0";
                hitApiMysterious(status);
            }
        });

        invisibleVisitor.setOnCheckedChangeListener((compoundButton, b) -> {
            if (switchStatus == "0"){
                switchStatus = "1";
                invisibleVisitor.setChecked(true);
                hitApichange_settings(switchStatus);
            }else {
                switchStatus = "0";
                invisibleVisitor.setChecked(false);
                hitApichange_settings(switchStatus);
            }
        });

        back.setOnClickListener(view -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit());

    }

    private void hitApiMysterious(String status) {
        viewModelClass.change_settings(CommonUtils.getUserId(),status,"2").observe(requireActivity(), response -> {
            if (response !=null){ if (response.getStatus()==1){}else {}
            }else {Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();}
        });

    }
    private void hitApichange_settings(String switchStatus) {
        viewModelClass.change_settings(CommonUtils.getUserId(),switchStatus,"1").observe(requireActivity(), response -> {
            if (response !=null){ if (response.getStatus()==1){}else {}
            }else {Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();}
        });

    }

    private void findId(View view) {
        block_user = view.findViewById(R.id.block_user);
        back = view.findViewById(R.id.back);
        store = view.findViewById(R.id.my_bag);
        invisibleVisitor = view.findViewById(R.id.invisibleVisitor);
        InvisibleVisitor = view.findViewById(R.id.InvisibleVisitor);
        mysterious = view.findViewById(R.id.mysterious);
        Mysterious = view.findViewById(R.id.Mysterious);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.INVISIBLE);
    }

}