package com.expert.blive.Agora.agoraLive.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.Adapter.AdpterAdminList;
import com.expert.blive.Agora.agoraLive.firebase.FirebaseHelper;
import com.expert.blive.Agora.agoraLive.firebase.models.GetBanUserpojo;
import com.expert.blive.HomeActivity.HomeMainCategory.search.OtherUserProfileFragment;
import com.expert.blive.ModelClass.FollowingRoot;
import com.expert.blive.ModelClass.ModleAdminClass;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.utils.CommonUtils;

import com.expert.blive.R;
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.opensource.svgaplayer.SVGAImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private View view;
    private ProgressBar progress_bar;
    private String otherUserId;
    String muteLive = "0";
    private MentionFriend mentionFriend;
    private SVGAImageView profieFrame;
    private long adminListCount;
    String ban, block, adminStatuscheck, liveUserName;
    private TextView mute, txtId, txtCountry;
    TextView kickOutUser, setAsAdmin, addBlockList;
    private TextView tv_username_top, tv_following, like, tv_level_Header, tv_badge_name, tv_coinBalanceProfile, tv_videos_count, tv_likes_other, tv_bio_other_user, tv_user_name, tv_followers_other;
    private CircleImageView civ_user_profile;
    private ImageView level_image_user, talent_image;
    private MaterialCardView chat_other_user;
    private List<ModleAdminClass> dataAdminList = new ArrayList<>();

    private Button btn_follow_user;
    private ImageView img_cancel, img_badges, textGender;
    private LinearLayout ll_main_after_shimmer, ll_badges;
    private boolean isLiveUserProfile = false;
    private String userName, name, getOtherUserId;
    private boolean isPlayerActivity = false;
    private boolean isLiveFollowFromPlayer = false;
    private String canBanUser, liveId;
    private MaterialCardView mentionOtherUser;
    androidx.appcompat.app.AlertDialog dailogbox;

    public UserDetailsFragment() {
        // Required empty public constructor
    }

    public UserDetailsFragment(String otherUserId, boolean isLiveUserProfile, boolean isPlayerActivity, boolean isLiveFollowFromPlayer, String canBanUser, String liveUserName, String liveId, MentionFriend mentionFriend) {
        this.otherUserId = otherUserId;
        this.isLiveUserProfile = isLiveUserProfile;
        this.liveId = liveId;
        this.isPlayerActivity = isPlayerActivity;
        this.liveUserName = liveUserName;
        this.canBanUser = canBanUser;
        this.isLiveFollowFromPlayer = isLiveFollowFromPlayer;
        this.mentionFriend = mentionFriend;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_user_details, container, false);
        findIds();
        getData();
        FirebaseHelper.getMuteList(liveUserName, otherUserId, getMutevalueEventListener);

        FirebaseHelper.getBanDataLive(liveUserName, otherUserId, valueEventListener);
        FirebaseHelper.getBanlock(liveUserName, otherUserId, valueEventListenerBlock);
//        FirebaseHelper.getAdmin(liveUserName, otherUserId, valueEventListenerAdmin);


        if (isPlayerActivity) {


            Toast.makeText(requireContext(), "" + canBanUser, Toast.LENGTH_LONG);
            if (canBanUser == null || canBanUser.equalsIgnoreCase("") || CommonUtils.getUserId().equalsIgnoreCase(otherUserId)) {
                tv_username_top.setVisibility(View.GONE);

            } else {
                tv_username_top.setVisibility(View.VISIBLE);

            }


        } else {


            if (isLiveUserProfile && CommonUtils.getUserId().equalsIgnoreCase(otherUserId)) {
                tv_username_top.setVisibility(View.GONE);
            } else if (isLiveUserProfile && !CommonUtils.getUserId().equalsIgnoreCase(otherUserId)) {
                tv_username_top.setVisibility(View.VISIBLE);
            }
            FirebaseHelper.getAdminList(liveUserName, valueEventListenerAdminList);
        }

        if (CommonUtils.getUserId().equalsIgnoreCase(otherUserId)) {

            mentionOtherUser.setVisibility(View.GONE);
        }


