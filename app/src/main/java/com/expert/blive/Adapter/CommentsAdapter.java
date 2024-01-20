package com.expert.blive.Adapter;

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
import com.expert.blive.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    Context context;
    ArrayList<CommentDetails> details;
    Callback callback;

    public CommentsAdapter(ArrayList<CommentDetails> details, Context context,Callback callback) {

        this.context = context;
        this.details = details;
        this.callback = callback;
    }
    public interface Callback{

        void commentLikes(CommentDetails details , ImageView likeUnlike, ImageView likeUnlike2);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_show, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (details.get(position).likeStatus) {
            holder.like2.setVisibility(View.VISIBLE);
            holder.like.setVisibility(View.GONE);
        } else {
            holder.like.setVisibility(View.VISIBLE);
            holder.like2.setVisibility(View.GONE);
        }

        holder.tvComments.setText(details.get(position).getComment());
        holder.tvName.setText(details.get(position).getName());
        Glide.with(context).load(details.get(position).getImage()).into(holder.usrImg);

        holder.like_button.setOnClickListener(view -> {
            callback.commentLikes(details.get(position),holder.like,holder.like2);
        });


    }

    @Override
    public int getItemCount() {

        return details.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView usrImg;
        ImageView like,like2;
        TextView tvComments,tvName;
        RelativeLayout like_button;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tvComments = itemView.findViewById(R.id.tvComment);
            usrImg = itemView.findViewById(R.id.usrImg);
            tvName = itemView.findViewById(R.id.tvName);
            like_button = itemView.findViewById(R.id.like_button);
            like2 = itemView.findViewById(R.id.like2);
            like = itemView.findViewById(R.id.like);

        }

    }

}
