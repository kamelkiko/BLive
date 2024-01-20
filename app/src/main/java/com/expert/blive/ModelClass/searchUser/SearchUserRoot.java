package com.expert.blive.ModelClass.searchUser;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchUserRoot  implements Serializable {
    public String success;
    public String message;
    public ArrayList<SearchUserDetail> details;

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

    public ArrayList<SearchUserDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<SearchUserDetail> details) {
        this.details = details;
    }
}
