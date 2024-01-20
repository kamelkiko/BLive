package com.expert.blive.HomeActivity.Profile;

import android.graphics.Color;
import android.graphics.PorterDuff;
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

public class MediaFragment extends Fragment {
    private TabLayout tablayout;
    private ViewPager viewPager;
    public static String rootUserMedia ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_media, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);
        setTabPro(view);
        click(view);

    }

    private void click(View view) {
        view.findViewById(R.id.back).setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit();
        });
    }

    private void setTabPro(View view) {
        if(rootUserMedia!=null) {

            tablayout.addTab(tablayout.newTab().setIcon(R.drawable.play_2).setText(rootUserMedia));
        }else
        {
            tablayout.addTab(tablayout.newTab().setIcon(R.drawable.play_2));
        }


        tablayout.addTab(tablayout.newTab().setIcon(R.drawable.kaladil));

        final PagerAdapter pagerAdapter = new Adapter(getChildFragmentManager(), tablayout.getTabCount());
        viewPager .setAdapter(pagerAdapter);
        viewPager .addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#9F1C71"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public static class Adapter extends FragmentStatePagerAdapter {

        private final  int   totalTabs ;


        public Adapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            totalTabs = behavior ;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new MyProfileFragment();
                case 1:
                    return new LikevideoFragment();

                default:
                    return null ;
            }
        }

        @Override
        public int getCount() {
            return totalTabs;
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