//        if (!otherUserId.equals(CommonUtils.getUserId())&&isLiveUserProfile) {
//
//            tv_username_top.setVisibility(View.VISIBLE);
//        } else {
//            FirebaseHelper.getAdmin(liveUserName, CommonUtils.getUserId(), valueEventListenerAdminTwo);
//
//        }


//


        clicks();
        return view;
    }

    private void clicks() {

        mentionOtherUser.setOnClickListener(view1 -> {
            if (name.equalsIgnoreCase("")) {
                mentionFriend.mentionName(userName);
            } else {
                mentionFriend.mentionName(name);
            }


            dismiss();

        });


        civ_user_profile.setOnClickListener(view1 -> {

            OtherUserProfileFragment otherUserProfileFragment = new OtherUserProfileFragment(getOtherUserId);

            otherUserProfileFragment.show(requireActivity().getSupportFragmentManager(), otherUserProfileFragment.getTag());


        });

        tv_username_top.setOnClickListener(view1 -> {


            showExitDialog(adminStatuscheck);

        });
        btn_follow_user.setOnClickListener(view1 -> {


            new MvvmViewModelClass().followingRootLiveData(CommonUtils.getUserId(), otherUserId).observe(requireActivity(), new Observer<FollowingRoot>() {
                @Override
                public void onChanged(FollowingRoot followingRoot) {
                    if (followingRoot.getSuccess().equals("1")) {
                        if (followingRoot.isFollowing_status()) {
                            btn_follow_user.setText("following");
                            tv_followers_other.setText(followingRoot.getFollowing_count());
                        } else {
                            btn_follow_user.setText("follow");
                            tv_followers_other.setText(followingRoot.getFollowing_count());

                        }
                    }
                }
            });

        });

    }

    private void getData() {


        HashMap<String, String> data = new HashMap<>();
        data.put("userId", CommonUtils.getUserId());
        if (otherUserId !=null){
            data.put("otherUserId", otherUserId);
        }else {
            data.put("otherUserId", CommonUtils.getUserId());

        }

        new MvvmViewModelClass().someFunctionality(requireActivity(), data).observe(requireActivity(), new Observer<OtherUserDataModel>() {
            @Override
            public void onChanged(OtherUserDataModel spinOneModal) {
                if (spinOneModal.getStatus().equals("1")) {
                    setData(spinOneModal.getDetails());
                }
            }
        });
    }

    private void setData(OtherUserDataModel.Details details) {

        userName = details.getUsername();
        name = details.getName();
        getOtherUserId = details.getId();

        if (details.getBio().equals("")) {
            tv_bio_other_user.setText("Add Bio");
        } else {
            tv_bio_other_user.setText(details.getBio());
        }
        CommonUtils.setAnimation(requireContext(),details.getMyFrame(),profieFrame);
        Glide.with(civ_user_profile.getContext()).load(details.getImage()).placeholder(R.drawable.app_logo).into(civ_user_profile);
        tv_user_name.setText(details.getName());
        txtId.setText("Id : " + details.getUsername());
        txtCountry.setText(details.getCountry());

        like.setText(details.getTalentLevel());

//        textGender.setText(details.getGender());
        if (details.getGender().equalsIgnoreCase("male")) {
            textGender.setImageResource(R.drawable.baseline_female_24);
        } else {
            textGender.setImageResource(R.drawable.baseline_male_24);

        }

        tv_level_Header.setText("Lv." + details.getLeval());

        Glide.with(requireContext()).load(details.getUserLevelImage()).placeholder(R.drawable.diamond).into(level_image_user);
        Glide.with(requireContext()).load(details.getUserTalentLevelImage()).placeholder(R.drawable.diamond).into(talent_image);


        tv_followers_other.setText(details.getFollowerCount());
        tv_following.setText(details.getFollowingUser());
        tv_likes_other.setText(details.getLikeVideo());
        tv_videos_count.setText(details.getLikeVideo());
        if (details.getId().equalsIgnoreCase(CommonUtils.getUserId())) {
            btn_follow_user.setVisibility(View.GONE);
            chat_other_user.setVisibility(View.GONE);

        } else {
            if (isLiveUserProfile) {

                btn_follow_user.setVisibility(View.VISIBLE);
                chat_other_user.setVisibility(View.VISIBLE);
            } else {

                btn_follow_user.setVisibility(View.VISIBLE);


                chat_other_user.setVisibility(View.VISIBLE);

            }

            if (details.getFollowStatus()) {
                btn_follow_user.setText("Following");
            } else {
                btn_follow_user.setText("Follow");
            }
        }

    }


    private void findIds() {
        tv_following = view.findViewById(R.id.tv_following);
        txtId = view.findViewById(R.id.txtId);
        txtCountry = view.findViewById(R.id.txtCountry);
        profieFrame = view.findViewById(R.id.profieFrame);

        mentionOtherUser = view.findViewById(R.id.mentionOtherUser);
        textGender = view.findViewById(R.id.textGender);
        tv_level_Header = view.findViewById(R.id.level);
        img_badges = view.findViewById(R.id.img_badges);
        tv_badge_name = view.findViewById(R.id.tv_badge_name);
        ll_badges = view.findViewById(R.id.ll_badges);
        tv_username_top = view.findViewById(R.id.tv_username_top);
        progress_bar = view.findViewById(R.id.progress_bar);
        level_image_user = view.findViewById(R.id.level_image_user);
        like = view.findViewById(R.id.likes);
        talent_image = view.findViewById(R.id.talent_image);
        img_cancel = view.findViewById(R.id.img_cancel);
        ll_main_after_shimmer = view.findViewById(R.id.ll_main_after_shimmer);

        civ_user_profile = view.findViewById(R.id.civ_user_profile);

        chat_other_user = view.findViewById(R.id.chat_other_user);
        chat_other_user.setOnClickListener(v -> {


            GiftBottomSheetFragment giftBottomSheetFragment = new GiftBottomSheetFragment(otherUserId, liveUserName, "", liveId);
            giftBottomSheetFragment.show(requireActivity().getSupportFragmentManager(), giftBottomSheetFragment.getTag());

            dismiss();
//        startActivity(new Intent(requireActivity(),OtherUserProfileActivity.class)
//        .putExtra(AppConstants.OTHER_USER_ID,otherUserId)
//        .putExtra(AppConstants.OTHER_USER_NAME,tv_username_top.getText().toString()));
        });
        img_cancel.setOnClickListener(v -> dismiss());


        btn_follow_user = view.findViewById(R.id.btn_follow_user);
//        btn_follow_user.setOnClickListener(v->followUnfollow());
        tv_videos_count = view.findViewById(R.id.tv_videos_count);
        tv_likes_other = view.findViewById(R.id.tv_likes_other);
        tv_bio_other_user = view.findViewById(R.id.tv_bio_other_user);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        tv_followers_other = view.findViewById(R.id.tv_followers_other);
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
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = displaySize(requireActivity())[1] * 55 / 100;
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private static int[] displaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Display) display).getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.i("height", String.valueOf(height));
        Log.i("width", String.valueOf(width));
        return new int[]{width, height};
    }


    private void banLiveUser(String otherUserId) {
        LiveMvvm liveMvvm = new LiveMvvm();
        liveMvvm.banLiveUser(requireActivity(), CommonUtils.getUserId(), otherUserId).observe(UserDetailsFragment.this, map -> {
            if (map.get("success").toString().equals("1")) {
                boolean status = (Boolean) map.get("status");
                if (status) {
//                    btn_ban_unban.setText("UnBlock Live");
                    blockProduct("1");
                } else {
//                    btn_ban_unban.setText("Block Live");
                }


            } else {
                Toast.makeText(requireActivity(), map.get("message").toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void blockProduct(String s) {

        HashMap hashMap = new HashMap();
        hashMap.put("userId", otherUserId);
        hashMap.put("blockStatus", s);


        FirebaseHelper.banLBlockStatus(liveUserName, otherUserId, hashMap,
                o -> {
                    Toast.makeText(requireActivity(), "User Block from live", Toast.LENGTH_SHORT).show();
                    addBlockList.setText(" UnBlock");
                },


                e -> Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void kickOut(String kickStatus, Dialog dialog) {
        HashMap hashMap = new HashMap();
        hashMap.put("userId", otherUserId);
        hashMap.put("kickStatus", kickStatus);


        FirebaseHelper.banLiveStatus(liveUserName, otherUserId, hashMap, true,
                o -> {

//
//                    Toast.makeText(requireContext(), "User Banned from live",
//                            Toast.LENGTH_SHORT).show();
                    kickOutUser.setText("Kick Remove");
                    dialog.dismiss();
                    dismiss();
                },


                e -> Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void mute(String muteStatus) {
        HashMap hashMap = new HashMap();
        hashMap.put("userId", otherUserId);
        hashMap.put("muteStatus", muteStatus);
//        hashMap.put("timeStamp", String.valueOf(new Date().getTime()));
        FirebaseHelper.setMuteList(liveUserName, otherUserId, hashMap, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                if (muteStatus.equals("1")) {
                    Toast.makeText(requireContext(), "User muted from live", Toast.LENGTH_SHORT).show();
                    mute.setText("UnMute");

                } else {
                    Toast.makeText(requireContext(), "User Unmuted from live", Toast.LENGTH_SHORT).show();
                    mute.setText("Mute");

                }
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.btn_ban_unban) {//
////               banLiveUser(otherUserId);
////            BanDialog();
//            showExitDialog(adminStatus);
//
//        }
    }


    private void BanDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(requireActivity());
        final View confirmdailog = layoutInflater.inflate(R.layout.dialog_ban, null);
        dailogbox = new androidx.appcompat.app.AlertDialog.Builder(requireActivity()).create();
//        dailogbox.setCancelable(false);
        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dailogbox.setView(confirmdailog);

        TextView textView = confirmdailog.findViewById(R.id.mute);


        Toast.makeText(requireContext(), "" + AgoraPkPlayerActivity.muteLive, Toast.LENGTH_SHORT).show();
        if (muteLive.equals("1")) {
            textView.setText("UnMute");
        } else {
            textView.setText("Mute");

        }

        textView.setOnClickListener(v -> {


//
            muteDialog(AgoraPkPlayerActivity.muteLive, dailogbox);
//                dailogbox.dismiss();

        });


        confirmdailog.findViewById(R.id.kickout24).setOnClickListener(v -> {
//            kickOut("0", dialog);
//            dailogbox.dismiss();

        });

        confirmdailog.findViewById(R.id.kickoutForever).setOnClickListener(v -> {
//            banLiveUser(otherUserId);
//            kickOut("1", dialog);
            dailogbox.dismiss();
        });
        dailogbox.show();
    }

    private void muteDialog(String muteLive, Dialog dialogs) {
        final AlertDialog.Builder al = new AlertDialog.Builder(requireActivity(), R.style.dialogStyle);
        if (muteLive.equals("1")) {
            al.setTitle("Are you sure?").setPositiveButton("Yes", (dialog, which) -> {
                mute("0");

                dialog.dismiss();
                dialogs.dismiss();
            }).setNegativeButton("No", (dialog, which) ->
                    {

                        dialog.dismiss();
                        dialogs.dismiss();
                    }
            ).setMessage("Are you sure you want to Unmute this user").show();

        } else {
            al.setTitle("Are you sure?").setPositiveButton("Yes", (dialog, which) -> {
                mute("1");
                dialog.dismiss();
                dialogs.dismiss();
            }).setNegativeButton("No", (dialog, which) -> dialog.dismiss()).setMessage("Are you sure you want to mute this user .").show();
        }


    }

    private void showExitDialog(String adminStatus) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_ban_layout);

        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        dialog.setCancelable(false);

        dialog.show();


        ImageView exit = dialog.findViewById(R.id.img_cancel);
        kickOutUser = dialog.findViewById(R.id.kickOutUser);
        mute = dialog.findViewById(R.id.mute);
        setAsAdmin = dialog.findViewById(R.id.setAsAdmin);
        addBlockList = dialog.findViewById(R.id.addBlockList);
        if (adminStatus == null) {

        } else {
            setAsAdmin.setVisibility(View.GONE);
            addBlockList.setVisibility(View.GONE);
        }

        if (muteLive.equals("1")) {
            mute.setText("UnMute");
        } else {
            mute.setText("Mute");

        }

        if (ban == null) {
            kickOutUser.setText("Kick Out User");
        } else {
            kickOutUser.setText("Kick Remove");

        }
        if (canBanUser == null) {
            setAsAdmin.setText("set As Admin");
        } else {
            setAsAdmin.setText("Remove Admin");

        }
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                dismiss();

            }
        });
        kickOutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ban == null) {
                    kickOut("0", dialog);

                } else {
                    FirebaseHelper.removeBanUser(liveUserName, otherUserId, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            ban = null;
                            kickOutUser.setText("Kick Out User");
                            dialog.dismiss();
                            dismiss();

                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }


            }
        });
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                muteDialog(muteLive, dialog);
            }
        });
        addBlockList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (block == null) {
                    banLiveUser(otherUserId);

                } else {
                    FirebaseHelper.removeBlock(liveUserName, otherUserId, new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            block = null;
                            addBlockList.setText("Block User");

                        }
                    }, new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }

            }
        });
        setAsAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adminListCount >= 5) {
                    Toast.makeText(requireContext(), "Max 5 admin", Toast.LENGTH_SHORT).show();

                    OpenDialog();

                } else {
                    if (canBanUser == null) {
                        setAs();
                    } else {
                        FirebaseHelper.removeAdmin(liveUserName, otherUserId);
                        setAsAdmin.setText("set As Admin");
                    }
                }


                dialog.dismiss();
                dismiss();
            }
        });

    }

    private void OpenDialog() {


        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.admin_list_bottom);

        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.white);
        dialog.setCancelable(false);
        dialog.show();
        RecyclerView recyclerView = dialog.findViewById(R.id.adminRecycler);
        ImageView imageView = dialog.findViewById(R.id.img_cancel);

        AdpterAdminList adpterAdminList = new AdpterAdminList(dataAdminList, new AdpterAdminList.RemoveAdmin() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void remove(ModleAdminClass id) {

                if (dataAdminList.contains(id)) {
                    dataAdminList.remove(id);
                    FirebaseHelper.removeAdmin(liveUserName, id.getUserId());
                } else {
                    Toast.makeText(requireContext(), "TryAgain", Toast.LENGTH_SHORT).show();
                }


            }
        });
        recyclerView.setAdapter(adpterAdminList);

        imageView.setOnClickListener(view1 -> {
            dialog.dismiss();
        });


    }

    private void setAs() {


        HashMap<String, String> data = new HashMap<>();

        data.put("userId", otherUserId);
        data.put("subAdminStatus", "1");
        data.put("username", userName);


        FirebaseHelper.setSubAdmin(liveUserName, otherUserId, data, new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(requireContext(), "You Are Admin", Toast.LENGTH_SHORT).show();

            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    ValueEventListener getMutevalueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()) {


                muteLive = (String) snapshot.getValue();


            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists()) {

//                String userId = String.valueOf(snapshot.getKey());
//                String status = String.valueOf(snapshot.getValue());
                android.util.Log.i("Agora Live Ban userId: ", snapshot.toString());
//                Log.i("Agora Live Ban userId status: ", status);


                GetBanUserpojo getBanUserpojo = snapshot.getValue(GetBanUserpojo.class);

                if (Objects.requireNonNull(getBanUserpojo).getUserId().equalsIgnoreCase(otherUserId)) {


//                        onBackPressed();
                    if (getBanUserpojo.getKickStatus().equals("0")) {

                        ban = "0";


                    }
                }
            } else {

                ban = null;
            }


//
        }


        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    ValueEventListener valueEventListenerAdminList = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists()) {


                adminListCount = snapshot.getChildrenCount();

//                String userId = String.valueOf(snapshot.getKey());
//                String status = String.valueOf(snapshot.getValue());
                android.util.Log.i("Agora Live Ban userId: ", snapshot.toString());
//                Log.i("Agora Live Ban userId status: ", status);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModleAdminClass adminClass = dataSnapshot.getValue(ModleAdminClass.class);
                    dataAdminList.add(adminClass);
                }


