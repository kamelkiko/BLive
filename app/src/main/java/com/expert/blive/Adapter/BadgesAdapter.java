package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.Badge;
import com.expert.blive.R;

import java.util.List;

public class BadgesAdapter extends RecyclerView.Adapter<BadgesAdapter.holder> {

    List<Badge> list ;
    Context context;
    Callback callback;

    public BadgesAdapter(List<Badge> list, Context context, Callback callback) {

        this.list = list;
        this.context = context;
        this.callback = callback;
    }
    public interface Callback{
        void openBadge(Badge detail);
    }

    @NonNull
    @Override
    public BadgesAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.badgeslist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BadgesAdapter.holder holder, int position) {

        Glide.with(context).load(list.get(position).getImage()).into(holder.badges);

        holder.itemView.setOnClickListener(view -> {
            callback.openBadge(list.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return (list != null && list.size() != 0 ? list.size() : 0);
    }

    public class holder extends RecyclerView.ViewHolder {

        private ImageView badges;
        public holder(@NonNull View itemView) {
            super(itemView);

            badges=itemView.findViewById(R.id.badges);
        }

    }

}
