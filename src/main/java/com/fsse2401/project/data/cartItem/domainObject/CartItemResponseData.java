package com.fsse2401.project.data.cartItem.domainObject;

import com.fsse2401.project.entity.CartItemEntity;
import com.fsse2401.project.entity.ProductEntity;
import com.fsse2401.project.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemResponseData {
    private Integer cid;
    private ProductEntity product;
    private UserEntity user;
    private Integer quantity;

    public CartItemResponseData(CartItemEntity entity) {
        this.cid = entity.getCid();
        this.product = entity.getProduct();
        this.user = entity.getUser();
        this.quantity = entity.getQuantity();
    }
}