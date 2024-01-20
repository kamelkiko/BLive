package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.FollowingDataModel;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;
import com.opensource.svgaplayer.SVGAImageView;

import java.util.List;

public class AdapterFollowFollowing extends RecyclerView.Adapter<AdapterFollowFollowing.ViewHolder> {
    List<FollowingDataModel.Detail> details;
    private String checkStatus;
    private Context context;
    private FollowUnfollowSection followUnfollowSection;



    public AdapterFollowFollowing(List<FollowingDataModel.Detail> details, String checkStatus,Context context,FollowUnfollowSection followUnfollowSection) {
        this.details = details;
        this.checkStatus = checkStatus;
        this.context = context;
        this.followUnfollowSection = followUnfollowSection;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_following, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CommonUtils.setAnimation(context, String.valueOf(details.get(position).getMyFrame()),holder.profileFrame);
        Glide.with(holder.user_profile).load(details.get(position).getImage()).placeholder(R.drawable.app_logo).into(holder.user_profile);
        holder.user_name.setText(details.get(position).getName());
        if (checkStatus.equalsIgnoreCase("1")) {

            if (details.get(position).isFriends()){

                holder.imageFriends.setVisibility(View.VISIBLE);
            }else {
                holder.imageFriends.setVisibility(View.GONE);
            }

            holder.followImage.setVisibility(View.GONE);
        } else if (checkStatus.equalsIgnoreCase("3")) {

            if (details.get(position).isFriends()){
                holder.followImage.setVisibility(View.GONE);
                holder.imageFriends.setVisibility(View.VISIBLE);
            }else {
                holder.followImage.setVisibility(View.VISIBLE);
                holder.imageFriends.setVisibility(View.GONE);
            }

        }else {
            holder.imageFriends.setVisibility(View.GONE);
            holder.followImage.setVisibility(View.GONE);
        }

        holder.followImage.setOnClickListener(view -> {

            followUnfollowSection.followUnfollow(details.get(position).getId(), checkStatus, holder.followImage);
        });

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView user_profile,followImage,imageFriends;
        private TextView user_name;
        private LinearLayout follow_following_layout;
        private SVGAImageView profileFrame;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            followImage = itemView.findViewById(R.id.followImage);
            imageFriends = itemView.findViewById(R.id.imageFriends);
//            follow_following_layout = itemView.findViewById(R.id.follow_following_layout);
            user_profile = itemView.findViewById(R.id.user_profile);
            profileFrame = itemView.findViewById(R.id.profileFrame);
        }
    }

    public interface FollowUnfollowSection{

        void followUnfollow(String otherUserId, String checkStatus, ImageView textView);

    }
}
