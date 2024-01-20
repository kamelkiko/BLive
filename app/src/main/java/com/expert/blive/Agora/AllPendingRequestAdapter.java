package com.expert.blive.Agora;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.expert.blive.R;
import com.expert.blive.databinding.ListPendingRequestBinding;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllPendingRequestAdapter extends RecyclerView.Adapter<AllPendingRequestAdapter.ViewHolder> {



    private Context context;
    private List<GoLiveModelClass> liveModelClasses;

    public AllPendingRequestAdapter(Context context, List<GoLiveModelClass> liveModelClasses) {
        this.context = context;
        this.liveModelClasses = liveModelClasses;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ListPendingRequestBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Glide.with(context).load(liveModelClasses.get(position).getImage()).placeholder(R.drawable.ic_baseline_account_circle_24).into(holder.binding.imgProfile);

        holder.binding.txtName.setText("Name: " + liveModelClasses.get(position).getName());
        holder.binding.txtUserName.setText("ID: " + liveModelClasses.get(position).getUserName());


    }

    @Override
    public int getItemCount() {
        return liveModelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListPendingRequestBinding binding;

        public ViewHolder(@NonNull @NotNull ListPendingRequestBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
