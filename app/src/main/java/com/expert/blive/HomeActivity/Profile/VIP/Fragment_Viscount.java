package com.expert.blive.HomeActivity.Profile.VIP;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.expert.blive.HomeActivity.Profile.category.MyWalletFragment;
import com.expert.blive.ModelClass.VIP.BuyVipRoot;
import com.expert.blive.ModelClass.VIP.VipRoot;
import com.expert.blive.R;
import com.expert.blive.databinding.FragmentViscountBinding;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.CommonUtils;

public class Fragment_Viscount extends Fragment {
    FragmentViscountBinding binding;
    private MvvmViewModelClass viewModelClass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        binding = FragmentViscountBinding.inflate(getLayoutInflater(), container, false);

        setData();

        return binding.getRoot();
    }

    private void setData() {

        viewModelClass.get_vips(CommonUtils.getUserId(),"3").observe(requireActivity(), new Observer<VipRoot>() {
            @Override
            public void onChanged(VipRoot response) {
                if (response !=null){
                    if (response.getStatus()==1){
                        setData(response.getDetails());
                        onClick(response.getDetails());
                    }
                }
            }

            private void setData(VipRoot.Details details) {
                Glide.with(requireContext()).load(details.getBadge()).into(binding.badgeImg);
                Glide.with(requireContext()).load(CommonUtils.getImage()).into(binding.userVipProfile);
                binding.vipName.setText(CommonUtils.getName());
                binding.cost.setText(details.getCoins());
                if (details.getPurchaseStatus()){
                    binding.vipBUY.setText("Purchased");
                }else {
                    binding.vipBUY.setText("Purchase");
                }
            }
        });
    }

    private void onClick(VipRoot.Details details) {
        binding.vipBUY.setOnClickListener(view ->  buyVip(details));
    }


    private void buyVip(VipRoot.Details details) {

        viewModelClass.buyVip(requireActivity(), CommonUtils.getUserId(), details.getId()).observe(requireActivity(), new Observer<BuyVipRoot>() {
            @Override
            public void onChanged(BuyVipRoot buyVipRoot) {

                if (buyVipRoot.getStatus() == 1) {


                    Toast.makeText(requireContext(), "1 " + buyVipRoot.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    notEnoughCoins();
                }
            }
        });
    }

    private void notEnoughCoins() {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Not enough coins, want to recharge?");
        builder.setTitle("Tips");
        builder.setCancelable(false);
        builder.setPositiveButton("Recharge", (DialogInterface.OnClickListener) (dialog, which) -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MyWalletFragment()).addToBackStack(null).commit();
            dialog.dismiss();
        });

        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}