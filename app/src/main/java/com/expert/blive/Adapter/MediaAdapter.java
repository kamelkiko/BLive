package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.Video.VideoDetail2;
import com.expert.blive.R;

import java.util.List;


public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.holder> {

    Callback callback;
    Context context;
    List<VideoDetail2>list;


    public MediaAdapter(Callback callback, Context context, List<VideoDetail2> list) {

        this.callback = callback;
        this.context = context;
        this.list = list;
    }

    public interface Callback{
        void call_video(int position);
        void videoDelete(VideoDetail2 detail);
    }

    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.realsdesign,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {

        Glide.with(context).load(list.get(position).getThumbnail()).placeholder(R.drawable.app_logo).into(holder.videoImage);
        holder.viewCount.setText(list.get(position).getViewCount());

        holder.itemView.setOnClickListener(view -> {
            callback.call_video(position);
            notifyDataSetChanged();
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                callback.videoDelete(list.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {

        private ImageView videoImage;
        private TextView viewCount;
        public holder(@NonNull View itemView) {
            super(itemView);
            videoImage=itemView.findViewById(R.id.videoImage);
            viewCount=itemView.findViewById(R.id.viewCount);
        }

    }

}
