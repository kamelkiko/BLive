package com.expert.blive.HomeActivity.Profile.VIP;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.ModelClass.VIP.VipRoot;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_My_VIP extends Fragment {
    private OtpRoot VipDetails;
    CircleImageView userImage;
    TextView userName;
    private TabLayout tablayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment__my__v_i_p, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.backgrounds01);
        VipDetails = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

        findId(view);
        onClick(view);
        setTabVip(view);

    }

    private void setTabVip(View view) {

        tablayout.addTab(tablayout.newTab().setText("Bronze"));
        tablayout.addTab(tablayout.newTab().setText("Silver"));
        tablayout.addTab(tablayout.newTab().setText("Gold"));


        final PagerAdapter pagerAdapter = new Adapter_Vip(getChildFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }

    public static class Adapter_Vip extends FragmentStatePagerAdapter {
        private final int totalTabs;


        public Adapter_Vip(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            totalTabs = behavior;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Fragment_Marquis();
                case 1:
                    return new Fragment_Count();
                case 2:
                    return new Fragment_Viscount();
                case 3:
//                    return new Fragment_Knight(list.get(3), frameImage);
                case 4:
//                    return new Fragment_Swordsman(list.get(4), frameImage);


                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return totalTabs;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        requireActivity().findViewById(R.id.img_video).setVisibility(View.VISIBLE);
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }

    private void findId(View view) {

        userImage = view.findViewById(R.id.user_Vip_profile);
        userName = view.findViewById(R.id.vip_name);
        tablayout = view.findViewById(R.id.tab_Vip);
        viewPager = view.findViewById(R.id.viewpager_Vip);
    }

//    private void setData(View view) {
//
//        userName.setText(VipDetails.getName());
//
//        Glide.with(requireContext()).load(VipDetails.getImage()).error(R.drawable.user_7).into(userImage);
//
//    }

    private void onClick(View view) {

        view.findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });

    }
}