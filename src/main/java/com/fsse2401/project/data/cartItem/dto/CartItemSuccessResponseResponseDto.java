package com.fsse2401.project.data.cartItem.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CartItemSuccessResponseResponseDto {
    public String result;

    public CartItemSuccessResponseResponseDto() {
        setResult("SUCCESS");
    }
}
