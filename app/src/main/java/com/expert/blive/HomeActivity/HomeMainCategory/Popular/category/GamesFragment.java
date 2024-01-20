package com.expert.blive.HomeActivity.HomeMainCategory.Popular.category;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.HomeActivity.Profile.NewprofileFragment;
import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.GamesEndAdapter;
import com.expert.blive.Adapter.GamesMidAdapter;
import com.expert.blive.Adapter.GamesTopAdapter;
import com.expert.blive.R;
import com.expert.blive.SpinWheel.SpinNewActivity;
import com.expert.blive.TicToc.TicTocActivity;
import com.expert.blive.teenpati.Game;
import com.expert.blive.utils.CommonUtils;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamesFragment extends Fragment implements GamesMidAdapter.GameClickCallBack {

    private RecyclerView games_top_recycler_view, games_mid_recycler_view, games_end_recycler_view;
    private LinearLayout spinBtn, game_play, teenPatti;
    private ImageView back;
    private MvvmViewModelClass viewModelClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewModelClass = new ViewModelProvider(this).get(MvvmViewModelClass.class);
        return inflater.inflate(R.layout.fragment_games, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findId(view);
        clicks();

        games_top_recycler_view = view.findViewById(R.id.games_top_recycler_view);
        teenPatti = view.findViewById(R.id.teenPatti);
        GamesTopAdapter gamesTopAdapter = new GamesTopAdapter();
        games_top_recycler_view.setAdapter(gamesTopAdapter);

        List<String> gamesList = new ArrayList<>();
        List<String> gameNamesList = new ArrayList<>();
        List<Integer> gamePicsList = new ArrayList<>();

        gamesList.add("https://showcase.codethislab.com/games/classic_backgammon/");
        gamesList.add("https://showcase.codethislab.com/games/downhill_ski/");
        gamesList.add("https://showcase.codethislab.com/");
        gamesList.add("https://showcase.codethislab.com/games/car_rush/");
        gamesList.add("https://showcase.codethislab.com/games/caribbean_stud_poker/");
        gamesList.add("https://showcase.codethislab.com/games/rugby_rush/");

        gameNamesList.add("Backgamon");
        gameNamesList.add("Downhill Ski");
        gameNamesList.add("Connect the Dots");
        gameNamesList.add("Car Rush");
        gameNamesList.add("Poker");
        gameNamesList.add("Rugby Rush");

        gamePicsList.add(R.drawable.backgamon_game_image);
        gamePicsList.add(R.drawable.down_hill_ski_game_image);
        gamePicsList.add(R.drawable.connect_dots);
        gamePicsList.add(R.drawable.car_rush);
        gamePicsList.add(R.drawable.pocker);
        gamePicsList.add(R.drawable.rugby_game);

        teenPatti.setOnClickListener(view1 -> getUserCoins());

        games_mid_recycler_view = view.findViewById(R.id.games_mid_recycler_view);
        GamesMidAdapter gamesMidAdapter = new GamesMidAdapter(requireContext(), gamesList, gameNamesList, gamePicsList, GamesFragment.this);
        games_mid_recycler_view.setAdapter(gamesMidAdapter);

        games_end_recycler_view = view.findViewById(R.id.games_end_recycler_view);
        GamesEndAdapter gamesEndAdapter = new GamesEndAdapter();
        games_end_recycler_view.setAdapter(gamesEndAdapter);

    }

    private void clicks() {

        back.setOnClickListener(view -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new NewprofileFragment()).addToBackStack(null).commit());

    }

    private void findId(View view) {
        games_top_recycler_view = view.findViewById(R.id.games_top_recycler_view);
        games_mid_recycler_view = view.findViewById(R.id.games_mid_recycler_view);
        games_end_recycler_view = view.findViewById(R.id.games_end_recycler_view);
        spinBtn = view.findViewById(R.id.spinBtnLine);
        game_play = view.findViewById(R.id.game_play);
        back = view.findViewById(R.id.back);

        spinBtn.setOnClickListener(view1 -> startActivity(new Intent(requireContext(), SpinNewActivity.class)));

        game_play.setOnClickListener(view1 -> startActivity(new Intent(requireContext(), TicTocActivity.class)));
    }

    @Override
    public void onGameClick(String gameUrl) {

        GameScreenActivity.gameLink = gameUrl;

        startActivity(new Intent(requireContext(), GameScreenActivity.class));

    }

    private void getUserCoins() {

        viewModelClass.getPurchasedCoin(CommonUtils.getUserId()).observe(requireActivity(), map -> {

            long coins = Long.parseLong(map.get("details").toString());
            FirebaseFirestore Db;
            Db = FirebaseFirestore.getInstance();

            DocumentReference teenPati = Db.collection("SpinnerTimerBools").document("TeenPatti");
            DocumentReference Coll = Db.collection("users").document(CommonUtils.getUserId());

            Map<String, Object> TestData = new HashMap<>();
            Map<String, Object> teenPatiData = new HashMap<>();
            TestData.put("Coins", 10000);
            TestData.put("bv1", 0);
            TestData.put("bv2", 0);
            TestData.put("bv3", 0);
            TestData.put("bv4", 0);
            TestData.put("bv5", 0);
            TestData.put("bv6", 0);
            TestData.put("bv7", 0);
            TestData.put("bv8", 0);
            TestData.put("YourWager", 0);
            TestData.put("id", CommonUtils.getUserId());


        teenPatiData.put("PotB", 0);
        teenPatiData.put("PotA", 0);
        teenPatiData.put("PotC", 0);
        teenPatiData.put("BetAllowed", true);
        teenPatiData.put("timerstart", true);
            teenPatiData.put("timer", 30);
        teenPatiData.put("RoundNum", 0);
        teenPatiData.put("cards", 0);
        teenPatiData.put("A1", true);
        teenPatiData.put("A2", true);
        teenPatiData.put("A3", true);
        teenPatiData.put("C11", "");
        teenPatiData.put("C22", "");
        teenPatiData.put("C33", "");
        teenPatiData.put("LOG", true);
        teenPatiData.put("show", false);


            Coll.set(TestData);
            teenPati.set(teenPatiData);


            startActivity(new Intent(requireActivity(), Game.class));


        });
    }
    @Override
    public void onResume() {

        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.VISIBLE);
    }
}