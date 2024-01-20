package com.expert.blive.ModelClass;

import java.io.Serializable;
import java.util.ArrayList;

public class CountryClass implements Serializable {

    public String success;
    public String message;
    public ArrayList<CountryRoot> details;

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

    public ArrayList<CountryRoot> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<CountryRoot> details) {
        this.details = details;
    }
}
