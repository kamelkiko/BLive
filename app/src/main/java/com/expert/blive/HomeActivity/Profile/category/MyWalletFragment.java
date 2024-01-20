package com.expert.blive.HomeActivity.Profile.category;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.WalletAdapter;
import com.expert.blive.ModelClass.TotalCoinModal;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;


public class MyWalletFragment extends Fragment {

    private RecyclerView wallet_recycler;
    private TextView rlDiamonds;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_wallet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        finds(view);
        click(view);
        getCoin();

        wallet_recycler.setAdapter(new WalletAdapter());
    }

    private void click(View view) {
        view.findViewById(R.id.back).setOnClickListener(view1 -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit();
        });
    }

    private void getCoin() {

        new MvvmViewModelClass().getCoinRootLiveData(CommonUtils.getUserId()).observe(requireActivity(), new Observer<TotalCoinModal>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(TotalCoinModal getCoinRoot) {

                if (getCoinRoot != null) {

                    if (getCoinRoot.getSuccess().equalsIgnoreCase("1")) {

                        if (getCoinRoot.getDetails() == null) {
//                            expCoin.setText("0.00");

                            rlDiamonds.setText("0.00");
                        } else {
//                            recivedCoin.setText(CommonUtils.prettyCount(Long.parseLong(getCoinRoot.getDetails().getMyCoins())));
                            rlDiamonds.setText(CommonUtils.prettyCount(Long.parseLong(getCoinRoot.getDetails().getPurchasedCoin())));

                        }


                    } else {
//                        expCoin.setText("0");
//                        tx58.setText("0");
                        rlDiamonds.setText("0");

                    }


                }

            }
        });

    }
    private void finds(View view) {

        wallet_recycler=view.findViewById(R.id.wallet_recycler);
        rlDiamonds=view.findViewById(R.id.rlDiamonds);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
    }

}