package com.fsse2401.project.data.user;

import com.fsse2401.project.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseData {
    private Integer uid;
    private String firebaseuid;
    private String email;

    public UserResponseData(UserEntity entity) {
        this.uid = entity.getUid();
        this.firebaseuid = entity.getFirebaseUid();
        this.email = entity.getEmail();
    }
}
