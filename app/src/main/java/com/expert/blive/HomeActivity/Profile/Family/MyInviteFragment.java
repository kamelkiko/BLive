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
import android.widget.ImageView;

import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.R;
import com.google.android.material.tabs.TabLayout;

public class MyInviteFragment extends Fragment {

    private TabLayout tablayout;
    private ViewPager viewPager;
    private ImageView back_button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_invite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findId(view);
        setTabMall(view);
        click(view);
    }

    private void click(View view) {

        back_button.setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit();
        });
    }

    private void findId(View view) {

        tablayout = view.findViewById(R.id.tab_mall);
        viewPager = view.findViewById(R.id.viewpager_mall);
        back_button = view.findViewById(R.id.back_button);
    }

    private void setTabMall(View view) {

        tablayout.addTab(tablayout.newTab().setText("Invite's FanClub"));
        tablayout.addTab(tablayout.newTab().setText("Request's FanClub"));

        final PagerAdapter pagerAdapter = new Adapter_mall(getChildFragmentManager(), tablayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public static class Adapter_mall extends FragmentStatePagerAdapter {
        private final int totalTabs;

        public Adapter_mall(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            totalTabs = behavior;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:

                    return new InvitesFragment();
                case 1:

                    return new RequestFamilyFragment();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return totalTabs;
        }
    }
}