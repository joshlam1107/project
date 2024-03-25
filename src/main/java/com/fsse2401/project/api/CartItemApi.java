package com.fsse2401.project.api;

import com.fsse2401.project.data.cartItem.domainObject.CartItemResponseData;
import com.fsse2401.project.data.cartItem.dto.CartItemResponseDto;
import com.fsse2401.project.data.cartItem.dto.CartItemSuccessResponseDto;
import com.fsse2401.project.service.CartItemService;
import com.fsse2401.project.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartItemApi {
    private final CartItemService cartItemService;

    @Autowired
    public CartItemApi(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PutMapping ("/{pid}/{quantity}")
    public CartItemSuccessResponseDto putCartItem (JwtAuthenticationToken jwtToken,
                                                  @PathVariable Integer pid, @PathVariable Integer quantity){
        cartItemService.putCartItem(JwtUtil.getFirebaseUserData(jwtToken), pid, quantity);
        return new CartItemSuccessResponseDto();
    }

    @GetMapping
    public List<CartItemResponseDto> getUserCart (JwtAuthenticationToken jwtToken){
        List<CartItemResponseDto> responseDtoList = new ArrayList<>();
        for (CartItemResponseData cartItemResponseData : cartItemService.getUserCart(JwtUtil.getFirebaseUserData(jwtToken))){
            responseDtoList.add(new CartItemResponseDto(cartItemResponseData));
        }
        return responseDtoList;
    }

    @PatchMapping("/{pid}/{quantity}")
    public CartItemResponseDto updateCartQuantity (JwtAuthenticationToken jwtToken, @PathVariable Integer pid, @PathVariable Integer quantity){
        return new CartItemResponseDto(cartItemService.updateCartQuantity(JwtUtil.getFirebaseUserData(jwtToken), pid, quantity));
    }

    @DeleteMapping("/{pid}")
    public CartItemSuccessResponseDto removeCartItem (JwtAuthenticationToken jwtToken, @PathVariable Integer pid){
        cartItemService.removeCartItem(JwtUtil.getFirebaseUserData(jwtToken), pid);
        return new CartItemSuccessResponseDto();
    }
}