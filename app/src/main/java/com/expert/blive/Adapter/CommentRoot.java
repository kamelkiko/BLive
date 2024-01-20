package com.expert.blive.Adapter;

import java.util.ArrayList;

public class CommentRoot {
    public String success;
    public String message;
    public String likeStatus;
    public String likeCount;
    public ArrayList<CommentDetails> details;

    public String getLikeStatus() {

        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {

        this.likeStatus = likeStatus;
    }

    public String getLikeCount() {

        return likeCount;
    }

    public void setLikeCount(String likeCount) {

        this.likeCount = likeCount;
    }

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

    public ArrayList<CommentDetails> getDetails() {

        return details;
    }

    public void setDetails(ArrayList<CommentDetails> details) {

        this.details = details;
    }

}
