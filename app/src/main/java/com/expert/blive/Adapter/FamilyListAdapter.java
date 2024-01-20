package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.R;

public class FamilyListAdapter extends RecyclerView.Adapter<FamilyListAdapter.holder> {


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.designfamily,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return 30;
    }

    public class holder extends RecyclerView.ViewHolder {

        public holder(@NonNull View itemView) {

            super(itemView);
        }

    }

}