//                Toast.makeText(requireContext(), ""+dataAdminList.size(), Toast.LENGTH_SHORT).show();

//                adminStatuscheck = snapshot.child("subAdminStatus").getValue(String.class);
//
//                Toast.makeText(requireContext(), "cc" + adminStatuscheck, Toast.LENGTH_SHORT).show();
//                if (adminStatuscheck.equals("1")){
//                    tv_username_top.setVisibility(View.VISIBLE);
//
//                }
//
//                if (adminStatuscheck==null){
//                    Toast.makeText(requireContext(), "sdsd", Toast.LENGTH_SHORT).show();
//
//
//                }else {
//                    Toast.makeText(requireContext(), "wefsd", Toast.LENGTH_SHORT).show();
//
//          }


//                                GetBanUserpojo getBanUserpojo = snapshot.getValue(GetBanUserpojo.class);
//                                Toast.makeText(requireContext(), ""+getBanUserpojo.getUserId(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(requireContext(), ""+otherUserId, Toast.LENGTH_SHORT).show();
//                                if (Objects.requireNonNull(getBanUserpojo).getUserId().equalsIgnoreCase(otherUserId)) {
//
//
////                        onBackPressed();
//                                        if (getBanUserpojo.getKickStatus().equals("0")){
//
//                                                ban="0";
//
//
//                                                }
//                                        }
//                                        }else {

            }


