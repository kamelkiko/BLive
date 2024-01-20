package com.expert.blive.Agora.agoraLive.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.utils.App;
import com.expert.blive.Agora.agoraLive.models.ModelAgoraMessages;

import com.expert.blive.R;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterMessagesAgora extends RecyclerView.Adapter<AdapterMessagesAgora.ViewHolder> {
    Context context;
    List<ModelAgoraMessages> list;
    private int lastAnimatedPosition = -1;

    SelectMessage selectMessage;
    String AdminStatus;

    public interface SelectMessage {
        void onMessageSelected(String otherUserId, String username, int positon);
    }

    public AdapterMessagesAgora(Context context, List<ModelAgoraMessages> list, SelectMessage selectMessage, String AdminStatus) {
        this.context = context;
        this.list = list;
        this.selectMessage = selectMessage;
        this.AdminStatus = AdminStatus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_comment_on_live, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (list.get(position).getType() == 1) {

            if (list.get(position).getAdminStatus() == null) {
                Glide.with(context).load(list.get(position).getImage()).placeholder(context.getResources().getDrawable(R.drawable.ic_user1)).into(holder.civ_user);

            } else if (list.get(position).getAdminStatus().equals("1")) {
                holder.civ_user.setImageResource(R.drawable.hostimage);

            } else {
                Glide.with(context).load(list.get(position).getImage()).placeholder(context.getResources().getDrawable(R.drawable.ic_user1)).into(holder.civ_user);

            }
            if (list.get(position).getUserId().equalsIgnoreCase(App.getSingleton().getMainHostUserId())) {
                holder.tv_levelComment.setVisibility(View.GONE);
                holder.txt_host.setVisibility(View.VISIBLE);
            } else {
                holder.tv_levelComment.setVisibility(View.VISIBLE);
                holder.txt_host.setVisibility(View.GONE);
                if (list.get(position).getLevel() != null || list.get(position).getLevel().isEmpty()) {
                    holder.tv_levelComment.setText("lv:0");
                } else {

                    holder.tv_levelComment.setText(list.get(position).getLevel());
                }
            }
            if (list.get(position).getMessage().contains("entered the stream")) {

                if (list.get(position).getMystryman()!=null){

                    if (list.get(position).getMystryman().equalsIgnoreCase("1")){
                        holder.txt_user_name.setText("*********");
                    }else {
                        holder.txt_user_name.setText(list.get(position).getUsername());
                    }
                }else{
                    Toast.makeText(context, "Technical issue..." , Toast.LENGTH_SHORT).show();
                }
            } else if (list.get(position).getMessage().contains("sent a ")) {
                holder.txt_user_name.setText(list.get(position).getUsername());
            } else {
                holder.txt_user_name.setText(list.get(position).getUsername() + " :");
            }
            holder.txt_user_comment.setText(list.get(position).getMessage());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectMessage.onMessageSelected(list.get(position).getUserId(), list.get(position).getUsername(), position);
                }
            });
            if (position > lastAnimatedPosition) {
                lastAnimatedPosition = position;
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.down_to_up);
                animation.setInterpolator(new AccelerateDecelerateInterpolator());
                holder.itemView.setAnimation(animation);
                animation.start();
            }
        } else {
            holder.tv_levelComment.setVisibility(View.GONE);
            holder.txt_user_comment.setText(list.get(position).getMessage());
            holder.llUser.setVisibility(View.GONE);
            holder.civ_user.setVisibility(View.GONE);
            holder.txt_user_name.setVisibility(View.GONE);
        }
        if (list.get(position).getLevel()!=null){
            if (list.get(position).getLevel().equalsIgnoreCase("0")) {
                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.call_bg));

            } else if (list.get(position).getLevel().equalsIgnoreCase("1")) {
                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.btnremoveads));

            } else if (list.get(position).getLevel().equalsIgnoreCase("2")) {
                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.btnsub));

            } else if (list.get(position).getLevel().equalsIgnoreCase("3")) {
                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.btn_bg));

            } else if (list.get(position).getLevel().equalsIgnoreCase("4")) {
                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.btnsub));


            } else if (list.get(position).getLevel().equalsIgnoreCase("5")) {
                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.button_dsign));


            } else if (list.get(position).getLevel().equalsIgnoreCase("6")) {
                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.tranparent));

            } else if (list.get(position).getLevel().equalsIgnoreCase("7")) {
                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.tranparent_round));

            } else if (list.get(position).getLevel().equalsIgnoreCase("8")) {
                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.btnsub));

            } else if (list.get(position).getLevel().equalsIgnoreCase("9")) {
                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.btnsub));

            } else {

                holder.bgCommentLayout.setBackground(context.getResources().getDrawable(R.drawable.call_bg));

            }
        }

    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clearAnimation();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civ_user;
        private RelativeLayout bgCommentLayout;
        private TextView txt_user_name, txt_user_comment, tv_levelComment, txt_colon, txt_host;
        private LinearLayout llUser;

        public void clearAnimation() {
            itemView.clearAnimation();
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_host = itemView.findViewById(R.id.txt_host);
            bgCommentLayout = itemView.findViewById(R.id.bgCommentLayout);
            txt_colon = itemView.findViewById(R.id.txt_colon);
            tv_levelComment = itemView.findViewById(R.id.tv_levelComment);
            civ_user = itemView.findViewById(R.id.civ_user);
            txt_user_name = itemView.findViewById(R.id.txt_user_name);
            llUser = itemView.findViewById(R.id.llUser);
            txt_user_comment = itemView.findViewById(R.id.txt_user_comment);
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
