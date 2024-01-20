package com.expert.blive.bottomteenpati;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.ModelClass.SpinOneModal;
import com.expert.blive.teenpati.History;
import com.expert.blive.teenpati.Rules;
import com.expert.blive.utils.CommonUtils;
import com.expert.blive.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.SetOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class GameBottomFragment extends BottomSheetDialogFragment {
    private View view;
    private FirebaseAuth firebaseAuth;
    boolean TimerDisplay = false;
    boolean ShowCardsBanner = false;
    boolean startTimerOne = false;
    private ImageButton startButton;
    boolean BetAllow = false;
    int[][] Trail = {{4, 4, 4}, {1, 1, 1}, {12, 12, 12}};
    int[][] Pure = {{2, 3, 4}, {8, 9, 10}, {1, 2, 3}, {1, 13, 12}, {7, 6, 5}};
    int[][] Sequence = {{12, 11, 10}, {5, 4, 3}, {10, 9, 8}, {4, 5, 6}, {9, 10, 11}, {1, 13, 12}, {13, 12, 11}};
    int[][] Colorss = {{1, 13, 11}, {1, 7, 3}, {6, 4, 2}, {13, 3, 8}, {1, 5, 9}, {10, 5, 12}, {13, 2, 4}, {12, 7, 3}, {1, 10, 4}, {11, 8, 5}};
    int[][] Pair = {{1, 1, 13}, {1, 1, 3}, {6, 6, 9}, {11, 11, 2}, {13, 13, 7}, {3, 3, 6}, {2, 7, 7}, {12, 11, 11}, {10, 4, 4}, {9, 10, 10}, {1, 2, 2}, {4, 8, 8}};
    int[][] HighCard = {{1, 9, 13}, {3, 6, 1}, {12, 9, 10}, {1, 6, 9}, {10, 4, 2}, {3, 8, 10}, {13, 7, 4}, {2, 10, 5}, {1, 8, 11}, {1, 7, 3}, {6, 12, 2}, {13, 10, 8}, {1, 5, 9}, {10, 5, 12}, {13, 2, 4}, {12, 7, 3}, {1, 10, 4}, {11, 8, 5}};

    int[] Cards = {R.drawable.c1,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c4,
            R.drawable.c5,
            R.drawable.c6,
            R.drawable.c7,
            R.drawable.c8,
            R.drawable.c9,
            R.drawable.c10,
            R.drawable.c11,
            R.drawable.c12,
            R.drawable.c13,
            R.drawable.d1,
            R.drawable.d2,
            R.drawable.d3,
            R.drawable.d4,
            R.drawable.d5,
            R.drawable.d6,
            R.drawable.d7,
            R.drawable.d8,
            R.drawable.d9,
            R.drawable.d10,
            R.drawable.d11,
            R.drawable.d12,
            R.drawable.d13,
            R.drawable.h1,
            R.drawable.h2,
            R.drawable.h3,
            R.drawable.h4,
            R.drawable.h5,
            R.drawable.h6,
            R.drawable.h7,
            R.drawable.h8,
            R.drawable.h9,
            R.drawable.h10,
            R.drawable.h11,
            R.drawable.h12,
            R.drawable.h13,
            R.drawable.s1,
            R.drawable.s2,
            R.drawable.s3,
            R.drawable.s4,
            R.drawable.s5,
            R.drawable.s6,
            R.drawable.s7,
            R.drawable.s8,
            R.drawable.s9,
            R.drawable.s9,
            R.drawable.s10,
            R.drawable.s11,
            R.drawable.s12,
            R.drawable.s13,
    };

    ImageView[] row1 = {};
    ImageView[] row2 = {};
    ImageView[] row3 = {};
    ImageView[] Bannerss = {};
    TextView[] BannerTextss = {};
    ImageView[] RowMasks = {};
    ImageView[] BirdMasks = {};
    ImageView[] CardPotMasks = {};
    long UserCoins = 0;
    long DeviceCoins = 0;
    long YourWager = 0;
    long PrizeCoins = 0;
    double MulPrize = 2.9;
    int RoundNum = 0;
    int MyPotA = 0;
    int MyPotB = 0;
    int MyPotC = 0;
    long GPotA = 0;
    long GPotB = 0;
    long GPotC = 0;
    boolean SendCoins = false;
    ListenerRegistration listenerRegdoc;
    ListenerRegistration listenerUserReg;
    boolean BetOnGoing = false;
    boolean[] BIRDS = {false, false, false};

    public GameBottomFragment() {

    }

    public enum CoinValues {
        None, Coins100, Coins1k, Coins10k, Coins100k;

        @SuppressLint("NotConstructor")
        private int CoinValues(CoinValues c) {
            switch (c) {
                case None:
                    return 0;
                case Coins100:
                    return 100;
                case Coins1k:
                    return 1000;
                case Coins10k:
                    return 10000;
                case Coins100k:
                    return 100000;
                default:
                    return 0;
            }

        }
    }

  CoinValues CV = CoinValues.None;

    @Override
    public void dismiss() {


        if (!BetOnGoing) {
            super.dismiss();
            FirebaseFirestore db;
            db = FirebaseFirestore.getInstance();
            final DocumentReference UserdocRef = db.collection("users").document(CommonUtils.getUserId());
            Map<String, Object> TestDat = new HashMap<>();
            TestDat.put("LOG", false);
            UserdocRef.update(TestDat);
            if (listenerRegdoc != null) {
                listenerRegdoc.remove();
            }
            if (listenerUserReg != null) {
                listenerUserReg.remove();
            }
        } else {
            Toast.makeText(requireContext(), "Bet is Ongoing!!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game_bottom, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        startButton = view.findViewById(R.id.startbutton);

        CheckforTimer();
        startButton.setOnClickListener(view -> {
            StartTimer(30);



        });
        row1 = new ImageView[]{view.findViewById(R.id.card01), view.findViewById(R.id.card02), view.findViewById(R.id.card03)};
        row2 = new ImageView[]{view.findViewById(R.id.card04), view.findViewById(R.id.card05), view.findViewById(R.id.card06)};
        row3 = new ImageView[]{view.findViewById(R.id.card07), view.findViewById(R.id.card08), view.findViewById(R.id.card09)};

        Bannerss = new ImageView[]{view.findViewById(R.id.cardrow01banner), view.findViewById(R.id.cardrow02banner), view.findViewById(R.id.cardrow03banner)};
        BannerTextss = new TextView[]{view.findViewById(R.id.cardrow01bannerText), view.findViewById(R.id.cardrow02bannerText), view.findViewById(R.id.cardrow03bannerText)};


        RowMasks = new ImageView[]{view.findViewById(R.id.cr01mask), view.findViewById(R.id.cr02mask), view.findViewById(R.id.cr03mask)};
        BirdMasks = new ImageView[]{view.findViewById(R.id.chrbmask), view.findViewById(R.id.chrpmask), view.findViewById(R.id.chrRmask)};
        CardPotMasks = new ImageView[]{view.findViewById(R.id.CardAmask), view.findViewById(R.id.CardBmask), view.findViewById(R.id.CardCmask)};

        for (int i = 0; i < 3; i++) {
            Bannerss[i].setVisibility(View.INVISIBLE);
            BannerTextss[i].setVisibility(View.INVISIBLE);

            RowMasks[i].setVisibility(View.INVISIBLE);
            BirdMasks[i].setVisibility(View.INVISIBLE);
            CardPotMasks[i].setVisibility(View.INVISIBLE);
        }

        ImageButton Chip100 = view.findViewById(R.id.chip100);
        ImageButton Chip1000 = view.findViewById(R.id.chip1000);
        ImageButton Chip10k = view.findViewById(R.id.chip10k);
        ImageButton Chip100k = view.findViewById(R.id.chip100k);

        ImageButton[] ChipButtons = {view.findViewById(R.id.chip100), view.findViewById(R.id.chip1000), view.findViewById(R.id.chip10k), view.findViewById(R.id.chip100k)};


        Chip100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserCoins==0){
                    Toast.makeText(requireContext(), "Add Coin First ", Toast.LENGTH_SHORT).show();
                }else {
                    if (BetAllow) {

                        if (CV != CoinValues.Coins100) {

                            for (int i = 0; i < 4; i++) {
                                if (ChipButtons[i] == Chip100) {
                                    ChipButtons[i].setImageResource(R.drawable.selected_chip);
                                    CV = CoinValues.Coins100;
                                } else {

                                    ChipButtons[i].setImageResource(R.drawable.normal_chip);

                                }
                            }
                        } else {
                            Chip100.setImageResource(R.drawable.normal_chip);
                            CV = CoinValues.None;

                        }
                    } else {
                        Chip100.setImageResource(R.drawable.normal_chip);
                        CV = CoinValues.None;


                    }


                }


            }
        });

        Chip1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserCoins==0){
                    Toast.makeText(requireContext(), "Add Coin First ", Toast.LENGTH_SHORT).show();
                }else {
                    if (BetAllow) {
                        if (CV != CoinValues.Coins1k) {
                            for (int i = 0; i < 4; i++) {
                                if (ChipButtons[i] == Chip1000) {
                                    ChipButtons[i].setImageResource(R.drawable.selected_chip);
                                    CV = CoinValues.Coins1k;
                                } else {
                                    ChipButtons[i].setImageResource(R.drawable.normal_chip);
                                }
                            }
                        } else {
                            Chip1000.setImageResource(R.drawable.normal_chip);
                            CV = CoinValues.None;
                        }
                    } else {
                        Chip1000.setImageResource(R.drawable.normal_chip);
                        CV = CoinValues.None;
                    }

                }
            }
        });

        Chip10k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (UserCoins==0){
                    Toast.makeText(requireContext(), "Add Coin First ", Toast.LENGTH_SHORT).show();
                }else {

                    if (BetAllow) {
                        if (CV != CoinValues.Coins10k) {
                            for (int i = 0; i < 4; i++) {
                                if (ChipButtons[i] == Chip10k) {
                                    ChipButtons[i].setImageResource(R.drawable.selected_chip);
                                    CV = CoinValues.Coins10k;
                                } else {
                                    ChipButtons[i].setImageResource(R.drawable.normal_chip);
                                }
                            }
                        } else {
                            Chip10k.setImageResource(R.drawable.normal_chip);
                            CV = CoinValues.None;
                        }
                    } else {
                        Chip10k.setImageResource(R.drawable.normal_chip);
                        CV = CoinValues.None;
                    }
                }

            }
        });

        Chip100k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserCoins==0){
                    Toast.makeText(requireContext(), "Add Coin First ", Toast.LENGTH_SHORT).show();
                }else {
                    if (BetAllow) {
                        if (CV != CoinValues.Coins100k) {
                            for (int i = 0; i < 4; i++) {
                                if (ChipButtons[i] == Chip100k) {
                                    ChipButtons[i].setImageResource(R.drawable.selected_chip);
                                    CV = CoinValues.Coins100k;
                                } else {
                                    ChipButtons[i].setImageResource(R.drawable.normal_chip);
                                }
                            }
                        } else {
                            Chip100k.setImageResource(R.drawable.normal_chip);
                            CV = CoinValues.None;
                        }
                    } else {
                        Chip100k.setImageResource(R.drawable.normal_chip);
                        CV = CoinValues.None;
                    }

                }
            }
        });


        ImageButton BBirdButton = view.findViewById(R.id.app_logoChair);

        BBirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BetAllow) {
//                    selectPot = true;
//                    BirdMasks[0].setVisibility(View.VISIBLE);
//                    BirdMasks[1].setVisibility(View.INVISIBLE);
//                    BirdMasks[2].setVisibility(View.INVISIBLE);

                    if (BETAllowed(0)) {

                        if (CV.CoinValues(CV) == 0) {
                            Toast.makeText(requireContext(), "Select Amount First", Toast.LENGTH_SHORT).show();
                        } else {
                            BetArea(0, CV.CoinValues(CV));

                        }
//                        BetArea(0, CV.CoinValues(CV));
                    } else {
                        Toast.makeText(requireContext(), "MAx 2 Pot Selection only", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        ImageButton PBirdButton = view.findViewById(R.id.app_logoChair);

        PBirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BetAllow) {
//                    selectPot = true;
//                    BirdMasks[0].setVisibility(View.INVISIBLE);
//                    BirdMasks[1].setVisibility(View.VISIBLE);
//                    BirdMasks[2].setVisibility(View.INVISIBLE);


                    if (BETAllowed(1)) {

                        if (CV.CoinValues(CV) == 0) {
                            Toast.makeText(requireContext(), "Select Amount First", Toast.LENGTH_SHORT).show();
                        } else {
                            BetArea(1, CV.CoinValues(CV));

                        }

//                        BetArea(1, CV.CoinValues(CV));
                    } else {
                        Toast.makeText(requireContext(), "MAx 2 Pot Selection only", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        ImageButton RBirdButton = view.findViewById(R.id.app_logoChair);

        RBirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BetAllow) {
//                    selectPot = true;
//                    BirdMasks[0].setVisibility(View.INVISIBLE);
//                    BirdMasks[1].setVisibility(View.INVISIBLE);
//                    BirdMasks[2].setVisibility(View.VISIBLE);
                    if (BETAllowed(2)) {
                        if (CV.CoinValues(CV) == 0) {
                            Toast.makeText(requireContext(), "Select Amount First", Toast.LENGTH_SHORT).show();
                        } else {
                            BetArea(2, CV.CoinValues(CV));

                        }

                    } else {
                        Toast.makeText(requireContext(), "MAx 2 Pot Selection only", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        ShowDoneScreen(0);
        CloseAllRanks(0);
        CloseAllRanks(1);

        ImageButton RankButton = view.findViewById(R.id.rankbutton);
        RankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRankButtonClick(0);
            }
        });
        ImageButton DailyCloseButton = view.findViewById(R.id.DailyCloseButton);
        DailyCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseAllRanks(0);
            }
        });


        ImageButton WeeklyRankButton = view.findViewById(R.id.WeeklyRankbutton);
        WeeklyRankButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnRankButtonClick(1);
            }
        });

        ImageButton WeeklyCloseButton = view.findViewById(R.id.WeeklyCloseButton);
        WeeklyCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CloseAllRanks(1);
            }
        });

        ImageButton RulesButton = view.findViewById(R.id.rulesbutton);
        RulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireContext(), Rules.class);
                startActivity(i);
            }
        });
        ImageButton Histry = view.findViewById(R.id.historyButton);
        Histry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(requireContext(), History.class);
                startActivity(i);
            }
        });
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupRatio(bottomSheetDialog);
            }
        });
        return dialog;
        
    }
    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheet.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.trans)));
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = displaySize(getActivity())[1] * 55 / 100;
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }
    private static int[] displaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        Log.i("height", String.valueOf(height));
        Log.i("width", String.valueOf(width));
        return new int[]{width, height};
    }

    @Override
    public void onStop() {
        super.onStop();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        final DocumentReference UserdocRef = db.collection("users").document(CommonUtils.getUserId());
        Map<String, Object> TestDat = new HashMap<>();
        TestDat.put("LOG", false);
        UserdocRef.update(TestDat);
        
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseFirestore db;

        db = FirebaseFirestore.getInstance();
        final DocumentReference UserdocRef = db.collection("users").document(CommonUtils.getUserId());
        //        FirebaseFirestore Db;
//        Db= FirebaseFirestore.getInstance();
        DocumentReference Coll = db.collection("SpinnerTimerBools").document("TeenPatti");
        Map<String, Object> TestDat = new HashMap<>();
        TestDat.put("LOG", true);
        UserdocRef.update(TestDat);
        Coll.update(TestDat);

    }


    void BetStoped(int n) {
        ImageView Banner = view.findViewById(R.id.StopBetsBanner);
        TextView Txt = view.findViewById(R.id.StopBetsBannerText);
        if (n == 0) {
            Banner.setVisibility(View.GONE);
            Txt.setVisibility(View.GONE);
        } else if (n == 1) {
            Banner.setVisibility(View.VISIBLE);
            Txt.setVisibility(View.VISIBLE);
            startButton.setVisibility(View.GONE);

        }
    }
    
    void CheckforTimer() {
        BetStoped(0);
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("SpinnerTimerBools").document("TeenPatti");
        final DocumentReference UserdocRef = db.collection("users").document(CommonUtils.getUserId());
        Map<String, Object> TestDat = new HashMap<>();
        TestDat.put("LOG", true);
        UserdocRef.update(TestDat);

        listenerUserReg = UserdocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("TAGError", String.valueOf(error));
                    return;

                }
                if (snapshot != null && snapshot.exists()) {

                    //Long Coins=0L;
                    if (snapshot.get("TEarning") != null) {
                        CheckTodayEarnings(snapshot.getLong("TEarning"));
                    }

                    if (snapshot.get("Coins") != null) {

                        //Coins = snapshot.getLong("Coins");
                        UserCoins = snapshot.getLong("Coins");
                        TextView CoinsText = view.findViewById(R.id.CoinsText);
                        CoinsText.setText(prettyCount(UserCoins));


                    } else {
                        UserCoins = 0;
                    }


                    if (snapshot.get("YourWager") != null) {
                        YourWager = snapshot.getLong("YourWager");
                    } else {
                        YourWager = 0;
                    }

                    if (snapshot.getLong("MyPotA") != null && snapshot.getLong("MyPotB") != null && snapshot.getLong("MyPotC") != null) {
                        Long PotA = snapshot.getLong("MyPotA");
                        Long PotB = snapshot.getLong("MyPotB");
                        Long PotC = snapshot.getLong("MyPotC");
                        String PotAString;
                        String PotBString;
                        String PotCString;
                        if (MyPotA != 0) {
                            PotAString = "You: " + prettyCount(MyPotA);

                        } else {
                            PotAString = "You: " + prettyCount(PotA);
                        }
                        if (MyPotB != 0) {

                            PotBString = "You: " + prettyCount(MyPotB);

                        } else {
                            PotBString = "You: " + prettyCount(PotB);
                        }
                        if (MyPotC != 0) {

                            PotCString = "You: " + prettyCount(MyPotC);

                        } else {
                            PotCString = "You: " + prettyCount(PotC);
                        }


                        TextView PA = view.findViewById(R.id.PotAText);
                        PA.setText(PotAString);
                        TextView PB = view.findViewById(R.id.PotBText);
                        PB.setText(PotBString);
                        TextView PC = view.findViewById(R.id.PotCText);
                        PC.setText(PotCString);


                    }


                } else {
                    Log.d("TagNull", "Current data: null");


                }
            }
        });


        listenerRegdoc = docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("TAGError", String.valueOf(error));
                    return;
                }

                if (snapshot != null && snapshot.exists()) {

                    boolean TimerStart = (Boolean) snapshot.get("timerstart");
                    TextView TM = view.findViewById(R.id.TimerText);
                    String Tim = snapshot.get("timer").toString();
                    TM.setText(Tim);
                    boolean RoundAlowed = (Boolean) snapshot.get("BetAllowed");

                    if (snapshot.get("Today") != null) {
                        Date DT = snapshot.getTimestamp("Today").toDate();

                        ResetEarnings(DT);
                    }

                    if (snapshot.get("RoundNum") != null) {
                        long number = snapshot.getLong("RoundNum");
                        int nm = (int) number;

                        RoundNum = nm;
                    }
                    TextView CoinsText = view.findViewById(R.id.CoinsText);
                    if (DeviceCoins != 0) {
                        CoinsText.setText(prettyCount(UserCoins - DeviceCoins));

                    } else {
                        CoinsText.setText(prettyCount(UserCoins));
                    }

                    if (snapshot.get("PotA") != null && snapshot.get("PotB") != null && snapshot.get("PotC") != null) {
                        TextView PA = view.findViewById(R.id.AText);
                        TextView PB = view.findViewById(R.id.BText);
                        TextView PC = view.findViewById(R.id.CText);
                        long PAlong = snapshot.getLong("PotA");
                        long PBlong = snapshot.getLong("PotB");
                        long PClong = snapshot.getLong("PotC");
                        PA.setText("Pot:" + prettyCount(PAlong));
                        PB.setText("Pot:" + prettyCount(PBlong));
                        PC.setText("Pot:" + prettyCount(PClong));
                    }

                    if (snapshot.get("timer") != null) {
                        long number = snapshot.getLong("timer");
                        int nm = (int) number;

                        if (nm <= 0) {
                            SendCoins = false;
                            //Log.d("CoinsStop","Cons Bets can be sent now");
                        }
                        if (!SendCoins) {
                            if (nm == 6) {

                                DeviceCoins = 0;
                            }
                            if (nm == 7) {

                                Log.d("Coins", "Cons Bets sent ");
                                SendCoins = true;
                                FinalBets();
                            }
                        }

                        if (!startTimerOne) {
                            startTimerOne = true;
                            StartTimer(nm);
                        }
                        if (nm > 0 && nm < 8) {
                            BetStoped(1);

                            BetOnGoing = true;
                        } else {

                            BetStoped(0);

                        }


                    }
                    if (RoundAlowed) {
                        BetAllow = true;
                        SetRandomPots();
                    } else {
                        BetAllow = false;
                    }
                    if (TimerStart) {
                        TimerDisplay = true;
                        TimerDisplayCheck();
                    } else {
                        TimerDisplay = false;
                        TimerDisplayCheck();
                    }
                    boolean ShowBannerCards = (Boolean) snapshot.get("show");
                    if (ShowBannerCards) {
                        ShowBanner();
                        ShowCardsBanner = true;

                    } else {
                        ShowCardsBanner = false;
                    }
                    CardsBannerDisplayCheck();


                } else {
                    Log.d("TagNull", "Current data: null");

                }
            }
        });

    }

    void ShowAllCards() {
        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        DocumentReference docRef = Db.collection("SpinnerTimerBools").document("TeenPatti");

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null && snapshot.exists()) {

                        ArrayList<Long> Cr = (ArrayList<Long>) snapshot.get("cards");
                        ArrayList<String> Typess = new ArrayList<String>();
                        ArrayList<Boolean> Areass = new ArrayList<Boolean>();


                        Areass.add((Boolean) snapshot.get("A1"));
                        Areass.add((Boolean) snapshot.get("A2"));
                        Areass.add((Boolean) snapshot.get("A3"));


                        Typess.add(String.valueOf(snapshot.get("C11")));
                        Typess.add(String.valueOf(snapshot.get("C22")));
                        Typess.add(String.valueOf(snapshot.get("C33")));
                        getBackgroundImageGetLoopCards(Cards, row1, row2, row3, Cr, Typess, Areass);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                }


            }
        });
    }
    void ShowBanner() {
        for (int a = 0; a < 4; a++) {
            Handler handler1 = new Handler();
            int AA = a;
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (AA == 2) {
                        ShowAllCards();
                    }

                }
            }, 1000 * a);
        }

    }
    void CheckTodayEarnings(long TTE) {
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("SpinnerTimerBools").document("TeenPatti");
        final DocumentReference UDoc = db.collection("users").document(CommonUtils.getUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.get("Limit") != null) {
                            long Lim = document.getLong("Limit");
                            long TE = TTE;

                            if (TE >= Lim) {
                                Toast.makeText(requireContext(), "You have exceeded today's game limit.", Toast.LENGTH_LONG).show();
//                                super.onBackPressed();
                                Map<String, Object> TestDat = new HashMap<>();
                                TestDat.put("LOG", false);
                                UDoc.update(TestDat);
                                if (listenerRegdoc != null) {
                                    listenerRegdoc.remove();
                                }
                                if (listenerUserReg != null) {
                                    listenerUserReg.remove();
                                }
                            }
                        }

                    } else {

                    }
                } else {

                }
            }
        });
    }

    void setEarningsToday() {
        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = Db.collection("users").document(CommonUtils.getUserId());
        Map<String, Object> MyData = new HashMap<>();
        MyData.put("TEarning", FieldValue.increment(PrizeCoins));
        MyData.put("Today", Timestamp.now());
        docRef.set(MyData, SetOptions.merge());
    }

    void ResetEarnings(Date DTT) {
        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = Db.collection("users").document(CommonUtils.getUserId());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.get("Today") != null) {
                            Date TDT = document.getTimestamp("Today").toDate();

                            if (DTT.getDay() != TDT.getDay() || DTT.getMonth() != TDT.getMonth() || DTT.getYear() != TDT.getYear()) {
                                Map<String, Object> MyData = new HashMap<>();
                                MyData.put("TEarning", 0);
                                docRef.set(MyData, SetOptions.merge());
                            }
                        }

                    } else {

                    }
                } else {

                }
            }
        });


    }

    Boolean BETAllowed(int i) {
        int K = 0;
        for (int j = 0; j < 3; j++) {
            if (BIRDS[j] == false) {
                K++;
            }
        }

        if (K == 3) {
            BIRDS[i] = true;
            return true;
        } else {
            if (K == 2) {
                if (BIRDS[i] == false) {
                    BIRDS[i] = true;
                    return true;
                } else {
                    return true;
                }
            } else {
                if (BIRDS[i] == false) {
                    return false;
                } else {
                    return true;
                }

            }
        }

    }


    void BetArea(int Area, int Coins) {

        //setPot Value

        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        DocumentReference Coll = Db.collection("SpinnerTimerBools").document("TeenPatti");
        DocumentReference UserColl = Db.collection("users").document(CommonUtils.getUserId());
        int UCoins = (int) (UserCoins - DeviceCoins);

        if (Area == 0) {
            if (UCoins - Coins >= 0) {
                MyPotA += Coins;
                DeviceCoins += Coins;
                GPotA += Coins;
                BetCoins(Coins);
                Map<String, Object> TestData1 = new HashMap<>();
                Map<String, Object> TestData2 = new HashMap<>();
                TestData1.put("PotA", FieldValue.increment(Coins));
                TestData2.put("MyPotA", FieldValue.increment(Coins));
                Coll.update(TestData1);
                UserColl.update(TestData2);

            }
        } else if (Area == 1) {
            if (UCoins - Coins >= 0) {
                MyPotB += Coins;
                DeviceCoins += Coins;
                GPotB += Coins;
                BetCoins(Coins);
                Map<String, Object> TestData1 = new HashMap<>();
                Map<String, Object> TestData2 = new HashMap<>();
                TestData1.put("PotB", FieldValue.increment(Coins));
                TestData2.put("MyPotB", FieldValue.increment(Coins));
                Coll.update(TestData1);
                UserColl.update(TestData2);

            }
        } else if (Area == 2) {
            if (UCoins - Coins >= 0) {
                MyPotC += Coins;
                DeviceCoins += Coins;
                GPotC += Coins;
                BetCoins(Coins);
                Map<String, Object> TestData1 = new HashMap<>();
                Map<String, Object> TestData2 = new HashMap<>();
                TestData1.put("PotC", FieldValue.increment(Coins));
                TestData2.put("MyPotC", FieldValue.increment(Coins));
                Coll.update(TestData1);
                UserColl.update(TestData2);

            }
        }
        String PotAString = "You: " + prettyCount(MyPotA);
        String PotBString = "You: " + prettyCount(MyPotB);
        String PotCString = "You: " + prettyCount(MyPotC);

        TextView PA = view.findViewById(R.id.PotAText);
        PA.setText(PotAString);
        TextView PB = view.findViewById(R.id.PotBText);
        PB.setText(PotBString);
        TextView PC = view.findViewById(R.id.PotCText);
        PC.setText(PotCString);
    }
    void BetCoins(int Coins) {
        int UCoins = (int) UserCoins;
        if (UCoins - Coins >= 0) {
            FirebaseFirestore Db;
            Db = FirebaseFirestore.getInstance();
            DocumentReference Coll = Db.collection("users").document(CommonUtils.getUserId());
            Map<String, Object> TestData = new HashMap<>();
            TestData.put("Coins", FieldValue.increment(-Coins));
            TestData.put("YourWager", FieldValue.increment(Coins));
            Coll.update(TestData);
            Log.d("CoinsSend", "Coinsa haahahaha");
        }
    }
    public String prettyCount(Number number) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.00").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat().format(numValue);
        }
    }

    void ShowDoneScreen(int i) {
        RelativeLayout RL = view.findViewById(R.id.roundDoneScreen);
        TextView Wager = view.findViewById(R.id.WagerText);
        TextView WinAm = view.findViewById(R.id.PrizeText);
        if (RL != null) {
            Wager.setText(String.valueOf(YourWager));
            WinAm.setText(String.valueOf(PrizeCoins));
            if (i == 0) {
                RL.setVisibility(View.INVISIBLE);
            } else {

                SetUserBetOnResults();
                RL.setVisibility(View.VISIBLE);
            }
        }

    }
    int SuccesCounter = 0;
    int FailureCounter = 0;
    void SetUserBetOnResults() {

        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        DocumentReference teenPati = Db.collection("SpinnerTimerBools").document("TeenPatti");

        final DocumentReference Coll = Db.collection("SpinnerTimerBools").document("TeenPatti").collection("Results").document(String.valueOf(RoundNum)).collection("Game").document(CommonUtils.getUserId());
        final DocumentReference docRef = Db.collection("users").document(CommonUtils.getUserId());
        Map<String, Object> TestData = new HashMap<>();
        TestData.put("BetCoins", YourWager);
        TestData.put("WinCoins", PrizeCoins);


        Coll.set(TestData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        SuccesCounter++;
                        if (SuccesCounter % 2 != 0) {
                            BIRDS[0] = false;
                            BIRDS[1] = false;
                            BIRDS[2] = false;
                            if (YourWager != 0) {
                                setEarningsToday();
                            }
                            Log.d("TAG", "DocumentSnapshot successfully written!" + SuccesCounter);
                            //SetPotValuesDefault(0);
                            long C = UserCoins + PrizeCoins;

                            addcoins(PrizeCoins);
                            Log.d("Amount", String.valueOf(UserCoins) + "    " + String.valueOf(PrizeCoins));
                            Map<String, Object> TestData1 = new HashMap<>();
                            TestData1.put("MyPotA", 0);
                            TestData1.put("MyPotB", 0);
                            TestData1.put("MyPotC", 0);
                            TestData1.put("YourWager", 0);
                            TestData1.put("Coins", C);
                            docRef.update(TestData1);
                            PrizeCoins = 0;
                            GPotA = GPotB = GPotC = 0;
                            BetOnGoing = false;
                            Map<String, Object> teenPatiData = new HashMap<>();

//                            teenPatiData.put("PotB", 0);
//                            teenPatiData.put("PotA", 0);
//                            teenPatiData.put("PotC", 0);
//                            teenPatiData.put("BetAllowed", true);
//                            teenPatiData.put("timerstart", true);
                            teenPatiData.put("timer", 30);
//                            teenPatiData.put("RoundNum", 0);
//                            teenPatiData.put("cards", 0);
//                            teenPatiData.put("A1", true);
//                            teenPatiData.put("A2", true);
//                            teenPatiData.put("A3", true);
//                            teenPatiData.put("C11", "");
//                            teenPatiData.put("C22", "");
//                            teenPatiData.put("C33", "");
//                            teenPatiData.put("LOG", true);
//                            teenPatiData.put("show", false);
                            teenPati.update(teenPatiData);
//                            StartTimer(30);

                        } else {
                            SuccesCounter = 0;
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        FailureCounter++;

                        if (FailureCounter % 2 != 0) {
                            Log.w("TAG", "Error writing document" + FailureCounter, e);
                            //SetPotValuesDefault(0);
                            long C = UserCoins + PrizeCoins;
                            Map<String, Object> TestData1 = new HashMap<>();
                            TestData1.put("MyPotA", 0);
                            TestData1.put("MyPotB", 0);
                            TestData1.put("MyPotC", 0);
                            TestData1.put("YourWager", 0);
                            TestData1.put("Coins", C);
                            docRef.update(TestData1);
                            PrizeCoins = 0;
                            GPotA = GPotB = GPotC = 0;
                        } else {
                            FailureCounter = 0;
                        }


                    }
                });


    }
    private void addcoins(long prizeCoins) {
        if (PrizeCoins > 0) {
            new MvvmViewModelClass().addPurchaseCoin(CommonUtils.getUserId(), String.valueOf(prizeCoins)).observe(requireActivity(), new Observer<SpinOneModal>() {
                @Override
                public void onChanged(SpinOneModal addPurchaseCoin) {
                    Toast.makeText(requireContext(), "" + addPurchaseCoin.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    void CloseAllRanks(int i) {
        if (i == 0) {
            RelativeLayout RL = view.findViewById(R.id.RankScreenDaily);
            RL.setVisibility(View.INVISIBLE);
        } else if (i == 1) {
            RelativeLayout WRL = view.findViewById(R.id.RankScreenWeekly);
            WRL.setVisibility(View.INVISIBLE);
        }


    }

    void FinalBets() {
        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        DocumentReference Coll = Db.collection("SpinnerTimerBools").document("TeenPatti");
        DocumentReference UserColl = Db.collection("users").document(CommonUtils.getUserId());

        int MyCoins = MyPotA + MyPotB + MyPotC;
        //twice amount
        Map<String, Object> TestData1 = new HashMap<>();
        Map<String, Object> TestData2 = new HashMap<>();
        TestData1.put("PotA", FieldValue.increment(MyPotA));
        TestData2.put("MyPotA", FieldValue.increment(MyPotA));
        TestData1.put("PotB", FieldValue.increment(MyPotB));
        TestData2.put("MyPotB", FieldValue.increment(MyPotB));
        TestData1.put("PotC", FieldValue.increment(MyPotC));
        TestData2.put("MyPotC", FieldValue.increment(MyPotC));
        TestData2.put("Coins", FieldValue.increment(-MyCoins));
        TestData2.put("YourWager", FieldValue.increment(MyCoins));
        Coll.update(TestData1);
        UserColl.update(TestData2);
        minPurchaseCoin(2 * MyCoins);
        MyPotA = MyPotB = MyPotC = 0;


        DeviceCoins = 0;

    }
    private void minPurchaseCoin(int increment) {
        if (increment > 0) {
            new MvvmViewModelClass().addPurchaseCoin(CommonUtils.getUserId(), String.valueOf(increment)).observe(requireActivity(), new Observer<SpinOneModal>() {
                @Override
                public void onChanged(SpinOneModal addPurchaseCoin) {
                    Toast.makeText(requireContext(), "" + addPurchaseCoin.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    void StartTimer(int nm) {


        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        DocumentReference Coll = Db.collection("SpinnerTimerBools").document("TeenPatti");

        Map<String, Object> TestData = new HashMap<>();
        TestData.put("BetAllowed", true);
        TestData.put("timerstart", true);
        Coll.update(TestData);
        int Timerr = nm;
        for (int a = 0; a <= Timerr; a++) {
            Handler handler1 = new Handler();
            int AA = a;
            handler1.postDelayed(new Runnable() {

                @Override
                public void run() {
                    Map<String, Object> TestD = new HashMap<>();
                    TestD.put("timer", Timerr - AA);
                    startButton.setVisibility(View.GONE);


//                    Toast.makeText(Game.this, "coin"+CV, Toast.LENGTH_SHORT).show();

                    Coll.update(TestD);
                    if (Timerr - AA == 10) {

                        StopBetting();
                    }
                    if (Timerr - AA == 9) {
                        GenCards();
                    }
                    if (Timerr - AA == 0) {

                        startButton.setVisibility(View.VISIBLE);
                        Map<String, Object> TestData = new HashMap<>();
                        TestData.put("timerstart", false);
                        TestData.put("show", true);
                        Coll.update(TestData);
                        SetResults();
                    }

                }
            }, 1000 * a);
        }
    }

    void StopBetting() {
        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        DocumentReference docRef = Db.collection("SpinnerTimerBools").document("TeenPatti");
        Map<String, Object> TestData = new HashMap<>();
        TestData.put("BetAllowed", false);
        docRef.update(TestData);
    }
    void SetResults() {
        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        final DocumentReference Coll = Db.collection("SpinnerTimerBools").document("TeenPatti");
        Coll.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null && snapshot.exists()) {

                        ArrayList<Boolean> Area = new ArrayList<Boolean>();
                        Area.add((Boolean) snapshot.get("A1"));
                        Area.add((Boolean) snapshot.get("A2"));
                        Area.add((Boolean) snapshot.get("A3"));

                        int AR = 0;
                        if (Area.get(0)) {
                            AR = 1;
                        } else if (Area.get(1)) {
                            AR = 2;
                        } else if (Area.get(2)) {
                            AR = 3;
                        }

                        long RNum = (long) snapshot.get("RoundNum");
                        RNum++;

                        Map<String, Object> TData = new HashMap<>();
                        TData.put("RoundNum", RNum);
                        Coll.update(TData);

                        DocumentReference NewColl = Db.collection("SpinnerTimerBools").document("TeenPatti").collection("Results").document(String.valueOf(RNum));
                        Map<String, Object> NData = new HashMap<>();
                        NData.put("Area", AR);
                        NData.put("time", Timestamp.now());
                        NewColl.set(NData);

                        for (int a = 0; a < 2; a++) {
                            Handler handler1 = new Handler();
                            int AA = a;
                            handler1.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (AA == 1) {
                                        Map<String, Object> TestData = new HashMap<>();
                                        TestData.put("show", false);
                                        Coll.update(TestData);
                                        SetPotValuesDefault(1);
                                    }
//
                                }
                            }, 1000 * a);
                        }


                    } else {
                        Log.d("TAG", "No such document");
                    }
                }
            }
        });
    }
    void SetPotValuesDefault(int i) {
        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        DocumentReference docRef = Db.collection("users").document(CommonUtils.getUserId());


        if (i == 0) {
            //SetUserBetOnResults();

            long C = UserCoins + PrizeCoins;
            Map<String, Object> TestData1 = new HashMap<>();
            TestData1.put("MyPotA", 0);
            TestData1.put("MyPotB", 0);
            TestData1.put("MyPotC", 0);
            TestData1.put("YourWager", 0);
            TestData1.put("Coins", C);
            docRef.update(TestData1);
            PrizeCoins = 0;


            PrizeCoins = 0;
            GPotA = GPotB = GPotC = 0;
        } else if (i == 1) {
            SetServerPotValDefault();
        }
    }
    void SetServerPotValDefault() {
        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        DocumentReference docRef = Db.collection("SpinnerTimerBools").document("TeenPatti");
        Map<String, Object> TestData = new HashMap<>();
        TestData.put("PotA", 0);
        TestData.put("PotB", 0);
        TestData.put("PotC", 0);
        TestData.put("BetAllowed", false);
        docRef.update(TestData);
    }

    void GenCards() {
        ArrayList<ArrayList<Integer>> Results = genTheCards();

        Log.d("data", "Results" + genTheCards().toString());
        ArrayList<Integer> Cards = Results.get(0);
        ArrayList<Integer> TypeSeqs = Results.get(1);

//        Log.d("data", "Cards"+Cards);
//        Log.d("data", "Cards"+TypeSeqs);

        ArrayList<Integer> P1 = new ArrayList<Integer>();
        ArrayList<Integer> P2 = new ArrayList<Integer>();
        ArrayList<Integer> P3 = new ArrayList<Integer>();

        String[] CardTypes = {"Trail", "Pure", "Colour", "Sequence", "Pair", "High"};

        for (int i = 0; i < 9; i++) {
            if (i < 3) {
                P1.add(Cards.get(i));
            } else if (i >= 3 && i < 6) {
                P2.add(Cards.get(i));
            } else if (i >= 6 && i < 9) {
                P3.add(Cards.get(i));
            }
        }

        Log.d("data", "P1" + P1);
        Log.d("data", "P2" + P2);
        Log.d("data", "P3" + P3);


        ArrayList<String> OldCardBanner = new ArrayList<String>();

        ArrayList<Integer> WinnerCardsBanner = new ArrayList<Integer>();

        boolean[] OldArea = {false, false, false};


        //cardsetting
        for (int j = 0; j < 3; j++) {
            WinnerCardsBanner.add(TypeSeqs.get(j));
            Log.d("data", "WinnerCardsBanner" + WinnerCardsBanner);
            for (int i = 0; i < 6; i++) {
                if (TypeSeqs.get(j) == i) {
                    OldCardBanner.add(CardTypes[i]);
                    Log.d("data", "OldCardBanner" + OldCardBanner);

                }
            }
        }

        Collections.sort(WinnerCardsBanner);
        Log.d("data", "WinnerCardsBanner Short" + WinnerCardsBanner);
        Log.d("data", "OldArea Short" + Arrays.toString(OldArea));

        for (int i = 0; i < 3; i++) {
            if (WinnerCardsBanner.get(0) == TypeSeqs.get(i)) {
                OldArea[i] = true;
            } else {
                OldArea[i] = false;
            }
        }

        Log.d("data", "OldArea" + Arrays.toString(OldArea));


        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        final DocumentReference Coll = Db.collection("SpinnerTimerBools").document("TeenPatti");
        Coll.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null && snapshot.exists()) {
//set Amount To Winner always win less amount invester

//                        long PotA = (long) 100;
//                        long PotB = (long) 10000;
//                        long PotC = (long) 1000;

                        long PotA = (long) snapshot.get("PotA");
                        long PotB = (long) snapshot.get("PotB");
                        long PotC = (long) snapshot.get("PotC");

                        ArrayList<Long> Pots = new ArrayList<Long>();
                        ArrayList<Long> UnsortedPots = new ArrayList<Long>();
                        int WinnedPot = 0;
                        boolean[] Area = {false, false, false};

                        Pots.add(PotA);
                        Pots.add(PotB);
                        Pots.add(PotC);
                        UnsortedPots.add(PotA);
                        UnsortedPots.add(PotB);
                        UnsortedPots.add(PotC);
                        Collections.sort(Pots);
                        Log.d("data", "Pots" + Pots);
                        Log.d("data", "UnsortedPots" + UnsortedPots);


                        //manageAll
                        if (PotA == 0 && PotB == 0 && PotC == 0) {

                            WinnedPot = GetRnNum(0, 2);

                            Toast.makeText(requireContext(), ""+WinnedPot, Toast.LENGTH_SHORT).show();

                        } else {
                            for (int i = 0; i < 3; i++) {
                                if (Pots.get(0).equals(UnsortedPots.get(i))) {

                                    WinnedPot = i;
                                    Log.d("data", "WinnedPot" + i);

                                }
                            }
                        }


                        for (int i = 0; i < 3; i++) {
                            if (i == WinnedPot) {
                                Area[i] = true;
                            } else {
                                Area[i] = false;
                            }
//setWinner
//                    Area[0]=true;
//                        Area[1]=false;
//                        Area[2]=false;

                        }
                        Log.d("data", "Area: " + Arrays.toString(Area));


                        ArrayList<Integer> NewRes = new ArrayList<Integer>();
                        ArrayList<String> NewCardBanner = new ArrayList<String>();
                        if (WinnedPot == 0) {
                            if (OldArea[0]) {

                                NewCardBanner.add(OldCardBanner.get(0));
                                NewCardBanner.add(OldCardBanner.get(1));
                                NewCardBanner.add(OldCardBanner.get(2));
                                Log.d("data", "NewCardBanner1: " + NewCardBanner);
                                for (int i = 0; i < 3; i++) {
                                    if (i == 0) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P1.get(k));
                                        }
                                    } else if (i == 1) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P2.get(k));
                                        }
                                    } else if (i == 2) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P3.get(k));
                                        }
                                    }

                                }
