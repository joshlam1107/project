package com.fsse2401.project.exception.cartItem;

import com.fsse2401.project.entity.ProductEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductOutOfStockException extends RuntimeException{
    public ProductOutOfStockException (ProductEntity product, Integer quantity){
        super(String.format("Product %s out of stock - quantity: %s, remaining stock: %s", product.getName(), quantity, product.getStock()));
    }
}