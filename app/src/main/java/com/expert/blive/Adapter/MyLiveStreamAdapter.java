package com.expert.blive.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.ModelClass.myLiveStream.MyLiveStreamRoot;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyLiveStreamAdapter extends RecyclerView.Adapter<MyLiveStreamAdapter.Holder> {
    List<MyLiveStreamRoot.UserDetails.AllLife> list;
    private Context context;
    public MyLiveStreamAdapter(List<MyLiveStreamRoot.UserDetails.AllLife> list,Context context){

        this.list=list;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public MyLiveStreamAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_two,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyLiveStreamAdapter.Holder holder, int position) {

        Picasso.with(context).load(Uri.parse(CommonUtils.getImage())).placeholder(R.drawable.app_logo).into(holder.img_my_profile);
        holder.name.setText(CommonUtils.getName());


            holder.message.setText(list.get(position).getTotaltimePerLive()+"min");

        if (list.get(position).getTotalCoin()!=null){
            holder.time.setText(CommonUtils.prettyCount(Long.parseLong(list.get(position).getTotalCoin().toString())));

        }else {
            holder.time.setText("0");

        }


    }

    @Override
    public int getItemCount() {
        return (list!=null && list.size()!=0 ? list.size():0);
    }

    public void loadData(List<MyLiveStreamRoot.UserDetails.AllLife> list) {

        this.list = list;
        notifyDataSetChanged();

    }

    public static class Holder extends RecyclerView.ViewHolder {
        private ImageView img_my_profile;
        private TextView name,message,time;
        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);

            img_my_profile = itemView.findViewById(R.id.img_my_profile);
            name = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
        }
    }
}
