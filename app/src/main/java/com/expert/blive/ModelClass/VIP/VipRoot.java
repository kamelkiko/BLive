package com.expert.blive.ModelClass.VIP;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VipRoot {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("details")
    @Expose
    private Details details;

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

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
    public class Details {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("coins")
        @Expose
        private String coins;
        @SerializedName("coinsTo")
        @Expose
        private String coinsTo;
        @SerializedName("valid")
        @Expose
        private String valid;
        @SerializedName("level")
        @Expose
        private String level;
        @SerializedName("badge")
        @Expose
        private String badge;
        @SerializedName("recieve_coins")
        @Expose
        private String recieveCoins;
        @SerializedName("live_float_tag")
        @Expose
        private String liveFloatTag;
        @SerializedName("enrty_effect")
        @Expose
        private String enrtyEffect;
        @SerializedName("invisible_visitor")
        @Expose
        private String invisibleVisitor;
        @SerializedName("mystry_man_rank")
        @Expose
        private String mystryManRank;
        @SerializedName("mystry_man_broadcast")
        @Expose
        private String mystryManBroadcast;
        @SerializedName("prevent_kick")
        @Expose
        private String preventKick;
        @SerializedName("vip_cust_service")
        @Expose
        private String vipCustService;
        @SerializedName("purchase_status")
        @Expose
        private Boolean purchaseStatus;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCoins() {
            return coins;
        }

        public void setCoins(String coins) {
            this.coins = coins;
        }

        public String getCoinsTo() {
            return coinsTo;
        }

        public void setCoinsTo(String coinsTo) {
            this.coinsTo = coinsTo;
        }

        public String getValid() {
            return valid;
        }

        public void setValid(String valid) {
            this.valid = valid;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getBadge() {
            return badge;
        }

        public void setBadge(String badge) {
            this.badge = badge;
        }

        public String getRecieveCoins() {
            return recieveCoins;
        }

        public void setRecieveCoins(String recieveCoins) {
            this.recieveCoins = recieveCoins;
        }

        public String getLiveFloatTag() {
            return liveFloatTag;
        }

        public void setLiveFloatTag(String liveFloatTag) {
            this.liveFloatTag = liveFloatTag;
        }

        public String getEnrtyEffect() {
            return enrtyEffect;
        }

        public void setEnrtyEffect(String enrtyEffect) {
            this.enrtyEffect = enrtyEffect;
        }

        public String getInvisibleVisitor() {
            return invisibleVisitor;
        }

        public void setInvisibleVisitor(String invisibleVisitor) {
            this.invisibleVisitor = invisibleVisitor;
        }

        public String getMystryManRank() {
            return mystryManRank;
        }

        public void setMystryManRank(String mystryManRank) {
            this.mystryManRank = mystryManRank;
        }

        public String getMystryManBroadcast() {
            return mystryManBroadcast;
        }

        public void setMystryManBroadcast(String mystryManBroadcast) {
            this.mystryManBroadcast = mystryManBroadcast;
        }

        public String getPreventKick() {
            return preventKick;
        }

        public void setPreventKick(String preventKick) {
            this.preventKick = preventKick;
        }

        public String getVipCustService() {
            return vipCustService;
        }

        public void setVipCustService(String vipCustService) {
            this.vipCustService = vipCustService;
        }

        public Boolean getPurchaseStatus() {
            return purchaseStatus;
        }

        public void setPurchaseStatus(Boolean purchaseStatus) {
            this.purchaseStatus = purchaseStatus;
        }

    }
}