package com.expert.blive.Agora.agoraLive.utils;

import java.util.Map;


public interface RtmEventHandler {
    void onConnectionStateChanged(int state, int reason);


    void onTokenExpired();

    void onPeersOnlineStatusChanged(Map<String, Integer> map);
}
