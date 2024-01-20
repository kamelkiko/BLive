package com.expert.blive.Agora.agoraLive.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import com.expert.blive.Agora.openvcall.model.EngineConfig;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.retrofit.ApiInterFace;
import com.expert.blive.retrofit.BaseUrl;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.AppConstants;
import com.expert.blive.utils.CommonUtils;
import com.expert.blive.Agora.agoraLive.models.ModelGetToken;
import com.expert.blive.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Map;

import io.agora.rtc.Constants;
import io.agora.rtc.RtcEngine;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgoraLiveMainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    public static final String AGORA_LIVE_IMAGE = "agora_live_image";
    public static final String AGORA_LIVE_FRAME = "agora_live_frame";
    public static final String AGORA_LIVE_GARAGE = "agora_live_garage";
    private static final int REQUEST_ENABLE_GPS = 5000;
    private static final int PERMISSION_ID = 2000;
    private Activity activity;
    public static final String AGORA_TOKEN = "agora_token";
    public static final String AGORA_RTM_TOKEN = "agora_RTM_token";
    public static final String AGORA_CHANNEL_NAME = "agora_channel";
    public static final String FOLLOWERS = "followers";
    public static final String FOLLOWER_STATUS = "follower_status";
    public static final String LIVE_REAL_NAME = "live_real_name";
    public static final String COUNT = "count";
    public static final String USER_LEVEL = "user_level";
    public static final String USER_STAR_COUNT = "user_star_count";
    public static final String COIN = "coin";
    private LiveMvvm liveMvvm;
    ApiInterFace apiInterface = BaseUrl.getRetrofitClient().create(ApiInterFace.class);
    String userName = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getUsername();
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation = null;
    AlertDialog dailogbox, dialog;
    RelativeLayout relativeLayout;
    private String maxValueOfJoiners = "1";
    private Button singleHostBtn, multiHostBtn;
    //     GifImageView gifImage;
    GifImageView gifImageView;
    TextView videolive, audiolive, multilive;
    String hostType = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agora_live_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        liveMvvm = new LiveMvvm();
        activity = AgoraLiveMainActivity.this;


        findIds();

