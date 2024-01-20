package com.expert.blive.HomeActivity.Profile.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.CoinHistoryAdaptor;
import com.expert.blive.ModelClass.CoinHistoryRoot;
import com.expert.blive.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CoinHistoryFragment extends Fragment {

    private RecyclerView history_holder;
    private CoinHistoryAdaptor coinHistoryAdaptor;
    private TextView receive,send,txtNoSendMoney;
    private MvvmViewModelClass mvvmViewModelClass;
    public static String userId,typeHistory;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coin_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findId(view);

        mvvmViewModelClass = new MvvmViewModelClass();

        history_holder.setLayoutManager(new LinearLayoutManager(getContext()));

        coinHistoryAdaptor =new CoinHistoryAdaptor(new ArrayList<>(),getContext());

        history_holder.setAdapter(coinHistoryAdaptor);

        getSendHistory();

        onClick(view);


    }

    private void onClick(@NotNull View view) {

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNoSendMoney.setVisibility(View.GONE);

                mvvmViewModelClass.coinReceiveHistoryRootLiveData(userId).observe(requireActivity(), new Observer<CoinHistoryRoot>() {
                    @Override
                    public void onChanged(CoinHistoryRoot coinHistoryRoot) {


                        if (coinHistoryRoot!=null){

                            if (coinHistoryRoot.getStatus().equalsIgnoreCase("1")){
                                typeHistory ="2";

                                coinHistoryAdaptor.loadData(coinHistoryRoot.getDetails());
                            }else {
                                txtNoSendMoney.setVisibility(View.VISIBLE);
                                txtNoSendMoney.setText("Money Not Received ");
                            }




                        }

                    }
                });


            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getSendHistory();
            }
        });

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit();
            }
        });
    }

    private void getSendHistory() {
        txtNoSendMoney.setVisibility(View.GONE);

        mvvmViewModelClass.coinSendHistoryRootLiveData(userId).observe(requireActivity(), new Observer<CoinHistoryRoot>() {
            @Override
            public void onChanged(CoinHistoryRoot coinHistoryRoot) {


                if (coinHistoryRoot!=null){



                    if (coinHistoryRoot.getStatus().equalsIgnoreCase("1")){
                        typeHistory = "1";

                        coinHistoryAdaptor.loadData(coinHistoryRoot.getDetails());
                    }else {
                        txtNoSendMoney.setVisibility(View.VISIBLE);
                    }



                }

            }
        });

    }

    private void findId(View view) {

        history_holder = view.findViewById(R.id.history_holder);
        txtNoSendMoney = view.findViewById(R.id.txtNoSendMoney);
        receive = view.findViewById(R.id.receive);
        send = view.findViewById(R.id.send);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requireActivity().onBackPressed();

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.INVISIBLE);
    }
}