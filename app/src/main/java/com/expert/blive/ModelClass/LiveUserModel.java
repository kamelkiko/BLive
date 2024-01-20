package com.expert.blive.ModelClass;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveUserModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("hostType")
        @Expose
        private String hostType;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("gifId")
        @Expose
        private String gifId;
        @SerializedName("channelName")
        @Expose
        private String channelName;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("rtmToken")
        @Expose
        private String rtmToken;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("count")
        @Expose
        private String count;
        @SerializedName("updated")
        @Expose
        private String updated;
        @SerializedName("archivedDate")
        @Expose
        private Object archivedDate;
        @SerializedName("archivedTime")
        @Expose
        private Object archivedTime;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("createdTime")
        @Expose
        private String createdTime;
        @SerializedName("totaltimePerLive")
        @Expose
        private String totaltimePerLive;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("coin")
        @Expose
        private String coin;
        @SerializedName("monthlyCoins")
        @Expose
        private String monthlyCoins;
        @SerializedName("FollowerCount")
        @Expose
        private Integer followerCount;
        @SerializedName("starCount")
        @Expose
        private String starCount;
        @SerializedName("myFrame")
        @Expose
        private String myFrame;

        public String getMyFrame() {

            return myFrame;
        }

        public void setMyFrame(String myFrame) {

            this.myFrame = myFrame;
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

        public String getGifId() {
            return gifId;
        }

        public void setGifId(String gifId) {
            this.gifId = gifId;
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

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public Object getArchivedDate() {
            return archivedDate;
        }

        public void setArchivedDate(Object archivedDate) {
            this.archivedDate = archivedDate;
        }

        public Object getArchivedTime() {
            return archivedTime;
        }

        public void setArchivedTime(Object archivedTime) {
            this.archivedTime = archivedTime;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public String getTotaltimePerLive() {
            return totaltimePerLive;
        }

        public void setTotaltimePerLive(String totaltimePerLive) {
            this.totaltimePerLive = totaltimePerLive;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getMonthlyCoins() {
            return monthlyCoins;
        }

        public void setMonthlyCoins(String monthlyCoins) {
            this.monthlyCoins = monthlyCoins;
        }

        public Integer getFollowerCount() {
            return followerCount;
        }

        public void setFollowerCount(Integer followerCount) {
            this.followerCount = followerCount;
        }

        public String getStarCount() {
            return starCount;
        }

        public void setStarCount(String starCount) {
            this.starCount = starCount;
        }

    }
}