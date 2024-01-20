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
import com.expert.blive.ModelClass.Family.Member;
import com.expert.blive.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.holder> {

    List<Member> list = new ArrayList<>();
    Context context;
    Callback callback;

    public MemberAdapter(List<Member> list, Context context,Callback callback) {

        this.list = list;
        this.context = context;
        this.callback = callback;
    }
    public interface Callback{
        void removeMember(Member member);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.inviteButton.setVisibility(View.GONE);
        holder.userPosition.setVisibility(View.GONE);


        Glide.with(holder.profile_hour.getContext()).load(list.get(position).getImage())
                .error(R.drawable.app_logo).into(holder.profile_hour);
        holder.hour_name.setText(list.get(position).getName());
//        holder.lv_txt.setText("Lv : "+list.get(position).getMy_level());


        holder.itemView.setOnClickListener(view -> {
            callback.removeMember(list.get(position));
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {

        private CircleImageView profile_hour;
        private TextView hour_name,lv_txt,inviteTV,positionTV;
        private RelativeLayout inviteButton,userPosition;
        public holder(@NonNull View itemView) {
            super(itemView);
            profile_hour=itemView.findViewById(R.id.profile_hour);
            hour_name=itemView.findViewById(R.id.hour_name);
            lv_txt=itemView.findViewById(R.id.lv_txt);
            inviteTV=itemView.findViewById(R.id.inviteTV);
            inviteButton=itemView.findViewById(R.id.inviteButton);
            userPosition=itemView.findViewById(R.id.userPosition);
            positionTV=itemView.findViewById(R.id.positionTV);
        }

    }

}
