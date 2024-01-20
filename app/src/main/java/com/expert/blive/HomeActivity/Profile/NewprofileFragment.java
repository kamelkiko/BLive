package com.expert.blive.HomeActivity.Profile;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.expert.blive.Agora.agoraLive.activity.OtherUserDataModel;
import com.expert.blive.ExtraFragments.ApplyForHost;
import com.expert.blive.ExtraFragments.WalletFragments;
import com.expert.blive.HomeActivity.Profile.VIP.Fragment_My_VIP;
import com.expert.blive.HomeActivity.Profile.category.ApplyForAgencyFragment;
import com.expert.blive.HomeActivity.Profile.category.MyBagFragment;
import com.expert.blive.MainActivity;
import com.expert.blive.Adapter.BannerAdapter;
import com.expert.blive.ExtraFragments.AllfollowFriendsFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Popular.category.GamesFragment;
import com.expert.blive.HomeActivity.Profile.Family.MyInviteFragment;
import com.expert.blive.HomeActivity.Profile.OtherOption.BlockUserFragment;
import com.expert.blive.HomeActivity.Profile.OtherOption.OtherOptionFragment;
import com.expert.blive.HomeActivity.Profile.category.CoinHistoryFragment;
import com.expert.blive.HomeActivity.Profile.category.MyLevelFragment;
import com.expert.blive.HomeActivity.Profile.category.MyWalletFragment;
import com.expert.blive.HomeMainActivity;
import com.expert.blive.ModelClass.Banner.BannerRoot;

import com.expert.blive.ModelClass.FolowCountStatus;
import com.expert.blive.ModelClass.LogOutClass;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.ModelClass.Poster.PosterRoot;

import com.expert.blive.ModelClass.TotalCoinModal;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;

import com.expert.blive.utils.CommonUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.opensource.svgaplayer.SVGAImageView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

public class NewprofileFragment extends Fragment {

