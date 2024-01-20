package com.expert.blive.Agora.agoraLive.models.model;


public class ChatMessage extends AbsRtmMessage {
    public ChatMessageData data;

    public ChatMessage(String fromUserId, String fromUserName, String message,String image) {
        data = new ChatMessageData();
        data.fromUserId = fromUserId;
        data.fromUserName = fromUserName;
        data.message = message;
        data.image=image;
    }

    public static class ChatMessageData {
        public String fromUserId;
        public String fromUserName;
        public String message;
        public String image;
    }
}
