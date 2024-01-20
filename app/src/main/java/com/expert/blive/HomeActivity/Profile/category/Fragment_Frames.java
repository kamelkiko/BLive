package com.expert.blive.HomeActivity.Profile.category;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.expert.blive.Adapter.MallFramesAdapter;
import com.expert.blive.ModelClass.Store.RootFrames;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.CommonUtils;
import com.opensource.svgaplayer.SVGAImageView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_Frames extends Fragment implements MallFramesAdapter.Callback {

    private MvvmViewModelClass viewModelClass;
    RecyclerView rvFrames;
    List<RootFrames.Detail> list = new ArrayList<>();
    View view;
    SwipeRefreshLayout refresh;
    RootFrames details;
    String frameId;
    TextView noItemFound ;
    MallFramesAdapter framesAdapter;

    public Fragment_Frames() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment__frames, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        findId(view);
        apiFrames();
        onClick(view);
    }

    private void apiFrames() {

        viewModelClass.getFrames(requireActivity(), CommonUtils.getUserId()).observe(requireActivity(), new Observer<RootFrames>() {
            @Override
            public void onChanged(RootFrames rootFrames) {
                if (rootFrames != null) {
                    if (rootFrames.getSuccess().equals(1)) {
                        noItemFound.setVisibility(View.GONE);
                        list = rootFrames.getDetails();

                        framesAdapter = new MallFramesAdapter(list, requireContext(), Fragment_Frames.this);
                        rvFrames.setAdapter(framesAdapter);
                    }
                } else {
                    noItemFound.setVisibility(View.VISIBLE);

                    Toast.makeText(requireActivity(), "Root is null", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void onClick(View view) {

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh.setRefreshing(true);
                apiFrames();
            }
        });
    }
    private void findId(View view) {
        rvFrames = view.findViewById(R.id.frame_recycler);
        refresh = view.findViewById(R.id.refresh);
        noItemFound = view.findViewById(R.id.noItemsFound);


    }

    @Override
    public void TryFrames(RootFrames.Detail detail) {
        String frame = detail.getImage();

        Dialog viewDetails_box = new Dialog(requireContext());
        viewDetails_box.setContentView(R.layout.image_dialog);
        viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
        Window window = viewDetails_box.getWindow();
        viewDetails_box.setCanceledOnTouchOutside(true);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        viewDetails_box.show();
        viewDetails_box.setCanceledOnTouchOutside(true);
        SVGAImageView svgaImage = viewDetails_box.findViewById(R.id.frames);
        CircleImageView zoomeImage = viewDetails_box.findViewById(R.id.img_profile);
        ImageView closeBtn = viewDetails_box.findViewById(R.id.close_BTN);

        Glide.with(requireContext()).load(CommonUtils.getImage()).error(R.drawable.user_one).into(zoomeImage);
        CommonUtils.setAnimation(requireContext(), frame, svgaImage);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDetails_box.dismiss();
            }
        });

    }

    @Override
    public void purchaseFrame(RootFrames.Detail detailPurchase, TextView buy) {


        String frameId = detailPurchase.getId();
        viewModelClass.purchaseFrames(requireActivity(), CommonUtils.getUserId(), frameId).observe(requireActivity(), new Observer<RootFrames>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(RootFrames rootPurchaseFrame) {
                if (rootPurchaseFrame != null) {
                    setDialog(rootPurchaseFrame.getMessage());
                    if (rootPurchaseFrame.getSuccess().equals(1)) {
                        buy.setText("Purchased");
                        Toast.makeText(requireActivity(), "" + rootPurchaseFrame.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        buy.setText("Purchase");
                        Toast.makeText(requireActivity(), "" + rootPurchaseFrame.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireActivity(), "root is null", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setDialog(String message) {

        if (message.equalsIgnoreCase("Insufficient Balance")) {
            Dialog viewDetails_box = new Dialog(requireContext());
            viewDetails_box.setContentView(R.layout.dialog_are_you_sure);
            viewDetails_box.getWindow().setBackgroundDrawable(new ColorDrawable());
            Window window = viewDetails_box.getWindow();
            viewDetails_box.setCanceledOnTouchOutside(true);
            window.setGravity(Gravity.CENTER);
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
            window.setAttributes(wlp);
            viewDetails_box.show();
            viewDetails_box.setCanceledOnTouchOutside(true);
        } else {

        }

    }
    @Override
    public void onResume() {
        super.onResume();

        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }
}
