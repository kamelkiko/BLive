package com.expert.blive.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MonthlyHistory implements Serializable {



    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("mesaage")
    @Expose
    private String mesaage;
    @SerializedName("details")
    @Expose
    private List<Detail> details = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMesaage() {
        return mesaage;
    }

    public void setMesaage(String mesaage) {
        this.mesaage = mesaage;
    }

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public class Detail implements Serializable {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("userId")
        @Expose
        private String userId;
        @SerializedName("monthlyCoins")
        @Expose
        private String monthlyCoins;
        @SerializedName("coinSharing")
        @Expose
        private String coinSharing;
        @SerializedName("basicSalary")
        @Expose
        private String basicSalary;
        @SerializedName("bonus1")
        @Expose
        private String bonus1;
        @SerializedName("bonus2")
        @Expose
        private String bonus2;
        @SerializedName("bonus3")
        @Expose
        private String bonus3;
        @SerializedName("liveDuration")
        @Expose
        private String liveDuration;
        @SerializedName("dateFrom")
        @Expose
        private String dateFrom;
        @SerializedName("dateTo")
        @Expose
        private String dateTo;
        @SerializedName("liveDays")
        @Expose
        private String liveDays;
        @SerializedName("coinExchange")
        @Expose
        private String coinExchange;

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

        public String getMonthlyCoins() {
            return monthlyCoins;
        }

        public void setMonthlyCoins(String monthlyCoins) {
            this.monthlyCoins = monthlyCoins;
        }

        public String getCoinSharing() {
            return coinSharing;
        }

        public void setCoinSharing(String coinSharing) {
            this.coinSharing = coinSharing;
        }

        public String getBasicSalary() {
            return basicSalary;
        }

        public void setBasicSalary(String basicSalary) {
            this.basicSalary = basicSalary;
        }

        public String getBonus1() {
            return bonus1;
        }

        public void setBonus1(String bonus1) {
            this.bonus1 = bonus1;
        }

        public String getBonus2() {
            return bonus2;
        }

        public void setBonus2(String bonus2) {
            this.bonus2 = bonus2;
        }

        public String getBonus3() {
            return bonus3;
        }

        public void setBonus3(String bonus3) {
            this.bonus3 = bonus3;
        }

        public String getLiveDuration() {
            return liveDuration;
        }

        public void setLiveDuration(String liveDuration) {
            this.liveDuration = liveDuration;
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

        public String getLiveDays() {
            return liveDays;
        }

        public void setLiveDays(String liveDays) {
            this.liveDays = liveDays;
        }

        public String getCoinExchange() {
            return coinExchange;
        }

        public void setCoinExchange(String coinExchange) {
            this.coinExchange = coinExchange;
        }

    }

}
