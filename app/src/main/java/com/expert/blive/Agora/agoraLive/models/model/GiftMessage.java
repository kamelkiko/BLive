package com.expert.blive.Agora.agoraLive.models.model;


public class GiftMessage {
    public GiftMessageData data;

    public static class GiftMessageData {
         public String fromUserId;
         public String fromUserName;
         public String toUserId;
         public String toUserName;
         public int giftId;
    }
}
