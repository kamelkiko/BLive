package com.expert.blive.HomeActivity.HomeMainCategory.Live.LiveCategory;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.expert.blive.Agora.activity.CallActivity;
import com.expert.blive.Agora.agoraLive.activity.OtherUserDataModel;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.PopularLiveAdapter;

import com.expert.blive.ModelClass.LiveUserModel;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.databinding.FragmentMultiBroadcast2Binding;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;


public class MultiBroadcastFragment extends Fragment {
    private FragmentMultiBroadcast2Binding binding;
    private OtpRoot details;
    private MvvmViewModelClass viewModelClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        binding = FragmentMultiBroadcast2Binding.inflate(inflater, container, false);

        hitApi();


        return binding.getRoot();
    }

    private void hitApi() {

        HashMap<String, String> data = new HashMap<>();
        data.put("userId", CommonUtils.getUserId());
        data.put("otherUserId", CommonUtils.getUserId());

        viewModelClass.someFunctionality(requireActivity(),data).observe(requireActivity(), response -> {
            if (response !=null){
                if (response.getStatus().equalsIgnoreCase("1")){
                    hitLiveUserApi(response.getDetails());
                }else {
                    Toast.makeText(requireContext(), ""+response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(requireContext(), "Technical issue....", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hitLiveUserApi(OtherUserDataModel.Details details) {
        viewModelClass.getLiveUserApi(requireActivity(), CommonUtils.getUserId()).observe(requireActivity(), liveUserModel -> {

            if (liveUserModel !=null){
                if (liveUserModel.getStatus()==1) {

                    binding.txtNoUserFound.setText("");

                    setAdapter(liveUserModel.getDetails(),details.getMyFrame(),details.getMyGarage(),details.getMystryMan());

                } else {

                    binding.txtNoUserFound.setText("No Live Users");
                }
            }else {
                Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void setAdapter(List<LiveUserModel.Detail> details, String myFrame, String myGarage, String mystryMan) {
        PopularLiveAdapter popularLiveAdapter = new PopularLiveAdapter(requireContext(), details, detail -> {
            App.getSingletone().setLiveType("");

            App.getSingleton().setGiftCheck("2");
            Intent intent = new Intent(requireActivity(), CallActivity.class);
            intent.putExtra("channelName", detail.getChannelName());
            intent.putExtra("userId", detail.getUserId());
            intent.putExtra("liveType", "multiLive");
            intent.putExtra("liveStatus", "host");
            intent.putExtra("token", detail.getToken());
            intent.putExtra("name", detail.getName());
            intent.putExtra("starCount", detail.getCount());
            intent.putExtra("coin", detail.getCoin());
            intent.putExtra("liveHostId", detail.getId());
            intent.putExtra("followCount", detail.getFollowerCount());
            intent.putExtra("image", detail.getImage());
            intent.putExtra("frame",detail.getMyFrame());
            intent.putExtra("myFrame",myFrame);
            intent.putExtra("myGarage",myGarage);
            intent.putExtra("mystryMan",mystryMan);
            intent.putExtra("status", "1");
            startActivity(intent);
        });
        binding.popularRecyclerView.setAdapter(popularLiveAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        details = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);

    }
}