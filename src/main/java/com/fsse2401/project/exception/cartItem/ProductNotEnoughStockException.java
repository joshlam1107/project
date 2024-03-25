package com.fsse2401.project.exception.cartItem;

import com.fsse2401.project.entity.ProductEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotEnoughStockException extends RuntimeException{
    public ProductNotEnoughStockException(ProductEntity product, Integer quantity){
        super(String.format("Product %s does not have enough stock - quantity: %s, remaining stock: %s", product.getName(), quantity, product.getStock()));
    }
}