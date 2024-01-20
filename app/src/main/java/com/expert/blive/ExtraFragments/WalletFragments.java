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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.ModelClass.ExchangeCoin;
import com.expert.blive.ModelClass.TotalCoinModal;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import java.util.HashMap;

public class WalletFragments extends Fragment {

    private MvvmViewModelClass viewModelClass;
    private TextView userDiamond;
    private LinearLayout llOneThousand,llTwoThousand,llThreeThousand,llFourThousand;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_wallet_fragments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findIds(view);
        getCoin();


        clicks(view);




    }

    private void clicks(View view) {
        view.findViewById(R.id.walletTV).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profileFramlayout, new PublicFragment()).addToBackStack(null).commit());
        view.findViewById(R.id.back_wallet).setOnClickListener(view12 -> requireActivity().onBackPressed());
        llThreeThousand.setOnClickListener(v -> hitApi("1"));
        llTwoThousand.setOnClickListener(v -> hitApi("2"));
        llOneThousand.setOnClickListener(v -> hitApi("3"));
        llFourThousand.setOnClickListener(v -> hitApi("4"));
    }

    private void hitApi(String s) {

        HashMap<String ,String> data = new HashMap<>();

        data.put("type",s);
        data.put("userId",CommonUtils.getUserId());

        viewModelClass.exchangeCoins(requireActivity(),data).observe(requireActivity(), exchangeCoin -> {
            if (exchangeCoin.getStatus()==1){
                if (exchangeCoin.getDetails().getHostStatus().equalsIgnoreCase("2")){
                    userDiamond.setText(exchangeCoin.getDetails().getMonthlyCoins());
                }else {
                    userDiamond.setText(exchangeCoin.getDetails().getCoin());
                }
            }
        });
    }

    private void findIds(View view) {
        userDiamond = view.findViewById(R.id.userDiamond);
        llThreeThousand = view.findViewById(R.id.llThreeThousand);
        llTwoThousand = view.findViewById(R.id.llTwoThousand);
        llOneThousand = view.findViewById(R.id.llOneThousand);
        llFourThousand = view.findViewById(R.id.llFourThousand);
    }

    private void getCoin() {

       viewModelClass.getCoinRootLiveData(CommonUtils.getUserId()).observe(requireActivity(), getCoinRoot -> {
           if (getCoinRoot != null) {
               if (getCoinRoot.getSuccess().equalsIgnoreCase("1")) {
                   if (getCoinRoot.getDetails() == null) {
                       userDiamond.setText("0");
                   } else {
                       userDiamond.setText(CommonUtils.prettyCount(Long.parseLong(getCoinRoot.getDetails().getPurchasedCoin())));
                   }
               } else {
                   userDiamond.setText("0");
               }
           }
       });

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }
}