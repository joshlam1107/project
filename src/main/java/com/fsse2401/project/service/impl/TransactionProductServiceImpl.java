package com.fsse2401.project.service.impl;

import com.fsse2401.project.entity.*;
import com.fsse2401.project.repository.TransactionProductRepository;
import com.fsse2401.project.service.TransactionProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionProductServiceImpl implements TransactionProductService {
    private final TransactionProductRepository transactionProductRepository;
    @Autowired
    public TransactionProductServiceImpl(TransactionProductRepository transactionProductRepository){
        this.transactionProductRepository = transactionProductRepository;
    }

    @Override
    public TransactionProductEntity createTransactionProduct (TransactionEntity transactionEntity, CartItemEntity cartItemEntity){
        TransactionProductEntity transactionProductEntity = new TransactionProductEntity(transactionEntity, cartItemEntity);
        return transactionProductRepository.save(transactionProductEntity);
    }
    public List<TransactionProductEntity> getTransactionProductEntitiesByTransactionInfoTid (Integer tid){
        return transactionProductRepository.getTransactionProductEntitiesByTransactionInfoTid(tid);
    }
}
