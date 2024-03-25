package com.fsse2401.project.repository;

import com.fsse2401.project.entity.CartItemEntity;
import com.fsse2401.project.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends CrudRepository <CartItemEntity, Integer> {
    Optional<CartItemEntity> findByProductPidAndUserFirebaseUid(Integer pid, String firebaseUid);
    List<CartItemEntity> findAllByUser (UserEntity user);
    Integer deleteByProductPidAndUserFirebaseUid(Integer pid, String firebaseUid);
    void deleteAllByUserFirebaseUid (String firebaseUid);
}