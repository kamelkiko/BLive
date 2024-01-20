package com.expert.blive.Adapter.ChatSection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.ChatInformation.ChatInformation;
import com.expert.blive.R;

import java.util.List;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.holder> {

    private ChatAdapter.callBackFromChatAdapter callBackFromChatAdapter;
    private List<ChatInformation> chatInformationArrayList;
    private Context context;


    public ChatAdapter(ChatAdapter.callBackFromChatAdapter callBackFromChatAdapter, List<ChatInformation> chatInformationArrayList,Context context) {
        this.callBackFromChatAdapter = callBackFromChatAdapter;
        this.chatInformationArrayList = chatInformationArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChatAdapter.holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {


        Glide.with(context).load(chatInformationArrayList.get(position).getImage()).placeholder(R.drawable.app_logo).into(holder.img_my_profile);
        holder.message.setText(chatInformationArrayList.get(position).getMessage());
        holder.name.setText(chatInformationArrayList.get(position).getName());
        holder.time.setText(chatInformationArrayList.get(position).getTime());
        holder.messageTab.setOnClickListener(v -> callBackFromChatAdapter.next(chatInformationArrayList.get(position)));

    }
    public void loadData(List<ChatInformation> messageDetailList){

        this.chatInformationArrayList=messageDetailList;

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {

        return (chatInformationArrayList!=null && chatInformationArrayList.size()!=0 ? chatInformationArrayList.size():0);
    }

    public interface callBackFromChatAdapter {

        void next(ChatInformation chatInformation);

    }

    static class holder extends RecyclerView.ViewHolder {

        TextView name,message,time;
        RelativeLayout messageTab;
        private ImageView img_my_profile;


        public holder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            message=itemView.findViewById(R.id.message);
            messageTab=itemView.findViewById(R.id.messageTab);
            time=itemView.findViewById(R.id.time);
            img_my_profile=itemView.findViewById(R.id.img_my_profile);

        }
    }
}

