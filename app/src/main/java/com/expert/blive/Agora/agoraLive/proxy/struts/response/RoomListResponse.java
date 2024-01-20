package com.expert.blive.Agora.agoraLive.proxy.struts.response;


import com.expert.blive.Agora.agoraLive.proxy.struts.model.RoomInfo;

import java.util.List;


public class RoomListResponse extends AbsResponse {
    public RoomList data;

    public static class RoomList {
        public int count;
        public int total;
        public String nextId;
        public List<RoomInfo> list;
    }
}
