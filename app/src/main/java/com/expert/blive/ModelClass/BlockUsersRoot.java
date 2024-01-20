package com.expert.blive.ModelClass;

import java.util.ArrayList;

public class BlockUsersRoot {
    public String success;
    public String message;

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

    public ArrayList<UserBlockDetail> getDetails() {

        return details;
    }

    public void setDetails(ArrayList<UserBlockDetail> details) {

        this.details = details;
    }

    public ArrayList<UserBlockDetail> details;
}
