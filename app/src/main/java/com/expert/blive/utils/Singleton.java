package com.expert.blive.utils;

import com.expert.blive.Agora.agoraLive.models.ModelPrivateMessagePlayer;

import java.util.ArrayList;
import java.util.List;

public class Singleton {

    private  String liveType="";
    private  String hostType="";
    private  String  check="";
    private  String AgoraToken="";
    private  String MyPk="";
    private  String getImage="";
    private  String userId="";
    private  String MyPkBattle="";
    private  int setPosition;
    private  String giftCheck="";

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public String getGetImage() {

        return getImage;
    }

    public void setGetImage(String getImage) {

        this.getImage = getImage;
    }

    public int getSetPosition() {

        return setPosition;
    }

    public void setSetPosition(int setPosition) {

        this.setPosition = setPosition;
    }

    List<ModelPrivateMessagePlayer> privateMessagePlayers = new ArrayList<>();

    public List<ModelPrivateMessagePlayer> getPrivateMessagePlayers() {
        return privateMessagePlayers;
    }

    public String getCheck() {
        return check;
    }

    public String getMyPkBattle() {
        return MyPkBattle;
    }

    public void setMyPkBattle(String myPkBattle) {
        MyPkBattle = myPkBattle;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public void setPrivateMessagePlayers(List<ModelPrivateMessagePlayer> privateMessagePlayers) {
        this.privateMessagePlayers = privateMessagePlayers;
    }
    public String getGiftCheck() {
        return giftCheck;
    }

    public void setGiftCheck(String giftCheck) {
        this.giftCheck = giftCheck;
    }

    public String getMyPk() {
        return MyPk;
    }
    public String getMainHostUserId() {
        return mainHostUserId;
    }

    public void setMainHostUserId(String mainHostUserId) {
        this.mainHostUserId = mainHostUserId;
    }

    String mainHostUserId;
    public void setMyPk(String myPk) {
        MyPk = myPk;
    }

    private boolean isStop=false;
    public String getAudioLive() {
        return audioLive;
    }

    public void setAudioLive(String audioLive) {
        this.audioLive = audioLive;
    }

    String audioLive="";
    String maxJoiners="";

    public String getMaxJoiners() {
        return maxJoiners;
    }
    public String getAgoraToken() {
        return AgoraToken;
    }
    public void setMaxJoiners(String maxJoiners) {
        this.maxJoiners = maxJoiners;
    }

    public void setAgoraToken(String agoraToken) {
        AgoraToken = agoraToken;
    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public String getLiveType() {
        return liveType;
    }

    public void setLiveType(String liveType) {
        this.liveType = liveType;
    }

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }
}
