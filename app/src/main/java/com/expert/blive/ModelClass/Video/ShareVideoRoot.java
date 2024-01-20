package com.expert.blive.ModelClass.Video;

public class ShareVideoRoot {
    public boolean success;
    public String message;
    public ShareVideoDetails details;

    public ShareVideoDetails getDetails() {

        return details;
    }

    public void setDetails(ShareVideoDetails details) {

        this.details = details;
    }

    public boolean isSuccess() {

        return success;
    }

    public void setSuccess(boolean success) {

        this.success = success;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

}
