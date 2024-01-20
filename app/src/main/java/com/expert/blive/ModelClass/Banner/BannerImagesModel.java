package com.expert.blive.ModelClass.Banner;

import java.io.Serializable;

public class BannerImagesModel implements Serializable {

    public String id;
    public String banner;
    public String country;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
