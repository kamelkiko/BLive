package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.expert.blive.ModelClass.EmojiGiftModel;
import com.expert.blive.databinding.ListGiftBinding;

import java.util.List;

public class EmojiGiftAdapter extends RecyclerView.Adapter<EmojiGiftAdapter.ViewHolder> {
    private Context context;
    private List<EmojiGiftModel.Detail> list;
    Select select;


    public interface Select {
        void details(EmojiGiftModel.Detail detail);
    }


    public EmojiGiftAdapter(Context context, List<EmojiGiftModel.Detail> list, Select select) {
        this.context = context;
        this.list = list;
        this.select = select;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ListGiftBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EmojiGiftModel.Detail model = list.get(position);

        holder.binding.txtAmount.setText(model.getPrimeAccount());
        Glide.with(context).load(model.getImage()).into(holder.binding.imgGifts);

        holder.binding.imgGifts.setOnClickListener(v -> {
            select.details(list.get(position));
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ListGiftBinding binding;

        public ViewHolder(@NonNull ListGiftBinding itemView) {
            super(itemView.getRoot());

            binding = itemView;
        }
    }
}
