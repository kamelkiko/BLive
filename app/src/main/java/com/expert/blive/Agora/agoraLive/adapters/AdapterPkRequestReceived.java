package com.expert.blive.Agora.agoraLive.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.Agora.agoraLive.models.ModelPkRequest;
import com.expert.blive.R;

import java.util.List;


public class AdapterPkRequestReceived extends RecyclerView.Adapter<AdapterPkRequestReceived.ViewHolder> {

    Context context;
    List<ModelPkRequest> list;
    Select select;


    public AdapterPkRequestReceived(Context context, List<ModelPkRequest> list, Select select) {
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
        if(list.get(position).getRequestStatus().equalsIgnoreCase("")){
            if (!list.get(position).getImage().equalsIgnoreCase("")) {
                Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.app_logo).into(holder.iv_hostImage);
            }

            holder.tv_hostName.setText(list.get(position).getMyName());
            holder.tv_timeLimit.setText("Time Limit- "+list.get(position).getTimeLimit()+" Min");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_hostName, tv_timeLimit;
        private ImageView iv_hostImage;
        private Button btn_invitePk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_hostName = itemView.findViewById(R.id.tv_hostName);
            tv_timeLimit = itemView.findViewById(R.id.tv_followers);
            iv_hostImage = itemView.findViewById(R.id.iv_hostImage);




            btn_invitePk= itemView.findViewById(R.id.btn_invitePk);
            btn_invitePk.setText("Accept");

            btn_invitePk.setOnClickListener(new View.OnClickListener() {
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

