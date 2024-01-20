package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.ModelClass.ModleAdminClass;
import com.expert.blive.R;

import java.util.List;

public class AdpterAdminList extends RecyclerView.Adapter<AdpterAdminList.ViewHolder> {

    private RemoveAdmin removeAdmin;
    List<ModleAdminClass> dataAdminList;

    public AdpterAdminList(List<ModleAdminClass> dataAdminList, RemoveAdmin removeAdmin) {
        this.dataAdminList = dataAdminList;
        this.removeAdmin = removeAdmin;
    }

    @NonNull
    @Override
    public AdpterAdminList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_live_admin, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterAdminList.ViewHolder holder, int position) {

        holder.txtId.setText(dataAdminList.get(position).getUsername());


        holder.txtSend.setOnClickListener(view -> {

            removeAdmin.remove(dataAdminList.get(position));
            notifyDataSetChanged();


        });


    }

    @Override
    public int getItemCount() {
        return dataAdminList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtId,txtSend;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtId = itemView.findViewById(R.id.txtName);
            txtSend = itemView.findViewById(R.id.txtSend);
        }
    }

    public interface RemoveAdmin {
        void remove(ModleAdminClass id);
    }
}
