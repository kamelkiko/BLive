package com.expert.blive.HomeActivity.Profile.Family;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.expert.blive.RealPathUtil;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.ModelClass.Family.FamilyRoot;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;
import com.github.dhaval2404.imagepicker.ImagePicker;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CreatefamilyFragment extends Fragment {

    private ImageView familyProfile,image2;
    private EditText familyNameET,familyDescriptionET;
    private TextView createFamily;
    private String ImagePath = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_createfamily, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        finds(view);
        clicks(view);


    }

    private void hitApiCreatefamily() {

        RequestBody Name = RequestBody.create(MediaType.parse("text/plain"), familyNameET.getText().toString());
        RequestBody Description = RequestBody.create(MediaType.parse("text/plain"), familyDescriptionET.getText().toString());
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), CommonUtils.getUserId());

        Toast.makeText(requireContext(), ""+ CommonUtils.getFileData(ImagePath, "familyImage"), Toast.LENGTH_SHORT).show();
        new MvvmViewModelClass().createFamily(requireActivity(),userId,Name,Description,CommonUtils.getFileData(ImagePath, "familyImage")).observe(requireActivity(), new Observer<FamilyRoot>() {
            @Override
            public void onChanged(FamilyRoot familyRoot) {
                if (familyRoot !=null){
                    if (familyRoot.getStatus()==1){
                        Toast.makeText(requireContext(), "1"+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();

//                        App.getSharedpref().saveString("familyId",familyRoot.getDetails());
//                        Toast.makeText(requireContext(), ""+familyRoot.getDetails().getFamilyImage(), Toast.LENGTH_SHORT).show();

                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new MyFamilyFragment()).addToBackStack(null).commit();

                    }else {
                        Toast.makeText(requireContext(), "2"+familyRoot.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    private void clicks(View view) {
        view.findViewById(R.id.back).setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new MyFamilyFragment()).addToBackStack(null).commit();
        });
        createFamily.setOnClickListener(view1 ->  {
            hitApiCreatefamily();
        });
        familyProfile.setOnClickListener(view1 -> {
            imagepike();
        });
    }

    private void imagepike() {
        int permission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 2);
        }
    }

    @SuppressLint("Range")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            assert data != null;
            Uri imageUriPhotos = data.getData();


            ImagePath = RealPathUtil.getRealPath(requireActivity(), imageUriPhotos);

            Toast.makeText(getContext(), ImagePath, Toast.LENGTH_SHORT).show();

            familyProfile.setImageURI(imageUriPhotos);

        } else if (resultCode == ImagePicker.RESULT_ERROR) {

            Toast.makeText(requireContext(), ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(requireContext(), "Image Uploading Cancelled", Toast.LENGTH_SHORT).show();
        }
    }



    private void finds(View view) {

        familyProfile=view.findViewById(R.id.familyProfile);
        image2=view.findViewById(R.id.image);
        familyNameET=view.findViewById(R.id.familyNameET);
        familyDescriptionET=view.findViewById(R.id.familyDescriptionET);
        createFamily=view.findViewById(R.id.createFamily);
    }

}