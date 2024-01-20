package com.expert.blive.ExtraFragments;

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

import com.google.android.material.tabs.TabLayout;
import com.expert.blive.R;
public class PublicFragment extends Fragment {

    private TabLayout publicTablayout;
    private ViewPager publicViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_public, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);
        setTablLayout();
    }

    private void finds(View view) {
        publicTablayout = view.findViewById(R.id.publicTablayout);
        publicViewPager = view.findViewById(R.id.publicViewPager);
    }

    private void setTablLayout() {

        publicTablayout.addTab(publicTablayout.newTab().setText("Public"));
        publicTablayout.addTab(publicTablayout.newTab().setText("Subscribed"));
        publicTablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final PagerAdapter pagerAdapter = new Adapter(getChildFragmentManager(),publicTablayout.getTabCount());
        publicViewPager.setAdapter(pagerAdapter);
        publicViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(publicTablayout));
        publicTablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(publicViewPager) {

        });
    }

    public static class Adapter extends FragmentStatePagerAdapter {

        private final int totalCount;

        public Adapter(@NonNull FragmentManager fm, int totalCount) {
            super(fm,totalCount);
            this.totalCount=totalCount;
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0:
                    return new PublicTab();
                case 1:
                    return  new SubscribedTab();
            }
            return null;
        }

        @Override
        public int getCount() {
            return totalCount;
        }
    }

}