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
import com.expert.blive.ModelClass.UserBlockDetail;
import com.expert.blive.R;

import java.util.ArrayList;

public class BlockUserAdapter extends RecyclerView.Adapter<BlockUserAdapter.Holder> {

    ArrayList<UserBlockDetail> details;

    Context context;

    OnClickInterface onClickInterface;

    public BlockUserAdapter(ArrayList<UserBlockDetail> details, Context context, OnClickInterface onClickInterface) {

        this.details = details;
        this.context = context;
        this.onClickInterface = onClickInterface;
    }

    public interface OnClickInterface {

        void onClick(String blockUserId);

    }

    @NonNull
    @Override
    public BlockUserAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.block_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BlockUserAdapter.Holder holder, int position) {

        Glide.with(holder.user_profile.getContext()).load(details.get(position).getImage()).placeholder(R.drawable.app_logo).into(holder.user_profile);
        holder.user_name.setText(details.get(position).getName());
        holder.user_Id.setText(details.get(position).getUsername());

        holder.txtBlock.setOnClickListener(view -> {

            onClickInterface.onClick(details.get(position).getId());

        });

    }

    @Override
    public int getItemCount() {

        return details.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private ImageView user_profile;

        private TextView user_name, user_Id, txtBlock;

        public Holder(@NonNull View itemView) {

            super(itemView);


            user_profile = itemView.findViewById(R.id.user_profile);
            user_name = itemView.findViewById(R.id.user_name);
            user_Id = itemView.findViewById(R.id.user_Id);
            txtBlock = itemView.findViewById(R.id.txtBlock);

        }

    }

    public interface UnBlockUser {

        void unblock(String details);

    }

}
