package com.expert.blive.Agora.agoraLive.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class GiftingTabAdapter extends FragmentPagerAdapter {



    public GiftingTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                GiftHotFragment giftHotFragment=new GiftHotFragment();
                return giftHotFragment;
            case 1:
                GiftLoveFragment giftLoveFragment=new GiftLoveFragment();
                return giftLoveFragment;
            case 2:
                GiftExclusiveFragment giftExclusiveFragment=new GiftExclusiveFragment();
                return giftExclusiveFragment;
            case 3:
                GiftLuxuryFragment giftLuxuryFragment=new GiftLuxuryFragment();
                return giftLuxuryFragment;
            case 4:
                GiftDecorationFragment giftDecorationFragment=new GiftDecorationFragment();
                return giftDecorationFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 5;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Hot";
            case 1:
                return "Love";
            case 2:
                return "Exclusive";
            case 3:
                return "Luxury";
            case 4:
                return "Decoration";

        }
        return null;
    }
}
