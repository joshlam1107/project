package com.fsse2401.project.service.impl;

import com.fsse2401.project.data.transaction.domainObject.TransactionResponseData;
import com.fsse2401.project.data.transaction.status.TransactionStatus;
import com.fsse2401.project.data.user.domainObject.FirebaseUserData;
import com.fsse2401.project.entity.CartItemEntity;
import com.fsse2401.project.entity.TransactionEntity;
import com.fsse2401.project.entity.TransactionProductEntity;
import com.fsse2401.project.entity.UserEntity;
import com.fsse2401.project.exception.cartItem.InvalidQuantityException;
import com.fsse2401.project.exception.cartItem.ProductNotEnoughStockException;
import com.fsse2401.project.exception.transaction.TransactionNotFoundException;
import com.fsse2401.project.exception.transaction.TransactionStatusException;
import com.fsse2401.project.exception.UserCartEmptyException;
import com.fsse2401.project.repository.TransactionRepository;
import com.fsse2401.project.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final ProductService productService;
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final CartItemService cartItemService;
    private final TransactionProductService transactionProductService;
    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, UserService userService,
                                  CartItemService cartItemService, TransactionProductService transactionProductService,
                                  ProductService productService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.transactionProductService = transactionProductService;
        this.productService = productService;
    }

    @Override
    public TransactionResponseData createTransaction (FirebaseUserData firebaseUserData){
        try {
            // gather UserEntity and UserCart
            UserEntity userEntity = userService.getEntityByFirebaseUserData(firebaseUserData);
            List<CartItemEntity> userCart = cartItemService.getEntityListByUser(userEntity);

            // throw exception if no UserCart
            if (userCart.isEmpty()) {
                throw new UserCartEmptyException();
            }

            // create TransactionEntity and set transaction status to prepare
            TransactionEntity transactionEntity = new TransactionEntity(userEntity, TransactionStatus.PREPARE);
            // save TransactionEntity first in order to create TransactionProductEntity
            transactionEntity = transactionRepository.save(transactionEntity);

            List<TransactionProductEntity> transactionProductEntityList = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            // create TransactionProductEntityList for items in UserCart
            for (CartItemEntity cartItemEntity : userCart){
                TransactionProductEntity transactionProductEntity =
                        transactionProductService.createTransactionProduct(transactionEntity, cartItemEntity);
                transactionProductEntityList.add(transactionProductEntity);

                //Calculate subtotal for each item and accumulate to total payment
                total = total.add(transactionProductEntity.getPrice().multiply(
                        new BigDecimal(transactionProductEntity.getQuantity()))
                );
            }
            // return total amount to setter
            transactionEntity.setTotal(total);

            // save TransactionEntity again with total payment
            transactionEntity = transactionRepository.save(transactionEntity);
            return new TransactionResponseData(transactionEntity,transactionProductEntityList);
        } catch (UserCartEmptyException ex){
            logger.warn("Prepare transaction: " + ex.getMessage());
            throw ex;
        }
    }
    @Override
    public TransactionResponseData getTransactionByTid (FirebaseUserData firebaseUserData, Integer tid){
        try {
            TransactionEntity transactionEntity = getEntityByTidAndUserFirebaseUid(tid, firebaseUserData.getFirebaseUid());
            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.getTransactionProductEntitiesByTransactionInfoTid(tid);
            return new TransactionResponseData(transactionEntity, transactionProductEntityList);
        } catch (TransactionNotFoundException ex){
            logger.warn("Get transaction: " + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public boolean payTransaction (FirebaseUserData firebaseUserData, Integer tid) {
        try {
            TransactionEntity transactionEntity = getEntityByTidAndUserFirebaseUid(tid, firebaseUserData.getFirebaseUid());

            // if TransactionStatus is not under Prepare, throw exception
            if (transactionEntity.getStatus() != TransactionStatus.PREPARE) {
                throw new TransactionStatusException(transactionEntity.getStatus());
            }

            List<TransactionProductEntity> transactionProductEntityList = transactionProductService.getTransactionProductEntitiesByTransactionInfoTid(tid);

            for (TransactionProductEntity transactionProductEntity : transactionProductEntityList) {

                //double check if order quantity is valid
                if (transactionProductEntity.getQuantity() < 1){
                    throw new InvalidQuantityException(transactionProductEntity.getQuantity());
                }
                // check if product stock is enough to proceed transaction
                if (!productService.isValidQuantity(transactionProductEntity.getPid(), transactionProductEntity.getQuantity())){
                    throw new ProductNotEnoughStockException(productService.getEntityByPid(transactionProductEntity.getPid()), transactionProductEntity.getQuantity());
                }
                // deduct product stock by order quantity once confirm transaction can be proceeded
                productService.deductStock(transactionProductEntity.getPid(), transactionProductEntity.getQuantity());

                // update TransactionStatus to processing
            } transactionEntity.setStatus(TransactionStatus.PROCESSING);
              transactionRepository.save(transactionEntity);
              return true;
        } catch (TransactionStatusException | InvalidQuantityException | ProductNotEnoughStockException ex){
            logger.warn("Pay transaction: " + ex.getMessage());
            throw ex;
        }
    }
    @Override
    public TransactionResponseData finishTransaction (FirebaseUserData firebaseUserData, Integer tid){
        try {
            TransactionEntity transactionEntity = getEntityByTidAndUserFirebaseUid(tid, firebaseUserData.getFirebaseUid());

            // throw exception if TransactionEntity is not under processing status
            if (transactionEntity.getStatus() != TransactionStatus.PROCESSING) {
                throw new TransactionStatusException(transactionEntity.getStatus());
            }
            // clear UserCart after checking status and update TransactionStatus to success
            cartItemService.emptyUserCart(firebaseUserData.getFirebaseUid());
            transactionEntity.setStatus(TransactionStatus.SUCCESS);
            transactionRepository.save(transactionEntity);

            return new TransactionResponseData(transactionEntity,
                    transactionProductService.getTransactionProductEntitiesByTransactionInfoTid(tid));
        } catch (TransactionStatusException ex){
            logger.warn("Finish transaction: "+ ex.getMessage());
            throw ex;
        }
    }
    public TransactionEntity getEntityByTidAndUserFirebaseUid (Integer tid, String firebaseUid){
        return transactionRepository.findTransactionEntityByTidAndUserFirebaseUid(tid, firebaseUid).orElseThrow(
                ()-> new TransactionNotFoundException(tid, firebaseUid));
    }
}