package com.expert.blive.ModelClass;

import java.io.Serializable;

public class GetCoinDetails implements Serializable {

    public String id;
    public String purchasedCoin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoin() {
        return purchasedCoin;
    }

    public void setCoin(String coin) {
        this.purchasedCoin = coin;
    }
}
