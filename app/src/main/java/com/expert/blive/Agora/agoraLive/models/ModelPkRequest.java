package com.expert.blive.Agora.agoraLive.models;



public class ModelPkRequest {

    String requestUserID;
    String otherUserId;
    String requestChannelName;
    String otherChannelName;
    String image;
    String token,otherToken;
    String rtmToken;
    String myName;
    String requestedCoins;
    String requestedImage;

    String otherMainCoins,requestMainCoins;

    public String getOtherMainCoins() {
        return otherMainCoins;
    }

    public void setOtherMainCoins(String otherMainCoins) {
        this.otherMainCoins = otherMainCoins;
    }

    public String getRequestMainCoins() {
        return requestMainCoins;
    }

    public void setRequestMainCoins(String requestMainCoins) {
        this.requestMainCoins = requestMainCoins;
    }

    public String getOtherImage() {
        return otherImage;
    }

    public void setOtherImage(String otherImage) {
        this.otherImage = otherImage;
    }

    String otherImage;


    public String getRequestedImage() {
        return requestedImage;
    }

    public void setRequestedImage(String requestedImage) {
        this.requestedImage = requestedImage;
    }

    public String getPkBattleId() {
        return pkBattleId;
    }

    public void setPkBattleId(String pkBattleId) {
        this.pkBattleId = pkBattleId;
    }

    String pkBattleId;

    public String getRequestedCoins() {
        return requestedCoins;
    }

    public void setRequestedCoins(String requestedCoins) {
        this.requestedCoins = requestedCoins;
    }

    public String getOtherCoins() {
        return otherCoins;
    }

    public void setOtherCoins(String otherCoins) {
        this.otherCoins = otherCoins;
    }

    String otherCoins;
    int timeLimit;

    public String getLiveId() {
        return liveId;
    }

    public void setLiveId(String liveId) {
        this.liveId = liveId;
    }

    String liveId;

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    String hostType;

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    String requestStatus;

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    String otherName;

    public String getRequestUserID() {
        return requestUserID;
    }

    public void setRequestUserID(String requestUserID) {
        this.requestUserID = requestUserID;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getRequestChannelName() {
        return requestChannelName;
    }

    public void setRequestChannelName(String requestChannelName) {
        this.requestChannelName = requestChannelName;
    }

    public String getOtherChannelName() {
        return otherChannelName;
    }

    public void setOtherChannelName(String otherChannelName) {
        this.otherChannelName = otherChannelName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRtmToken() {
        return rtmToken;
    }

    public void setRtmToken(String rtmToken) {
        this.rtmToken = rtmToken;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getOtherToken() {
        return otherToken;
    }

    public void setOtherToken(String otherToken) {
        this.otherToken = otherToken;
    }
}

