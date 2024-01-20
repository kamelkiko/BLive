package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.Family.FamilyDetail;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FanclubRequestAdapter extends RecyclerView.Adapter<FanclubRequestAdapter.holder> {

    List<FamilyDetail>list;
    Context context;
    Callback callback;

    public FanclubRequestAdapter(List<FamilyDetail> list, Context context,Callback callback) {

        this.list = list;
        this.context = context;
        this.callback = callback;
    }
    public interface Callback{
        void acceptRequest(FamilyDetail detail);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        holder.inviteButton.setVisibility(View.GONE);
        holder.btnAccept.setVisibility(View.VISIBLE);
        holder.hour_name.setText(list.get(position).getUserdetails().getName());
        Glide.with(context).load(list.get(position).getUserdetails().getImage()).into(holder.profile_hour);

        holder.btnAccept.setOnClickListener(view -> {
            callback.acceptRequest(list.get(position));
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        private CircleImageView profile_hour;
        private TextView hour_name,lv_txt,inviteTV;
        private RelativeLayout inviteButton,btnAccept;
        public holder(@NonNull View itemView) {
            super(itemView);
            profile_hour=itemView.findViewById(R.id.profile_hour);
            hour_name=itemView.findViewById(R.id.hour_name);
            lv_txt=itemView.findViewById(R.id.lv_txt);
            inviteButton=itemView.findViewById(R.id.inviteButton);
            inviteTV=itemView.findViewById(R.id.inviteTV);
            btnAccept=itemView.findViewById(R.id.btnAccept);
        }

    }

}
