package com.expert.blive.Agora.agoraLive.proxy.struts.response;

public class RefreshTokenResponse extends AbsResponse {
    public TokenData data;

    public class TokenData {
        public String rtcToken;
        public String rtmToken;
    }
}
