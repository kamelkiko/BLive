package com.expert.blive.HomeActivity.Leaderboard;

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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

public class MainLeaderboardFragment extends BottomSheetDialogFragment {

    private TabLayout tablayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_leaderboard, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




        finds(view);
        tabLayoutMethod();
        click(view);
    }

    private void click(View view) {
        view.findViewById(R.id.back).setOnClickListener(view1 -> requireActivity().onBackPressed());
    }

    public void tabLayoutMethod() {

        tablayout.addTab(tablayout.newTab().setText("Daily"));
        tablayout.addTab(tablayout.newTab().setText("Weekly"));
        tablayout.addTab(tablayout.newTab().setText("Monthly"));

        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter pagerAdapter = new Adaptor(getChildFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
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
                    return new DailyFragment();
                case 1:
                    return new WeeklyFragment();
                case 2:
                    return new MonthlyFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return totalCount;
        }

    }
    private void finds(View view) {
        tablayout=view.findViewById(R.id.tablayout);
        viewPager=view.findViewById(R.id.viewpager_Pro);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }
}