package com.expert.blive.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MothlyModel {

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

        @SerializedName("monthlyCoins")
        @Expose
        private Integer monthlyCoins;
        @SerializedName("monthlyLive")
        @Expose
        private Integer monthlyLive;
        @SerializedName("bonus")
        @Expose
        private Integer bonus;
        @SerializedName("liveDays")
        @Expose
        private Integer liveDays;
        @SerializedName("basicSalary")
        @Expose
        private Integer basicSalary;
        @SerializedName("coinExchange")
        @Expose
        private Integer coinExchange;

        public Integer getMonthlyCoins() {
            return monthlyCoins;
        }

        public void setMonthlyCoins(Integer monthlyCoins) {
            this.monthlyCoins = monthlyCoins;
        }

        public Integer getMonthlyLive() {
            return monthlyLive;
        }

        public void setMonthlyLive(Integer monthlyLive) {
            this.monthlyLive = monthlyLive;
        }

        public Integer getBonus() {
            return bonus;
        }

        public void setBonus(Integer bonus) {
            this.bonus = bonus;
        }

        public Integer getLiveDays() {
            return liveDays;
        }

        public void setLiveDays(Integer liveDays) {
            this.liveDays = liveDays;
        }

        public Integer getBasicSalary() {
            return basicSalary;
        }

        public void setBasicSalary(Integer basicSalary) {
            this.basicSalary = basicSalary;
        }

        public Integer getCoinExchange() {
            return coinExchange;
        }

        public void setCoinExchange(Integer coinExchange) {
            this.coinExchange = coinExchange;
        }

    }

}
