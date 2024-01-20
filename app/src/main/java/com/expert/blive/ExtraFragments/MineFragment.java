package com.expert.blive.ExtraFragments;


import android.app.AlertDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.MineThemeAdapter;
import com.expert.blive.Agora.agoraLive.firebase.FirebaseHelper;
import com.expert.blive.ModelClass.MyWallPaper;
import com.expert.blive.R;
import com.expert.blive.databinding.FragmentMineBinding;
import com.expert.blive.utils.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MineFragment extends Fragment {

    private FragmentMineBinding binding;
    private MvvmViewModelClass viewModelClass;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass =new ViewModelProvider(this).get(MvvmViewModelClass.class);
        binding = FragmentMineBinding.inflate(getLayoutInflater(), container, false);
        setData();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        setData();

    }

    private void setData() {

        viewModelClass.myWallpapers(CommonUtils.getUserId()).observe(requireActivity(), myWallPaper -> {
            if (myWallPaper.getStatus()==1){
                setAdapter(myWallPaper.getDetails());
            }else {
                binding.noTheme.setVisibility(View.VISIBLE);
                binding.mineThemeRecycler.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setAdapter(List<MyWallPaper.Detail> details) {
        MineThemeAdapter mineThemeLayoutBinding = new MineThemeAdapter(details, new MineThemeAdapter.ApplyTheme() {
            @Override
            public void applyAudioTheme(MyWallPaper.Detail id) {
                openBottomDialog(id);
            }
        });
        binding.mineThemeRecycler.setAdapter(mineThemeLayoutBinding);
    }

    private void openBottomDialog(MyWallPaper.Detail id) {
        LayoutInflater layoutInflater = LayoutInflater.from(requireContext());
        final View confirmdailog = layoutInflater.inflate(R.layout.multi_request_dailog, null);
        final AlertDialog dailogbox = new AlertDialog.Builder(requireContext()).create();
        dailogbox.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dailogbox.setView(confirmdailog);
        dailogbox.setCanceledOnTouchOutside(false);
        TextView tv_text = confirmdailog.findViewById(R.id.tv_text);
        tv_text.setText("Want To Apply" + id.getWallpaperId());
        ImageView iv_image = confirmdailog.findViewById(R.id.iv_image);
        iv_image.setVisibility(View.VISIBLE);


        Glide.with(iv_image.getContext()).load(id.getImage()).into(iv_image);
        Button acceptBtn = confirmdailog.findViewById(R.id.confirmBooking);
        Button cancel = confirmdailog.findViewById(R.id.btn_editBooking);
        acceptBtn.setEnabled(true);
        acceptBtn.setText(R.string.apply);
        cancel.setText(R.string.cancel);
        dailogbox.show();
        acceptBtn.setOnClickListener(v -> {

            HashMap<String , String > data  = new HashMap<>();
            data.put("wallId",id.getWallpaperId());
            data.put("userId",CommonUtils.getUserId());


            viewModelClass.applyWallpaper(requireActivity(),data).observe(requireActivity(), map -> {
                Toast.makeText(requireContext(), ""+map.get("message"), Toast.LENGTH_SHORT).show();
                FirebaseHelper.setUserAudioBackImage(CommonUtils.getUserId(), CommonUtils.getUserId(),id.getImage());
            });

            dailogbox.dismiss();
        });
        confirmdailog.findViewById(R.id.btn_editBooking).setOnClickListener(view -> dailogbox.dismiss());

    }


}