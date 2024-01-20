package com.expert.blive.ExtraFragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.expert.blive.RealPathUtil;
import com.expert.blive.ModelClass.CountryList;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;

import static android.content.ContentValues.TAG;


public class ApplyForHost extends Fragment {
    private Spinner spinner_country_select;
    private EditText get_name, get_number, get_address, get_email_address, get_national_id, get_agency_id;
    private String name, number, address, email_address, national_id, agency_id, country, paymentTypeName, paymentMethodName;
    private Button apply_for_host_button;
    private ImageView imageNationaliId;
    private LinearLayout clickImage;
    private LiveMvvm liveMvvm;
    RadioButton paymentMethodButton, paymentTypeButton;
    private RadioGroup paymentMethod, paymentType;
    ArrayList<String> countryList = new ArrayList<>();
    private String stringPhotoPath;
    CompoundButton.OnCheckedChangeListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        liveMvvm = new ViewModelProvider(this).get(LiveMvvm.class);
        return inflater.inflate(R.layout.fragment_apply_to_be_offial_talent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findId(view);
        getCountry();


        OnClick(view);
        if (stringPhotoPath != null) {
            clickImage.setVisibility(View.GONE);
        } else {
            clickImage.setVisibility(View.VISIBLE);
        }

    }
    private void changeStatus(RadioButton radioButton, boolean status) {
        radioButton.setOnCheckedChangeListener(null);
        radioButton.setChecked(status);
        radioButton.setOnCheckedChangeListener(listener);
    }
    private void getCountry() {

        liveMvvm.getCountries(requireActivity()).observe(requireActivity(), countryClass -> {
            if (countryClass.getSuccess().equalsIgnoreCase("1")) {
                setAdapter(countryClass.getDetails());
            } else {
                Toast.makeText(requireContext(), "" + countryClass.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapter(List<CountryList.Detail> details) {

        for (int i = 0; i < details.size(); i++) {

            countryList.add(details.get(i).getName());

        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, countryList);
        spinner_country_select.setAdapter(arrayAdapter);
        spinner_country_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_country_select.setSelection(i);
                country = spinner_country_select.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void OnClick(View viewnew) {
        viewnew.findViewById(R.id.imageBack_applyHost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
        viewnew.findViewById(R.id.imageBack_applyHost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });





        viewnew.findViewById(R.id.clickImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
//                int permission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
//                if (permission != PackageManager.PERMISSION_GRANTED) {
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
//                } else {
//                    Intent intent = new Intent(Intent.ACTION_PICK);
//                    intent.setType("image/*");
//                    startActivityForResult(intent, 2);
//                }
            }
        });
        apply_for_host_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(requireContext(), "gyikhugyjihilhjiuouuyui", Toast.LENGTH_SHORT).show();


                int paymentMethodId = paymentMethod.getCheckedRadioButtonId();
                int paymentTypeId = paymentType.getCheckedRadioButtonId();


                paymentMethodButton = viewnew.findViewById(paymentMethodId);
                paymentTypeButton = viewnew.findViewById(paymentTypeId);

                paymentTypeName = paymentTypeButton.getText().toString();
                paymentMethodName = paymentMethodButton.getText().toString();


                name = get_name.getText().toString();
                number = get_number.getText().toString();
                address = get_address.getText().toString();
                email_address = get_email_address.getText().toString();
                national_id = get_national_id.getText().toString();
                agency_id = get_agency_id.getText().toString();

                if (name.length() == 0) {
                    get_name.setError("Add Real Name");
                    get_name.requestFocus();
                } else if (number.length() == 0 || !Patterns.PHONE.matcher(get_number.getText().toString()).matches()) {
                    get_number.setError("Add Valid Number");
                    get_number.requestFocus();
                } else if (country.length() == 0) {
                    Toast.makeText(requireActivity(), "Select Country", Toast.LENGTH_SHORT).show();
                } else if (email_address.length() == 0 || !Patterns.EMAIL_ADDRESS.matcher(get_email_address.getText().toString()).matches()) {
                    get_email_address.setError("Add  Valid Email Address");
                    get_email_address.requestFocus();
                } else if (national_id.length() == 0) {
                    get_national_id.setError("Enter Valid Id");
                    get_national_id.requestFocus();
                } else if (stringPhotoPath.length() == 0) {
                    Toast.makeText(requireActivity(), "Upload National Id", Toast.LENGTH_SHORT).show();

                } else if (agency_id.length() == 0)  {
                    get_agency_id.setError("Add  Agency Id");
                    get_agency_id.requestFocus();
                } else {
                    hitApi();
                }
            }
        });
    }

    private void hitApi() {

        Toast.makeText(requireContext(), ""+agency_id, Toast.LENGTH_SHORT).show();

        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("phone", CommonUtils.getRequestBodyText(number));
        data.put("email", CommonUtils.getRequestBodyText(email_address));
        data.put("name", CommonUtils.getRequestBodyText(name));
        data.put("country", CommonUtils.getRequestBodyText(country));
        data.put("national_no", CommonUtils.getRequestBodyText(national_id));
        data.put("address", CommonUtils.getRequestBodyText(address));
        data.put("agencyId", CommonUtils.getRequestBodyText(agency_id));
        data.put("paymentType", CommonUtils.getRequestBodyText(paymentTypeName));
        data.put("userId", CommonUtils.getRequestBodyText(CommonUtils.getUserId()));
        data.put("paymentMethod", CommonUtils.getRequestBodyText(paymentMethodName));


        liveMvvm.getApplyForHost(requireActivity(),data, CommonUtils.getFileData(stringPhotoPath, "nationalId"))
                .observe(requireActivity(), uploadClass -> {
                    if (uploadClass.get("status").toString().equalsIgnoreCase("1")) {
                        OtpRoot details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
                        details.setHost_status(uploadClass.get("status").toString());
                        App.getSharedpref().saveModel(AppConstant.USER_INFO, details);
                        Log.d(TAG, "onChanged: "+App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class).getHost_status());
                        Toast.makeText(requireContext(), "1   "+uploadClass.get("message"), Toast.LENGTH_SHORT).show();
                        requireActivity().onBackPressed();
                    }
                    else {
                        Toast.makeText(requireContext(), "0   "+uploadClass.get("message"), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void findId(View view) {
        spinner_country_select = view.findViewById(R.id.spinner_country_select);
        imageNationaliId = view.findViewById(R.id.imageNationaliId);

        clickImage = view.findViewById(R.id.clickImage);
        paymentMethod = view.findViewById(R.id.radioGrp2);
        paymentType = view.findViewById(R.id.radioGrp);
        get_name = view.findViewById(R.id.get_name);
        get_number = view.findViewById(R.id.get_number);
        get_address = view.findViewById(R.id.get_address);
        get_email_address = view.findViewById(R.id.get_email_address);
        get_national_id = view.findViewById(R.id.get_national_id);
        get_agency_id = view.findViewById(R.id.get_agency_id);
        apply_for_host_button = view.findViewById(R.id.apply_for_host_button);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (stringPhotoPath != null) {
            clickImage.setVisibility(View.GONE);
        } else {
            clickImage.setVisibility(View.VISIBLE);
        }
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            assert data != null;
            Uri imageUriPhotos = data.getData();

            stringPhotoPath = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos);


            clickImage.setVisibility(View.GONE);
            imageNationaliId.setImageURI(imageUriPhotos);

        } else {
            Toast.makeText(requireContext(), "Image Uploading Cancelled", Toast.LENGTH_SHORT).show();
        }
    }
}