package com.fsse2401.project.service.impl;

import com.fsse2401.project.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project.entity.UserEntity;
import com.fsse2401.project.repository.UserRepository;
import com.fsse2401.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getEntityByFirebaseUserData (FirebaseUserData firebaseUserData){
//        Optional<UserEntity> userEntityOptional = userRepository.findByFirebaseUid(firebaseUserData.getFirebaseUid());
//        if (userEntityOptional.isEmpty()){
//            UserEntity userEntity = new UserEntity(firebaseUserData);
//            return userRepository.save(userEntity);
//        } else {
//            return userEntityOptional.get();
//        }
        return userRepository.findByFirebaseUid(firebaseUserData.getFirebaseUid()).
                orElseGet(() -> userRepository.save(new UserEntity(firebaseUserData)));
    }
}
