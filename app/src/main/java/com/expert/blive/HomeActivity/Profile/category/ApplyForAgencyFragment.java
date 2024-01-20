  package com.expert.blive.HomeActivity.Profile.category;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.expert.blive.RealPathUtil;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;


public class  ApplyForAgencyFragment extends Fragment {
    private EditText amount ,introduce_id,approval_name,special_name,introduce_name_,phone;
    private String user_image, aadhar_front,aadhar_back, stringAddressIdProof,familyAddress, bankName,accountNumber,ipsc_number,paymentMethodName;
    private ImageView image_set, done_id_proof, done_address, done_family_add, done_bank_detail,done_aadhaar_back,aadhaar_card_front_proof,aadhaar_card_back_proof,address_text_proof,address_family_proof;
    private LinearLayout add_image;
    private Button id_proof, get_address, family_Address,select_payment_method,apply_for_agency,aadhaar_back;
    private Uri imageUriPhotos, imageUriPhotos2;
    private MvvmViewModelClass mvvmViewModelClass;
    private OtpRoot details;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply_for_agency, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

        mvvmViewModelClass = new MvvmViewModelClass();

        findIds(view);
        onClicks(view);
        amount.setText("₹ " + amount.getText().toString());

        if (details!=null){

            special_name.setText(details.getUsername());
            phone.setText(details.getPhone());
        }


    }

    private void onClicks(View view) {

        address_family_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImageDialog(familyAddress);

            }
        });

        address_text_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImageDialog(stringAddressIdProof);
            }
        });

        aadhaar_card_back_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImageDialog(aadhar_back);

            }
        });

        aadhaar_card_front_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openImageDialog(aadhar_front);

            }
        });

        aadhaar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOpen(2);
                done_aadhaar_back.setVisibility(View.VISIBLE);
            }
        });

        id_proof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOpen(1);
                done_id_proof.setVisibility(View.VISIBLE);
            }
        });
        get_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOpen(3);
                done_address.setVisibility(View.VISIBLE);
            }
        });
        family_Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOpen(4);

                done_family_add.setVisibility(View.VISIBLE);
            }
        });
        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageOpen(5);
                add_image.setVisibility(View.GONE);
            }
        });
