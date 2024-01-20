package com.expert.blive.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class AdapterCountry extends RecyclerView.Adapter<AdapterCountry.ViewHolder> {
    Context requireContext;
    List<DetailCountry.Details> detilas;
    GetData getData;

    public AdapterCountry(Context requireContext, List<DetailCountry.Details> detilas, GetData getData) {
        this.requireContext = requireContext;
        this.detilas = detilas;
        this.getData = getData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.country_flag, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position == 0) {
            holder.txtCountry.setText("Global");
            holder.imageGLOBAL.setImageResource(R.drawable.worldwide);
            holder.imageGLOBAL.setVisibility(View.VISIBLE);
            holder.imageFlag.setVisibility(View.GONE);

        } else {

                int flagOffset = 0x1F1E6;
                int asciiOffset = 0x41;
                holder.txtCountry.setText(detilas.get(position - 1).getName());
                String country = detilas.get(position - 1).getCode2l();

                if (country!=null){
                    int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
                    int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;
                    String flag = new String(Character.toChars(firstChar))
                            + new String(Character.toChars(secondChar));

                    holder.imageGLOBAL.setVisibility(View.GONE);
                    holder.imageFlag.setVisibility(View.VISIBLE);

                    holder.imageFlag.setText(flag);
                }
                else {
                    holder.itemView.setVisibility(View.GONE);
                }







        }

//        Glide.with(holder.imageFlag).load(getEmojiByUnicode(detilas.get(position).getEmojiU())).into(holder.imageFlag);


        holder.itemView.setOnClickListener(view -> getData.getData(holder.txtCountry.getText().toString()));

    }

    @Override
    public int getItemCount() {
        return detilas.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCountry, imageFlag;
        private ImageView imageGLOBAL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtCountry = itemView.findViewById(R.id.txtCountry);
            imageFlag = itemView.findViewById(R.id.imageFlag);
            imageGLOBAL = itemView.findViewById(R.id.imageGLOBAL);
        }
    }

    public interface GetData {

        void getData(String s);
    }


    public void getEmojiByUnicode(int unicode) {

    }
}

