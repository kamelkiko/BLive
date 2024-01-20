package com.expert.blive.ExtraFragments;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.expert.blive.Adapter.ChatSection.MessageAdapter;
import com.expert.blive.ModelClass.ChatInformation.ChatInformation;
import com.expert.blive.ModelClass.searchUser.SearchUserDetail;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;
import com.google.android.exoplayer2.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ChatBottomFragment extends BottomSheetDialogFragment implements MessageAdapter.Callback {
    public static SearchUserDetail searchUserDetail;
    private EditText text_message;
    private RelativeLayout send,leaveChatBottom;
    public static boolean sendCheck=false;
    private RecyclerView chat_recycler;
    private String otherUserId,videoId;
    private MessageAdapter messageAdapter;
    private List<ChatInformation> chatInformationArrayList = new ArrayList<>();

    public ChatBottomFragment(String otherUserId) {
        this.otherUserId = otherUserId;
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
        layoutParams.height = displaySize(requireActivity())[1] * 55 / 100;
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
        Log.i("height", String.valueOf(height));
        Log.i("width", String.valueOf(width));
        return new int[]{width, height};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_bottom, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        onBackPressed(view);

        view.findViewById(R.id.leave_chat).setOnClickListener(view12 -> requireActivity().onBackPressed());
        view.findViewById(R.id.chatMsgTV).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,new LiveAudioFragment()).addToBackStack(null).commit());

        findIds(view);

        leaveChatBottom.setOnClickListener(view1 -> {
            dismiss();
        });

        send.setOnClickListener(v -> {
            if(text_message.getText().toString().trim().length()!=0) {
                sendMessage(text_message.getText().toString().trim());
                text_message.setText(null);
                ChatFragment.sendCheck=true;
            }
        });
        messageAdapter = new MessageAdapter(new ArrayList<>(),ChatBottomFragment.this);
        chat_recycler.setAdapter(messageAdapter);
        receiveData();

    }

    private void receiveData() {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("chat-Panel@"+otherUserId+ CommonUtils.getUserId());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    chatInformationArrayList.clear();

                    ChatInformation chatInformation;

                    for (DataSnapshot child : snapshot.getChildren()) {

                        chatInformation = child.getValue(ChatInformation.class);

                        chatInformationArrayList.add(chatInformation);

                    }

                    messageAdapter.loadData(chatInformationArrayList);

                    chat_recycler.scrollToPosition(chatInformationArrayList.size() - 1);

                }
                else
                {
                    DatabaseReference databaseReference= FirebaseDatabase.getInstance()
                            .getReference("chat-Panel@"+CommonUtils.getUserId()+otherUserId);


                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            if(snapshot.exists())
                            {

                                chatInformationArrayList.clear();

                                ChatInformation chatInformation;

                                for (DataSnapshot child : snapshot.getChildren()) {

                                    chatInformation = child.getValue(ChatInformation.class);

                                    chatInformationArrayList.add(chatInformation);

                                }

                                messageAdapter.loadData(chatInformationArrayList);

                                chat_recycler.scrollToPosition(chatInformationArrayList.size() - 1);
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void findIds(View view) {

        text_message = view.findViewById(R.id.text_message);
        send = view.findViewById(R.id.send);
        leaveChatBottom = view.findViewById(R.id.leaveChatBottom);
        chat_recycler = view.findViewById(R.id.chat_recycler);
    }
    private void sendMessage(String message)
    {

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("chat-Panel@"+otherUserId+CommonUtils.getUserId());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {

                    android.util.Log.d(TAG, "onDataChange: if call " + sendCheck);


                    if( ChatFragment.sendCheck)
                    {
                        ChatFragment.sendCheck=false;

                        String random_key_generator=databaseReference.push().getKey();

                        ChatInformation chatInformation=new ChatInformation(message,CommonUtils.getUserId(),
                                random_key_generator,getDate(),getTime(),CommonUtils.getName(),CommonUtils.getImage(),videoId,"");

                        databaseReference.child(random_key_generator).setValue(chatInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DatabaseReference databaseReference;

                                if(!otherUserId.equals(CommonUtils.getUserId()))
                                {
                                    databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("last-Message").child(otherUserId).child(CommonUtils.getUserId());

                                }
                                else
                                {
                                    databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("last-Message").child(CommonUtils.getUserId()).child(otherUserId);

                                }
                                saveLastMessage(databaseReference, chatInformation);


                            }
                        });
                    }

                }
                else
                {


                    android.util.Log.d(TAG, "onDataChange: else call");

                    DatabaseReference databaseReference= FirebaseDatabase.getInstance()
                            .getReference("chat-Panel@"+CommonUtils.getUserId()+otherUserId);


                    if(ChatFragment.sendCheck)
                    {
                        ChatFragment.sendCheck=false;

                        String random_key_generator=databaseReference.push().getKey();

                        ChatInformation chatInformation=new ChatInformation(message,CommonUtils.getUserId(),
                                random_key_generator,getDate(),getTime(),CommonUtils.getName(), CommonUtils.getImage(),videoId,"");


                        databaseReference.child(random_key_generator).setValue(chatInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                DatabaseReference databaseReference;

                                if(!otherUserId.equals(CommonUtils.getUserId()))
                                {
                                    databaseReference= FirebaseDatabase.getInstance()
                                            .getReference("last-Message").child(otherUserId).child(CommonUtils.getUserId());

                                }
                                else
                                {
                                    databaseReference= FirebaseDatabase.getInstance()
                                            .getReference("last-Message").child(CommonUtils.getUserId()).child(otherUserId);

                                }
                                saveLastMessage(databaseReference, chatInformation);
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private String getDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        Date date = new Date();
        return formatter.format(date);
    }

    private String getTime() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
    private void saveLastMessage(DatabaseReference databaseReference,ChatInformation chatInformation)
    {
        databaseReference.setValue(chatInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    @Override
    public void playVideo(ChatInformation chatInformation) {
    }
}