//        image_set.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageOpen(1);
//            }
//        });

        view.findViewById(R.id.imageBack_applyAgency).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.bank_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(requireContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.agency_layout);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();


                dialog.findViewById(R.id.proceed).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText bank_name = dialog.findViewById(R.id.bank_name);
                        EditText account_number = dialog.findViewById(R.id.account_number);
                        EditText ipsc = dialog.findViewById(R.id.ipsc);
                        bankName = bank_name.getText().toString();
                        accountNumber = account_number.getText().toString();
                        ipsc_number = ipsc.getText().toString();
                        if (bankName.length()==0){
                            Toast.makeText(requireContext(), "Please enter name" , Toast.LENGTH_SHORT).show();

                        }
                        else if (accountNumber.length()==0){
                            Toast.makeText(requireContext(), "Please enter account number" , Toast.LENGTH_SHORT).show();

                        }
                        else if(ipsc_number.length()==0){

                            Toast.makeText(requireContext(), "Please enter account ipfc number" , Toast.LENGTH_SHORT).show();

                        }
                        else{
                            dialog.dismiss();
                            Toast.makeText(requireContext(), ""+paymentMethodName, Toast.LENGTH_SHORT).show();
                            done_bank_detail.setVisibility(View.VISIBLE);

                        }




                    }
                });
            }
        });

        select_payment_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(requireContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.select_payment_method);
                dialog.setCanceledOnTouchOutside(true);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setGravity(Gravity.CENTER);
                dialog.show();
                RadioButton paymentMethodButton;
                RadioGroup paymentMethod;
                paymentMethod = dialog.findViewById(R.id.radioGrp2);
                int paymentMethodId = paymentMethod.getCheckedRadioButtonId();

                paymentMethodButton = dialog.findViewById(paymentMethodId);

                paymentMethodName = paymentMethodButton.getText().toString();

            }
        });
        apply_for_agency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                applyForAgency();

            }
        });


    }

    private void openImageDialog(String image) {

        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.open_image);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        dialog.show();

        ImageView status_image = dialog.findViewById(R.id.status_image);

        if (image!=null){

            status_image.setImageURI(Uri.parse(image));

        }


    }

    private void applyForAgency() {

        HashMap<String, RequestBody> data = new HashMap<>();
        data.put("userId", CommonUtils.getRequestBodyText(CommonUtils.getUserId()));
        data.put("username", CommonUtils.getRequestBodyText(details.getUsername()));
        data.put("email", CommonUtils.getRequestBodyText(introduce_id.getText().toString()));
        data.put("special_approval_name", CommonUtils.getRequestBodyText(approval_name.getText().toString()));
        data.put("phone", CommonUtils.getRequestBodyText(details.getPhone()));
        data.put("deposit_amount", CommonUtils.getRequestBodyText(amount.getText().toString()));
        data.put("bank_name", CommonUtils.getRequestBodyText(bankName));
        data.put("account_num", CommonUtils.getRequestBodyText(accountNumber));
        data.put("IFCS_code", CommonUtils.getRequestBodyText(ipsc_number));
        data.put("password", CommonUtils.getRequestBodyText(introduce_name_.getText().toString()));
        data.put("agencyCode", CommonUtils.getRequestBodyText(""));
        data.put("payment_method", CommonUtils.getRequestBodyText(paymentMethodName));

        mvvmViewModelClass.agencyLiveData(data,CommonUtils.getFileData(user_image,"image"),
                CommonUtils.getFileData(aadhar_front,"aadharCardFront"),
                CommonUtils.getFileData(stringAddressIdProof,"panCardFrontPhoto"),
                CommonUtils.getFileData(aadhar_back,"aadharCardBack"),
                CommonUtils.getFileData(familyAddress,"govt_photoId_proof")).observe(requireActivity(), new Observer<Map>() {
            @Override
            public void onChanged(Map map) {
                if (map.get("success").toString().equalsIgnoreCase("1")) {

                    OtpRoot details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

                    details.setAgency_status(map.get("status").toString());

                    Toast.makeText(requireContext(), ""+map.get("message").toString(), Toast.LENGTH_SHORT).show();
                    App.getSharedpref().saveModel(AppConstant.USER_INFO, details);
                    requireActivity().onBackPressed();

                }

            }
        });



    }

    private void findIds(View view) {
        aadhaar_back = view.findViewById(R.id.aadhaar_back);
        amount = view.findViewById(R.id.amount);
        image_set = view.findViewById(R.id.image_set);
        add_image = view.findViewById(R.id.add_image);
        id_proof = view.findViewById(R.id.id_proof);
        get_address = view.findViewById(R.id.get_address);
        done_id_proof = view.findViewById(R.id.done_id_proof);
        done_address = view.findViewById(R.id.done_address);
        done_family_add = view.findViewById(R.id.done_family_add);
        done_bank_detail = view.findViewById(R.id.done_bank_detail);
        family_Address = view.findViewById(R.id.family_Address);
        select_payment_method = view.findViewById(R.id.select_payment_method);
        apply_for_agency = view.findViewById(R.id.apply_for_agency);
        approval_name = view.findViewById(R.id.approval_name);
        introduce_id = view.findViewById(R.id.introduce_id);
        special_name = view.findViewById(R.id.special_name);
        introduce_name_ = view.findViewById(R.id.introduce_name_);
        phone = view.findViewById(R.id.phone);
        done_aadhaar_back = view.findViewById(R.id.done_aadhaar_back);
        aadhaar_card_front_proof = view.findViewById(R.id.aadhaar_card_front_proof);
        aadhaar_card_back_proof = view.findViewById(R.id.aadhaar_card_back_proof);
        address_text_proof = view.findViewById(R.id.address_text_proof);
        address_family_proof = view.findViewById(R.id.address_family_proof);

    }

    private void imageOpen(int i) {
        int permission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, i);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {


            if (requestCode == 5) {
                assert data != null;
                imageUriPhotos = data.getData();

                user_image = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos);

                Toast.makeText(getContext(), user_image, Toast.LENGTH_SHORT).show();
                image_set.setImageURI(imageUriPhotos);

            } else if (requestCode == 1) {
                assert data != null;
                imageUriPhotos2 = data.getData();

                aadhar_front = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos2);

                aadhaar_card_front_proof.setImageURI(imageUriPhotos2);


            } else if (requestCode == 3) {
                assert data != null;
                imageUriPhotos2 = null;
                imageUriPhotos2 = data.getData();

                address_text_proof.setImageURI(imageUriPhotos2);

                stringAddressIdProof = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos2);

            }
            else if (requestCode ==2){
                imageUriPhotos2 = null;
                imageUriPhotos2 = data.getData();
                aadhaar_card_back_proof.setImageURI(imageUriPhotos2);

                aadhar_back = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos2);
            }

            else if (requestCode==4){
                imageUriPhotos2 = null;
                imageUriPhotos2 = data.getData();

                address_family_proof.setImageURI(imageUriPhotos2);

                familyAddress =  RealPathUtil.getRealPath(requireActivity(), imageUriPhotos2);


            }
            else if (resultCode == ImagePicker.RESULT_ERROR) {

                Toast.makeText(requireContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(requireContext(), "Image Uploading Cancelled", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}