package com.expert.blive.ExtraFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.expert.blive.LiveHistoryFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.AdapterMonthlyHistory;
import com.expert.blive.ModelClass.MonthlyHistory;
import com.expert.blive.R;
import com.expert.blive.databinding.FragmentMothlyLiveHistoryBinding;

import java.util.List;


public class MothlyLiveHistoryFragment extends Fragment {

    private FragmentMothlyLiveHistoryBinding binding;
    private MvvmViewModelClass viewModelClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        binding = FragmentMothlyLiveHistoryBinding.inflate(getLayoutInflater(), container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMontlyData();
        binding.back.setOnClickListener(v -> requireActivity().onBackPressed());


    }

    private void getMontlyData() {
        viewModelClass.userAllStats(requireActivity(), "49").observe(requireActivity(), monthlyHistory -> {
            if (monthlyHistory.getStatus() == 1) {
                binding.noPastLive.setVisibility(View.GONE);

                setDataAdapter(monthlyHistory.getDetails());
            } else {

                binding.noPastLive.setVisibility(View.VISIBLE);
            }
        });


    }

    private void setDataAdapter(List<MonthlyHistory.Detail> details) {
        AdapterMonthlyHistory adapterMonthlyHistory = new AdapterMonthlyHistory(details, monthly -> {

            Bundle bundle = new Bundle();
            Fragment live = new LiveHistoryFragment();
            bundle.putSerializable("data", monthly);
            live.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, live).addToBackStack(null).commit();

        });
        binding.recyMothly.setAdapter(adapterMonthlyHistory);
    }
}