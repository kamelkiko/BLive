package com.expert.blive.Agora.agoraLive.proxy.interfaces;


import com.expert.blive.Agora.agoraLive.proxy.struts.model.CreateUserBody;
import com.expert.blive.Agora.agoraLive.proxy.struts.model.LoginBody;
import com.expert.blive.Agora.agoraLive.proxy.struts.model.UserRequestBody;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.CreateUserResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.EditUserResponse;
import com.expert.blive.Agora.agoraLive.proxy.struts.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("ent/v1/user")
    Call<CreateUserResponse> requestCreateUser(@Header("reqId") long reqId, @Header("reqType") int reqType,
                                               @Body CreateUserBody body);

    @POST("ent/v1/user/{userId}")
    Call<EditUserResponse> requestEditUser(@Header("token") String token, @Header("reqId") long reqId,
                                           @Header("reqType") int reqType, @Path("userId") String userId,
                                           @Body UserRequestBody info);

    @POST("ent/v1/user/login")
    Call<LoginResponse> requestLogin(@Header("reqId") long reqId, @Header("reqType") int reqType, @Body LoginBody body);
}
