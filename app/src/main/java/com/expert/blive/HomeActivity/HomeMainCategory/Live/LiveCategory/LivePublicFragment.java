package com.expert.blive.HomeActivity.HomeMainCategory.Live.LiveCategory;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.expert.blive.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.jetbrains.annotations.NotNull;

public class LivePublicFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_public, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.close_live).setOnClickListener(view12 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new PublicLiveFragment()).addToBackStack(null).commit());

        view.findViewById(R.id.bottomSheetImg).setOnClickListener(view1 -> {

            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
            bottomSheetDialog.setContentView(R.layout.live_user_bottom_sheet);


//            text1.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//
//                public void onClick(View v) {
//
//                    bottomSheetDialog.dismiss();
//                }

            bottomSheetDialog.getBehavior().getHalfExpandedRatio();

            bottomSheetDialog.show();
        });



        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.profile_image_live_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();

        dialog.findViewById(R.id.close_profile).setOnClickListener(view13 -> dialog.dismiss());

    }
}