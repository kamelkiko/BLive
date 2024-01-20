package com.expert.blive.Agora.agoraLive.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.expert.blive.Agora.agoraLive.adapters.AdapterGiftCategory;
import com.expert.blive.Agora.agoraLive.adapters.GiftingTabAdapter;
import com.expert.blive.Agora.agoraLive.firebase.FirebaseHelper;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSendGift;
import com.expert.blive.ModelClass.OtpRoot;
import com.expert.blive.retrofit.GiftCategoryModel;
import com.expert.blive.retrofit.LiveMvvm;
import com.expert.blive.utils.App;
import com.expert.blive.utils.AppConstant;
import com.expert.blive.utils.CommonUtils;
import com.expert.blive.Agora.GiftModel;
import com.expert.blive.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GiftBottomSheetFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private View view;
    private RecyclerView rv_coins, rv_categoryGift;
    Button getcoin;
    TextView balance;
    private VideoMvvm videoMvvm;
    private List<GiftModel.Detail> listgift = new ArrayList<>();
    private String userchannelId, channelName, categoryId = "",getComboCount = "", liveid, pkbattleid="";
    private ProgressBar progress_bar, progress_bar_gifts;
    private LinearLayout ll_main;
    private ImageView iv_back;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private LiveMvvm liveMvvm;
    private List<GiftCategoryModel.Detail> list = new ArrayList<>();
    private Button sendgift;
    private RelativeLayout relativeLayout;
    private Spinner spinner;

    public GiftBottomSheetFragment() {
        // Required empty public constructor
    }

    public GiftBottomSheetFragment(String userchannelId, String channelName, String liveid, String pkbattleid) {
        this.channelName = channelName;
        this.userchannelId = userchannelId;
        this.liveid = liveid;
        this.pkbattleid = pkbattleid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gift_bottom_sheet, container, false);
        videoMvvm = new VideoMvvm();
        liveMvvm = new LiveMvvm();
        findIds();
        getGiftCategories();
        return view;
    }

    private void getGiftCategories() {
        liveMvvm.giftCategory(getActivity()).observe(getViewLifecycleOwner(), new Observer<GiftCategoryModel>() {
            @Override
            public void onChanged(GiftCategoryModel giftCategoryModel) {
                if (giftCategoryModel.getSuccess().equalsIgnoreCase("1")) {
                    progress_bar.setVisibility(View.GONE);
                    list = giftCategoryModel.getDetails();
                    categoryId = list.get(0).getId();
                    getGifts(categoryId);
                    AdapterGiftCategory adapterGiftCategory = new AdapterGiftCategory(getActivity(), list, new AdapterGiftCategory.Click() {
                        @Override
                        public void onClick(String id) {
                            getGifts(id);
                        }
                    });
                    rv_categoryGift.setAdapter(adapterGiftCategory);
                } else {
                    Toast.makeText(getActivity(), giftCategoryModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setAdapter() {
        GiftingTabAdapter giftingTabAdapter = new GiftingTabAdapter(getChildFragmentManager());
        viewPager.setAdapter(giftingTabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                    setupRatio(bottomSheetDialog);
                }
            });
        }
        return dialog;
    }

    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        bottomSheet.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.trans)));
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = displaySize(requireActivity())[1] * 55 / 100;
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

    @SuppressLint("SetTextI18n")
    private void getGifts(String id) {
        progress_bar_gifts.setVisibility(View.VISIBLE);
        videoMvvm.sendLiveGift(getActivity(), CommonUtils.getUserId(), id).observe(requireActivity(), giftModel -> {
            if (giftModel.getSuccess().equalsIgnoreCase("1")) {
                progress_bar_gifts.setVisibility(View.GONE);
                listgift = giftModel.getDetails();
                if (!giftModel.getCoin().equalsIgnoreCase("")) {
                    balance.setText("My Diamonds: " + CommonUtils.prettyCount(Long.parseLong(giftModel.getCoin())));
                } else {
                    balance.setText("My Diamonds : 0");
                }
                GiftAdapterTwo giftAdapter = new GiftAdapterTwo(getActivity(), listgift, (position, balance, sound, price, id1, image, timing, giftname, thumbnail, type) -> {
                    if (App.getSingleton().getGiftCheck().equalsIgnoreCase("1")) {
                    } else if (App.getSingleton().getGiftCheck().equalsIgnoreCase("2")) {
                        relativeLayout.setVisibility(View.VISIBLE);
                        sendgift.setOnClickListener(view -> {
                            if (balance.equalsIgnoreCase("0") || balance.equalsIgnoreCase("")) {
                                Toast.makeText(requireContext(), "Add Diamonds", Toast.LENGTH_SHORT).show();
//                                            startActivity(new Intent(getActivity(), PurchaseCoinActivity.class));
                            } else {
                                long balancevalue = Long.parseLong(balance.trim());
                                long priceValue = Long.parseLong(price);
                                long combovalue = Long.parseLong(getComboCount);
                                long newPrice = priceValue * combovalue;
                                String finalprice = String.valueOf(newPrice);
                                if (balancevalue >= newPrice) {

                                    giftSend(finalprice, sound, id1, userchannelId, image, timing, giftname, thumbnail,type);
                                } else {
                                    Toast.makeText(requireContext(), "Add Diamonds", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                rv_coins.setAdapter(giftAdapter);
                progress_bar.setVisibility(View.GONE);
                ll_main.setVisibility(View.VISIBLE);
            }
        });
    }

    private void giftSend(String price, String sound, String id, String getOtherUserid,String image, String time, String giftname, String thumbnail,String type) {

        videoMvvm.giftLiveSend(requireActivity(), CommonUtils.getUserId(), price, getOtherUserid, id, pkbattleid, liveid).observe(this, map -> {
            if (map.get("success").toString().equalsIgnoreCase("1")) {
                JSONObject jsonObject = new JSONObject(map);
                try {
                    JSONObject details = jsonObject.getJSONObject("details");

                    String myStars = details.getString("myStar");
                    String startStatus = details.getString("startStatus");
                    String coinsRecieved = details.getString("coinsRecieved");

                    OtpRoot details1 = App.getSharedpref().getModel(AppConstant.USER_INFO, OtpRoot.class);
                    String username = details1.getUsername();
                    String name = details1.getName();
                    String userImage = details1.getImage();

                    ModelSendGift modelSendGift = new ModelSendGift();
                    modelSendGift.setGiftId(id);
                    modelSendGift.setGiftImage(image);
                    modelSendGift.setGiftPrice(Integer.parseInt(price));
                    modelSendGift.setUserName(username);
                    modelSendGift.setUserId(CommonUtils.getUserId());
                    modelSendGift.setName(name);
                    modelSendGift.setMyLevel("");
                    modelSendGift.setLiveLevel("");
                    modelSendGift.setMyStars(myStars);
                    modelSendGift.setSound(sound);

                    modelSendGift.setLiveStars(startStatus);
                    modelSendGift.setGifttime(time);
                    modelSendGift.setGiftName(giftname);
                    modelSendGift.setUserImage(userImage);
                    modelSendGift.setMybox("");
                    modelSendGift.setLivebox(coinsRecieved);
                    modelSendGift.setThumbnail(thumbnail);
                    modelSendGift.setType(type);
                    FirebaseHelper.sendGift(channelName, modelSendGift);


                    Toast.makeText(requireContext(), "gift :- "+type, Toast.LENGTH_SHORT).show();




//                       show if (otherChannelName!=null||!otherChannelName.equalsIgnoreCase("")){
//                            FirebaseHelper.sendGift(otherChannelName, modelSendGift);
//                        }
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
//                    Toast.makeText(getActivity(), "Gift Sent", Toast.LENGTH_SHORT).show();
                dismiss();
            } else {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findIds() {
        relativeLayout = view.findViewById(R.id.rl_bottomSend);
        spinner = view.findViewById(R.id.sp_comboGift);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getComboCount = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                getComboCount = "1";
            }
        });

        sendgift = view.findViewById(R.id.sendGift);
        progress_bar_gifts = view.findViewById(R.id.progress_bar_gifts);
        rv_categoryGift = view.findViewById(R.id.rv_categoryGift);
        tabLayout = view.findViewById(R.id.tabLayoutGift);
        viewPager = view.findViewById(R.id.viewPagerGift);
        iv_back = view.findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        ll_main = view.findViewById(R.id.ll_main);
        progress_bar = view.findViewById(R.id.progress_bar);
        getcoin = view.findViewById(R.id.bt_getcoin);
        getcoin.setOnClickListener(this);
        balance = view.findViewById(R.id.balance);
        rv_coins = view.findViewById(R.id.rv_coins);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_getcoin:
                Toast.makeText(requireContext(), "Add Diamonds", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_back:
                dismiss();
                break;
        }
    }
}