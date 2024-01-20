package com.expert.blive.ExtraFragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.expert.blive.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;


public class ThemesFragment extends BottomSheetDialogFragment {


    private TabLayout tabLayout_popular;
    private ViewPager viewPager_popular;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_themes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);
        setTabLayout();
        clicks(view);

    }

    private void finds(View view) {
        tabLayout_popular = view.findViewById(R.id.tabLayout_popular);
        viewPager_popular = view.findViewById(R.id.viewPager_popular);
    }

    private void clicks(View view) {
        view.findViewById(R.id.iv_back).setOnClickListener(v -> dismiss());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            setupRatio(bottomSheetDialog);
        });
        return dialog;

    }

    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheet.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = displaySize(requireActivity())[1] * 50 / 100;
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setDraggable(false);

        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private static int[] displaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.i("height", String.valueOf(height));
        Log.i("width", String.valueOf(width));
        return new int[]{width, height};
    }


    private void setTabLayout() {
        tabLayout_popular.addTab(tabLayout_popular.newTab().setText("Mine"));
        tabLayout_popular.addTab(tabLayout_popular.newTab().setText("Store"));
        final PagerAdapter pagerAdapter = new Adaptor(getChildFragmentManager(), tabLayout_popular.getTabCount());

        tabLayout_popular.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager_popular.setAdapter(pagerAdapter);
        viewPager_popular.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout_popular));
        tabLayout_popular.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager_popular));


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
                    return new MineFragment();
                case 1:
                    return new StoreFragment();

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