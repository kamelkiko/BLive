package com.expert.blive.HomeActivity.HomeMainCategory.search;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.Adapter.BadgesAdapter;
import com.expert.blive.Agora.agoraLive.activity.OtherUserDataModel;
import com.expert.blive.ExtraFragments.ChatFragment;
import com.expert.blive.ExtraFragments.MessagesFragment;
import com.expert.blive.HomeActivity.HomeMainCategory.Subscription.category.ExoPlayerViewFragment;
import com.expert.blive.ModelClass.Badge;
import com.expert.blive.ModelClass.Video.VideoDetail2;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.MediaAdapter;
import com.expert.blive.ModelClass.FolowCountStatus;
import com.expert.blive.ModelClass.UserBlockDetail;
import com.expert.blive.ModelClass.Video.VideoRoot2;
import com.expert.blive.ModelClass.VisitRoot;
import com.expert.blive.ModelClass.searchUser.SearchUserDetail;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.CommonUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class OtherUserProfileFragment extends BottomSheetDialogFragment {

    public static SearchUserDetail searchUserDetail;
    private LinearLayout badgeLayout;
    private ImageView back, genderMale,genderFemale,approveImage;
    private CardView more;
    private ShapeableImageView profile_photo;
    private LinearLayout yes_btn, no_btn;
    private CircleImageView dp;
    private RecyclerView following_user_RV,badgeRecycler;
    private TextView user_name,reels_not_found,userName,level_text_my,user_ids_profile,bio,number_of_followers,number_of_following,coin_text;
    private RelativeLayout messageSame,message, rlMain,liveBTN,rlLoading;
    private MvvmViewModelClass mvvmViewModelClass;
    private String getOtherUserId;
    private ArrayList<UserBlockDetail> list;
    private NestedScrollView scrollView;
    private AppCompatButton follow_button;
    private int followerCount, afterApiFollower;

    public OtherUserProfileFragment(String getOtherUserId) {
        this.getOtherUserId = getOtherUserId;
    }

    public OtherUserProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mvvmViewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_other_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findIsd(view);
        hitApisomeFunctionality();
//        visitprofile();
        clicks();
        setData(searchUserDetail);
        getVideo(getOtherUserId);
        getCount();

    }

    private void hitApisomeFunctionality() {
        HashMap<String, String> data = new HashMap<>();

        data.put("userId", CommonUtils.getUserId());
        data.put("otherUserId", CommonUtils.getUserId());

        mvvmViewModelClass.someFunctionality(requireActivity(),data).observe(requireActivity(), response -> {
            if (response !=null){
                if (response.getStatus().equalsIgnoreCase("1")){
                    visitprofile(response.getDetails());
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void getCount() {
        mvvmViewModelClass.getAllCounts(searchUserDetail.getId()).observe(requireActivity(), folowCountStatus -> {
            if(folowCountStatus !=null) {
                if (folowCountStatus.getStatus().equalsIgnoreCase("1")) {
                    number_of_followers.setText(folowCountStatus.getDetails().getFollowersCount().toString());
                    number_of_following.setText(folowCountStatus.getDetails().getFollowingCount().toString());
                }else {}
            }else {
                Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void visitprofile(OtherUserDataModel.Details details) {
        String otherUserId = searchUserDetail.getId();
        if (details.getInvisibleVisitor().equalsIgnoreCase("0")){
            mvvmViewModelClass.setVistors(otherUserId,CommonUtils.getUserId()).observe(requireActivity(), visitRoot -> {
                if (visitRoot !=null){
                    if (visitRoot.getStatus()==1){
                    }
                }
            });
        }else {}
    }

    @Override
    public void onResume() {

        super.onResume();
        mvvmViewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        getBlockedUser();
        if (searchUserDetail != null) {
            getVideo(searchUserDetail.getId());
        } else {
            getVideo(getOtherUserId);
        }

    }

    private void getBlockedUser() {

        mvvmViewModelClass.getBlockedUsersLiveData(requireActivity(), CommonUtils.getUserId()).observe(requireActivity(), response -> {
            if (response != null) {
                if (response.getSuccess().equals("1")) {
                    list = response.getDetails();
                    checkData(response.getDetails());
                } else {
                    Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void checkData(ArrayList<UserBlockDetail> details) {


        for (int i = 0; i < details.size(); i++) {

            if (searchUserDetail.getId().equals(details.get(i).getBlockUserId())) {
                rlMain.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
            }
        }

    }

    private void hitApi() {

        mvvmViewModelClass.userBlockLiveData(requireActivity(), CommonUtils.getUserId(), searchUserDetail.getId()).observe(requireActivity(), response -> {
            if (response != null) {
                if (response.getSuccess().equals("1")) {
                    Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new SearchUserFragment()).addToBackStack(null).commit();
                } else {
                    Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            setupRatio(bottomSheetDialog);
        });
        return dialog;
    }

    private void setupRatio(BottomSheetDialog bottomSheetDialog) {

        FrameLayout bottomSheet = bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        assert bottomSheet != null;
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = displaySize(requireActivity())[1] * 100 / 100;
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private static int[] displaySize(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        com.google.android.exoplayer2.util.Log.i("height", String.valueOf(height));
        com.google.android.exoplayer2.util.Log.i("width", String.valueOf(width));
        return new int[]{width, height};
    }

    @SuppressLint("SetTextI18n")
    private void followOrUnfollow() {


        mvvmViewModelClass.followingRootLiveData(CommonUtils.getUserId(), searchUserDetail.getId()).observe(requireActivity(), followingRoot -> {
            if (followingRoot != null) {
                if (followingRoot.isFollowing_status()) {
                    follow_button.setText("Following");
                    afterApiFollower = followerCount + 1;
                    number_of_followers.setText(String.valueOf(afterApiFollower));
                } else {
                    follow_button.setText("Follow");
                    if (followerCount == 0) {
                        number_of_followers.setText(String.valueOf(followerCount));
                    } else {
                        afterApiFollower = followerCount - 1;
                        number_of_followers.setText(String.valueOf(afterApiFollower));
                    }
                }
            }
        });
    }

    private void clicks() {

        liveBTN.setOnClickListener(view -> Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show());

        more.setOnClickListener(view -> {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.more_dailog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            dialog.show();

            LinearLayout yes_btn = dialog.findViewById(R.id.yes_btn);
            LinearLayout no_btn = dialog.findViewById(R.id.no_btn);

            no_btn.setOnClickListener(view1 -> dialog.dismiss());
            yes_btn.setOnClickListener(view1 -> blockUser(dialog));
        });

        yes_btn.setOnClickListener(view -> hitApi());
        no_btn.setOnClickListener(view -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new SearchUserFragment()).addToBackStack(null).commit());

        messageSame.setOnClickListener(view -> {
            if (searchUserDetail != null) {
                ChatFragment.searchUserDetail = searchUserDetail;
                ChatFragment.other_userId = searchUserDetail.getId();
                MessagesFragment.searchUserDetail = searchUserDetail;
            } else {
                ChatFragment.other_userId = getOtherUserId;
            }
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ChatFragment()).addToBackStack(null).commit();
        });
        follow_button.setOnClickListener(view -> followOrUnfollow());
        back.setOnClickListener(view -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new SearchUserFragment()).addToBackStack(null).commit());
    }

    private void blockUser(Dialog dialog) {
        mvvmViewModelClass.userBlockLiveData(requireActivity(), CommonUtils.getUserId(), searchUserDetail.getId()).observe(requireActivity(), response -> {
            if (response != null) {
                Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new SearchUserFragment()).addToBackStack(null).commit();
                dialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Soemthing went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setData(SearchUserDetail details) {

        Glide.with(requireContext()).load(details.getImage()).error(R.drawable.app_logo).into(profile_photo);
        Glide.with(requireContext()).load(details.getImage()).error(R.drawable.app_logo).into(dp);
//        Glide.with(requireActivity()).load(details.getOfficialTag().getImage()).into(approveImage);
        user_ids_profile.setText(details.getUsername());
        if (details.getFollowStatus() == null) {
            Log.d(TAG, "setData: ");
        } else {
            if (details.getFollowStatus().equalsIgnoreCase("1")) {

                follow_button.setText("Following");
                followerCount = Integer.parseInt(details.getFollowerCount());

            } else {
                follow_button.setText("Follow");
            }
        }
        if (details.getGender().equalsIgnoreCase("male")) {
            genderMale.setVisibility(View.VISIBLE);
            genderFemale.setVisibility(View.GONE);
        } else {
            genderMale.setVisibility(View.GONE);
            genderFemale.setVisibility(View.VISIBLE);
        }
        if (details.getBadge() != null) {
            badgeLayout.setVisibility(View.VISIBLE);
            setAdapter(details.getBadge());
        } else {
            badgeLayout.setVisibility(View.GONE);
        }

        if (details.getOfficial_tag() !=null){
            Glide.with(requireActivity()).load(details.getOfficial_tag().getImage()).into(approveImage);

        }else {
            approveImage.setVisibility(View.GONE);
        }

        user_name.setText(details.getName());
        userName.setText(details.getName());

        bio.setText(details.getBio());
        coin_text.setText(details.getCoin());

    }

    private void setAdapter(List<Badge> badge) {

        BadgesAdapter badgesAdapter = new BadgesAdapter(badge, requireContext(), this::openDailog);
        badgeRecycler.setAdapter(badgesAdapter);

    }

    private void openDailog(Badge detail) {

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

    private void getVideo(String getOtherUserId) {

        String otherUserId = searchUserDetail.getId();

        mvvmViewModelClass.getVideoRootLiveData(otherUserId).observe(requireActivity(), videoRoot2 -> {
            if (videoRoot2 !=null){
                if (videoRoot2.getSuccess()==1){
                    Log.d(TAG, "onChanged: "+videoRoot2.getDetails().size());
                    MediaAdapter mediaAdapter = new MediaAdapter(new MediaAdapter.Callback() {
                        @Override
                        public void call_video(int position) {

                            Bundle bundle = new Bundle();
                            bundle.putSerializable("key",videoRoot2.getDetails().get(position));
//                            App.getSharedpref().saveModel(AppConstant.USER_INFO, videoRoot.getDetails().get(position));
                            ExoPlayerViewFragment exoPlayerViewFragment = new ExoPlayerViewFragment();
                            exoPlayerViewFragment.setArguments(bundle);
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, exoPlayerViewFragment).addToBackStack(null).commit();
                        }
                        @Override
                        public void videoDelete(VideoDetail2 detail) {
                        }
                    }, requireContext(), videoRoot2.getDetails());
                    following_user_RV.setAdapter(mediaAdapter);
                }else {
                    reels_not_found.setVisibility(View.VISIBLE);
                }
            }else {
                Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void findIsd(View view) {

        profile_photo = view.findViewById(R.id.profile_photo);
        user_ids_profile = view.findViewById(R.id.user_ids_profile);
        level_text_my = view.findViewById(R.id.level_text_my);
        bio = view.findViewById(R.id.bio);
        follow_button = view.findViewById(R.id.follow_button);
        user_name = view.findViewById(R.id.user_name);
        userName = view.findViewById(R.id.userName);
        genderMale = view.findViewById(R.id.genderMale);
        genderFemale = view.findViewById(R.id.genderFemale);
        messageSame = view.findViewById(R.id.messageSame);
        liveBTN = view.findViewById(R.id.liveBTN);
        rlLoading = view.findViewById(R.id.rlLoading);
        message = view.findViewById(R.id.message);
        yes_btn = view.findViewById(R.id.yes_btn);
        no_btn = view.findViewById(R.id.no_btn);
        reels_not_found = view.findViewById(R.id.reels_not_found);
        number_of_followers = view.findViewById(R.id.number_of_followers);
        number_of_following = view.findViewById(R.id.number_of_following);
        following_user_RV = view.findViewById(R.id.recycler);
        badgeRecycler = view.findViewById(R.id.badgeRecycler);
        dp = view.findViewById(R.id.dp);
        badgeLayout = view.findViewById(R.id.badgeLayout);
        back = view.findViewById(R.id.back_);
        more = view.findViewById(R.id.more);
        rlMain = view.findViewById(R.id.rlMain);
        scrollView = view.findViewById(R.id.scrollView);
        coin_text = view.findViewById(R.id.coin_text);
        approveImage = view.findViewById(R.id.approveImage);




        user_ids_profile.setText("ID: "+App.getSingleton().getUserId());
    }

}