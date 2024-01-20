package com.expert.blive.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GamesMidAdapter extends RecyclerView.Adapter<GamesMidAdapter.ViewHolder> {

    private List<String> games_list;
    private List<String> games_names_list;
    private List<Integer> games_pics_list;
    private Context context;
    private GameClickCallBack gameClickCallBack;

    public GamesMidAdapter(Context context, List<String> games_list, List<String> games_names_list, List<Integer> games_pics_list, GameClickCallBack gameClickCallBack) {
        this.context = context;
        this.games_list = games_list;
        this.games_names_list = games_names_list;
        this.games_pics_list = games_pics_list;
        this.gameClickCallBack = gameClickCallBack;
    }

    public interface GameClickCallBack {

        void onGameClick(String gameUrl);

    }

    @NonNull
    @NotNull
    @Override
    public GamesMidAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.games_mid_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GamesMidAdapter.ViewHolder holder, int position) {

        holder.lay_game.setOnClickListener(view -> {

            gameClickCallBack.onGameClick(games_list.get(position));

        });

        holder.txt_game_name.setText(games_names_list.get(position));

        holder.img_game.setImageResource(games_pics_list.get(position));

    }

    @Override
    public int getItemCount() {
        return games_list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout lay_game;
        private TextView txt_game_name;
        private ImageView img_game;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            lay_game = itemView.findViewById(R.id.lay_game);
            txt_game_name = itemView.findViewById(R.id.txt_game_name);
            img_game = itemView.findViewById(R.id.img_game);

        }
    }
}
