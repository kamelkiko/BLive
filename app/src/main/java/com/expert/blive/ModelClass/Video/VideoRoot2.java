package com.expert.blive.ModelClass.Video;

import java.util.ArrayList;

public class VideoRoot2 {
    public int success;
    public String message;
    public ArrayList<VideoDetail2> details;

    public int getSuccess() {

        return success;
    }

    public void setSuccess(int success) {

        this.success = success;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public ArrayList<VideoDetail2> getDetails() {

        return details;
    }

    public void setDetails(ArrayList<VideoDetail2> details) {

        this.details = details;
    }

}
