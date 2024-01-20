//package com.expert.blive.HomeActivity.Profile.VIP;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//
//import androidx.appcompat.widget.AppCompatButton;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.expert.blive.HomeActivity.Profile.category.MyWalletFragment;
//import com.expert.blive.ModelClass.VIP.BuyVipRoot;
//import com.expert.blive.ModelClass.VIP.VipRoot;
//import com.expert.blive.R;
//import com.expert.blive.databinding.FragmentSwordsmanBinding;
//import com.expert.blive.mvvm.MvvmViewModelClass;
//import com.expert.blive.utils.CommonUtils;
//
//
//public class Fragment_Swordsman extends Fragment {
//    FragmentSwordsmanBinding binding;
//    public  VipRoot.Detail detail;
//    public  String frameImage;
//
//
//    public Fragment_Swordsman(VipRoot.Detail detail, String frameImage) {
//        this.detail = detail;
//        this.frameImage = frameImage;
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        binding = FragmentSwordsmanBinding.inflate(getLayoutInflater(), container, false);
//        onClick();
//        setData();
//        return binding.getRoot();
//    }
//
//    private void setData() {
//        binding.cost.setText(detail.getCoins());
////        binding.privilegeLay.removerUserTxt.setTextColor(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.removeUserImg.setColorFilter(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.exclusiveGiftImag.setColorFilter(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.exclusiveTxt.setTextColor(Color.parseColor("#bd9a5f"));
////
////        binding.privilegeLay.invisibleRankImag.setColorFilter(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.invisibleRankTxt.setTextColor(Color.parseColor("#bd9a5f"));
////
////        binding.privilegeLay.hideImage.setColorFilter(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.hideTxt.setTextColor(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.smsBanImage.setColorFilter(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.smsBanTxt.setTextColor(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.bulletSmsImage.setColorFilter(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.bulletSmsTxt.setTextColor(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.exclusiveBulletImage.setColorFilter(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.exclusiveBulletTxt.setTextColor(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.recommendImag.setColorFilter(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.recommTxt.setTextColor(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.comingsoonImag.setColorFilter(Color.parseColor("#bd9a5f"));
////        binding.privilegeLay.comingSoonTxt.setTextColor(Color.parseColor("#bd9a5f"));
//
//        if (detail.getBuy()) {
//            binding.VipStatus.setText("VIP 5 Activated");
//        } else {
//            binding.VipStatus.setText("VIP not opened");
//
//        }
//        binding.vipName.setText(CommonUtils.getName());
//        Glide.with(requireContext()).load(CommonUtils.getImage()).error(R.drawable.user_7).into(binding.userVipProfile);
//        CommonUtils.setAnimation(requireContext(),frameImage,binding.profieFrame);
//
//    }
//
//
//    private void onClick() {
//
//        binding.vipBUY.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buyVip();
//            }
//        });
//
//
//        binding.vipInc.vip1Llayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Dialog newDialog = new Dialog(requireContext());
//                newDialog.setContentView(R.layout.item_vip);
//                newDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//                Window window = newDialog.getWindow();
//                newDialog.setCanceledOnTouchOutside(true);
//                window.setGravity(Gravity.CENTER);
//                WindowManager.LayoutParams wlp = window.getAttributes();
//                wlp.gravity = Gravity.CENTER;
//                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
//                window.setAttributes(wlp);
//                //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                newDialog.show();
//                newDialog.setCanceledOnTouchOutside(true);
//                AppCompatButton confirm =newDialog.findViewById(R.id.confirm_vip);
//
//                confirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        newDialog.dismiss();
//                    }
//                });
//            }
//
//        });
//
//        binding.vipInc.micWave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Dialog newDialog = new Dialog(requireContext());
//                newDialog.setContentView(R.layout.item_mic_wave);
//                newDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//                Window window = newDialog.getWindow();
//                newDialog.setCanceledOnTouchOutside(true);
//                window.setGravity(Gravity.CENTER);
//                WindowManager.LayoutParams wlp = window.getAttributes();
//                wlp.gravity = Gravity.CENTER;
//                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
//                window.setAttributes(wlp);
//                //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                newDialog.show();
//                newDialog.setCanceledOnTouchOutside(true);
//                AppCompatButton confirm =newDialog.findViewById(R.id.confirm_vip);
//
//                confirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        newDialog.dismiss();
//                    }
//                });
//            }
//
//        });
//
//        binding.vipInc.uniuqeFrame.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Dialog newDialog = new Dialog(requireContext());
//                newDialog.setContentView(R.layout.item_unique_frames);
//                newDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//                Window window = newDialog.getWindow();
//                newDialog.setCanceledOnTouchOutside(true);
//                window.setGravity(Gravity.CENTER);
//                WindowManager.LayoutParams wlp = window.getAttributes();
//                wlp.gravity = Gravity.CENTER;
//                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
//                window.setAttributes(wlp);
//                //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                newDialog.show();
//                newDialog.setCanceledOnTouchOutside(true);
//                AppCompatButton confirm =newDialog.findViewById(R.id.confirm_vip);
//
//                confirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        newDialog.dismiss();
//                    }
//                });
//            }
//
//        });
//
//
//
//        binding.vipInc.chatBubble.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Dialog newDialog = new Dialog(requireContext());
//                newDialog.setContentView(R.layout.items_exclusive_bubble);
//                newDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//                Window window = newDialog.getWindow();
//                newDialog.setCanceledOnTouchOutside(true);
//                window.setGravity(Gravity.CENTER);
//                WindowManager.LayoutParams wlp = window.getAttributes();
//                wlp.gravity = Gravity.CENTER;
//                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
//                window.setAttributes(wlp);
//                //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                newDialog.show();
//                newDialog.setCanceledOnTouchOutside(true);
//                AppCompatButton confirm =newDialog.findViewById(R.id.confirm_vip);
//
//                confirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        newDialog.dismiss();
//                    }
//                });
//            }
//
//        });
//
//        binding.vipInc.entranceEffectLlayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Dialog newDialog = new Dialog(requireContext());
//                newDialog.setContentView(R.layout.items_entrance_effect);
//                newDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
//                Window window = newDialog.getWindow();
//                newDialog.setCanceledOnTouchOutside(true);
//                window.setGravity(Gravity.CENTER);
//                WindowManager.LayoutParams wlp = window.getAttributes();
//                wlp.gravity = Gravity.CENTER;
//                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
//                window.setAttributes(wlp);
//                //   viewDetails_box.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                newDialog.show();
//                newDialog.setCanceledOnTouchOutside(true);
//                AppCompatButton confirm =newDialog.findViewById(R.id.confirm_vip);
//
//                confirm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        newDialog.dismiss();
//                    }
//                });
//            }
//
//        });
//    }
//
//    private void buyVip() {
//        new MvvmViewModelClass().buyVip(requireActivity(), CommonUtils.getUserId(), detail.getId()).observe(requireActivity(), new Observer<BuyVipRoot>() {
//            @Override
//            public void onChanged(BuyVipRoot buyVipRoot) {
//
//                if (buyVipRoot.getStatus() == 1) {
////                    binding.buyBtn.setVisibility(View.GONE);
//                    Toast.makeText(requireContext(), "1 " + buyVipRoot.getMessage(), Toast.LENGTH_SHORT).show();
//                } else {
//                    notEnoughCoins();
//                }
//            }
//        });
//    }
//
//    private void notEnoughCoins() {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setMessage("Not enough coins, want to recharge?");
//        builder.setTitle("Tips");
//        builder.setCancelable(false);
//        builder.setPositiveButton("Recharge", (DialogInterface.OnClickListener) (dialog, which) -> {
//            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new MyWalletFragment()).addToBackStack(null).commit();
//            dialog.dismiss();
//        });
//
//        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
//            dialog.cancel();
//        });
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
//}