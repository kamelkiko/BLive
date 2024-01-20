package com.expert.blive.HomeActivity.Profile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.expert.blive.RealPathUtil;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.ModelClass.OtpClass;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class EditProfileFragment extends Fragment {
    private Button SaveButton;
    private EditText getName, password,getAddBio;
    private String user_name, user_gender, user_DOB, user_lat = "", user_long = "", user_image, stringPhotoPath;
    private ImageView circular_image;
    private TextView setNumber, getDOB, getGender,getLocation;
    private OtpRoot details;
    static int PICK_IMAGE_PHOTO = 1;
    private String selected_date = "";
    private int mYear, mDay, mMonth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().setBackgroundDrawableResource(R.drawable.bg_gradient);

        findId(view);
        OnClick(view);

        details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

        setData(details);
        Picasso.with(requireContext()).load(Uri.parse(details.getImage())).placeholder(R.drawable.user_7).into(circular_image);
    }

    private void setData(OtpRoot details) {



        if (details.getPhone() != null) {
            setNumber.setText(details.getPhone());
        } else {
            setNumber.setText("123456789");
        }

        if (details.getImage() != null && details.getImage().length() != 0) {

            Picasso.with(requireActivity()).load(details.getImage()).error(R.drawable.user_7).into(circular_image);

        }


        if(details.getName()!=null){

            getName.setText(details.getName());

        }
        if (details.getPassword()!=null)
        {
            password.setText(details.getPassword());
        }

        if (details.getGender()!=null){

            getGender.setText(details.getGender());
        }

        if (details.getBio()!=null){

            getAddBio.setText(details.getBio());
        }

        if (details.getCountry()!=null){

            getLocation.setText(details.getCountry());
        }

    }

    private void OnClick(View view) {

        getGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(requireContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.gender_layout);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                RadioGroup radioGrp = dialog.findViewById(R.id.radioGrp);
                dialog.findViewById(R.id.done_gender).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedId = radioGrp.getCheckedRadioButtonId();
                        RadioButton radioSexButton = dialog.findViewById(selectedId);
                        Toast.makeText(requireContext(), radioSexButton.getText(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        getGender.setText(radioSexButton.getText().toString());
                    }
                });
            }
        });
        getDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDOBCalender();
            }
        });

        circular_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_PHOTO);
            }
        });

        SaveButton.setOnClickListener(view1 -> hitApi());

        view.findViewById(R.id.bacl_img_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new NewprofileFragment()).addToBackStack(null).commit();
            }
        });
    }

    private void getDOBCalender() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int i, int i1, int i2) {
                Date myDate = new Date();
                myDate.setMonth(i1);
                myDate.setYear(i - 1900);
                myDate.setDate(i2);
//                        date=dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM-dd");
                // Format the date to Strings
                String mdy = dmyFormat.format(myDate);

                selected_date = mdy;
//
                getDOB.setText(selected_date);
            }

        }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private void findId(View view) {
        SaveButton = view.findViewById(R.id.SaveButton);
        getName = view.findViewById(R.id.getName);
        getGender = view.findViewById(R.id.getGender);
        getDOB = view.findViewById(R.id.getDOB);
        getLocation = view.findViewById(R.id.getLocation);
        getAddBio = view.findViewById(R.id.getAddBio);
        circular_image = view.findViewById(R.id.circular_image);
        setNumber = view.findViewById(R.id.setNumber);
        password = view.findViewById(R.id.getPassword);
    }

    private void hitApi() {

        user_name = getName.getText().toString().trim();
        user_gender = getGender.getText().toString().trim();
        user_DOB = getAddBio.getText().toString().trim();
        user_long = getLocation.getText().toString().trim();

        if (user_name.isEmpty()) {
            getName.setError("Enter Username");
            getName.requestFocus();
        } else if (user_gender.isEmpty()) {
            getGender.setError("Enter gender");
            getName.requestFocus();
        } else if (getAddBio.getText().toString().isEmpty()) {
            getAddBio.setError("Enter Bio");
            getAddBio.requestFocus();
        }

       else {
            new MvvmViewModelClass().UpdateUserProfile(requireActivity(), CommonUtils.getRequestBodyText(user_name),
                            CommonUtils.getRequestBodyText(user_gender), CommonUtils.getRequestBodyText(user_DOB),
                            CommonUtils.getRequestBodyText(user_lat), CommonUtils.getRequestBodyText(user_long),
                            CommonUtils.getRequestBodyText(CommonUtils.getUserId()),CommonUtils.getRequestBodyText(password.getText().toString()),
                            CommonUtils.getFileData(stringPhotoPath, "image"))
                    .observe(requireActivity(), new Observer<OtpClass>() {
                        @Override
                        public void onChanged(OtpClass updateClass) {

                            Log.d(TAG, "onChanged: " + user_name);
                            Log.d(TAG, "onChanged: " + user_gender);
                            Log.d(TAG, "onChanged: " + user_DOB);
                            Log.d(TAG, "onChanged: " + user_lat);
                            Log.d(TAG, "onChanged: " + user_long);
                            Log.d(TAG, "onChanged: " + stringPhotoPath);

                            Toast.makeText(requireContext(), "" + CommonUtils.getUserId(), Toast.LENGTH_SHORT).show();

                            if (updateClass.getSuccess().equals("1")) {

                                Log.d(TAG, "onChanged: image=-" + updateClass.getDetails().getImage());

                                App.getSharedpref().saveModel(AppConstant.USER_INFO, updateClass.getDetails());

                                Toast.makeText(requireContext(), "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new NewprofileFragment()).addToBackStack(null).commit();
                            } else {
                                Toast.makeText(requireContext(), "Update Profile Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            assert data != null;
            Uri imageUriPhotos = data.getData();


            stringPhotoPath = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos);

            Toast.makeText(getContext(), stringPhotoPath, Toast.LENGTH_SHORT).show();

            circular_image.setImageURI(imageUriPhotos);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {

            Toast.makeText(requireContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(requireContext(), "Image Uploading Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.GONE);
    }
}