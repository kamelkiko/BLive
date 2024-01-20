package com.expert.blive.ModelClass.myLiveStream;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



    public class MyLiveStreamRoot {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("userDetails")
        @Expose
        private UserDetails userDetails;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public UserDetails getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(UserDetails userDetails) {
            this.userDetails = userDetails;
        }

        public class UserDetails {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("userBanStatus")
            @Expose
            private String userBanStatus;
            @SerializedName("badge")
            @Expose
            private String badge;
            @SerializedName("crown")
            @Expose
            private String crown;
            @SerializedName("leval")
            @Expose
            private String leval;
            @SerializedName("my_level")
            @Expose
            private String myLevel;
            @SerializedName("totaltimePerLive")
            @Expose
            private String totaltimePerLive;



            public void setTotaltimePerLive(String totaltimePerLive) {
                this.totaltimePerLive = totaltimePerLive;
            }

            @SerializedName("talent_level")
            @Expose

            private String talentLevel;
            @SerializedName("expCoin")
            @Expose
            private String expCoin;
            @SerializedName("wallet")
            @Expose
            private String wallet;
            @SerializedName("incomeDollar")
            @Expose
            private String incomeDollar;
            @SerializedName("beans")
            @Expose
            private String beans;
            @SerializedName("privateAccount")
            @Expose
            private String privateAccount;
            @SerializedName("loginOtp")
            @Expose
            private String loginOtp;
            @SerializedName("likeVideo")
            @Expose
            private String likeVideo;
            @SerializedName("followingUser")
            @Expose
            private String followingUser;
            @SerializedName("profilePhotoStatus")
            @Expose
            private String profilePhotoStatus;
            @SerializedName("onlineStatus")
            @Expose
            private String onlineStatus;
            @SerializedName("coin")
            @Expose
            private String coin;
            @SerializedName("total_send_coin")
            @Expose
            private String totalSendCoin;
            @SerializedName("purchasedCoin")
            @Expose
            private String purchasedCoin;
            @SerializedName("username")
            @Expose
            private String username;
            @SerializedName("name")
            @Expose
            private String name;
            @SerializedName("email")
            @Expose
            private String email;
            @SerializedName("phone")
            @Expose
            private String phone;
            @SerializedName("password")
            @Expose
            private String password;
            @SerializedName("image")
            @Expose
            private String image;
            @SerializedName("bio")
            @Expose
            private String bio;
            @SerializedName("social_id")
            @Expose
            private String socialId;
            @SerializedName("gender")
            @Expose
            private String gender;
            @SerializedName("locationName")
            @Expose
            private String locationName;
            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;
            @SerializedName("dob")
            @Expose
            private String dob;
            @SerializedName("video")
            @Expose
            private String video;
            @SerializedName("followerCount")
            @Expose
            private String followerCount;
            @SerializedName("country")
            @Expose
            private String country;
            @SerializedName("likeNotifaction")
            @Expose
            private String likeNotifaction;
            @SerializedName("commentNotification")
            @Expose
            private String commentNotification;
            @SerializedName("followersNotification")
            @Expose
            private String followersNotification;
            @SerializedName("messageNotification")
            @Expose
            private String messageNotification;
            @SerializedName("videoNotification")
            @Expose
            private String videoNotification;
            @SerializedName("reg_id")
            @Expose
            private String regId;
            @SerializedName("deviceId")
            @Expose
            private String deviceId;
            @SerializedName("device_type")
            @Expose
            private String deviceType;
            @SerializedName("login_type")
            @Expose
            private String loginType;
            @SerializedName("usernameChangeStatus")
            @Expose
            private String usernameChangeStatus;
            @SerializedName("created")
            @Expose
            private String created;
            @SerializedName("updated")
            @Expose
            private String updated;
            @SerializedName("status")
            @Expose
            private String status;
            @SerializedName("hotlist")
            @Expose
            private String hotlist;
            @SerializedName("liveHotlist")
            @Expose
            private String liveHotlist;
            @SerializedName("liveHostListDateTime")
            @Expose
            private String liveHostListDateTime;
            @SerializedName("liveStatus")
            @Expose
            private String liveStatus;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("nationalId")
            @Expose
            private String nationalId;
            @SerializedName("agencyId")
            @Expose
            private String agencyId;
            @SerializedName("paymentType")
            @Expose
            private String paymentType;
            @SerializedName("join_agencyId")
            @Expose
            private String joinAgencyId;
            @SerializedName("host_status")
            @Expose
            private String hostStatus;
            @SerializedName("registerType")
            @Expose
            private String registerType;
            @SerializedName("profileStatus")
            @Expose
            private String profileStatus;
            @SerializedName("posterImage")
            @Expose
            private Object posterImage;
            @SerializedName("banUnban")
            @Expose
            private String banUnban;
            @SerializedName("kick")
            @Expose
            private String kick;
            @SerializedName("agencyBan")
            @Expose
            private String agencyBan;
            @SerializedName("antikick_block_mute")
            @Expose
            private String antikickBlockMute;
            @SerializedName("screen_shot_and_recording")
            @Expose
            private String screenShotAndRecording;
            @SerializedName("dp_approve")
            @Expose
            private String dpApprove;
            @SerializedName("admin")
            @Expose
            private String admin;
            @SerializedName("allLives")
            @Expose
            private List<AllLife> allLives = null;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUserBanStatus() {
                return userBanStatus;
            }

            public void setUserBanStatus(String userBanStatus) {
                this.userBanStatus = userBanStatus;
            }

            public String getBadge() {
                return badge;
            }

            public void setBadge(String badge) {
                this.badge = badge;
            }

            public String getCrown() {
                return crown;
            }

            public void setCrown(String crown) {
                this.crown = crown;
            }

            public String getLeval() {
                return leval;
            }

            public void setLeval(String leval) {
                this.leval = leval;
            }

            public String getMyLevel() {
                return myLevel;
            }

            public void setMyLevel(String myLevel) {
                this.myLevel = myLevel;
            }

            public String getTalentLevel() {
                return talentLevel;
            }

            public void setTalentLevel(String talentLevel) {
                this.talentLevel = talentLevel;
            }

            public String getExpCoin() {
                return expCoin;
            }

            public void setExpCoin(String expCoin) {
                this.expCoin = expCoin;
            }

            public String getWallet() {
                return wallet;
            }

            public void setWallet(String wallet) {
                this.wallet = wallet;
            }

            public String getIncomeDollar() {
                return incomeDollar;
            }

            public void setIncomeDollar(String incomeDollar) {
                this.incomeDollar = incomeDollar;
            }

            public String getBeans() {
                return beans;
            }

            public void setBeans(String beans) {
                this.beans = beans;
            }

            public String getPrivateAccount() {
                return privateAccount;
            }

            public void setPrivateAccount(String privateAccount) {
                this.privateAccount = privateAccount;
            }

            public String getLoginOtp() {
                return loginOtp;
            }

            public void setLoginOtp(String loginOtp) {
                this.loginOtp = loginOtp;
            }

            public String getLikeVideo() {
                return likeVideo;
            }

            public void setLikeVideo(String likeVideo) {
                this.likeVideo = likeVideo;
            }

            public String getFollowingUser() {
                return followingUser;
            }

            public void setFollowingUser(String followingUser) {
                this.followingUser = followingUser;
            }

            public String getProfilePhotoStatus() {
                return profilePhotoStatus;
            }

            public void setProfilePhotoStatus(String profilePhotoStatus) {
                this.profilePhotoStatus = profilePhotoStatus;
            }

            public String getOnlineStatus() {
                return onlineStatus;
            }

            public void setOnlineStatus(String onlineStatus) {
                this.onlineStatus = onlineStatus;
            }

            public String getCoin() {
                return coin;
            }

            public void setCoin(String coin) {
                this.coin = coin;
            }

            public String getTotalSendCoin() {
                return totalSendCoin;
            }

            public void setTotalSendCoin(String totalSendCoin) {
                this.totalSendCoin = totalSendCoin;
            }

            public String getPurchasedCoin() {
                return purchasedCoin;
            }

            public void setPurchasedCoin(String purchasedCoin) {
                this.purchasedCoin = purchasedCoin;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getBio() {
                return bio;
            }

            public void setBio(String bio) {
                this.bio = bio;
            }

            public String getSocialId() {
                return socialId;
            }

            public void setSocialId(String socialId) {
                this.socialId = socialId;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getLocationName() {
                return locationName;
            }

            public void setLocationName(String locationName) {
                this.locationName = locationName;
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

            public String getDob() {
                return dob;
            }

            public void setDob(String dob) {
                this.dob = dob;
            }

            public String getVideo() {
                return video;
            }

            public void setVideo(String video) {
                this.video = video;
            }

            public String getFollowerCount() {
                return followerCount;
            }

            public void setFollowerCount(String followerCount) {
                this.followerCount = followerCount;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getLikeNotifaction() {
                return likeNotifaction;
            }

            public void setLikeNotifaction(String likeNotifaction) {
                this.likeNotifaction = likeNotifaction;
            }

            public String getCommentNotification() {
                return commentNotification;
            }

            public void setCommentNotification(String commentNotification) {
                this.commentNotification = commentNotification;
            }

            public String getFollowersNotification() {
                return followersNotification;
            }

            public void setFollowersNotification(String followersNotification) {
                this.followersNotification = followersNotification;
            }

            public String getMessageNotification() {
                return messageNotification;
            }

            public void setMessageNotification(String messageNotification) {
                this.messageNotification = messageNotification;
            }

            public String getVideoNotification() {
                return videoNotification;
            }

            public void setVideoNotification(String videoNotification) {
                this.videoNotification = videoNotification;
            }

            public String getRegId() {
                return regId;
            }

            public void setRegId(String regId) {
                this.regId = regId;
            }

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getDeviceType() {
                return deviceType;
            }

            public void setDeviceType(String deviceType) {
                this.deviceType = deviceType;
            }

            public String getLoginType() {
                return loginType;
            }

            public void setLoginType(String loginType) {
                this.loginType = loginType;
            }

            public String getUsernameChangeStatus() {
                return usernameChangeStatus;
            }

            public void setUsernameChangeStatus(String usernameChangeStatus) {
                this.usernameChangeStatus = usernameChangeStatus;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public String getUpdated() {
                return updated;
            }

            public void setUpdated(String updated) {
                this.updated = updated;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getHotlist() {
                return hotlist;
            }

            public void setHotlist(String hotlist) {
                this.hotlist = hotlist;
            }

            public String getLiveHotlist() {
                return liveHotlist;
            }

            public void setLiveHotlist(String liveHotlist) {
                this.liveHotlist = liveHotlist;
            }

            public String getLiveHostListDateTime() {
                return liveHostListDateTime;
            }

            public void setLiveHostListDateTime(String liveHostListDateTime) {
                this.liveHostListDateTime = liveHostListDateTime;
            }

            public String getLiveStatus() {
                return liveStatus;
            }

            public void setLiveStatus(String liveStatus) {
                this.liveStatus = liveStatus;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getNationalId() {
                return nationalId;
            }

            public void setNationalId(String nationalId) {
                this.nationalId = nationalId;
            }

            public String getAgencyId() {
                return agencyId;
            }

            public void setAgencyId(String agencyId) {
                this.agencyId = agencyId;
            }

            public String getPaymentType() {
                return paymentType;
            }

            public void setPaymentType(String paymentType) {
                this.paymentType = paymentType;
            }

            public String getJoinAgencyId() {
                return joinAgencyId;
            }

            public void setJoinAgencyId(String joinAgencyId) {
                this.joinAgencyId = joinAgencyId;
            }

            public String getHostStatus() {
                return hostStatus;
            }

            public void setHostStatus(String hostStatus) {
                this.hostStatus = hostStatus;
            }

            public String getRegisterType() {
                return registerType;
            }

            public void setRegisterType(String registerType) {
                this.registerType = registerType;
            }

            public String getProfileStatus() {
                return profileStatus;
            }

            public void setProfileStatus(String profileStatus) {
                this.profileStatus = profileStatus;
            }

            public Object getPosterImage() {
                return posterImage;
            }

            public void setPosterImage(Object posterImage) {
                this.posterImage = posterImage;
            }

            public String getBanUnban() {
                return banUnban;
            }

            public void setBanUnban(String banUnban) {
                this.banUnban = banUnban;
            }

            public String getKick() {
                return kick;
            }

            public void setKick(String kick) {
                this.kick = kick;
            }

            public String getAgencyBan() {
                return agencyBan;
            }

            public void setAgencyBan(String agencyBan) {
                this.agencyBan = agencyBan;
            }

            public String getAntikickBlockMute() {
                return antikickBlockMute;
            }

            public void setAntikickBlockMute(String antikickBlockMute) {
                this.antikickBlockMute = antikickBlockMute;
            }

            public String getScreenShotAndRecording() {
                return screenShotAndRecording;
            }

            public void setScreenShotAndRecording(String screenShotAndRecording) {
                this.screenShotAndRecording = screenShotAndRecording;
            }

            public String getDpApprove() {
                return dpApprove;
            }

            public void setDpApprove(String dpApprove) {
                this.dpApprove = dpApprove;
            }

            public String getAdmin() {
                return admin;
            }

            public void setAdmin(String admin) {
                this.admin = admin;
            }

            public List<AllLife> getAllLives() {
                return allLives;
            }

            public void setAllLives(List<AllLife> allLives) {
                this.allLives = allLives;
            }


            public class AllLife {

                @SerializedName("id")
                @Expose
                private String id;
                @SerializedName("hostType")
                @Expose
                private String hostType;
                @SerializedName("userId")
                @Expose
                private String userId;
                @SerializedName("gifId")
                @Expose
                private String gifId;
                @SerializedName("channelName")
                @Expose
                private String channelName;
                @SerializedName("token")
                @Expose
                private String token;
                @SerializedName("latitude")
                @Expose
                private String latitude;
                @SerializedName("longitude")
                @Expose
                private String longitude;
                @SerializedName("rtmToken")
                @Expose
                private String rtmToken;
                @SerializedName("status")
                @Expose
                private String status;
                @SerializedName("count")
                @Expose
                private String count;
                @SerializedName("updated")
                @Expose
                private String updated;
                @SerializedName("archivedDate")
                @Expose
                private String archivedDate;
                @SerializedName("archivedTime")
                @Expose
                private String archivedTime;
                @SerializedName("createdDate")
                @Expose
                private String createdDate;
                @SerializedName("createdTime")
                @Expose
                private String createdTime;
                @SerializedName("totaltimePerLive")
                @Expose
                private String totaltimePerLive;
                @SerializedName("created")
                @Expose
                private String created;
                @SerializedName("totalCoin")
                @Expose
                private Object totalCoin;

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

                public String getGifId() {
                    return gifId;
                }

                public void setGifId(String gifId) {
                    this.gifId = gifId;
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

                public String getArchivedDate() {
                    return archivedDate;
                }

                public void setArchivedDate(String archivedDate) {
                    this.archivedDate = archivedDate;
                }

                public String getArchivedTime() {
                    return archivedTime;
                }

                public void setArchivedTime(String archivedTime) {
                    this.archivedTime = archivedTime;
                }

                public String getCreatedDate() {
                    return createdDate;
                }

                public void setCreatedDate(String createdDate) {
                    this.createdDate = createdDate;
                }

                public String getCreatedTime() {
                    return createdTime;
                }

                public void setCreatedTime(String createdTime) {
                    this.createdTime = createdTime;
                }

                public String getTotaltimePerLive() {
                    return totaltimePerLive;
                }

                public void setTotaltimePerLive(String totaltimePerLive) {
                    this.totaltimePerLive = totaltimePerLive;
                }

                public String getCreated() {
                    return created;
                }

                public void setCreated(String created) {
                    this.created = created;
                }

                public Object getTotalCoin() {
                    return totalCoin;
                }

                public void setTotalCoin(Object totalCoin) {
                    this.totalCoin = totalCoin;
                }

            }

        }


}
