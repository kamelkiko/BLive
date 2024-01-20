package com.expert.blive.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.ModelClass.Levels.LevelDetail;
import com.expert.blive.R;
import com.google.firebase.database.annotations.NotNull;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LevelsAdapter extends RecyclerView.Adapter<LevelsAdapter.ViewHolder> {
    private List<LevelDetail> levelDetails;

    public LevelsAdapter(List<LevelDetail> levelDetails) {
        this.levelDetails = levelDetails;
    }

    @NonNull
    @NotNull
    @Override
    public LevelsAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.levels_list_lay, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LevelsAdapter.ViewHolder holder, int position) {

        holder.level.setText("Level " + levelDetails.get(position).getLevel());
        holder.level_exp.setText("Exp: " + levelDetails.get(position).getExperince());
        holder.level_number.setText(levelDetails.get(position).getLevel_num());
        Picasso.with(holder.itemView.getContext()).load(levelDetails.get(position).getImage()).error(R.drawable.user_7).into(holder.level_image);
        holder.vip_level.setVisibility(View.GONE);




        if (Integer.parseInt(levelDetails.get(position).getLevel())>=0 && Integer.parseInt(levelDetails.get(position).getLevel())<=5){

            holder.level_layout.setBackgroundColor(Color.parseColor("#Ebd300"));

        }
        if (Integer.parseInt(levelDetails.get(position).getLevel())>=6 && Integer.parseInt(levelDetails.get(position).getLevel())<=13){

            holder.level_layout.setBackgroundColor(Color.parseColor("#78c2b5"));

        }
        if (Integer.parseInt(levelDetails.get(position).getLevel())>=14 && Integer.parseInt(levelDetails.get(position).getLevel())<=35){

            holder.level_layout.setBackgroundColor(Color.parseColor("#ef7ec0"));

        }



    }

    @Override
    public int getItemCount() {
        return (levelDetails != null && levelDetails.size() != 0 ? levelDetails.size() : 0);
    }

    public void loadData(List<LevelDetail> levelDetails) {

        this.levelDetails = levelDetails;

        notifyDataSetChanged();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView level_exp, level, level_number;
        private ImageView level_image, vip_level;
        private RelativeLayout level_layout;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            level_exp = itemView.findViewById(R.id.level_exp);
            level = itemView.findViewById(R.id.level);
            level_number = itemView.findViewById(R.id.level_number);
            level_image = itemView.findViewById(R.id.level_image);
            vip_level = itemView.findViewById(R.id.vip_level);
            level_layout = itemView.findViewById(R.id.level_layout);


        }
    }
}
