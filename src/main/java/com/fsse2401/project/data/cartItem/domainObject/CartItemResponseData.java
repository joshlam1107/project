package com.fsse2401.project.data.cartItem.domainObject;

import com.fsse2401.project.entity.CartItemEntity;
import com.fsse2401.project.entity.ProductEntity;
import com.fsse2401.project.entity.UserEntity;

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

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
