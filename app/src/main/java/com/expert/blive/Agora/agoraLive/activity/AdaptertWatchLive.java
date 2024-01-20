package com.expert.blive.Agora.agoraLive.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSetUserViewer;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptertWatchLive extends RecyclerView.Adapter<AdaptertWatchLive.ViewHolder> {
    List<ModelSetUserViewer> listviewers;
    UserInfo userInfo;

    public AdaptertWatchLive(List<ModelSetUserViewer> listviewers,UserInfo userInfo) {
        this.listviewers = listviewers;
        this.userInfo = userInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_watch,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.img_following).load(listviewers.get(position).getImage()).placeholder(R.drawable.ic_profile_user).into(holder.img_following);
        holder.tv_following_name.setText(listviewers.get(position).getName());
        holder.tv_following_username.setText(listviewers.get(position).getLevel());

        holder.itemView.setOnClickListener(view -> {
            userInfo.sendData(listviewers.get(position));

        });
    }

    @Override
    public int getItemCount() {
        return listviewers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img_following;
        TextView tv_following_username,tv_following_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_following = itemView.findViewById(R.id.img_following);
            tv_following_username = itemView.findViewById(R.id.tv_following_username);
            tv_following_name = itemView.findViewById(R.id.tv_following_name);
        }
    }

    public interface UserInfo{
        void sendData(ModelSetUserViewer listviewers);
    }
}
