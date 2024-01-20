package com.expert.blive.Agora.agoraLive.proxy.struts.response;


import com.expert.blive.Agora.agoraLive.models.model.PKMessage;
import com.expert.blive.Agora.agoraLive.proxy.struts.model.EnterRoomUserInfo;
import com.expert.blive.Agora.agoraLive.proxy.struts.model.SeatInfo;

import java.util.List;


public class EnterRoomResponse extends AbsResponse {
    public RoomData data;

    public static class RoomData {
        public RoomInfo room;
        public EnterRoomUserInfo user;
    }

    public static class RoomInfo {
        public String roomId;
        public String roomName;
        public String channelName;
        public int type;
        public List<SeatInfo> coVideoSeats;
        public List<RankInfo> rankUsers;
        public int roomRank;
        public int currentUsers;
        public Owner owner;
        public PkInfo pk;
    }

    public static class RankInfo {
        public String userId;
        public String userName;
        public String avatar;
    }

    public static class Owner {
        public String userId;
        public int uid;
        public String userName;
        public int enableVideo;
        public int enableAudio;
    }

    public static class PkInfo {
        public int state;
        public String pkRoomId;
        public String pkChannelName;
        public long pkStartTime;
        public int pkRoomRank;
        public int hostRoomRank;
        public long countDown;
        public Owner pkRoomOwner;
        public PKMessage.RelayConfig relayConfig;
    }
}
