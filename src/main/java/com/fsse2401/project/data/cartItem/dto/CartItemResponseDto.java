package com.fsse2401.project.data.cartItem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2401.project.data.cartItem.domainObject.CartItemResponseData;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
@Getter @Setter
public class CartItemResponseDto {
    private Integer pid;
    private String name;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("price")
    private BigDecimal price;
    @JsonProperty("cart_quantity")
    private Integer quantity;
    @JsonProperty("stock")
    private Integer stock;

    public CartItemResponseDto(CartItemResponseData data) {
        this.pid = data.getProduct().getPid();
        this.name = data.getProduct().getName();
        this.imageUrl = data.getProduct().getImageUrl();
        this.price = data.getProduct().getPrice();
        this.quantity = data.getQuantity();
        this.stock = data.getProduct().getStock();
    }
}