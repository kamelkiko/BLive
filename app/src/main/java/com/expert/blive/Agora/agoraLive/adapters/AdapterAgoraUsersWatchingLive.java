package com.expert.blive.Agora.agoraLive.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAgoraUsersWatchingLive extends RecyclerView.Adapter<AdapterAgoraUsersWatchingLive.ViewHolder> {

    Context context;
    List<ModelSetUserViewer> liveUsersList;
    SelectUser selectUser;
    private int lastAnimatedPosition = -1;

    public interface SelectUser {
        void onLiveUserViewerSelected(int position, String userId);
    }

    public AdapterAgoraUsersWatchingLive(Context context, List<ModelSetUserViewer> liveUsersList, SelectUser selectUser) {
        this.context = context;
        this.liveUsersList = liveUsersList;
        this.selectUser = selectUser;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_gifters, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try {
            Glide.with(context).load(liveUsersList.get(position).getImage())
                    .placeholder(R.drawable.ic_user1).into(holder.img_viewer);
        } catch (Exception ex) {
        }

        holder.itemView.setOnClickListener(v -> selectUser.onLiveUserViewerSelected(position, liveUsersList.get(position).getUserId()));

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.in_from_right);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            holder.itemView.setAnimation(animation);
            animation.start();
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return liveUsersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView img_viewer;

        public void clearAnimation() {
            itemView.clearAnimation();
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_viewer = itemView.findViewById(R.id.img_viewer);
        }
    }
}
