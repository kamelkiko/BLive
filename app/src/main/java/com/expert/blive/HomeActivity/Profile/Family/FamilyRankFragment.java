package com.expert.blive.HomeActivity.Profile.Family;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expert.blive.R;
import com.google.android.material.tabs.TabLayout;

public class FamilyRankFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_family_rank, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout=view.findViewById(R.id.tabLayout);
        viewPager=view.findViewById(R.id.viewPager);





        tabLayoutMethod();
    }


    private void tabLayoutMethod() {
        tabLayout.addTab(tabLayout.newTab().setText("Total"));
        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));
        tabLayout.addTab(tabLayout.newTab().setText("Weekly"));
        tabLayout.addTab(tabLayout.newTab().setText("Daily"));


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final PagerAdapter pagerAdapter = new Adaptor(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
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
                    return new TotalFragment();
                case 1:
                    return new MonthlyFragment();
                case 2:
                    return new WeeklyFragment();
                case 3:
                    return new DalyFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return totalCount;
        }
    }
}