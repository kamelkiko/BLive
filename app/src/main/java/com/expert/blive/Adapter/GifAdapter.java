package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.ModelClass.GifDetail;
import com.expert.blive.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class GifAdapter  extends RecyclerView.Adapter<GifAdapter.Holder> {

    private List<GifDetail> gifDetails;
    private callBackFromGifAdapter callBackFromGifAdapter;

    public GifAdapter(List<GifDetail> gifDetails,  callBackFromGifAdapter callBackFromGifAdapter) {
        this.gifDetails = gifDetails;
        this.callBackFromGifAdapter = callBackFromGifAdapter;
    }

    public interface callBackFromGifAdapter{

        void submit(GifDetail gifDetail);

    }


    @NonNull
    @NotNull
    @Override
    public GifAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.get_gif,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GifAdapter.Holder holder, int position) {

        Picasso.with(holder.itemView.getContext()).load(gifDetails.get(position).getGifUrl()).error(R.drawable.user_7).into(holder.background_gif1);

        holder.background_gif1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callBackFromGifAdapter.submit(gifDetails.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {

        return (gifDetails != null && gifDetails.size() != 0 ? gifDetails.size() : 0);
    }

    public void loadDate(List<GifDetail> gifDetails){

        this.gifDetails = gifDetails;
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private GifImageView background_gif1;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);

            background_gif1 = itemView.findViewById(R.id.background_gif1);
        }
    }
}
