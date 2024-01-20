package com.expert.blive.Agora.agoraLive.firebase.models;

public class ModelGoLive {

    String viewers,gifters;

    public ModelGoLive( String viewers, String gifters) {
        this.viewers = viewers;
        this.gifters = gifters;
    }


    public String getViewers() {
        return viewers;
    }

    public void setViewers(String viewers) {
        this.viewers = viewers;
    }

    public String getGifters() {
        return gifters;
    }

    public void setGifters(String gifters) {
        this.gifters = gifters;
    }
}
