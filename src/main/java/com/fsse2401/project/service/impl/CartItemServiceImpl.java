package com.fsse2401.project.service.impl;

import com.fsse2401.project.data.cartItem.domainObject.CartItemResponseData;
import com.fsse2401.project.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project.entity.CartItemEntity;
import com.fsse2401.project.entity.UserEntity;
import com.fsse2401.project.entity.ProductEntity;
import com.fsse2401.project.exception.cartItem.CartItemNotFoundException;
import com.fsse2401.project.exception.cartItem.InvalidQuantityException;
import com.fsse2401.project.exception.cartItem.ProductNotEnoughStockException;
import com.fsse2401.project.exception.UserCartEmptyException;
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
            //check if input quantity is valid at the beginning
            if (quantity < 1) {
                throw new InvalidQuantityException(quantity);
            }
            //check if there is any existing user cart
            Optional <CartItemEntity> cartItemEntityOptional =
                    cartItemRepository.findByProductPidAndUserFirebaseUid(pid,firebaseUserData.getFirebaseUid());

            //if no user cart is found, create new one
            if (cartItemEntityOptional.isEmpty()){
                ProductEntity productEntity = productService.getEntityByPid(pid);

                // check if order quantity is larger than existing product stock
                if (!isValidQuantity(pid ,quantity)){
                    throw new ProductNotEnoughStockException(productEntity, quantity);
                }

                CartItemEntity cartItemEntity = new CartItemEntity(productService.getEntityByPid(pid)
                        , userService.getEntityByFirebaseUserData(firebaseUserData)
                        , quantity);
                cartItemRepository.save(cartItemEntity);

                // else if there is existing user cart found
            } else {
                CartItemEntity cartItemEntity = cartItemEntityOptional.get();

                // check if sum of order quantity and user cart quantity is larger than existing product stock
                if (!isValidQuantity(pid, cartItemEntity.getQuantity() + quantity)) {
                    throw new ProductNotEnoughStockException(cartItemEntity.getProduct(), cartItemEntity.getQuantity() + quantity);
                }
                // add input quantity to user cart quantity after checking
                cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
                cartItemRepository.save(cartItemEntity);
            }
            return true;
        } catch (InvalidQuantityException | ProductNotEnoughStockException ex){
            logger.warn("Put cart item: " + ex.getMessage());
            throw ex;
        }
    }
    @Override
    public List<CartItemResponseData> getUserCart(FirebaseUserData firebaseUserData){
        try {
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            List<CartItemResponseData> responseDataList = new ArrayList<>();
            // get user cart list by user entity and add into cart item response data list
            for (CartItemEntity entity : getEntityListByUser(userEntity)) {
                responseDataList.add(new CartItemResponseData(entity));
            }
            // throw exception if no item found in user cart
            if (responseDataList.isEmpty()){
                throw new UserCartEmptyException();
            }
            return responseDataList;
        } catch (UserCartEmptyException ex){
            logger.warn("Get user cart: " + ex.getMessage());
            throw ex;
        }
    }
    @Override
    public CartItemResponseData updateCartQuantity (FirebaseUserData firebaseUserData, Integer pid, Integer quantity){
        try {
            //check if input quantity is valid at the beginning
            if (quantity < 1) {
                throw new InvalidQuantityException(quantity);
            }

            CartItemEntity cartItemEntity = getByProductAndUser(pid, firebaseUserData.getFirebaseUid());

            // check if updated order quantity is larger than existing product stock
            if (!isValidQuantity(pid, quantity)) {
                throw new ProductNotEnoughStockException(productService.getEntityByPid(pid), quantity);
            }
            // directly edit the number of order quantity
            cartItemEntity.setQuantity(quantity);
            cartItemRepository.save(cartItemEntity);
            return new CartItemResponseData(cartItemEntity);
        } catch (InvalidQuantityException | CartItemNotFoundException | ProductNotEnoughStockException ex) {
            logger.warn("Update cart quantity: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    @Transactional
    public boolean removeCartItem (FirebaseUserData firebaseUserData, Integer pid){
            // use firebase userID and productID to check if cart item exists, then delete
            Integer count = cartItemRepository.deleteByProductPidAndUserFirebaseUid(pid, firebaseUserData.getFirebaseUid());
            // throw exception if CartItem is not found as count is less than 1
            if (count <= 0){
                CartItemNotFoundException ex = new CartItemNotFoundException(pid, firebaseUserData.getFirebaseUid());
                logger.warn("Remove cart item: " + ex.getMessage());
                throw ex;
            }
            return true;
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
