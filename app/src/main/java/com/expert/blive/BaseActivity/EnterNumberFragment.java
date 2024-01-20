package com.expert.blive.BaseActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.R;
import com.expert.blive.utils.AppConstant;
import com.hbb20.CountryCodePicker;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class EnterNumberFragment extends Fragment {
    private EditText editNumber;
    private Button img_video,signIn_button;
    private String phone, otp;
    private CountryCodePicker ccp;
    private MvvmViewModelClass viewModelClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_enter_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findIds(view);

        clicks();

//        view.findViewById(R.id.close_number).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new LoginFragment()).addToBackStack(null).commit();
//            }
//        });
    }

    private void clicks() {

        int maxLength = 10;
        editNumber.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});

        signIn_button.setOnClickListener(view -> {
            phone = editNumber.getText().toString();
            if (phone.isEmpty() || !Patterns.PHONE.matcher(editNumber.getText().toString()).matches()) {
                editNumber.setError("Enter Valid Number");
                editNumber.requestFocus();
            }
            else {
                hitApi(phone);
            }
        });
    }

    private void hitApi(String phone) {
        String code = "+"+ccp.getSelectedCountryCode();
        viewModelClass.checkEmailOrPhone(requireActivity(), code+phone).observe(requireActivity(), map -> {
            if (map.get("success").toString().equalsIgnoreCase("1")) {
                Fragment fragment = new VerificationFragment();
                Bundle bundle = new Bundle();
                bundle.putString(AppConstant.OTP, map.get("otp").toString());
                bundle.putString(AppConstant.PHONE, code+phone);
                bundle.putString(AppConstant.COUNTRY_NAME, ccp.getSelectedCountryName());
                fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, fragment).addToBackStack(null).commit();

            } else {
                Toast.makeText(requireActivity(), "" + map.get("message").toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void findIds(View view) {
        ccp = view.findViewById(R.id.ccp);
        signIn_button = view.findViewById(R.id.verify_btn);
        editNumber = view.findViewById(R.id.editNumber);
    }
}