//package com.expert.blive.ExtraFragments;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.expert.blive.HomeActivity.HomeMainCategory.Popular.PopularMainFragment;
//import com.expert.blive.mvvm.MvvmViewModelClass;
//import com.expert.blive.Adapter.LeadeBordOverAll;
//import com.expert.blive.ModelClass.TopGifterModel;
//import com.expert.blive.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TopGiftersFragment extends Fragment {
//    private MvvmViewModelClass viewModelClass;
//    private RecyclerView LeaderboardRV;
//    private View viewWeekly, viewMonthly, viewDaily;
//    private TextView totallyTV, monthlyTV, weeklyTV, dailyTv, txtUserNameOne, txtNoUser,
//            txtUserNameTwo, txtUserNameThree,txtUserNameFour, txtUserCoinTwo, txtUserCoinOne, txtUserCoinThree,txtUserCoinFour;
//    private ImageView topGifterOne, topGifterTwo, topGifterThree,topGifterFourth;
//
//    private LinearLayout mainLayoutView;
//    private String isLiveUser;
//    private LinearLayout thirdLayout, secondLayout, topLayout, fourthLayout;
//    public List<TopGifterModel.Detail> amount = new ArrayList<>();
//    private List<TopGifterModel.Detail> TopThree = new ArrayList<>();
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
//        return inflater.inflate(R.layout.fragment_top_gifters, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        findIds(view);
//
//        getTopGifter();
//
//
//
//        view.findViewById(R.id.topGifterBackImg).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new PopularMainFragment()).addToBackStack(null).commit());
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
//        txtUserNameOne = view.findViewById(R.id.txtNameOne);
//        txtUserNameFour = view.findViewById(R.id.txtNameFour);
//        txtUserNameTwo = view.findViewById(R.id.txtNameTwo);
//        txtUserNameThree = view.findViewById(R.id.txtNameThree);
//        topGifterOne = view.findViewById(R.id.topGifterOne);
//        topGifterTwo = view.findViewById(R.id.topGifterTwo);
//        topGifterThree = view.findViewById(R.id.topGifterThree);
//        dailyTv = view.findViewById(R.id.DailyTv);
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
//    private void getTopGifter() {
//
//        viewModelClass.topGifter("1").observe(requireActivity(), topGifterModel -> {
//            if (topGifterModel.getStatus().equalsIgnoreCase("1")) {
//                mainLayoutView.setVisibility(View.VISIBLE);
//                txtNoUser.setVisibility(View.GONE);
//                amount = topGifterModel.getDetails();
//                if (amount.size() == 0) {
//                    secondLayout.setVisibility(View.GONE);
//                    thirdLayout.setVisibility(View.GONE);
//                    topLayout.setVisibility(View.GONE);
//                    fourthLayout.setVisibility(View.GONE);
//                } else if (amount.size() == 1) {
//
//                    fourthLayout.setVisibility(View.GONE);
//
//                    secondLayout.setVisibility(View.GONE);
//                    thirdLayout.setVisibility(View.GONE);
//                    topLayout.setVisibility(View.VISIBLE);
//
//                    topGifterOne.setVisibility(View.VISIBLE);
//                    txtUserNameOne.setVisibility(View.VISIBLE);
//                    txtUserCoinOne.setVisibility(View.VISIBLE);
//                    Toast.makeText(requireContext(), ""+amount.get(0).getUserInfo().getImage(), Toast.LENGTH_SHORT).show();
//
//                    Glide.with(topGifterOne).load(amount.get(0).getUserInfo().getImage()).into(topGifterOne);
//
//                    if (amount.get(0).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameOne.setText(amount.get(0).getUserInfo().getUsername());
//
//
//                    } else {
//                        txtUserNameOne.setText(amount.get(0).getUserInfo().getName());
//
//
//                    }
//                    txtUserCoinOne.setText(String.valueOf(amount.get(0).getCoin()));
//
//
//                } else if (amount.size() == 2) {
//                    fourthLayout.setVisibility(View.GONE);
//
//                    secondLayout.setVisibility(View.VISIBLE);
//                    thirdLayout.setVisibility(View.GONE);
//                    topLayout.setVisibility(View.VISIBLE);
//
//
//                    topGifterOne.setVisibility(View.VISIBLE);
//                    txtUserNameOne.setVisibility(View.VISIBLE);
//                    txtUserCoinOne.setVisibility(View.VISIBLE);
//
//                    topGifterTwo.setVisibility(View.VISIBLE);
//                    txtUserNameTwo.setVisibility(View.VISIBLE);
//                    txtUserCoinTwo.setVisibility(View.VISIBLE);
//
//
//                    Glide.with(topGifterOne).load(amount.get(0).getUserInfo().getImage()).into(topGifterOne);
//                    if (amount.get(0).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameOne.setText(amount.get(0).getUserInfo().getUsername());
//
//
//                    } else {
//                        txtUserNameOne.setText(amount.get(0).getUserInfo().getName());
//
//
//                    }
//                    txtUserCoinOne.setText(String.valueOf(amount.get(0).getCoin()));
//
//
//                    Glide.with(topGifterTwo).load(amount.get(1).getUserInfo().getImage()).into(topGifterTwo);
//                    if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameTwo.setText(amount.get(1).getUserInfo().getUsername());
//                    } else {
//                        txtUserNameOne.setText(amount.get(1).getUserInfo().getName());
//                    }
//                    txtUserCoinTwo.setText(String.valueOf(amount.get(1).getCoin()));
//
//
//                } else if (amount.size() == 3) {
//                    fourthLayout.setVisibility(View.GONE);
//                    secondLayout.setVisibility(View.VISIBLE);
//                    thirdLayout.setVisibility(View.VISIBLE);
//                    topLayout.setVisibility(View.VISIBLE);
//
//                    topGifterOne.setVisibility(View.VISIBLE);
//                    txtUserNameOne.setVisibility(View.VISIBLE);
//                    txtUserCoinOne.setVisibility(View.VISIBLE);
//
//                    topGifterTwo.setVisibility(View.VISIBLE);
//                    txtUserNameTwo.setVisibility(View.VISIBLE);
//                    txtUserCoinTwo.setVisibility(View.VISIBLE);
//
//                    topGifterThree.setVisibility(View.VISIBLE);
//                    txtUserNameThree.setVisibility(View.VISIBLE);
//                    txtUserCoinThree.setVisibility(View.VISIBLE);
//
//
//                    Glide.with(topGifterOne).load(amount.get(0).getUserInfo().getImage()).into(topGifterOne);
//                    if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameOne.setText(amount.get(0).getUserInfo().getUsername());
//                    } else {
//                        txtUserNameOne.setText(amount.get(0).getUserInfo().getName());
//                    }
//                    txtUserCoinOne.setText(String.valueOf(amount.get(0).getCoin()));
//
//
//                    Glide.with(topGifterTwo).load(amount.get(1).getUserInfo().getImage()).into(topGifterTwo);
//                    if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameTwo.setText(amount.get(1).getUserInfo().getUsername());
//                    } else {
//                        txtUserNameTwo.setText(amount.get(1).getUserInfo().getName());
//                    }
//                    txtUserCoinTwo.setText(String.valueOf(amount.get(1).getCoin()));
//
//                    Glide.with(topGifterThree).load(amount.get(2).getUserInfo().getImage()).into(topGifterThree);
//                    if (amount.get(2).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameThree.setText(amount.get(2).getUserInfo().getUsername());
//
//
//                    } else {
//                        txtUserNameThree.setText(amount.get(2).getUserInfo().getName());
//
//
//                    }
//                    txtUserCoinThree.setText(String.valueOf(amount.get(2).getCoin()));
//
//
//                } else if (amount.size() == 4) {
//
//                    fourthLayout.setVisibility(View.VISIBLE);
//                    secondLayout.setVisibility(View.VISIBLE);
//                    thirdLayout.setVisibility(View.VISIBLE);
//                    topLayout.setVisibility(View.VISIBLE);
//
//                    topGifterOne.setVisibility(View.VISIBLE);
//                    txtUserNameOne.setVisibility(View.VISIBLE);
//                    txtUserCoinOne.setVisibility(View.VISIBLE);
//
//                    topGifterTwo.setVisibility(View.VISIBLE);
//                    txtUserNameTwo.setVisibility(View.VISIBLE);
//                    txtUserCoinTwo.setVisibility(View.VISIBLE);
//
//                    topGifterThree.setVisibility(View.VISIBLE);
//                    txtUserNameThree.setVisibility(View.VISIBLE);
//                    txtUserCoinThree.setVisibility(View.VISIBLE);
//
//                    topGifterFourth.setVisibility(View.VISIBLE);
//                    txtUserNameFour.setVisibility(View.VISIBLE);
//                    txtUserCoinFour.setVisibility(View.VISIBLE);
//
//
//
//                    Glide.with(topGifterOne).load(amount.get(0).getUserInfo().getImage()).into(topGifterOne);
//                    if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameOne.setText(amount.get(0).getUserInfo().getUsername());
//                    } else {
//                        txtUserNameOne.setText(amount.get(0).getUserInfo().getName());
//                    }
//                    txtUserCoinOne.setText(String.valueOf(amount.get(0).getCoin()));
//
//
//                    Glide.with(topGifterTwo).load(amount.get(1).getUserInfo().getImage()).into(topGifterTwo);
//                    if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameTwo.setText(amount.get(1).getUserInfo().getUsername());
//                    } else {
//                        txtUserNameTwo.setText(amount.get(1).getUserInfo().getName());
//                    }
//                    txtUserCoinTwo.setText(String.valueOf(amount.get(1).getCoin()));
//
//                    Glide.with(topGifterThree).load(amount.get(2).getUserInfo().getImage()).into(topGifterThree);
//                    if (amount.get(2).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameThree.setText(amount.get(2).getUserInfo().getUsername());
//
//
//                    } else {
//                        txtUserNameThree.setText(amount.get(2).getUserInfo().getName());
//
//
//                    }
//                    txtUserCoinThree.setText(String.valueOf(amount.get(2).getCoin()));
//
//
//                    Glide.with(topGifterFourth).load(amount.get(3).getUserInfo().getImage()).into(topGifterFourth);
//                    if (amount.get(3).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameFour.setText(amount.get(3).getUserInfo().getUsername());
//
//
//                    } else {
//                        txtUserNameFour.setText(amount.get(3).getUserInfo().getName());
//
//
//                    }
//                    txtUserCoinFour.setText(String.valueOf(amount.get(3).getCoin()));
//
//
//
//
//
//
//                } else {
//
//
//                    fourthLayout.setVisibility(View.VISIBLE);
//                    secondLayout.setVisibility(View.VISIBLE);
//                    thirdLayout.setVisibility(View.VISIBLE);
//                    topLayout.setVisibility(View.VISIBLE);
//
//                    topGifterOne.setVisibility(View.VISIBLE);
//                    txtUserNameOne.setVisibility(View.VISIBLE);
//                    txtUserCoinOne.setVisibility(View.VISIBLE);
//
//                    topGifterTwo.setVisibility(View.VISIBLE);
//                    txtUserNameTwo.setVisibility(View.VISIBLE);
//                    txtUserCoinTwo.setVisibility(View.VISIBLE);
//
//                    topGifterThree.setVisibility(View.VISIBLE);
//                    txtUserNameThree.setVisibility(View.VISIBLE);
//                    txtUserCoinThree.setVisibility(View.VISIBLE);
//
//                    topGifterFourth.setVisibility(View.VISIBLE);
//                    txtUserNameFour.setVisibility(View.VISIBLE);
//                    txtUserCoinFour.setVisibility(View.VISIBLE);
//
//                    Glide.with(topGifterOne).load(amount.get(0).getUserInfo().getImage()).into(topGifterOne);
//                    if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameOne.setText(amount.get(0).getUserInfo().getUsername());
//                    } else {
//                        txtUserNameOne.setText(amount.get(0).getUserInfo().getName());
//                    }
//                    txtUserCoinOne.setText(String.valueOf(amount.get(0).getCoin()));
//
//                    Glide.with(topGifterTwo).load(amount.get(1).getUserInfo().getImage()).into(topGifterTwo);
//                    if (amount.get(1).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameTwo.setText(amount.get(1).getUserInfo().getUsername());
//                    } else {
//                        txtUserNameTwo.setText(amount.get(1).getUserInfo().getName());
//                    }
//                    txtUserCoinTwo.setText(String.valueOf(amount.get(1).getCoin()));
//
//                    Glide.with(topGifterThree).load(amount.get(2).getUserInfo().getImage()).into(topGifterThree);
//                    if (amount.get(2).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameThree.setText(amount.get(2).getUserInfo().getUsername());
//
//
//                    } else {
//                        txtUserNameThree.setText(amount.get(2).getUserInfo().getName());
//
//
//                    }
//                    txtUserCoinThree.setText(String.valueOf(amount.get(2).getCoin()));
//
//                    Glide.with(requireContext()).load(amount.get(3).getUserInfo().getImage()).error(R.drawable.app_logo).into(topGifterFourth);
//
//                    if (amount.get(3).getUserInfo().getName().equalsIgnoreCase("")) {
//                        txtUserNameFour.setText(amount.get(3).getUserInfo().getUsername());
//
//
//                    } else {
//                        txtUserNameFour.setText(amount.get(3).getUserInfo().getName());
//
//
//                    }
//                    txtUserCoinFour.setText(String.valueOf(amount.get(3).getCoin()));
//
//
//
//                    amount.remove(0);
//                    amount.remove(1);
//                    amount.remove(2);
////                        amount.remove(3);
//
//                    setData(amount);
//
//                }
//
//
//            } else {
//
//
//                txtNoUser.setVisibility(View.VISIBLE);
//                mainLayoutView.setVisibility(View.GONE);
//
//
//            }
//        });
//
//
//    }
//
//    private void setData(List<TopGifterModel.Detail> details) {
//
//
//        LeadeBordOverAll leatherBoardRVAdapter = new LeadeBordOverAll(details);
//
//        LeaderboardRV.setAdapter(leatherBoardRVAdapter);
//
//
//
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
//
//    }
//}