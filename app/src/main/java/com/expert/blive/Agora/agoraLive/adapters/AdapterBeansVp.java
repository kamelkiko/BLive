package com.expert.blive.Agora.agoraLive.adapters;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;



public class AdapterBeansVp extends FragmentPagerAdapter {

    String  string;
    public AdapterBeansVp(@NonNull FragmentManager fm, String click) {
        super(fm);
        string = click;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
//            case 0:
//                fragment = new BeanDailyFragment(string);
//                break;
//            case 1:
//                fragment = new BeanMonthlyFragment(string);
//                break;
//            case 2:
//                fragment = new BeanOverAllFragment(string);
//                break;
//            case 3:
//                fragment = new BeansAllFragmnet(string);
//                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Daily";
                break;
            case 1:
                title = "Weekly";
                break;
            case 2:
                title = "Monthly";
                break;
            case 3:
                title = "OverAll";
                break;
        }
        return title;
    }
}

