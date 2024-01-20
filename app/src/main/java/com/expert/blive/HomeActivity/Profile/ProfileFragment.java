package com.expert.blive.HomeActivity.Profile;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.expert.blive.ExtraFragments.AllfollowFriendsFragment;
import com.expert.blive.ExtraFragments.ApplyForHost;
import com.expert.blive.ExtraFragments.FindFriends;
import com.expert.blive.ExtraFragments.LeadtherboardFragment;
import com.expert.blive.ExtraFragments.SubscribedTab;

import com.expert.blive.ExtraFragments.WalletFragments;
import com.expert.blive.HomeActivity.Leaderboard.MainLeaderboardFragment;
import com.expert.blive.HomeActivity.Profile.category.ApplyForAgencyFragment;
import com.expert.blive.HomeActivity.Profile.category.CoinHistoryFragment;
import com.expert.blive.HomeActivity.Profile.category.LevelsScreenFragment;
import com.expert.blive.HomeActivity.Profile.category.MyLevelFragment;
import com.expert.blive.HomeMainActivity;
import com.expert.blive.LiveHistoryFragment;
import com.expert.blive.MainActivity;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.ModelClass.CheckStatusRoot;
import com.expert.blive.ModelClass.FolowCountStatus;
import com.expert.blive.ModelClass.LogOutClass;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.ModelClass.Poster.PosterRoot;
import com.expert.blive.ModelClass.TotalCoinModal;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;
import com.expert.blive.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ProfileFragment extends Fragment {

    private TextView emmaTV, rlDiamonds, user_id_profile, user_status, follower, friends, following, userCountry, level, expCoin, tx58, likes;
    private ImageView user_img_profile, add_banner, talent_image, approveImage, level_image_user;
    private OtpRoot details;
    private Fragment fragment;
    String talentImage;
    private Bundle bundle;
    private LottieAnimationView animationView;
    private SwipeRefreshLayout refreshLayout;
    private MvvmViewModelClass mvvmViewModelClass;
    private LinearLayout setting_profile, llDiamonds, llTalentImage, llFollowers, llFriends, llFollowing, check_status_layout, check_status, check_agencyStatus, level_layout, top_up, other_option, coinHistory;
    private String levelImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg_gradient);
        mvvmViewModelClass = new MvvmViewModelClass();
        details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);


        CoinHistoryFragment.userId = details.getId();
        checkStatus();
        getCoin();
        getLevel();
        findId(view);
        OnClick(view);
        setData();
        getFollowUnFollowCount();

        refreshLayout.setRefreshing(true);


        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {

                details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
                refreshLayout.setRefreshing(true);
                checkStatus();
                getCoin();
                getLevel();
                setData();

            }
        });


        add_banner.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (details.getPoster_img() != null) {
                    if (details.getPoster_img().length() != 0 && details.getPoster_img() != null) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.show_host_status);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.setCanceledOnTouchOutside(true);
                        Window window = dialog.getWindow();
                        window.setGravity(Gravity.CENTER);
                        dialog.show();

                        ImageView status_image = dialog.findViewById(R.id.status_image);
                        TextView about_status = dialog.findViewById(R.id.about_status);

                        status_image.setImageResource(R.drawable.checked);
                        about_status.setText("Your request is proved");

                    }
                } else {
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.select_image_dailog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.setCanceledOnTouchOutside(true);
                    Window window = dialog.getWindow();
                    window.setGravity(Gravity.CENTER);
                    dialog.show();

                    ImageView gallery = dialog.findViewById(R.id.gallery);

                    gallery.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            int permission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                            if (permission != PackageManager.PERMISSION_GRANTED) {
                                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                            } else {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent, 2);
                            }

                        }
                    });
                }
            }
        });

        level_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.level_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);
                dialog.show();

                ImageView level_image = dialog.findViewById(R.id.level_image);

                Glide.with(requireContext()).load(levelImage).into(level_image);


                Log.d(TAG, "onClick: " + Integer.parseInt(details.getMy_level()));


            }
        });


    }

    private void getFollowUnFollowCount() {

        new MvvmViewModelClass().getAllCounts(CommonUtils.getUserId()).observe(requireActivity(), new Observer<FolowCountStatus>() {

            @Override
            public void onChanged(FolowCountStatus folowCountStatus) {

                if (folowCountStatus.getStatus().equalsIgnoreCase("1")) {

                    follower.setText(folowCountStatus.getDetails().getFollowersCount().toString());
                    following.setText(folowCountStatus.getDetails().getFollowingCount().toString());
                    friends.setText(folowCountStatus.getDetails().getFriendsCount().toString());

                }
            }
        });

    }


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

        mvvmViewModelClass.myTalentLevelRootLiveData(details.getId()).observe(requireActivity(), myTalentLevelRoot -> {

//                if (myTalentLevelRoot != null) {
//
////                    likes.setText("Tal."+myTalentLevelRoot.getDetails().getTalent_level());
////
////                     talentImage = myTalentLevelRoot.getDetails().getImage();
//
//                    Glide.with(talent_image.getContext()).load(talentImage).into(talent_image);


//                }
        });

    }

    private void getCoin() {

        mvvmViewModelClass.getCoinRootLiveData(details.getId()).observe(requireActivity(), new Observer<TotalCoinModal>() {

            @Override
            public void onChanged(TotalCoinModal getCoinRoot) {

                if (getCoinRoot != null) {

                    if (getCoinRoot.getSuccess().equalsIgnoreCase("1")) {

                        if (getCoinRoot.getDetails() == null) {
                            expCoin.setText("0");
                            tx58.setText("0");
                            rlDiamonds.setText("0");
                        } else {
                            expCoin.setText(getCoinRoot.getDetails().getPurchasedCoin());
                            tx58.setText(getCoinRoot.getDetails().getPurchasedCoin());
                            rlDiamonds.setText(getCoinRoot.getDetails().getMyCoins());
//                            expCoin.setText(CommonUtils.prettyCount(Long.parseLong(getCoinRoot.getDetails().getPurchasedCoin())));
//                            tx58.setText(CommonUtils.prettyCount(Long.parseLong(getCoinRoot.getDetails().getPurchasedCoin())));
//                            rlDiamonds.setText(CommonUtils.prettyCount(Long.parseLong(getCoinRoot.getDetails().getMyCoins())));

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
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            assert data != null;
            Uri imageUriPhotos = data.getData();
            postImage(imageUriPhotos.getPath());

//            stringPhotoPath = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos);


//            circular_image.setImageURI(imageUriPhotos);

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void checkStatus() {

        Log.d(TAG, "checkStatus: " + CommonUtils.getUserId());

        mvvmViewModelClass. checkStatusRootLiveData(CommonUtils.getUserId()).observe(requireActivity(), checkStatusRoot -> {

            if (checkStatusRoot.getStatus().equals("1")) {


                OtpRoot details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

                details.setHost_status(checkStatusRoot.getHost_status());

                if (checkStatusRoot.getHost_status().equals("2")) {
                    approveImage.setVisibility(View.VISIBLE);
                } else {
                    approveImage.setVisibility(View.GONE);
                }


                App.getSharedpref().saveModel(AppConstant.USER_INFO, details);

                details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

                Log.d(TAG, "onChanged: " + details.getHost_status());

//                    showStatus();
            } else {
                Toast.makeText(requireContext(), "" + checkStatusRoot.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        check_status.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//            }
//        });

    }

    private void showStatus() {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.show_host_status);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.show();
        ImageView status_image = dialog.findViewById(R.id.status_image);
        TextView about_status = dialog.findViewById(R.id.about_status);

        if (details.getHost_status().equals("1")) {
            status_image.setImageResource(R.drawable.expired);
            about_status.setText("Your request is pending");
        }
        if (details.getHost_status().equals("2")) {
            status_image.setImageResource(R.drawable.checked);
            about_status.setText("Your request is proved");
        }

    }

    private void setData() {

        refreshLayout.setRefreshing(false);


        Glide.with(requireContext()).load(details.getUserLevelImage()).into(level_image_user);
        if (details != null) {
            user_id_profile.setText(details.getUsername().substring(1));

            userCountry.setText(details.getCountry());

            Glide.with(requireContext()).load(details.getUserLevelImage()).into(level_image_user);


//            if (details.getAgency_status().equals("0")||details.getAgency_status().equals("1")){
//                check_agencyStatus.setVisibility(View.VISIBLE);
//            }else{
//                check_agencyStatus.setVisibility(View.GONE);
//
//            }
            emmaTV.setText(details.getName());
            if (details.getImage() != null && details.getImage().length() != 0) {
                Picasso.with(requireActivity()).load(details.getImage()).error(R.drawable.user_7).into(user_img_profile);
                Log.d("TAG", "onViewCreated: " + details.getImage());
            }
            if (details.getHost_status().equals("0")) {

                check_status_layout.setVisibility(View.GONE);

            }
            if (details.getHost_status().equals("1")) {

                user_status.setText("Pending");

                Log.d(TAG, "setData: " + details.getId());

            }
            if (details.getHost_status().equals("2")) {

                user_status.setText("Proved");
                approveImage.setVisibility(View.VISIBLE);
            }
            if (details.getMy_level() != null) {
                level.setText("Lv. " + " " + details.getMy_level());
            }
            if (details.getCoin() != null) {
                expCoin.setText(details.getPurchasedCoin());
            }
            if (details.getCoin() != null) {
                rlDiamonds.setText(details.getCoin());
            }
            if (details.getWallet() != null) {

            }
            if (details.getTalent_level() != null) {

                if (Integer.parseInt(details.getMy_level()) >= 0 && Integer.parseInt(details.getMy_level()) <= 6) {

                    talent_image.setImageResource(R.drawable.diamond_level);

                }
                if (Integer.parseInt(details.getMy_level()) >= 6 && Integer.parseInt(details.getMy_level()) <= 14) {

                    talent_image.setImageResource(R.drawable.my_level_crown);

                }
                if (Integer.parseInt(details.getMy_level()) >= 14 && Integer.parseInt(details.getMy_level()) <= 20) {

                    talent_image.setImageResource(R.drawable.level_crown_two);

                }

                likes.setText(details.getTalent_level());
            }


        } else {
            emmaTV.setText("username");
        }

        user_id_profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setText(user_id_profile.getText());

                Toast.makeText(requireContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onResume() {

        super.onResume();
        mvvmViewModelClass = new MvvmViewModelClass();
        getFollowUnFollowCount();
        details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
        setData();

        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.VISIBLE);

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
        llDiamonds = view.findViewById(R.id.llDiamonds);
        approveImage = view.findViewById(R.id.approveImage);
        check_status_layout = view.findViewById(R.id.check_status_layout);
        user_status = view.findViewById(R.id.user_status);
        check_status = view.findViewById(R.id.check_status);
        following = view.findViewById(R.id.following);
        follower = view.findViewById(R.id.follower);
//        check_agencyStatus = view.findViewById(R.id.check_agencyStatus);
        userCountry = view.findViewById(R.id.userCountry);
        level = view.findViewById(R.id.level);
        expCoin = view.findViewById(R.id.expCoin);
        tx58 = view.findViewById(R.id.tx58);
        likes = view.findViewById(R.id.likes);
        add_banner = view.findViewById(R.id.add_banner);
        level_layout = view.findViewById(R.id.level_layout);
        top_up = view.findViewById(R.id.top_up);
//        other_option = view.findViewById(R.id.other_option);
        coinHistory = view.findViewById(R.id.coinHistory);
        talent_image = view.findViewById(R.id.talent_image);


    }

    private void OnClick(View view) {


        llDiamonds.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new WalletFragments()).addToBackStack(null).commit();
        });


        llFollowers.setOnClickListener(view1 -> {

            bundle = new Bundle();
            bundle.putString("checkStatus", "3");

            fragment = new AllfollowFriendsFragment();

            fragment.setArguments(bundle);


            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).addToBackStack(null).commit();


        });


        llTalentImage.setOnClickListener(view1 -> {


            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.level_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog.show();


            ImageView back = dialog.findViewById(R.id.back);
            ImageView level_image = dialog.findViewById(R.id.level_image);

            Glide.with(requireContext()).load(talentImage).into(level_image);


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


        coinHistory.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new CoinHistoryFragment()).addToBackStack(null).commit();


            }
        });

        top_up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(requireContext(), "Coming Soon.....", Toast.LENGTH_SHORT).show();


            }
        });

        setting_profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(requireContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.log_out_dialog);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                dialog.findViewById(R.id.yesText).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        new MvvmViewModelClass().logOutClassLiveData(requireActivity(), details.getId()).observe(requireActivity(), new Observer<LogOutClass>() {

                            @Override
                            public void onChanged(LogOutClass logOutClass) {

                                App.getSharedpref().clearPreferences();
                                dialog.dismiss();
                                startActivity(new Intent(requireContext(), MainActivity.class));
                            }
                        });
                    }
                });
                dialog.findViewById(R.id.noText).setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                    }
                });

            }
        });

        view.findViewById(R.id.apply_for_host).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (details.getHost_status().equals("1") || details.getHost_status().equals("2") || details.getHost_status().equals("3")) {

                    Toast.makeText(requireContext(), "All ready host", Toast.LENGTH_SHORT).show();
                } else {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ApplyForHost()).addToBackStack(null).commit();
                }
            }
        });

        view.findViewById(R.id.ranking_family).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new LeadtherboardFragment()).addToBackStack(null).commit();
            }
        });


        view.findViewById(R.id.EditProfileImg).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new EditProfileFragment()).addToBackStack(null).commit();
            }
        });
        view.findViewById(R.id.wallet).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(requireContext(), "coming soon...", Toast.LENGTH_SHORT).show();
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new WalletFragments()).addToBackStack(null).commit();
            }
        });
        view.findViewById(R.id.redeem_cash).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(requireContext(), "coming soon...", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.find_friends).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new FindFriends()).addToBackStack(null).commit();
            }
        });

        view.findViewById(R.id.my_subscription).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new SubscribedTab()).addToBackStack(null).commit();
            }
        });

        view.findViewById(R.id.myLiveStream).setOnClickListener(view1 -> {

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new LiveHistoryFragment()).addToBackStack(null).commit();
        });

        view.findViewById(R.id.profileTopGiftersRL).setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MainLeaderboardFragment()).addToBackStack(null).commit();
        });


        view.findViewById(R.id.backImgMain).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(requireContext(), HomeMainActivity.class));
            }
        });
        view.findViewById(R.id.talent_level).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new LevelsScreenFragment()).addToBackStack(null).commit();

            }
        });

        view.findViewById(R.id.my_level).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MyLevelFragment()).addToBackStack(null).commit();


            }
        });

        view.findViewById(R.id.apply_for_agency).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: apply_for_agency");

                if (details.getAgency_status() != null) {
                    if (details.getAgency_status().equals("0")) {
                        Toast.makeText(requireContext(), "request is pending", Toast.LENGTH_SHORT).show();
                    } else if (details.getAgency_status().equals("1")) {
                        Toast.makeText(requireContext(), "request is Proved", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ApplyForAgencyFragment()).addToBackStack(null).commit();

                }
            }

        });

