package com.expert.blive.ModelClass.Video;

import java.io.Serializable;

public class ShareVideoDetails implements Serializable {
    public String videoId;
    public String userId;
    public String name;
    public String image;
    public String description;
    public String videoPath;
    public String commentCount;
    public String viewCount;
    public String likeCount;
    public String thumbnail;
    public String hashTag;
    public String shareCount;

    public String getVideoId() {

        return videoId;
    }

    public void setVideoId(String videoId) {

        this.videoId = videoId;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {

        this.userId = userId;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image = image;
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

    public String getThumbnail() {

        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {

        this.thumbnail = thumbnail;
    }

    public String getHashTag() {

        return hashTag;
    }

    public void setHashTag(String hashTag) {

        this.hashTag = hashTag;
    }

    public String getShareCount() {

        return shareCount;
    }

    public void setShareCount(String shareCount) {

        this.shareCount = shareCount;
    }

}
