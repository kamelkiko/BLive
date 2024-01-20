package com.expert.blive.Adapter;

public class CommentDetails {
    public String videoCommentId;
    public String videoId;
    public String userId;
    public String comment;
    public String name;
    public String username;
    public String image;
    public String likeCount;
    public boolean likeStatus;

    public String getLikeCount() {

        return likeCount;
    }

    public void setLikeCount(String likeCount) {

        this.likeCount = likeCount;
    }

    public boolean isLikeStatus() {

        return likeStatus;
    }

    public void setLikeStatus(boolean likeStatus) {

        this.likeStatus = likeStatus;
    }

    public String getVideoCommentId() {

        return videoCommentId;
    }

    public void setVideoCommentId(String videoCommentId) {

        this.videoCommentId = videoCommentId;
    }

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

    public String getComment() {

        return comment;
    }

    public void setComment(String comment) {

        this.comment = comment;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {

        this.image = image;
    }

}
