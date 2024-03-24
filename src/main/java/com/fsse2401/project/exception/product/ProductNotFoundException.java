package com.fsse2401.project.exception.product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException (Integer pid){
        super("Product not found - pid:" + pid);
    }
}