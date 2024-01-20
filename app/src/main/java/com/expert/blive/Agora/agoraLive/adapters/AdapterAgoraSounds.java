package com.expert.blive.Agora.agoraLive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.expert.blive.Agora.agoraLive.models.ModelAgoraSounds;
import com.expert.blive.R;

import java.util.List;

public class AdapterAgoraSounds extends RecyclerView.Adapter<AdapterAgoraSounds.ViewHolder> {

    Context context;
    List<ModelAgoraSounds.Detail> list;
    Select select;

    public interface Select {
        void onSelected(int position, String soundPath, String soundId,String soundTitle);

        void onStopped(int position, String soundPath, String soundId);
    }

    public AdapterAgoraSounds(Context context, List<ModelAgoraSounds.Detail> list, Select select) {
        this.context = context;
        this.list = list;
        this.select = select;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_live_music, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_sound_name_live.setText(list.get(position).getSoundTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (holder.img_select_sound.getDrawable() == context.getResources().getDrawable(R.drawable.exo_notification_pause)) {
//                    select.onStopped(position, list.get(position).getSoundPath(), list.get(position).getId());
//                } else {
                    select.onSelected(position, list.get(position).getSoundPath(), list.get(position).getId(),list.get(position).getSoundTitle());
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_sound_name_live;
        private ImageView img_select_sound;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sound_name_live = itemView.findViewById(R.id.tv_sound_name_live);
            img_select_sound = itemView.findViewById(R.id.img_select_sound);
        }
    }
}
