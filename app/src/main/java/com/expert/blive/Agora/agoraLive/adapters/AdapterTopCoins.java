package com.expert.blive.Agora.agoraLive.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSendGift;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTopCoins extends RecyclerView.Adapter<AdapterTopCoins.ViewHolder> {
    List<ModelSendGift> list;
    UserInfo userInfo;


    public AdapterTopCoins(List<ModelSendGift> list, UserInfo userInfo) {
        this.list = list;
        this.userInfo = userInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gifters_top, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterTopCoins.ViewHolder holder, int position) {


        Glide.with(holder.img_viewer).load(list.get(position).getUserImage()).placeholder(R.drawable.app_logo).into(holder.img_viewer);

       holder.txtUserName.setText(list.get(position).getName());
        holder.txtCoin.setText(String.valueOf(list.get(position).getGiftPrice()));

        holder.itemView.setOnClickListener(view -> {
            userInfo.sendData(list.get(position));
        });

    }

    @Override
    public int getItemCount() {


        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img_viewer;
        TextView txtCoin,txtUserName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_viewer = itemView.findViewById(R.id.img_viewer);
            txtCoin = itemView.findViewById(R.id.txtCoin);
            txtUserName = itemView.findViewById(R.id.txtUserName);

        }
    }
    public interface UserInfo{
        void sendData(ModelSendGift listviewers);
    }
}

