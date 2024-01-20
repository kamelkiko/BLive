package com.expert.blive.Agora.agoraLive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.Agora.agoraLive.models.ModelPrivateMessagePlayer;
import com.bumptech.glide.Glide;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPrivateMessagesPlayer extends RecyclerView.Adapter<AdapterPrivateMessagesPlayer.ViewHolder> {
    Context context;
    List<ModelPrivateMessagePlayer> list;

    public AdapterPrivateMessagesPlayer(Context context, List<ModelPrivateMessagePlayer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_private_messages, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.get(position).isMessageFromLive()) {
            setMessageFromLive(holder, position);
        } else {
            setMyMessage(holder, position);
        }
    }

    private void setMyMessage(ViewHolder holder, int position) {
        holder.myRL.setVisibility(View.VISIBLE);
        holder.receiverRL.setVisibility(View.GONE);
        holder.myMsg.setText(list.get(position).getMessage());
        Glide.with(context).load(list.get(position).getImage()).into(holder.my_Img);
    }

    private void setMessageFromLive(ViewHolder holder, int position) {
        holder.myRL.setVisibility(View.GONE);
        holder.receiverRL.setVisibility(View.VISIBLE);
        holder.receiverMsg.setText(list.get(position).getMessage());
        Glide.with(context).load(list.get(position).getImage()).into(holder.receiverImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout receiverRL;
        private RelativeLayout myRL;
        private CircleImageView receiverImg, my_Img;
        private TextView myMsg, receiverMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMsg = itemView.findViewById(R.id.receiverMsg);
            myMsg = itemView.findViewById(R.id.myMsg);
            my_Img = itemView.findViewById(R.id.my_Img);
            receiverImg = itemView.findViewById(R.id.receiverImg);
            receiverRL = itemView.findViewById(R.id.receiverRL);
            myRL = itemView.findViewById(R.id.myRL);
        }
    }
}
