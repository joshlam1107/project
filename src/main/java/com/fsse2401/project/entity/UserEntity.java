package com.fsse2401.project.entity;

import com.fsse2401.project.data.user.domainObject.FirebaseUserData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Getter @Setter @NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;
    @Column(name = "firebase_uid", nullable = false)
    private String firebaseUid;
    @Column(nullable = false)
    private String email;

    public UserEntity(FirebaseUserData userdata) {
        this.firebaseUid = userdata.getFirebaseUid();
        this.email = userdata.getEmail();
    }
}
