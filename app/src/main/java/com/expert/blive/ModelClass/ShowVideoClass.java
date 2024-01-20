package com.expert.blive.ModelClass;

import java.io.Serializable;
import java.util.List;

public class ShowVideoClass implements Serializable {
    public String notificationCount;
    public String success;
    public String message;
    public List<ShowVideoDetail> details;

    public String getNotificationCount() {

        return notificationCount;
    }

    public void setNotificationCount(String notificationCount) {

        this.notificationCount = notificationCount;
    }

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

    public List<ShowVideoDetail> getDetails() {

        return details;
    }

    public void setDetails(List<ShowVideoDetail> details) {

        this.details = details;
    }

}
