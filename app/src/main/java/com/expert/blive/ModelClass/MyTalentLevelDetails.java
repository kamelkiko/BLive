package com.expert.blive.ModelClass;

import java.io.Serializable;

public class MyTalentLevelDetails implements Serializable {

    public String talent_level;
    public String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTalent_level() {
        return talent_level;
    }

    public void setTalent_level(String talent_level) {
        this.talent_level = talent_level;
    }
}
