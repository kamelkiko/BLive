package com.expert.blive.ExtraFragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.expert.blive.Agora.agoraLive.activity.AgoraPkLiveActivity;
import com.expert.blive.Agora.agoraLive.ui.VideoGridContainer;
import com.expert.blive.HomeActivity.HomeMainCategory.Live.LiveMainFragment;
import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;
import com.expert.blive.Agora.agoraLive.activity.AgoraLiveMainActivity;
import com.expert.blive.Agora.agoraLive.models.ModelGetToken;
import com.expert.blive.Agora.openvcall.model.EngineConfig;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.AppConstants;
import com.expert.blive.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import io.agora.rtc.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChooseSelectionFragment extends Fragment {
    private LiveMvvm liveMvvm;
    private View view;
    private Button start_live;
    private String maxValueOfJoiners = "1";
    androidx.appcompat.app.AlertDialog dailogbox, dialog;
    private ImageView imageFourPerson, imgSixPerson, imageEightPerson, imageCamera;
    private LinearLayout llEightPerson, llSixPerson, llFourPerson;
    String userName;



    @Override
    public void onResume() {
        super.onResume();


        imageEightPerson.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.dark_grey));
        imgSixPerson.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.dark_grey));
        imageFourPerson.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.dark_grey));
        VideoGridContainer.MAX_USER = 0;
//        cameraView.isFacingFront();

//        cameraView.isFacingFront();
//        cameraView.toggleFacing();

        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
//        requireActivity().findViewById(R.id.img_video).setVisibility(View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        liveMvvm = new ViewModelProvider(this).get(LiveMvvm.class);
        view = inflater.inflate(R.layout.fragment_choose_selection, container, false);

        if (!App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getUsername().isEmpty() && App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getUsername() != null) {
            userName = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getUsername();
        } else {
            userName = CommonUtils.getUserId();

        }



        VideoGridContainer.MAX_USER = 0;
        findIDs();
//        cameraView.start();
//        cameraView.isFacingFront();

        clicks();
        return view;
    }

    @SuppressLint("ResourceAsColor")
    private void clicks() {
        view.findViewById(R.id.back_img_from_live).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new LiveMainFragment()).addToBackStack(null).commit());
        imageEightPerson.setOnClickListener(view1 -> {

            setImageResourses(imageEightPerson, imageFourPerson, imgSixPerson);
            VideoGridContainer.MAX_USER = 9;

        });
        imgSixPerson.setOnClickListener(view1 -> {
            setImageResourses(imgSixPerson, imageFourPerson, imageEightPerson);
            VideoGridContainer.MAX_USER = 6;

        });
        imageFourPerson.setOnClickListener(view1 -> {
            setImageResourses(imageFourPerson, imageEightPerson, imgSixPerson);
            VideoGridContainer.MAX_USER = 5;

        });
        imageCamera.setOnClickListener(view1 -> {

        });

        start_live.setOnClickListener(view1 -> {
            if (VideoGridContainer.MAX_USER == 0) {
                Toast.makeText(requireActivity(), "Select Live Type", Toast.LENGTH_SHORT).show();
            } else {
                setChannelNameGetToken(userName, "1");

            }
        });


    }


    private void setChannelNameGetToken(String channelName, String type) {
        ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);
        if (CommonUtils.isNetworkConnected(requireActivity())) {
            apiInterface.setAgoraChannel(channelName, CommonUtils.getUserId(), "", "", type, 1, String.valueOf(VideoGridContainer.MAX_USER)).enqueue(new Callback<ModelGetToken>() {
                @Override
                public void onResponse(@NotNull Call<ModelGetToken> call, @NotNull Response<ModelGetToken> response) {

                    if (response.body() != null) {

//                            Toast.makeText(activity, "Starting Live", Toast.LENGTH_LONG).show();
                        if (response.body().getSuccess().equalsIgnoreCase("1")) {
                            Log.i("Agora:RtmToken", response.body().getDetails().getRtmToken());
                            Log.i("Agora:ChannelName", response.body().getDetails().getChannelName());

                            loginRTM(response.body().getDetails().getRtmToken());
                            App.getSingleton().setHostType("1");
                            App.getSingleton().setMainHostUserId(CommonUtils.getUserId());

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

    private void setImageResourses(ImageView imageEightPerson, ImageView imgSixPerson, ImageView imageFourPerson) {
        imageEightPerson.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.white));
        imgSixPerson.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.dark_grey));
        imageFourPerson.setColorFilter(ContextCompat.getColor(requireActivity(), R.color.dark_grey));


    }

    private void findIDs() {



        imageFourPerson = view.findViewById(R.id.imageFourPerson);
        start_live = view.findViewById(R.id.start_live);
        imgSixPerson = view.findViewById(R.id.imgSixPerson);
        imageEightPerson = view.findViewById(R.id.imageEightPerson);
        imageCamera = view.findViewById(R.id.imageCamera);
        llEightPerson = view.findViewById(R.id.llEightPerson);
        llSixPerson = view.findViewById(R.id.llSixPerson);
        llFourPerson = view.findViewById(R.id.llFourPerson);


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
        liveMvvm.liveRequest(requireActivity(), CommonUtils.getUserId(), query).observe(requireActivity(), map -> Toast.makeText(requireActivity(), map.get("message").toString(), Toast.LENGTH_SHORT).show());
    }


}