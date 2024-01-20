package com.expert.blive.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyWallPaper {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("wallpaperId")
        @Expose
        private String wallpaperId;
        @SerializedName("price")
        @Expose
        private String price;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("dateFrom")
        @Expose
        private String dateFrom;
        @SerializedName("dateTo")
        @Expose
        private String dateTo;
        @SerializedName("wallpaperExpire")
        @Expose
        private String wallpaperExpire;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getWallpaperId() {
            return wallpaperId;
        }

        public void setWallpaperId(String wallpaperId) {
            this.wallpaperId = wallpaperId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDateFrom() {
            return dateFrom;
        }

        public void setDateFrom(String dateFrom) {
            this.dateFrom = dateFrom;
        }

        public String getDateTo() {
            return dateTo;
        }

        public void setDateTo(String dateTo) {
            this.dateTo = dateTo;
        }

        public String getWallpaperExpire() {
            return wallpaperExpire;
        }

        public void setWallpaperExpire(String wallpaperExpire) {
            this.wallpaperExpire = wallpaperExpire;
        }

    }
}
