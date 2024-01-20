package com.expert.blive.BaseActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.expert.blive.HomeMainActivity;
import com.expert.blive.ModelClass.OtpClass;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.ModelClass.RegisterDetails;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LoginFragment extends Fragment {
    public FusedLocationProviderClient locationProviderClient;
    private MvvmViewModelClass mvvmViewModelClass;
    private AppCompatImageView gif_image;
    private CheckBox cb_terms;
    private TextView signup_btn;
    private EditText getName, password;
    private ImageView login_with_facebook, login_with_google;
    private GoogleSignInClient mGoogleSignInClient;
    private AppCompatButton login_with_nico, login_btn;
    private static int RC_SIGN_IN = 2;
    String deviceId, country, countryNew;
    private OtpRoot otpRoot;
    private RegisterDetails registerDetails;
    private static final int PERMISSION_ID = 44;
    public static double latitude, longitude;
    FirebaseAuth firebaseAuth;
    BeginSignInRequest signInRequest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mvvmViewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        locationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        findId(view);
        onClick(view);
        deviceId = Settings.Secure.getString(requireActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();

        firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }

    private void loginWithGoogle(String countryName, double latitude, double longitude) {


        if (countryName != null) {
            countryNew = countryName;
        } else {
            countryNew = "";
        }

        login_with_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    private void onClick(View view) {

        login_with_facebook.setOnClickListener(view1 -> {
            Toast.makeText(requireContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
        });

        view.findViewById(R.id.login_phone).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_layout, new EnterNumberFragment()).addToBackStack(null).commit();


            }
        });

        login_with_nico.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                loginWithNico();

            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://3.132.115.65/EpLive/index.php/Privacy_Policy")));
            }
        });

    }

    private void loginWithNico() {

        if (getName.getText().toString().trim().length() == 0) {
            Toast.makeText(requireContext(), "Username can't be null.", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().trim().length() == 0) {
            Toast.makeText(requireContext(), "Password can't be null.", Toast.LENGTH_SHORT).show();
        } else {
            loginApi();
        }
    }

    private void loginApi() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(getName.getText().toString(), password.getText().toString())
                .addOnSuccessListener(authResult ->
                        startActivity(new Intent(requireContext(), HomeMainActivity.class)))
                .addOnFailureListener(faild ->
                        Toast.makeText(requireContext(), "" + faild.getMessage(), Toast.LENGTH_SHORT).show());
//        mvvmViewModelClass.userLoginLiveData(requireActivity(), getName.getText().toString(), password.getText().toString(), "1", "545", "1212", "", "").observe(requireActivity(), response -> {
//            if (response != null) {
//                if (response.getSuccess().equals("1")) {
//                    App.getSharedpref().saveString(AppConstant.SESSION, "1");
//                    registerDetails = response.getDetails();
//                    App.getSharedpref().saveString(AppConstant.USERID, registerDetails.getId());
//                    App.getSharedpref().saveModel(AppConstant.USER_INFO, registerDetails);
//                    startActivity(new Intent(requireContext(), HomeMainActivity.class));
//                    Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(requireContext(), "" + response.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                Toast.makeText(requireContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    private void findId(View view) {

        gif_image = view.findViewById(R.id.gif_image);

        cb_terms = view.findViewById(R.id.round);

        login_with_google = view.findViewById(R.id.google);

        login_with_facebook = view.findViewById(R.id.login_with_facebook);

        login_with_nico = view.findViewById(R.id.login_btn);

        password = view.findViewById(R.id.password);

        getName = view.findViewById(R.id.getName);
        signup_btn = view.findViewById(R.id.signup_btn);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.addOnSuccessListener(sucess ->
                    Log.e("KAMELOO", sucess.getDisplayName())).addOnFailureListener(f ->
                    Log.e("KAMELOO", f.getMessage())).getResult(ApiException.class);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Toast.makeText(requireActivity(), "LogIn Success", Toast.LENGTH_SHORT).show();
                socialLoginWithGoogleandFacebookCaller(personEmail, personName, personId);
            }
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("KAMELOO", e.getStatus().toString());
        }

    }

    private void socialLoginWithGoogleandFacebookCaller(String personEmail, String personName, String personId) {

        Log.d("personName", "personName: " + personName);
        Log.d("personId", "personId: " + personId);
        Log.d("personEmail", "personEmail: " + personEmail);
        Log.d("deviceId", "deviceId: " + deviceId);

        mvvmViewModelClass.socialLoginRootLiveData(requireActivity(), personName, personId, personEmail, "", "", "", deviceId, countryNew).observe(requireActivity(), new Observer<OtpClass>() {

            @Override
            public void onChanged(OtpClass otpClass) {

                if (otpClass != null) {
                    if (otpClass.getSuccess().equalsIgnoreCase("1")) {
                        Toast.makeText(requireContext(), "1   " + otpClass.getMessage(), Toast.LENGTH_SHORT).show();
                        App.getSharedpref().saveString(AppConstant.SESSION, "1");
                        otpRoot = otpClass.getDetails();
                        App.getSharedpref().saveString(AppConstant.USERID, otpClass.getDetails().getId());
                        App.getSharedpref().saveString("country", otpClass.getDetails().getCountry());
                        App.getSharedpref().saveModel(AppConstant.USER_INFO, otpClass.getDetails());
                        startActivity(new Intent(requireContext(), HomeMainActivity.class));
                    } else {
                        Toast.makeText(requireActivity(), "0   " + otpClass.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Technical issue....", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void onResume() {
        super.onResume();
        getLastLocation();
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }


    private void requestPermissions() {
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                if (locationProviderClient != null) {
                    locationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {

                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            getCompleteAddressString();

                        }
                    });
                } else {
                    Toast.makeText(requireContext(), "locationProviderClient null ", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(requireActivity(), "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        locationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()

        );
    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {


            Location mLastLocation = locationResult.getLastLocation();
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();

            Geocoder geocoder;
            List<Address> addresses = null;
            geocoder = new Geocoder(requireActivity(), Locale.getDefault());
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert addresses != null;
            if (!addresses.isEmpty()) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder();

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(" ");
                }
                country = addresses.get(0).getCountryName();

            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    private void getCompleteAddressString() {

        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(requireContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses != null) {
            Address returnedAddress = addresses.get(0);
            StringBuilder strReturnedAddress = new StringBuilder();

            for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(" ");
            }
            country = addresses.get(0).getCountryName();
//            App.getSharedpref().saveString(AppConstants.USER_COUNTRY_NAME, addresses.get(0).getCountryName());
            loginWithGoogle(country, latitude, longitude);
//            App.getSharedpref().saveString(AppConstants.USER_CURRENT_ADDRESS, addresses.get(0).getLocality());

        } else {
            Toast.makeText(requireContext(), "COUNTRY null", Toast.LENGTH_SHORT).show();
        }
    }

}
