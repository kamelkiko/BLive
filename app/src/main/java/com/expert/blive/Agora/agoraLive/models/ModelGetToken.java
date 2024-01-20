package com.expert.blive.Agora.agoraLive.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelGetToken {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("requestStatus")
    @Expose
    private String requestStatus;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private Details details;

    public String getSuccess() {
        return success;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
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

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
    public class Details {

        @SerializedName("followerCount")
        @Expose
        private String followerCount;
        @SerializedName("checkBoxStatus")
        @Expose
        private String checkBoxStatus;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("coin")
        @Expose
        private String coin;
        @SerializedName("userLeval")
        @Expose
        private String userLeval;
        @SerializedName("starCount")
        @Expose
        private String starCount;
        @SerializedName("toke")
        @Expose
        private String toke;
        @SerializedName("count")
        @Expose
        private String count;
        @SerializedName("box")
        @Expose
        private String box;
        @SerializedName("channelName")
        @Expose
        private String channelName;
        @SerializedName("rtmToken")
        @Expose
        private String rtmToken;
        @SerializedName("mainId")
        @Expose
        private String mainId;
        @SerializedName("bool")
        @Expose
        private String bool;


        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getFollowerCount() {
            return followerCount;
        }

        public String getBool() {
            return bool;
        }

        public void setBool(String bool) {
            this.bool = bool;
        }

        public void setFollowerCount(String followerCount) {
            this.followerCount = followerCount;
        }

        public String getCheckBoxStatus() {
            return checkBoxStatus;
        }

        public void setCheckBoxStatus(String checkBoxStatus) {
            this.checkBoxStatus = checkBoxStatus;
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

        public String getUserLeval() {
            return userLeval;
        }

        public void setUserLeval(String userLeval) {
            this.userLeval = userLeval;
        }

        public String getStarCount() {
            return starCount;
        }

        public void setStarCount(String starCount) {
            this.starCount = starCount;
        }

        public String getToke() {
            return toke;
        }

        public void setToke(String toke) {
            this.toke = toke;
        }

        public String getBox() {
            return box;
        }

        public void setBox(String box) {
            this.box = box;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getRtmToken() {
            return rtmToken;
        }

        public void setRtmToken(String rtmToken) {
            this.rtmToken = rtmToken;
        }

        public String getMainId() {
            return mainId;
        }

        public void setMainId(String mainId) {
            this.mainId = mainId;
        }

    }
}