    private TextView emmaTV, rlDiamonds, user_id_profile, user_status, follower, friends, following, userCountry, visitor, level, expCoin, tx58, likes, gender, recivedCoin;
    private ImageView user_img_profile, add_banner, talent_image, approveImage, level_image_user;
    private OtpRoot details;
    private Fragment fragment;
    SliderView imageSlider;
    private BannerAdapter bannerAdapter;
    String talentImage;
    SVGAImageView profileFRame;
    private Bundle bundle;
    private LottieAnimationView animationView;
    private SwipeRefreshLayout refreshLayout;
    private MvvmViewModelClass mvvmViewModelClass;
    private RelativeLayout setting_profile, llVisitor, gamebtt, llTalentImage, llFollowers, llFriends, llFollowing, media, medals, reward,
            check_status_layout, check_status, check_agencyStatus, level_layout, top_up, other_option, coinHistory, applyHost, Store,my_bag,
            block_user,Wallet,fanClub,invites;
    private String levelImage;
    private RelativeLayout rlLoading;
    private NestedScrollView scrollView;
    public static String accountCheck, mysteriousCheck;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mvvmViewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_newprofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg_gradient);

        details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

        Toast.makeText(requireContext(), "userId  "+CommonUtils.getUserId(), Toast.LENGTH_SHORT).show();

        CoinHistoryFragment.userId = details.getId();
        getLevel();
        findId(view);
        hitApi();
        OnClick(view);
        getBanner();
        getCoin();
        getFollowUnFollowCount();




        bannerAdapter = new BannerAdapter(requireContext(), new ArrayList<>());
        imageSlider.setSliderAdapter(bannerAdapter);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.SWAP);
        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        imageSlider.startAutoCycle();

        HomeMainActivity.screenStatus = 4;
        rlLoading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

    }

    private void getAnimation() {
        animationView.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> animationView.setVisibility(View.GONE), 3000);
    }

    private void hitApi() {
        HashMap<String, String> data = new HashMap<>();

        data.put("userId", CommonUtils.getUserId());
        data.put("otherUserId", CommonUtils.getUserId());
        mvvmViewModelClass.someFunctionality(requireActivity(),data).observe(requireActivity(), response -> {
            if (response !=null){
                if (response.getStatus().equalsIgnoreCase("1")){
                    setData(response.getDetails());
                    accountCheck = response.getDetails().getInvisibleVisitor();
                    mysteriousCheck = response.getDetails().getMystryMan();
                }
            }
        });

    }

    private void getBanner() {

        mvvmViewModelClass.bannerRootLiveData().observe(requireActivity(), bannerRoot -> {

            if (bannerRoot != null) {

                bannerAdapter.loadData(bannerRoot.getDetails());
            }

        });
    }

    private void getFollowUnFollowCount() {

        mvvmViewModelClass.getAllCounts(CommonUtils.getUserId()).observe(requireActivity(), new Observer<FolowCountStatus>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(FolowCountStatus folowCountStatus) {
                if(folowCountStatus !=null)
                {
                if (folowCountStatus.getStatus().equalsIgnoreCase("1")) {
                    rlLoading.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    follower.setText(folowCountStatus.getDetails().getFollowersCount().toString());
                    following.setText(folowCountStatus.getDetails().getFollowingCount().toString());
                    friends.setText(folowCountStatus.getDetails().getFriendsCount().toString());
                    visitor.setText(folowCountStatus.getDetails().getVisitorsCount().toString());

                }else {
                    rlLoading.setVisibility(View.GONE);

                }
            }else
                {
                    Toast.makeText(requireContext(), "root is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @SuppressLint("SetTextI18n")
    private void getLevel() {

        mvvmViewModelClass.myLevelRootLiveData(details.getId()).observe(requireActivity(), myLevelRoot -> {

            if (myLevelRoot != null) {

                if (myLevelRoot.getDetails() == null) {
                    level.setText("Lv." + " 0");
                    App.getSharedpref().saveString("level", "0");

                } else {
                    if (!App.getSharedpref().getString("level").equalsIgnoreCase(myLevelRoot.getDetails().getMy_level())) {

                        animationView.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                animationView.setVisibility(View.GONE);
                            }
                        }, 3000);
                    } else {

                    }
                    App.getSharedpref().saveString("level", myLevelRoot.getDetails().getMy_level());
                    level.setText("Lv." + " " + myLevelRoot.getDetails().getMy_level());
                    Glide.with(requireContext()).load(myLevelRoot.getDetails().getImage()).into(level_image_user);

                    levelImage = myLevelRoot.getDetails().getImage();
                }
            }
        });
    }

    private void getCoin() {

        mvvmViewModelClass.getCoinRootLiveData(details.getId()).observe(requireActivity(), new Observer<TotalCoinModal>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(TotalCoinModal getCoinRoot) {

                if (getCoinRoot != null) {

                    if (getCoinRoot.getSuccess().equalsIgnoreCase("1")) {

                        if (getCoinRoot.getDetails() == null) {
                            recivedCoin.setText("0.00");

                            rlDiamonds.setText("0.00");
                        } else {
                            recivedCoin.setText(getCoinRoot.getDetails().getMyCoins());
                            rlDiamonds.setText(getCoinRoot.getDetails().getPurchasedCoin());
                        }
                    } else {
                        expCoin.setText("0");
                        tx58.setText("0");
                        rlDiamonds.setText("0");
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            assert data != null;
            Uri imageUriPhotos = data.getData();
            postImage(imageUriPhotos.getPath());
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(requireContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "Image Uploading Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void postImage(String imageUriPhotos) {

        mvvmViewModelClass.posterRootLiveData(CommonUtils.getRequestBodyText(CommonUtils.getUserId()), CommonUtils.getFileData(imageUriPhotos, "image")).observe(requireActivity(), new Observer<PosterRoot>() {
            @Override
            public void onChanged(PosterRoot posterRoot) {
                if (posterRoot != null) {
                    if (posterRoot.getSuccess().equalsIgnoreCase("1")) {
                        Toast.makeText(requireActivity(), "" + posterRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        OtpRoot details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
                        details.setPoster_img(posterRoot.getDetails().getImage());
                        App.getSharedpref().saveModel(AppConstant.USER_INFO, details);
                    }
                } else {

                    Toast.makeText(requireContext(), "response null", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @SuppressLint("SetTextI18n")
    private void setData(OtherUserDataModel.Details details) {
        user_id_profile.setText(this.details.getUsername());
        CommonUtils.setAnimation(requireContext(), details.getMyFrame(), profileFRame);
        if (this.details.gender != null) {
            gender.setText(details.getGender());
        } else {
            gender.setText("Gender");
        }
        if (this.details.leval != null) {
            level.setText("Lv " + this.details.leval);
        } else {
            level.setText("Lv");
        }
        Glide.with(requireContext()).load(this.details.getUserLevelImage()).into(level_image_user);
        if (this.details != null) {
            user_id_profile.setText(this.details.getUsername());
            Glide.with(requireContext()).load(this.details.getUserLevelImage()).into(level_image_user);
            emmaTV.setText(this.details.getName());
            if (this.details.getImage() != null && this.details.getImage().length() != 0) {
                Picasso.with(requireActivity()).load(this.details.getImage()).error(R.drawable.user_7).into(user_img_profile);
                Log.d("TAG", "onViewCreated: " + this.details.getImage());
            }
        } else {
            emmaTV.setText("username");
        }
        getAnimation();
    }
    @Override
    public void onResume() {
        super.onResume();
        mvvmViewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        getFollowUnFollowCount();
        details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
        hitApi();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
    }
    private void findId(View view) {
        emmaTV = view.findViewById(R.id.emmaTV);
        rlDiamonds = view.findViewById(R.id.rlDiamonds);
        refreshLayout = view.findViewById(R.id.swipe);
        level_image_user = view.findViewById(R.id.level_image_user);
        animationView = view.findViewById(R.id.animationView);
        llFollowing = view.findViewById(R.id.llFollowing);
        user_id_profile = view.findViewById(R.id.user_id_profile);
        friends = view.findViewById(R.id.friends);
        user_img_profile = view.findViewById(R.id.user_img_profile);
        llFriends = view.findViewById(R.id.llFriends);
        llFollowers = view.findViewById(R.id.llFollowers);
        llTalentImage = view.findViewById(R.id.llTalentImage);
        setting_profile = view.findViewById(R.id.setting_profile);
        approveImage = view.findViewById(R.id.approveImage);
        check_status_layout = view.findViewById(R.id.check_status_layout);
        user_status = view.findViewById(R.id.user_status);
        check_status = view.findViewById(R.id.check_status);
        following = view.findViewById(R.id.following);
        follower = view.findViewById(R.id.follower);
        userCountry = view.findViewById(R.id.userCountry);
        level = view.findViewById(R.id.level);
        likes = view.findViewById(R.id.likes);
        add_banner = view.findViewById(R.id.add_banner);
        level_layout = view.findViewById(R.id.level_layout);
        top_up = view.findViewById(R.id.top_up);
        other_option = view.findViewById(R.id.other_option);
        coinHistory = view.findViewById(R.id.coinHistory);
        talent_image = view.findViewById(R.id.talent_image);
        imageSlider = view.findViewById(R.id.imageSlider);
        gamebtt = view.findViewById(R.id.gamebtt);
        llVisitor = view.findViewById(R.id.llVisitor);
        visitor = view.findViewById(R.id.visitor);
        gender = view.findViewById(R.id.textgender);
        recivedCoin = view.findViewById(R.id.recivedCoin);
        media = view.findViewById(R.id.media);
        medals = view.findViewById(R.id.medals);
        reward = view.findViewById(R.id.reward);
        applyHost = view.findViewById(R.id.addHost);
        Store = view.findViewById(R.id.store);
        profileFRame = view.findViewById(R.id.profileFrame);
        my_bag = view.findViewById(R.id.my_bag);
        block_user = view.findViewById(R.id.block_user);
        Wallet = view.findViewById(R.id.Wallet);
        rlLoading = view.findViewById(R.id.rlLoading);
        scrollView = view.findViewById(R.id.scrollView);
        fanClub = view.findViewById(R.id.fanClub);
        invites = view.findViewById(R.id.invites);
    }

    private void OnClick(View view) {

        view.findViewById(R.id.EditProfileImg).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new EditProfileFragment()).addToBackStack(null).commit());
        view.findViewById(R.id.walletBTN).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new WalletFragments()).addToBackStack(null).commit());
        view.findViewById(R.id.vipbutton).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new Fragment_My_VIP()).addToBackStack(null).commit());
        view.findViewById(R.id.Payroll).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new ApplyForAgencyFragment()).addToBackStack(null).commit());
        view.findViewById(R.id.agent).setOnClickListener(view1 -> Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show());
        invites.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new MyInviteFragment()).addToBackStack(null).commit());
        Wallet.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new WalletFragments()).addToBackStack(null).commit());
        block_user.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new BlockUserFragment()).addToBackStack(null).commit());
        my_bag.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MyBagFragment()).addToBackStack(null).commit());
        gamebtt.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new GamesFragment()).addToBackStack(null).commit());
        other_option.setOnClickListener(view12 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new OtherOptionFragment()).addToBackStack(null).commit());
        media.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MyProfileFragment()).addToBackStack(null).commit());
        reward.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new RewordFragment()).addToBackStack(null).commit());
        applyHost.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new ApplyForHost()).addToBackStack(null).commit());
        Store.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new StoreFragment()).addToBackStack(null).commit());
        fanClub.setOnClickListener(view1 -> Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show());
        medals.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MyLevelFragment()).addToBackStack(null).commit());

        llFollowers.setOnClickListener(view1 -> {
            bundle = new Bundle();
            bundle.putString("checkStatus", "3");
            fragment = new AllfollowFriendsFragment();
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).addToBackStack(null).commit();
        });

        llFollowing.setOnClickListener(view1 -> {
            bundle = new Bundle();
            bundle.putString("checkStatus", "1");
            fragment = new AllfollowFriendsFragment();
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).addToBackStack(null).commit();
        });

        llFriends.setOnClickListener(view1 -> {
            bundle = new Bundle();
            bundle.putString("checkStatus", "2");
            fragment = new AllfollowFriendsFragment();
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).addToBackStack(null).commit();
        });

        llVisitor.setOnClickListener(view1 -> {
            bundle = new Bundle();
            bundle.putString("checkStatus", "4");
            fragment = new AllfollowFriendsFragment();
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).addToBackStack(null).commit();
        });

        setting_profile.setOnClickListener(view13 -> {
            final Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.log_out_dialog);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.show();
            dialog.findViewById(R.id.yesText).setOnClickListener(view131 -> mvvmViewModelClass.logOutClassLiveData(requireActivity(), details.getId()).observe(requireActivity(), new Observer<LogOutClass>() {
                @Override
                public void onChanged(LogOutClass logOutClass) {
                    App.getSharedpref().clearPreferences();
                    dialog.dismiss();
                    startActivity(new Intent(requireContext(), MainActivity.class));
                }
            }));
            dialog.findViewById(R.id.noText).setOnClickListener(view1312 -> dialog.dismiss());
        });
    }
}