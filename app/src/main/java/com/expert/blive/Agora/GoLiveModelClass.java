package com.expert.blive.Agora;

import java.util.List;

public class GoLiveModelClass {
    private String userID = "";
    private String image = "";
    private String liveType = "";
    private String liveStatus = "";
    private boolean live = false;
    private String name = "";
    private String userName = "";
    private String requestStatus = "";
    private String currentDiamond = "";
    private String pkTime = "";
    private String svga = "";
    private String myGarage = "";
    private String mystryman = "";

    public String getMystryman() {

        return mystryman;
    }

    public void setMystryman(String mystryman) {

        this.mystryman = mystryman;
    }

    public String getMyGarage() {
        return myGarage;
    }

    public void setMyGarage(String myGarage) {
        this.myGarage = myGarage;
    }

    public String getSvga() {
        return svga;
    }

    public void setSvga(String svga) {
        this.svga = svga;
    }

    private String pkStatus = "";
    private String allowStatus = "";
    private int position;
    private List<Integer> reservedList;

    public int getPosition() {
        return position;
    }

    public List<Integer> getReservedList() {
        return reservedList;
    }

    public void setReservedList(List<Integer> reservedList) {
        this.reservedList = reservedList;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAllowStatus() {
        return allowStatus;
    }

    public void setAllowStatus(String allowStatus) {
        this.allowStatus = allowStatus;
    }

    private String otherUserChannelName = "";
    private String otherUserToken = "";

    private String banOrKick = "";
    private String mute = "";

    public String getMute() {
        return mute;
    }

    public void setMute(String mute) {
        this.mute = mute;
    }

    public String getBanOrKick() {
        return banOrKick;
    }

    public void setBanOrKick(String banOrKick) {
        this.banOrKick = banOrKick;
    }

    public String getCurrentDiamond() {
        return currentDiamond;
    }

    public void setCurrentDiamond(String currentDiamond) {
        this.currentDiamond = currentDiamond;
    }

    public String getPkTime() {
        return pkTime;
    }

    public void setPkTime(String pkTime) {
        this.pkTime = pkTime;
    }

    public String getPkStatus() {
        return pkStatus;
    }

    public void setPkStatus(String pkStatus) {
        this.pkStatus = pkStatus;
    }

    public String getOtherUserChannelName() {
        return otherUserChannelName;
    }

    public void setOtherUserChannelName(String otherUserChannelName) {
        this.otherUserChannelName = otherUserChannelName;
    }

    public String getOtherUserToken() {
        return otherUserToken;
    }

    public void setOtherUserToken(String otherUserToken) {
        this.otherUserToken = otherUserToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

}
