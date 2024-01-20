package com.expert.blive.ModelClass.Banner;

import java.io.Serializable;
import java.util.ArrayList;

public class BannerRoot implements Serializable {

    public String success;
    public String message;
    public ArrayList<BannerImagesModel> details;

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

    public ArrayList<BannerImagesModel> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<BannerImagesModel> details) {
        this.details = details;
    }
}
