package com.expert.blive.ModelClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.expert.blive.Agora.firebase.PKLiveUserModel;
import com.expert.blive.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AdapterPkBattle extends RecyclerView.Adapter<AdapterPkBattle.ViewHolder> {

    private Context context;
    private List<PKLiveUserModel.Detail> list;
    private Select select;
    String name;

    public interface Select {
        void clickLive(String channelName
                , String token, String userChannelId, String rtmToken, String image
                , String followerCount, String followerStatus, String purchasedCoin, String liveName,
                       String liveLevel, String liveStarCount, String liveid, String hostType, String boxstatus, String box, String bool, String count);
    }

    public AdapterPkBattle(Context context, List<PKLiveUserModel.Detail> list,Select select) {
        this.context = context;
        this.list = list;
        this.select = select;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_live, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        long startCount=0;
//
//        if (list.get(position).getCountStar()==null){
//
//        }else {
//            startCount = Long.parseLong(list.get(position).getCountStar());
//
//        }
//
//
//        if (startCount<10000){
//            holder.txtView.setText("0");
//        }else if (startCount<50000){
//            holder.txtView.setText("1");
//        }else if (startCount<200000){
//            holder.txtView.setText("2");
//        } else if (startCount < 1000000) {
//            holder.txtView.setText("Rising");
//        }else if (startCount<2000000){
//            holder.txtView.setText("Big");
//        }else {
//            holder.txtView.setText("Super");
//        }
//
////        if (list.get(position).getHost_status()!=null){
////            if (list.get(position).getHost_status().equalsIgnoreCase("2")) {
////                holder.approveImage.setVisibility(View.VISIBLE);
////            } else {
////                holder.approveImage.setVisibility(View.GONE);
////            }
////        }
//
//
//        String timeAgo = (list.get(position).getCreated().contains("just")) ? list.get(position).getCreated() : list.get(position).getCreated() + " ago";
//        holder.tv_live_time_ago.setText(timeAgo);
//        if (list.get(position).getHostType().equalsIgnoreCase("2")) {
//            holder.tv_pkicon.setVisibility(View.VISIBLE);
//        } else {
//            holder.tv_pkicon.setVisibility(View.GONE);
//        }
//        if (list.get(position).getImage().contains("/uploads/logo/logo.png")) {
//            Glide.with(context).load(R.drawable.app_logo)
//                    .centerCrop()
//                    .placeholder(R.drawable.app_logo)
//                    .into(holder.rv_imgCategory);
//        } else {
//            Glide.with(context).load(list.get(position).getPosterImageString())
//                    .centerCrop()
//                    .placeholder(R.drawable.app_logo)
//                    .into(holder.rv_imgCategory);
//        }
//        if (list.get(position).() == null || (list.get(position).getName().equalsIgnoreCase(""))) {
//            if (list.get(position).getUsername() == null || list.get(position).getUsername().equalsIgnoreCase("")) {
//                name = list.get(position).getUserId();
//            } else {
//                name = list.get(position).getUsername();
//            }
//
//        } else {
//            name = list.get(position).getName();
//        }
//        holder.tv_categoryName.setText(name);
//        holder.tv_likeCount.setText("Followers: " + list.get(position).getFollowerCount());
//
//
//        holder.itemView.setOnClickListener(v -> select.clickLive(list.get(position).getChannelName(), list.get(position).getToken(), list.get(position).getUserId(), list.get(position).getRtmToken()
//                , list.get(position).getImage()
//                , list.get(position).getFollowerCount(), list.get(position).getFollowStatus(), list.get(position).getLiveGiftings()
//                , list.get(position).getName(), list.get(position).getUserLeval(), list.get(position).getStarCount(), list.get(position).getId(), list.get(position).getHostType(), list.get(position).getCheckBoxStatus(), list.get(position).getBox(), list.get(position).getBool(), list.get(position).getCount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView rv_imgCategory;
        private ImageView approveImage;
        private TextView tv_categoryName, tv_likeCount, tv_live_time_ago, tv_pkicon,txtView;
        private LottieAnimationView animationView;
        private LinearLayout starCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_pkicon = itemView.findViewById(R.id.tv_pkicon);
            approveImage = itemView.findViewById(R.id.approveImage);
            txtView = itemView.findViewById(R.id.txtView);
            starCount = itemView.findViewById(R.id.starCount);
            tv_live_time_ago = itemView.findViewById(R.id.tv_live_time_ago);
            rv_imgCategory = itemView.findViewById(R.id.rv_imgCategory);
            tv_likeCount = itemView.findViewById(R.id.tv_likeCount);
            tv_categoryName = itemView.findViewById(R.id.tv_categoryName);

//            animationView = itemView.findViewById(R.id.animationView);
//            animationView.addValueCallback(
//                    new KeyPath("**"),
//                    LottieProperty.COLOR_FILTER,
//                    new SimpleLottieValueCallback<ColorFilter>() {
//                        @Override
//                        public ColorFilter getValue(LottieFrameInfo<ColorFilter> frameInfo) {
////                            return new PorterDuffColorFilter(Color.parseColor("#FF673AB7"), PorterDuff.Mode.SRC_ATOP);
//                            return new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
//                        }
//                    }
//            );

        }
    }
}
