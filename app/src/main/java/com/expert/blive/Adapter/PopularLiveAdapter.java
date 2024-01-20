package com.expert.blive.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.LiveUserModel;
import com.expert.blive.R;
import com.expert.blive.databinding.PopularItemListBinding;


import java.util.List;

public class PopularLiveAdapter extends RecyclerView.Adapter<PopularLiveAdapter.ViewHolder> {

    private Context context;
    private List<LiveUserModel.Detail> list;
    private Click click;

    public PopularLiveAdapter(Context context, List<LiveUserModel.Detail> list, Click click) {
        this.context = context;
        this.list = list;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ViewHolder(PopularItemListBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.app_logo).into(holder.binding.hotViewImage);
        Log.d("TAG", "onBindViewHolder: image:-"+list.get(position).getImage());

        holder.itemView.setOnClickListener(view -> {

            click.setOnOpenLiveUser(list.get(position));

        });

        holder.binding.hostName.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        PopularItemListBinding binding;

        public ViewHolder(@NonNull PopularItemListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }


    public interface Click {
        void setOnOpenLiveUser(LiveUserModel.Detail detail);
    }
}
