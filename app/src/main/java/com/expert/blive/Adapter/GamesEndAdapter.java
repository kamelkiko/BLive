package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.R;

import org.jetbrains.annotations.NotNull;

public class GamesEndAdapter extends RecyclerView.Adapter<GamesEndAdapter.ViewHolder> {
    @NonNull
    @NotNull
    @Override
    public GamesEndAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.game_end_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GamesEndAdapter.ViewHolder holder, int position) {

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