//                                Log.d("data", "NewRes:"+pots);

                            } else if (OldArea[1]) {
                                Log.d("data", "NewCardBanner2: " + NewCardBanner);

                                NewCardBanner.add(OldCardBanner.get(1));
                                NewCardBanner.add(OldCardBanner.get(0));
                                NewCardBanner.add(OldCardBanner.get(2));

                                for (int i = 0; i < 3; i++) {
                                    if (i == 0) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P2.get(k));
                                        }
                                    } else if (i == 1) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P1.get(k));
                                        }
                                    } else if (i == 2) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P3.get(k));
                                        }
                                    }

                                }
                            } else if (OldArea[2]) {
                                Log.d("data", "NewCardBanner3: " + NewCardBanner);

                                NewCardBanner.add(OldCardBanner.get(2));
                                NewCardBanner.add(OldCardBanner.get(0));
                                NewCardBanner.add(OldCardBanner.get(1));

                                for (int i = 0; i < 3; i++) {
                                    if (i == 0) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P3.get(k));
                                        }
                                    } else if (i == 1) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P1.get(k));
                                        }
                                    } else if (i == 2) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P2.get(k));
                                        }
                                    }

                                }
                            }
                        } else if (WinnedPot == 1) {
                            if (OldArea[0]) {
                                NewCardBanner.add(OldCardBanner.get(1));
                                NewCardBanner.add(OldCardBanner.get(0));
                                NewCardBanner.add(OldCardBanner.get(2));

                                for (int i = 0; i < 3; i++) {
                                    if (i == 0) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P2.get(k));
                                        }
                                    } else if (i == 1) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P1.get(k));
                                        }
                                    } else if (i == 2) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P3.get(k));
                                        }
                                    }

                                }
                            } else if (OldArea[1]) {
                                NewCardBanner.add(OldCardBanner.get(0));
                                NewCardBanner.add(OldCardBanner.get(1));
                                NewCardBanner.add(OldCardBanner.get(2));

                                for (int i = 0; i < 3; i++) {
                                    if (i == 0) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P1.get(k));
                                        }
                                    } else if (i == 1) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P2.get(k));
                                        }
                                    } else if (i == 2) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P3.get(k));
                                        }
                                    }

                                }
                            } else if (OldArea[2]) {
                                NewCardBanner.add(OldCardBanner.get(0));
                                NewCardBanner.add(OldCardBanner.get(2));
                                NewCardBanner.add(OldCardBanner.get(1));

                                for (int i = 0; i < 3; i++) {
                                    if (i == 0) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P1.get(k));
                                        }
                                    } else if (i == 1) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P3.get(k));
                                        }
                                    } else if (i == 2) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P2.get(k));
                                        }
                                    }

                                }
                            }
                        } else if (WinnedPot == 2) {
                            if (OldArea[0]) {
                                NewCardBanner.add(OldCardBanner.get(1));
                                NewCardBanner.add(OldCardBanner.get(2));
                                NewCardBanner.add(OldCardBanner.get(0));

                                for (int i = 0; i < 3; i++) {
                                    if (i == 0) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P2.get(k));
                                        }
                                    } else if (i == 1) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P3.get(k));
                                        }
                                    } else if (i == 2) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P1.get(k));
                                        }
                                    }

                                }
                            } else if (OldArea[1]) {
                                NewCardBanner.add(OldCardBanner.get(0));
                                NewCardBanner.add(OldCardBanner.get(2));
                                NewCardBanner.add(OldCardBanner.get(1));

                                for (int i = 0; i < 3; i++) {
                                    if (i == 0) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P1.get(k));
                                        }
                                    } else if (i == 1) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P3.get(k));
                                        }
                                    } else if (i == 2) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P2.get(k));
                                        }
                                    }

                                }
                            } else if (OldArea[2]) {
                                NewCardBanner.add(OldCardBanner.get(0));
                                NewCardBanner.add(OldCardBanner.get(1));
                                NewCardBanner.add(OldCardBanner.get(2));

                                for (int i = 0; i < 3; i++) {
                                    if (i == 0) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P1.get(k));
                                        }
                                    } else if (i == 1) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P2.get(k));
                                        }
                                    } else if (i == 2) {
                                        for (int k = 0; k < 3; k++) {
                                            NewRes.add(P3.get(k));
                                        }
                                    }

                                }
                            }
                        }


                        Map<String, Object> NewData = new HashMap<>();

                        NewData.put("cards", NewRes);
                        NewData.put("C11", NewCardBanner.get(0));
                        NewData.put("C22", NewCardBanner.get(1));
                        NewData.put("C33", NewCardBanner.get(2));
                        NewData.put("A1", Area[0]);
                        NewData.put("A2", Area[1]);
                        NewData.put("A3", Area[2]);

                        Coll.update(NewData);


                    } else {
                        Log.d("TAG", "No such document");
                    }
                }
            }
        });


    }


    ArrayList<ArrayList<Integer>> genTheCards() {
        int kk = 0;
        int[][][] ChoseCard = {Trail, Pure, Colorss, Sequence, Pair, HighCard};

        int[] Typer = {0, 1, 2, 3, 4, 5};
        ArrayList<Integer> Typ = new ArrayList<Integer>();
        for (int i = 0; i < Typer.length; i++) {
            Typ.add(Typer[i]);
        }

        Collections.shuffle(Typ);
        kk = Typ.get(0);
        Typ.remove(0);
        int Tseq1 = kk;


        Collections.shuffle(Typ);
        kk = Typ.get(0);
        Typ.remove(0);
        int Tseq2 = kk;

        Collections.shuffle(Typ);
        kk = Typ.get(0);
        Typ.remove(0);
        int Tseq3 = kk;


        Log.d("data", "Tseq2" + Tseq2);
        Log.d("data", "Tseq1" + Tseq1);
        Log.d("data", "Tseq3" + Tseq3);

        for (int i = 0; i < Typer.length; i++) {
            Typ.add(Typer[i]);

        }
        int SuiteIndx = GetRnNum(1, 3);
        ArrayList<Integer> TDeck = new ArrayList<Integer>();


        Log.d("data", "SuiteIndx" + SuiteIndx);

        ArrayList<Integer> r1 = SetAllCards(TDeck, ChoseCard, Tseq1, Tseq2, Tseq3, SuiteIndx);

        Log.d("data", "r1" + r1.toString());
        ArrayList<Integer> Ty = new ArrayList<>();
        Ty.add(Tseq1);
        Ty.add(Tseq2);
        Ty.add(Tseq3);
        Log.d("data", "Ty" + Ty);
        ArrayList<ArrayList<Integer>> Result = new ArrayList<ArrayList<Integer>>();
        Result.add(r1);
        Result.add(Ty);

        return Result;

    }
    int GetRnNum(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
    ArrayList<Integer> SetAllCards(ArrayList<Integer> TMDeck, int[][][] ChoseCard, int TypeSequence1, int TypeSequence2, int TypeSequence3, int SuiteIndx) {
        int[][] SelectedCardType1 = ChoseCard[TypeSequence1];
        int[][] SelectedCardType2 = ChoseCard[TypeSequence2];
        int[][] SelectedCardType3 = ChoseCard[TypeSequence3];


        Log.d("data", "SetAllCards: ");
        int Ranindex1 = GetRnNum(0, SelectedCardType1.length - 1);
        int[] C1 = SelectedCardType1[Ranindex1];


        Log.d("data", "C1" + Arrays.toString(C1));

        int Ranindex2 = GetRnNum(0, SelectedCardType2.length - 1);
        int[] C2 = SelectedCardType2[Ranindex2];
        Log.d("data", "C2" + Arrays.toString(C2));

        int Ranindex3 = GetRnNum(0, SelectedCardType3.length - 1);
        int[] C3 = SelectedCardType3[Ranindex3];
        Log.d("data", "C3" + Arrays.toString(C3));


        ChangeCard(TMDeck, C1, TypeSequence1, SuiteIndx);

        if (ChangeCard(TMDeck, C2, TypeSequence2, SuiteIndx)) {
            if (ChangeCard(TMDeck, C3, TypeSequence3, SuiteIndx)) {
                return TMDeck;
            } else {
                return Set3Cards(TMDeck, ChoseCard, TypeSequence3, SuiteIndx);
            }
        } else {
            return Set6Cards(TMDeck, ChoseCard, TypeSequence2, TypeSequence3, SuiteIndx);
        }
    }
    boolean ChangeCard(ArrayList<Integer> TMDeck, int[] C, int TypeSequence, int SuiteIndx) {
        int[] j = {0, 1, 2, 3};
        ArrayList<Integer> jk = new ArrayList<>();
        for (int i = 0; i < j.length; i++) {
            jk.add(j[i]);
        }
        ArrayList<Integer> CK = new ArrayList<>();
        for (int i = 0; i < C.length; i++) {
            int kk;
            if (TypeSequence == 1 || TypeSequence == 3) {
                kk = SuiteIndx - 1;
                CK.add(C[i] - 1 + (13 * kk));

            } else {
                Collections.shuffle(jk);
                kk = jk.get(0);

                jk.remove(0);
                CK.add(C[i] - 1 + (13 * kk));

            }

            // if(kk == 0)
            // {
            //   print("Clubs");
            // }
            // else if(kk==1)
            // {
            //   print("Diamond");
            // }
            // else if(kk==2)
            // {
            //   print("Heart");
            // }
            // else if(kk==3)
            // {
            //   print("Spade");
            // }
        }
        if (!TMDeck.contains(CK.get(0)) && !TMDeck.contains(CK.get(1)) && !TMDeck.contains(CK.get(2))) {
            for (int i = 0; i < CK.size(); i++) {
                TMDeck.add(CK.get(i));
            }

            return true;
        } else {
            //print("Not Added to Deck");
            return false;
        }

    }
    ArrayList<Integer> Set6Cards(ArrayList<Integer> TMDeck, int[][][] ChoseCard, int TypeSequence2, int TypeSequence3, int SuiteIndx) {
        int[][] SelectedCardType2 = ChoseCard[TypeSequence2];

        Log.d("data", "SelectedCardType2" + Arrays.deepToString(SelectedCardType2));
        int Ranindex2 = GetRnNum(0, SelectedCardType2.length - 1);

        Log.d("data", "Ranindex2   " + Ranindex2);
        int[] C2 = SelectedCardType2[Ranindex2];
        Log.d("data", "C2   " + C2);

        int[][] SelectedCardType3 = ChoseCard[TypeSequence3];
        int Ranindex3 = GetRnNum(0, SelectedCardType3.length - 1);
        int[] C3 = SelectedCardType3[Ranindex3];
        Log.d("data", "C2   " + C3);
        Log.d("data", "Ranindex3   " + Ranindex3);

        if (ChangeCard(TMDeck, C2, TypeSequence2, SuiteIndx)) {
            if (ChangeCard(TMDeck, C3, TypeSequence3, SuiteIndx)) {
                return TMDeck;
            } else {
                return Set3Cards(TMDeck, ChoseCard, TypeSequence3, SuiteIndx);
            }
        } else {
            return Set6Cards(TMDeck, ChoseCard, TypeSequence2, TypeSequence3, SuiteIndx);
        }
    }
    void getBackgroundImageGetLoopCards(int[] MyCards, ImageView[] r1, ImageView[] r2, ImageView[] r3, ArrayList<Long> r, ArrayList<String> Ty, ArrayList<Boolean> AR) {
        FirebaseFirestore Db;
        Db = FirebaseFirestore.getInstance();
        DocumentReference UserDocRef = Db.collection("users").document(CommonUtils.getUserId());
        UserDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {


                if (task.isSuccessful()) {
                    DocumentSnapshot snapshot = task.getResult();
                    if (snapshot != null && snapshot.exists()) {

                        ArrayList<Long> MyPots = new ArrayList<Long>();
                        MyPots.add(snapshot.getLong("MyPotA"));
                        MyPots.add(snapshot.getLong("MyPotB"));
                        MyPots.add(snapshot.getLong("MyPotC"));
                        loppcards(MyCards, r1, r2, r3, r, Ty, AR, MyPots);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                }


            }
        });
    }

    ArrayList<Integer> Set3Cards(ArrayList<Integer> TMDeck, int[][][] ChoseCard, int TypeSequence3, int SuiteIndx) {


        int[][] SelectedCardType3 = ChoseCard[TypeSequence3];
        int Ranindex3 = GetRnNum(0, SelectedCardType3.length - 1);
        int[] C3 = SelectedCardType3[Ranindex3];


        if (ChangeCard(TMDeck, C3, TypeSequence3, SuiteIndx)) {
            return TMDeck;
        } else {
            return Set3Cards(TMDeck, ChoseCard, TypeSequence3, SuiteIndx);
        }
    }
    void TimerDisplayCheck() {
        ImageView TimerBoard = view.findViewById(R.id.Timerboard);
        TextView Txt = view.findViewById(R.id.TimerText);
        if (!TimerDisplay) {
            TimerBoard.setVisibility(View.INVISIBLE);
            Txt.setVisibility(View.INVISIBLE);
        } else {
            TimerBoard.setVisibility(View.VISIBLE);
            Txt.setVisibility(View.VISIBLE);
        }
    }
    void SetRandomPots() {
        TextView PA = view.findViewById(R.id.AText);
        TextView PB = view.findViewById(R.id.BText);
        TextView PC = view.findViewById(R.id.CText);
        long PAlong = GetRnNum(1000, 100000);
        long PBlong = GetRnNum(1000, 100000);
        long PClong = GetRnNum(1000, 100000);
        GPotA += PAlong;
        GPotB += PBlong;
        GPotC += PClong;
        PA.setText("Pot:" + prettyCount(GPotA));
        PB.setText("Pot:" + prettyCount(GPotB));
        PC.setText("Pot:" + prettyCount(GPotC));

    }
    void CardsBannerDisplayCheck() {
        ImageView Banner = view.findViewById(R.id.ShowCardsBanner);
        TextView Txt = view.findViewById(R.id.ShowCardsBannerText);


        if (!ShowCardsBanner) {
            Banner.setVisibility(View.INVISIBLE);
            Txt.setVisibility(View.INVISIBLE);

        } else {
            Banner.setVisibility(View.VISIBLE);
            Txt.setVisibility(View.VISIBLE);
        }
    }
    void OnRankButtonClick(int i) {
        if (i == 0) {
            RelativeLayout RL = view.findViewById(R.id.RankScreenDaily);
            RL.setVisibility(View.VISIBLE);
        } else if (i == 1) {
            CloseAllRanks(0);
            RelativeLayout WRL = view.findViewById(R.id.RankScreenWeekly);
            WRL.setVisibility(View.VISIBLE);
        }
    }
    void loppcards(int[] Cards, ImageView[] r1, ImageView[] r2, ImageView[] r3, ArrayList<Long> r, ArrayList<String> Ty, ArrayList<Boolean> AR, ArrayList<Long> MyPots) {
        ImageView[] Banners = {view.findViewById(R.id.cardrow01banner), view.findViewById(R.id.cardrow02banner), view.findViewById(R.id.cardrow03banner)};
        TextView[] BannerTexts = {view.findViewById(R.id.cardrow01bannerText), view.findViewById(R.id.cardrow02bannerText), view.findViewById(R.id.cardrow03bannerText)};
        for (int a = 0; a < 10; a++) {
            Handler handler1 = new Handler();
            int AA = a;
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {


                    if (AA < 3) {
                        Banners[AA].setVisibility(View.VISIBLE);
                        BannerTexts[AA].setVisibility(View.VISIBLE);
                        BannerTexts[AA].setText(Ty.get(AA));
                    }
                    if (AA == 3) {
                        for (int i = 0; i < 3; i++) {
                            if (AR.get(i)) {
                                RowMasks[i].setVisibility(View.INVISIBLE);
                                BirdMasks[i].setVisibility(View.INVISIBLE);
                                CardPotMasks[i].setVisibility(View.INVISIBLE);
                            } else {
                                RowMasks[i].setVisibility(View.VISIBLE);
                                BirdMasks[i].setVisibility(View.VISIBLE);
                                CardPotMasks[i].setVisibility(View.VISIBLE);
                            }
                        }
                    } else if (AA == 6) {
                        for (int i = 0; i < 3; i++) {
                            Banners[i].setVisibility(View.INVISIBLE);
                            BannerTexts[i].setVisibility(View.INVISIBLE);
                            RowMasks[i].setVisibility(View.INVISIBLE);
                            BirdMasks[i].setVisibility(View.INVISIBLE);
                            CardPotMasks[i].setVisibility(View.INVISIBLE);
                        }

                        for (int i = 0; i < 3; i++) {
                            if (AR.get(i)) {
                                try {
                                    PrizeCoins = (long) (MyPots.get(i) * MulPrize);

                                } catch (Exception e) {

                                }

                            }
                        }
                        ShowDoneScreen(1);
                        for (int i = 0; i < 3; i++) {
                            if (AR.get(i)) {
                                ImageView Bird = view.findViewById(R.id.WinningBirdBig);
                                ImageView WBird = view.findViewById(R.id.WinningBird);

                                if (i == 0) {
                                    Bird.setImageResource(R.drawable.app_logo);
                                    WBird.setImageResource(R.drawable.app_logo);
                                } else if (i == 1) {
                                    Bird.setImageResource(R.drawable.app_logo);
                                    WBird.setImageResource(R.drawable.app_logo);
                                } else if (i == 2) {
                                    Bird.setImageResource(R.drawable.app_logo);
                                    WBird.setImageResource(R.drawable.app_logo);
                                }
                            }

                        }
                    } else if (AA == 9) {

                        ShowDoneScreen(0);


                    }

                    for (int j = 0; j < 3; j++) {
                        if (AA == 0) {
                            r1[j].setImageResource(Cards[r.get(j).intValue()]);
                        } else if (AA == 1) {
                            r2[j].setImageResource(Cards[r.get(j + 3).intValue()]);
                        } else if (AA == 2) {
                            r3[j].setImageResource(Cards[r.get(j + 6).intValue()]);
                        } else if (AA == 6) {
                            r1[j].setImageResource(R.drawable.bcard);
                            r2[j].setImageResource(R.drawable.pcard);
                            r3[j].setImageResource(R.drawable.rcard);
                        }
                    }


                }
            }, 300 * a);
        }


    }


}