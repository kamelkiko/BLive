package com.expert.blive.Agora.agoraLive.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSetUserViewer;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterUserList extends RecyclerView.Adapter<AdapterUserList.ViewHolder> {
    private List<ModelSetUserViewer> listViewers;
    GetData getData;

    public AdapterUserList(List<ModelSetUserViewer> listViewers,GetData getData) {
        this.listViewers = listViewers;
        this.getData = getData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_live, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(holder.personImge3.getContext()).load(listViewers.get(position).getImage()).placeholder(R.drawable.app_logo).into(holder.personImge3);

        holder.itemView.setOnClickListener(view -> {
            getData.getData(listViewers.get(position).getUserId());
        });

    }

    @Override
    public int getItemCount() {
        return listViewers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView personImge3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            personImge3 = itemView.findViewById(R.id.personImge3);
        }
    }
    public interface GetData{
        void getData(String number);
    }
}
