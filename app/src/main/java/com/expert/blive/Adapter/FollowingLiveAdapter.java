package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.ModelClass.ShowVideoDetail;
import com.expert.blive.R;
import com.google.android.exoplayer2.ui.PlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FollowingLiveAdapter extends RecyclerView.Adapter<FollowingLiveAdapter.ViewHolder> {

    private Context context;

    private List<ShowVideoDetail> videoDetails;

    private int num=2,like_count;

    private  callBackFromFollowingLiveAdapter callBackFromFollowingLiveAdapter;

    public FollowingLiveAdapter(Context context, List<ShowVideoDetail> videoDetails, callBackFromFollowingLiveAdapter callBackFromFollowingLiveAdapter) {
        this.context = context;
        this.videoDetails = videoDetails;
        this.callBackFromFollowingLiveAdapter = callBackFromFollowingLiveAdapter;
    }
    public interface callBackFromFollowingLiveAdapter{

        void submit(ShowVideoDetail videoDetails);

        void operateView(int pos,ShowVideoDetail videoDetails) ;
    }


    @NonNull
    @NotNull
    @Override
    public FollowingLiveAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FollowingLiveAdapter.ViewHolder holder, int position) {

    }

    public void loadData(List<ShowVideoDetail> videoDetails){

        this.videoDetails=videoDetails;

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return (videoDetails!=null && videoDetails.size()!=0 ? videoDetails.size():0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private PlayerView playerview;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            playerview = itemView.findViewById(R.id.playerviews);



        }
    }
}
