package com.expert.blive.ModelClass;


import java.io.Serializable;

public class OtpClass implements Serializable {
    public String success;
    public String message;
    public OtpRoot details;

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

    public OtpRoot getDetails() {
        return details;
    }

    public void setDetails(OtpRoot details) {
        this.details = details;
    }

}

