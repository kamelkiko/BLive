package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.expert.blive.ModelClass.Banner.BannerImagesModel;
import com.expert.blive.R;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BannerAdapter extends SliderViewAdapter<BannerAdapter.SliderAdapterVH> {

    private Context context;
    private List<BannerImagesModel> list;

    public BannerAdapter(Context context,List<BannerImagesModel> list) {
        this.context = context;
        this.list=list;
    }

    @Override
    public BannerAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_slide, null);
        return new SliderAdapterVH(inflate);
    }

    public void loadData(List<BannerImagesModel> list){

        this.list=list;
        notifyDataSetChanged();

    }

    @Override
    public void onBindViewHolder(BannerAdapter.SliderAdapterVH viewHolder, int position) {

        Picasso.with(context).load(list.get(position).getBanner()).error(R.drawable.eplive_logo).into(viewHolder.img);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    public static class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        ImageView img;

        public SliderAdapterVH(View itemView) {

            super(itemView);

            img=itemView.findViewById(R.id.img124);

        }
    }
}