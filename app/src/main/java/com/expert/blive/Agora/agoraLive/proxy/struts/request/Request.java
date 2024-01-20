package com.expert.blive.Agora.agoraLive.proxy.struts.request;

public class Request {
    // General purposes
    public static final int APP_VERSION = 1;
    public static final int OSS = 2;
    public static final int GIFT_LIST = 3;
    public static final int MUSIC_LIST = 4;

    // User management
    public static final int CREATE_USER = 5;
    public static final int EDIT_USER = 6;
    public static final int USER_LOGIN = 7;

    // Live Room
    public static final int CREATE_ROOM = 8;
    public static final int ROOM_LIST = 9;
    public static final int ENTER_ROOM = 10;
    public static final int LEAVE_ROOM = 11;
    public static final int AUDIENCE_LIST = 12;
    public static final int SEND_GIFT = 13;
    public static final int GIFT_RANK = 14;
    public static final int REFRESH_TOKEN = 15;
    public static final int MODIFY_USER_STATE = 16;
    public static final int MODIFY_SEAT_STATE = 17;
    public static final int SEAT_STATE = 18;
    public static final int PK_START_STOP = 19;

    public static String getRequestString(int request) {
        switch (request) {
            case APP_VERSION: return "app_version";
            case OSS: return "oss";
            case GIFT_LIST: return "gift_list";
            case MUSIC_LIST: return "music_list";
            case CREATE_USER: return "create_user";
            case EDIT_USER: return "edit_user";
            case USER_LOGIN: return "user_login";
            case CREATE_ROOM: return "create_room";
            case ROOM_LIST: return "room_list";
            case ENTER_ROOM: return "enter_room";
            case LEAVE_ROOM: return "leave_room";
            case AUDIENCE_LIST: return "audience_list";
            case SEND_GIFT: return "send_gift";
            case GIFT_RANK: return "gift_rank";
            case REFRESH_TOKEN: return "refresh_token";
            case MODIFY_USER_STATE: return "modify_user_state";
            case MODIFY_SEAT_STATE: return "modify_seat_state";
            case SEAT_STATE: return "seat_state";
            case PK_START_STOP: return "pk_start_stop";
            default: return "unknown";
        }
    }
}