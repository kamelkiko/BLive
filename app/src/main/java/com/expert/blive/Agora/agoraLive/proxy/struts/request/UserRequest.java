package com.expert.blive.Agora.agoraLive.proxy.struts.request;

public class UserRequest extends Request {
    public String token;
    public String userName;
    public String userId;
    public String avatar;

    public UserRequest(String userName) {
        this.userName = userName;
    }

    public UserRequest(String token, String userId, String userName) {
        this.token = token;
        this.userId = userId;
        this.userName = userName;
    }
}
