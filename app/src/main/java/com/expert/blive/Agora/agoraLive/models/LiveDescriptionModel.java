package com.expert.blive.Agora.agoraLive.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveDescriptionModel {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

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

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }
    public class Detail {

        @SerializedName("frist")
        @Expose
        private String frist;
        @SerializedName("sec")
        @Expose
        private String sec;
        @SerializedName("third")
        @Expose
        private String third;

        public String getFrist() {
            return frist;
        }

        public void setFrist(String frist) {
            this.frist = frist;
        }

        public String getSec() {
            return sec;
        }

        public void setSec(String sec) {
            this.sec = sec;
        }

        public String getThird() {
            return third;
        }

        public void setThird(String third) {
            this.third = third;
        }

    }

}