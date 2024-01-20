package com.expert.blive.ModelClass.Family;

import java.util.ArrayList;

public class FamilyRoot {
    public int status;
    public String message;
    public FamilyDetails details;
    public ArrayList<FamilyDetail> detail;

    public ArrayList<FamilyDetail> getDetail() {

        return detail;
    }

    public void setDetail(ArrayList<FamilyDetail> detail) {

        this.detail = detail;
    }

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

    public FamilyDetails getDetails() {

        return details;
    }

    public void setDetails(FamilyDetails details) {

        this.details = details;
    }

}
