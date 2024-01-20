package com.expert.blive.HomeActivity.HomeMainCategory.Subscription.category;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.ModelClass.ShowVideoClass;
import com.expert.blive.R;

import org.jetbrains.annotations.NotNull;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

public class SubscribedVideosFragment extends Fragment {

    private RecyclerView subscribeRV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscribed_videos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findIds(view);

        getVideo();

    }

    private void getVideo() {
        new MvvmViewModelClass().getAllVideosLiveData(requireActivity()).observe(requireActivity(), new Observer<ShowVideoClass>() {
            @Override
            public void onChanged(ShowVideoClass showVideoClass) {

                if (showVideoClass.getSuccess().equalsIgnoreCase("1")) {
                    Log.d(TAG, "onChanged: " + showVideoClass.getDetails().size());
//                    FollowingSubscriptionAdapter followingSubscriptionAdapter = new FollowingSubscriptionAdapter(new FollowingSubscriptionAdapter.FollowingVideoCallback() {
//                        @Override
//                        public void call_video(int position) {
//
//                            Bundle bundle = new Bundle();
//
//                            bundle.putSerializable("key", showVideoClass.getDetails().get(position));
//
//                            ExoPlayerViewFragment exoPlayerViewFragment = new ExoPlayerViewFragment();
//
//                            exoPlayerViewFragment.setArguments(bundle);
//
//                            Toast.makeText(requireContext(), "open", Toast.LENGTH_SHORT).show();
//                            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, exoPlayerViewFragment).addToBackStack(null).commit();
//                        }
//                    }, requireContext(), showVideoClass.getDetails());
//                    subscribeRV.setAdapter(followingSubscriptionAdapter);

                } else {

                    Toast.makeText(requireContext(), "" + showVideoClass.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void findIds(View view) {
        subscribeRV = view.findViewById(R.id.subscribeRV);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.VISIBLE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.VISIBLE);
    }

}