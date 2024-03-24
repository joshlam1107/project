package com.fsse2401.project.service;

import com.fsse2401.project.data.cartItem.domainObject.CartItemResponseData;
import com.fsse2401.project.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project.entity.CartItemEntity;
import com.fsse2401.project.entity.UserEntity;
import java.util.List;

public interface CartItemService {
    boolean putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity);
    List<CartItemResponseData> getUserCart(FirebaseUserData firebaseUserData);
    CartItemResponseData updateCartQuantity (FirebaseUserData firebaseUserData, Integer pid, Integer quantity);
    boolean removeCartItem (FirebaseUserData firebaseUserData, Integer pid);
    List<CartItemEntity> getEntityListByUser(UserEntity userEntity);
    void emptyUserCart(String firebaseUid);
}