package com.expert.blive.ModelClass.ApplyForAgency;

import java.io.Serializable;

public class AgencyRoot implements Serializable {

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

    public AgencyDetails getDetails() {
        return details;
    }

    public void setDetails(AgencyDetails details) {
        this.details = details;
    }

    public AgencyDetails details;

}
