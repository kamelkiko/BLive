package com.expert.blive.Agora.agoraLive.models;

public class ModelAgoraMessages {

    String message,image,username,userId,level,AdminStatus;
    int type;
    long timeStamp;
    String mystryman;

    public ModelAgoraMessages() {
    }

    public ModelAgoraMessages(String message, String image, String username, String userId, String level, int type,String AdminStatus,long timeStamp,String mystryman) {
        this.message = message;
        this.image = image;
        this.username = username;
        this.AdminStatus = AdminStatus;
        this.userId=userId;
        this.level=level;
        this.type=type;
        this.timeStamp=timeStamp;
        this.mystryman=mystryman;
    }

    public String getMystryman() {
        return mystryman;
    }

    public void setMystryman(String mystryman) {
        this.mystryman = mystryman;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getAdminStatus() {
        return AdminStatus;
    }

    public void setAdminStatus(String adminStatus) {
        AdminStatus = adminStatus;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
