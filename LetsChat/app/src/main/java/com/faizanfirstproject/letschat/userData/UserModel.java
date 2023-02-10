package com.faizanfirstproject.letschat.userData;

public class UserModel {
    private String UId,name,phoneNumber,profileImage;

    public UserModel() {
    }

    public UserModel(String UId, String name, String phoneNumber, String profileImage) {
        this.UId = UId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }

    public String getUId() {
        return UId;
    }

    public void setUId(String UId) {
        this.UId = UId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
