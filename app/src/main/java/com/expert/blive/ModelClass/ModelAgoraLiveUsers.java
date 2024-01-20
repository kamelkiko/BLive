package com.expert.blive.ModelClass;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelAgoraLiveUsers {

    @SerializedName("status")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public class Detail {

        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("host_status")
        @Expose
        private String host_status;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("coin")
        @Expose
        private String coin;
        @SerializedName("leval")
        @Expose
        private String leval;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("followerCount")
        @Expose
        private String followerCount;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("hostType")
        @Expose
        private String hostType;
        @SerializedName("liveGiftings")
        @Expose
        private String liveGiftings;
        @SerializedName("userId")
        @Expose
        private String userId;

        public String getLiveGiftings() {
            return liveGiftings;
        }

        public void setLiveGiftings(String liveGiftings) {
            this.liveGiftings = liveGiftings;
        }

        public String getHost_status() {
            return host_status;
        }

        public void setHost_status(String host_status) {
            this.host_status = host_status;
        }

        @SerializedName("channelName")
        @Expose
        private String channelName;
        @SerializedName("count")
        @Expose
        private String count;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("starCount")
        @Expose
        private String starCount;
        @SerializedName("latitude")
        @Expose
        private String latitude;

        public String getStarCount() {
            return starCount;
        }

        public void setStarCount(String starCount) {
            this.starCount = starCount;
        }

        public String getPosterImageString() {
            return posterImageString;
        }

        public void setPosterImageString(String posterImageString) {
            this.posterImageString = posterImageString;
        }

        @SerializedName("posterImage")
        @Expose
        private String posterImageString;

        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("rtmToken")
        @Expose
        private String rtmToken;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("archivedDate")
        @Expose
        private String archivedDate;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("checkBoxStatus")
        @Expose
        private String checkBoxStatus;
        @SerializedName("box")
        @Expose
        private String box;
        @SerializedName("purchasedCoin")
        @Expose
        private String purchasedCoin;
        @SerializedName("userLeval")
        @Expose
        private String userLeval;
        @SerializedName("startCount")
        @Expose
        private String startCount;
        @SerializedName("followStatus")
        @Expose
        private String followStatus;
        @SerializedName("bool")
        @Expose
        private String bool;
        @SerializedName("myFrame")
        @Expose
        private String myFrame;
        @SerializedName("myGarage")
        @Expose
        private String myGarage;

        public String getMyFrame() {
            return myFrame;
        }

        public void setMyFrame(String myFrame) {
            this.myFrame = myFrame;
        }

        public String getMyGarage() {
            return myGarage;
        }

        public void setMyGarage(String myGarage) {
            this.myGarage = myGarage;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getBool() {
            return bool;
        }

        public void setBool(String bool) {
            this.bool = bool;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getLeval() {
            return leval;
        }

        public void setLeval(String leval) {
            this.leval = leval;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getFollowerCount() {
            return followerCount;
        }

        public void setFollowerCount(String followerCount) {
            this.followerCount = followerCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHostType() {
            return hostType;
        }

        public void setHostType(String hostType) {
            this.hostType = hostType;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getRtmToken() {
            return rtmToken;
        }

        public void setRtmToken(String rtmToken) {
            this.rtmToken = rtmToken;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getArchivedDate() {
            return archivedDate;
        }

        public void setArchivedDate(String archivedDate) {
            this.archivedDate = archivedDate;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getCheckBoxStatus() {
            return checkBoxStatus;
        }

        public void setCheckBoxStatus(String checkBoxStatus) {
            this.checkBoxStatus = checkBoxStatus;
        }

        public String getBox() {
            return box;
        }

        public void setBox(String box) {
            this.box = box;
        }

        public String getPurchasedCoin() {
            return purchasedCoin;
        }

        public void setPurchasedCoin(String purchasedCoin) {
            this.purchasedCoin = purchasedCoin;
        }

        public String getUserLeval() {
            return userLeval;
        }

        public void setUserLeval(String userLeval) {
            this.userLeval = userLeval;
        }

        public String getStartCount() {
            return startCount;
        }

        public void setStartCount(String startCount) {
            this.startCount = startCount;
        }

        public String getFollowStatus() {
            return followStatus;
        }

        public void setFollowStatus(String followStatus) {
            this.followStatus = followStatus;
        }

    }

}
