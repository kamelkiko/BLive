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

public class InvitesAdapter extends RecyclerView.Adapter<InvitesAdapter.holder> {
    List<FamilyDetail> details;
    Context context;

    public InvitesAdapter(List<FamilyDetail> details, Context context) {

        this.details = details;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.designfamily,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.inviteButton.setVisibility(View.GONE);
        holder.Lv_lay.setVisibility(View.GONE);
        holder.btnAccept.setVisibility(View.VISIBLE);

        holder.hour_name.setText(details.get(position).getFamilyId().getFamilyName());
        Glide.with(context).load(details.get(position).getFamilyId().getFamilyImage()).into(holder.profile_hour);
    }

    @Override
    public int getItemCount() {

        return details.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        private CircleImageView profile_hour;
        private TextView hour_name,lv_txt,inviteTV,confirmTV;
        private RelativeLayout inviteButton,btnAccept,Lv_lay;
        public holder(@NonNull View itemView) {
            super(itemView);
            profile_hour=itemView.findViewById(R.id.profile_hour);
            hour_name=itemView.findViewById(R.id.hour_name);
            lv_txt=itemView.findViewById(R.id.lv_txt);
            inviteButton=itemView.findViewById(R.id.inviteButton);
            inviteTV=itemView.findViewById(R.id.inviteTV);
            btnAccept=itemView.findViewById(R.id.btnAccept);
            confirmTV=itemView.findViewById(R.id.confirmTV);
            Lv_lay=itemView.findViewById(R.id.Lv_lay);
        }

    }

}
