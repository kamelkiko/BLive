package com.expert.blive.ModelClass.getVideos;

import com.expert.blive.ModelClass.ShowVideoDetail;

import java.io.Serializable;
import java.util.ArrayList;

public class GetVideoRoot implements Serializable {

    public String message;
    public String success;
    public ArrayList<ShowVideoDetail> details;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public ArrayList<ShowVideoDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<ShowVideoDetail> details) {
        this.details = details;
    }
}
