package com.expert.blive.Agora.agoraLive;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MultiLiveHostsModel {
    @SerializedName("channelName")
    @Expose
    String channelName ="";
    @SerializedName("uid")
    @Expose
    String uid="";
    @SerializedName("userid")
    @Expose
    String userid="";
    @SerializedName("username")
    @Expose
    String username="";
    @SerializedName("userImage")
    @Expose
    String userImage="";
    @SerializedName("userMultiLiveDetailsModels")
    @Expose
    ArrayList<UserMultiLiveDetailsModel> userMultiLiveDetailsModels= new ArrayList<>();


    public MultiLiveHostsModel(String channelName, String uid, String userid, String username, String userImage, ArrayList<UserMultiLiveDetailsModel> userMultiLiveDetailsModels) {
        this.channelName = channelName;
        this.uid = uid;
        this.userid = userid;
        this.username = username;
        this.userImage = userImage;
        this.userMultiLiveDetailsModels = userMultiLiveDetailsModels;
    }

    public MultiLiveHostsModel(){}
    public MultiLiveHostsModel(String channelName) {

    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public ArrayList<UserMultiLiveDetailsModel> getUserMultiLiveDetailsModels() {
        return userMultiLiveDetailsModels;
    }

    public void setUserMultiLiveDetailsModels(ArrayList<UserMultiLiveDetailsModel> userMultiLiveDetailsModels) {
        this.userMultiLiveDetailsModels = userMultiLiveDetailsModels;
    }
}

