package com.fsse2401.project.service.impl;

import com.fsse2401.project.data.cartItem.domainObject.CartItemResponseData;
import com.fsse2401.project.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project.entity.CartItemEntity;
import com.fsse2401.project.entity.UserEntity;
import com.fsse2401.project.entity.ProductEntity;
import com.fsse2401.project.exception.cartItem.CartItemNotFoundException;
import com.fsse2401.project.exception.cartItem.InvalidQuantityException;
import com.fsse2401.project.exception.cartItem.ProductOutOfStockException;
import com.fsse2401.project.exception.product.ProductNotFoundException;
import com.fsse2401.project.repository.CartItemRepository;
import com.fsse2401.project.service.CartItemService;
import com.fsse2401.project.service.ProductService;
import com.fsse2401.project.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CartItemServiceImpl implements CartItemService {
    Logger logger = LoggerFactory.getLogger((CartItemServiceImpl.class));
    private final UserService userService;
    private final ProductService productService;
    private final CartItemRepository cartItemRepository;
    @Autowired
    public CartItemServiceImpl(UserService userService, CartItemRepository cartItemRepository, ProductService productService) {
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
        this.productService = productService;
    }
    @Override
    public boolean putCartItem(FirebaseUserData firebaseUserData, Integer pid, Integer quantity) {
        try {
            if (quantity < 1) {
                throw new InvalidQuantityException(quantity);
            }

            Optional <CartItemEntity> cartItemEntityOptional =
                    cartItemRepository.findByProductPidAndUserFirebaseUid(pid,firebaseUserData.getFirebaseUid());

            if (cartItemEntityOptional.isEmpty()){
                ProductEntity productEntity = productService.getEntityByPid(pid);

                if (!isValidQuantity(pid ,quantity)){
                    throw new ProductOutOfStockException(productEntity, quantity);
                }

                CartItemEntity cartItemEntity = new CartItemEntity(productService.getEntityByPid(pid)
                        , userService.getEntityByFirebaseUserData(firebaseUserData)
                        , quantity);
                cartItemRepository.save(cartItemEntity);

            } else {
                CartItemEntity cartItemEntity = cartItemEntityOptional.get();

                if (!isValidQuantity(pid, cartItemEntity.getQuantity() + quantity)) {
                    throw new ProductOutOfStockException(cartItemEntity.getProduct(), cartItemEntity.getQuantity() + quantity);
                }
                cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
                cartItemRepository.save(cartItemEntity);
            }
            return true;
        } catch (InvalidQuantityException ex){
            logger.warn("Put cart item: " + ex.getMessage());
            throw ex;
        } catch (ProductOutOfStockException ex){
            logger.warn("Put cart item: "+ ex.getMessage());
            throw ex;
        }

    }
    @Override
    public List<CartItemResponseData> getUserCart(FirebaseUserData firebaseUserData){
        UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
        List<CartItemResponseData> responseDataList = new ArrayList<>();
        for (CartItemEntity entity : getEntityListByUser(userEntity)){
            responseDataList.add(new CartItemResponseData(entity));
        }
        return responseDataList;
    }
    @Override
    public CartItemResponseData updateCartQuantity (FirebaseUserData firebaseUserData, Integer pid, Integer quantity){
        try {
            if (quantity < 1) {
                throw new InvalidQuantityException(quantity);
            }

            CartItemEntity cartItemEntity = getByProductAndUser(pid, firebaseUserData.getFirebaseUid());

            if (!isValidQuantity(pid, quantity)) {
                throw new ProductOutOfStockException(productService.getEntityByPid(pid), quantity);
            }

            cartItemEntity.setQuantity(quantity);
            cartItemRepository.save(cartItemEntity);
            return new CartItemResponseData(cartItemEntity);

        } catch (InvalidQuantityException ex) {
            logger.warn("Update cart quantity: " + ex.getMessage());
            throw ex;
        } catch (CartItemNotFoundException ex){
            logger.warn("Update cart quantity: "+ ex.getMessage());
            throw ex;
        } catch (ProductOutOfStockException ex) {
            logger.warn("Update cart quantity: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public boolean removeCartItem (FirebaseUserData firebaseUserData, Integer pid){
        try{
            cartItemRepository.deleteCartItemEntityByProductPidAndUserFirebaseUid(pid, firebaseUserData.getFirebaseUid());
            return true;
        } catch (CartItemNotFoundException ex){
            logger.warn("Remove cart item: " + ex.getMessage());
            throw ex;
        }
    }

    public boolean isValidQuantity(Integer pid, Integer quantity) {
        return productService.getProductByProductId(pid).getStock() >= quantity;
    }

    public CartItemEntity getByProductAndUser(Integer pid, String firebaseUid){
        return cartItemRepository.findByProductPidAndUserFirebaseUid(pid, firebaseUid)
                .orElseThrow(() -> new CartItemNotFoundException(pid, firebaseUid));
    }

    public List<CartItemEntity> getEntityListByUser(UserEntity userEntity){
        return cartItemRepository.findAllByUser(userEntity);
    }
    @Override
    @Transactional
    public void emptyUserCart(String firebaseUid){
        cartItemRepository.deleteAllByUserFirebaseUid(firebaseUid);
    }
}
