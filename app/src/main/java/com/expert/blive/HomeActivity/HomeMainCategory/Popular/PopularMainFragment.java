package com.expert.blive.HomeActivity.HomeMainCategory.Popular;

import android.Manifest;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.expert.blive.HomeActivity.HomeMainCategory.Popular.category.PopularFragment;
import com.expert.blive.HomeActivity.Leaderboard.MainLeaderboardFragment;
import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Live.LiveCategory.MultiBroadcastFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Popular.category.NearByFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Popular.category.NewestFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Popular.category.PkFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.search.SearchUserFragment;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class PopularMainFragment extends Fragment {

    private XTabLayout tabLayout_popular;

    private ViewPager viewPager_popular;

    private ImageView imgSearch, user_profile, profileTopGiftersRL;

    private OtpRoot otpRoot;
    View view;

    String[] permission = {android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_MEDIA_AUDIO,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            android.Manifest.permission.CAMERA, android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            android.Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO, Manifest.permission.BLUETOOTH,
            Manifest.permission.ACCESS_NETWORK_STATE};

    public PopularMainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permission, 10);
        }

        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg_gradient);
        view = inflater.inflate(R.layout.fragment_popular_main, container, false);
        otpRoot = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
        Toast.makeText(requireContext(), "userid"+CommonUtils.getUserId(), Toast.LENGTH_SHORT).show();

        findId(view);
        backPressed(view);
        tabLayoutMethod();
        onclick(view);
        return view;

    }

    private void findId(View view) {
        tabLayout_popular = view.findViewById(R.id.tabLayout_popular);
        viewPager_popular = view.findViewById(R.id.viewPager_popular);
        imgSearch = view.findViewById(R.id.imgSearch);
        user_profile = view.findViewById(R.id.user_profile);
        profileTopGiftersRL = view.findViewById(R.id.profileTopGiftersRL);
    }

    private void onclick(View view) {
        imgSearch.setOnClickListener(view12 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new SearchUserFragment()).addToBackStack(null).commit());
        profileTopGiftersRL.setOnClickListener( view2 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MainLeaderboardFragment()).addToBackStack(null).commit());
        user_profile.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new NewprofileFragment()).addToBackStack(null).commit());

        if (otpRoot != null) {
            if (otpRoot.getImage() != null && otpRoot.getImage().length() != 0) {
                Picasso.with(getContext()).load(otpRoot.getImage()).error(R.drawable.user_7).into(user_profile);
            }
        }
    }

    public void tabLayoutMethod() {

        tabLayout_popular.addTab(tabLayout_popular.newTab().setText("Popular"));
        tabLayout_popular.addTab(tabLayout_popular.newTab().setText("PARTY"));
        tabLayout_popular.addTab(tabLayout_popular.newTab().setText("PK"));
        tabLayout_popular.addTab(tabLayout_popular.newTab().setText("EXPLORE"));
        tabLayout_popular.addTab(tabLayout_popular.newTab().setText("MultiBroadcast"));

        tabLayout_popular.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter pagerAdapter = new Adaptor(getChildFragmentManager(), tabLayout_popular.getTabCount());
        viewPager_popular.setAdapter(pagerAdapter);
        viewPager_popular.addOnPageChangeListener(new XTabLayout.TabLayoutOnPageChangeListener(tabLayout_popular));
        tabLayout_popular.addOnTabSelectedListener(new XTabLayout.ViewPagerOnTabSelectedListener(viewPager_popular));
    }

    public static class Adaptor extends FragmentStatePagerAdapter {

        private final int totalCount;

        public Adaptor(@NonNull FragmentManager fm, int behavior) {

            super(fm, behavior);

            this.totalCount = behavior;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new PopularFragment();
                case 1:
                    return new NearByFragment();
                case 2:
                    return new PkFragment();
                case 3:
                    return new NewestFragment();
                case 4:
                    return new MultiBroadcastFragment();
                case 5:
//                    return new NewestFragment();
                case 6:
//                    return new GamesFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return totalCount;
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.VISIBLE);
    }

    private void backPressed(View sView) {

        sView.setFocusableInTouchMode(true);

        sView.requestFocus();

        sView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    if (i == KeyEvent.KEYCODE_BACK) {

                        final Dialog dialog = new Dialog(requireActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(true);
                        dialog.setContentView(R.layout.exit_app_dialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        TextView yesBtn = dialog.findViewById(R.id.yesText);
                        TextView noBtn = dialog.findViewById(R.id.noText);

                        yesBtn.setOnClickListener(view1 -> {
                            requireActivity().finishAffinity();
                        });
                        noBtn.setOnClickListener(view1 -> {
                            dialog.dismiss();
                        });
                        dialog.show();
                        return true;
                    }
                }
                return false;
            }
        });
    }

}