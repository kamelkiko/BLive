package com.expert.blive.Agora.agoraLive.proxy.struts.model;

public class ModifySeatStateRequestBody {
    private String userId;
    private int state;
    private int no;
    private String virtualAvatar;

    public ModifySeatStateRequestBody(int no, String userId, int state, String virtualAvatar) {
        this.no = no;
        this.userId = userId;
        this.state = state;
        this.virtualAvatar = virtualAvatar;
    }
}
