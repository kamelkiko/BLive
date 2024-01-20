package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.Agora.openvcall.propeller.ui.RecyclerItemClickListener;
import com.expert.blive.ModelClass.RewordRoot;
import com.expert.blive.R;

import java.util.List;

import io.grpc.Context;

public class RewordAdapter extends RecyclerView.Adapter<RewordAdapter.holder> {

    private List<RewordRoot.Detail> list;


    public RewordAdapter(List<RewordRoot.Detail> list) {

        this.list = list;

    }

    @NonNull
    @Override
    public RewordAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rewordlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RewordAdapter.holder holder, int position) {

        holder.coin.setText(list.get(position).getAmount());
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {

        private TextView coin;
        public holder(@NonNull View itemView) {
            super(itemView);
            coin = itemView.findViewById(R.id.coin);

        }

    }

}
