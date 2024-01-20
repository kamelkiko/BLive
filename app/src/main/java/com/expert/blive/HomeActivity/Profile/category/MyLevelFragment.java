package com.expert.blive.HomeActivity.Profile.category;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.MyLevelsAdapter;
import com.expert.blive.ModelClass.Levels.LevelRoot;
import com.expert.blive.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class MyLevelFragment extends Fragment {

    private MvvmViewModelClass mvvmViewModelClass;
    private RecyclerView recyclerView;
    private MyLevelsAdapter levelsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_level, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mvvmViewModelClass = new MvvmViewModelClass();
        recyclerView = view.findViewById(R.id.recycler_levels);
        levelsAdapter = new MyLevelsAdapter(new ArrayList<>(), "2", new MyLevelsAdapter.GetCoinData() {
            @Override
            public void getTalent(String image) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.level_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(true);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.CENTER);
                dialog.show();

;
                ImageView level_image = dialog.findViewById(R.id.level_image);

                Glide.with(requireContext()).load(image).into(level_image);







            }
        });
        recyclerView.setAdapter(levelsAdapter);
        getLevels();

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit();
            }
        });
    }

    private void getLevels() {

        mvvmViewModelClass.levelRootLiveData().observe(requireActivity(), new Observer<LevelRoot>() {
            @Override
            public void onChanged(LevelRoot levelRoot) {

                if (levelRoot!=null){

                    levelsAdapter.loadData(levelRoot.getDetails());
                }
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.INVISIBLE);
    }
}