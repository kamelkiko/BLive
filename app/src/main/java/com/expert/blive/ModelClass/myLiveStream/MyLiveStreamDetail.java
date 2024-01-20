package com.expert.blive.ModelClass.myLiveStream;

import java.io.Serializable;

public class MyLiveStreamDetail implements Serializable {

    public String id;
    public String hostType;
    public String userId;
    public String channelName;
    public String token;
    public String latitude;
    public String longitude;
    public String rtmToken;
    public String status;
    public String archivedDate;
    public String created;
    public String count;
    public String updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostType() {
        return hostType;
    }

    public void setHostType(String hostType) {
        this.hostType = hostType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getRtmToken() {
        return rtmToken;
    }

    public void setRtmToken(String rtmToken) {
        this.rtmToken = rtmToken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(String archivedDate) {
        this.archivedDate = archivedDate;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
