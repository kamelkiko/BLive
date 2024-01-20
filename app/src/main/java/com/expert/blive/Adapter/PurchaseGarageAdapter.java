package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.ModelClass.Store.RootFrames;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;
import com.opensource.svgaplayer.SVGAImageView;

import java.util.List;

public class PurchaseGarageAdapter extends RecyclerView.Adapter<PurchaseGarageAdapter.holder> {

    private List<RootFrames.Detail> list;
    private Context context;
    private Callback callback;

    public PurchaseGarageAdapter(List<RootFrames.Detail> list, Context context,Callback callback) {

        this.list = list;
        this.context = context;
        this.callback = callback;
    }
    public interface Callback{

        void setGarage(RootFrames.Detail detail,TextView textView);
    }
    @NonNull
    @Override
    public PurchaseGarageAdapter.holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subscribed,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PurchaseGarageAdapter.holder holder, int position) {

        if(list.get(position).getApplied()){
            holder.applyed.setText("applied");
        }else {
            holder.applyed.setText("apply");
        }

        CommonUtils.setAnimation(context,list.get(position).getMainImage(),holder.frameImage);
        holder.framePrice.setText(list.get(position).getPrice());
        holder.applied_ly.setOnClickListener(view -> {
            callback.setGarage(list.get(position),holder.applyed);
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public class holder extends RecyclerView.ViewHolder {
        private RelativeLayout tryNew, applied_ly;
        private SVGAImageView frameImage;
        private TextView Validity, framePrice ,applyed;
        public holder(@NonNull View itemView) {
            super(itemView);
            frameImage = itemView.findViewById(R.id.framesImg);
            applyed = itemView.findViewById(R.id.applyed);
            applied_ly = itemView.findViewById(R.id.applied_lay);
            framePrice = itemView.findViewById(R.id.Frame_price);
            tryNew = itemView.findViewById(R.id.tryNew);
        }

    }

}
