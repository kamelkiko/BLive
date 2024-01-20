package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.HomeActivity.Profile.category.CoinHistoryFragment;
import com.expert.blive.ModelClass.CoinHistoryRoot;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoinHistoryAdaptor  extends RecyclerView.Adapter<CoinHistoryAdaptor.Holder> {


    List<CoinHistoryRoot.Detail> coinHistoryDetailList;
    private Context context;

    public CoinHistoryAdaptor(List<CoinHistoryRoot.Detail> coinHistoryDetailList, Context context) {
        this.coinHistoryDetailList = coinHistoryDetailList;
        this.context = context;
    }
    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.coin_history_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CoinHistoryAdaptor.Holder holder, int position) {

        if(CoinHistoryFragment.typeHistory.equals("2")) {

             holder.date.setText(coinHistoryDetailList.get(position).getCreated());
            holder.amount.setText(CommonUtils.prettyCount(Long.parseLong(coinHistoryDetailList.get(position).getCoin())));

            if(coinHistoryDetailList.get(position).getUserInfo().getName()!=null)
            {
                String name="From :"+ coinHistoryDetailList.get(position).getUserInfo().getName();
                holder.source.setText(name);
            }
            else
            {
                String name="From :"+ coinHistoryDetailList.get(position).getUserInfo().getName();
                holder.source.setText(name);
            }

            holder.profile_image3.setColorFilter(context.getColor(R.color.app_pink_color));
            holder.profile_image3.setImageResource(R.drawable.down);
        }
        else
        {
            // done
            holder.date.setText(coinHistoryDetailList.get(position).getCreated());
            holder.amount.setText(coinHistoryDetailList.get(position).getCoin());


//                String name="From "+ coinHistoryDetailList.get(position).getUserInfo().getName();
//                holder.source.setText(name);



            holder.profile_image3.setColorFilter(context.getColor(R.color.app_pink_color));
            holder.profile_image3.setImageResource(R.drawable.up);
        }

    }

    @Override
    public int getItemCount() {
        return (coinHistoryDetailList!=null && coinHistoryDetailList.size()!=0 ? coinHistoryDetailList.size():0);
    }

    public void loadData(List<CoinHistoryRoot.Detail> coinHistoryDetailList)
    {
        this.coinHistoryDetailList=coinHistoryDetailList;
        notifyDataSetChanged();
    }
    public static class Holder extends RecyclerView.ViewHolder {
        TextView date,source,amount;
        ImageView profile_image3;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);

            date=itemView.findViewById(R.id.date);
            source=itemView.findViewById(R.id.source);
            amount=itemView.findViewById(R.id.amount);
            profile_image3=itemView.findViewById(R.id.profile_image3);

        }
    }
}
