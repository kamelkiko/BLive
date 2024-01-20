package com.expert.blive.HomeActivity.HomeMainCategory.Live;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.Agora.agoraLive.activity.AgoraPkLiveActivity;
import com.expert.blive.ExtraFragments.ChooseSelectionFragment;
import com.expert.blive.HomeMainActivity;
import com.expert.blive.RealPathUtil;
import com.expert.blive.Adapter.GifAdapter;
import com.expert.blive.Agora.activity.CallActivity;
import com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity;
import com.expert.blive.Agora.agoraLive.models.ModelGetToken;
import com.expert.blive.Agora.agoraLive.ui.VideoGridContainer;
import com.expert.blive.Agora.openvcall.model.EngineConfig;
import com.expert.blive.ExtraFragments.ApplyForHost;
import com.expert.blive.HomeActivity.HomeMainCategory.Live.LiveCategory.PostLiveFragment;
import com.expert.blive.HomeActivity.Profile.EditProfileFragment;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.AppConstants;
import com.expert.blive.utils.CommonUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import io.agora.rtc.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class  LiveMainFragment extends Fragment {

    private MvvmViewModelClass viewModelClass;
    private String stringPhotoPath,posterIMG;
    private RelativeLayout audio_live, pk_live_new;
    private ImageView circular_image;
    private OtpRoot details;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference().child("userInfoBattle");
    String userName;
    private ProgressBar progressBar;
    private String maxValueOfJoiners = "1";
    private RecyclerView gif_recycler_view;
    private GifAdapter gifAdapter;
    private Uri video;
    private String video_path;
    androidx.appcompat.app.AlertDialog dailogbox, dialog;
    ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("LiveHistory");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_live_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        getStatus();
        details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
        findIds(view);
        getPosterImage(CommonUtils.getUserId());

        if (!App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getUsername().isEmpty() && App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getUsername() != null) {
            userName = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getUsername();
        } else {
            userName = CommonUtils.getUserId();
        }
        view.findViewById(R.id.circular_image).setOnClickListener(view1 -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 2);

        });
        view.findViewById(R.id.post_video).setOnClickListener(view12 -> {
            int permission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            } else {
                Intent intent;
                intent = new Intent(Intent.ACTION_PICK);
                intent.setType("video/*");
                startActivityForResult(intent, 2);
            }
        });

        view.findViewById(R.id.cancel_live).setOnClickListener(view13 -> startActivity(new Intent(requireContext(), HomeMainActivity.class)));

        view.findViewById(R.id.public_layout).setOnClickListener(view1 -> {

            if (posterIMG == null){
                Toast.makeText(requireContext(), "Please Select Poster", Toast.LENGTH_SHORT).show();
            }else {
                VideoGridContainer.MAX_USER = 0;
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ChooseSelectionFragment()).addToBackStack(null).commit();
            }
        });
        view.findViewById(R.id.single_live).setOnClickListener(view14 -> {
            if (details.getName().equals("")) {
                Toast.makeText(requireContext(), "Update Profile", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new EditProfileFragment()).addToBackStack(null).commit();
            }else if (posterIMG
                    == null){
                Toast.makeText(requireContext(), "Please Select Poster", Toast.LENGTH_SHORT).show();
            } else {

                VideoGridContainer.MAX_USER = 4;
                setChannelNameGetToken(userName, "1");
            }

//            if (details.getHost_status().equals("2")) {

////                startActivity(new Intent(requireContext(), HomeMainActivity.class));
//            }
//            if (details.getHost_status().equals("1")) {
//
//                showStatus();
//                Toast.makeText(requireContext(), "request is pending", Toast.LENGTH_SHORT).show();
//            }
//            if (details.getHost_status().equals("0")) {
//                Toast.makeText(requireContext(), "Apply for host", Toast.LENGTH_SHORT).show();
//            }

        });

        view.findViewById(R.id.audio_live).setOnClickListener(view1 -> {

//            if (details.getHost_status().equals("2")) {

            if (details.getName().equals("")) {
                Toast.makeText(requireContext(), "Update Profile", Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new EditProfileFragment()).addToBackStack(null).commit();
            } else {

                hitGenerateTokenApi(CommonUtils.getUserId(), "3");
            }
//            }
//            if (details.getHost_status().equals("1")) {
//
//                showStatus();
//                Toast.makeText(requireContext(), "request is pending", Toast.LENGTH_SHORT).show();
//            }
//            if (details.getHost_status().equals("0")) {
//                Toast.makeText(requireContext(), "Apply for host", Toast.LENGTH_SHORT).show();
//            }

        });

    }

    private void getStatus() {

        viewModelClass.checkStatusRootLiveData(CommonUtils.getUserId()).observe(requireActivity(), checkStatusRoot -> {
            if (checkStatusRoot.getStatus().equals("1")) {
                details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
                details.setHost_status(checkStatusRoot.getHost_status());
                App.getSharedpref().saveModel(AppConstant.USER_INFO, details);
                Log.d(TAG, "onChanged: " + details.getHost_status());
            }
        });

    }

    private void findIds(@NotNull View view) {

        circular_image = view.findViewById(R.id.circular_image);

    }

    private void getGifVideo() {


        viewModelClass.gifRootLiveData().observe(requireActivity(), gifRoot -> {
            if (gifRoot != null) {
                gifAdapter.loadDate(gifRoot.getDetails());
            }
        });

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

    }


    private void hitGenerateTokenApi(String userId, String type) {

        if (CommonUtils.isNetworkConnected(requireActivity())) {

            apiInterface.setAgoraChannel(userId, CommonUtils.getUserId(), "", "", type, 1, "1").enqueue(new Callback<ModelGetToken>() {
                @Override
                public void onResponse(@NotNull Call<ModelGetToken> call, @NotNull Response<ModelGetToken> response) {
                    try {
                        if (response.body() != null) {

                            if (response.body().getSuccess().equalsIgnoreCase("1")) {
                                Log.i("Agora:RtmToken", response.body().getDetails().getRtmToken());
                                Log.i("Agora:ChannelName", response.body().getDetails().getChannelName());

                                App.getSingleton().setHostType("1");

                                App.getSingleton().setMainHostUserId(CommonUtils.getUserId());

                                Intent intent = new Intent(requireActivity(), CallActivity.class);
                                intent.putExtra("channelName", response.body().getDetails().getChannelName());
                                intent.putExtra("liveUserName", userName);
                                intent.putExtra("userId", CommonUtils.getUserId());
                                intent.putExtra("liveId", response.body().getDetails().getMainId());
                                intent.putExtra("liveType", "multiLive");
                                intent.putExtra("liveStatus", "hostLive");
                                intent.putExtra("token", response.body().getDetails().getToke());
                                intent.putExtra("level", response.body().getDetails().getUserLeval());
                                intent.putExtra("coin", "0");
                                intent.putExtra("starCount", response.body().getDetails().getStarCount());
                                intent.putExtra("status", "2");
                                intent.putExtra("name", response.body().getDetails().getName());
                                intent.putExtra("image", App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getImage());
                                startActivity(intent);

//                                config().setChannelName(response.body().getDetails().getChannelName());
                                App.getSingleton().setAgoraToken(response.body().getDetails().getToke());


                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("onResponse", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ModelGetToken> call, @NotNull Throwable t) {
                    Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(requireActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {

        super.onResume();

        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            assert data != null;
            Uri imageUriPhotos = data.getData();

            stringPhotoPath = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos);

            hitApiPosterApp(CommonUtils.getUserId(), stringPhotoPath);

            circular_image.setImageURI(imageUriPhotos);


        } else if (resultCode == ImagePicker.RESULT_ERROR) {

            Toast.makeText(requireContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();

        } else if (resultCode == Activity.RESULT_OK && requestCode == 3) {

            assert data != null;
            video = data.getData();

            video_path = RealPathUtil.getRealPath(requireContext(), video);

            Bundle bundle = new Bundle();
            bundle.putString("video", video_path);
            Fragment fragment = new PostLiveFragment();
            fragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, fragment).addToBackStack(null).commit();
        } else {
            Toast.makeText(requireContext(), "Image Uploading Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void hitApiPosterApp(String userId, String stringPhotoPath) {

        if (requireActivity() != null) {
           viewModelClass.addPosterImage(CommonUtils.getRequestBodyText(userId), CommonUtils.getFileData(stringPhotoPath, "posterImage")).observe(requireActivity(), map -> {

               if (map.get("status").toString().equals("1")) {
                   Toast.makeText(requireContext(), "" + map.get("message"), Toast.LENGTH_SHORT).show();

               }
           });

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void setChannelNameGetToken(String channelName, String type) {
        if (CommonUtils.isNetworkConnected(requireActivity())) {
            apiInterface.setAgoraChannel(channelName, CommonUtils.getUserId(), "", "", type, 1, String.valueOf(VideoGridContainer.MAX_USER)).enqueue(new Callback<ModelGetToken>() {

                @Override
                public void onResponse(@NotNull Call<ModelGetToken> call, @NotNull Response<ModelGetToken> response) {

                    try {
                        if (response.body() != null) {

                            if (response.body().getSuccess().equalsIgnoreCase("1")) {
                                loginRTM(response.body().getDetails().getRtmToken());
                                App.getSingleton().setHostType(type);
                                App.getSingleton().setMainHostUserId(CommonUtils.getUserId());
                                AgoraPkLiveActivity.live_status = response.body().getDetails().getBool();

                                startActivity(new Intent(getActivity(), AgoraPkLiveActivity.class)
                                        .putExtra("boxstatus", response.body().getDetails().getCheckBoxStatus())
                                        .putExtra(AgoraLiveMainActivity.USER_LEVEL, response.body().getDetails().getUserLeval())
                                        .putExtra(AgoraLiveMainActivity.USER_STAR_COUNT, response.body().getDetails().getStarCount())
                                        .putExtra(AgoraLiveMainActivity.AGORA_TOKEN, response.body().getDetails().getToke())
                                        .putExtra(AgoraLiveMainActivity.AGORA_RTM_TOKEN, response.body().getDetails().getRtmToken())
                                        .putExtra(AgoraLiveMainActivity.FOLLOWERS, response.body().getDetails().getFollowerCount())
                                        .putExtra(AgoraLiveMainActivity.COIN, response.body().getDetails().getCoin())
                                        .putExtra("role", Constants.CLIENT_ROLE_BROADCASTER)
                                        .putExtra("liveid", response.body().getDetails().getMainId())
                                        .putExtra(AppConstants.AGORA_LIVE_ID, response.body().getDetails().getMainId())
                                        .putExtra(AgoraLiveMainActivity.AGORA_CHANNEL_NAME, response.body().getDetails().getChannelName())
                                        .putExtra("box", response.body().getDetails().getBox())
                                        .putExtra("hostType", type)
                                        .putExtra("bool", response.body().getDetails().getBool()));

                                    App.getSingleton().setMaxJoiners(maxValueOfJoiners);
                                config().setChannelName(response.body().getDetails().getChannelName());
                                App.getSingleton().setAgoraToken(response.body().getDetails().getToke());

                            } else {
                                banDialog(response.body().getRequestStatus());
                            }
                        } else {
                            Toast.makeText(requireContext(), "Technical issue..", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(requireActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("onResponse", "" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(@NotNull Call<ModelGetToken> call, @NotNull Throwable t) {

                    Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(requireActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }


    }

    private void banDialog(String requeststatus) {

        LayoutInflater layoutInflater = LayoutInflater.from(requireContext());
        final View confirmdailog = layoutInflater.inflate(R.layout.dialog_banned, null);
        dailogbox = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).create();
        dailogbox.setCancelable(false);
        dailogbox.setCanceledOnTouchOutside(true);
        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dailogbox.setView(confirmdailog);
        dailogbox.show();
        TextView bantext = dailogbox.findViewById(R.id.tv_bantext);
        Button button = dailogbox.findViewById(R.id.bt_sentRequest);
        assert bantext != null;
        if (requeststatus == null) {
            bantext.setText("Apply for Host To Go Live");
        } else {
            bantext.setText("Request Under Process");
            button.setVisibility(View.GONE);
        }


        assert button != null;
        button.setOnClickListener((View view) -> {
            if (requeststatus == null) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ApplyForHost()).addToBackStack(null).commit();

            } else {
                Toast.makeText(requireActivity(), "Request already send to the admin", Toast.LENGTH_SHORT).show();

            }

            dailogbox.dismiss();
        });
    }

    private void sendRequestDialog() {

        LayoutInflater layoutInflater = LayoutInflater.from(requireContext());
        final View confirmdailog = layoutInflater.inflate(R.layout.dialog_sendliverequest, null);
        dialog = new androidx.appcompat.app.AlertDialog.Builder(requireContext()).create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setView(confirmdailog);
        dialog.show();


        EditText et_querytext = dialog.findViewById(R.id.et_querytext);
        Button send = dialog.findViewById(R.id.bt_send);
        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String getquerytext = et_querytext.getText().toString();
                if (getquerytext.isEmpty()) {
                    et_querytext.setError("Enter query");
                } else {
                    request(getquerytext);
                    dialog.dismiss();

                }
            }
        });
    }

    private void request(String query) {

        new LiveMvvm().liveRequest(requireActivity(), CommonUtils.getUserId(), query).observe(requireActivity(), new Observer<Map>() {

            @Override
            public void onChanged(Map map) {

                Toast.makeText(requireActivity(), map.get("message").toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected App application() {

        return (App) requireActivity().getApplication();
    }


    protected EngineConfig config() {

        return application().engine();
    }


    private void loginRTM(String rtmToken) {

        if (CommonUtils.isNetworkConnected(requireActivity())) {
        } else {
            Toast.makeText(requireActivity(), "No Internet", Toast.LENGTH_SHORT).show();
        }
    }
    private void getPosterImage(String userId){

        viewModelClass.getPosterImage(userId).observe(requireActivity(), response -> {

            if (response !=null){
                if (response.getStatus().equalsIgnoreCase("1")){
                    posterIMG = response.getDetails();
                    Glide.with(requireContext()).load(response.getDetails()).placeholder(R.drawable.app_logo).into(circular_image);
                }
            }
        });
    }
}