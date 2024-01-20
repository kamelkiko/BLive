package com.expert.blive.BaseActivity;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.expert.blive.HomeMainActivity;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.ModelClass.OtpClass;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;


import org.jetbrains.annotations.NotNull;

import in.aabhasjindal.otptextview.OtpTextView;

import static android.content.ContentValues.TAG;



public class VerificationFragment extends Fragment {
    private MvvmViewModelClass viewModelClass;
    private String phone, otp,countryName;
    private Button continue_button;
    private OtpTextView otp_holder;
    OtpRoot otpRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_verification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findId(view);

        getArrgumentsOtp();

        view.findViewById(R.id.close_verification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new EnterNumberFragment()).addToBackStack(null).commit();
            }
        });

        continue_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: "+phone);
                Log.d(TAG, "onClick: "+otp_holder.getOtp());

                viewModelClass.checkOtpVerfication(requireActivity(), phone, otp_holder.getOtp(),"","",countryName,"").observe(requireActivity(), new Observer<OtpClass>() {
                    @Override
                    public void onChanged(OtpClass otpClass) {
                        if (otpClass.getSuccess().equalsIgnoreCase("1")) {
                            App.getSharedpref().saveString(AppConstant.SESSION, "1");
                            otpRoot = otpClass.getDetails();
                            App.getSharedpref().saveString(AppConstant.USERID, otpClass.getDetails().getId());
                            App.getSharedpref().saveModel(AppConstant.USER_INFO, otpClass.getDetails());
                            startActivity(new Intent(requireContext(), HomeMainActivity.class));
                        } else {
                            Toast.makeText(requireActivity(), "" + otpClass.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    private void findId(View view) {
        continue_button = view.findViewById(R.id.continue_button);
        otp_holder = view.findViewById(R.id.otp_holder);
    }

    private void getArrgumentsOtp() {
        if (getArguments() != null) {
            phone = getArguments().getString(AppConstant.PHONE);
            otp = getArguments().getString(AppConstant.OTP);
            countryName = getArguments().getString(AppConstant.COUNTRY_NAME);

            Toast.makeText(requireContext(), "" + phone, Toast.LENGTH_SHORT).show();
            Toast.makeText(requireContext(), "" + otp, Toast.LENGTH_SHORT).show();

        } else {
//            requireActivity().onBackPressed();
        }

    }
}