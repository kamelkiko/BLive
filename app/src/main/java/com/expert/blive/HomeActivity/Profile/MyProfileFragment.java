package com.expert.blive.HomeActivity.Profile;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.expert.blive.Adapter.UserBadgeAdapter;
import com.expert.blive.Agora.agoraLive.activity.OtherUserDataModel;
import com.expert.blive.HomeActivity.HomeMainCategory.Subscription.category.ExoPlayerViewFragment;
import com.expert.blive.ModelClass.Store.RootFrames;
import com.expert.blive.ModelClass.Video.VideoDetail2;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.MediaAdapter;
import com.expert.blive.ModelClass.Video.VideoRoot2;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfileFragment extends Fragment  {

    private TextView reels_not_found;
    private SwipeRefreshLayout pullToRefresh;
    private RecyclerView recycler, badgeRecycler;
    private LinearLayout badgeLayout;
    private RelativeLayout rlLoading;
    private UserBadgeAdapter badgesAdapter;
    private NestedScrollView scrollView;
    List<OtherUserDataModel.Badge> list2;
    private MvvmViewModelClass viewModelClass;
    private ImageView coverIMG, genderMale, genderFemale, level_image_user_my, talent_image_my, approveImage;
    private CircleImageView userDp;
    private TextView userName, age, level_text_my, likes_my, user_ids_profile;
    String talentImage, stringPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_myprofile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);
        getDetails();
        click(view);



    }

    private void click(View view) {

        view.findViewById(R.id.back).setOnClickListener(view1 -> {
            requireActivity().onBackPressed();
        });

    }

    private void getDetails() {

        HashMap<String, String> data = new HashMap<>();
        data.put("userId", CommonUtils.getUserId());
        data.put("otherUserId", CommonUtils.getUserId());
        viewModelClass.someFunctionality(requireActivity(), data).observe(requireActivity(), new Observer<OtherUserDataModel>() {
            @Override
            public void onChanged(OtherUserDataModel response) {
                if (response != null) {
                    if (response.getStatus().equalsIgnoreCase("1")) {
                        setData(response.getDetails());
                    }
                } else {
                    Toast.makeText(requireContext(), "Technical issue.....", Toast.LENGTH_SHORT).show();
                }
             }

            private void setData(OtherUserDataModel.Details details) {

                Glide.with(requireContext()).load(details.getImage()).into(userDp);
                user_ids_profile.setText(details.getUsername());
                userName.setText(details.getName());
                if (details.getGender().equalsIgnoreCase("male")) {
                    genderMale.setVisibility(View.VISIBLE);
                    genderFemale.setVisibility(View.GONE);
                } else {
                    genderMale.setVisibility(View.GONE);
                    genderFemale.setVisibility(View.VISIBLE);
                }
                age.setVisibility(View.GONE);
        //                level.setText("Lv : " + details.getMyLevel());
        //                likes.setText("Lv : " + details.getTalentLevel());
                Glide.with(requireContext()).load(details.getImage()).into(coverIMG);
                Glide.with(requireContext()).load(details.getImage()).into(userDp);
                if (details.getOfficialTag() !=null){
                    Glide.with(requireActivity()).load(details.getOfficialTag().getImage()).into(approveImage);

                }else {
                    approveImage.setVisibility(View.GONE);
                }

                if (details.getBadge() != null) {
                    badgeLayout.setVisibility(View.VISIBLE);
                    setAdapter(details.getBadge());
                } else {
                    badgeLayout.setVisibility(View.GONE);
                }


            }

            private void setAdapter(List<OtherUserDataModel.Badge> badge) {
                badgesAdapter = new UserBadgeAdapter(badge, requireContext(), new UserBadgeAdapter.Callback() {

                    @Override
                    public void openBadge(OtherUserDataModel.Badge detail) {

                        openDailog(detail);
                    }


                });
                badgeRecycler.setAdapter(badgesAdapter);

            }
        });
    }



    private void openDailog(OtherUserDataModel.Badge detail) {

        Dialog dialog = new Dialog(requireContext());
        dialog.setContentView(R.layout.badges_dailog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        ImageView badges = dialog.findViewById(R.id.badges);
        TextView badgeTitle = dialog.findViewById(R.id.badgeTitle);

        Glide.with(requireContext()).load(detail.getImage()).into(badges);
        badgeTitle.setText(detail.getTitle());
        dialog.show();
    }

    private void getData() {

        viewModelClass.myVideos(CommonUtils.getUserId()).observe(requireActivity(), new Observer<VideoRoot2>() {

            @Override
            public void onChanged(VideoRoot2 videoRoot) {
                if (videoRoot.getSuccess()==1){
                    Log.d(TAG, "onChanged: "+videoRoot.getDetails().size());
                    MediaAdapter mediaAdapter = new MediaAdapter(new MediaAdapter.Callback() {
                        @Override
                        public void call_video(int position) {

                            Bundle bundle = new Bundle();

                            bundle.putSerializable("key",videoRoot.getDetails().get(position));

//                            App.getSharedpref().saveModel(AppConstant.USER_INFO, videoRoot.getDetails().get(position));

                            ExoPlayerViewFragment exoPlayerViewFragment = new ExoPlayerViewFragment();

                            exoPlayerViewFragment.setArguments(bundle);

                            Toast.makeText(requireContext(), "open", Toast.LENGTH_SHORT).show();
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, exoPlayerViewFragment).addToBackStack(null).commit();
                        }

                        @Override
                        public void videoDelete(VideoDetail2 detail) {
                            openVideoDailog(detail);
                        }
                    }, requireContext(), videoRoot.getDetails());
                    recycler.setAdapter(mediaAdapter);
                }else {
                    reels_not_found.setVisibility(View.VISIBLE);
                }
            }
        });

    }
    private void openVideoDailog(VideoDetail2 detail) {

        String videoId = detail.getId();

        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.video_delete_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.show();
        dialog.findViewById(R.id.yesText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewModelClass.deleteVideo(CommonUtils.getUserId(),videoId).observe(requireActivity(), new Observer<RootFrames>() {
                    @Override
                    public void onChanged(RootFrames response) {
                        if (response !=null){
                            if (response.getStatus()==1){
                                Toast.makeText(requireContext(), ""+response.getMessage(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                getData();
                            }else {
                                getData();
                                Toast.makeText(requireContext(), ""+response.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                        }
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
    private void finds(View view) {

        recycler=view.findViewById(R.id.recycler);
        pullToRefresh=view.findViewById(R.id.pullToRefresh);
        reels_not_found=view.findViewById(R.id.reels_not_found);
        user_ids_profile = view.findViewById(R.id.user_ids_profile);
        approveImage = view.findViewById(R.id.approveImage);
        badgeRecycler = view.findViewById(R.id.badgeRecycler);
        genderMale = view.findViewById(R.id.genderMale);
        genderFemale = view.findViewById(R.id.genderFemale);
        age = view.findViewById(R.id.age);
        userName = view.findViewById(R.id.userName);
        userDp = view.findViewById(R.id.userDp);
        coverIMG = view.findViewById(R.id.coverIMG);
        badgeLayout = view.findViewById(R.id.badgeLayout);
        likes_my = view.findViewById(R.id.likes_my);
        level_text_my = view.findViewById(R.id.level_text_my);
        level_image_user_my = view.findViewById(R.id.level_image_user_my);
        talent_image_my = view.findViewById(R.id.talent_image_my);
        rlLoading = view.findViewById(R.id.rlLoading);
        scrollView = view.findViewById(R.id.scrollView);


        user_ids_profile.setText("ID: "+App.getSingleton().getUserId());
    }

    @Override
    public void onResume() {
        super.onResume();
        getDetails();
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }

}



//    private void setData(OtherUserDataModel.Details details) {
//        Glide.with(requireContext()).load(details.getImage()).into(userDp);
//        user_ids_profile.setText(details.getUsername());
//        userName.setText(details.getName());
//        if (details.getGender().equalsIgnoreCase("male")) {
//            genderMale.setVisibility(View.VISIBLE);
//            genderFemale.setVisibility(View.GONE);
//        } else {
//            genderMale.setVisibility(View.GONE);
//            genderFemale.setVisibility(View.VISIBLE);
//        }
//        age.setVisibility(View.GONE);
////                level.setText("Lv : " + details.getMyLevel());
////                likes.setText("Lv : " + details.getTalentLevel());
//        Glide.with(requireContext()).load(details.getImage()).into(coverIMG);
//        Glide.with(requireContext()).load(details.getImage()).into(userDp);
//        if (details.getOfficialTag() !=null){
//            Glide.with(requireActivity()).load(details.getOfficialTag().getImage()).into(approveImage);
//
//        }else {
//            approveImage.setVisibility(View.GONE);
//        }
//
//        if (details.getBadge() != null) {
//            badgeLayout.setVisibility(View.VISIBLE);
//            setAdapter(details.getBadge());
//        } else {
//            badgeLayout.setVisibility(View.GONE);
//        }
//
//
//    }

//    private void setAdapter(List<OtherUserDataModel.Badge> badge) {
//
//        badgesAdapter = new BadgesAdapter(badge, requireContext(), new BadgesAdapter.Callback() {
//
//            @Override
//            public void openBadge(OtherUserDataModel.Badge detail) {
//
//                openDailog(detail);
//            }
//
//
//        });
//        badgeRecycler.setAdapter(badgesAdapter);
//
//    }
