package com.expert.blive.HomeActivity.HomeMainCategory.Popular.category;

import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.AGORA_CHANNEL_NAME;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.AGORA_LIVE_FRAME;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.AGORA_LIVE_GARAGE;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.AGORA_LIVE_IMAGE;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.AGORA_RTM_TOKEN;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.AGORA_TOKEN;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.COIN;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.COUNT;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.FOLLOWERS;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.FOLLOWER_STATUS;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.LIVE_REAL_NAME;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.USER_LEVEL;
import static com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity.USER_STAR_COUNT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.expert.blive.Agora.agoraLive.activity.AgoraPkPlayerActivity;
import com.expert.blive.Agora.agoraLive.adapters.AdapterAgoraLiveMain;
import com.expert.blive.Agora.openvcall.model.EngineConfig;
import com.expert.blive.ModelClass.ModelAgoraLiveUsers;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstants;
import com.expert.blive.utils.CommonUtils;
import com.google.android.material.card.MaterialCardView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.agora.rtc.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewestFragment extends Fragment {
    private RecyclerView nearBy_recycler_view;
    private MaterialCardView card_no_live;
    private AdapterAgoraLiveMain nearByAdapter;
    private Boolean isLiveClicked = false;
    public static String check_status = "0",garage;
    private SwipeRefreshLayout refreshLayout;
    private LiveMvvm liveMvvm;
    private MvvmViewModelClass viewModelClass;
    private List<ModelAgoraLiveUsers.Detail> list = new ArrayList<>();
    private Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        liveMvvm = new ViewModelProvider(this).get(LiveMvvm.class);
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_newest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findIds(view);
        hitApi();
        getLive();

        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(() -> {
          getLive();
            refreshLayout.setRefreshing(true);
        });

    }

    private void hitApi() {
        HashMap<String, String> data = new HashMap<>();
        data.put("userId", CommonUtils.getUserId());
        data.put("otherUserId", CommonUtils.getUserId());
        viewModelClass.someFunctionality(requireActivity(),data).observe(requireActivity(), response -> {
            if (response !=null){
                if (response.getStatus().equalsIgnoreCase("1")){
                    garage =  response.getDetails().getMyGarage();
                }
            }
        });

    }

    private void findIds(View view) {
        nearBy_recycler_view = view.findViewById(R.id.nearBy_recycler_view);
        card_no_live = view.findViewById(R.id.card_no_live);
        refreshLayout = view.findViewById(R.id.swipe);



    }



    private void getLive() {

        activity = requireActivity();

        liveMvvm.getHighestCoinAndFollowingLiveUserList(requireActivity(), CommonUtils.getUserId(), "", "", "1").observe(requireActivity(), modelAgoraLiveUsers -> {

            if (modelAgoraLiveUsers !=null){
                if (modelAgoraLiveUsers.getSuccess().equalsIgnoreCase("1")) {


                    refreshLayout.setRefreshing(false);
                    nearBy_recycler_view.setVisibility(View.VISIBLE);
                    card_no_live.setVisibility(View.GONE);
                    list = modelAgoraLiveUsers.getDetails();
                    setAdapter(list);
                } else {
                    card_no_live.setVisibility(View.VISIBLE);
                    nearBy_recycler_view.setVisibility(View.GONE);
                }
            }else {
                Toast.makeText(activity, "Technical issue..", Toast.LENGTH_SHORT).show();
            }


        });
    }

    private void setAdapter(List<ModelAgoraLiveUsers.Detail> list) {
        if (activity != null) {

            nearByAdapter = new AdapterAgoraLiveMain(activity, list, (channelName, token, userChannelId, rtmToken, image, followerCount
                    , followerStatus, purchasedCoin, liveName, liveLevel, liveStarCount, liveid, hostType, boxstatus, box, bool, count,frame) -> {
                if (!isLiveClicked) {
                    isLiveClicked = true;

                    Toast.makeText(requireContext(), "" + channelName, Toast.LENGTH_SHORT).show();
                    config().setChannelName(channelName);
                    App.getSingleton().setAgoraToken(token);
                    App.getSingleton().setMainHostUserId(userChannelId);
                    App.getSingleton().setHostType(hostType);

                    generateOtherUserToken(channelName, userChannelId, image, followerCount, followerStatus, purchasedCoin, liveName, liveLevel
                            , liveStarCount, liveid, hostType, boxstatus, box, bool, count,frame);
                } else {
                    Toast.makeText(requireContext(), "Please wait connecting...", Toast.LENGTH_SHORT).show();
                }
            });
            nearBy_recycler_view.setAdapter(nearByAdapter);
            nearByAdapter.notifyDataSetChanged();
        } else {
            getLive();
        }

    }

    private void generateOtherUserToken(String channelName, String userChannelId, String image, String followerCount, String followeStatus
            , String purchasedCoin, String liveName, String liveLevel, String lievStarCount, String liveid, String hostType, String boxtstaus
            , String box, String status_bool, String count, String frame) {
        Log.i("Agora:ChannelName", channelName);
        if (CommonUtils.isNetworkConnected(requireActivity())) {
            ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);
            apiInterface.otherRtmToken(CommonUtils.getUserId()).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(@NonNull Call<Map> call, @NonNull Response<Map> response) {
                    if (response.body() != null) {
                        isLiveClicked = false;
                        if (Objects.requireNonNull(response.body().get("success")).toString().equalsIgnoreCase("1")) {
                            String token = Objects.requireNonNull(response.body().get("token")).toString();
                            Log.i("Agora:OtherToken", token);
//                            Intent intent;
//
//                            if(hostType.equalsIgnoreCase("2")){
//                                intent = new Intent(requireActivity(), AgoraPkPlayerActivity.class);
//                            }else {
//                                intent = new Intent(requireActivity(), AgoraPkPlayerActivity.class);
//                            }

                            Intent intent = new Intent(requireActivity(), AgoraPkPlayerActivity.class);
                            intent.putExtra(AGORA_CHANNEL_NAME, channelName);
                            intent.putExtra(AppConstants.OTHER_USER_ID, userChannelId);
                            intent.putExtra(AGORA_RTM_TOKEN, token);
                            intent.putExtra(AGORA_LIVE_IMAGE, image);
                            intent.putExtra(FOLLOWERS, followerCount);
                            intent.putExtra(FOLLOWER_STATUS, followeStatus);
                            intent.putExtra(USER_LEVEL, liveLevel);
                            intent.putExtra(USER_STAR_COUNT, lievStarCount);
                            intent.putExtra(COIN, purchasedCoin);
                            intent.putExtra(LIVE_REAL_NAME, liveName);
                            intent.putExtra(AGORA_LIVE_FRAME,frame);
                            intent.putExtra(AGORA_LIVE_GARAGE,garage);
                            intent.putExtra(COUNT, count);
                            intent.putExtra("role", Constants.CLIENT_ROLE_AUDIENCE);
                            intent.putExtra(AGORA_TOKEN, token);
                            intent.putExtra("liveid", liveid);
                            intent.putExtra("hostType", hostType);
                            intent.putExtra("boxstatus", boxtstaus);
                            intent.putExtra("box", box);
                            startActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Map> call, @NonNull Throwable t) {
                    Log.i("Agora:OtherToken", t.getMessage());
                }
            });
        } else {
            Toast.makeText(requireActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }


    }

    protected App application() {
        return (App) requireActivity().getApplication();
    }


    protected EngineConfig config() {
        return application().engine();
    }
}

