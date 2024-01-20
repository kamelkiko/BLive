package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.ModelClass.MonthlyHistory;
import com.expert.blive.databinding.MonthlyLiveDataBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdapterMonthlyHistory  extends RecyclerView.Adapter<AdapterMonthlyHistory.ViewHolder> {

    List<MonthlyHistory.Detail> details ;
    GetData getData;
    public AdapterMonthlyHistory(List<MonthlyHistory.Detail> details,GetData getData ) {

        this.details = details;
        this.getData = getData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(MonthlyLiveDataBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");
        Date dateObj;
        try {
             dateObj = curFormater.parse(details.get(position).getDateFrom());
            SimpleDateFormat postFormater = new SimpleDateFormat("MMMM, yyyy");
            holder.binding.monthName.setText(postFormater.format(dateObj) );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.binding.monthDuration.setText("Total Duration : "+details.get(position).getLiveDuration()+" min");

        holder.binding.zerroMinus3.setText("+"+details.get(position).getMonthlyCoins());

        holder.itemView.setOnClickListener(v -> {
            getData.getMyData(details.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MonthlyLiveDataBinding binding;
        public ViewHolder(@NonNull MonthlyLiveDataBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;
        }
    }

    public interface GetData{
        void getMyData(MonthlyHistory.Detail monthly);
    }
}
