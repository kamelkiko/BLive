package com.expert.blive.ExtraFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.MyLiveStreamAdapter;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.ModelClass.myLiveStream.MyLiveStreamRoot;
import com.expert.blive.R;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyLiveStreamHistoryFragment extends Fragment {

    private MvvmViewModelClass mvvmViewModelClass;
    private ImageView back;
    private OtpRoot otpRoot;
    private TextView txtNoLiveStream;
    private RecyclerView liveHistory;
    private MyLiveStreamAdapter myLiveStreamAdapter;
    public static String image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mvvmViewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_my_live_stream_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        otpRoot =  App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
        image = otpRoot.getImage();

        findIds(view);
        getMyLiveStreams();

        myLiveStreamAdapter = new MyLiveStreamAdapter(new ArrayList<>(),requireContext());
        liveHistory.setAdapter(myLiveStreamAdapter);

    }

    private void getMyLiveStreams() {

        mvvmViewModelClass.myLiveStreamRootLiveData(CommonUtils.getUserId()).observe(requireActivity(), myLiveStreamRoot -> {
            if (myLiveStreamRoot.getUserDetails().getAllLives()!=null){
                myLiveStreamAdapter.loadData(myLiveStreamRoot.getUserDetails().getAllLives());
            }else {
                txtNoLiveStream.setVisibility(View.VISIBLE);
            }
        });
    }

    private void findIds(View view) {
        liveHistory = view.findViewById(R.id.liveHistory);
        txtNoLiveStream = view.findViewById(R.id.txtNoLiveStream);

    }
}