package com.expert.blive.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.Store.RootFrames;
import com.expert.blive.ModelClass.TotalCoinModal;
import com.expert.blive.R;
import com.opensource.svgaplayer.SVGAImageView;

import java.util.List;

public class MallFramesAdapter extends RecyclerView.Adapter<MallFramesAdapter.Holder> {

    private List<RootFrames.Detail> list;
    public static TotalCoinModal coinModal ;

    Context context;
    Callback callback;


    public MallFramesAdapter(List<RootFrames.Detail> list, Context context, Callback callback) {
        this.list = list;
        this.context = context;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frames, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        if (list.get(position).getPurchasedType().equalsIgnoreCase(String.valueOf(true))) {
                 holder.buy.setText("Purchased");
        }else {
            holder.buy.setText("Purchase");
        }

        holder.Validity.setText(list.get(position).getValidity() + " Days");
        holder.framePrice.setText(list.get(position).getPrice());
        holder.frameImage.setVisibility(View.GONE);
        holder.frameIMG.setVisibility(View.VISIBLE);
        Glide.with(context).load(list.get(position).getThumbnail()).error(R.drawable.app_logo).into(holder.frameIMG);
        holder.purchase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.purchaseFrame(list.get(position),holder.buy);

                }
            });


        holder.tryNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.TryFrames(list.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        private RelativeLayout tryNew, purchase;
        private SVGAImageView frameImage;
        private TextView Validity, framePrice ,buy;
        private ImageView frameIMG;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tryNew = itemView.findViewById(R.id.tryNew);
            frameImage = itemView.findViewById(R.id.framesImg);
            Validity = itemView.findViewById(R.id.themetime);
            framePrice = itemView.findViewById(R.id.FramePrice);
            purchase = itemView.findViewById(R.id.purchase);
            buy = itemView.findViewById(R.id.buy);
            frameIMG = itemView.findViewById(R.id.frameIMG);
        }

    }


    public interface Callback {
        void TryFrames(RootFrames.Detail detail);

        void purchaseFrame(RootFrames.Detail detail, TextView buy );
    }
}