//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(AgoraLiveMainActivity.this);
    }

    private void request(String query) {
        liveMvvm.liveRequest(this, CommonUtils.getUserId(), query).observe(this, new Observer<Map>() {
            @Override
            public void onChanged(Map map) {
                if (map.get("success").toString().equalsIgnoreCase("1")) {
                    Toast.makeText(activity, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, map.get("message").toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (checkPermissions()) {
//            getLastLocation();
//        } else {
//            requestPermissions();
//        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    protected RtcEngine rtcEngine() {
        return application().rtcEngine();
    }

    private void loginRTM(String rtmToken) {
        if (CommonUtils.isNetworkConnected(activity)) {
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    boolean isLiveClicked = false;

    private void findIds() {
//        videolive = findViewById(R.id.tv_videoLive);
//        videolive.setOnClickListener(this);
        multilive = findViewById(R.id.tv_multiLive);
        multilive.setOnClickListener(this);
        audiolive = findViewById(R.id.tv_audioLive);
        audiolive.setOnClickListener(this);
        singleHostBtn = findViewById(R.id.btn_hostLive);
        singleHostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinHost();
            }
        });
        multiHostBtn = findViewById(R.id.btn_multiLive);
        multiHostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                multiHost();
            }
        });
//        AndExoPlayerView andExoPlayerView = findViewById(R.id.andExoPlayerView);
        relativeLayout = findViewById(R.id.rl_gift);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                cameraView.setVisibility(View.GONE);
//                andExoPlayerView.setVisibility(View.VISIBLE);
//                andExoPlayerView.setSource("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_30mb.mp4");
            }
        });

        findViewById(R.id.fab_go_live).setOnClickListener(v -> {
            findViewById(R.id.fab_go_live).setEnabled(false);
//                gifImage=(GifImageView)findViewById(R.id.gifImage);
//              gifImage.setVisibility(View.VISIBLE);

            gifImageView = findViewById(R.id.gifdrawable);

            Uri uri = Uri.parse("http://netdna.webdesignerdepot.com/uploads/2013/04/Dvdp3.gif");
            gifImageView.setImageURI(uri);

            if (isLocationEnabled()) {
                if (!isLiveClicked) {
                    isLiveClicked = true;


                    hostType = "2";

                    setChannelNameGetToken(userName, "2");
                } else {
                    Toast.makeText(activity, "Connecting...", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(activity, "Turn on Gps to Go Live", Toast.LENGTH_SHORT).show();
//                turnGPSOn();
            }
        });
        findViewById(R.id.img_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void multiHost() {
        multiHostBtn.setBackgroundResource(R.drawable.btn_bg);
        multiHostBtn.setTextColor(getResources().getColor(R.color.white));
        singleHostBtn.setBackgroundResource(R.drawable.stroke_primary);
        singleHostBtn.setTextColor(getResources().getColor(R.color.app_pink_color));
//        pkHostBtn.setBackgroundResource(R.drawable.stroke_primary);
//        pkHostBtn.setTextColor(getResources().getColor(R.color.purple));
        maxValueOfJoiners = "4";
    }

    private void sinHost() {
        singleHostBtn.setBackgroundResource(R.drawable.btn_bg);
        singleHostBtn.setTextColor(getResources().getColor(R.color.white));
//        pkHostBtn.setBackgroundResource(R.drawable.stroke_primary);
//        pkHostBtn.setTextColor(getResources().getColor(R.color.purple));
        multiHostBtn.setBackgroundResource(R.drawable.stroke_primary);
        multiHostBtn.setTextColor(getResources().getColor(R.color.app_pink_color));
        maxValueOfJoiners = "1";
    }

    String channelName = "";

    protected App application() {
        return (App) getApplication();
    }

    protected EngineConfig config() {
        return application().engine();
    }

    private void setChannelNameGetToken(String channelName, String type) {


             apiInterface.setAgoraChannel(channelName,
                    CommonUtils.getUserId(),
                   "",
                    "",
                    type, 0,"").enqueue(new Callback<ModelGetToken>() {
                @Override
                public void onResponse(Call<ModelGetToken> call, Response<ModelGetToken> response) {
                    try {
                        if (response.body() != null) {
                            isLiveClicked = true;
//                            Toast.makeText(activity, "Starting Live", Toast.LENGTH_LONG).show();
                            if (response.body().getSuccess().equalsIgnoreCase("1")) {
                                Log.i("Agora:RtmToken", response.body().getDetails().getRtmToken());
                                Log.i("Agora:ChannelName", response.body().getDetails().getChannelName());

                                loginRTM(response.body().getDetails().getRtmToken());

                                App.getSingleton().setHostType(type);

//                                App.getSingleton().setMainHostUserId(CommonUtils.userId(activity));


//                                if (type.equals("2")) {
//                                    startActivity(new Intent(activity, AgoraPkBattle.class)
//                                            .putExtra("boxstatus", response.body().getDetails().getCheckBoxStatus())
//                                            .putExtra(USER_LEVEL, response.body().getDetails().getUserLeval())
//                                            .putExtra(USER_STAR_COUNT, response.body().getDetails().getStarCount())
//                                            .putExtra(AGORA_TOKEN, response.body().getDetails().getToke())
//                                            .putExtra(AGORA_RTM_TOKEN, response.body().getDetails().getRtmToken())
//                                            .putExtra(FOLLOWERS, response.body().getDetails().getFollowerCount())
//                                            .putExtra(COIN, response.body().getDetails().getCoin())
//                                            .putExtra("role", Constants.CLIENT_ROLE_BROADCASTER)
//                                            .putExtra("liveid", response.body().getDetails().getMainId())
//                                            .putExtra(AppConstants.AGORA_LIVE_ID, response.body().getDetails().getMainId())
//                                            .putExtra(AGORA_CHANNEL_NAME, channelName)
//                                            .putExtra("box", response.body().getDetails().getBox())
//                                            .putExtra("hostType", hostType).putExtra("bool", response.body().getDetails().getBool()));
//                                }


//                                else


                                    if (type.equals("1")) {
                                    startActivity(new Intent(activity, AgoraPkLiveActivity.class)
                                            .putExtra("boxstatus", response.body().getDetails().getCheckBoxStatus())
                                            .putExtra(USER_LEVEL, response.body().getDetails().getUserLeval())
                                            .putExtra(USER_STAR_COUNT, response.body().getDetails().getStarCount())
                                            .putExtra(AGORA_TOKEN, response.body().getDetails().getToke())
                                            .putExtra(AGORA_RTM_TOKEN, response.body().getDetails().getRtmToken())
                                            .putExtra(FOLLOWERS, response.body().getDetails().getFollowerCount())
                                            .putExtra(COIN, response.body().getDetails().getCoin())
                                            .putExtra("role", Constants.CLIENT_ROLE_BROADCASTER)
                                            .putExtra("liveid", response.body().getDetails().getMainId())
                                            .putExtra(AppConstants.AGORA_LIVE_ID, response.body().getDetails().getMainId())
                                            .putExtra(AGORA_CHANNEL_NAME, channelName)
                                            .putExtra("box", response.body().getDetails().getBox())
                                            .putExtra("hostType", hostType).putExtra("bool", response.body().getDetails().getBool()));

                                }

                                App.getSingleton().setMaxJoiners(maxValueOfJoiners);
                                config().setChannelName(channelName);
                                App.getSingleton().setAgoraToken(response.body().getDetails().getToke());
                                finish();
                            } else {
                                banDialog(response.body().getMessage(), response.body().getRequestStatus());
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelGetToken> call, Throwable t) {
                    Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


//        if (mLocation == null) {
//            Toast.makeText(activity, "Location not known,Turn On GPS", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (CommonUtils.isNetworkConnected(activity)) {
//            Log.e("ParamsAgoraLive", "setChannelNameGetToken: " + channelName + " " +
//                    CommonUtils.getUserId() + " " +
//                    String.valueOf(mLocation.getLatitude()) + " " +
//                    String.valueOf(mLocation.getLongitude()) + " " +
//                    hostType + " " + 0);
//
    }


    private void banDialog(String message, String requeststatus) {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View confirmdailog = layoutInflater.inflate(R.layout.dialog_banned, null);
        dailogbox = new AlertDialog.Builder(this).create();
        dailogbox.setCancelable(false);
        dailogbox.setCanceledOnTouchOutside(true);
        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dailogbox.setView(confirmdailog);
        dailogbox.show();

        TextView bantext = dailogbox.findViewById(R.id.tv_bantext);
        bantext.setText(message);
        Button button = dailogbox.findViewById(R.id.bt_sentRequest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (requeststatus.equalsIgnoreCase("1")) {
                    Toast.makeText(activity, "Request already send to the admin", Toast.LENGTH_SHORT).show();
                } else {
                    sendRequestDialog();
                }
                dailogbox.dismiss();
            }
        });
    }

    private void alertDailog() {
        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        final View confirmdailog = layoutInflater.inflate(R.layout.multi_request_dailog, null);
        final android.app.AlertDialog dailogbox = new android.app.AlertDialog.Builder(activity).create();
        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dailogbox.setView(confirmdailog);
        dailogbox.setCanceledOnTouchOutside(false);
        dailogbox.setCancelable(false);

        TextView tv_text = confirmdailog.findViewById(R.id.tv_text);
        tv_text.setText("How many max users can join your live session?");

//        Glide.with(activity).load(multiLiveModelCount.getImage()).placeholder(R.drawable.ic_user1).into(iv_image);

        Button yes, no, btn_back;

        yes = confirmdailog.findViewById(R.id.confirmBooking);
        no = confirmdailog.findViewById(R.id.btn_editBooking);
        btn_back = confirmdailog.findViewById(R.id.btn_back);
        btn_back.setVisibility(View.VISIBLE);

        yes.setText("4 Users");
        no.setText("6 Users");

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                maxValueOfJoiners = "4";

                dailogbox.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maxValueOfJoiners = "6";
                dailogbox.dismiss();
            }
        });
        dailogbox.show();
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sinHost();
                dailogbox.dismiss();
            }
        });
        dailogbox.show();
    }


    private void sendRequestDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View confirmdailog = layoutInflater.inflate(R.layout.dialog_sendliverequest, null);
        dialog = new AlertDialog.Builder(this).create();
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
                    onBackPressed();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logoutAgoraRTM();

    }

    private void logoutAgoraRTM() {


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    private void generateOtherUserToken(String channelName, String userChannelId, String image) {
        Log.i("Agora:ChannelName", channelName);
        if (CommonUtils.isNetworkConnected(activity)) {
            apiInterface.otherRtmToken(CommonUtils.getUserId()).enqueue(new Callback<Map>() {
                @Override
                public void onResponse(Call<Map> call, Response<Map> response) {
                    if (response.body() != null) {
                        if (response.body().get("success").toString().equalsIgnoreCase("1")) {
                            String token = response.body().get("token").toString();
                            Log.i("Agora:OtherToken", token);
                            loginRTM(token);
//                            Intent intent = new Intent(activity, AgoraPlayerActivity.class);
//                            intent.putExtra(AGORA_CHANNEL_NAME, channelName);
//                            intent.putExtra(AppConstants.OTHER_USER_ID, userChannelId);
//                            intent.putExtra(AGORA_RTM_TOKEN, token);
//                            intent.putExtra(AGORA_LIVE_IMAGE, image);
//                            intent.putExtra("role", Constants.CLIENT_ROLE_AUDIENCE);
//                            intent.putExtra(AGORA_TOKEN, token);
//                            startActivity(intent);
                        } else {

                        }
                    }
                }

                @Override
                public void onFailure(Call<Map> call, Throwable t) {
                    Log.i("Agora:OtherToken", t.getMessage());
                }
            });
        } else {
            Toast.makeText(activity, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

//    public void turnGPSOn() {
//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(this)
//                    .addApi(LocationServices.API).addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this).build();
//            mGoogleApiClient.connect();
//            LocationRequest locationRequest = LocationRequest.create();
//            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            locationRequest.setInterval(30 * 1000);
//            locationRequest.setFastestInterval(5 * 1000);
//            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
//                    .addLocationRequest(locationRequest);
//
//            // **************************
//
//            builder.setAlwaysShow(true); // this is the key ingredient
//
//            // **************************
//
//            PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
//                    .checkLocationSettings(mGoogleApiClient, builder.build());
//            result.setResultCallback(new com.google.android.gms.common.api.ResultCallback<LocationSettingsResult>() {
//                @Override
//                public void onResult(LocationSettingsResult result) {
//                    final Status status = result.getStatus();
//                    final LocationSettingsStates state = result.getLocationSettingsStates();
//                    switch (status.getStatusCode()) {
//                        case LocationSettingsStatusCodes.SUCCESS:
//                            // All location settings are satisfied. The client can
//                            // initialize location
//                            // requests here.
////                            permissions();
//                            break;
//                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                            // Location settings are not satisfied. But could be
//                            // fixed by showing the user
//                            // a dialog.
//                            try {
//                                // Show the dialog by calling
//                                // startResolutionForResult(),
//                                // and check the result in onActivityResult().
//                                status.startResolutionForResult(AgoraLiveMainActivity.this, REQUEST_ENABLE_GPS);
//                            } catch (IntentSender.SendIntentException e) {
//                                // Ignore the error.
//                            }
//                            break;
//                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                            // Location settings are not satisfied. However, we have
//                            // no way to fix the
//                            // settings so we won't show the dialog.
//                            Toast.makeText(AgoraLiveMainActivity.this, "GPS Off", Toast.LENGTH_SHORT).show();
//                            break;
//                    }
//                }
//            });
//        }
//    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i("location", "connected");

    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("location", "onConnectionSuspended");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("location", connectionResult.getErrorMessage());
    }


//    private void getLastLocation() {
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                if (ActivityCompat.checkSelfPermission(AgoraLiveMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AgoraLiveMainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                mFusedLocationClient.getLastLocation().addOnCompleteListener(
//                        new OnCompleteListener<Location>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Location> task) {
//                                Location location = task.getResult();
//                                if (location == null) {
////                                    Toast.makeText(activity, "Fetching Location", Toast.LENGTH_SHORT).show();
//                                    requestNewLocationData();
//                                } else {
////                                    Toast.makeText(activity, "Location Fetched", Toast.LENGTH_SHORT).show();
//                                    Log.i("location", location.getLatitude() + " : " + location.getLongitude());
//                                    mLocation = location;
//                                }
//                            }
//                        }
//                );
//            } else {
//                turnGPSOn();
//            }
//        } else {
//            requestPermissions();
//        }
//    }

//    @SuppressLint("MissingPermission")
//    private void requestNewLocationData() {
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(0);
//        mLocationRequest.setFastestInterval(0);
//        mLocationRequest.setNumUpdates(1);
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(AgoraLiveMainActivity.this);
//        mFusedLocationClient.requestLocationUpdates(
//                mLocationRequest, mLocationCallback,
//                Looper.myLooper()
//        );
//    }

//    private LocationCallback mLocationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
////            Toast.makeText(activity, "Location Fetched", Toast.LENGTH_SHORT).show();
//            Location mLastLocation = locationResult.getLastLocation();
//            mLocation = mLastLocation;
//            Log.i("location", mLastLocation.getLatitude() + " : " + mLastLocation.getLongitude());
//        }
//    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(AgoraLiveMainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AgoraLiveMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                AgoraLiveMainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            switch (requestCode) {
                case 1001: {
                    int count = 0;
                    if (grantResults.length > 0)
                        for (int i = 0; i < grantResults.length; i++) {
                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                                count++;
                        }

                    if (count == grantResults.length) {
                    } else if ((Build.VERSION.SDK_INT > 23 && !shouldShowRequestPermissionRationale(permissions[0])
                            && !shouldShowRequestPermissionRationale(permissions[1])
                    )) {
                    } else {
                        Toast.makeText(AgoraLiveMainActivity.this, "Permission Not granted", Toast.LENGTH_SHORT).show();
                        requestPermissions();
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_GPS) {
            if (resultCode == RESULT_OK) {
                requestPermissions();
                Toast.makeText(activity, "Gps Enabled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Turn On GPS to use this Feature !", Toast.LENGTH_SHORT).show();
//                turnGPSOn();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void setTextViewDrawableColor(TextView textView, int color) {
        for (Drawable drawable : textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.setColorFilter(new PorterDuffColorFilter(getColor(color), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_videoLive:
                App.getSingleton().setAudioLive("");
                hostType = "2";
                videolive.setTextColor(getResources().getColor(R.color.white));
                setTextViewDrawableColor(videolive, R.color.white);
                multilive.setTextColor(getResources().getColor(R.color.dark_grey));
                setTextViewDrawableColor(multilive, R.color.dark_grey);
                audiolive.setTextColor(getResources().getColor(R.color.dark_grey));
                setTextViewDrawableColor(audiolive, R.color.dark_grey);
                sinHost();
                break;
            case R.id.tv_multiLive:


                hostType = "1";
                setChannelNameGetToken(userName, "1");

//                Toast.makeText(activity, "Coming Soon", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(AgoraLiveMainActivity.this, MultiHostLiveActivity.class));
//                App.getSingleton().setAudioLive("");
//                hostType="1";
//                multilive.setTextColor(getResources().getColor(R.color.white));
//                setTextViewDrawableColor(multilive, R.color.white);
//                videolive.setTextColor(getResources().getColor(R.color.darkgrey));
//                setTextViewDrawableColor(videolive, R.color.darkgrey);
//                audiolive.setTextColor(getResources().getColor(R.color.darkgrey));
//                setTextViewDrawableColor(audiolive, R.color.darkgrey);
//                multiHost();
                break;
            case R.id.tv_audioLive:
                Toast.makeText(activity, "Coming Soon", Toast.LENGTH_SHORT).show();
//                hostType="3";
//                App.getSingleton().setAudioLive("1");
////                FirebaseHelper.audioLiveStatus(CommonUtils.userId(activity),"1");
//                audiolive.setTextColor(getResources().getColor(R.color.white));
//                setTextViewDrawableColor(audiolive, R.color.white);
//                videolive.setTextColor(getResources().getColor(R.color.darkgrey));
//                setTextViewDrawableColor(videolive, R.color.darkgrey);
//                multilive.setTextColor(getResources().getColor(R.color.darkgrey));
//                setTextViewDrawableColor(multilive, R.color.darkgrey);
//                sinHost();
//                Toast.makeText(activity, "Under Development", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}