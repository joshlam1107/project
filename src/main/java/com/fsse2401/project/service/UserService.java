package com.fsse2401.project.service;

import com.fsse2401.project.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project.entity.UserEntity;

public interface UserService {
    UserEntity getEntityByFirebaseUserData (FirebaseUserData firebaseUserData);
}