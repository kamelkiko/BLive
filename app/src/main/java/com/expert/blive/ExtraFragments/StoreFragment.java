package com.expert.blive.ExtraFragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.StoreAdapter;
import com.expert.blive.ModelClass.PrurchaseWallpaper;
import com.expert.blive.ModelClass.StoreImages;
import com.expert.blive.R;
import com.expert.blive.databinding.FragmentStoreBinding;
import com.expert.blive.utils.CommonUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.List;


public class StoreFragment extends Fragment {
    private FragmentStoreBinding binding;
    private int typeData=0;
    private MvvmViewModelClass viewModelClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        binding = FragmentStoreBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getStoreWallPaper();
    }

    private void getStoreWallPaper() {

        viewModelClass.getAudioLiveImages(requireActivity()).observe(requireActivity(), detailCountry -> setAdapter(detailCountry.getDetails()));

    }

    private void setAdapter(List<StoreImages.Detail> details) {
        StoreAdapter adapter = new StoreAdapter(details, this::OpenOtherBottomShgeet);
        binding.mineThemeRecycler.setAdapter(adapter);


    }

    @SuppressLint("ResourceAsColor")
    private void OpenOtherBottomShgeet(StoreImages.Detail detail) {
//        BottomSheetDialog sendGiftUsersDialog = new BottomSheetDialog(requireContext());
//        ChooseThemeAudioBinding liveUserJoinedBinding = ChooseThemeAudioBinding.inflate(LayoutInflater.from(requireContext()));
//        sendGiftUsersDialog.setContentView(liveUserJoinedBinding.getRoot());
//
//        View contentView = View.inflate(getContext(), R.layout.choose_theme_audio, null);

//        sendGiftUsersDialog.getBehavior().getState() = BottomSheetBehavior.STATE_EXPANDED;STATE_EXPANDED
                // or since com.google.android.material:material:1.1.0-beta01


        BottomSheetDialog bottomSheet = new BottomSheetDialog(requireContext());
        View view = View.inflate(requireContext(), R.layout.choose_theme_audio, null);
        bottomSheet.setContentView(view);
        bottomSheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(bottomSheetBehavior.getPeekHeight());


        RoundedImageView civ_user_profile;
        AppCompatButton txtByThemeStore;
        TextView tvOneWeek,tvTwoWeek,tvThreeWeek;





        tvOneWeek = bottomSheet.findViewById(R.id.tvOneWeek);
        tvTwoWeek = bottomSheet.findViewById(R.id.tvTwoWeek);
        tvThreeWeek = bottomSheet.findViewById(R.id.tvThreeWeek);
        civ_user_profile = bottomSheet.findViewById(R.id.civ_user_profile);
        txtByThemeStore = bottomSheet.findViewById(R.id.txtByThemeStore);

        Glide.with(civ_user_profile.getContext()).load(detail.getImage()).into(civ_user_profile);

        txtByThemeStore.setText(detail.getPrice());

        bottomSheet.show();
        bottomSheet.setDismissWithAnimation(true);

        tvOneWeek.setOnClickListener(v -> {
          typeData=1;
          setTextBackGround(tvOneWeek, tvTwoWeek, tvThreeWeek, 1, txtByThemeStore, detail.getPrice());
        });
        tvTwoWeek.setOnClickListener(v -> {
            typeData=2;
            setTextBackGround(tvTwoWeek, tvOneWeek, tvThreeWeek, 2, txtByThemeStore,detail.getPrice());
        });
        tvThreeWeek.setOnClickListener(v -> {
        typeData=3;
        setTextBackGround(tvThreeWeek, tvTwoWeek, tvOneWeek, 3, txtByThemeStore,detail.getPrice());
        });
        txtByThemeStore.setOnClickListener(v -> hitApi(detail,typeData,txtByThemeStore,txtByThemeStore,bottomSheet));




    }

    private void hitApi(StoreImages.Detail detail, int typeData, AppCompatButton txtByThemeStore, AppCompatButton v, BottomSheetDialog bottomSheet) {

        HashMap<String , String> data  = new HashMap<>();

        data.put("userId", CommonUtils.getUserId());
        data.put("type",String.valueOf(typeData));
        data.put("wallpaperId",detail.getId());
        data.put("price",v.getText().toString());

        viewModelClass.prurchaseWallpaper(requireActivity(), data).observe(requireActivity(), prurchaseWallpaper -> {
        bottomSheet.dismiss();
        Toast.makeText(requireContext(), ""+prurchaseWallpaper.getMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setTextBackGround(TextView tvOneWeek, TextView tvTwoWeek, TextView tvThreeWeek, int type, AppCompatButton txtByThemeStore, String price) {

        long priceWall = Long.parseLong(price);
        if (type==1){
            txtByThemeStore.setText(String.valueOf(priceWall));
        }else if (type==2){
            priceWall=  2*priceWall;
            txtByThemeStore.setText(String.valueOf(priceWall));
        }else if (type==3){
            priceWall=  4*priceWall;
            txtByThemeStore.setText(String.valueOf(priceWall));

        }
        tvOneWeek.setBackgroundDrawable(requireContext().getDrawable(R.drawable.stroke_primary));
        tvOneWeek.setTextColor(requireContext().getColor(R.color.app_pink_color));
        tvTwoWeek.setBackgroundDrawable(requireContext().getDrawable(R.drawable.stroke_week));
        tvTwoWeek.setTextColor(requireContext().getColor(R.color.black));
        tvThreeWeek.setBackgroundDrawable(requireContext().getDrawable(R.drawable.stroke_week));
        tvOneWeek.setTextColor(requireContext().getColor(R.color.black));


    }
}