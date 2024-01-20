package com.expert.blive.ModelClass.ChatInformation;



public class ChatInformation  {

    private  String message;

    String  mediaType="";

    private String objectKey;

    private String image;

    private String date;

    private String time;

    private String userId;

    private String name;

    private String thumbnail;



    public ChatInformation(String message, String type, String objectKey, String date, String time, String name, String image, String mediaType, String thumbnail) {
        this.message = message;
        this.userId = type;
        this.objectKey = objectKey;
        this.date = date;
        this.time = time;
        this.name = name;
        this.image = image;
        this.mediaType = mediaType;
        this.thumbnail = thumbnail;


    }
    public String getThumbnail() {

        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {

        this.thumbnail = thumbnail;
    }

    public ChatInformation() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public String getMediaType() {

        return mediaType;
    }

    public void setMediaType(String mediaType) {

        this.mediaType = mediaType;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}

