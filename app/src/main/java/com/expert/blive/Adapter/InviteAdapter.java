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

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.holder> {

    List<FamilyDetail> list;
    Context context;
    Callback callback;

    public InviteAdapter(List<FamilyDetail> list, Context context,Callback callback) {

        this.list = list;
        this.context = context;
        this.callback = callback;
    }
    public interface Callback{
       void inviteUser(FamilyDetail detail,TextView textView);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        if (list.get(position).invite_status){
            holder.inviteTV.setText("Invited");
        }else {
            holder.inviteTV.setText("Invite");
        }


        holder.hour_name.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getImage()).into(holder.profile_hour);
        holder.inviteButton.setOnClickListener(view -> {
            callback.inviteUser(list.get(position),holder.inviteTV);
        });

    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    public class holder extends RecyclerView.ViewHolder {

        private CircleImageView profile_hour;
        private TextView hour_name,lv_txt,inviteTV;
        private RelativeLayout inviteButton;

        public holder(@NonNull View itemView) {
            super(itemView);
            profile_hour=itemView.findViewById(R.id.profile_hour);
            hour_name=itemView.findViewById(R.id.hour_name);
            lv_txt=itemView.findViewById(R.id.lv_txt);
            inviteButton=itemView.findViewById(R.id.inviteButton);
            inviteTV=itemView.findViewById(R.id.inviteTV);
        }

    }

}
