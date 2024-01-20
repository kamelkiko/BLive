package com.expert.blive.Agora.agoraLive.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.retrofit.GetWinnerPkBattlePojo;
import com.expert.blive.R;

import java.util.List;


public class AdapterHostsJoiners extends RecyclerView.Adapter<AdapterHostsJoiners.ViewHolder> {

    Context context;
    List<GetWinnerPkBattlePojo.Detail> list;

    public interface Select {
        void onClick(int pos);
    }

    public AdapterHostsJoiners(Context context, List<GetWinnerPkBattlePojo.Detail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_host_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (list.get(position)!=null){


            if (list.get(position).getImage()==null){
                holder.iv_hostImage.setImageResource(R.drawable.app_logo);

            }else {


                Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.app_logo).into(holder.iv_hostImage);
            }


            Animation animation = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.down_to_up);
            Animation animationTwo = AnimationUtils.loadAnimation(context.getApplicationContext(), R.anim.top_to_bottom);

            if (position == 0) {
                holder.itemView.startAnimation(animation);
            } else {
                holder.itemView.startAnimation(animationTwo);
            }


            holder.tv_hostName.setText(list.get(position).getUsername());


            if (list.get(position).getResult().equalsIgnoreCase("WINNER")) {
                holder.ll_backGround.setBackgroundResource(R.drawable.stroke_primary);
//            holder.iv_winImage.setVisibility(View.VISIBLE);
                holder.iv_winImage.setImageResource(R.drawable.app_logo);
            } else if (list.get(position).getResult().equalsIgnoreCase("LOSSER")) {
                holder.ll_backGround.setBackgroundResource(R.drawable.app_logo);
                holder.iv_winImage.setImageResource(R.drawable.app_logo);
            } else {
                holder.iv_winImage.setVisibility(View.GONE);
                holder.ll_backGround.setBackgroundResource(R.drawable.app_logo);
                Toast.makeText(context, "Battle Draw", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_hostName;
        private LinearLayout ll_backGround;
        private ImageView iv_hostImage, iv_winImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_hostName = itemView.findViewById(R.id.tv_hostName);
            iv_hostImage = itemView.findViewById(R.id.iv_hostImage);
            iv_winImage = itemView.findViewById(R.id.iv_winImage);
            ll_backGround = itemView.findViewById(R.id.ll_backGround);

        }
    }


    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }
}

