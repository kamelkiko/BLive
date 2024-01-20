package com.expert.blive.ModelClass;

import java.io.Serializable;
import java.util.ArrayList;

public class GifRoot implements Serializable {

    public String status;
    public String message;
    public ArrayList<GifDetail> details;

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

    public ArrayList<GifDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<GifDetail> details) {
        this.details = details;
    }
}
