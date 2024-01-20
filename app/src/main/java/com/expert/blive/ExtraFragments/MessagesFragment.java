package com.expert.blive.ExtraFragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.expert.blive.Adapter.ChatSection.ChatAdapter;
import com.expert.blive.ModelClass.ChatInformation.ChatInformation;
import com.expert.blive.ModelClass.searchUser.SearchUserDetail;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;
import com.google.android.exoplayer2.util.Log;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MessagesFragment extends BottomSheetDialogFragment implements ChatAdapter.callBackFromChatAdapter {

    private ChatAdapter chat_list;
    private String check;
    public static SearchUserDetail searchUserDetail;
    public static String other_userId, userId,roomId;
    private List<ChatInformation> chatInformationArrayList = new ArrayList<>();
    ChatInformation chatInformationGlobal;

    public MessagesFragment(ChatInformation chatInformation) {
        this.chatInformationGlobal = chatInformation;
    }

    public MessagesFragment() {


    }

    public MessagesFragment(String check) {

        this.check = check;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            setupRatio(bottomSheetDialog);
        });
        return dialog;
    }
    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        if (chatInformationGlobal!=null||check!=null){
            layoutParams.height = displaySize(requireActivity())[1] * 50/ 100;
        }else {
            layoutParams.height = displaySize(requireActivity())[1] * 100/ 100;

        }


        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private static int[] displaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        ((Display) display).getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        com.google.android.exoplayer2.util.Log.i("height", String.valueOf(height));
        Log.i("width", String.valueOf(width));
        return new int[]{width, height};
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.msgBackImg).setOnClickListener(view1 -> getActivity().onBackPressed());

        RecyclerView recyclerView=view.findViewById(R.id.chat_recycle);
        chat_list=new ChatAdapter(MessagesFragment.this,new ArrayList<>(),getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chat_list);

        receiveData();
    }

    private void receiveData() {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("last-Message").child(CommonUtils.getUserId());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()) {
                        chatInformationArrayList.clear();
                        ChatInformation chatInformation;
                        for (DataSnapshot child : snapshot.getChildren()) {

                            chatInformation = child.getValue(ChatInformation.class);

                            chatInformationArrayList.add(chatInformation);

                        }


                    }
                if (chatInformationGlobal!=null){
                    chatInformationArrayList.add(0,chatInformationGlobal);
                }
                chat_list.loadData(chatInformationArrayList);
            }
//                else {
//                    if (chatInformationGlobal!=null){
//                        chatInformationArrayList.add(0,chatInformationGlobal);
//                    }
//                    chat_list.loadData(chatInformationArrayList);
//                }





            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
//        if (getActivity()!=null){
//            getActivity().findViewById(R.id.rl_main).setVisibility(View.VISIBLE);
//            getActivity().findViewById(R.id.img_video).setVisibility(View.VISIBLE);
//        }

    }

    @Override
    public void next(ChatInformation chatInformation) {



        if(chatInformationGlobal!=null||check!=null){
            ChatFragment userDetailsFragment = new ChatFragment("1");
            ChatFragment.other_userId = chatInformation.getUserId();
            userDetailsFragment.show(requireActivity().getSupportFragmentManager(), userDetailsFragment.getTag());

        }else {
            ChatFragment.searchUserDetail =  searchUserDetail;

            ChatFragment.other_userId = chatInformation.getUserId();

            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new ChatFragment()).addToBackStack(null).commit();

        }
          }



}