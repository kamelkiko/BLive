package com.expert.blive.Agora;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GiftModel {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("coin")
    @Expose
    private String coin;
    @SerializedName("details")
    @Expose
    private List<Detail> details;

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

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
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
        @SerializedName("giftCategoryId")
        @Expose
        private String giftCategoryId;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("primeAccount")
        @Expose
        private String primeAccount;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;
        @SerializedName("timing")
        @Expose
        private String timing;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("sound")
        @Expose
        private String sound;
        @SerializedName("updated")
        @Expose
        private String updated;
        @SerializedName("merePaise")
        @Expose
        private String merePaise;
        @SerializedName("type")
        @Expose
        private String type;

        public String getType() {

            return type;
        }

        public void setType(String type) {

            this.type = type;
        }

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGiftCategoryId() {
            return giftCategoryId;
        }

        public void setGiftCategoryId(String giftCategoryId) {
            this.giftCategoryId = giftCategoryId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrimeAccount() {
            return primeAccount;
        }

        public void setPrimeAccount(String primeAccount) {
            this.primeAccount = primeAccount;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public String getTiming() {
            return timing;
        }

        public void setTiming(String timing) {
            this.timing = timing;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }

        public String getMerePaise() {
            return merePaise;
        }

        public void setMerePaise(String merePaise) {
            this.merePaise = merePaise;
        }
    }
}