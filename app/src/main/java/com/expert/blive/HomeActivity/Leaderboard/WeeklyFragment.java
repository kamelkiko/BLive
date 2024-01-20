package com.expert.blive.HomeActivity.Leaderboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.expert.blive.ExtraFragments.LeatherBoardRVAdapter;
import com.expert.blive.ModelClass.GiftTopGifters;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class WeeklyFragment extends Fragment {

    private MvvmViewModelClass viewModelClass;
    private CircleImageView topGifterThree,topGifterTwo,topGifterOne;
    private TextView txtUserNameOne,txtUserNameTwo,txtUserNameThree,txtNoUsers,coinFirstTV,coinTwoTV,coinThreeTV;
    private RecyclerView LeaderboardRV;
    public List<GiftTopGifters.Detail> amount = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_weekly2, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        finds(view);
        getTopGifter();
    }

    private void finds(View view) {

        topGifterThree = view.findViewById(R.id.topGifterThree);
        topGifterTwo = view.findViewById(R.id.topGifterTwo);
        topGifterOne = view.findViewById(R.id.topGifterOne);
        txtUserNameOne = view.findViewById(R.id.txtUserNameOne);
        txtUserNameTwo = view.findViewById(R.id.txtUserNameTwo);
        txtUserNameThree = view.findViewById(R.id.txtUserNameThree);
        txtNoUsers = view.findViewById(R.id.txtNoUsers);
        LeaderboardRV = view.findViewById(R.id.LeaderboardRV);
        coinFirstTV = view.findViewById(R.id.coinFirstTV);
        coinTwoTV = view.findViewById(R.id.coinTwoTV);
        coinThreeTV = view.findViewById(R.id.coinThreeTV);

    }
    private void getTopGifter() {

        HashMap<String, String> data = new HashMap<>();
        data.put("type", "2");
        data.put("userId", CommonUtils.getUserId());
        viewModelClass.montlyGiftAmount(requireActivity(), data).observe(requireActivity(), giftTopGifters -> {
            if (giftTopGifters.getStatus().equalsIgnoreCase("1")) {
                txtNoUsers.setVisibility(View.GONE);
                amount = giftTopGifters.getDetails();
                if (amount.size() == 0) {
                } else if (amount.size() == 1) {
                    topGifterOne.setVisibility(View.VISIBLE);
                    txtUserNameOne.setVisibility(View.VISIBLE);

                    Glide.with(topGifterOne).load(amount.get(0).getImage()).error(R.drawable.app_logo).into(topGifterOne);

                    if (amount.get(0).getName().equalsIgnoreCase("")) {
                        txtUserNameOne.setText(amount.get(0).getUsername());
                        coinFirstTV.setText(amount.get(0).getCoin());
                    } else {
                        txtUserNameOne.setText(amount.get(0).getName());

                    }

                } else if (amount.size() == 2) {

                    topGifterOne.setVisibility(View.VISIBLE);
                    txtUserNameOne.setVisibility(View.VISIBLE);

                    topGifterTwo.setVisibility(View.VISIBLE);
                    txtUserNameTwo.setVisibility(View.VISIBLE);

                    Glide.with(topGifterOne).load(amount.get(0).getImage()).error(R.drawable.app_logo).into(topGifterOne);
                    if (amount.get(0).getName().equalsIgnoreCase("")) {
                        txtUserNameOne.setText(amount.get(0).getUsername());
                        coinFirstTV.setText(amount.get(0).getCoin());
                    } else {
                        txtUserNameOne.setText(amount.get(0).getName());
                    }
                    Glide.with(topGifterTwo).load(amount.get(1).getImage()).error(R.drawable.app_logo).into(topGifterTwo);
                    if (amount.get(1).getName().equalsIgnoreCase("")) {
                        txtUserNameTwo.setText(amount.get(1).getUsername());
                        coinTwoTV.setText(amount.get(0).getCoin());
                    } else {
                        txtUserNameOne.setText(amount.get(1).getName());
                    }
                } else if (amount.size() == 3) {
                    topGifterOne.setVisibility(View.VISIBLE);
                    txtUserNameOne.setVisibility(View.VISIBLE);

                    topGifterTwo.setVisibility(View.VISIBLE);
                    txtUserNameTwo.setVisibility(View.VISIBLE);

                    topGifterThree.setVisibility(View.VISIBLE);
                    txtUserNameThree.setVisibility(View.VISIBLE);


                    Glide.with(topGifterOne).load(amount.get(0).getImage()).error(R.drawable.app_logo).into(topGifterOne);
                    if (amount.get(1).getName().equalsIgnoreCase("")) {
                        txtUserNameOne.setText(amount.get(0).getUsername());
                        coinFirstTV.setText(amount.get(0).getCoin());
                    } else {
                        txtUserNameOne.setText(amount.get(0).getName());
                    }


                    Glide.with(topGifterTwo).load(amount.get(1).getImage()).error(R.drawable.app_logo).into(topGifterTwo);
                    if (amount.get(1).getName().equalsIgnoreCase("")) {
                        txtUserNameTwo.setText(amount.get(1).getUsername());
                        coinTwoTV.setText(amount.get(0).getCoin());
                    } else {
                        txtUserNameTwo.setText(amount.get(1).getName());
                    }
                    Glide.with(topGifterThree).load(amount.get(2).getImage()).error(R.drawable.app_logo).into(topGifterThree);
                    if (amount.get(2).getName().equalsIgnoreCase("")) {
                        txtUserNameThree.setText(amount.get(2).getUsername());
                        coinThreeTV.setText(amount.get(0).getCoin());
                    } else {
                        txtUserNameThree.setText(amount.get(2).getName());
                    }
                } else {
                    topGifterOne.setVisibility(View.VISIBLE);
                    txtUserNameOne.setVisibility(View.VISIBLE);
                    topGifterTwo.setVisibility(View.VISIBLE);
                    txtUserNameTwo.setVisibility(View.VISIBLE);

                    topGifterThree.setVisibility(View.VISIBLE);
                    txtUserNameThree.setVisibility(View.VISIBLE);
                    Glide.with(topGifterOne).load(amount.get(0).getImage()).error(R.drawable.app_logo).into(topGifterOne);
                    if (amount.get(1).getName().equalsIgnoreCase("")) {
                        txtUserNameOne.setText(amount.get(0).getUsername());
                        coinFirstTV.setText(amount.get(0).getCoin());
                    } else {
                        txtUserNameOne.setText(amount.get(0).getName());
                    }
                    Glide.with(topGifterTwo).load(amount.get(1).getImage()).error(R.drawable.app_logo).into(topGifterTwo);
                    if (amount.get(1).getName().equalsIgnoreCase("")) {
                        txtUserNameTwo.setText(amount.get(1).getUsername());
                        coinTwoTV.setText(amount.get(0).getCoin());
                    } else {
                        txtUserNameTwo.setText(amount.get(1).getName());
                    }
                    Glide.with(topGifterThree).load(amount.get(2).getImage()).error(R.drawable.app_logo).into(topGifterThree);
                    if (amount.get(2).getName().equalsIgnoreCase("")) {
                        txtUserNameThree.setText(amount.get(2).getUsername());
                        coinThreeTV.setText(amount.get(0).getCoin());
                    } else {
                        txtUserNameThree.setText(amount.get(2).getName());
                    }
                    amount.remove(0);
                    amount.remove(1);
                    amount.remove(2);
                    setAdpter(amount);

                }


            } else {
                txtNoUsers.setVisibility(View.VISIBLE);
            }
        });

    }
    private void setAdpter(List<GiftTopGifters.Detail> amount) {
        LeatherBoardRVAdapter leatherBoardRVAdapter = new LeatherBoardRVAdapter(amount);
        LeaderboardRV.setAdapter(leatherBoardRVAdapter);

    }
}

