package com.expert.blive.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FolowCountStatus {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private Details details;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

        @SerializedName("followersCount")
        @Expose
        private Integer followersCount;
        @SerializedName("followingCount")
        @Expose
        private Integer followingCount;
        @SerializedName("friendsCount")
        @Expose
        private Integer friendsCount;
        @SerializedName("visitorsCount")
        @Expose
        private Integer visitorsCount;

        public Integer getVisitorsCount() {

            return visitorsCount;
        }

        public void setVisitorsCount(Integer visitorsCount) {

            this.visitorsCount = visitorsCount;
        }

        public Integer getFollowersCount() {
            return followersCount;
        }

        public void setFollowersCount(Integer followersCount) {
            this.followersCount = followersCount;
        }

        public Integer getFollowingCount() {
            return followingCount;
        }

        public void setFollowingCount(Integer followingCount) {
            this.followingCount = followingCount;
        }

        public Integer getFriendsCount() {
            return friendsCount;
        }

        public void setFriendsCount(Integer friendsCount) {
            this.friendsCount = friendsCount;
        }

    }
}
