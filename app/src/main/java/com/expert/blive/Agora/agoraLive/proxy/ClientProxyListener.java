package com.expert.blive.Agora.agoraLive.proxy;


import com.expert.blive.Agora.agoraLive.proxy.struts.response.AppVersionResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.AudienceListResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.CreateRoomResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.CreateUserResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.EditUserResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.EnterRoomResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.GiftListResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.GiftRankResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.LeaveRoomResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.LoginResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.ModifySeatStateResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.ModifyUserStateResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.MusicListResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.OssPolicyResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.RefreshTokenResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.RoomListResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.SeatStateResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.SendGiftResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.StartStopPkResponse;

public interface ClientProxyListener {
    void onAppVersionResponse(AppVersionResponse response);

    void onRefreshTokenResponse(RefreshTokenResponse refreshTokenResponse);

    void onOssPolicyResponse(OssPolicyResponse response);

    void onMusicLisResponse(MusicListResponse response);

    void onGiftListResponse(GiftListResponse response);

    void onRoomListResponse(RoomListResponse response);

    void onCreateUserResponse(CreateUserResponse response);

    void onEditUserResponse(EditUserResponse response);

    void onLoginResponse(LoginResponse response);

    void onCreateRoomResponse(CreateRoomResponse response);

    void onEnterRoomResponse(EnterRoomResponse response);

    void onLeaveRoomResponse(LeaveRoomResponse response);

    void onAudienceListResponse(AudienceListResponse response);

    void onRequestSeatStateResponse(SeatStateResponse response);

    void onModifyUserStateResponse(ModifyUserStateResponse response);

    void onModifySeatStateResponse(ModifySeatStateResponse response);

    void onSendGiftResponse(SendGiftResponse response);

    void onGiftRankResponse(GiftRankResponse response);

    void onStartStopPkResponse(StartStopPkResponse response);

    void onResponseError(int requestType, int error, String message);
}