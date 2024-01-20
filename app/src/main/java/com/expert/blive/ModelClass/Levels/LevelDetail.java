package com.expert.blive.ModelClass.Levels;

import java.io.Serializable;

public class LevelDetail implements Serializable {

    public String id;
    public String image;
    public String level;
    public String level_num;

    public String created;
    public String updated;
    public String talent_level;
    public String experince;

    public String getTalent_level() {
        return talent_level;
    }

    public void setTalent_level(String talent_level) {
        this.talent_level = talent_level;
    }

    public String getExperince() {
        return experince;
    }

    public void setExperince(String experince) {
        this.experince = experince;
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

    public String getLevel_num() {
        return level_num;
    }

    public void setLevel_num(String level_num) {
        this.level_num = level_num;
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
}