//
        }


        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private int getAge(String dobString) {

        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date == null) return 0;

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.setTime(date);

        int year = dob.get(Calendar.YEAR);
        int month = dob.get(Calendar.MONTH);
        int day = dob.get(Calendar.DAY_OF_MONTH);

        dob.set(year, month + 1, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }


        return age;
    }

    ValueEventListener valueEventListenerBlock = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

            if (snapshot.exists()) {

//                String userId = String.valueOf(snapshot.getKey());
//                String status = String.valueOf(snapshot.getValue());
                android.util.Log.i("Agora Live Ban userId: ", snapshot.toString());
//                Log.i("Agora Live Ban userId status: ", status);


                block = snapshot.child("blockStatus").getValue(String.class);


//status                GetBanUserpojo getBanUserpojo = snapshot.getValue(GetBanUserpojo.class);
//                Toast.makeText(requireContext(), "" + getBanUserpojo.getUserId(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(requireContext(), "" + otherUserId, Toast.LENGTH_SHORT).show();
//                if (Objects.requireNonNull(getBanUserpojo).getUserId().equalsIgnoreCase(otherUserId)) {
//
//
////                        onBackPressed();
//                    if (getBanUserpojo.getKickStatus().equals("1")) {
//
//                        block = "0";
//
//
//                    }
//                }
            } else {

                block = null;
            }


//
        }


        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    public interface MentionFriend {
        void mentionName(String name);
    }
}

