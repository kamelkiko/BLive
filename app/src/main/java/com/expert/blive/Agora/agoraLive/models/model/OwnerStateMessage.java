package com.expert.blive.Agora.agoraLive.models.model;


public class OwnerStateMessage extends AbsRtmMessage {
    public OwnerState data;

    public static class OwnerState {
        public String userId;
        public int uid;
        public String userName;
        public int enableAudio;
        public int enableVideo;
    }
}
