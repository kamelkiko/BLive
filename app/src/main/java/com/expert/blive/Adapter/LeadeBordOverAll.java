package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.TopGifterModel;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LeadeBordOverAll extends RecyclerView.Adapter<LeadeBordOverAll.ViewHolder> {

private List<TopGifterModel.Detail> amount;

public LeadeBordOverAll(List<TopGifterModel.Detail> amount) {
        this.amount = amount;

        }

@NonNull
@Override
public LeadeBordOverAll.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item_list, parent, false));
        }

@Override
public void onBindViewHolder(@NonNull LeadeBordOverAll.ViewHolder holder, int position) {
//        if (position == 0) {
//
//            holder.img_medal.setColorFilter(context.getResources().getColor(R.color.darkyellow));
//
//
//        }
//        if (position == 1) {
//            holder.img_medal.setColorFilter(context.getResources().getColor(R.color.dark_gray));
//
//        }
//
//        if (position == 2) {
//            holder.img_medal.setColorFilter(context.getResources().getColor(R.color.platinum));
//
//
//        }
////        if (position > 2) {
//            holder.img_medal.setColorFilter(context.getResources().getColor(R.color.white));
//
//        }
//        holder.userIdText.setText(amount.get(position).getUsername());
        if (amount.get(position).getUserInfo().getName().equalsIgnoreCase("")) {
        holder.txtUsername.setText(amount.get(position).getUserInfo().getUsername());

        } else {
        holder.txtUsername.setText(amount.get(position).getUserInfo().getName());

        }
        holder.txtNumber.setText(String.valueOf(position + 4));
        Glide.with(holder.img_contributer.getContext()).load(amount.get(position).getUserInfo().getImage()).placeholder(R.drawable.app_logo).into(holder.img_contributer);
        holder.txtCoins.setText(String.valueOf(amount.get(position).getCoin()));

        }

@Override
public int getItemCount() {
        return amount.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView userIdText, txtUsername, txtCoins, txtNumber;
    CircleImageView img_contributer;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

//            userIdText = itemView.findViewById(R.id.userIdText);
        txtUsername = itemView.findViewById(R.id.txtName);
        img_contributer = itemView.findViewById(R.id.personImge);
        txtNumber = itemView.findViewById(R.id.txtNumber);
        txtCoins = itemView.findViewById(R.id.txtCoin);
    }
}
}