//        check_agencyStatus.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//
//                checkAgencyStatus();
//            }
//        });
//
//        other_option.setOnClickListener(view12 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new OtherOptionFragment()).addToBackStack(null).commit());
    }

    private void checkAgencyStatus() {

        mvvmViewModelClass.agencyStatusRootLiveData(details.getUsername()).observe(requireActivity(), new Observer<CheckStatusRoot>() {

            @Override
            public void onChanged(CheckStatusRoot checkStatusRoot) {

                if (checkStatusRoot != null) {

                    OtpRoot otpRoot = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

                    otpRoot.setAgency_status(checkStatusRoot.getStatus());

                    App.getSharedpref().saveModel(AppConstant.USER_INFO, otpRoot);

                    Log.d(TAG, "c: " + otpRoot.getId());

                    showAgencyStatus(checkStatusRoot);

                }

            }
        });

    }

    private void showAgencyPanelLink(CheckStatusRoot checkStatusRoot) {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.panel_link_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.show();
        TextView panel_link = dialog.findViewById(R.id.panel_link);
        TextView user_name = dialog.findViewById(R.id.user_name);
        TextView password = dialog.findViewById(R.id.password);

        panel_link.setText(checkStatusRoot.getPanelLink());
        user_name.setText(checkStatusRoot.getAgencyCode());
        password.setText(checkStatusRoot.getPassword());

        panel_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(checkStatusRoot.getPanelLink()));
                startActivity(browserIntent);

            }
        });


    }

    private void showAgencyStatus(CheckStatusRoot checkStatusRoot) {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.show_host_status);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.show();
        ImageView status_image = dialog.findViewById(R.id.status_image);
        TextView about_status = dialog.findViewById(R.id.about_status);

        if (details.getAgency_status() != null) {

            if (details.getAgency_status().equals("0")) {
                status_image.setImageResource(R.drawable.expired);
                about_status.setText("Your request is pending");
            } else {
                showAgencyPanelLink(checkStatusRoot);
            }
        } else {
            status_image.setImageResource(R.drawable.expired);
            about_status.setText("Please request for agency");
        }
    }


}