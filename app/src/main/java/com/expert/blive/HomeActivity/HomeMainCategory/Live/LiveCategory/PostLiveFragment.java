package com.expert.blive.HomeActivity.HomeMainCategory.Live.LiveCategory;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.expert.blive.HomeActivity.HomeMainCategory.Subscription.SubcriptionFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.ModelClass.UploadClass;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import static android.content.ContentValues.TAG;


public class PostLiveFragment extends Fragment {
    private String image_url,description,hashtag;
    private ImageView video_thumbnail;
    private EditText enter_description_post, hashtag_get, hashtag_two_get;
    private MvvmViewModelClass viewModelClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_post_live, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);



        image_url = getArguments().getString("path");
        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(image_url, MediaStore.Images.Thumbnails.MINI_KIND);
        video_thumbnail.setImageBitmap(thumb);

        Log.d(TAG, "onViewCreated: "+image_url);



//        String hashtag_two = hashtag_two_get.getText().toString();

        Toast.makeText(requireContext(), ""+image_url, Toast.LENGTH_SHORT).show();


        view.findViewById(R.id.post_button).setOnClickListener(view1 -> {
            description = enter_description_post.getText().toString();
            hashtag = hashtag_get.getText().toString();

            viewModelClass.uploadPostLiveData(requireActivity(),
                    CommonUtils.getRequestBodyText(CommonUtils.getUserId()),
                    CommonUtils.getRequestBodyText(hashtag),
                    CommonUtils.getRequestBodyText(""),
                    CommonUtils.getRequestBodyText(description),
                    CommonUtils.getRequestBodyText(""),
                    CommonUtils.getRequestBodyText(""),
                    CommonUtils.getRequestBodyText(""),
                    CommonUtils.getFileData(image_url, "videoPath"),
                    CommonUtils.getFileData(image_url, "thumbnail")).observe(requireActivity(), uploadClass -> {
                        Log.d(TAG, "onChanged: " + hashtag);
                        Log.d(TAG, "onChanged: " + description);
                        Log.d(TAG, "onChanged: " + image_url);

                        Toast.makeText(requireContext(), "" + CommonUtils.getUserId(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(requireContext(), ""+hashtag, Toast.LENGTH_SHORT).show();
                        Toast.makeText(requireContext(), ""+description, Toast.LENGTH_SHORT).show();

                        if (uploadClass.getSuccess().equals("1")) {

                            Log.d(TAG, "onChanged: " + uploadClass.message);

    //                            App.getSharedpref().saveModel(AppConstant.USER_INFO, uploadClass);
                            Toast.makeText(requireContext(), "Video Posted Successfully", Toast.LENGTH_SHORT).show();
    //                            startActivity(new Intent(requireContext(), HomeMainActivity.class));
                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new SubcriptionFragment()).addToBackStack(null).commit();
                        } else {
                            Toast.makeText(requireContext(), "Update Profile Fail", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

    private void finds(View view) {
        video_thumbnail = view.findViewById(R.id.video_thumbnail);
        enter_description_post = view.findViewById(R.id.enter_description);
        hashtag_get = view.findViewById(R.id.hashtag_get);
        hashtag_two_get = view.findViewById(R.id.hashtag_two_get);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }
}