package com.expert.blive.BaseActivity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.R;

public class RegisterFragment extends Fragment {

    private MvvmViewModelClass viewModelClass;
    private TextView usrEdit, emailEdit, passwordEdit, cnfrmPassword;
    private String username, email, password;
    private AppCompatButton sign_btn;
    private NestedScrollView scrollView;
    private RelativeLayout rlMain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        findIds(view);

        sign_btn.setOnClickListener(view1 -> checkData());

    }

    private void findIds(View view) {

        sign_btn = view.findViewById(R.id.sign_btn);
        usrEdit = view.findViewById(R.id.usrEdit);
        emailEdit = view.findViewById(R.id.emailEdit);
        passwordEdit = view.findViewById(R.id.passwordEdit);
        cnfrmPassword = view.findViewById(R.id.cnfrmPassword);
        rlMain = view.findViewById(R.id.rlMain);
        scrollView = view.findViewById(R.id.scrollView);

    }

    private void checkData() {

        if (usrEdit.getText().toString().trim().length() < 6) {
            Toast.makeText(requireContext(), "Username must be of atleast 6 characters", Toast.LENGTH_SHORT).show();

        } else if (emailEdit.getText().toString().length() < 6) {
            Toast.makeText(requireContext(), "Email must be of atleast 6 characters", Toast.LENGTH_SHORT).show();

        } else if (passwordEdit.getText().toString().length() < 6) {
            Toast.makeText(requireContext(), "Password must be of atleast 6 characters", Toast.LENGTH_SHORT).show();

        } else if (!passwordEdit.getText().toString().equals(cnfrmPassword.getText().toString().trim())) {
            Toast.makeText(requireContext(), "Password and confirm password must be same", Toast.LENGTH_SHORT).show();
        } else {
            username = usrEdit.getText().toString().trim();
            email = emailEdit.getText().toString().trim();
            password = passwordEdit.getText().toString().trim();
            hitApi();
        }

    }

    private void hitApi() {

        rlMain.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        Log.d(TAG, "hitApi: " + username);
        Log.d(TAG, "hitApi: " + password);
        Log.d(TAG, "hitApi: " + email);


        viewModelClass.registerLiveData(requireActivity(), username, password, email, "", "android", "121", "1", "1").observe(requireActivity(), response -> {
            if (response != null) {
                if (response.getSuccess().equals("1")) {
                    Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                    rlMain.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new LoginFragment()).addToBackStack(null).commit();
                } else {
                    Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                    rlMain.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            } else {
                Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                rlMain.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });

    }

}