package com.expert.blive.Agora.agoraLive.proxy.struts.model;

public class UserRequestBody {
    String userName;
    String avatar;

    public UserRequestBody(String name, String avatar) {
        this.userName = name;
        this.avatar = avatar;
    }
}
