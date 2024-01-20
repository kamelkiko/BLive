package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.R;

import org.jetbrains.annotations.NotNull;

public class SubscribedAdapter extends RecyclerView.Adapter<SubscribedAdapter.ViewHolder> {
    @NonNull
    @NotNull
    @Override
    public SubscribedAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_rv_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SubscribedAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
