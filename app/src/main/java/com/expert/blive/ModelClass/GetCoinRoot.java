package com.expert.blive.ModelClass;

import java.io.Serializable;

public class GetCoinRoot implements Serializable {

    public String success;
    public String message;
    public GetCoinDetails details;

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

    public GetCoinDetails getDetails() {
        return details;
    }

    public void setDetails(GetCoinDetails details) {
        this.details = details;
    }
}
