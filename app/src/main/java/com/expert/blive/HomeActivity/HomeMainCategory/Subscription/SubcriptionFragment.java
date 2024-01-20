package com.expert.blive.HomeActivity.HomeMainCategory.Subscription;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.expert.blive.HomeActivity.HomeMainCategory.Subscription.category.FollowingSubcriptionFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Subscription.category.ForYouSubcriptionFragment;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class SubcriptionFragment extends Fragment {

    private TabLayout homeTabLayout;
    private ViewPager popularhomeScreenViewPager;
    private ImageView profile_image;
    private OtpRoot otpRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subcription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        otpRoot = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

        homeTabLayout = view.findViewById(R.id.homeTabLayout);
        profile_image = view.findViewById(R.id.profile_image);
        popularhomeScreenViewPager = view.findViewById(R.id.popularhomeScreenViewPager);
        setTabLayout();
        onClicks();
        if (otpRoot!=null){

            if (otpRoot.getImage()!=null&&otpRoot.getImage().length()!=0){

                Picasso.with(getContext()).load(otpRoot.getImage()).error(R.drawable.user_7).into(profile_image);

            }
        }
    }

    private void setTabLayout() {

        homeTabLayout.addTab(homeTabLayout.newTab().setText("For you"));
        homeTabLayout.addTab(homeTabLayout.newTab().setText("Following"));
//        homeTabLayout.addTab(homeTabLayout.newTab().setText("Subscribed videos"));
        homeTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final PagerAdapter pagerAdapter = new Adapter(getChildFragmentManager(), homeTabLayout.getTabCount());
        popularhomeScreenViewPager.setAdapter(pagerAdapter);
        popularhomeScreenViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(homeTabLayout));
        homeTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(popularhomeScreenViewPager) {

        });
    }

    private void onClicks() {

    }

    public static class Adapter extends FragmentStatePagerAdapter {
        private final int totalCount;
        public Adapter(FragmentManager childFragmentManager, int totalCount) {

            super(childFragmentManager, FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.totalCount = totalCount;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new ForYouSubcriptionFragment();

                case 1:
                    return new FollowingSubcriptionFragment();

//                case 2:
//                    return new SubscribedVideosFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return totalCount;
        }
    }


    @Override
    public void onResume() {

        super.onResume();
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
    }

}