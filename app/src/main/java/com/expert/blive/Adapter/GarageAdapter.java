package com.expert.blive.Adapter;

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
import com.expert.blive.R;
import com.opensource.svgaplayer.SVGAImageView;
import java.util.ArrayList;
import java.util.List;

public class GarageAdapter extends RecyclerView.Adapter<GarageAdapter.holder> {
    private List<RootFrames.Detail> list = new ArrayList<>();
    private Context context;
    private Callback callback;

    public GarageAdapter(List<RootFrames.Detail> list, Context context,Callback callback) {

        this.list = list;
        this.context = context;
        this.callback = callback;
    }
    public interface Callback{
        void TryFrames(RootFrames.Detail detail);
        void purchaseFrame(RootFrames.Detail detail, TextView buy );
    }

    @NonNull
    @Override
    public GarageAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_frames, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GarageAdapter.holder holder, int position) {


        if (list.get(position).getPurcheseStatus()){
            holder.buy.setText("Purchased");
        }else {
            holder.buy.setText("Purchase");
        }

        holder.Validity.setText(list.get(position).getValidity() + " Days");
        holder.framePrice.setText(list.get(position).getPrice());
//        CommonUtils.setAnimation(context,list.get(position).getMainImage(),holder.frameImage);
        holder.frameImage.setVisibility(View.GONE);
        holder.frameIMG.setVisibility(View.VISIBLE);
        Glide.with(context).load(list.get(position).getImage()).into(holder.frameIMG);
        holder.purchase.setOnClickListener(view -> {
            callback.purchaseFrame(list.get(position),holder.buy);
        });
        holder.tryNew.setOnClickListener(view -> {
            callback.TryFrames(list.get(position));
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {

        private RelativeLayout tryNew, purchase;
        private SVGAImageView frameImage;
        private TextView Validity, framePrice ,buy;
        private ImageView frameIMG;
        public holder(@NonNull View itemView) {
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

}
