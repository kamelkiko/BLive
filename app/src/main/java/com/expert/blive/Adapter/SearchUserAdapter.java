package com.expert.blive.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.expert.blive.ModelClass.searchUser.SearchUserDetail;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;
import com.opensource.svgaplayer.SVGAImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.Holder> {

    private final callBackFromSearchUserAdapter callBackFromSearchUserAdapter;
    private List<SearchUserDetail> list;
    private Context context;

    public SearchUserAdapter(callBackFromSearchUserAdapter callBackFromSearchUserAdapter, List<SearchUserDetail> list, Context context) {
        this.callBackFromSearchUserAdapter = callBackFromSearchUserAdapter;
        this.list = list;
        this.context = context;

    }


    public interface callBackFromSearchUserAdapter {

        void submit(SearchUserDetail userDetail);

    }

    @NonNull
    @NotNull
    @Override
    public SearchUserAdapter.Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchUserAdapter.Holder holder, @SuppressLint("RecyclerView") int position) {

        CommonUtils.setAnimation(context,list.get(position).getMyFrame(),holder.profileFrame);
            Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.app_logo).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

//                    holder.forgot_loading.setVisibility(View.GONE);

                    return false;

                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

//                    holder.forgot_loading.setVisibility(View.GONE);

                    return false;

                }
            }).into(holder.user_profile);


        if (list.get(position).getUsername() != null && list.get(position).getUsername().length() != 0) {

            holder.user_Id.setText(list.get(position).getUsername());

        }

        if (list.get(position).getName() != null && list.get(position).getName().length() != 0) {



            holder.user_name.setText(list.get(position).getName());

        }else {

            holder.user_name.setText(list.get(position).getUsername());

        }
        holder.user_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBackFromSearchUserAdapter != null) {
                    callBackFromSearchUserAdapter.submit(list.get(position));
                }

            }
        });
    }

    public void loadData(List<SearchUserDetail> list) {

        this.list = list;
        notifyDataSetChanged();

    }

    public void filterList(List<SearchUserDetail> list) {
        this.list = list;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return (list != null && list.size() != 0 ? list.size() : 0);

    }

    public static class Holder extends RecyclerView.ViewHolder {
        private RelativeLayout user_layout;
        private ImageView user_profile;
        private TextView user_name, user_Id;
        private SVGAImageView profileFrame;
        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);

            user_layout = itemView.findViewById(R.id.user_layout);
            user_profile = itemView.findViewById(R.id.user_profile);
            user_Id = itemView.findViewById(R.id.user_Id);
            user_name = itemView.findViewById(R.id.user_name);
            profileFrame = itemView.findViewById(R.id.profileFrame);


        }
    }
}