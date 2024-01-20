package com.expert.blive.Adapter.ChatSection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.ChatInformation.ChatInformation;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.holder> {

    private List<ChatInformation> chatInformationArrayList;
    Callback callback;

    public MessageAdapter(List<ChatInformation> messageDetailList,Callback callback) {
        this.chatInformationArrayList = messageDetailList;
        this.callback = callback;
    }
    public interface Callback{
        void playVideo(ChatInformation chatInformation);
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        if (chatInformationArrayList.get(position).getUserId().equals(CommonUtils.getUserId())) {
            holder.sender.setVisibility(View.VISIBLE);
            holder.receive.setVisibility(View.GONE);

//            Toast.makeText(holder.itemView.getContext(), ""+MessageFragment.userId, Toast.LENGTH_SHORT).show();

            if(chatInformationArrayList.get(position).getMediaType().equalsIgnoreCase("1")){
                holder.sender_message.setVisibility(View.GONE);
                holder.senderCardView.setVisibility(View.VISIBLE);
                Glide.with(holder.videoImage).load(chatInformationArrayList.get(position).getThumbnail()).into(holder.videoImage);

                Toast.makeText(holder.itemView.getContext(), "video id "+chatInformationArrayList.get(position).getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(holder.itemView.getContext(), "Thumbnail1 "+chatInformationArrayList.get(position).getThumbnail(), Toast.LENGTH_SHORT).show();
            }else{
                holder.sender_message.setVisibility(View.VISIBLE);
                holder.sender_message.setText(chatInformationArrayList.get(position).getMessage());
            }


            Glide.with(holder.itemView.getContext()).load(chatInformationArrayList.get(position).getImage()).into(holder.reciverImage);

//            holder.time_1.setText(chatInformationArrayList.get(position).getTime().substring(0,5));


        } else {
            holder.sender.setVisibility(View.GONE);

            holder.receive.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext()).load(chatInformationArrayList.get(position).getImage()).into(holder.senderImage);


            if(chatInformationArrayList.get(position).getMediaType().equalsIgnoreCase("1")){
                holder.receiver_message.setVisibility(View.GONE);
                holder.receiverCardView.setVisibility(View.VISIBLE);
                Glide.with(holder.videoImage2).load(chatInformationArrayList.get(position).getThumbnail()).into(holder.videoImage2);
                Toast.makeText(holder.itemView.getContext(), "video id "+chatInformationArrayList.get(position).getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(holder.itemView.getContext(), "Thumbnail2 "+chatInformationArrayList.get(position).getThumbnail(), Toast.LENGTH_SHORT).show();
            }else{
                holder.receiver_message.setVisibility(View.VISIBLE);
                holder.receiver_message.setText(chatInformationArrayList.get(position).getMessage());
            }
//            holder.time_1.setText(chatInformationArrayList.get(position).getTime().substring(0,5));
        }

        holder.senderCardView.setOnClickListener(view -> {
            callback.playVideo(chatInformationArrayList.get(position));
        });
        holder.receiverCardView.setOnClickListener(view -> {
            callback.playVideo(chatInformationArrayList.get(position));
        });

    }


    public void loadData(List<ChatInformation> messageDetailList) {

        this.chatInformationArrayList = messageDetailList;

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return (chatInformationArrayList != null && chatInformationArrayList.size() != 0 ? chatInformationArrayList.size() : 0);
    }

    public static class holder extends RecyclerView.ViewHolder {
        private RelativeLayout receive, sender;

        private CircleImageView senderImage, reciverImage;
        private TextView receiver_message, sender_message;
        private CardView senderCardView,receiverCardView;
        private ImageView videoImage,videoImage2;

        public holder(@NonNull View itemView) {
            super(itemView);
            receive = itemView.findViewById(R.id.sender);
            sender = itemView.findViewById(R.id.receive);
            receiver_message = itemView.findViewById(R.id.sender_message);
            sender_message = itemView.findViewById(R.id.receiver_message);
            senderImage = itemView.findViewById(R.id.chatCircleImg3);
            reciverImage = itemView.findViewById(R.id.chatCircleImg2);
            senderCardView = itemView.findViewById(R.id.senderCardView);
            receiverCardView = itemView.findViewById(R.id.receiverCardView);
            videoImage = itemView.findViewById(R.id.videoImage);
            videoImage2 = itemView.findViewById(R.id.videoImage2);
        }
    }
}
