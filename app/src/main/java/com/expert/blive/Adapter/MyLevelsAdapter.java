package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.Levels.LevelDetail;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyLevelsAdapter extends RecyclerView.Adapter<MyLevelsAdapter.Holder> {
    private List<LevelDetail> levelDetails;
    private String s;
    private GetCoinData getCoinData;

    public MyLevelsAdapter(List<LevelDetail> levelDetails, String s, GetCoinData getCoinData) {

        this.levelDetails = levelDetails;
        this.s = s;
        this.getCoinData = getCoinData;
    }
    @NonNull
    @NotNull
    @Override
    public MyLevelsAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_levels_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyLevelsAdapter.Holder holder, int position) {

        if (s.equalsIgnoreCase("1")){
            holder.level.setText("Tal."+levelDetails.get(position).getLevel());

        }else {
            holder.level.setText("Lv."+levelDetails.get(position).getLevel());

        }

        holder.level_exp.setText("Exp: " + CommonUtils.prettyCount(Long.parseLong(levelDetails.get(position).getExperince())));

//        holder.level_number.setText(levelDetails.get(position).getLevel_num());

        Glide.with(holder.itemView.getContext()).load(levelDetails.get(position).getImage()).into(holder.level_image_vip);

        holder.itemView.setOnClickListener(view ->{

            getCoinData.getTalent(levelDetails.get(position).getImage());
        } );


    }



    public void loadData(List<LevelDetail> levelDetails) {

        this.levelDetails = levelDetails;

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return (levelDetails != null && levelDetails.size() != 0 ? levelDetails.size() : 0);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private TextView level_exp, level, level_number;
        private ImageView level_image_vip;
        private LinearLayout level_layout;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            level_exp = itemView.findViewById(R.id.level_exp);
            level = itemView.findViewById(R.id.level);
            level_number = itemView.findViewById(R.id.level_number);
            level_layout = itemView.findViewById(R.id.level_layout);
            level_image_vip = itemView.findViewById(R.id.level_image_vip);

        }
    }
    public  interface GetCoinData{
        void getTalent(String image);
    }
}
