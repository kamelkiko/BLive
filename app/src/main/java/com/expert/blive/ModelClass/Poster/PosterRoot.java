package com.expert.blive.ModelClass.Poster;

import java.io.Serializable;

public class PosterRoot implements Serializable {
    public String success;
    public String message;
    public PosterDetails details;

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

    public PosterDetails getDetails() {
        return details;
    }

    public void setDetails(PosterDetails details) {
        this.details = details;
    }
}
