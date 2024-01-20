package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.expert.blive.ModelClass.PkModelList;
import com.expert.blive.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class PkAdapter extends RecyclerView.Adapter<PkAdapter.ViewHolder> {
    private ArrayList<PkModelList> pkModelLists;



    public PkAdapter(ArrayList<PkModelList> pkModelLists) {
        this.pkModelLists = pkModelLists;
    }

    @NonNull
    @NotNull
    @Override
    public PkAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pk_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PkAdapter.ViewHolder holder, int position) {
        holder.img_one_pk.setImageResource(pkModelLists.get(position).getGetImg_one());
        holder.img_two_pk.setImageResource(pkModelLists.get(position).getGetImg_two());


    }

    @Override
    public int getItemCount() {
        return pkModelLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_one_pk,img_two_pk;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img_one_pk = itemView.findViewById(R.id.img_one_pk);
            img_two_pk = itemView.findViewById(R.id.img_two_pk);
        }
    }
}
