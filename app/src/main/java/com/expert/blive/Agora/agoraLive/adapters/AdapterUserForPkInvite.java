package com.expert.blive.Agora.agoraLive.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.GrtFriendsLiveDetails;
import com.expert.blive.R;

import java.util.List;

public class AdapterUserForPkInvite extends RecyclerView.Adapter<AdapterUserForPkInvite.ViewHolder> {

    Context context;
    List<GrtFriendsLiveDetails.Deatil> list;
    Select select;


    public AdapterUserForPkInvite(Context context, List<GrtFriendsLiveDetails.Deatil> list, Select select) {
        this.context = context;
        this.list = list;
        this.select = select;
    }

    public interface Select {
        void onClick(int pos);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_my_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


            Glide.with(context).load(list.get(position).getPosterImage()).placeholder(R.drawable.app_logo).into(holder.iv_hostImage);


        holder.tv_hostName.setText(list.get(position).getName());
        holder.tv_followers.setText("Followers: "+list.get(position).getFollowerCount());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_hostName,tv_followers;
        private ImageView iv_hostImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_hostName=itemView.findViewById(R.id.tv_hostName);
            tv_followers=itemView.findViewById(R.id.tv_followers);
            iv_hostImage=itemView.findViewById(R.id.iv_hostImage);


            itemView.findViewById(R.id.btn_invitePk).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    select.onClick(getLayoutPosition());
                }
            });


        }
    }


    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }
}

