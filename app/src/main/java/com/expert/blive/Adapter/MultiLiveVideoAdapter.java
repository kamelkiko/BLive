package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.Agora.GoLiveModelClass;
import com.expert.blive.R;
import com.expert.blive.databinding.LayoutMultiUserLiveListBinding;
import com.expert.blive.utils.CommonUtils;


import java.util.List;

public class MultiLiveVideoAdapter extends RecyclerView.Adapter<MultiLiveVideoAdapter.ViewHolder> {


    private Context context;
    private List<GoLiveModelClass> goLiveModelClasses;
    private List<Integer> reservedSheet;
    private Click click;
    private String check="";
    private int muteStatus;


    public MultiLiveVideoAdapter(Context context, List<GoLiveModelClass> goLiveModelClasses, String check,int muteStatus,Click click, List<Integer> reservedSheet) {
        this.context = context;
        this.goLiveModelClasses = goLiveModelClasses;
        this.reservedSheet = reservedSheet;
        this.click = click;
        this.check = check;
        this.muteStatus = muteStatus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutMultiUserLiveListBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (!goLiveModelClasses.get(position).getUserID().equalsIgnoreCase("")) {

            if (goLiveModelClasses.get(position).getSvga() != null) {
                CommonUtils.setAnimation(holder.itemView.getContext(), goLiveModelClasses.get(position).getSvga(), holder.binding.profieFrame);
            }

            Glide.with(context).load(goLiveModelClasses.get(position).getImage()).placeholder(R.drawable.user_7).into(holder.binding.imgUserProfile);
            holder.binding.txtUserName.setText((!goLiveModelClasses.get(position).getName().equalsIgnoreCase("")) ? goLiveModelClasses.get(position).getName() : goLiveModelClasses.get(position).getUserName());
            holder.binding.rlMic.setVisibility(View.VISIBLE);

//            if (reservedSheet!=null){
//                if (reservedSheet.contains(String.valueOf(position))){
//                    holder.binding.imgChair.setImageResource(R.drawable.ic_lock);
//                }else {
//                    holder.binding.imgChair.setImageResource(R.drawable.ic_baseline_add_24);
//                }
//            }

//            if (Integer.parseInt(goLiveModelClasses.get(position).getPosition())==position){
//            }else {
            Glide.with(context).load(goLiveModelClasses.get(position).getImage()).placeholder(R.drawable.app_logo).into(holder.binding.imgUserProfile);
                holder.binding.txtUserName.setText((!goLiveModelClasses.get(position).getName().equalsIgnoreCase("")) ? goLiveModelClasses.get(position).getName() : goLiveModelClasses.get(position).getUserName());
                holder.binding.rlMic.setVisibility(View.VISIBLE);
//            }


//            if (muteStatus==1){
//                holder.binding.imgMic.setImageResource(R.drawable.ic_baseline_mic_24);
//
//            }else {
//                holder.binding.imgMic.setImageResource(R.drawable.ic_baseline_mic_off_24);
//
//            }
            if (goLiveModelClasses.get(position).getMute().equalsIgnoreCase("1")) {
                holder.binding.imgMic.setImageResource(R.drawable.ic_baseline_mic_off_24);
                holder.binding.lottieGift.setVisibility(View.GONE);
            } else {
                holder.binding.imgMic.setImageResource(R.drawable.ic_baseline_mic_24);
                holder.binding.lottieGift.setVisibility(View.VISIBLE);

            }


        } else {

            Toast.makeText(context, "" + check, Toast.LENGTH_SHORT).show();
            if (check.equals("1")) {
                holder.binding.rlMic.setVisibility(View.GONE);
                if (reservedSheet != null) {
                    if (reservedSheet.contains(String.valueOf(position))) {
                        holder.binding.imgChair.setImageResource(R.drawable.lock);
                    } else {
                        holder.binding.imgChair.setImageResource(R.drawable.ic_baseline_add_24);
                    }
                }
            } else {
                if (reservedSheet != null) {
                    if (reservedSheet.contains(String.valueOf(position))) {
                        holder.binding.imgChair.setImageResource(R.drawable.lock);

                    } else {
                        holder.binding.imgChair.setImageResource(R.drawable.chair_24);

                    }
                }
                
                holder.binding.rlMic.setVisibility(View.GONE);
                holder.binding.txtUserName.setText("Seat No:- " + (position + 1));
            }

        }

        holder.itemView.setOnClickListener(view -> {
            if (!goLiveModelClasses.get(position).getUserID().equalsIgnoreCase("")) {
                click.setOnUserKickListener(goLiveModelClasses.get(position), position);
            } else {
                if (check.equals("1")) {
                        click.setOnUserKickListener(null, position);

                } else {
                    click.setOnUserKickListener(null, position);

                }

            }


        });

        holder.binding.imgMic.setOnClickListener(v -> {

            click.micClick(goLiveModelClasses.get(position));
        });


    }

    @Override
    public int getItemCount() {
        return goLiveModelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutMultiUserLiveListBinding binding;

        public ViewHolder(@NonNull LayoutMultiUserLiveListBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public interface Click {
        void setOnUserKickListener(GoLiveModelClass goLiveModelClass, int pos);
        void micClick(GoLiveModelClass goLiveModelClass);
    }


}
