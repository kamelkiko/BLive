package com.expert.blive.Agora.agoraLive.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.expert.blive.Agora.agoraLive.models.GetLiveHistoryMonthWisePojo;
import com.expert.blive.R;

import java.util.List;


public class AdapterLiveCoins extends RecyclerView.Adapter<AdapterLiveCoins.LiveCoins> {


    private Context context;
    private List<GetLiveHistoryMonthWisePojo.Detail> list;


    public AdapterLiveCoins(Context context, List<GetLiveHistoryMonthWisePojo.Detail> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public LiveCoins onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_live_gift_history, parent, false);
        return new LiveCoins(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LiveCoins holder, int position) {


        holder.tv_startDateTime.setText(list.get(position).getCreated().substring(8, 16));
        holder.tv_endDateTime.setText(list.get(position).getArchivedDate().substring(8, 16));


        String s=list.get(position).getDuration();

        if(s.contains(".")){
            int end = 0;

            for (int ii = -1; (ii = s.indexOf(".", ii + 1)) != -1; ii++) {
                end=ii;
            }

            s=s.substring(0,end);
        }


        holder.tv_duration.setText(s);
        holder.tv_coins.setText(list.get(position).getCoin());


//        holder.sessionLiveAt.setText("Live at: "+list.get(position).getCreated());
//        holder.coinsRec.setText("Coins Received: "+list.get(position).getTotalCoin()+" Coins");
//
//        if(!list.get(position).getTotalTime().equalsIgnoreCase("") || !list.get(position).getTotalTime().equalsIgnoreCase(" ")){
//            holder.duration.setText("Session Time: "+list.get(position).getTotalTime()+" min");
//        }
//


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LiveCoins extends RecyclerView.ViewHolder {

        private TextView sessionLiveAt, duration, coinsRec;
        private TextView tv_startDateTime, tv_endDateTime, tv_duration, tv_typeOfLive, tv_coins;

        public LiveCoins(@NonNull View itemView) {
            super(itemView);

//            sessionLiveAt = itemView.findViewById(R.id.tv_liveAt);
//            duration = itemView.findViewById(R.id.tv_liveDuration);
//            coinsRec = itemView.findViewById(R.id.tv_coinsReceived);


            tv_startDateTime = itemView.findViewById(R.id.tv_startDateTime);
            tv_endDateTime = itemView.findViewById(R.id.tv_endDateTime);
            tv_duration = itemView.findViewById(R.id.tv_duration);
//            tv_typeOfLive = itemView.findViewById(R.id.tv_typeOfLive);
            tv_coins = itemView.findViewById(R.id.tv_coins);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }
}

