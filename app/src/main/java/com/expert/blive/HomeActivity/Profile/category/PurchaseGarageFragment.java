package com.expert.blive.HomeActivity.Profile.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.expert.blive.Adapter.PurchaseGarageAdapter;
import com.expert.blive.ModelClass.Store.RootFrames;
import com.expert.blive.R;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.utils.CommonUtils;

import java.util.List;

public class PurchaseGarageFragment extends Fragment implements PurchaseGarageAdapter.Callback {

    private RecyclerView frame_recycler;
    private PurchaseGarageAdapter purchaseGarageAdapter;
    private MvvmViewModelClass viewModelClass;
    private List<RootFrames.Detail> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_purchase_garage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        finds(view);
        hitApi();
    }

    private void hitApi() {

        viewModelClass.getUserGarage(CommonUtils.getUserId()).observe(requireActivity(), new Observer<RootFrames>() {

            @Override
            public void onChanged(RootFrames response) {
                if (response !=null){
                    if (response.getStatus()==1){

                        list = response.getDetails();
                        purchaseGarageAdapter = new PurchaseGarageAdapter(list,requireContext(),PurchaseGarageFragment.this);
                        frame_recycler.setAdapter(purchaseGarageAdapter);

                    }else {
                        Toast.makeText(requireContext(), ""+response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void finds(View view) {
        frame_recycler = view.findViewById(R.id.frame_recycler);

    }

    @Override
    public void setGarage(RootFrames.Detail detail, TextView textView) {

        String frameId = detail.getId();
        String type = detail.getType();
        Toast.makeText(requireContext(), "type :-  "+type, Toast.LENGTH_SHORT).show();

        viewModelClass.setGarage(CommonUtils.getUserId(),frameId,type).observe(requireActivity(), new Observer<RootFrames>() {
            @Override
            public void onChanged(RootFrames response) {
                if (response !=null){
                    if (response.getStatus()==1){
                        textView.setText("applied");
                        hitApi();
                    }else {
                        textView.setText("apply");
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}