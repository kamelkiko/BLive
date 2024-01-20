package com.expert.blive.Agora.agoraLive.firebase;

import com.google.firebase.database.ChildEventListener;

import io.agora.rtc.RtcEngine;

public class ChildEventListenersObject {

   public static ChildEventListener viewerChildListsner,giftChildListsner,liveBanEventListener,bulletEventListener;

   public static RtcEngine rtcEngine;




    public static RtcEngine getRtcEngine() {
        return rtcEngine;
    }

    public static void setRtcEngine(RtcEngine rtcEngine) {
        ChildEventListenersObject.rtcEngine = rtcEngine;
    }

    public static ChildEventListener getViewerChildListsner() {
        return viewerChildListsner;
    }

    public static void setViewerChildListsner(ChildEventListener viewerChildListsner) {
        ChildEventListenersObject.viewerChildListsner = viewerChildListsner;
    }

    public static ChildEventListener getGiftChildListsner() {
        return giftChildListsner;
    }

    public static void setGiftChildListsner(ChildEventListener giftChildListsner) {
        ChildEventListenersObject.giftChildListsner = giftChildListsner;
    }

    public static ChildEventListener getBulletChildListsner() {
        return bulletEventListener;
    }

    public static void setBulletChildListsner(ChildEventListener bulletChildListsner) {
        ChildEventListenersObject.bulletEventListener = bulletChildListsner;
    }

    public static ChildEventListener getLiveBanEventListener() {
        return liveBanEventListener;
    }

    public static void setLiveBanEventListener(ChildEventListener liveBanEventListener) {
        ChildEventListenersObject.liveBanEventListener = liveBanEventListener;
    }
}
