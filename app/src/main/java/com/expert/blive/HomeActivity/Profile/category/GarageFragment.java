package com.expert.blive.HomeActivity.Profile.category;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.expert.blive.Adapter.GarageAdapter;

import com.expert.blive.ModelClass.Store.RootFrames;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.CommonUtils;
import com.opensource.svgaplayer.SVGAImageView;

import java.util.ArrayList;
import java.util.List;

public class GarageFragment extends Fragment implements GarageAdapter.Callback{

    private MvvmViewModelClass viewModelClass;
    private RecyclerView recycler;
    private List<RootFrames.Detail> list = new ArrayList<>();
    private GarageAdapter garageAdapter;
    private TextView noItemFound;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_garage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        findId(view);
        getGarage();





    }

    private void getGarage() {

        viewModelClass.getGarage(CommonUtils.getUserId()).observe(requireActivity(), new Observer<RootFrames>() {

            @Override
            public void onChanged(RootFrames response) {
                if (response !=null){
                    if (response.getStatus()==1){
                        recycler.setVisibility(View.VISIBLE);
                        noItemFound.setVisibility(View.GONE);
                        list = response.getDetails();
//                        garageAdapter.loadData(list);
                        garageAdapter = new GarageAdapter(list,requireContext(),GarageFragment.this);
                        recycler.setAdapter(garageAdapter);
                    }else {
                        recycler.setVisibility(View.GONE);
                        noItemFound.setVisibility(View.VISIBLE);
                        Toast.makeText(requireContext(), ""+response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findId(View view) {
        recycler = view.findViewById(R.id.frame_recycler);
        noItemFound = view.findViewById(R.id.noItemsFound);
    }

    @Override
    public void TryFrames(RootFrames.Detail detail) {

        Dialog viewDetails_box = new Dialog(requireContext());
        viewDetails_box.setContentView(R.layout.garage_dialog);
        viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
        Window window = viewDetails_box.getWindow();
        viewDetails_box.setCanceledOnTouchOutside(true);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        viewDetails_box.show();
        viewDetails_box.setCanceledOnTouchOutside(true);
        SVGAImageView svgaImage = viewDetails_box.findViewById(R.id.frames);
        CommonUtils.setAnimationTwo(requireContext(), detail.getMainImage(), svgaImage);
        ImageView closeBtn = viewDetails_box.findViewById(R.id.close_BTN);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDetails_box.dismiss();
            }
        });

    }

    @Override
    public void purchaseFrame(RootFrames.Detail detail, TextView buy) {
        String frameId = detail.getId();
        viewModelClass.userPurchaseGarage( CommonUtils.getUserId(), frameId).observe(requireActivity(), new Observer<RootFrames>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(RootFrames rootPurchaseFrame) {
                if (rootPurchaseFrame != null) {
                    setDialog(rootPurchaseFrame.getMessage());
                    if (rootPurchaseFrame.getStatus()==1) {
                        buy.setText("Purchased");
                        Toast.makeText(requireActivity(), "" + rootPurchaseFrame.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        buy.setText("Purchase");
                        Toast.makeText(requireActivity(), "" + rootPurchaseFrame.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "root is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setDialog(String message) {

        if (message.equalsIgnoreCase("Insufficient Balance")) {
            Dialog viewDetails_box = new Dialog(requireContext());
            viewDetails_box.setContentView(R.layout.dialog_are_you_sure);
            viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
            Window window = viewDetails_box.getWindow();
            viewDetails_box.setCanceledOnTouchOutside(true);
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
            window.setAttributes(wlp);
            //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            viewDetails_box.show();
            viewDetails_box.setCanceledOnTouchOutside(true);
        } else {

        }

    }

}