package com.expert.blive.Agora.agoraLive.models;


public class ModelPrivateMessagePlayer {
    String image,message,username,userId;
    boolean isMessageFromLive;

    public ModelPrivateMessagePlayer(String image, String message, String username, String userId, boolean isMessageFromLive) {
        this.image = image;
        this.message = message;
        this.username = username;
        this.userId = userId;
        this.isMessageFromLive = isMessageFromLive;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isMessageFromLive() {
        return isMessageFromLive;
    }

    public void setMessageFromLive(boolean messageFromLive) {
        isMessageFromLive = messageFromLive;
    }
}
