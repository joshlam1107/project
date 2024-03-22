package com.fsse2401.project.data.cartItem.dto;

public class CartItemSuccessResponseResponseDto {
    public String result;

    public CartItemSuccessResponseResponseDto() {
        setResult("SUCCESS");
    }

    public String getResult() {
        return result;
    }

    private void setResult(String result) {
        this.result = result;
    }
}
