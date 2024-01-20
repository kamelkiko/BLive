package com.expert.blive.HomeActivity.Profile.MyBag;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.AdapterSubscribed;
import com.expert.blive.ModelClass.Store.RootFrames;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;
import com.opensource.svgaplayer.SVGAImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_Subscribed extends Fragment implements AdapterSubscribed.Callback {
    View view;
    AdapterSubscribed adapterSubscribed;
    RecyclerView rvFrames;
    TextView frameExpire;
    List<RootFrames.Detail> list = new ArrayList<>();
    private MvvmViewModelClass viewModelClass;

    public Fragment_Subscribed() {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        view = inflater.inflate(R.layout.fragment__subscribed, container, false);
        findId(view);
        apiGetPurchasedFrames();
        setAdapter(view);



        return view;
    }


    private void findId(View view) {

        rvFrames = view.findViewById(R.id.frame_recycler);
        frameExpire = view.findViewById(R.id.FramesExpiry);
        //refresh = view.findViewById(R.id.refresh);
    }

    private void setAdapter(View view) {

        adapterSubscribed = new AdapterSubscribed(new ArrayList<>(), requireContext(), Fragment_Subscribed.this);
        rvFrames.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    private void apiGetPurchasedFrames() {

        viewModelClass.getPurchaseFrame(requireActivity(), CommonUtils.getUserId()).observe(requireActivity(), new Observer<RootFrames>() {
            @Override
            public void onChanged(RootFrames rootFrames) {
                if (rootFrames != null) {
                    Toast.makeText(requireActivity(), "ok" + list, Toast.LENGTH_SHORT).show();
                    if (rootFrames.getSuccess().equals(1)) {
                        list = rootFrames.getDetails();
                        frameExpire.setVisibility(View.GONE);
                        adapterSubscribed.loadData(list);


                    }
                } else {
                    frameExpire.setVisibility(View.VISIBLE);
                    Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                }
                rvFrames.setAdapter(adapterSubscribed);
            }
        });
    }

    @Override
    public void TryFrame(RootFrames.Detail detail) {

        Dialog viewDetails_box = new Dialog(view.getContext());
        viewDetails_box.setContentView(R.layout.image_dialog);
        viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
        Window window = viewDetails_box.getWindow();
        viewDetails_box.setCanceledOnTouchOutside(true);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        viewDetails_box.show();
        viewDetails_box.setCanceledOnTouchOutside(true);

        CircleImageView zoomeImage = viewDetails_box.findViewById(R.id.img_profile);
        Glide.with(requireContext()).load(CommonUtils.getImage())
                .error(R.drawable.user_one).into(zoomeImage);

        SVGAImageView svgaImage = viewDetails_box.findViewById(R.id.frames);
        CommonUtils.setAnimation(requireContext(), detail.getFrameIMage(), svgaImage);
        ImageView closeBtn = viewDetails_box.findViewById(R.id.close_BTN);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDetails_box.dismiss();
            }
        });
    }

    @Override
    public void Frame(RootFrames.Detail detail, TextView buy) {

        viewModelClass.appliedFrames(requireActivity(), CommonUtils.getUserId(), detail.getFrameId())
                .observe(requireActivity(), new Observer<RootFrames>() {
                    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
                    @Override
                    public void onChanged(RootFrames rootFrames) {
                        if (rootFrames != null) {

                            if (rootFrames.getSuccess().equals(1)) {
//                                Toast.makeText(requireActivity(), "" + rootFrames.getMessage(), Toast.LENGTH_SHORT).show();
                                buy.setText("Applied");
                                apiGetPurchasedFrames();
                            } else {
                                buy.setText("Apply");
                            }

                        } else {
                            Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                        }
                        //  adapterSubscribed.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onResume() {
        super.onResume();

        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }

}