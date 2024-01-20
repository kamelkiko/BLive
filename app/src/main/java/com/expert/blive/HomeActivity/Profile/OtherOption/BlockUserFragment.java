package com.expert.blive.HomeActivity.Profile.OtherOption;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.BlockUserAdapter;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;


public class BlockUserFragment extends Fragment implements BlockUserAdapter.OnClickInterface {

    private RecyclerView block_user_recycler;

    private TextView NoBlockTxt;

    private OtpRoot detailsUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_block_userk, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        detailsUser = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

        findIds(view);

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit();
            }
        });
    }

    private void findIds(View view) {

        block_user_recycler = view.findViewById(R.id.block_user_recycler);
        NoBlockTxt = view.findViewById(R.id.NoBlockTxt);

    }

    @Override
    public void onResume() {

        super.onResume();
        getBanUsers();
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }

    private void getBanUsers() {

        new MvvmViewModelClass().getBlockedUsersLiveData(requireActivity(), CommonUtils.getUserId()).observe(requireActivity(), response -> {
            if (response != null) {
                if (response.getSuccess().equals("1")) {
                    NoBlockTxt.setVisibility(View.GONE);
                    block_user_recycler.setAdapter(new BlockUserAdapter(response.getDetails(), requireContext(), BlockUserFragment.this));
                }
            }
        });


    }

    @Override
    public void onClick(String blockUserId) {

        new MvvmViewModelClass().userBlockLiveData(requireActivity(), CommonUtils.getUserId(), blockUserId).observe(requireActivity(), response -> {
            if (response != null) {
                onResume();
                Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Soemthing went wrong.", Toast.LENGTH_SHORT).show();
            }
        });

    }

}