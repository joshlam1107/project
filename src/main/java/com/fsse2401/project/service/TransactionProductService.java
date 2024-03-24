package com.fsse2401.project.service;

import com.fsse2401.project.entity.CartItemEntity;
import com.fsse2401.project.entity.TransactionEntity;
import com.fsse2401.project.entity.TransactionProductEntity;
import java.util.List;

public interface TransactionProductService {
    TransactionProductEntity createTransactionProduct (TransactionEntity transactionEntity,
                                                       CartItemEntity cartItemEntity);
    List<TransactionProductEntity> getTransactionProductEntitiesByTransactionInfoTid (Integer tid);
}