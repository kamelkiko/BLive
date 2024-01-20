package com.expert.blive.ModelClass.getVideos;

import java.io.Serializable;

public class GetVideoDetail implements Serializable {

    public String id;
    public String userId;
    public String hashTag;
    public String description;
    public String videoPath;
    public String allowComment;
    public String allowDuetReact;
    public String allowDownloads;
    public String viewVideo;
    public String soundId;
    public String channelId;
    public String categoryId;
    public String commentCount;
    public String viewCount;
    public String likeCount;
    public String downloadPath;
    public String thumbnail;
    public String status;
    public Object adminComment;
    public Object rejectVideoTime;
    public String created;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getHashTag() {
        return hashTag;
    }

    public void setHashTag(String hashTag) {
        this.hashTag = hashTag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getAllowComment() {
        return allowComment;
    }

    public void setAllowComment(String allowComment) {
        this.allowComment = allowComment;
    }

    public String getAllowDuetReact() {
        return allowDuetReact;
    }

    public void setAllowDuetReact(String allowDuetReact) {
        this.allowDuetReact = allowDuetReact;
    }

    public String getAllowDownloads() {
        return allowDownloads;
    }

    public void setAllowDownloads(String allowDownloads) {
        this.allowDownloads = allowDownloads;
    }

    public String getViewVideo() {
        return viewVideo;
    }

    public void setViewVideo(String viewVideo) {
        this.viewVideo = viewVideo;
    }

    public String getSoundId() {
        return soundId;
    }

    public void setSoundId(String soundId) {
        this.soundId = soundId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getAdminComment() {
        return adminComment;
    }

    public void setAdminComment(Object adminComment) {
        this.adminComment = adminComment;
    }

    public Object getRejectVideoTime() {
        return rejectVideoTime;
    }

    public void setRejectVideoTime(Object rejectVideoTime) {
        this.rejectVideoTime = rejectVideoTime;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
