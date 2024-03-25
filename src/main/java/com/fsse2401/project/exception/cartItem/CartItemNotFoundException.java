package com.fsse2401.project.exception.cartItem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CartItemNotFoundException extends RuntimeException{
    public CartItemNotFoundException (Integer pid, String firebaseUid){
        super(String.format("Cart item not found - user ID: %s, product ID: %s", firebaseUid, pid));
    }
}