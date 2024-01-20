//package com.expert.blive.ExtraFragments;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.lifecycle.Observer;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.util.DisplayMetrics;
//import android.view.Display;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.expert.blive.mvvm.MvvmViewModelClass;
//import com.expert.blive.ModelClass.GiftTopGifters;
//import com.expert.blive.R;
//import com.google.android.exoplayer2.util.Log;
//import com.google.android.material.bottomsheet.BottomSheetBehavior;
//import com.google.android.material.bottomsheet.BottomSheetDialog;
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//
//public class TopGifterFragment extends BottomSheetDialogFragment {
//
//    private RecyclerView LeaderboardRV;
//    private View viewWeekly, viewMonthly, viewDaily;
//    private TextView totallyTV, monthlyTV, weeklyTV, dailyTv, txtUserNameOne, txtNoUser,
//            txtUserNameTwo, txtUserNameThree,txtUserNameFour, txtUserCoinTwo, txtUserCoinOne, txtUserCoinThree,txtUserCoinFour;
//    private ImageView topGifterOne, topGifterTwo, topGifterThree,topGifterFourth;
//
//    private LinearLayout mainLayoutView;
//    private String isLiveUser;
//    LinearLayout thirdLayout, secondLayout, topLayout, fourthLayout;
//    public List<GiftTopGifters.Detail> amount = new ArrayList<>();
//    private List<GiftTopGifters.Detail> TopThree = new ArrayList<>();
//
//    public TopGifterFragment(String b) {
//        this.isLiveUser = b;
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_top_gifter, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        findIds(view);
//        getTopGifter("1");
//        view.findViewById(R.id.img_back_ranking).setOnClickListener(view1 -> dismiss());
//
//
//        view.findViewById(R.id.weeklyTV).setOnClickListener(view12 -> {
//            getTopGifter("2");
//            //                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutLeader, new WeekFragment()).addToBackStack(null).commit();
//            viewWeekly.setVisibility(View.VISIBLE);
//            viewDaily.setVisibility(View.GONE);
//
//            viewMonthly.setVisibility(View.GONE);
//            weeklyTV.setTextColor(getResources().getColor(R.color.white));
//            dailyTv.setTextColor(getResources().getColor(R.color.grey));
//            dailyTv.setTextColor(getResources().getColor(R.color.grey));
//        });
//
//        view.findViewById(R.id.monthlyTV).setOnClickListener(view13 -> {
//            getTopGifter("3");
////          requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutLeader, new MonthlyFragment()).addToBackStack(null).commit();
//            viewMonthly.setVisibility(View.VISIBLE);
//            viewDaily.setVisibility(View.GONE);
//
//            viewWeekly.setVisibility(View.GONE);
//            monthlyTV.setTextColor(getResources().getColor(R.color.white));
//            dailyTv.setTextColor(getResources().getColor(R.color.grey));
//            weeklyTV.setTextColor(getResources().getColor(R.color.grey));
//        });
//
//        view.findViewById(R.id.DailyTv).setOnClickListener(view14 -> {
//
//
//            getTopGifter("1");
//
////                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutLeader, new DailyFragment()).addToBackStack(null).commit();
//            viewDaily.setVisibility(View.VISIBLE);
//            viewMonthly.setVisibility(View.GONE);
//            viewWeekly.setVisibility(View.GONE);
//            dailyTv.setTextColor(getResources().getColor(R.color.white));
//            weeklyTV.setTextColor(getResources().getColor(R.color.grey));
//            monthlyTV.setTextColor(getResources().getColor(R.color.grey));
//
//        });
//
//
//    }
//
//    private void findIds(View view) {
//
//        thirdLayout = view.findViewById(R.id.thirdLayout);
//        LeaderboardRV = view.findViewById(R.id.LeaderboardRV);
//        txtNoUser = view.findViewById(R.id.txtNoUser);
//        mainLayoutView = view.findViewById(R.id.mainLayoutView);
//        secondLayout = view.findViewById(R.id.secondLayout);
//        topLayout = view.findViewById(R.id.topLayout);
//        fourthLayout = view.findViewById(R.id.fourthLayout);
//        txtUserCoinTwo = view.findViewById(R.id.txtUserCoinTwo);
//        txtUserCoinOne = view.findViewById(R.id.txtUserCoinOne);
//        txtUserCoinThree = view.findViewById(R.id.txtUserCoinThree);
//        txtUserCoinFour = view.findViewById(R.id.txtUserCoinFour);
//
//        txtUserNameOne = view.findViewById(R.id.txtNameOne);
//        txtUserNameFour = view.findViewById(R.id.txtNameFour);
//        txtUserNameTwo = view.findViewById(R.id.txtNameTwo);
//        txtUserNameThree = view.findViewById(R.id.txtNameThree);
//        topGifterOne = view.findViewById(R.id.topGifterOne);
//        topGifterTwo = view.findViewById(R.id.topGifterTwo);
//        topGifterThree = view.findViewById(R.id.topGifterThree);
//        dailyTv = view.findViewById(R.id.DailyTv);
//        topGifterOne = view.findViewById(R.id.topGifterOne);
//        topGifterTwo = view.findViewById(R.id.topGifterTwo);
//        topGifterThree = view.findViewById(R.id.topGifterThree);
//        topGifterFourth = view.findViewById(R.id.topGifterFour);
//        weeklyTV = view.findViewById(R.id.weeklyTV);
//        monthlyTV = view.findViewById(R.id.monthlyTV);
//        totallyTV = view.findViewById(R.id.totallyTV);
//        viewDaily = view.findViewById(R.id.viewDaily);
//        viewMonthly = view.findViewById(R.id.viewMonthly);
//        viewWeekly = view.findViewById(R.id.viewWeekly);
//
//    }
//
//
//    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.setOnShowListener(dialogInterface -> {
//            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
//            setupRatio(bottomSheetDialog);
//        });
//        return dialog;
//    }
//
//    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
//        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
//        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
//        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
//        layoutParams.height = displaySize(requireActivity())[1] * 55 / 100;
//        bottomSheet.setLayoutParams(layoutParams);
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//    }
//
//    private static int[] displaySize(Context context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//        DisplayMetrics metrics = new DisplayMetrics();
//        ((Display) display).getMetrics(metrics);
//        int width = metrics.widthPixels;
//        int height = metrics.heightPixels;
//        Log.i("height", String.valueOf(height));
//        Log.i("width", String.valueOf(width));
//        return new int[]{width, height};
//    }
//
//
//
//    private void getTopGifter(String daily) {
//        amount.clear();
//        HashMap<String, String> data = new HashMap<>();
//        data.put("type", daily);
//        data.put("userId", isLiveUser);
//        new MvvmViewModelClass().montlyGiftAmount(requireActivity(), data).observe(requireActivity(), new Observer<GiftTopGifters>() {
//            @Override
//            public void onChanged(GiftTopGifters giftTopGifters) {
//                    if (giftTopGifters.getStaus().equalsIgnoreCase("1")) {
//                        mainLayoutView.setVisibility(View.VISIBLE);
//                        txtNoUser.setVisibility(View.GONE);
//                        amount = giftTopGifters.getDetails();
//                        if (amount.size() == 0) {
//                            secondLayout.setVisibility(View.GONE);
//                            thirdLayout.setVisibility(View.GONE);
//                            topLayout.setVisibility(View.GONE);
//                            fourthLayout.setVisibility(View.GONE);
//                        } else if (amount.size() == 1) {
//
//                            fourthLayout.setVisibility(View.GONE);
//
//                            secondLayout.setVisibility(View.GONE);
//                            thirdLayout.setVisibility(View.GONE);
//                            topLayout.setVisibility(View.VISIBLE);
//
//                            topGifterOne.setVisibility(View.VISIBLE);
//                            txtUserNameOne.setVisibility(View.VISIBLE);
//                            txtUserCoinOne.setVisibility(View.VISIBLE);
//
//                            Glide.with(topGifterOne).load(amount.get(0).getUserInfo().getImage()).into(topGifterOne);
//
//                            if (amount.get(0).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameOne.setText(amount.get(0).getUserInfo().getUsername());
//
//
//                            } else {
//                                txtUserNameOne.setText(amount.get(0).getUserInfo().getName());
//
//
//                            }
//                            txtUserCoinOne.setText(String.valueOf(amount.get(0).getCoin()));
//
//
//                        } else if (amount.size() == 2) {
//                            fourthLayout.setVisibility(View.GONE);
//
//                            secondLayout.setVisibility(View.VISIBLE);
//                            thirdLayout.setVisibility(View.GONE);
//                            topLayout.setVisibility(View.VISIBLE);
//
//
//                            topGifterOne.setVisibility(View.VISIBLE);
//                            txtUserNameOne.setVisibility(View.VISIBLE);
//                            txtUserCoinOne.setVisibility(View.VISIBLE);
//
//                            topGifterTwo.setVisibility(View.VISIBLE);
//                            txtUserNameTwo.setVisibility(View.VISIBLE);
//                            txtUserCoinTwo.setVisibility(View.VISIBLE);
//
//
//                            Glide.with(topGifterOne).load(amount.get(0).getUserInfo().getImage()).into(topGifterOne);
//                            if (amount.get(0).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameOne.setText(amount.get(0).getUserInfo().getUsername());
//
//
//                            } else {
//                                txtUserNameOne.setText(amount.get(0).getUserInfo().getName());
//
//
//                            }
//                            txtUserCoinOne.setText(String.valueOf(amount.get(0).getCoin()));
//
//
//                            Glide.with(topGifterTwo).load(amount.get(1).getUserInfo().getImage()).into(topGifterTwo);
//                            if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameTwo.setText(amount.get(1).getUserInfo().getUsername());
//                            } else {
//                                txtUserNameOne.setText(amount.get(1).getUserInfo().getName());
//                            }
//                            txtUserCoinTwo.setText(String.valueOf(amount.get(1).getCoin()));
//
//
//                        } else if (amount.size() == 3) {
//                            fourthLayout.setVisibility(View.GONE);
//                            secondLayout.setVisibility(View.VISIBLE);
//                            thirdLayout.setVisibility(View.VISIBLE);
//                            topLayout.setVisibility(View.VISIBLE);
//
//                            topGifterOne.setVisibility(View.VISIBLE);
//                            txtUserNameOne.setVisibility(View.VISIBLE);
//                            txtUserCoinOne.setVisibility(View.VISIBLE);
//
//                            topGifterTwo.setVisibility(View.VISIBLE);
//                            txtUserNameTwo.setVisibility(View.VISIBLE);
//                            txtUserCoinTwo.setVisibility(View.VISIBLE);
//
//                            topGifterThree.setVisibility(View.VISIBLE);
//                            txtUserNameThree.setVisibility(View.VISIBLE);
//                            txtUserCoinThree.setVisibility(View.VISIBLE);
//
//
//                            Glide.with(topGifterOne).load(amount.get(0).getUserInfo().getImage()).into(topGifterOne);
//                            if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameOne.setText(amount.get(0).getUserInfo().getUsername());
//                            } else {
//                                txtUserNameOne.setText(amount.get(0).getUserInfo().getName());
//                            }
//                            txtUserCoinOne.setText(String.valueOf(amount.get(0).getCoin()));
//
//
//                            Glide.with(topGifterTwo).load(amount.get(1).getUserInfo().getImage()).into(topGifterTwo);
//                            if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameTwo.setText(amount.get(1).getUserInfo().getUsername());
//                            } else {
//                                txtUserNameTwo.setText(amount.get(1).getUserInfo().getName());
//                            }
//                            txtUserCoinTwo.setText(String.valueOf(amount.get(1).getCoin()));
//
//                            Glide.with(topGifterThree).load(amount.get(2).getUserInfo().getImage()).into(topGifterThree);
//                            if (amount.get(2).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameThree.setText(amount.get(2).getUserInfo().getUsername());
//
//
//                            } else {
//                                txtUserNameThree.setText(amount.get(2).getUserInfo().getName());
//
//
//                            }
//                            txtUserCoinThree.setText(String.valueOf(amount.get(2).getCoin()));
//
//
//                        } else if (amount.size() == 4) {
//
//                            fourthLayout.setVisibility(View.VISIBLE);
//                            secondLayout.setVisibility(View.VISIBLE);
//                            thirdLayout.setVisibility(View.VISIBLE);
//                            topLayout.setVisibility(View.VISIBLE);
//
//                            topGifterOne.setVisibility(View.VISIBLE);
//                            txtUserNameOne.setVisibility(View.VISIBLE);
//                            txtUserCoinOne.setVisibility(View.VISIBLE);
//
//                            topGifterTwo.setVisibility(View.VISIBLE);
//                            txtUserNameTwo.setVisibility(View.VISIBLE);
//                            txtUserCoinTwo.setVisibility(View.VISIBLE);
//
//                            topGifterThree.setVisibility(View.VISIBLE);
//                            txtUserNameThree.setVisibility(View.VISIBLE);
//                            txtUserCoinThree.setVisibility(View.VISIBLE);
//
//                            topGifterFourth.setVisibility(View.VISIBLE);
//                            txtUserNameFour.setVisibility(View.VISIBLE);
//                            txtUserCoinFour.setVisibility(View.VISIBLE);
//
//
//
//                            Glide.with(topGifterOne).load(amount.get(0).getUserInfo().getImage()).into(topGifterOne);
//                            if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameOne.setText(amount.get(0).getUserInfo().getUsername());
//                            } else {
//                                txtUserNameOne.setText(amount.get(0).getUserInfo().getName());
//                            }
//                            txtUserCoinOne.setText(String.valueOf(amount.get(0).getCoin()));
//
//
//                            Glide.with(topGifterTwo).load(amount.get(1).getUserInfo().getImage()).into(topGifterTwo);
//                            if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameTwo.setText(amount.get(1).getUserInfo().getUsername());
//                            } else {
//                                txtUserNameTwo.setText(amount.get(1).getUserInfo().getName());
//                            }
//                            txtUserCoinTwo.setText(String.valueOf(amount.get(1).getCoin()));
//
//                            Glide.with(topGifterThree).load(amount.get(2).getUserInfo().getImage()).into(topGifterThree);
//                            if (amount.get(2).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameThree.setText(amount.get(2).getUserInfo().getUsername());
//
//
//                            } else {
//                                txtUserNameThree.setText(amount.get(2).getUserInfo().getName());
//
//
//                            }
//                            txtUserCoinThree.setText(String.valueOf(amount.get(2).getCoin()));
//
//
//                            Glide.with(topGifterFourth).load(amount.get(3).getUserInfo().getImage()).into(topGifterFourth);
//                            if (amount.get(3).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameFour.setText(amount.get(3).getUserInfo().getUsername());
//
//
//                            } else {
//                                txtUserNameFour.setText(amount.get(3).getUserInfo().getName());
//
//
//                            }
//                            txtUserCoinFour.setText(String.valueOf(amount.get(3).getCoin()));
//
//
//
//
//
//
//                        } else {
//
//                            fourthLayout.setVisibility(View.VISIBLE);
//                            secondLayout.setVisibility(View.VISIBLE);
//                            thirdLayout.setVisibility(View.VISIBLE);
//                            topLayout.setVisibility(View.VISIBLE);
//
//                            topGifterOne.setVisibility(View.VISIBLE);
//                            txtUserNameOne.setVisibility(View.VISIBLE);
//                            txtUserCoinOne.setVisibility(View.VISIBLE);
//
//                            topGifterTwo.setVisibility(View.VISIBLE);
//                            txtUserNameTwo.setVisibility(View.VISIBLE);
//                            txtUserCoinTwo.setVisibility(View.VISIBLE);
//
//                            topGifterThree.setVisibility(View.VISIBLE);
//                            txtUserNameThree.setVisibility(View.VISIBLE);
//                            txtUserCoinThree.setVisibility(View.VISIBLE);
//
//                            topGifterFourth.setVisibility(View.VISIBLE);
//                            txtUserNameFour.setVisibility(View.VISIBLE);
//                            txtUserCoinFour.setVisibility(View.VISIBLE);
//
//
//
//                            Glide.with(topGifterOne).load(amount.get(0).getUserInfo().getImage()).into(topGifterOne);
//                            if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameOne.setText(amount.get(0).getUserInfo().getUsername());
//                            } else {
//                                txtUserNameOne.setText(amount.get(0).getUserInfo().getName());
//                            }
//                            txtUserCoinOne.setText(String.valueOf(amount.get(0).getCoin()));
//
//
//                            Glide.with(topGifterTwo).load(amount.get(1).getUserInfo().getImage()).into(topGifterTwo);
//                            if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameTwo.setText(amount.get(1).getUserInfo().getUsername());
//                            } else {
//                                txtUserNameTwo.setText(amount.get(1).getUserInfo().getName());
//                            }
//                            txtUserCoinTwo.setText(String.valueOf(amount.get(1).getCoin()));
//
//                            Glide.with(topGifterThree).load(amount.get(2).getUserInfo().getImage()).into(topGifterThree);
//                            if (amount.get(2).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameThree.setText(amount.get(2).getUserInfo().getUsername());
//
//
//                            } else {
//                                txtUserNameThree.setText(amount.get(2).getUserInfo().getName());
//
//
//                            }
//                            txtUserCoinThree.setText(String.valueOf(amount.get(2).getCoin()));
//
//
//                            Glide.with(topGifterFourth).load(amount.get(3).getUserInfo().getImage()).into(topGifterFourth);
//                            if (amount.get(3).getUserInfo().getName().equalsIgnoreCase("")) {
//                                txtUserNameFour.setText(amount.get(3).getUserInfo().getUsername());
//
//
//                            } else {
//                                txtUserNameFour.setText(amount.get(3).getUserInfo().getName());
//
//
//                            }
//                            txtUserCoinFour.setText(String.valueOf(amount.get(3).getCoin()));
//
//
//                            amount.remove(0);
//                            amount.remove(1);
//                            amount.remove(2);
//                            amount.remove(3);
//
//                            setAdpter(amount);
//
//                        }
//
//
//                    } else {
//
//
//                        txtNoUser.setVisibility(View.VISIBLE);
//                        mainLayoutView.setVisibility(View.GONE);
//
//
//                    }
////                setDifference(topAmount);
//            }
//        });
//
//    }
//
//    private void setAdpter(List<GiftTopGifters.Detail> amount) {
//
//        LeatherBoardRVAdapter leatherBoardRVAdapter = new LeatherBoardRVAdapter(amount);
//
//        LeaderboardRV.setAdapter(leatherBoardRVAdapter);
//
//    }
//}