package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.FriendsModel;
import com.expert.blive.R;

import java.util.List;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    List<List<FriendsModel.Deatail>> details;



    public FriendsAdapter(List<List<FriendsModel.Deatail>> details) {
        this.details = details;

    }

    @NonNull
    @Override
    public FriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FriendsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_following, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull FriendsAdapter.ViewHolder holder, int position) {
        Glide.with(holder.user_profile).load(details.get(position).get(0).getImage()).placeholder(R.drawable.app_logo).into(holder.user_profile);
        holder.user_name.setText(details.get(position).get(0).getName());
        holder.user_Id.setText(details.get(position).get(0).getUsername());
        holder.follow_following_text.setText("friends");



    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView user_profile;
        TextView user_name, user_Id, follow_following_text;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
//            follow_following_text = itemView.findViewById(R.id.follow_following_text);
            user_Id = itemView.findViewById(R.id.user_Id);
            user_profile = itemView.findViewById(R.id.user_profile);
        }
    }
}
