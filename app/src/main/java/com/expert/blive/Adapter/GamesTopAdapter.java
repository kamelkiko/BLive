package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.R;

import org.jetbrains.annotations.NotNull;

public class GamesTopAdapter extends RecyclerView.Adapter<GamesTopAdapter.ViewHolder> {
    @NonNull
    @NotNull
    @Override
    public GamesTopAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.games_top_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GamesTopAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
