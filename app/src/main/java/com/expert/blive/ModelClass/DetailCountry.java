package com.expert.blive.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailCountry {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<Details> details = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Details> getDetails() {
        return details;
    }

    public void setDetails(List<Details> details) {
        this.details = details;
    }
    public class Details {

        @SerializedName("country")
        @Expose
        private String name;
        @SerializedName("name")
        @Expose
        private String country;
        @SerializedName("code2l")
        @Expose
        private String code2l;

        public String getCountry() {
            return country;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode2l() {
            return code2l;
        }

        public void setCode2l(String code2l) {
            this.code2l = code2l;
        }

    }
}