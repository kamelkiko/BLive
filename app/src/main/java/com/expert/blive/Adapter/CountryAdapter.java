package com.expert.blive.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.ModelClass.DetailCountry;
import com.expert.blive.R;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    List<DetailCountry.Details> detilas;
    GetData getData;
    public CountryAdapter(List<DetailCountry.Details> details, GetData getData) {
        this.detilas = details;
        this.getData = getData;
    }

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_image,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;
        holder.txtCountry.setText(detilas.get(position).getCode2l());
        String country = detilas.get(position ).getCode2l();
        int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;

        String flag = new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));
        holder.imageFlag.setText(flag);
        holder.itemView.setOnClickListener(view -> {

            getData.getData(detilas.get(position).getCountry());

        });

    }

    @Override
    public int getItemCount() {
        return detilas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCountry, imageFlag;
        private ImageView imageGLOBAL;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCountry = itemView.findViewById(R.id.countryName);
            imageFlag = itemView.findViewById(R.id.imageFlag);
            imageGLOBAL = itemView.findViewById(R.id.imageGLOBAL);
        }
    }

    public interface GetData {

        void getData(String s);
    }


    public void filterList(List<DetailCountry.Details> list) {
        this.detilas = list;
        notifyDataSetChanged();

    }
}
