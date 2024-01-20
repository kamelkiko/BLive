package com.expert.blive.Agora.agoraLive;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserMultiLiveDetailsModel{
    @SerializedName("uid")
    @Expose
    String uid="";
    @SerializedName("username")
    @Expose
    String username="";

    @SerializedName("userImage")
    @Expose
    String userImage="";

    @SerializedName("userid")
    @Expose
    String userid="";

    public UserMultiLiveDetailsModel(){}

    public UserMultiLiveDetailsModel(String uid, String username, String userImage, String userid) {
        this.uid = uid;
        this.username = username;
        this.userImage = userImage;
        this.userid = userid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

