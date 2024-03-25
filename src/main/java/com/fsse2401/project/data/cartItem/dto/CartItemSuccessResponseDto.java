package com.fsse2401.project.data.cartItem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemSuccessResponseDto {
    public String result;

    public CartItemSuccessResponseDto() {
        setResult("SUCCESS");
    }
}