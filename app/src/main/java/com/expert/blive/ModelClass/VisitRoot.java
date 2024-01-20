package com.expert.blive.ModelClass;

import java.util.ArrayList;

public class VisitRoot {
    public int status;
    public String message;
    public ArrayList<VisitorDetail> details;

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {

        this.status = status;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public ArrayList<VisitorDetail> getDetails() {

        return details;
    }

    public void setDetails(ArrayList<VisitorDetail> details) {

        this.details = details;
    }
}
