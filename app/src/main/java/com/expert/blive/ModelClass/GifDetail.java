package com.expert.blive.ModelClass;

import java.io.Serializable;

public class GifDetail implements Serializable {

    public String id;
    public String gifUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }
}
