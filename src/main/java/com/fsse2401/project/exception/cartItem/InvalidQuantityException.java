package com.fsse2401.project.exception.cartItem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidQuantityException extends RuntimeException{
    public InvalidQuantityException(Integer quantity){
        super(String.format("Invalid quantity - quantity: %d", quantity));
    }
}