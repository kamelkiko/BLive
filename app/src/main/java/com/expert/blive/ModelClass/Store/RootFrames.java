package com.expert.blive.ModelClass.Store;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RootFrames implements Serializable {

    @SerializedName("success")
    @Expose
    private Integer success;
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

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
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
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("level")
        @Expose
        private String level;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("validity")
        @Expose
        private String validity;
        @SerializedName("available")
        @Expose
        private Boolean available;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("frameId")
        @Expose
        private String frameId;
        @SerializedName("dateFrom")
        @Expose
        private String dateFrom;
        @SerializedName("dateTo")
        @Expose
        private String dateTo;
        @SerializedName("frameIMage")
        @Expose
        private String frameIMage;
        @SerializedName("purchasedType")
        @Expose
        private String purchasedType;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("mainImage")
        @Expose
        private String mainImage;
        @SerializedName("valid")
        @Expose
        private String valid;
        @SerializedName("created")
        @Expose
        private String created;
        @SerializedName("purcheseStatus")
        @Expose
        private Boolean purcheseStatus;
        @SerializedName("isApplied")
        @Expose
        private Boolean isApplied;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;

        public String getThumbnail() {

            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {

            this.thumbnail = thumbnail;
        }

        public String getId() {

            return id;
        }

        public void setId(String id) {

            this.id = id;
        }

        public String getImage() {

            return image;
        }

        public void setImage(String image) {

            this.image = image;
        }

        public String getLevel() {

            return level;
        }

        public void setLevel(String level) {

            this.level = level;
        }

        public String getPrice() {

            return price;
        }

        public void setPrice(String price) {

            this.price = price;
        }

        public String getValidity() {

            return validity;
        }

        public void setValidity(String validity) {

            this.validity = validity;
        }

        public Boolean getAvailable() {

            return available;
        }

        public void setAvailable(Boolean available) {

            this.available = available;
        }

        public String getUserId() {

            return userId;
        }

        public void setUserId(String userId) {

            this.userId = userId;
        }

        public String getFrameId() {

            return frameId;
        }

        public void setFrameId(String frameId) {

            this.frameId = frameId;
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

        public String getFrameIMage() {

            return frameIMage;
        }

        public void setFrameIMage(String frameIMage) {

            this.frameIMage = frameIMage;
        }

        public String getPurchasedType() {

            return purchasedType;
        }

        public void setPurchasedType(String purchasedType) {

            this.purchasedType = purchasedType;
        }

        public String getName() {

            return name;
        }

        public void setName(String name) {

            this.name = name;
        }

        public String getType() {

            return type;
        }

        public void setType(String type) {

            this.type = type;
        }

        public String getMainImage() {

            return mainImage;
        }

        public void setMainImage(String mainImage) {

            this.mainImage = mainImage;
        }

        public String getValid() {

            return valid;
        }

        public void setValid(String valid) {

            this.valid = valid;
        }

        public String getCreated() {

            return created;
        }

        public void setCreated(String created) {

            this.created = created;
        }

        public Boolean getPurcheseStatus() {

            return purcheseStatus;
        }

        public void setPurcheseStatus(Boolean purcheseStatus) {

            this.purcheseStatus = purcheseStatus;
        }

        public Boolean getApplied() {

            return isApplied;
        }

        public void setApplied(Boolean applied) {

            isApplied = applied;
        }

    }
}