package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.FollowingDataModel;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.holder> {

    List<FollowingDataModel.Detail> details;
    Context context;
    Callback callback;

    public ShareAdapter(List<FollowingDataModel.Detail> details, Context context, Callback callback) {

        this.details = details;
        this.context = context;
        this.callback = callback;
    }
    public interface Callback{
        void sendVideo(FollowingDataModel.Detail detail,TextView textView);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_show,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        holder.like_button.setVisibility(View.GONE);
        holder.sendBT.setVisibility(View.VISIBLE);
        holder.tvName.setText(details.get(position).getName());
        Glide.with(context).load(details.get(position).getImage()).into(holder.usrImg);

        holder.sendBT.setOnClickListener(view -> {
            callback.sendVideo(details.get(position),holder.sendTV);
        });
    }

    @Override
    public int getItemCount() {

        return details.size();
    }

    public class holder extends RecyclerView.ViewHolder {

        CircleImageView usrImg;
        TextView tvName,sendTV;
        RelativeLayout like_button;
        CardView sendBT;
        public holder(@NonNull View itemView) {
            super(itemView);
            usrImg=itemView.findViewById(R.id.usrImg);
            tvName=itemView.findViewById(R.id.tvName);
            like_button=itemView.findViewById(R.id.like_button);
            sendBT=itemView.findViewById(R.id.sendBT);
            sendTV=itemView.findViewById(R.id.sendTV);
        }

    }

}
