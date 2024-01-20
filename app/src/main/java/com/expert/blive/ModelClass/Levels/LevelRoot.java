package com.expert.blive.ModelClass.Levels;

import java.io.Serializable;
import java.util.ArrayList;

public class LevelRoot implements Serializable {

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

    public ArrayList<LevelDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<LevelDetail> details) {
        this.details = details;
    }

    public String success;
    public String message;
    public ArrayList<LevelDetail> details;



}
