package com.expert.blive.ModelClass;

import java.io.Serializable;

public class UpdateClass implements Serializable {

    public String success;
    public String message;
    public UpdateRoot details;


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

    public UpdateRoot getDetails() {
        return details;
    }

    public void setDetails(UpdateRoot details) {
        this.details = details;
    }
}
