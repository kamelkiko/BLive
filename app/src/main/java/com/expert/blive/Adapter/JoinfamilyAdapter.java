package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.Family.FamilyDetail;
import com.expert.blive.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class JoinfamilyAdapter extends RecyclerView.Adapter<JoinfamilyAdapter.holdewr>{
    List<FamilyDetail> list;
    Context context;
    Callback callback;

    public JoinfamilyAdapter(List<FamilyDetail> list, Context context, Callback callback) {

        this.list = list;
        this.context = context;
        this.callback = callback;
    }
    public interface Callback{
        void openProfile(FamilyDetail familyDetail);
    }

    @NonNull
    @Override
    public holdewr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holdewr(LayoutInflater.from(parent.getContext()).inflate(R.layout.designfamily,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull holdewr holder, int position) {


        holder.familyNameTV.setText(list.get(position).getFamilyName());
        Glide.with(holder.profile_hour.getContext()).load(list.get(position).getFamilyImage())
                .error(R.drawable.app_logo).into(holder.profile_hour);

        holder.itemView.setOnClickListener(view -> {
            callback.openProfile(list.get(position));
        });

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class holdewr extends RecyclerView.ViewHolder {
        TextView familyNameTV;
        CircleImageView profile_hour;
        public holdewr(@NonNull View itemView) {
            super(itemView);
            familyNameTV=itemView.findViewById(R.id.familyNameTV);
            profile_hour=itemView.findViewById(R.id.profile_hour);
        }

    }

}
