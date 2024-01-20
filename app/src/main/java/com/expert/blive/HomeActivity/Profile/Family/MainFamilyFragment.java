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
import android.widget.TextView;

import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.R;
import com.google.android.material.tabs.TabLayout;

public class MainFamilyFragment extends Fragment {

    private ImageView back_joind;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView inviteTV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_family, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        finds(view);
        tabLayoutMethod();
        clicks();



    }



    private void clicks() {
        inviteTV.setOnClickListener(view -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new InviteUserFragment()).addToBackStack(null).commit();

        });
        back_joind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit();
            }
        });
    }

    private void finds(View view) {
        back_joind=view.findViewById(R.id.back_joind);
        tabLayout=view.findViewById(R.id.tabLayout);
        viewPager=view.findViewById(R.id.viewPager);
        inviteTV=view.findViewById(R.id.inviteTV);
    }

    private void tabLayoutMethod() {
        tabLayout.addTab(tabLayout.newTab().setText("FanClub Rank"));
        tabLayout.addTab(tabLayout.newTab().setText("My FanClub"));

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
                    return new FamilyRankFragment();
                case 1:
                    return new MyFamilyFragment();
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
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.INVISIBLE);
    }
}