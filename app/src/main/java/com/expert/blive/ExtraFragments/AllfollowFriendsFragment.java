package com.expert.blive.ExtraFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.AdapterFollowFollowing;
import com.expert.blive.Adapter.FriendsAdapter;
import com.expert.blive.ModelClass.FollowingDataModel;
import com.expert.blive.ModelClass.FollowingRoot;
import com.expert.blive.ModelClass.FriendsModel;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import java.util.List;


public class AllfollowFriendsFragment extends Fragment {

    private RecyclerView search_recycler;
    private String checkStatus;
    private TextView dailyTv, weeklyTV, monthlyTV, txtNoUser,TotallyTV;
    private View viewDaily, viewWeekly, viewMonthly,viewTotally;
    private LinearLayout llFollowing, llFriends, llFollowers,llVisitor;
    private ImageView back;
    private MvvmViewModelClass viewModelClass;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_allfollow_friends, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findIds(view);
        clicks();
        checkScreen();


    }

    private void clicks() {

        back.setOnClickListener(view -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit());

        llFollowing.setOnClickListener(view -> {
            checkStatus = "1";
            getFollowingData();
            viewWeekly.setVisibility(View.GONE);
            viewDaily.setVisibility(View.VISIBLE);
            viewMonthly.setVisibility(View.GONE);
            viewTotally.setVisibility(View.GONE);
            weeklyTV.setTextColor(getResources().getColor(R.color.grey));
            dailyTv.setTextColor(getResources().getColor(R.color.app_pink_color));
            monthlyTV.setTextColor(getResources().getColor(R.color.grey));
            TotallyTV.setTextColor(getResources().getColor(R.color.grey));

        });
        llFollowers.setOnClickListener(view -> {

            checkStatus = "3";

           getFollowersData();
            viewMonthly.setVisibility(View.VISIBLE);
            viewDaily.setVisibility(View.GONE);
            viewTotally.setVisibility(View.GONE);
            viewWeekly.setVisibility(View.GONE);
            monthlyTV.setTextColor(getResources().getColor(R.color.app_pink_color));
            dailyTv.setTextColor(getResources().getColor(R.color.grey));
            weeklyTV.setTextColor(getResources().getColor(R.color.grey));
            TotallyTV.setTextColor(getResources().getColor(R.color.grey));


        });
        llFriends.setOnClickListener(view -> {
            getFriendsData();
            checkStatus = "2";
            viewDaily.setVisibility(View.GONE);
            viewMonthly.setVisibility(View.GONE);
            viewTotally.setVisibility(View.GONE);
            viewWeekly.setVisibility(View.VISIBLE);
            dailyTv.setTextColor(getResources().getColor(R.color.grey));
            weeklyTV.setTextColor(getResources().getColor(R.color.app_pink_color));
            monthlyTV.setTextColor(getResources().getColor(R.color.grey));
            TotallyTV.setTextColor(getResources().getColor(R.color.grey));

        });

        llVisitor.setOnClickListener(view -> {
            getVisitors();

            checkStatus = "4";
            viewDaily.setVisibility(View.GONE);
            viewMonthly.setVisibility(View.GONE);
            viewTotally.setVisibility(View.VISIBLE);
            viewWeekly.setVisibility(View.GONE);
            dailyTv.setTextColor(getResources().getColor(R.color.grey));
            TotallyTV.setTextColor(getResources().getColor(R.color.app_pink_color));
            weeklyTV.setTextColor(getResources().getColor(R.color.grey));
            monthlyTV.setTextColor(getResources().getColor(R.color.grey));

        });


    }

    private void checkScreen() {
        if (checkStatus.equalsIgnoreCase("1")) {

            getFollowingData();
            viewWeekly.setVisibility(View.GONE);
            viewDaily.setVisibility(View.VISIBLE);
            viewTotally.setVisibility(View.GONE);
            viewMonthly.setVisibility(View.GONE);
            weeklyTV.setTextColor(getResources().getColor(R.color.grey));
            dailyTv.setTextColor(getResources().getColor(R.color.app_pink_color));
            monthlyTV.setTextColor(getResources().getColor(R.color.grey));
            TotallyTV.setTextColor(getResources().getColor(R.color.grey));

        } else if (checkStatus.equalsIgnoreCase("2")) {

            getFriendsData();
            viewDaily.setVisibility(View.GONE);
            viewMonthly.setVisibility(View.GONE);
            viewTotally.setVisibility(View.GONE);
            viewWeekly.setVisibility(View.VISIBLE);
            dailyTv.setTextColor(getResources().getColor(R.color.grey));
            weeklyTV.setTextColor(getResources().getColor(R.color.app_pink_color));
            monthlyTV.setTextColor(getResources().getColor(R.color.grey));
            TotallyTV.setTextColor(getResources().getColor(R.color.grey));


        } else if (checkStatus.equalsIgnoreCase("3")) {


            getFollowersData();
            viewMonthly.setVisibility(View.VISIBLE);
            viewDaily.setVisibility(View.GONE);
            viewTotally.setVisibility(View.GONE);
            viewWeekly.setVisibility(View.GONE);
            monthlyTV.setTextColor(getResources().getColor(R.color.app_pink_color));
            dailyTv.setTextColor(getResources().getColor(R.color.grey));
            TotallyTV.setTextColor(getResources().getColor(R.color.grey));
            weeklyTV.setTextColor(getResources().getColor(R.color.grey));


        }else if (checkStatus.equalsIgnoreCase("4")) {


            getVisitors();
            viewDaily.setVisibility(View.GONE);
            viewMonthly.setVisibility(View.GONE);
            viewTotally.setVisibility(View.VISIBLE);
            viewWeekly.setVisibility(View.GONE);
            monthlyTV.setTextColor(getResources().getColor(R.color.grey));
            TotallyTV.setTextColor(getResources().getColor(R.color.app_pink_color));
            dailyTv.setTextColor(getResources().getColor(R.color.grey));
            weeklyTV.setTextColor(getResources().getColor(R.color.grey));


        }
    }

    private void getVisitors() {

        viewModelClass.getVisitors(CommonUtils.getUserId()).observe(requireActivity(), visitRoot -> {
           if(visitRoot!=null)
           {
            if (visitRoot.getStatus().equalsIgnoreCase("1")){

                if (visitRoot.getDetails() != null) {
                    txtNoUser.setVisibility(View.GONE);
                    setData(visitRoot.getDetails());
                    search_recycler.setVisibility(View.VISIBLE);
                } else {
                    txtNoUser.setVisibility(View.VISIBLE);
                }
            }else {
                txtNoUser.setVisibility(View.VISIBLE);
                search_recycler.setVisibility(View.GONE);
            }
        }else {
               Toast.makeText(requireActivity(), "root is null", Toast.LENGTH_SHORT).show();
           }
        });
    }

    private void getFriendsData() {

        viewModelClass.getFriends(CommonUtils.getUserId()).observe(requireActivity(), friendsModel -> {
            if(friendsModel !=null){
            if (friendsModel.getStatus().equalsIgnoreCase("1")) {

                if (friendsModel.getDetails() != null) {
                    txtNoUser.setVisibility(View.GONE);
                    setData(friendsModel.getDetails());
                    search_recycler.setVisibility(View.VISIBLE);
                } else {
                    txtNoUser.setVisibility(View.VISIBLE);
                }
            }else {
                txtNoUser.setVisibility(View.VISIBLE);
                search_recycler.setVisibility(View.GONE);
            }
        }else
            {
                Toast.makeText(requireActivity(), "root is null", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setDataFriends(List<List<FriendsModel.Deatail>> deatails) {


        FriendsAdapter friendsAdapter = new FriendsAdapter(deatails);
        search_recycler.setAdapter(friendsAdapter);


    }

    private void getFollowingData() {

        viewModelClass.getFollowing(CommonUtils.getUserId()).observe(requireActivity(), followingDataModel -> {
            if(followingDataModel!=null){
                if (followingDataModel.getStatus().equalsIgnoreCase("1")) {
                 if (followingDataModel.getDetails() != null) {
                     txtNoUser.setVisibility(View.GONE);
                     setData(followingDataModel.getDetails());
                     search_recycler.setVisibility(View.VISIBLE);
                 } else {
                     txtNoUser.setVisibility(View.VISIBLE);
                 }
                } else {
                    txtNoUser.setVisibility(View.VISIBLE);
                    search_recycler.setVisibility(View.GONE);
                }
                Toast.makeText(requireActivity(), "root is null", Toast.LENGTH_SHORT).show();
            }else {
            }
        });

    }

    private void setData(List<FollowingDataModel.Detail> details) {

        AdapterFollowFollowing adapterFollowFollowing = new AdapterFollowFollowing(details, checkStatus,requireContext(), this::hitfollowUnfollow);
        search_recycler.setAdapter(adapterFollowFollowing);
    }

    private void hitfollowUnfollow(String otherUserId, String checkStatus, ImageView textView) {
        viewModelClass.followingRootLiveData(CommonUtils.getUserId(), otherUserId).observe(requireActivity(), followingRoot -> {
            if (followingRoot.getSuccess().equals("1")) {
                if (followingRoot.isFollowing_status()) {

                    if (checkStatus.equalsIgnoreCase("3")){
                        textView.setVisibility(View.GONE);
                    }else if (checkStatus.equalsIgnoreCase("1")){
                        textView.setVisibility(View.GONE);
                    }else {
                        textView.setVisibility(View.GONE);
                    }
                } else {
                    if (checkStatus.equalsIgnoreCase("3")){
                        textView.setVisibility(View.GONE);
                    }else if (checkStatus.equalsIgnoreCase("1")){
                        textView.setVisibility(View.GONE);
                    }else {
                        textView.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void getFollowersData() {

        viewModelClass.getFollowers(CommonUtils.getUserId()).observe(requireActivity(), followingDataModel -> {
            if (followingDataModel.getStatus().equalsIgnoreCase("1")) {
                search_recycler.setVisibility(View.VISIBLE);
                txtNoUser.setVisibility(View.GONE);
                setData(followingDataModel.getDetails());
            } else {
                search_recycler.setVisibility(View.GONE);
                txtNoUser.setVisibility(View.VISIBLE);
            }
        });

    }
    private void findIds(View view) {

        checkStatus = getArguments().getString("checkStatus");

        llFollowing = view.findViewById(R.id.llFollowing);
        txtNoUser = view.findViewById(R.id.txtNoUser);
        llFriends = view.findViewById(R.id.llFriends);
        llFollowers = view.findViewById(R.id.llFollowers);
        search_recycler = view.findViewById(R.id.search_recycler);
        weeklyTV = view.findViewById(R.id.weeklyTV);
        viewDaily = view.findViewById(R.id.viewDaily);
        dailyTv = view.findViewById(R.id.DailyTv);
        viewWeekly = view.findViewById(R.id.viewWeekly);
        monthlyTV = view.findViewById(R.id.monthlyTV);
        viewMonthly = view.findViewById(R.id.viewMonthly);
        back = view.findViewById(R.id.back);
        llVisitor = view.findViewById(R.id.llVisitor);
        TotallyTV = view.findViewById(R.id.TotallyTV);
        viewTotally = view.findViewById(R.id.viewTotally);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.INVISIBLE);
    }
}