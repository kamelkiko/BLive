package com.expert.blive.ModelClass;

import java.io.Serializable;

public class MyLevelDetails implements Serializable {

    public String getMy_level() {
        return my_level;
    }

    public void setMy_level(String my_level) {
        this.my_level = my_level;
    }

    private String my_level;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
