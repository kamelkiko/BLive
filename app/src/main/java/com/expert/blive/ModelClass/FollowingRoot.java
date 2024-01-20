package com.expert.blive.ModelClass;

import java.io.Serializable;

public class FollowingRoot implements Serializable {
    public String success;
    public String message;
    public boolean following_status;
    public String following_count;

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

    public boolean isFollowing_status() {
        return following_status;
    }

    public void setFollowing_status(boolean following_status) {
        this.following_status = following_status;
    }

    public String getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(String following_count) {
        this.following_count = following_count;
    }
}
