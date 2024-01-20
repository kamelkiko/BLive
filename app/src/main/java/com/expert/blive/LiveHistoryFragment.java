package com.expert.blive;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.expert.blive.ExtraFragments.MothlyLiveHistoryFragment;
import com.expert.blive.ModelClass.MonthlyHistory;
import com.expert.blive.ModelClass.MothlyModel;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.CommonUtils;
import com.expert.blive.databinding.FragmentLiveHistoryBinding;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LiveHistoryFragment extends Fragment {

    FragmentLiveHistoryBinding binding;
    MonthlyHistory.Detail monthly;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLiveHistoryBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.back.setOnClickListener(v -> requireActivity().onBackPressed());

        if (getArguments()==null){
            getCurrentMonthData();
            binding.txtMonthlyRecord.setVisibility(View.VISIBLE);
        }else {
            binding.txtMonthlyRecord.setVisibility(View.GONE);
            monthly = (MonthlyHistory.Detail)getArguments().getSerializable("data");
            setDataMine(monthly);
        }

        binding.txtMonthlyRecord.setOnClickListener(v -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MothlyLiveHistoryFragment ()).addToBackStack(null).commit());

    }

    @SuppressLint("SetTextI18n")
    private void setDataMine(MonthlyHistory.Detail monthly) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj;
        try {
            dateObj = curFormater.parse(monthly.getDateFrom());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat postFormater = new SimpleDateFormat("MMMM, yyyy");
            assert dateObj != null;
            binding.txtMonth.setText(postFormater.format(dateObj) );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        binding.txtTotal.setText(monthly.getMonthlyCoins());
        binding.erningRecord.setText(monthly.getMonthlyCoins());
        binding.erningRecord2.setText(monthly.getMonthlyCoins()+" Min");
        binding.erningRecord3.setText(monthly.getLiveDays());
        binding.zerroMinus3.setText(monthly.getBonus1());
        binding.zerroMinus.setText(monthly.getCoinExchange());
        binding.zerroMinusBase.setText(monthly.getBasicSalary());

    }

    private void getCurrentMonthData() {

        new MvvmViewModelClass().userStats(CommonUtils.getUserId()).observe(requireActivity(), mothlyModel -> {
            if (mothlyModel.getStatus()==1){

                setData(mothlyModel.getDetails());
            }else {
                requireActivity().onBackPressed();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setData(MothlyModel.Details details) {
        Date d = new Date();
        CharSequence s  = DateFormat.format("MMMM, yyyy ", d.getTime());

            binding.txtMonth.setText(s);

        binding.txtTotal.setText(details.getMonthlyCoins().toString());
        binding.erningRecord.setText(details.getMonthlyCoins().toString());
        binding.erningRecord2.setText(details.getMonthlyLive()+" Min");
        binding.erningRecord3.setText(details.getLiveDays().toString());
        binding.zerroMinus3.setText(details.getBonus().toString());
        binding.zerroMinus.setText(details.getCoinExchange().toString());
        binding.zerroMinusBase.setText(details.getBasicSalary().toString());
    }

    @Override
    public void onResume() {
        super.onResume();

        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
//        requireActivity().findViewById(R.id.img_video).setVisibility(View.GONE);

    }
}