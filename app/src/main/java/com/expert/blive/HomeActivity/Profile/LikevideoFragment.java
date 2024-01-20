package com.expert.blive.HomeActivity.Profile;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.expert.blive.HomeActivity.HomeMainCategory.Subscription.category.ExoPlayerViewFragment;
import com.expert.blive.ModelClass.Video.VideoDetail2;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.MediaAdapter;
import com.expert.blive.ModelClass.Video.VideoRoot2;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

public class LikevideoFragment extends Fragment {

    private RecyclerView recycler;
    private TextView reels_not_found;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_likevideo, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);
        getData();
    }
    private void getData() {

        new MvvmViewModelClass().myLikes(CommonUtils.getUserId()).observe(requireActivity(), new Observer<VideoRoot2>() {

            @Override
            public void onChanged(VideoRoot2 videoRoot) {
                if (videoRoot !=null){
                    if (videoRoot.getSuccess()==1){
                        Log.d(TAG, "onChanged: "+videoRoot.getDetails().size());
                        MediaAdapter mediaAdapter = new MediaAdapter(new MediaAdapter.Callback() {
                            @Override
                            public void call_video(int position) {

                                Bundle bundle = new Bundle();

                                bundle.putSerializable("key",videoRoot.getDetails().get(position));

//                            App.getSharedpref().saveModel(AppConstant.USER_INFO, videoRoot.getDetails().get(position));

                                ExoPlayerViewFragment exoPlayerViewFragment = new ExoPlayerViewFragment();

                                exoPlayerViewFragment.setArguments(bundle);

                                Toast.makeText(requireContext(), "open", Toast.LENGTH_SHORT).show();
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, exoPlayerViewFragment).addToBackStack(null).commit();
                            }

                            @Override
                            public void videoDelete(VideoDetail2 detail) {

                            }
                        }, requireContext(), videoRoot.getDetails());
                        recycler.setAdapter(mediaAdapter);
                    }else {
                        reels_not_found.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void finds(View view) {

        recycler=view.findViewById(R.id.recycler);
        reels_not_found=view.findViewById(R.id.reels_not_found);
    }

}
