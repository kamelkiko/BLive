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

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.expert.blive.Agora.agoraLive.activity.AgoraPkPlayerActivity;
import com.expert.blive.Agora.agoraLive.activity.OtherUserDataModel;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.AdapterCountry;
import com.expert.blive.Adapter.BannerAdapter;
import com.expert.blive.Agora.agoraLive.adapters.AdapterAgoraLiveMain;
import com.expert.blive.Agora.agoraLive.ui.VideoGridContainer;
import com.expert.blive.Agora.openvcall.model.EngineConfig;
import com.expert.blive.ExtraFragments.CountryFragments;
import com.expert.blive.HomeActivity.HomeMainCategory.Popular.PopularMainFragment;
import com.expert.blive.ModelClass.DetailCountry;
import com.expert.blive.ModelClass.ModelAgoraLiveUsers;
import com.expert.blive.R;
import com.expert.blive.databinding.FragmentPopularBinding;
import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstants;
import com.expert.blive.utils.CommonUtils;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.agora.rtc.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularFragment extends Fragment implements AdapterCountry.GetData, SwipeRefreshLayout.OnRefreshListener {

    private FragmentPopularBinding binding;
    private LiveMvvm liveMvvm;
    private BannerAdapter bannerAdapter;
    private Boolean isLiveClicked = false;
    private MvvmViewModelClass viewModelClass;
    private List<ModelAgoraLiveUsers.Detail> list = new ArrayList<>();
    private String mySterious,garage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the  layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        liveMvvm = new ViewModelProvider(this).get(LiveMvvm.class);
        binding = FragmentPopularBinding.inflate(inflater, container, false);

        getDetail();
        getBanner();
        getLive("");
        getCountry();

        bannerAdapter = new BannerAdapter(requireContext(), new ArrayList<>());
        binding.imageSlider.setSliderAdapter(bannerAdapter);
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.SWAP);
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        binding.imageSlider.startAutoCycle();
        binding.swipe.setOnRefreshListener(PopularFragment.this);

        binding.moreButton.setOnClickListener(view -> {

            CountryFragments userDetailsFragment = new CountryFragments(this::getData);
            userDetailsFragment.show(requireActivity().getSupportFragmentManager(), userDetailsFragment.getTag());

        });

        return binding.getRoot();
    }

    private void getDetail() {
        HashMap<String, String> data = new HashMap<>();
        data.put("userId", CommonUtils.getUserId());
        data.put("otherUserId", CommonUtils.getUserId());
        viewModelClass.someFunctionality(requireActivity(),data).observe(requireActivity(), response -> {
            if (response !=null){
             if (response.getStatus().equalsIgnoreCase("1")){
                 getLive(response.getDetails().getCountry());
                 getReword(response.getDetails());
                 mySterious = response.getDetails().getMystryMan();
                 garage =  response.getDetails().getMyGarage();
             }
            }
        });

    }

    private void getReword(OtherUserDataModel.Details details) {

        if (details.getBuy()){
            if (details.getClaimed()==0){
                Dialog viewDetails_box = new Dialog(requireContext());
                viewDetails_box.setContentView(R.layout.reword_dialog);
                viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
                Window window = viewDetails_box.getWindow();
                viewDetails_box.setCanceledOnTouchOutside(true);
                window.setGravity(Gravity.CENTER);
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                viewDetails_box.show();
                viewDetails_box.setCanceledOnTouchOutside(true);
                TextView textView = viewDetails_box.findViewById(R.id.rewordCoin);
                RelativeLayout claimBTN = viewDetails_box.findViewById(R.id.claimBTN);
                ImageView close_BTN = viewDetails_box.findViewById(R.id.close_BTN);

                textView.setText(details.getAmount());
                claimBTN.setOnClickListener(view -> hitApi(viewDetails_box));
                close_BTN.setOnClickListener(view -> viewDetails_box.dismiss());

            }else {
            }
        }

    }

    private void hitApi(Dialog viewDetails_box) {

        viewModelClass.get_my_claim(CommonUtils.getUserId()).observe(requireActivity(), response -> {
            if (response !=null){
                if (response.getStatus()==1){
                    viewDetails_box.dismiss();
                }else {
                    Toast.makeText(requireContext(), ""+response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void getLive(String s) {

        liveMvvm.getAgoraLiveUsers(requireActivity(), CommonUtils.getUserId(), "", "", "1",s).observe(requireActivity(), modelAgoraLiveUsers -> {
            if (modelAgoraLiveUsers!=null){
                if (modelAgoraLiveUsers.getSuccess().equalsIgnoreCase("1")) {
                    binding.swipe.setRefreshing(false);
                    binding.nearByRecyclerView.setVisibility(View.VISIBLE);
                    binding.cardNoLive.setVisibility(View.GONE);
                    list = modelAgoraLiveUsers.getDetails();
                    setAdapter(list);
                } else {
                    binding.cardNoLive.setVisibility(View.VISIBLE);
                    binding.nearByRecyclerView.setVisibility(View.GONE);
                }
            }else {
                Toast.makeText(requireContext(), "Technical issue..", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void getBanner() {

        viewModelClass.bannerRootLiveData().observe(requireActivity(), bannerRoot -> {
            if (bannerRoot !=null){
                if (bannerRoot != null) {
                    bannerAdapter.loadData(bannerRoot.getDetails());
                }
            }else {
                Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCountry() {

        viewModelClass.getCountry(requireActivity()).observe(requireActivity(), detailCountry -> {
            if (detailCountry.getStatus().equalsIgnoreCase("1")) {
                setAdapters(detailCountry.getDetails());
            }
        });
    }

    private void setAdapters(List<DetailCountry.Details> detailss) {
        AdapterCountry adapterCountry = new AdapterCountry(requireContext(), detailss, s -> {
            if (s.equals("Global")) {
                getLive("");
            } else {
                getLive(s);
            }
        });

        binding.countryFlag.setAdapter(adapterCountry);

    }
    @Override
    public void getData(String s) {

        binding.swipe.setRefreshing(true);
        getLive(s);

    }
    private void setAdapter(List<ModelAgoraLiveUsers.Detail> list) {
        AdapterAgoraLiveMain nearByAdapter = new AdapterAgoraLiveMain(requireActivity(), list, (channelName, token, userChannelId, rtmToken
                , image, followerCount, followerStatus, purchasedCoin, liveName, liveLevel, liveStarCount, liveid, hostType, boxstatus, box
                , bool, count,frame) -> {
            if (!isLiveClicked) {
                isLiveClicked = true;

                config().setChannelName(channelName);
                App.getSingleton().setAgoraToken(token);
                App.getSingleton().setMainHostUserId(userChannelId);
                App.getSingleton().setHostType(hostType);

                generateOtherUserToken(channelName, userChannelId, image, followerCount, followerStatus, purchasedCoin, liveName, liveLevel, liveStarCount
                        , liveid, hostType, boxstatus, box, count,frame);
            } else {
                Toast.makeText(requireContext(), "Please wait connecting...", Toast.LENGTH_SHORT).show();
            }
        });
        binding.nearByRecyclerView.setAdapter(nearByAdapter);

    }

    protected EngineConfig config() {

        return application().engine();
    }

    protected App application() {

        return (App) requireActivity().getApplication();
    }

    private void generateOtherUserToken(String channelName, String userChannelId, String image, String followerCount, String followeStatus
                                        , String purchasedCoin, String liveName, String liveLevel, String lievStarCount, String liveid, String hostType
                                        , String boxtstaus, String box, String count, String frame) {

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
                            intent.putExtra(AGORA_LIVE_FRAME, frame);
                            intent.putExtra(AGORA_LIVE_GARAGE, garage);
                            if (hostType.equalsIgnoreCase("2")) {
                                intent.putExtra(COUNT, "2");
                                App.getSingleton().setMyPkBattle("1");
                                VideoGridContainer.MAX_USER = 2;
                            } else {
                                intent.putExtra(COUNT, count);
                            }
                            intent.putExtra("role", Constants.CLIENT_ROLE_AUDIENCE);
                            intent.putExtra(AGORA_TOKEN, token);
                            intent.putExtra("liveid", liveid);
                            intent.putExtra("hostType", hostType);
                            intent.putExtra("boxstatus", boxtstaus);
                            intent.putExtra("box", box);
                            intent.putExtra("mySterious",mySterious);
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

    @Override
    public void onRefresh() {

        new Handler().postDelayed(() -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new PopularMainFragment()).addToBackStack(null).commit(), 500);
    }

}