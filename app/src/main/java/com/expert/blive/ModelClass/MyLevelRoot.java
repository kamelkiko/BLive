package com.expert.blive.ModelClass;

import java.io.Serializable;

public class MyLevelRoot implements Serializable {
    private String success;
    private String message;

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

    public MyLevelDetails getDetails() {
        return details;
    }

    public void setDetails(MyLevelDetails details) {
        this.details = details;
    }

    private MyLevelDetails details;
}
