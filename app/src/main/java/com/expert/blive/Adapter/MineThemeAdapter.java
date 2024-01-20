package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.MyWallPaper;
import com.expert.blive.databinding.MineThemeLayoutBinding;

import java.util.List;

public class MineThemeAdapter extends RecyclerView.Adapter<MineThemeAdapter.ViewHolder> {

    List<MyWallPaper.Detail> details;
    ApplyTheme applyTheme;

    public MineThemeAdapter(List<MyWallPaper.Detail> details, ApplyTheme applyTheme) {
        this.details = details;
        this.applyTheme = applyTheme;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MineThemeLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (details.get(position).getWallpaperExpire().equalsIgnoreCase("0")){
            holder.binding.rlExpired.setVisibility(View.GONE);
        }else {
            holder.binding.rlExpired.setVisibility(View.VISIBLE);

        }

        Glide.with(holder.binding.luckyRoundIV.getContext()).load(details.get(position).getImage()).into(holder.binding.luckyRoundIV);
//        holder.binding.luckyRoundIV.


        holder.itemView.setOnClickListener(v -> {
            applyTheme.applyAudioTheme(details.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MineThemeLayoutBinding binding;
        public ViewHolder(@NonNull  MineThemeLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public interface ApplyTheme{

        void applyAudioTheme(MyWallPaper.Detail id);
    }
}
