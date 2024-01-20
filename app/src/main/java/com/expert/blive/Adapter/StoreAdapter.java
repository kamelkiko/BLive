package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.StoreImages;
import com.expert.blive.databinding.StoreThemeLayoutBinding;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {
    private SeeDesign seeDesign;
    List<StoreImages.Detail> details;


    public StoreAdapter(List<StoreImages.Detail> details, SeeDesign seeDesign) {
        this.details = details;
        this.seeDesign = seeDesign;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(StoreThemeLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Glide.with(holder.binding.luckyRoundIV.getContext()).load(details.get(position).getImage()).into(holder.binding.luckyRoundIV);


        if (details.get(position).getPrice().equalsIgnoreCase("0")) {
            holder.binding.txtCoin.setText("Free");
            holder.binding.coin.setVisibility(View.GONE);
        } else {
            holder.binding.coin.setVisibility(View.VISIBLE);
            holder.binding.txtCoin.setText(details.get(position).getPrice());

        }

        holder.itemView.setOnClickListener(v -> {

            seeDesign.seetheme(details.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        StoreThemeLayoutBinding binding;

        public ViewHolder(@NonNull StoreThemeLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public interface SeeDesign {

        void seetheme(StoreImages.Detail detail);
    }
}
