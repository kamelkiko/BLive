package com.expert.blive.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GiftTopGifters {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("details")
    @Expose
    private List<Detail> details;

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

    public List<Detail> getDetails() {

        return details;
    }

    public void setDetails(List<Detail> details) {

        this.details = details;
    }

    public class Detail {

        @SerializedName("id")
        @Expose
        private String id;

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("username")
        @Expose
        private String username;

        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("coin")
        @Expose
        private String coin;

        public String getId() {

            return id;
        }

        public void setId(String id) {

            this.id = id;
        }

        public String getName() {

            return name;
        }

        public void setName(String name) {

            this.name = name;
        }

        public String getUsername() {

            return username;
        }

        public void setUsername(String username) {

            this.username = username;
        }

        public String getImage() {

            return image;
        }

        public void setImage(String image) {

            this.image = image;
        }

        public String getCoin() {

            return coin;
        }

        public void setCoin(String coin) {

            this.coin = coin;
        }

    }

}