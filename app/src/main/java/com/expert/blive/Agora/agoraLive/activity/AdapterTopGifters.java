package com.expert.blive.Agora.agoraLive.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSendGift;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTopGifters extends RecyclerView.Adapter<AdapterTopGifters.ViewHolder> {
    List<ModelSendGift> list;
    private UserSelection userSelection;
    int size = 0;


    public AdapterTopGifters(List<ModelSendGift> list, UserSelection userSelection) {
        this.list = list;
        this.userSelection = userSelection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_gifters, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (list.size() == 1 && position == 0) {
            Glide.with(holder.img_viewer).load(list.get(position).getUserImage()).placeholder(R.drawable.app_logo).into(holder.img_viewer);
            holder.crown3.setColorFilter(R.color.yellow);
        }
        if (list.size() == 2 && position == 1) {
            Glide.with(holder.img_viewer).load(list.get(position).getUserImage()).placeholder(R.drawable.app_logo).into(holder.img_viewer);
            holder.crown3.setColorFilter(R.color.dark_grey);
        }

        if (list.size() == 3 && position == 2) {
            Glide.with(holder.img_viewer).load(list.get(position).getUserImage()).placeholder(R.drawable.app_logo).into(holder.img_viewer);
            holder.crown3.setColorFilter(R.color.platinum);
        }
        holder.img_viewer.setOnClickListener(view -> {
            userSelection.userSelection(list.get(position).getUserId());

        });


    }

    @Override
    public int getItemCount() {


        if (list.size() > 3) {

            size = 3;

        } else {

            size = list.size();

        }

        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img_viewer;
        AppCompatImageView crown3;
        TextView txtCoin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_viewer = itemView.findViewById(R.id.personImge3);
            crown3 = itemView.findViewById(R.id.crown3);
            txtCoin = itemView.findViewById(R.id.txtCoin);

        }
    }

    public interface UserSelection {

        void userSelection(String userId);

    }
}
