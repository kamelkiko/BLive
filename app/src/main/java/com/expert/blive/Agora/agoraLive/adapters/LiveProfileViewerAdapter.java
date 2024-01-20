package com.expert.blive.Agora.agoraLive.adapters;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveProfileViewerAdapter extends RecyclerView.Adapter<LiveProfileViewerAdapter.ViewHolder> {

    Context context;
    List<ModelSetUserViewer> liveUsersList;
    SelectUser selectUser;
    private int lastAnimatedPosition = -1;



    public LiveProfileViewerAdapter(Context requireContext, List<com.expert.blive.Agora.agoraLive.firebase.models.ModelSetUserViewer> listviewers, SelectUser selectUser) {
        this.context = context;
        this.liveUsersList = liveUsersList;
        this.selectUser = selectUser;


    }

    public interface SelectUser {
        void onLiveUserViewerSelected(int position, String userId);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_profile_viewers, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(liveUsersList.get(position).getImage()).placeholder(R.drawable.ic_user1).into(holder.img_viewer);
        holder.textView.setText(liveUsersList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectUser.onLiveUserViewerSelected(position, liveUsersList.get(position).getUserId());
            }
        });
    }



    @Override
    public int getItemCount() {
        return liveUsersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView img_viewer;
        TextView textView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_viewer = itemView.findViewById(R.id.img_viewer);
            textView = itemView.findViewById(R.id.tv_viewername);
        }
    }
}

