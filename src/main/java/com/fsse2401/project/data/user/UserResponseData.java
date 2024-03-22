package com.fsse2401.project.data.user;

import com.fsse2401.project.entity.UserEntity;

public class UserResponseData {
    private Integer uid;
    private String firebaseuid;
    private String email;

    public UserResponseData(UserEntity entity) {
        this.uid = entity.getUid();
        this.firebaseuid = entity.getFirebaseUid();
        this.email = entity.getEmail();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFirebaseuid() {
        return firebaseuid;
    }

    public void setFirebaseuid(String firebaseuid) {
        this.firebaseuid = firebaseuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
