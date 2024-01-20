package com.expert.blive.ExtraFragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayoutStates;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.expert.blive.mvvm.MvvmViewModelClass;
import com.expert.blive.Adapter.ChatSection.MessageAdapter;
import com.expert.blive.ModelClass.ChatInformation.ChatInformation;
import com.expert.blive.ModelClass.Video.ShareVideoRoot;
import com.expert.blive.ModelClass.searchUser.SearchUserDetail;
import com.expert.blive.R;
import com.expert.blive.utils.CommonUtils;
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

import static android.content.ContentValues.TAG;

public class ChatFragment extends BottomSheetDialogFragment implements MessageAdapter.Callback {
    public static SearchUserDetail searchUserDetail;
    private EditText text_message;
    private RelativeLayout send;
    public static String other_userId,videoId;
    private String check;
    int count = 0;
    public static boolean sendCheck = false;
    private RecyclerView chat_recycler;
    private MessageAdapter messageAdapter;
    private List<ChatInformation> chatInformationArrayList = new ArrayList<>();

    public ChatFragment(String check) {
        this.check = check;
    }
    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
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
        if (check != null) {
            layoutParams.height = displaySize(requireActivity())[1] * 50 / 100;
        } else {
            layoutParams.height = displaySize(requireActivity())[1] * 100 / 100;
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
        com.google.android.exoplayer2.util.Log.i("width", String.valueOf(width));
        return new int[]{width, height};
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.chatMsgTV).setOnClickListener(view1 -> requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, new LiveAudioFragment()).addToBackStack(null).commit());
        view.findViewById(R.id.rlLeaveChat).setOnClickListener(view1 -> requireActivity().onBackPressed());

        findIds(view);
        send.setOnClickListener(v -> {
            if (text_message.getText().toString().trim().length() != 0) {
                sendMessage(text_message.getText().toString().trim());
                text_message.setText(null);
                ChatFragment.sendCheck = true;
            }
        });
        messageAdapter = new MessageAdapter(new ArrayList<>(),ChatFragment.this);
        chat_recycler.setAdapter(messageAdapter);
        receiveData();

    }

    private void receiveData() {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("chat-Panel@" + ChatFragment.other_userId + CommonUtils.getUserId());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    count++;

                    chatInformationArrayList.clear();

                    ChatInformation chatInformation;

                    for (DataSnapshot child : snapshot.getChildren()) {

                        chatInformation = child.getValue(ChatInformation.class);

                        chatInformationArrayList.add(chatInformation);
                    }
                    messageAdapter.loadData(chatInformationArrayList);

                        chat_recycler.scrollToPosition(chatInformationArrayList.size() - 1);

                } else {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference("chat-Panel@" + CommonUtils.getUserId() + ChatFragment.other_userId);


                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            if (snapshot.exists()) {
                                count++;

                                chatInformationArrayList.clear();

                                ChatInformation chatInformation;

                                for (DataSnapshot child : snapshot.getChildren()) {

                                    chatInformation = child.getValue(ChatInformation.class);

                                    chatInformationArrayList.add(chatInformation);

                                }
                                Toast.makeText(requireContext(), "b" + count, Toast.LENGTH_SHORT).show();

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
    private void sendMessage(String message) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("chat-Panel@" + ChatFragment.other_userId + CommonUtils.getUserId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    Log.d(TAG, "onDataChange: if call " + sendCheck);
                    if (ChatFragment.sendCheck) {
                        ChatFragment.sendCheck = false;
                        String random_key_generator = databaseReference.push().getKey();

                        databaseReference.child(random_key_generator).setValue(new ChatInformation(message, CommonUtils.getUserId(),
                                random_key_generator, getDate(), getTime(), CommonUtils.getName(), CommonUtils.getImage(),"0","")).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                DatabaseReference databaseReference;

                                if (!ChatFragment.other_userId.equals(CommonUtils.getUserId())) {
                                    databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("last-Message").child(ChatFragment.other_userId).child(CommonUtils.getUserId());
                                } else {
                                    databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("last-Message").child(CommonUtils.getUserId()).child(ChatFragment.other_userId);
                                }
                                saveLastMessage(databaseReference, new ChatInformation(message, CommonUtils.getUserId(),
                                        random_key_generator, getDate(), getTime(), CommonUtils.getName(), CommonUtils.getImage(),videoId,""));
                            }
                        });
                    }
                } else {
                    Log.d(TAG, "onDataChange: else call");

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                            .getReference("chat-Panel@" + CommonUtils.getUserId() + ChatFragment.other_userId);
                    if (ChatFragment.sendCheck) {
                        ChatFragment.sendCheck = false;
                        String random_key_generator = databaseReference.push().getKey();
                        databaseReference.child(random_key_generator).setValue(new ChatInformation(message, CommonUtils.getUserId(),
                                random_key_generator, getDate(), getTime(), CommonUtils.getName(), CommonUtils.getImage(),"0","")
                        ).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                DatabaseReference databaseReference;
                                if (!ChatFragment.other_userId.equals(CommonUtils.getUserId())) {
                                    databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("last-Message").child(ChatFragment.other_userId).child(CommonUtils.getUserId());

                                } else {
                                    databaseReference = FirebaseDatabase.getInstance()
                                            .getReference("last-Message").child(CommonUtils.getUserId()).child(ChatFragment.other_userId);

                                }

                                saveLastMessage(databaseReference, new ChatInformation(message, CommonUtils.getUserId(),
                                        random_key_generator, getDate(), getTime(), CommonUtils.getName(), CommonUtils.getImage(),"0","")
                                );


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

    private void findIds(View view) {

        text_message = view.findViewById(R.id.text_message);
        send = view.findViewById(R.id.send);
        chat_recycler = view.findViewById(R.id.chat_recycler);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().findViewById(R.id.bottom_nav).setVisibility(View.GONE);
//        getActivity().findViewById(R.id.img_video).setVisibility(View.GONE);
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

    private void saveLastMessage(DatabaseReference databaseReference, ChatInformation chatInformation) {

        Toast.makeText(requireContext(), ""+CommonUtils.getImage(), Toast.LENGTH_SHORT).show();
        Toast.makeText(requireContext(), ""+CommonUtils.getName(), Toast.LENGTH_SHORT).show();


//        Toast.makeText(getContext(), MessageFragment.other_userId + " " + MessageFragment.userId, Toast.LENGTH_SHORT).show();
        databaseReference.setValue(chatInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });

    }

    private void onBackPressed(View view) {

        view.setFocusableInTouchMode(true);

        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {

            @Override

            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

                    if (i == KeyEvent.KEYCODE_BACK) {

                        dismiss();
                        return true;

                    }

                }

                return false;

            }

        });

    }

    @Override
    public void playVideo(ChatInformation chatInformation) {

        String videoId = chatInformation.getMessage();

        Toast.makeText(requireContext(), "videoId  "+videoId, Toast.LENGTH_SHORT).show();
        Toast.makeText(requireContext(), "PlayVideo", Toast.LENGTH_SHORT).show();

        new MvvmViewModelClass().shareVideo(requireActivity(),CommonUtils.getUserId(),videoId).observe(requireActivity(), new Observer<ShareVideoRoot>() {

            @Override
            public void onChanged(ShareVideoRoot shareVideoRoot) {

                if (shareVideoRoot != null) {
                    if (shareVideoRoot.success){
                        Toast.makeText(requireContext(), ""+shareVideoRoot.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(ConstraintLayoutStates.TAG, "onChanged: "+shareVideoRoot.getDetails());

                        Bundle bundle = new Bundle();

                        bundle.putSerializable("key",shareVideoRoot.getDetails());
                        Toast.makeText(requireContext(), "videoPath  "+shareVideoRoot.getDetails(), Toast.LENGTH_SHORT).show();

//                      App.getSharedpref().saveModel(AppConstant.USER_INFO, videoRoot.getDetails().get(position));

                        VideoPlayFragment videoPlayerFragment = new VideoPlayFragment();

                        videoPlayerFragment.setArguments(bundle);

                        Toast.makeText(requireContext(), "open", Toast.LENGTH_SHORT).show();
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, videoPlayerFragment).addToBackStack(null).commit();

                    }else {
                        Toast.makeText(requireContext(), ""+shareVideoRoot.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(requireContext(), "Technical issue...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}