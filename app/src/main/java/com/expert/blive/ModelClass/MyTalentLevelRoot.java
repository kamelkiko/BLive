package com.expert.blive.ModelClass;

import java.io.Serializable;

public class MyTalentLevelRoot  implements Serializable {
    public String success;
    public String message;
    public MyTalentLevelDetails details;

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

    public MyTalentLevelDetails getDetails() {
        return details;
    }

    public void setDetails(MyTalentLevelDetails details) {
        this.details = details;
    }
}
