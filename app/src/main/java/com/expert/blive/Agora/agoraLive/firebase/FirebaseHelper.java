package com.expert.blive.Agora.agoraLive.firebase;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.expert.blive.Agora.agoraLive.firebase.models.ModelSendGift;
import com.expert.blive.Agora.agoraLive.firebase.models.ModelSetUserViewer;
import com.expert.blive.Agora.agoraLive.firebase.models.MultiLiveModelCount;
import com.expert.blive.ModelClass.ModelBulletMessage;
import com.expert.blive.ModelClass.ModelPkTimeLimit;
import com.expert.blive.ModelClass.ModelUserEntry;
import com.expert.blive.Agora.agoraLive.MultiLiveHostsModel;
import com.expert.blive.Agora.agoraLive.models.ModelAgoraMessages;
import com.expert.blive.Agora.agoraLive.models.ModelPkRequest;
import com.expert.blive.Agora.agoraLive.models.model.ModelBulletMain;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class    FirebaseHelper {
    public static final String TAG_AGORA_FIRE_BASE = "Agora : Firebase : ";
    public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference databaseReferenceParent = database.getReference("users");
    public static DatabaseReference databaseReferenceParent3 = database.getReference("fakeView");
    public static DatabaseReference databaseReferenceParentChat = database.getReference("Chat");
    public static DatabaseReference databaseReferenceParentSubAdmin = database.getReference("subAdmin");

    public static DatabaseReference databaseReferenceParent4 = database.getReference("fakeViewCount");
    public static DatabaseReference databaseReferenceParent2 = database.getReference("kickOutList");
    public static DatabaseReference databaseReferenceParentBlock = database.getReference("blockUsers");
    public static DatabaseReference databaseReferenceParent5 = database.getReference("muteList");
    public static DatabaseReference databaseReferenceParent6 = database.getReference("audioLiveStatus");
    public static DatabaseReference databaseReferenceParent7 = database.getReference("publicBulletMessage");
    public static DatabaseReference databaseReferenceParent8 = database.getReference("liveUpdate");
    public static DatabaseReference databaseReferenceParent9 = database.getReference("multiLiveGuests");
    public static DatabaseReference databaseReferenceParent10 = database.getReference("multiLiveHostId");
    public static DatabaseReference     databaseReferenceParentAudioBackGround = database.getReference("audioBack");
    public static String userName;

    public FirebaseHelper(firebaseOnlineStatus firebaseOnlineStatus) {
        this.firebaseOnlineStatus = firebaseOnlineStatus;
    }



    @SuppressLint("LongLogTag")
    public static void updateLiveGuestsList(String channelName, MultiLiveHostsModel multiLiveHostsModel) {
        databaseReferenceParent9.child(channelName).setValue(multiLiveHostsModel)
                .addOnSuccessListener(aVoid -> Log.i(TAG_AGORA_FIRE_BASE + " viewer user set ", "success"))
                .addOnFailureListener(e -> Log.i(TAG_AGORA_FIRE_BASE + " viewer user set ", "failure"));
    }


    public static void setHostId(String channelName, int hostId){
        databaseReferenceParent10.child(channelName).setValue(hostId);
    }

    public static void setUserAudioBackImage(String channelName,String userId, String image){

        databaseReferenceParentAudioBackGround.child(channelName).child(userId).setValue(image);


    }

    public static void getAudioBackGround(String channelName,String userId, ValueEventListener valueEventListener){

        databaseReferenceParentAudioBackGround.child(channelName).child(userId).addValueEventListener(valueEventListener);


    }
       public static void removeBack(String channelName){

        databaseReferenceParentAudioBackGround.child(channelName).removeValue();


    }


    public static void removeHostId(String channelName){
        databaseReferenceParent10.child(channelName).removeValue();
    }
    public static void getHostId(String channelName ,ValueEventListener valueEventListener ){
        databaseReferenceParent10.child(channelName).addValueEventListener(valueEventListener);
    }
    public static void getLiveGuestsList(String channelName, ValueEventListener valueEventListener) {
        databaseReferenceParent9.child(channelName).addValueEventListener(valueEventListener);
    }
    public static void removeLiveGuestsList(String channelName, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
        databaseReferenceParent9.child(channelName).removeValue().addOnFailureListener(onFailureListener).addOnSuccessListener(onSuccessListener);
    }

    public static void removeMessages(String token) {
        databaseReferenceParentChat.child(token).removeValue();

    }
    public static void sendMessageFireBase(String token, ModelAgoraMessages modelAgoraMessages) {
        databaseReferenceParentChat.child(token).child(databaseReferenceParentChat.push().getKey()).setValue(modelAgoraMessages).addOnSuccessListener(aVoid -> Log.i(TAG_AGORA_FIRE_BASE + " viewer user set ", "success"))
                .addOnFailureListener(e -> Log.i(TAG_AGORA_FIRE_BASE + " viewer user set ", "failure"));

    }
    public static void setSubAdmin(String token,String userId, HashMap hashMap,OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
        databaseReferenceParentSubAdmin.child(token).child(userId).setValue(hashMap ).addOnSuccessListener(aVoid -> Log.i(TAG_AGORA_FIRE_BASE + " viewer user set ", "success"))
                .addOnFailureListener(e -> Log.i(TAG_AGORA_FIRE_BASE + " viewer user set ", "failure"));

    }
    public static void getAdmin(String token,String userId,ValueEventListener valueEventListener) {
        databaseReferenceParentSubAdmin.child(token).child(userId).addValueEventListener(valueEventListener);
    }

    public static void getAdminList(String token,  ValueEventListener valueEventListener) {
        databaseReferenceParentSubAdmin.child(token).addValueEventListener(valueEventListener);

    }

    public static void removeAdmin(String token,String userId) {
        databaseReferenceParentSubAdmin.child(token).child(userId).removeValue();
    }

    public static void getMessage(String token, ValueEventListener valueEventListener) {
        databaseReferenceParentChat.child(token).addValueEventListener(valueEventListener);

    }

    public static void deletePkRequest(ModelPkRequest modelPkRequest, String channelId, String otherChannelId) {
        databaseReferenceParent
                .child(channelId)
                .child("sentPkRequest")
                .setValue(null);

        databaseReferenceParent
                .child(otherChannelId)
                .child("ReceivePkRequest")
                .child(modelPkRequest.getOtherUserId())
                .setValue(null);
    }

    public static void deleteBulletMessage() {
        databaseReferenceParent7.removeValue();
    }

    public static void removeAdminLiveStatus(String liveUser, ChildEventListener childEventListener) {
        databaseReferenceParent.child(liveUser).removeEventListener(childEventListener);
    }

    public static void removeMuteMessagesListener(String liveName, ChildEventListener childEventListener) {
        databaseReferenceParent.child(liveName).child("muteMessages").removeEventListener(childEventListener);
    }

    public static void removeTimeForAudience(String channelId, ValueEventListener valueEventListener) {
        databaseReferenceParent.child(channelId).child("timeLimitPkbattle").removeEventListener(valueEventListener);
    }

    public static void removeHost(String userId, String channelId, ValueEventListener childGiftListener) {
        databaseReferenceParent.child(channelId).child("myHosts").child(userId).setValue(null);
    }


    public static void getTimerForAudience(String channelId, ValueEventListener valueEventListener) {
        databaseReferenceParent.child(channelId).child("timeLimitPkbattle").addValueEventListener(valueEventListener);
    }


    public static void acceptRejectStatusPkRequest(String channelId, ValueEventListener valueEventListener) {
        databaseReferenceParent
                .child(channelId)
                .child("sentPkRequest")
                .addValueEventListener(valueEventListener);
    }


    public static void setTimerForAudience(String channelId, String otherChannelId, ModelPkTimeLimit modelPkTimeLimit, boolean status) {
        if (status) {
            databaseReferenceParent.child(channelId).child("timeLimitPkbattle").setValue(modelPkTimeLimit);
        } else {
            // remove value
            databaseReferenceParent.child(channelId).child("timeLimitPkbattle").setValue(null);
            databaseReferenceParent.child(otherChannelId).child("timeLimitPkbattle").setValue(null);
        }
    }

    public static void sendPkRequest(ModelPkRequest modelPkRequest, String channelId, String otherChannelId) {
        databaseReferenceParent
                .child(channelId)
                .child("sentPkRequest")
                .setValue(modelPkRequest);

        databaseReferenceParent
                .child(otherChannelId)
                .child("ReceivePkRequest")
                .child(modelPkRequest.getOtherUserId())
                .setValue(modelPkRequest);
    }

    public static void getPkRequest(String channelId, ChildEventListener valueEventListener) {
        databaseReferenceParent.child(channelId).child("ReceivePkRequest").addChildEventListener(valueEventListener);
    }


    public static void getMultiRequest(String liveName, ChildEventListener valueEventListener) {
        databaseReferenceParent.child(liveName).child("multiRequest").addChildEventListener(valueEventListener);
    }


    public static void sendMultiRequest(String userName, String userId, MultiLiveModelCount multiLiveModelCount) {
        databaseReferenceParent
                .child(userName)
                .child("multiRequest")
                .child(userId)
                .setValue(multiLiveModelCount);
    }

    public static void audioLiveStatus(String userId,String status) {
        databaseReferenceParent6.child(userId).setValue(status);
    }

    public static void getAudioLiveStatus(String userId,ValueEventListener valueEventListener) {
        databaseReferenceParent6.child(userId).addValueEventListener(valueEventListener);
    }

    public static void adminStatus(String isLive) {
        databaseReferenceParent
                .child(userName)
                .child("statusLive")
                .setValue(isLive);
    }

    public static void getAdminLiveStatus(String liveUser, ChildEventListener childEventListener) {
        databaseReferenceParent.child(liveUser).addChildEventListener(childEventListener);
    }

    public static void adminHost(String isLive) {
        databaseReferenceParent
                .child(userName)
                .child("noOfHost")
                .setValue(isLive);
    }

    public static void canRequest(String totalAccepts) {
        databaseReferenceParent
                .child(userName)
                .child("canRequest")
                .setValue(totalAccepts);
    }

    public static void setliveUser() {
        databaseReferenceParent.child(userName).setValue(userName).addOnSuccessListener(new OnSuccessListener<Void>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG_AGORA_FIRE_BASE + " live user set ", "success");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Agora : Firebase :  live user set ", "failure");
            }
        });
    }
    @SuppressLint("LongLogTag")
    public static void setViewerUser(String liveUser, ModelSetUserViewer modelSetUserViewer) {
        databaseReferenceParent.child(liveUser).child("viewers").push().setValue(modelSetUserViewer).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i(TAG_AGORA_FIRE_BASE + " viewer user set ", "success");
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i(TAG_AGORA_FIRE_BASE + " viewer user set ", "failure");
            }
        });
    }

    public static void getViewersLive(String liveUser, ChildEventListener childEventListener) {
        databaseReferenceParent.child(liveUser).child("viewers").addChildEventListener(childEventListener);
    }

    public static void removeUserLeaveChannel(String key, String liveUsername, ChildEventListener childEventListener, ChildEventListener childGiftListener) {
        databaseReferenceParent
                .child(liveUsername)
                .child("viewers")
                .child(key)
                .removeValue();

        removePlayerListener(liveUsername, childEventListener, childGiftListener);
    }

    public static void removeUserLeaveChannel(String key, String liveUsername) {
        databaseReferenceParent
                .child(liveUsername)
                .child("viewers")
                .child(key)
                .removeValue();
    }
    public static void removePlayerListener(String liveUser, ChildEventListener childViewerListener, ChildEventListener childGiftListener) {
        databaseReferenceParent.child(liveUser).child("viewers").removeEventListener(childViewerListener);
        databaseReferenceParent.child(liveUser).child("gifts").removeEventListener(childGiftListener);
    }
    public static void sendGift(String liveUsername, ModelSendGift modelSendGift) {
        databaseReferenceParent.child(liveUsername).child("gifts").push().setValue(modelSendGift).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Agora Gift Sent : ", "success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Agora Gift Sent : ", e.getMessage());
            }
        });
    }
    public static void sendPublicBulletMessage(String liveUsername, ModelBulletMessage modelBulletMessage) {
        databaseReferenceParent7.setValue(modelBulletMessage).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Agora Bullet Sent : ", "success");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Agora Bullet Sent : ", e.getMessage());
            }
        });
    }


    public static void getpublicBulletMessage(String liveUsername, ValueEventListener childEventListener) {
        databaseReferenceParent7.addValueEventListener(childEventListener);
    }

    public static void giftsListener(String liveName, ChildEventListener childEventListener) {
        databaseReferenceParent.child(liveName).child("gifts").addChildEventListener(childEventListener);
    }

    public static void banListener(String liveName, ChildEventListener childEventListener) {
        databaseReferenceParent.child(liveName).child("gifts").addChildEventListener(childEventListener);
    }

    public static void deleteGiftAfterRecevingFromFireBase(String key, String channelName) {
        databaseReferenceParent
                .child(channelName)
                .child("gifts")
                .child(key)
                .removeValue();
    }

    public static void setLiveStatus(boolean isLive) {

        databaseReferenceParent
                .child(userName)
                .child("liveStatus")
                .setValue(isLive);
    }

    public static void updateLiveTime(String userName,HashMap<String, String> map) {
        databaseReferenceParent8
                .child(userName)
                .setValue(map);
    }

    public static void removeLiveTime() {
        databaseReferenceParent8
                .child(userName)
                .removeValue();
    }

    public static void getBulletMessage(String livename,ValueEventListener valueEventListener) {
        databaseReferenceParent.child(livename).child("bulletMessage").addValueEventListener(valueEventListener);
    }


    public static void sendBulletMessage(String livename, ModelBulletMain modelBulletMain) {
        databaseReferenceParent.child(livename).child("bulletMessage").setValue(modelBulletMain);
    }

    public static void entryUser(String livename, ModelUserEntry modelUserEntry) {
        databaseReferenceParent.child(livename).child("userEntry").setValue(modelUserEntry);
    }

    public static void getentryUser(String livename, ValueEventListener valueEventListener) {
        databaseReferenceParent.child(livename).child("userEntry").addValueEventListener(valueEventListener);
    }

    public static void removeentryUser(String livename) {
        databaseReferenceParent.child(livename).child("userEntry").removeValue();
    }


    static boolean isLive = false;

    static ValueEventListener getLiveStatusvalueEventListener;

    public void getLiveStatus() {
        getLiveStatusvalueEventListener = new ValueEventListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    databaseReferenceParent.child(userName).child("liveStatus").removeEventListener(getLiveStatusvalueEventListener);
                    isLive = (snapshot.exists()) ? (boolean) snapshot.getValue() : false;
                    Log.i("Agora Live Status Exists:", String.valueOf(snapshot.exists()));
                    Log.i("Agora Live Status : ", String.valueOf(isLive));
                    firebaseOnlineStatus.isOnline(isLive);
                } catch (Exception e) {
                    Log.i("Agora Live Status Exception : ", e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("Agora Live Status : ", error.getMessage());
            }
        };
        databaseReferenceParent.child(userName).child("liveStatus").addValueEventListener(getLiveStatusvalueEventListener);
    }

    firebaseOnlineStatus firebaseOnlineStatus;

    public static void banLiveStatus(String username, String userId, HashMap  hashMap, boolean doBan, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
//        databaseReferenceParent.child(username).child("banList").child(userId).setValue(doBan).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
        databaseReferenceParent2.child(username).child(userId).setValue(hashMap).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void banLBlockStatus(String username, String userId, HashMap hashMap, OnSuccessListener onSuccessListener, OnFailureListener onFailureListener) {
//        databaseReferenceParent.child(username).child("banList").child(userId).setValue(doBan).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
        databaseReferenceParentBlock.child(username).child(userId).setValue(hashMap).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);
    }

    public static void getBanDataLive(String liveName,String userId,ValueEventListener valueEventListener){

//        Query query=databaseReferenceParent2.child(liveName).equalTo(userId);
//        Query query=databaseReferenceParent2.child(liveName).equalTo(userId);

        databaseReferenceParent2.child(liveName).child(userId).addValueEventListener(valueEventListener);
//        query.addChildEventListener(valueEventListener);
    }    public static void getBanlock(String liveName,String userId,ValueEventListener valueEventListener){

//        Query query=databaseReferenceParent2.child(liveName).equalTo(userId);
//        Query query=databaseReferenceParent2.child(liveName).equalTo(userId);

        databaseReferenceParentBlock.child(liveName).child(userId).addValueEventListener(valueEventListener);
//        query.addChildEventListener(valueEventListener);
    }

    public static void removeBanListener(String liveName,String userId,ChildEventListener childEventListener) {
        databaseReferenceParent2.child(liveName).child(userId).removeEventListener(childEventListener);
    }
   public static void removeBanUser(String liveName,String userId, OnSuccessListener onSuccessListener, OnFailureListener onFailureListene) {
        databaseReferenceParent2.child(liveName).child(userId).removeValue().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListene);
    }

    public static void removeKickOutUser(String liveName) {
        databaseReferenceParent2.child(liveName).removeValue();
    }
    public static void removeBlock(String liveName,String userId, OnSuccessListener onSuccessListener, OnFailureListener onFailureListene) {
        databaseReferenceParentBlock.child(liveName).child(userId).removeValue().addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListene);
    }


    public static void getFakeViewers(ChildEventListener childEventListener, int count) {

        Query query = databaseReferenceParent3.limitToFirst(count);
        query.addChildEventListener(childEventListener);
    }

    public static void getfakeCounts(String liveName,ValueEventListener valueEventListener){
        databaseReferenceParent4.child(liveName).child(liveName).addValueEventListener(valueEventListener);
    }

    public static void setMuteList(String username, String userId, HashMap  hashMap,  OnSuccessListener onSuccessListener, OnFailureListener onFailureListener){
        databaseReferenceParent5.child(username).child(userId).setValue(hashMap).addOnSuccessListener(onSuccessListener).addOnFailureListener(onFailureListener);

    }
    public static void getMuteList(String liveName,String userId,ValueEventListener valueEventListener){
        databaseReferenceParent5.child(liveName).child(userId).child("muteStatus").addValueEventListener(valueEventListener);
    }

    public static void getMaxRequestStatus(String liveName, ValueEventListener valueEventListener) {
        databaseReferenceParent.child(liveName).child("canRequest").addValueEventListener(valueEventListener);
    }


    public static void getMyMultiRequest(String liveName, String userId, ValueEventListener valueEventListener) {
        databaseReferenceParent.child(liveName).child("multiRequest").child(userId).addValueEventListener(valueEventListener);
    }


}
