package com.expert.blive.ModelClass;

public class VideoRoot {
    public int status;
    public String message;
    public boolean likeStatus;
    public String success;
    public String likeStatusChange;
    public VideoDetails details;

    public String getLikeStatusChange() {

        return likeStatusChange;
    }

    public void setLikeStatusChange(String likeStatusChange) {

        this.likeStatusChange = likeStatusChange;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {

        this.status = status;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public boolean isLikeStatus() {

        return likeStatus;
    }

    public void setLikeStatus(boolean likeStatus) {

        this.likeStatus = likeStatus;
    }

    public String getSuccess() {

        return success;
    }

    public void setSuccess(String success) {

        this.success = success;
    }

    public VideoDetails getDetails() {

        return details;
    }

    public void setDetails(VideoDetails details) {

        this.details = details;
    }

}
