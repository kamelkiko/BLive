package com.expert.blive.Agora.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.databinding.ListRequestMultiliveBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PKLiveUserAdapter extends RecyclerView.Adapter<PKLiveUserAdapter.ViewHolder> {

    private Context context;
    private List<PKLiveUserModel.Detail> dimaond;
    private Click click;


    public PKLiveUserAdapter(Context context, List<PKLiveUserModel.Detail> dimaond, Click click) {
        this.context = context;
        this.dimaond = dimaond;
        this.click = click;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        return new ViewHolder(ListRequestMultiliveBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

//        Glide.with(context).load(dimaond.get(position).getPosterImage()).into(holder.binding.imgProfile);
//            holder.binding.txtName.setText(dimaond.get(position).getName());
//        holder.binding.rlRejected.setVisibility(View.GONE);
//
//        holder.binding.rlAccept.setOnClickListener(view -> {
//
//            click.setOnSendPKInvitationListener(dimaond.get(position));
//
//        });
//
//        holder.binding.txtAccept.setText("Request");
    }

    @Override
    public int getItemCount() {

        return dimaond.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ListRequestMultiliveBinding binding;

        public ViewHolder(@NonNull @NotNull ListRequestMultiliveBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }

    public interface Click {

        void setOnSendPKInvitationListener(PKLiveUserModel.Detail dimaond);
    }
}
